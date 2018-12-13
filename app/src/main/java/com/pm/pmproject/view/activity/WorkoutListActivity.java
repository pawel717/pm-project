package com.pm.pmproject.view.activity;

import android.app.ListActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.MenuItem;
import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.model.entity.TrainingDao;
import com.pm.pmproject.model.entity.TrainingTypeDao;
import android.content.Intent;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class WorkoutListActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private DaoSession daoSession;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final ListView listview = findViewById(R.id.workout_list);

        daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());
        List<Training> trainings = daoSession.getTrainingDao().queryBuilder()
                .orderDesc(TrainingDao.Properties.Date)
                .list();

        AdapterTraining adapter = new AdapterTraining(this, android.R.layout.simple_list_item_1, trainings);
        listview.setAdapter(adapter);
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
