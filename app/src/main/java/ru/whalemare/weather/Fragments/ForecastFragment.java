package ru.whalemare.weather.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.ParserConfig;
import ru.whalemare.weather.R;
import ru.whalemare.weather.adapters.WeathersAdapter;
import ru.whalemare.weather.interfaces.ForecastsCallback;
import ru.whalemare.weather.models.Weather;
import ru.whalemare.weather.tasks.WeatherTask;

public class ForecastFragment extends Fragment {
    private static final String TAG = "WHALETAG";
    private static final String KEY_WEATHER = "KEY_WEATHER";
    private static final String KEY_FORECASTS = "KEY_FORECASTS";

    private TextView pressRefresh;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private String weatherCode;
    private ArrayList<Weather> weathers;
    ParserConfig config;

    ForecastsCallback callback = new ForecastsCallback() {
        @Override
        public void onForecastsRetrieved(List<Weather> weathers) {
            setWeathers(weathers);
            adapter = new WeathersAdapter(weathers, listener);
            recyclerView.setAdapter(adapter);
        }
    };

    void setWeathers(List<Weather> weathers) {
        this.weathers = (ArrayList<Weather>) weathers;
    }

    public ForecastFragment() {
    }

    public static ForecastFragment newInstance(String weatherCode){
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(KEY_WEATHER, weatherCode);
        fragment.setArguments(args);
        return fragment;
    }


    private OnChooseForecastListener listener;
    public interface OnChooseForecastListener {
        void sendForecast(Weather weather);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnChooseForecastListener) context; // ??
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            this.weatherCode = getArguments().getString(KEY_WEATHER, null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_weathers);
        layoutManager = new LinearLayoutManager(getContext()); // или getActivity().getContext()?
        recyclerView.setLayoutManager(layoutManager);

        pressRefresh = (TextView) view.findViewById(R.id.press_refresh);
        config = new ParserConfig(getContext(), weatherCode, callback, listener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        super.onResume();
        if (!checkInternet()) {
            pressRefresh.setText("Нет подключения к интернету");
            Log.d(TAG, "onCreateView: интернета нет");
        } else {
            pressRefresh.setVisibility(View.GONE); // уберем TextView с layout
            WeatherTask weatherTask = new WeatherTask(config);
            weatherTask.execute();
        }
    }

    private boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE); // getApplicationContext
        NetworkInfo internetInfo = cm.getActiveNetworkInfo();
        return !(internetInfo == null || !internetInfo.isConnectedOrConnecting());
    }
}
