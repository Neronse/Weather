package ru.whalemare.weather.tasks;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.whalemare.weather.R;
import ru.whalemare.weather.interfaces.ForecastClient;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class RetrofitTask {

    Context context;
    private final String BASE_URL;
    private final Retrofit retrofit;

    public RetrofitTask(Context context){
        this.context = context;
        this.BASE_URL = context.getString(R.string.gismeteo_base_url);

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public ForecastClient createClient() {
        return this.retrofit.create(ForecastClient.class);
    }

}
