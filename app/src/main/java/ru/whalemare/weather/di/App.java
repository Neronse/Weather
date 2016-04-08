package ru.whalemare.weather.di;

import android.app.Application;
import android.content.Context;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .networkModule(new NetworkModule(getApplicationContext()))
                .build();
    }

    public static App get(Context context) {
        return ((App)context.getApplicationContext());
    }

    public AppComponent getComponent(){
        return component;
    }

}
