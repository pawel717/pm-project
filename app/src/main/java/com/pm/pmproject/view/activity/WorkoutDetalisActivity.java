package com.pm.pmproject.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.AttributeTraining;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;

public class WorkoutDetalisActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private Training training;
    private LinearLayout parentLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detalis);

        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());

        Intent intent = getIntent();

        training = daoSession.getTrainingDao().load(intent.getLongExtra("trainingId", 0));

        TextView textViewWorkoutType = (TextView)findViewById(R.id.text_view_workout_type);
        textViewWorkoutType.setText(training.getType().getName());

        TextView textViewTrainingDate = (TextView)findViewById(R.id.text_view_training_date);
        textViewTrainingDate.setText(training.getDate().toString());

        TextView textViewTrainingTime = (TextView)findViewById(R.id.text_view_training_time);
        textViewTrainingTime.setText(training.getDuration().toString());

        // for all attributes make o view with attribute name and value
        for(AttributeTraining attributeTraining : training.getAttributes()) {
            // create field view
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field_saved, null);
            // get name and value views
            TextView nameTextView = (TextView)rowView.findViewById(R.id.name_text_view);
            TextView valueTextView = (TextView)rowView.findViewById(R.id.value_text_view);
            // populate name and value views
            nameTextView.setText(attributeTraining.getAttribute().getType());
            valueTextView.setText(attributeTraining.getValue());
            // Add the new row at the end of list
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());

            // set listeners - make popup with options (delete, edit, cancel)
            nameTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            valueTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }


    }
}
