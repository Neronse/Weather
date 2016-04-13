package ru.whalemare.weather.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;

import javax.inject.Inject;

import ru.whalemare.weather.database.CitiesProvider;
import ru.whalemare.weather.database.DatabaseHandler;
import ru.whalemare.weather.di.App;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityLoader extends CursorLoader {

    private String TAG = getClass().getSimpleName();
    private String query;
    private int doing = 1; // need, then data is downloading by second call. Downloading only in "onResume" method

    @Inject
    DatabaseHandler database;

    public CityLoader(Context context, Bundle args) {
        super(context);

        App.get(context).getComponent().inject(this);

        if (args != null) {
            this.query = args.getString("query", null);
            this.doing = args.getInt("DO");
        }
        else
            this.query = null;
    }

    @Override
    public Cursor loadInBackground() {
        if (doing != -1) {
            Cursor cursor;

            final String[] rowsCities = {
                    CitiesProvider.CitiesMetaData.KEY_ID,
                    CitiesProvider.CitiesMetaData.KEY_CITY_NAME,
                    CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE};

            if (query == null) {
                cursor = getContext().getContentResolver().query(CitiesProvider.CITIES_CONTENT_URI, rowsCities, null, null, null);
            } else {
                cursor = getContext().getContentResolver().query(CitiesProvider.CITIES_CONTENT_URI, rowsCities, "city_name LIKE ?", new String[]{"%" + query + "%"}, null);
            }
            database.close();

            return cursor;
        } else {
            database.initializeDatabaseFromAPK();
            database.openReadOnlyDatabase();
            return null;
        }
    }
}
