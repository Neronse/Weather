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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import ru.whalemare.weather.ParserConfig;
import ru.whalemare.weather.R;
import ru.whalemare.weather.adapters.WeathersAdapter;
import ru.whalemare.weather.di.AppComponent;
import ru.whalemare.weather.di.DaggerAppComponent;
import ru.whalemare.weather.di.RetrofitModule;
import ru.whalemare.weather.interfaces.ForecastClient;
import ru.whalemare.weather.models.forecast.FORECAST;
import ru.whalemare.weather.models.forecast.MMWEATHER;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForecastFragment extends Fragment {
    private static final String TAG = "WHALETAG";
    private static final String KEY_WEATHER = "KEY_WEATHER";

    private TextView pressRefresh;
    private SwipeRefreshLayout swipeRefresh;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private String gismeteoCode;
    ParserConfig config;

    public ForecastFragment() {
        this.setRetainInstance(true);
    }

    public static ForecastFragment newInstance(String gismeteoCode){
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(KEY_WEATHER, gismeteoCode);
        fragment.setArguments(args);

        return fragment;
    }


    private OnChooseForecastListener listener;
    public interface OnChooseForecastListener {
        void sendForecast(FORECAST forecast);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        AppComponent component = DaggerAppComponent.builder()
                .retrofitModule(new RetrofitModule(getContext()))
                .build();

        component.inject(this);

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
            this.gismeteoCode = getArguments().getString(KEY_WEATHER, null);
            tryToGetForecast();
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
        config = new ParserConfig(getContext(), gismeteoCode, listener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

//        tryToGetForecast();
        swipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tryToGetForecast();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    Snackbar snackbar;
    void tryToGetForecast(){
        if (!checkInternet()) {
            pressRefresh.setText("Нет подключения к интернету");
            snackbar = Snackbar.make(getView(), "Подключитесь к интернету", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", view -> {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    });
                    snackbar.show();
        } else {
            if (snackbar != null)
                if (snackbar.isShown())
                    snackbar.dismiss();
            downloadForecast(gismeteoCode);
        }
    }

    private boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internetInfo = cm.getActiveNetworkInfo();
        return !(internetInfo == null || !internetInfo.isConnectedOrConnecting());
    }

    //Retrofit 2.0
    @Inject ForecastClient client;
    private void downloadForecast(String gismeteoCode){
        Observable<MMWEATHER> weather = client.getData(gismeteoCode);

        Subscriber<MMWEATHER> subscriber = new Subscriber<MMWEATHER>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
                e.printStackTrace();
            }

            @Override
            public void onNext(MMWEATHER mmweather) {
                Log.d(TAG, "onNext");
                List<FORECAST> forecasts = mmweather.getREPORT().getTOWN().getFORECAST();
                adapter = new WeathersAdapter(forecasts, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        weather.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
