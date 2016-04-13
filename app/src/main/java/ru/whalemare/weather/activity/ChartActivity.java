package ru.whalemare.weather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.whalemare.weather.R;
import ru.whalemare.weather.charts.LineView;

public class ChartActivity extends AppCompatActivity {

    @Bind(R.id.line_view)
    public LineView lineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ArrayList<String> bottom = new ArrayList<>();
        for (int i=0; i<20; i++){
            bottom.add("" + i);
        }

        ArrayList<ArrayList<Integer>> integers = new ArrayList<>();
        ArrayList<Integer> subIntegers = new ArrayList<>();

        Random random = new Random();

        for (Integer i=0; i < 20; i++){
            subIntegers.add(random.nextInt(10));
        }
        integers.add(subIntegers);

        ButterKnife.bind(this);
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_All);
        lineView.setBottomTextList(bottom);
        lineView.setDataList(integers);
    }
}
