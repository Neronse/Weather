package ru.whalemare.weather.activity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.whalemare.weather.R;
import ru.whalemare.weather.database.CitiesProvider;
import ru.whalemare.weather.models.Stats;

public class ChartActivity extends AppCompatActivity {

    @Bind(R.id.linechart) public LineChart lineChart;
    @Bind(R.id.toolbar) public Toolbar toolbar;
    public AutoCompleteTextView from;
    public AutoCompleteTextView to;

    private String gismeteoCode;
    private List<Stats> allStats = new ArrayList<>();
    List<Stats> statistics = new ArrayList<>();

    private String TAG = getClass().getSimpleName();
    List<String> labels;
    List<Entry> values;

    LimitLine maxLimitLine, minLimitLine;

    LineDataSet dataSet;
    LineData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

        gismeteoCode = getIntent().getStringExtra(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("График температур");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Cursor cursor = getContentResolver().query(CitiesProvider.STATS_CONTENT_URI, null,
                CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE + " = " + gismeteoCode, null, null);

        allStats = getStatsFromCursor(cursor);

        labels = new ArrayList<>();
        values = new ArrayList<>();

        for (Stats stat : allStats) {
            values.add(new Entry(stat.getTemperature(), values.size()));
            labels.add(stat.getDate());
        }

        dataSet = new LineDataSet(values, "");
        setConfigLineDataSet(dataSet, false, false, 1.8f, Color.WHITE, Color.WHITE);

        data = new LineData(labels, dataSet);
        data.setDrawValues(false);
        setConfigLineChart(true, "", false, Color.rgb(0, 191, 165), false, false);

        XAxis x = lineChart.getXAxis();
        YAxis y = lineChart.getAxisLeft();
        y.setDrawGridLines(false);
        x.setDrawGridLines(false);

        maxLimitLine = setMaxLimitLine(allStats.get(0).getMax());
        y.addLimitLine(maxLimitLine);

        minLimitLine = setMinLimitLine(allStats.get(0).getMin());
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

    MaterialCalendarView calendar;
    List<CalendarDay> dates = new ArrayList<>();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        if (id == R.id.action_choose_range) {
            DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_view, null);
            builder.setView(view);

            calendar = (MaterialCalendarView) view.findViewById(R.id.materialCalendar);

            calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

            
            builder.setTitle("Выберите даты");
            builder.setNegativeButton("Закрыть", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                changeDataChart(allStats);
            });

            builder.setPositiveButton("Ок", ((dialogInterface, i) -> {
                dates = calendar.getSelectedDates();
                statistics = getListOfStatsFromDatabase(dates);
                if (statistics.size() != 0) {
                    Collections.sort(statistics, (stats, t1) -> stats.getDate().compareTo(t1.getDate()));
                    changeDataChart(statistics);
                    statistics.clear();
                } else {
                    changeDataChart(allStats);
                }

                if (calendar.getSelectedDates().size() == 0) {
                    Toast.makeText(ChartActivity.this, "Ничего не выбрано. Показана стандартная статистика", Toast.LENGTH_SHORT).show();
                }
            }));
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Stats> getListOfStatsFromDatabase(@NonNull List<CalendarDay> dates) {

        final String q = "?";
        final String WHERE = CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE + " = " + q +
                " AND " + CitiesProvider.StatsMetaData.KEY_DATE + " = " + q;

        for (int i=0; i<dates.size(); i++) {
            String chooseDate =
                    dates.get(i).getDay() + "." +
                            (dates.get(i).getMonth()+1) + "." +
                            dates.get(i).getYear();

            if (labels.contains(chooseDate)) {
                Cursor cursor = getContentResolver().query(CitiesProvider.STATS_CONTENT_URI, null, WHERE, new String[]{gismeteoCode, chooseDate}, CitiesProvider.StatsMetaData.KEY_DATE);
                statistics.addAll(getStatsFromCursor(cursor));
            }
        }

        return statistics;
    }

    private List<Stats> getStatsFromCursor(@Nullable Cursor cursor) {
        List<Stats> statistics = new ArrayList<>();
        int min = 1000, max = -1000;

        if (cursor != null) {
            cursor.moveToFirst();

            do {
                String bottomLabel = cursor.getString(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_DATE));
                Integer value = cursor.getInt(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_T_MAX));

                statistics.add(new Stats(gismeteoCode, null, bottomLabel, value));

                if (min > value) {
                    min = value;
                }
                if (max < value) {
                    max = value;
                }
            } while (cursor.moveToNext());


            statistics.get(0).setMin(min);
            statistics.get(0).setMax(max);
            cursor.close();
            return statistics;
        } else {
            Toast.makeText(ChartActivity.this, "Cursor with charts is " + cursor, Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }

    void changeDataChart(List<Stats> statistics) {
        int max=-1000, min=1000;
        List<Entry> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (Stats stat : statistics) {
            int value = stat.getTemperature();

            values.add(new Entry(value, values.size()));
            labels.add(stat.getDate());

            if (min > value) {
                min = value;
            }
            if (max < value) {
                max = value;
            }
        }

        maxLimitLine = setMaxLimitLine(max);
        minLimitLine = setMinLimitLine(min);

        dataSet = new LineDataSet(values, "");
        setConfigLineDataSet(dataSet, false, false, 1.8f, Color.WHITE, Color.WHITE);
        dataSet.notifyDataSetChanged();

        data = new LineData(labels, dataSet);
        data.setDrawValues(false);
        data.notifyDataChanged();

        lineChart.setData(data);

        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
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

    public LimitLine setMaxLimitLine(int max) {
        LimitLine maxLimitLine = new LimitLine(max, "Максимальная температура");
        maxLimitLine.setLineWidth(4f);
        maxLimitLine.enableDashedLine(10f, 10f, 0f);
        maxLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        maxLimitLine.setTextSize(10f);
        return maxLimitLine;
    }

    public LimitLine setMinLimitLine(int min) {
        LimitLine minLimitLine = new LimitLine(min, "Минимальная температура");
        minLimitLine.setLineWidth(4f);
        minLimitLine.enableDashedLine(10f, 10f, 0f);
        minLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        minLimitLine.setTextSize(10f);
        return minLimitLine;
    }
}
