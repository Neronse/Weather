package ru.whalemare.weather.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;

import javax.inject.Inject;

import ru.whalemare.weather.database.DatabaseHandler;
import ru.whalemare.weather.di.AppComponent;
import ru.whalemare.weather.di.AppModule;
import ru.whalemare.weather.di.DaggerAppComponent;
import ru.whalemare.weather.di.NetworkModule;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityLoader extends CursorLoader {

    private String TAG = getClass().getSimpleName();
    private String query;

    @Inject DatabaseHandler database;

    public CityLoader(Context context, Bundle args) {
        super(context);
        AppComponent component = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .networkModule(new NetworkModule(context))
                .build();
        component.inject(this);
        if (args != null)
            this.query = args.getString("query", null);
        else
            this.query = null;
    }

    @Override
    public Cursor loadInBackground() {
        database.initializeDatabaseFromAPK();
        database.openDatabase();
        Cursor cursor;

        if (query == null) {
            cursor = database.getCursorWithAllData();
        } else {
            cursor = database.getCursorWithDataByQuery(query);
        }

        return cursor;
    }
}
