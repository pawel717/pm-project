package com.pm.pmproject.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.Attribute;
import com.pm.pmproject.model.entity.AttributeDao;
import com.pm.pmproject.model.entity.AttributeTraining;
import com.pm.pmproject.model.entity.AttributeTrainingDao;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.service.NotificationService;
import com.pm.pmproject.util.DateFormatted;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetalisActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DaoSession daoSession;
    private Training training;
    private LinearLayout parentLinearLayout;
    private List<View> additionalFields;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detalis);

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

        populateView();
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


    private void populateView(){
        Intent intent = getIntent();

        training = daoSession.getTrainingDao().load(intent.getLongExtra("trainingId", 0));
        training.refresh();
        training.resetAttributes();

        TextView textViewWorkoutType = findViewById(R.id.text_view_workout_type);
        textViewWorkoutType.setText(training.getType().getName());

        TextView textViewTrainingDate = findViewById(R.id.text_view_training_date);
        textViewTrainingDate.setText(new DateFormatted(training.getDate()).toString());

        TextView textViewTrainingTime = findViewById(R.id.text_view_training_time);
        textViewTrainingTime.setText(training.getDuration().toString());

        // for all attributes make o view with attribute name and value
        for(AttributeTraining attributeTraining : training.getAttributes()) {
            // create field view
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field_saved, null);
            // get name and value views
            TextView nameTextView = rowView.findViewById(R.id.name_text_view);
            TextView valueTextView = rowView.findViewById(R.id.value_text_view);
            // populate name and value views
            nameTextView.setText(attributeTraining.getAttribute().getType());
            valueTextView.setText(attributeTraining.getValue());
            // Add the new row at the end of list
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
        }

        Button buttonEdit = findViewById(R.id.button_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.edit_workout);
                TextView textViewWorkoutType = (findViewById(R.id.text_view_workout_type));
                textViewWorkoutType.setText(training.getType().getName());
                TextView textViewTrainingDate = (findViewById(R.id.text_view_training_date));
                textViewTrainingDate.setText(training.getDate().toString());
                TextView textViewDuration = (findViewById(R.id.text_view_training_time));
                textViewDuration.setText(training.getDuration().toString());
                for(AttributeTraining attributeTraining : training.getAttributes()){
                    View rowView = addField();
                    EditText nameEditText = rowView.findViewById(R.id.name_edit_text);
                    EditText valueEditText = rowView.findViewById(R.id.value_edit_text);
                    nameEditText.setText(attributeTraining.getAttribute().getType());
                    valueEditText.setText(attributeTraining.getValue());
                }
            }
        });
    }

    public void onAddField(View v) {
        addField();
    }

    private View addField() {
        LinearLayout parentLinearLayout = findViewById(R.id.parent_linear_layout);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        additionalFields.add(rowView);
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
        return rowView;
    }

    public void onDelete(View v) {
        LinearLayout parentLinearLayout = findViewById(R.id.parent_linear_layout);
        additionalFields.remove(v.getParent());
        parentLinearLayout.removeView((View) v.getParent());
    }

    public void save(View v) {
        // need to save training and attributes to database
        daoSession.getAttributeTrainingDao().queryBuilder()
                .where(AttributeTrainingDao.Properties.TrainingId.eq(training.getId()))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        // for all added fields
        for (View field : additionalFields) {
            // get values from view
            EditText nameEditText = field.findViewById(R.id.name_edit_text);
            EditText valueEditText = field.findViewById(R.id.value_edit_text);
            String name = nameEditText.getText().toString().trim();
            String value = valueEditText.getText().toString().trim();

            // dont add attribute if no name or no value provided
            if (name.isEmpty() || value.isEmpty())
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
            AttributeTraining attributeTraining = new AttributeTraining();
            attributeTraining.setAttributeId(attribute.getId());
            attributeTraining.setTrainingId(training.getId());
            attributeTraining.setValue(valueEditText.getText().toString());
            daoSession.getAttributeTrainingDao().save(attributeTraining);
        }

        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
}
