package ru.whalemare.weather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.whalemare.weather.ForecastService;
import ru.whalemare.weather.R;
import ru.whalemare.weather.fragments.CityFragment;
import ru.whalemare.weather.models.MMWEATHER;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CityActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        String TAG = "WHALETAG";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        testRetrofit();

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_city, new CityFragment().newInstance())
                    .commit();
    }

    private void testRetrofit(){
        String BASE_URL = "http://informer.gismeteo.ru/xml/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        ForecastService forecastService = retrofit.create(ForecastService.class);
        Observable<MMWEATHER> weather = forecastService.getData("30823");

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
                Log.d(TAG, mmweather.getREPORT().getTOWN().getFORECAST().get(0).getDay() + "");
                Log.d(TAG, mmweather.getREPORT().getTOWN().getFORECAST().get(0).getMonth() + "");
                Log.d(TAG, mmweather.getREPORT().getTOWN().getFORECAST().get(0).getYear() + "");
            }
        };

        weather.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
