package ru.whalemare.weather.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.whalemare.weather.R;
import ru.whalemare.weather.interfaces.ForecastApiClient;
import ru.whalemare.weather.models.ForecastRestApiModel;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
@Module
public class NetworkModule {

    private final String BASE_URL;
    private final Context context;

    public NetworkModule(Context context) {
        this.context = context;
        this.BASE_URL = context.getString(R.string.gismeteo_base_url);
    }

    @Provides
    ForecastRestApiModel provideForecastRestApiModel(){
        return new ForecastRestApiModel(context);
    }

    @Provides
    ForecastApiClient provideForecastApiClient(){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        return retrofit.create(ForecastApiClient.class);
    }




}
