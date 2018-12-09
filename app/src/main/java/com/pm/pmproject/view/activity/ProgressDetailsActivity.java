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
import com.pm.pmproject.model.entity.AttributeProgress;
import com.pm.pmproject.model.entity.AttributeTraining;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Progress;
import com.pm.pmproject.model.entity.Training;

public class ProgressDetailsActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private Progress progress;
    private LinearLayout parentLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_details);

        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());

        Intent intent = getIntent();

        progress = daoSession.getProgressDao().load(intent.getLongExtra("progressId", 0));

        TextView textViewProgressDate = (TextView)findViewById(R.id.text_view_progress_date);
        textViewProgressDate.setText(progress.getDate().toString());

        // for all attributes make o view with attribute name and value
        for(AttributeProgress attributeProgress : progress.getAttributes()) {
            // create field view
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field_saved, null);
            // get name and value views
            TextView nameTextView = (TextView) rowView.findViewById(R.id.name_text_view);
            TextView valueTextView = (TextView) rowView.findViewById(R.id.value_text_view);
            // populate name and value views
            nameTextView.setText(attributeProgress.getAttribute().getType());
            valueTextView.setText(attributeProgress.getValue());
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
