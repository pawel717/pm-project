package com.pm.pmproject.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.util.DateFormatted;

import java.util.List;

class AdapterTraining extends ArrayAdapter<Training> {

    private Context context;
    private List<Training> trainingList;
    private DaoSession daoSession;

    public AdapterTraining(@NonNull Context context, int resource, @NonNull List<Training> objects) {
        super(context, resource, objects);
        this.context = context;
        this.trainingList = objects;
        this.daoSession = DaoSessionProvider.getDaoSession(context.getApplicationContext());
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
        trainingDateTextView.setText(new DateFormatted(training.getDate()).toString());

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

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View popupView = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_popup, null);

                Button button_yes = popupView.findViewById(R.id.button_yes);
                Button button_no = popupView.findViewById(R.id.button_no);

                PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, true);

                popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

                button_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                button_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v){
                        daoSession.getTrainingDao().delete(training);
                        clear();
                        addAll(daoSession.getTrainingDao().loadAll());
                        notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });

                return true;
            }
        });

        // return the completed view to render on screen
        return convertView;
    }
}
