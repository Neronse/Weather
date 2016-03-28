package ru.whalemare.weather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.whalemare.weather.R;
import ru.whalemare.weather.models.Weather;

public class FullForecastFragment extends Fragment {
    private static final String TAG = "WHALETAG";

    private static final String ARG_FORECAST = "FORECAST";

    private Weather weather;

    public FullForecastFragment() {
    }

    public static FullForecastFragment newInstance(Weather weather) {
        FullForecastFragment fragment = new FullForecastFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FORECAST, weather);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.weather = getArguments().getParcelable(ARG_FORECAST);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_forecast, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Подробно");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String textNowTemperature = weather.getTemperature_max() + getContext().getString(R.string.celcium);
        String textData = weather.getDay() + "." + weather.getMonth() + "." + weather.getYear(); // на 21.09.2016
        String textTod = weather.getHumanTod() + ", " + weather.getHumanWeekday(); // Утро, Четверг
        String textCloudiness = weather.getHumanAboutWeather(); // Ясно: без осадков
        String textPressure = getContext().getString(R.string.atmosphere_pressure) + weather.getPressure_min()
                + "-" + weather.getPressure_max() + getContext().getString(R.string.mm_rt_st); // 776-788 мм.рт.ст.
        String textWind = getContext().getString(R.string.wind) + weather.getWind_min()
                + "-" + weather.getWind_max() + getContext().getString(R.string.m_s); // 2-4 м/c
        String textRelwet = getContext().getString(R.string.relwet) + weather.getRelwet_min()
                + "-" + weather.getRelwet_max() + " %"; // 77-78%
        String textHeat = getContext().getString(R.string.heat) + weather.getHeat_min() +
                getContext().getString(R.string.celcium) + " | " + weather.getHeat_max() + getContext().getString(R.string.celcium);

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
