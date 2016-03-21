package ru.whalemare.weather.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.whalemare.weather.ForecastsCallback;
import ru.whalemare.weather.ParserConfig;
import ru.whalemare.weather.R;
import ru.whalemare.weather.adapters.WeathersAdapter;
import ru.whalemare.weather.objects.Weather;
import ru.whalemare.weather.tasks.WeatherTask;

public class ForecastFragment extends Fragment {
    private static final String TAG = "WHALETAG";
    private static final String KEY_WEATHER = "KEY_WEATHER";

    private TextView pressRefresh;

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
        }
    };


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
            this.weatherCode = getArguments().getString(KEY_WEATHER, "-1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d(TAG, "onCreateView: ");

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_weathers);
        layoutManager = new LinearLayoutManager(getContext()); // или getActivity().getContext()?
        recyclerView.setLayoutManager(layoutManager);

        pressRefresh = (TextView) view.findViewById(R.id.press_refresh);
        config = new ParserConfig(getContext(), weatherCode, callback, listener);

        return view;
    }

    @Override
    public void onResume() {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_refresh:
                pressRefresh.setVisibility(View.GONE); // уберем TextView с layout
                WeatherTask weatherTask = new WeatherTask(config);
                weatherTask.execute();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE); // getApplicationContext
        NetworkInfo internetInfo = cm.getActiveNetworkInfo();
        return !(internetInfo == null || !internetInfo.isConnectedOrConnecting());
    }
}
