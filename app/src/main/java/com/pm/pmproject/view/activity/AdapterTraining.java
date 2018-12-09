package com.pm.pmproject.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.entity.Training;

import java.util.List;

class AdapterTraining extends ArrayAdapter<Training> {

    private Context context;
    private List<Training> trainingList;

    public AdapterTraining(@NonNull Context context, int resource, @NonNull List<Training> objects) {
        super(context, resource, objects);
        this.context = context;
        this.trainingList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // if convertView is not being reused need to inflate view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_training, parent, false);
        }

        Training training = getItem(position);

        // populate training item view
        TextView trainingNameTextView = (TextView)convertView.findViewById(R.id.training_name);
        trainingNameTextView.setText(training.getType().getName());

        TextView trainingDateTextView = convertView.findViewById(R.id.training_date);
        trainingDateTextView.setText(training.getDate().toString());

        // set onClick listener for training item
        convertView.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // add training id to intent and show activity with training details
                    Intent intentWorkoutDetails = new Intent(context, WorkoutDetalisActivity.class);
                    intentWorkoutDetails.putExtra("trainingId", training.getId());
                    context.startActivity(intentWorkoutDetails);
                }
            });

        // return the completed view to render on screen
        return convertView;
    }
}
