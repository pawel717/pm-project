package com.pm.pmproject.view.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DbOpenHelper;
import com.pm.pmproject.model.entity.Attribute;
import com.pm.pmproject.model.entity.AttributeTraining;
import com.pm.pmproject.model.entity.DaoMaster;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.model.entity.TrainingType;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    private Button trainingButton;
    private Button workoutList;
    private Button newProgressButton;

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

        workoutList = (Button) findViewById(R.id.button_training_history);
        workoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTrainingHistory = new Intent(getBaseContext(), WorkoutListActivity.class);
                startActivity(intentTrainingHistory);
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

        // enable logs for debugging purposes
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
