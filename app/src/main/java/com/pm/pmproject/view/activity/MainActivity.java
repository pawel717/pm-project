package com.pm.pmproject.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.pm.pmproject.R;
import com.pm.pmproject.service.NotificationService;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button trainingButton;
    private Button newProgressButton;
    private Button statisticsButton;
    private Button workoutList;
    private Button progressHistoryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        trainingButton = (Button) findViewById(R.id.button_new_training);
        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewTraining = new Intent(getBaseContext(), NewWorkoutActivity.class);
                startActivity(intentNewTraining);
            }
        });

        newProgressButton = (Button) findViewById(R.id.button_new_progress);
        newProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewProgress = new Intent(getBaseContext(), NewProgressActivity.class);
                startActivity(intentNewProgress);
            }
        });

        statisticsButton = (Button) findViewById(R.id.button_statistics);
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStatistics = new Intent(getBaseContext(), StatisticsActivity.class);
                startActivity(intentStatistics);
            }
        });

        workoutList = (Button) findViewById(R.id.button_training_history);
        workoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTrainingHistory = new Intent(getBaseContext(), WorkoutListActivity.class);
                startActivity(intentTrainingHistory);
            }
        });

        progressHistoryButton = (Button) findViewById(R.id.button_progress_history);
        progressHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProgressHistory = new Intent(getBaseContext(), ProgressListActivity.class);
                startActivity(intentProgressHistory);
            }
        });



        // enable logs for debugging purposes
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
