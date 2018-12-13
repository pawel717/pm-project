package com.pm.pmproject.view.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class NewWorkoutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private Spinner spinnerWorkoutType;
    private DaoSession daoSession;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        List<String> trainingTypes = new ArrayList<String>();
        for(TrainingType trainingType : this.daoSession.getTrainingTypeDao().loadAll()) {
            trainingTypes.add(trainingType.getName());
        }

        this.spinnerWorkoutType = findViewById(R.id.spinner_workout_type);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, trainingTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkoutType.setAdapter(spinnerAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intentHome = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_new_training:
                Intent intentNewTraining = new Intent(getBaseContext(), NewWorkoutActivity.class);
                startActivity(intentNewTraining);
                break;
            case R.id.nav_new_progress:
                Intent intentNewProgress = new Intent(getBaseContext(), NewProgressActivity.class);
                startActivity(intentNewProgress);
                break;
            case R.id.nav_statistics:
                Intent intentStatistics = new Intent(getBaseContext(), StatisticsActivity.class);
                startActivity(intentStatistics);
                break;
            case R.id.nav_training_history:
                Intent intentTrainingHistory = new Intent(getBaseContext(), WorkoutListActivity.class);
                startActivity(intentTrainingHistory);
                break;
            case R.id.nav_progress_history:
                Intent intentProgressHistory = new Intent(getBaseContext(), ProgressListActivity.class);
                startActivity(intentProgressHistory);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        Long elapsedTime = (long) Math.floor((SystemClock.elapsedRealtime() - chronometer.getBase())/1000);

        Intent intentSaveNewTraining = new Intent(getBaseContext(), SaveNewWorkoutActivity.class);
        // pass data in intent object
        intentSaveNewTraining.putExtra("elapsedTime", elapsedTime);
        intentSaveNewTraining.putExtra("workoutType", spinnerWorkoutType.getSelectedItem().toString());

        startActivity(intentSaveNewTraining);
    }
}
