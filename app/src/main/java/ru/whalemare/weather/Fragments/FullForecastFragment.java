package ru.whalemare.weather.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.whalemare.weather.R;
import ru.whalemare.weather.activity.ChartActivity;
import ru.whalemare.weather.models.forecast.FORECAST;

public class FullForecastFragment extends Fragment {
    private static final String TAG = "WHALETAG";

    private static final String ARG_FORECAST = "FORECAST";

    private FORECAST forecast;

    public FullForecastFragment() {
    }

    public static FullForecastFragment newInstance(FORECAST forecast) {
        FullForecastFragment fragment = new FullForecastFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FORECAST, forecast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.forecast = getArguments().getParcelable(ARG_FORECAST);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_forecast, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getContext().getString(R.string.more));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String textNowTemperature = forecast.getTEMPERATURE().getMax() + getContext().getString(R.string.celcium);
        String textData = forecast.getDay() + "." + forecast.getMonth() + "." + forecast.getYear(); // на 21.09.2016
        String textTod = forecast.getHumanTod() + ", " + forecast.getHumanWeekday(); // Утро, Четверг
        String textCloudiness = forecast.getPHENOMENA().getHumanAboutWeather(); // Ясно: без осадков
        String textPressure = getContext().getString(R.string.atmosphere_pressure) + forecast.getPRESSURE().getMin()
                + "-" + forecast.getPRESSURE().getMax() + getContext().getString(R.string.mm_rt_st); // 776-788 мм.рт.ст.
        String textWind = getContext().getString(R.string.wind) + forecast.getWIND().getMin()
                + "-" + forecast.getWIND().getMax() + getContext().getString(R.string.m_s); // 2-4 м/c
        String textRelwet = getContext().getString(R.string.relwet) + forecast.getRELWET().getMin()
                + "-" + forecast.getRELWET().getMax() + " %"; // 77-78%
        String textHeat = getContext().getString(R.string.heat) + forecast.getHEAT().getMin() +
                getContext().getString(R.string.celcium) + " | " + forecast.getHEAT().getMax() + getContext().getString(R.string.celcium);

        TextView nowTemperature = (TextView) view.findViewById(R.id.now_temperature_full);
        TextView nowDate = (TextView) view.findViewById(R.id.now_data_full);
        TextView todWeekday = (TextView) view.findViewById(R.id.tod_weekday);
        TextView cloudiness = (TextView) view.findViewById(R.id.cloudiness);
        TextView pressure = (TextView) view.findViewById(R.id.pressure);
        TextView wind = (TextView) view.findViewById(R.id.wind);
        TextView relwet = (TextView) view.findViewById(R.id.relwet);
        TextView heat = (TextView) view.findViewById(R.id.heat);

        nowTemperature.setText(textNowTemperature);
        nowDate.setText(textData);
        todWeekday.setText(textTod);
        cloudiness.setText(textCloudiness);
        pressure.setText(textPressure);
        wind.setText(textWind);
        relwet.setText(textRelwet);
        heat.setText(textHeat);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        } else if (item.getItemId() == R.id.action_chart) {
            Log.d(TAG, "onOptionsItemSelected: статистика");
            Toast.makeText(getContext(), "Chart", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ChartActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
