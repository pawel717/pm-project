package com.pm.pmproject.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.pm.pmproject.R;
import com.pm.pmproject.model.database.DaoSessionProvider;
import com.pm.pmproject.model.entity.DaoSession;
import com.pm.pmproject.model.entity.Training;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatisticsActivity extends AppCompatActivity {
    private DaoSession daoSession;
    private long relativeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        this.daoSession = DaoSessionProvider.getDaoSession(getApplicationContext());

        LineChart chart = (LineChart) findViewById(R.id.chart);

        List<Training> trainings = daoSession.getTrainingDao().loadAll();

        List<Entry> entries = new ArrayList<Entry>();

        relativeTime = new Date().getTime();

        for (Training training : trainings) {

            // turn data into Entry objects
            entries.add(new Entry(training.getDate().getTime() - relativeTime, training.getDuration()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "training duration"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#115dd8"));
        dataSet.setValueTextColor(Color.parseColor("#2a3342")); // styling, ...

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);


        Description description = new Description();
        description.setText("Date");
        chart.setDescription(description);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");

            @Override
            public  String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.HOURS.toMillis((long) value+relativeTime);
                return mFormat.format(new Date(millis));
            }
        });
        xAxis.setLabelRotationAngle(-30f);

        chart.invalidate(); // refresh
    }
}
