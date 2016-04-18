package ru.whalemare.weather.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
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

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

        String gismeteoCode = getIntent().getStringExtra(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE);
        Log.d(TAG, "gismeteoCode = " + gismeteoCode);
        Toast.makeText(ChartActivity.this, gismeteoCode, Toast.LENGTH_SHORT).show();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("График температур");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Cursor cursor = getContentResolver().query(CitiesProvider.STATS_CONTENT_URI, null,
                CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE + " = " + gismeteoCode, null, null);

        List<String> labels = new ArrayList<>();
        List<Entry> values = new ArrayList<>();
        List<Entry> minValues = new ArrayList<>();

        int min = 1000, max = -1000;

        if(cursor != null) {
            cursor.moveToFirst();

            do {
                String bottomLabel = cursor.getString(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_DATE));
                Integer value = cursor.getInt(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_T_MAX));
                Integer minValue = cursor.getInt(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_T_MIN));

                Log.d(TAG, "value = " + value);

                if (min > value) {
                    min = value;
                }
                if (max < value) {
                    max = value;
                }

                labels.add(bottomLabel);
                values.add(new Entry(value, values.size()));
                minValues.add(new Entry(minValue, minValues.size()));
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(ChartActivity.this, "Cursor with charts is " + cursor, Toast.LENGTH_SHORT).show();
        }

        LineDataSet dataSet = new LineDataSet(values, "");
        setConfigLineDataSet(dataSet, false, false, 1.8f, Color.WHITE, Color.WHITE);

        LineData data = new LineData(labels, dataSet);
        data.setDrawValues(false);

        setConfigLineChart(true, "", false, Color.rgb(0, 191, 165), false, false);

        XAxis x = lineChart.getXAxis();
        YAxis y = lineChart.getAxisLeft();
        y.setDrawGridLines(false);
        x.setDrawGridLines(false);

        LimitLine maxLimitLine = new LimitLine(max, "Максимальная температура");
        maxLimitLine.setLineWidth(4f);
        maxLimitLine.enableDashedLine(10f, 10f, 0f);
        maxLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        maxLimitLine.setTextSize(10f);
        y.addLimitLine(maxLimitLine);

        LimitLine minLimitLine = new LimitLine(min, "Минимальная температура");
        minLimitLine.setLineWidth(4f);
        minLimitLine.enableDashedLine(10f, 10f, 0f);
        minLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        minLimitLine.setTextSize(10f);
        y.addLimitLine(minLimitLine);

        lineChart.setData(data);
        lineChart.animateY(1000);
        lineChart.setCameraDistance(5.5f);
        lineChart.invalidate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        if (id == R.id.action_choose_range) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_view, null);
            builder.setView(view);
            builder.setTitle("Выберите диапазон дат");
            builder.show();
//            AlertDialog dialog = builder.create();
//            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setConfigLineDataSet(LineDataSet dataSet, boolean drawCubic, boolean drawCircles, float lineWidth, int circleColor, int lineColor) {
        dataSet.setDrawCubic(drawCubic);
        dataSet.setDrawCircles(drawCircles);
        dataSet.setLineWidth(lineWidth);
        dataSet.setCircleColor(circleColor);
        dataSet.setColor(lineColor);
    }

    private void setConfigLineChart(boolean pinchZoom, String description, boolean enableLegend, int backgroundColor, boolean drawGridBackground, boolean enableRightAxis) {
        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(pinchZoom);
        lineChart.setDescription(description);
        lineChart.getLegend().setEnabled(enableLegend);
        lineChart.setBackgroundColor(backgroundColor);
        lineChart.setDrawGridBackground(drawGridBackground);
        lineChart.getAxisRight().setEnabled(enableRightAxis);
    }

}
