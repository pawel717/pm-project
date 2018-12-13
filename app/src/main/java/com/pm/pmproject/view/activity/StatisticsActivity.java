package com.pm.pmproject.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatisticsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DaoSession daoSession;
    private long relativeTime;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());

        LineChart chart = findViewById(R.id.chart);

        List<Training> trainings = daoSession.getTrainingDao().loadAll();

        List<Entry> entries = new ArrayList<Entry>();

        relativeTime = new Date().getTime();

        for (Training training : trainings) {

            // turn data into Entry objects
            entries.add(new Entry(training.getDate().getTime() - relativeTime, training.getDuration()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "training duration"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#115dd8"));
        dataSet.setValueTextColor(Color.parseColor("#2a3342")); // styling, ...

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);


        Description description = new Description();
        description.setText("Date");
        chart.setDescription(description);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");

            @Override
            public  String getFormattedValue(float value, AxisBase axis) {

                long millis = (long) value+relativeTime;
                return mFormat.format(new Date(millis));
            }
        });
        xAxis.setLabelRotationAngle(-30f);

        chart.invalidate(); // refresh
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
}
