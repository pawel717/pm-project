package com.pm.pmproject.view.activity;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.model.entity.TrainingDao;
import com.pm.pmproject.model.entity.TrainingTypeDao;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListActivity extends AppCompatActivity {

    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        final ListView listview = (ListView) findViewById(R.id.workout_list);

        daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());
        List<Training> trainings = daoSession.getTrainingDao().queryBuilder()
                .orderDesc(TrainingDao.Properties.Date)
                .list();

        AdapterTraining adapter = new AdapterTraining(this, android.R.layout.simple_list_item_1, trainings);
        listview.setAdapter(adapter);
    }
}
