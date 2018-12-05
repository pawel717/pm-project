package com.pm.pmproject.view.activity;

import android.content.Context;
import android.content.Intent;
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
import com.pm.pmproject.model.entity.AttributeTraining;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.model.entity.TrainingType;
import com.pm.pmproject.model.entity.TrainingTypeDao;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SaveNewWorkoutActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    private List<View> additionalFields;
    private Training training;
    private Date trainingDate;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_new_workout);

        additionalFields = new ArrayList<View>();

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
        trainingDate = Calendar.getInstance().getTime();
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
            // save attribute
            Attribute attribute = new Attribute();
            attribute.setType(nameEditText.getText().toString());
            attribute.setId(daoSession.getAttributeDao().getKey(attribute));
            daoSession.getAttributeDao().save(attribute);
            // save attributeTraining
            AttributeTraining attributeTraining = new AttributeTraining();
            attributeTraining.setAttributeId(attribute.getId());
            attributeTraining.setValue(valueEditText.getText().toString());
            attributeTraining.setTrainingId(training.getId());
            daoSession.getAttributeTrainingDao().save(attributeTraining);
        }
    }
}
