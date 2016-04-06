package ru.whalemare.weather.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.whalemare.weather.di.AppComponent;
import ru.whalemare.weather.di.AppModule;
import ru.whalemare.weather.di.DaggerAppComponent;
import ru.whalemare.weather.di.NetworkModule;
import ru.whalemare.weather.interfaces.DatabaseHandler;
import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityLoader extends AsyncTaskLoader<List<City>> {

    private String TAG = getClass().getSimpleName();

    @Inject DatabaseHandler database;

    public CityLoader(Context context) {
        super(context);
        AppComponent component = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .networkModule(new NetworkModule(context))
                .build();
        component.inject(this);
    }

    @Override
    public List<City> loadInBackground() {
        database.initializeDatabaseFromAPK();

        database.openDatabase();

        List<City> cities = new ArrayList<>(database.getAllData());

        database.close();
        return cities;
//        return null;
    }
}
