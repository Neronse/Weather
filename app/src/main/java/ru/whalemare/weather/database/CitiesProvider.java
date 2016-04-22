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

    public static final String STATS_URL = "content://" + PROVIDER_NAME + "/" + StatsMetaData.TABLE_NAME;
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
    static final int STATS_ALL_DATA = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, CitiesMetaData.TABLE_NAME, ALL_CITIES);
        uriMatcher.addURI(PROVIDER_NAME, StatsMetaData.TABLE_NAME, STATS_ALL_DATA);
    }

    public static class CitiesMetaData implements BaseColumns {
        public final static String KEY_ID = "_id";

        public final static String TABLE_NAME = "cities"; // string in db
        public final static String KEY_GISMETEO_CODE = "gismeteo_code"; // string in db
        public final static String KEY_CITY_NAME = "city_name"; // string in db
        public final static String KEY_REGION_CODE = "region_code"; // string in db
        public final static String KEY_REGION_NAME = "region_name"; // string in db
    }

    public static class StatsMetaData implements BaseColumns {
        public final static String KEY_ID = "_id";

        public final static String TABLE_NAME = "stats"; // string in db
        public final static String KEY_GISMETEO_CODE = "gismeteo_code"; // string in db
        public final static String KEY_TOD = "tod"; // string in db
        public final static String KEY_DATE = "date"; // string in db
        public final static String KEY_T_MAX = "t_max"; // integer in db
        public final static String KEY_T_MIN = "t_min"; // integer in db
    }

    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DatabaseHandlerImpl databaseHandler = new DatabaseHandlerImpl(getContext());
        db = databaseHandler.getWritableDatabase();

        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case STATS_ALL_DATA:
                qb.setTables(StatsMetaData.TABLE_NAME);
                qb.setProjectionMap(statsValues);
                if (sortOrder == null) {
                    sortOrder = StatsMetaData.KEY_DATE;
                }
                break;
            case ALL_CITIES:
                qb.setTables(CitiesMetaData.TABLE_NAME);
                qb.setProjectionMap(citiesValues);
                if (sortOrder == null) {
                    sortOrder = CitiesMetaData.KEY_CITY_NAME;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        if (getContext() != null)
            c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_CITIES:
                return "vnd.android.cursor.dir/vnd." + PROVIDER_NAME + CitiesMetaData.TABLE_NAME;
            case STATS_ALL_DATA:
                return "vnd.android.cursor.item/vnd." + PROVIDER_NAME + StatsMetaData.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    /**
     * Using <b>only</b> for inserting statistics
     */
    public Uri insert(Uri uri, ContentValues values) { //todo uriMatcher add
        Log.d(TAG, "insert");
        long rowID = db.insert(StatsMetaData.TABLE_NAME, null, values);
        if (rowID > 0) {
            Uri returnUri = ContentUris.withAppendedId(STATS_CONTENT_URI, rowID);
            if (getContext() != null)
                getContext().getContentResolver().notifyChange(returnUri, null);
            return returnUri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case ALL_CITIES:
                count = db.delete(CitiesMetaData.TABLE_NAME, selection, selectionArgs);
                break;
            case STATS_ALL_DATA:
                count = db.delete(StatsMetaData.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (getContext() != null)
            getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case ALL_CITIES:
                count = db.update(CitiesMetaData.TABLE_NAME, values, selection, selectionArgs);
                break;
            case STATS_ALL_DATA:
                count = db.update(StatsMetaData.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (getContext() != null)
            getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) { //todo do nothing?
        return super.bulkInsert(uri, values);
    }
}
