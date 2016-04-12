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

    @Inject DatabaseHandler database;

    public CityLoader(Context context, Bundle args) {
        super(context);

        App.get(context).getComponent().inject(this);

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

        final String[] rows = {
                CitiesProvider.CitiesMetaData.KEY_ID,
                CitiesProvider.CitiesMetaData.KEY_CITY_NAME,
                CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE};

        if (query == null) {
            cursor = getContext().getContentResolver().query(CitiesProvider.CONTENT_URI, rows, null, null, null);
        } else {
            cursor = getContext().getContentResolver().query(CitiesProvider.CONTENT_URI, rows, "city_name LIKE \'%" + query + "%\'", null, null); // FIXME: 12.04.2016 how to write the same query, but in the argument
        }

        return cursor;
    }
}
