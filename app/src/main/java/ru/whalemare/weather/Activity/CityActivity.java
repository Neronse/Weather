package ru.whalemare.weather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.whalemare.weather.R;
import ru.whalemare.weather.fragments.CityFragment;

public class CityActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        String TAG = "WHALETAG";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

//        testRetrofit();

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_city, new CityFragment().newInstance())
                    .commit();
    }

/*    private void testRetrofit(){
        String BASE_URL = "http://informer.gismeteo.ru/xml/";

        RetrofitTask retrofit = new RetrofitTask.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        ForecastClient forecastService = retrofit.create(ForecastClient.class);
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
                toListWeather(mmweather.getREPORT().getTOWN().getFORECAST());
                Log.d(TAG, mmweather.getREPORT().getTOWN().getFORECAST().get(0).getDay() + "");
                Log.d(TAG, mmweather.getREPORT().getTOWN().getFORECAST().get(0).getMonth() + "");
                Log.d(TAG, mmweather.getREPORT().getTOWN().getFORECAST().get(0).getYear() + "");
            }
        };

        weather.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private List<Weather> toListWeather(List<MMWEATHER.FORECAST> getted) {

        List<Weather> weathers = new ArrayList<>();

        Weather weather = new Weather();
        for (MMWEATHER.FORECAST forecast : getted) {
            weather.setDay(forecast.getDay().toString());
            weather.setMonth(forecast.getMonth().toString());
            weather.setYear(forecast.getYear().toString());
            weather.setTod(forecast.getTod());
            weather.setTemperature_max(forecast.getTEMPERATURE().getMax());

            weathers.add(weather);

            Log.d(TAG, weather.getDay() + "." + weather.getYear());
        }

        return weathers;
    }*/
}
