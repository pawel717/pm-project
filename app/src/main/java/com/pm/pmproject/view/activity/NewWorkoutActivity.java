package com.pm.pmproject.view.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.Spinner;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.TrainingType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewWorkoutActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private Spinner spinnerWorkoutType;
    private DaoSession daoSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        List<String> trainingTypes = new ArrayList<String>();
        for(TrainingType trainingType : this.daoSession.getTrainingTypeDao().loadAll()) {
            trainingTypes.add(trainingType.getName());
        }

        this.spinnerWorkoutType = (Spinner) findViewById(R.id.spinner_workout_type);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, trainingTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkoutType.setAdapter(spinnerAdapter);
    }

    public void startWorkout(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            chronometer.start();
            running=true;

        }
    }

    public void pauseWorkout(View v){
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime()-chronometer.getBase();
            running=false;
        }
    }

    public void stopWorkout(View v){
        // if user has not paused chronometer before - stop it
        if(running)
            chronometer.stop();

        Long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();

        Intent intentSaveNewTraining = new Intent(getBaseContext(), SaveNewWorkoutActivity.class);
        // pass data in intent object
        intentSaveNewTraining.putExtra("elapsedTime", elapsedTime);
        intentSaveNewTraining.putExtra("workoutType", spinnerWorkoutType.getSelectedItem().toString());

        startActivity(intentSaveNewTraining);
    }
}
