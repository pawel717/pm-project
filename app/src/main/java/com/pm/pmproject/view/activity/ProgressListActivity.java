package com.pm.pmproject.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Progress;
import com.pm.pmproject.model.entity.ProgressDao;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.model.entity.TrainingDao;

import java.util.List;

public class ProgressListActivity extends AppCompatActivity {

    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_list);

        final ListView listview = (ListView) findViewById(R.id.progress_list);

        daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());
        List<Progress> progresses = daoSession.getProgressDao().queryBuilder()
                .orderDesc(ProgressDao.Properties.Date)
                .list();

        AdapterProgress adapter = new AdapterProgress(this, android.R.layout.simple_list_item_1, progresses);
        listview.setAdapter(adapter);
    }
}
