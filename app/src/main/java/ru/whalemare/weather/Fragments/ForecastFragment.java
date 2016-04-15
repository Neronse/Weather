package ru.whalemare.weather.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.whalemare.weather.ParserConfig;
import ru.whalemare.weather.R;
import ru.whalemare.weather.adapters.WeathersAdapter;
import ru.whalemare.weather.database.CitiesProvider;
import ru.whalemare.weather.di.App;
import ru.whalemare.weather.models.ForecastRestApiModel;
import ru.whalemare.weather.models.forecast.FORECAST;
import ru.whalemare.weather.models.forecast.MMWEATHER;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForecastFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private static final String KEY_GISMETEO_CODE = "KEY_GISMETEO_CODE";

    private TextView pressRefresh;
    private SwipeRefreshLayout swipeRefresh;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private String gismeteoCode;
    ParserConfig config;

    @Inject ForecastRestApiModel model;

    public ForecastFragment() {
        this.setRetainInstance(true);
    }

    public static ForecastFragment newInstance(String gismeteoCode){
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(KEY_GISMETEO_CODE, gismeteoCode);
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

        App.get(context).getComponent().inject(this);

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
        Log.d(TAG, "onCreate");

        if (getArguments() != null) {
            this.gismeteoCode = getArguments().getString(KEY_GISMETEO_CODE, null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        tryToGetForecast();

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
        Log.d(TAG, "onStart");
        swipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefresh.setOnRefreshListener(() -> {
            tryToGetForecast();
            swipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
    private void downloadForecast(String gismeteoCode){
        Observable<MMWEATHER> observable = model.getData(gismeteoCode);

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
                List<FORECAST> forecasts = mmweather.getREPORT().getTOWN().getFORECAST();
                adapter = new WeathersAdapter(forecasts, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                for (int i=0; i<forecasts.size(); i++){
                    FORECAST forecast = forecasts.get(i);
                    int tod = forecast.getTod();
                    String date = Integer.toString(forecast.getDay()) + "." + Integer.toString(forecast.getMonth()) +
                            "." + Integer.toString(forecast.getYear()); // DD.MM.YYYY

                    if (isExistStatsBy(tod, date, gismeteoCode)){
                        Log.d(TAG, "Data by tod = " + tod + "\n" +
                                "by date = " + date + "\n" +
                                "by gismeteoCode = " + gismeteoCode + " already exist!");

                    } else {
                        Log.d(TAG, "Inserting data");
                        ContentValues values = new ContentValues();

                        values.put(CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE, gismeteoCode);
                        values.put(CitiesProvider.StatsMetaData.KEY_TOD, Integer.toString(tod));
                        values.put(CitiesProvider.StatsMetaData.KEY_DATE, date);
                        values.put(CitiesProvider.StatsMetaData.KEY_T_MAX, forecast.getTEMPERATURE().getMax());
                        values.put(CitiesProvider.StatsMetaData.KEY_T_MIN, forecast.getTEMPERATURE().getMin());

                        getContext().getContentResolver().insert(CitiesProvider.STATS_CONTENT_URI, values);
                    }
                }
            }

            private boolean isExistStatsBy(int tod, String date, String gismeteoCode) {
                boolean exist = false;
                final String[] projection = {
                        CitiesProvider.StatsMetaData.KEY_ID,
                        CitiesProvider.StatsMetaData.KEY_TOD,
                        CitiesProvider.StatsMetaData.KEY_DATE,
                        CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE
                };

                Cursor cursor = getContext().getContentResolver().query(CitiesProvider.STATS_CONTENT_URI, projection,
                        CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE + " = ? AND " +
                        CitiesProvider.StatsMetaData.KEY_TOD + " = ? AND " +
                        CitiesProvider.StatsMetaData.KEY_DATE + " = ?",
                        new String[] {gismeteoCode, Integer.toString(tod), date}, null);

                if (cursor.getCount() > 0) {
                    exist = true;
                } else {
                    Log.d(TAG, "-------");
                    printDataInStats(cursor);
                    Log.d(TAG, "-------");
                    exist = false;
                }

                cursor.close();
                return exist;
            }


            private void printDataInStats(Cursor cursor) {
                cursor.moveToFirst();
                while (cursor.moveToNext()){
                    Log.d(TAG, cursor.getString(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_TOD)));
                    Log.d(TAG, cursor.getString(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_DATE)));
                    Log.d(TAG, cursor.getString(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE)));
                    Log.d(TAG, "========");
                }
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
