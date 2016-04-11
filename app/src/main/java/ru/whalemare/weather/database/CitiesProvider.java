package ru.whalemare.weather.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */

public class CitiesProvider extends ContentProvider {

    private final String TAG = getClass().getSimpleName();

    static final String PROVIDER_NAME = "ru.whalemare.weather.database.CitiesProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/cte";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;
    static final int ALL_CITIES = 0;
    static final int SINGLE_CITY = 1;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "cte", ALL_CITIES);
        uriMatcher.addURI(PROVIDER_NAME, "cte/*", SINGLE_CITY);
    }

    static class CitiesMetaData implements BaseColumns {

        final static String TABLE_NAME = "cities";
        final static String KEY_ID = "_id";
        final static String KEY_GISMETEO_CODE = "gismeteo_code";
        final static String KEY_CITY_NAME = "city_name";
        final static String KEY_REGION_CODE = "region_code";
        final static String KEY_REGION_NAME = "region_name";

    }


    SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        DatabaseHandlerImpl databaseHandler = new DatabaseHandlerImpl(getContext());
        db = databaseHandler.getWritableDatabase();

        if (db != null) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(CitiesMetaData.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case ALL_CITIES:
                qb.setProjectionMap(values);
                break;
            case SINGLE_CITY:

                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = CitiesMetaData.KEY_CITY_NAME;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_CITIES:
                return "vnd.android.cursor.dir/cte";
            case SINGLE_CITY:
                return "vnd.android.cursor.item/cte"; //// TODO: 11.04.2016 mistake?
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
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
