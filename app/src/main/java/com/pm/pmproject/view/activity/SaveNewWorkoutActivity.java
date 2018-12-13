package com.pm.pmproject.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.Attribute;
import com.pm.pmproject.model.entity.AttributeDao;
import com.pm.pmproject.model.entity.AttributeTraining;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.model.entity.TrainingType;
import com.pm.pmproject.model.entity.TrainingTypeDao;
import com.pm.pmproject.service.NotificationService;
import com.pm.pmproject.util.DateFormatted;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SaveNewWorkoutActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    private List<View> additionalFields;
    private Training training;
    private Date trainingDate;
    private DaoSession daoSession;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_new_workout);

        additionalFields = new ArrayList<View>();
        additionalFields.add(findViewById(R.id.field_burned_calories));

        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());
        this.training = new Training();

        Intent intent = getIntent();
        TextView textViewWorkoutType = (TextView)(findViewById(R.id.text_view_workout_type));
        String workoutType = intent.getStringExtra("workoutType");
        textViewWorkoutType.setText(workoutType);
        TrainingType trainingType = this.daoSession.getTrainingTypeDao()
                .queryBuilder()
                .where(TrainingTypeDao.Properties.Name.eq(workoutType))
                .build()
                .list().get(0);
        training.setType(trainingType);

        TextView textViewTrainingDate = (TextView)(findViewById(R.id.text_view_training_date));
        trainingDate = new DateFormatted(Calendar.getInstance().getTime());

        textViewTrainingDate.setText(trainingDate.toString());
        training.setDate(trainingDate);

        TextView textViewDuration = (TextView)(findViewById(R.id.text_view_training_time));
        Long elapsedTime = intent.getLongExtra("elapsedTime", 0);
        textViewDuration.setText(String.valueOf(elapsedTime));
        training.setDuration(elapsedTime);
    }

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        additionalFields.add(rowView);
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
    }

    public void onDelete(View v) {
        additionalFields.remove((View) v.getParent());
        parentLinearLayout.removeView((View) v.getParent());
    }

    public void save(View v) {
        Intent intent = getIntent();
        Long elapsedTime = intent.getLongExtra("elapsedTime", 0);
        String workoutType = intent.getStringExtra("workoutType");

        // need to save training and attributes to database
        daoSession.getTrainingDao().save(training);

        // for all added fields
        for(View field : additionalFields) {
            // get values from view
            EditText nameEditText = (EditText)field.findViewById(R.id.name_edit_text);
            EditText valueEditText = (EditText)field.findViewById(R.id.value_edit_text);
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
            daoSession.getAttributeDao().save(attribute);
            // save attributeTraining
            AttributeTraining attributeTraining = new AttributeTraining();
            attributeTraining.setAttributeId(attribute.getId());
            attributeTraining.setValue(valueEditText.getText().toString());
            attributeTraining.setTrainingId(training.getId());

            daoSession.getAttributeTrainingDao().save(attributeTraining);
        }

        // add last training date to shared preferences
        preferences = getApplicationContext().getSharedPreferences("pmproject", Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        Date now = new Date();
        preferencesEditor.putLong("lastTrainingDate", now.getTime());
        preferencesEditor.apply();
        long lastTrainingDateMilis = preferences.getLong("lastTrainingDate", new Date().getTime());
        Log.d("asas", String.valueOf(lastTrainingDateMilis));
        // create notification service
        Intent intentNotificationService = new Intent(getBaseContext(), NotificationService.class);
        startService(intentNotificationService);


        // go to workout list activity
        Intent intentWorkoutList = new Intent(getBaseContext(), WorkoutListActivity.class);
        startActivity(intentWorkoutList);
    }
}
