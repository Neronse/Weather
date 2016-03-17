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

import ru.whalemare.weather.R;
import ru.whalemare.weather.objects.Weather;
import ru.whalemare.weather.tasks.WeatherTask;

public class MainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WHALETAG";

    private String mParam1;
    private String mParam2;

    private TextView pressRefresh;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public MainFragment() {
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
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate: ");
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
            WeatherTask weatherTask = new WeatherTask(getActivity().getApplicationContext(), recyclerView, listener);
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
                WeatherTask weatherTask = new WeatherTask(getActivity().getApplicationContext(), recyclerView, listener);
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
