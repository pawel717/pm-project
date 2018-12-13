package com.pm.pmproject.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

public class WorkoutDetalisActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private Training training;
    private LinearLayout parentLinearLayout;
    private List<View> additionalFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detalis);
        additionalFields = new ArrayList<View>();
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());

        populateView();
    }

    private void populateView(){
        Intent intent = getIntent();

        training = daoSession.getTrainingDao().load(intent.getLongExtra("trainingId", 0));
        training.refresh();
        training.resetAttributes();

        TextView textViewWorkoutType = (TextView)findViewById(R.id.text_view_workout_type);
        textViewWorkoutType.setText(training.getType().getName());

        TextView textViewTrainingDate = (TextView)findViewById(R.id.text_view_training_date);
        textViewTrainingDate.setText(new DateFormatted(training.getDate()).toString());

        TextView textViewTrainingTime = (TextView)findViewById(R.id.text_view_training_time);
        textViewTrainingTime.setText(training.getDuration().toString());

        // for all attributes make o view with attribute name and value
        for(AttributeTraining attributeTraining : training.getAttributes()) {
            // create field view
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field_saved, null);
            // get name and value views
            TextView nameTextView = (TextView) rowView.findViewById(R.id.name_text_view);
            TextView valueTextView = (TextView) rowView.findViewById(R.id.value_text_view);
            // populate name and value views
            nameTextView.setText(attributeTraining.getAttribute().getType());
            valueTextView.setText(attributeTraining.getValue());
            // Add the new row at the end of list
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
        }

        Button buttonEdit = (Button)findViewById(R.id.button_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.edit_workout);
                TextView textViewWorkoutType = (TextView)(findViewById(R.id.text_view_workout_type));
                textViewWorkoutType.setText(training.getType().getName());
                TextView textViewTrainingDate = (TextView)(findViewById(R.id.text_view_training_date));
                textViewTrainingDate.setText(training.getDate().toString());
                TextView textViewDuration = (TextView)(findViewById(R.id.text_view_training_time));
                textViewDuration.setText(training.getDuration().toString());
                for(AttributeTraining attributeTraining : training.getAttributes()){
                    View rowView = addField();
                    EditText nameEditText = (EditText) rowView.findViewById(R.id.name_edit_text);
                    EditText valueEditText = (EditText) rowView.findViewById(R.id.value_edit_text);
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
        LinearLayout parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        additionalFields.add(rowView);
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
        return rowView;
    }

    public void onDelete(View v) {
        LinearLayout parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        additionalFields.remove((View) v.getParent());
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
            EditText nameEditText = (EditText) field.findViewById(R.id.name_edit_text);
            EditText valueEditText = (EditText) field.findViewById(R.id.value_edit_text);
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
