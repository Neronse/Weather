package ru.whalemare.weather.tasks;

import android.content.Context;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.whalemare.weather.R;
import ru.whalemare.weather.interfaces.ForecastClient;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class RetrofitTask implements RetrofitNet {

    Context context;
    private final String BASE_URL;

    String TAG = "WHALETAG";

    public RetrofitTask(Context context){
        this.context = context;
        this.BASE_URL = context.getString(R.string.gismeteo_base_url);
    }

    @Override
    public Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        Log.d(TAG, "getRetrofit: " + retrofit);
        return retrofit;
    }

    @Override
    public ForecastClient getClient() {
        ForecastClient client =  getRetrofit().create(ForecastClient.class);
        Log.d(TAG, "getClient: " + client);
        return client;
    }



}
