package com.pm.pmproject.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.Attribute;
import com.pm.pmproject.model.entity.AttributeDao;
import com.pm.pmproject.model.entity.AttributeProgress;
import com.pm.pmproject.model.entity.AttributeTraining;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Progress;
import com.pm.pmproject.util.DateFormatted;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewProgressActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private LinearLayout parentLinearLayout;
    private List<View> additionalFields;
    private Progress progress;
    private Date progressDate;
    private DaoSession daoSession;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_progress);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        additionalFields = new ArrayList<View>();

        parentLinearLayout = findViewById(R.id.parent_linear_layout);

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());
        this.progress = new Progress();

        addPredefinedField("Chest circumference", "");
        addPredefinedField("Biceps circumference", "");
        addPredefinedField("Waist circumference", "");
        addPredefinedField("Forearm circumference", "");
        addPredefinedField("Thigh circumference", "");
        addPredefinedField("Hip circumference", "");
        addPredefinedField("Body weight", "");

        TextView textViewProgressDate = (findViewById(R.id.text_view_progress_date));
        progressDate = new DateFormatted(Calendar.getInstance().getTime());
        progress.setDate(progressDate);
        textViewProgressDate.setText(progressDate.toString());
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
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        additionalFields.add(rowView);
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
    }

    public void addPredefinedField(String name, String value) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        EditText nameEditText = rowView.findViewById(R.id.name_edit_text);
        EditText valueEditText = rowView.findViewById(R.id.value_edit_text);
        nameEditText.setText(name);
        valueEditText.setText(value);
        nameEditText.setFocusable(false);
        nameEditText.setFocusableInTouchMode(false);
        nameEditText.setInputType(InputType.TYPE_NULL);
        // Add the new row before the add field button.
        additionalFields.add(rowView);
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
    }

    public void onDelete(View v) {
        additionalFields.remove(v.getParent());
        parentLinearLayout.removeView((View) v.getParent());
    }

    public void save(View v) {

        // need to save training and attributes to database
        daoSession.getProgressDao().save(progress);

        // for all added fields
        for(View field : additionalFields) {
            // get values from view
            EditText nameEditText = field.findViewById(R.id.name_edit_text);
            EditText valueEditText = field.findViewById(R.id.value_edit_text);
            String name = nameEditText.getText().toString().trim();
            String value = valueEditText.getText().toString().trim();

            // dont add attribute if no name or no value provided
            if(name.isEmpty() || value.isEmpty())
                continue;

            // save attribute
            Attribute attribute = daoSession.getAttributeDao().queryBuilder()
                    .where(AttributeDao.Properties.Type.eq(nameEditText.getText().toString()))
                    .build().unique();
            if(attribute == null) {
                attribute = new Attribute();
                attribute.setType(nameEditText.getText().toString());
                daoSession.getAttributeDao().save(attribute);
            }
            // save attributeTraining
            AttributeProgress attributeProgress = new AttributeProgress();
            attributeProgress.setAttributeId(attribute.getId());
            attributeProgress.setValue(valueEditText.getText().toString());
            attributeProgress.setProgressId(progress.getId());

            daoSession.getAttributeProgressDao().save(attributeProgress);
        }

        // go to workout list activity
        Intent intentProgressList = new Intent(getBaseContext(), ProgressListActivity.class);
        startActivity(intentProgressList);
    }
}
