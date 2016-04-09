package ru.whalemare.weather.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */

public class CitiesProvider extends ContentProvider {

    private final String TAG = getClass().getSimpleName();

    static class CitiesMetaData implements BaseColumns {

        final static String TABLE_NAME = "cities";
        final static String KEY_ID = "_id";
        final static String KEY_GISMETEO_CODE = "gismeteo_code";
        final static String KEY_CITY_NAME = "city_name";
        final static String KEY_REGION_CODE = "region_code";
        final static String KEY_REGION_NAME = "region_name";

    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
