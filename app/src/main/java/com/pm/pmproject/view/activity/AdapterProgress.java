package com.pm.pmproject.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.entity.Progress;
import com.pm.pmproject.model.entity.Training;

import java.util.List;

class AdapterProgress extends ArrayAdapter<Progress> {

    private Context context;
    private List<Progress> progressList;

    public AdapterProgress(@NonNull Context context, int resource, @NonNull List<Progress> objects) {
        super(context, resource, objects);
        this.context = context;
        this.progressList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // if convertView is not being reused need to inflate view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false);
        }

        Progress progress = getItem(position);

        // populate training item view
        TextView progressNameTextView = (TextView)convertView.findViewById(R.id.progress_name);
        progressNameTextView.setText("Progress");

        TextView progressDateTextView = convertView.findViewById(R.id.progress_date);
        progressDateTextView.setText(progress.getDate().toString());

        // set onClick listener for training item
        convertView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // add training id to intent and show activity with training details
                        Intent intentProgressDetails = new Intent(context, ProgressDetailsActivity.class);
                        intentProgressDetails.putExtra("progressId", progress.getId());
                        context.startActivity(intentProgressDetails);
                    }
                });

        // return the completed view to render on screen
        return convertView;
    }
}
