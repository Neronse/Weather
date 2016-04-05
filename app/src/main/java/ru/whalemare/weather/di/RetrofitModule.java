package ru.whalemare.weather.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.whalemare.weather.interfaces.ForecastClient;
import ru.whalemare.weather.tasks.RetrofitTask;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
@Module
public class RetrofitModule {

    private Context context;

    public RetrofitModule(Context context){
        this.context = context;
    }

    @Singleton
    @Provides
    ForecastClient provideForecastClient(){
        return new RetrofitTask(context).getClient();
    }


}
