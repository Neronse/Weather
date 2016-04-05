package ru.whalemare.weather.models;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import ru.whalemare.weather.di.AppComponent;
import ru.whalemare.weather.di.DaggerAppComponent;
import ru.whalemare.weather.interfaces.ForecastApiModel;
import ru.whalemare.weather.di.NetworkModule;
import ru.whalemare.weather.interfaces.ForecastApiClient;
import ru.whalemare.weather.models.forecast.MMWEATHER;
import rx.Observable;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class ForecastRestApiModel implements ForecastApiModel {

    @Inject ForecastApiClient client;

    String TAG = "WHALETAG";

    public ForecastRestApiModel(Context context) {
        AppComponent component = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(context))
                .build();

        component.inject(this);
    }

    @Override
    public Observable<MMWEATHER> getData(String code) {
        Log.d(TAG, "getData in ForecastApiModel; client = " + client);
        return client.getData(code);
    }
}
