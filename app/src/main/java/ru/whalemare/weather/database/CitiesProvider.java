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
    static final String URL = "content://" + PROVIDER_NAME + "/" + CitiesMetaData.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);


    private static HashMap<String, String> values;
    static {
        values = new HashMap<>();
        values.put(CitiesMetaData.KEY_ID, CitiesMetaData.KEY_ID);
        values.put(CitiesMetaData.KEY_CITY_NAME, CitiesMetaData.KEY_CITY_NAME);
        values.put(CitiesMetaData.KEY_GISMETEO_CODE, CitiesMetaData.KEY_GISMETEO_CODE);
        values.put(CitiesMetaData.KEY_REGION_CODE, CitiesMetaData.KEY_REGION_CODE);
        values.put(CitiesMetaData.KEY_REGION_NAME, CitiesMetaData.KEY_REGION_NAME);
    }

    static final UriMatcher uriMatcher;
    static final int ALL_CITIES = 0;
    static final int SINGLE_CITY = 1;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, CitiesMetaData.TABLE_NAME, ALL_CITIES);
        uriMatcher.addURI(PROVIDER_NAME, CitiesMetaData.TABLE_NAME, SINGLE_CITY);
    }

    public static class CitiesMetaData implements BaseColumns {

        public final static String TABLE_NAME = "cities";
        public final static String KEY_ID = "_id";
        public final static String KEY_GISMETEO_CODE = "gismeteo_code";
        public final static String KEY_CITY_NAME = "city_name";
        public final static String KEY_REGION_CODE = "region_code";
        public final static String KEY_REGION_NAME = "region_name";
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
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
/*        long rowID = db.insert(CitiesMetaData.TABLE_NAME, null, values);
        if (rowID > 0) {
            Uri tempUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(tempUri, null);
            return tempUri;
        }
        throw new SQLException("Failed to add a record into " + uri);*/
        return null;
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
//                    count = db.update(CitiesMetaData.TABLE_NAME, values, selection, selectionArgs);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unknown URI " + uri);
//            }
//            getContext().getContentResolver().notifyChange(uri, null);
//            return count;
        return 0;
    }
}
