package com.pm.pmproject.view.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DbOpenHelper;
import com.pm.pmproject.model.entity.DaoMaster;
import com.pm.pmproject.model.entity.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {
    private Button trainingButton;
    private Button workoutList;
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

        DaoSession mDaoSession = new DaoMaster(
                new DbOpenHelper(getApplicationContext(), "database.db").getWritableDb())
                .newSession();


    }
}
