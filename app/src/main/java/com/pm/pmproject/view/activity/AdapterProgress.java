package com.pm.pmproject.view.activity;

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
import com.pm.pmproject.model.entity.Progress;
import com.pm.pmproject.model.entity.Training;
import com.pm.pmproject.util.DateFormatted;

import java.util.List;

class AdapterProgress extends ArrayAdapter<Progress> {

    private Context context;
    private List<Progress> progressList;
    private DaoSession daoSession;


    public AdapterProgress(@NonNull Context context, int resource, @NonNull List<Progress> objects) {
        super(context, resource, objects);
        this.context = context;
        this.progressList = objects;
        this.daoSession = DaoSessionProvider.getDaoSession(context.getApplicationContext());
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
        progressDateTextView.setText(new DateFormatted(progress.getDate()).toString());

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
                        daoSession.getProgressDao().delete(progress);
                        clear();
                        addAll(daoSession.getProgressDao().loadAll());
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
