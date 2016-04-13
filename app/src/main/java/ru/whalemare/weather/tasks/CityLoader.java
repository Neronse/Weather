package ru.whalemare.weather.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.util.Log;

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

    @Inject
    DatabaseHandler database;

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
        database.openReadOnlyDatabase();
        Cursor cursor;

        final String[] rows = {
                CitiesProvider.CitiesMetaData.KEY_ID,
                CitiesProvider.CitiesMetaData.KEY_CITY_NAME,
                CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE};

        if (query == null) {
            cursor = getContext().getContentResolver().query(CitiesProvider.CITIES_CONTENT_URI, rows, null, null, null);

            ContentValues values = new ContentValues();
            values.put(CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE, "30823");
            values.put(CitiesProvider.StatsMetaData.KEY_TOD, "2");
            values.put(CitiesProvider.StatsMetaData.KEY_DATE, "15.04.2016");
            values.put(CitiesProvider.StatsMetaData.KEY_T_MAX, 1000);
            values.put(CitiesProvider.StatsMetaData.KEY_T_MIN, -300);

            String url = CitiesProvider.STATS_URL + "/1";
            Uri uri = Uri.parse(url);
            int updated = getContext().getContentResolver().update(CitiesProvider.STATS_CONTENT_URI, values, null, null);

            Cursor tempCursor = getContext().getContentResolver().query(CitiesProvider.STATS_CONTENT_URI, null, null, null, null);
            if (tempCursor != null) {
                tempCursor.moveToFirst();
                while (tempCursor.moveToNext()){
                    Log.d(TAG, "loadInBackground: temp max = " + tempCursor.getInt(tempCursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_T_MAX)));
                }
            }
            Log.d(TAG, "loadInBackground: updated is " + updated);
        } else {
            cursor = getContext().getContentResolver().query(CitiesProvider.CITIES_CONTENT_URI, rows, "city_name LIKE ?", new String[]{"%"+query+"%"}, null);
        }
        database.close();

        return cursor;
    }
}
