package ru.whalemare.weather.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private TextView pressRefresh;
    private SwipeRefreshLayout swipeRefresh;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private String weatherCode;
    ParserConfig config;

    ForecastsCallback callback = new ForecastsCallback() {
        @Override
        public void onForecastsRetrieved(List<Weather> weathers) {
            adapter = new WeathersAdapter(weathers, listener);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };

    public ForecastFragment() {
        this.setRetainInstance(true);
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
            listener = (OnChooseForecastListener) context;
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

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_weathers);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        pressRefresh = (TextView) view.findViewById(R.id.press_refresh);
        config = new ParserConfig(getContext(), weatherCode, callback, listener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        tryToGetForecast();

        swipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tryToGetForecast();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * Depending on whether there is Internet or not makes visible RecyclerView or TextView
     */
    Snackbar snackbar;
    void tryToGetForecast(){
        if (!checkInternet()) {
            pressRefresh.setText("Нет подключения к интернету");
            snackbar = Snackbar.make(getView(), "Подключитесь к интернету", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    });
                    snackbar.show();
        } else {
            if (snackbar != null)
                if (snackbar.isShown())
                    snackbar.dismiss();
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
