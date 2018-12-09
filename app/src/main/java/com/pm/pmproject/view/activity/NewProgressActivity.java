package com.pm.pmproject.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.Attribute;
import com.pm.pmproject.model.entity.AttributeProgress;
import com.pm.pmproject.model.entity.AttributeTraining;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Progress;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.model.entity.TrainingType;
import com.pm.pmproject.model.entity.TrainingTypeDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewProgressActivity extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    private List<View> additionalFields;
    private Progress progress;
    private Date progressDate;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_progress);

        additionalFields = new ArrayList<View>();

        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());
        this.progress = new Progress();

        addPredefinedField("Chest circumference", "");
        addPredefinedField("Biceps circumference", "");
        addPredefinedField("Waist circumference", "");
        addPredefinedField("Forearm circumference", "");
        addPredefinedField("Thigh circumference", "");
        addPredefinedField("Hip circumference", "");
        addPredefinedField("Body weight", "");

        TextView textViewProgressDate = (TextView)(findViewById(R.id.text_view_progress_date));
        progressDate = Calendar.getInstance().getTime();
        progress.setDate(progressDate);
        textViewProgressDate.setText(progressDate.toString());
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
        EditText nameEditText = (EditText)rowView.findViewById(R.id.name_edit_text);
        EditText valueEditText = (EditText)rowView.findViewById(R.id.value_edit_text);
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
        additionalFields.remove((View) v.getParent());
        parentLinearLayout.removeView((View) v.getParent());
    }

    public void save(View v) {

        // need to save training and attributes to database
        daoSession.getProgressDao().save(progress);

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
            Attribute attribute = new Attribute();
            attribute.setType(nameEditText.getText().toString());
            attribute.setId(daoSession.getAttributeDao().getKey(attribute));
            daoSession.getAttributeDao().save(attribute);
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
