package ru.whalemare.weather.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */

public class CitiesProvider extends ContentProvider {

    private final String TAG = getClass().getSimpleName();

    static final String PROVIDER_NAME = "ru.whalemare.weather.database.CitiesProvider";
    static final String CITIES_URL = "content://" + PROVIDER_NAME + "/" + CitiesMetaData.TABLE_NAME;
    public static final Uri CITIES_CONTENT_URI = Uri.parse(CITIES_URL);

    static final String STATS_URL = "content://" + PROVIDER_NAME + "/" + StatsMetaData.TABLE_NAME;
    public static final Uri STATS_CONTENT_URI = Uri.parse(STATS_URL);


    private static HashMap<String, String> citiesValues;
    static {
        citiesValues = new HashMap<>();
        citiesValues.put(CitiesMetaData.KEY_ID, CitiesMetaData.KEY_ID);
        citiesValues.put(CitiesMetaData.KEY_CITY_NAME, CitiesMetaData.KEY_CITY_NAME);
        citiesValues.put(CitiesMetaData.KEY_GISMETEO_CODE, CitiesMetaData.KEY_GISMETEO_CODE);
        citiesValues.put(CitiesMetaData.KEY_REGION_CODE, CitiesMetaData.KEY_REGION_CODE);
        citiesValues.put(CitiesMetaData.KEY_REGION_NAME, CitiesMetaData.KEY_REGION_NAME);
    }

    private static HashMap<String, String> statsValues;
    static {
        statsValues = new HashMap<>();
        statsValues.put(StatsMetaData.KEY_ID, StatsMetaData.KEY_ID);
        statsValues.put(StatsMetaData.KEY_GISMETEO_CODE, StatsMetaData.KEY_GISMETEO_CODE);
        statsValues.put(StatsMetaData.KEY_TOD, StatsMetaData.KEY_TOD);
        statsValues.put(StatsMetaData.KEY_DATE, StatsMetaData.KEY_DATE);
        statsValues.put(StatsMetaData.KEY_T_MAX, StatsMetaData.KEY_T_MAX);
        statsValues.put(StatsMetaData.KEY_T_MIN, StatsMetaData.KEY_T_MIN);
    }

    static final UriMatcher uriMatcher;
    static final int ALL_CITIES = 0;
    static final int SINGLE_CITY = 1;
    static final int STATS_ALL_DATA = 2;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, CitiesMetaData.TABLE_NAME, ALL_CITIES);
        uriMatcher.addURI(PROVIDER_NAME, CitiesMetaData.TABLE_NAME, SINGLE_CITY);
        uriMatcher.addURI(PROVIDER_NAME, StatsMetaData.TABLE_NAME, STATS_ALL_DATA);
    }

    public static class CitiesMetaData implements BaseColumns {

        public final static String TABLE_NAME = "cities";
        public final static String KEY_ID = "_id";
        public final static String KEY_GISMETEO_CODE = "gismeteo_code";
        public final static String KEY_CITY_NAME = "city_name";
        public final static String KEY_REGION_CODE = "region_code";
        public final static String KEY_REGION_NAME = "region_name";
    }

    public static class StatsMetaData implements BaseColumns{
        public final static String TABLE_NAME = "stats";
        public final static String KEY_ID = "_id";
        public final static String KEY_GISMETEO_CODE = "gismeteo_code";
        public final static String KEY_TOD = "tod";
        public final static String KEY_DATE = "date";
        public final static String KEY_T_MAX = "t_max"; // integer in db
        public final static String KEY_T_MIN = "t_min"; // integer in db
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
                qb.setProjectionMap(citiesValues);
                break;
            case SINGLE_CITY:

                break;
            case STATS_ALL_DATA:
                Log.d(TAG, "query: Была запрошена статистика по uri = " + uri);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder.equals("")) {
            sortOrder = CitiesMetaData.KEY_CITY_NAME;
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_CITIES:
                return "vnd.android.cursor.dir/vnd." + PROVIDER_NAME + CitiesMetaData.TABLE_NAME;
            case SINGLE_CITY:
                return "vnd.android.cursor.item/vnd." + PROVIDER_NAME + CitiesMetaData.TABLE_NAME;
            case STATS_ALL_DATA:
                return "vnd.android.cursor.item/vnd." + PROVIDER_NAME + StatsMetaData.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert");
        long rowID = db.insert(StatsMetaData.TABLE_NAME, null, values);
        if (rowID > 0) {
            Uri returnUri = ContentUris.withAppendedId(CITIES_CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(returnUri, null);
            return returnUri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
/*        int count = 0;
        switch (uriMatcher.match(uri)) {
            case ALL_CITIES:
                count = db.delete(CitiesMetaData.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;*/
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//            int count = 0;
//            switch (uriMatcher.match(uri)) {
//                case ALL_CITIES:
//                    count = db.update(CitiesMetaData.TABLE_NAME, citiesValues, selection, selectionArgs);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unknown URI " + uri);
//            }
//            getContext().getContentResolver().notifyChange(uri, null);
//            return count;
        return 0;
    }
}
