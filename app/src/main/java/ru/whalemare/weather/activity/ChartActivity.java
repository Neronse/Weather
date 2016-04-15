package ru.whalemare.weather.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.whalemare.weather.R;
import ru.whalemare.weather.database.CitiesProvider;

public class ChartActivity extends AppCompatActivity {

    @Bind(R.id.linechart)
    public LineChart lineChart;

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

        String gismeteoCode = getIntent().getStringExtra(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE);
        Log.d(TAG, "gismeteoCode = " + gismeteoCode);
        Toast.makeText(ChartActivity.this, gismeteoCode, Toast.LENGTH_SHORT).show();

        Cursor cursor = getContentResolver().query(CitiesProvider.STATS_CONTENT_URI, null,
                CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE + " = " + gismeteoCode, null, null);

        List<String> labels = new ArrayList<>();
        List<Entry> values = new ArrayList<>();

        if(cursor != null) {
            cursor.moveToFirst();

            do {
                String bottomLabel = cursor.getString(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_DATE));
                Integer maxValue = cursor.getInt(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_T_MAX));
                Integer minValue = cursor.getInt(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_T_MIN));

                labels.add(bottomLabel);
                values.add(new Entry(maxValue, values.size()));

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(ChartActivity.this, "Cursor with charts is " + cursor, Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        LineDataSet dataSet = new LineDataSet(values, "Температура");
        dataSet.setDrawCubic(true);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(1.8f);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setColor(Color.WHITE);

        LineData data = new LineData(labels, dataSet);
        data.setDrawValues(false);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);
        lineChart.setDescription("");
        lineChart.getLegend().setEnabled(false);
        lineChart.setBackgroundColor(Color.rgb(0, 191, 165));
        lineChart.setDrawGridBackground(false);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getAxisRight().setEnabled(false);

        XAxis x = lineChart.getXAxis();
        x.setDrawGridLines(false);

        YAxis y = lineChart.getAxisLeft();
        y.setDrawGridLines(false);


        lineChart.setData(data);
        lineChart.animateY(1000);
        lineChart.invalidate();
    }

}
