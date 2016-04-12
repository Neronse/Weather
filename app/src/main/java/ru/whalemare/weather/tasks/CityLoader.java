package ru.whalemare.weather.tasks;

import android.content.Context;
import android.database.Cursor;
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

//        String[] rows = {
//                CitiesProvider.CitiesMetaData.KEY_ID,
//                CitiesProvider.CitiesMetaData.KEY_CITY_NAME,
//                CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE};

        if (query == null) {
            cursor = getContext().getContentResolver().query(CitiesProvider.CONTENT_URI, null, null, null, null);
        } else {
            cursor = database.getCursorWithDataByQuery(query);
        }

        Log.d(TAG, "cursor = " + cursor.getCount());

//        cursor.moveToFirst();
//        while (cursor.moveToNext()) {
//            Log.d(TAG, "" + cursor.getString(0));
//            Log.d(TAG, "" + cursor.getString(1));
//            Log.d(TAG, "" + cursor.getString(2));
//        }


        return cursor;
    }
}
