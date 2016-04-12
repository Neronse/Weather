package ru.whalemare.weather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.R;
import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class DatabaseHandlerImpl extends SQLiteOpenHelper implements DatabaseHandler {

    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase database;
    private final Context context;

    private final String DATABASE_NAME;
    private final int DATABASE_VERSION;

    public DatabaseHandlerImpl(Context context) {
        super(context, context.getString(R.string.database_name), null, context.getResources().getInteger(R.integer.database_version));
        this.DATABASE_NAME = context.getString(R.string.database_name);
        this.DATABASE_VERSION = context.getResources().getInteger(R.integer.database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
//        database.execSQL(getQueryCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//        database.execSQL("DROP TABLE IF EXISTS " + CitiesProvider.CitiesMetaData.TABLE_NAME);
//        onCreate(database);
//        if (newVersion == 3)
//            database.execSQL("CREATE TABLE IF NOT EXISTS stats (" +
//                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
//                "gismeteo_code TEXT NOT NULL, " +
//                "tod TEXT NOT NULL, " +
//                "date TEXT NOT NULL, " +
//                "t_max INTEGER NOT NULL, " +
//                "t_min INTEGER NOT NULL" +
//                ")");
    }

    public void initializeDatabaseFromAPK() {
        if (!isInitialized()) {
            Log.d(TAG, "initializeDatabaseFromAPK: создадим бд из апк");
            // creating empty database for rewriting
            getReadableDatabase();

            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                throw new RuntimeException("Error copying database", e);
            }
        } else {
            Log.d(TAG, "initializeDatabaseFromAPK: база данных уже есть в телефоне");
        }
    }

    private boolean isInitialized() {

        SQLiteDatabase checkDatabase = null;

        try {
            checkDatabase = SQLiteDatabase.openDatabase(DATABASES_FOLDER + DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
            Log.d(TAG, e.getMessage());
        } finally {
            if (checkDatabase != null)
                checkDatabase.close();
        }

        if (checkDatabase != null)
            return true;
        else
            return false;
    }

    private void copyDatabaseFromAssets() throws IOException {

        OutputStream output = null;
        InputStream input = null;

        try {
            //open database how input stream
            input = new BufferedInputStream(context.getAssets().open(DATABASE_NAME));

            // open empty database how output stream
            output = new BufferedOutputStream(new FileOutputStream(DATABASES_FOLDER + DATABASE_NAME));

            // перемещаем байты из входящего файла в исходящий
            byte[] buffer = new byte[1024];
            int lenght;
            while ((lenght = input.read(buffer)) > 0)
                output.write(buffer, 0, lenght);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert output != null;
            output.flush();
            output.close();
            input.close();
        }
    }

    public void openReadOnlyDatabase() {
        String path = DATABASES_FOLDER + DATABASE_NAME;
        this.database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (this.database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public List<City> getAllData() {
        final String query = "SELECT * FROM " + CitiesProvider.CitiesMetaData.TABLE_NAME +" ORDER BY city_name ASC"; // FIXME: 25.03.2016 add already sorted the database
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor;

        try {
            cursor = database.rawQuery(query, null);
        } catch (IOError e) {
            e.printStackTrace();
            Log.e(TAG, "getAllData: не удалось получить курсор");
            return null;
        }

        List<City> data = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int gismeteoCodeIndex = cursor.getColumnIndex(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE);
            int cityNameIndex = cursor.getColumnIndex(CitiesProvider.CitiesMetaData.KEY_CITY_NAME);
            int regionCodeIndex = cursor.getColumnIndex(CitiesProvider.CitiesMetaData.KEY_REGION_CODE);
            int regionNameIndex = cursor.getColumnIndex(CitiesProvider.CitiesMetaData.KEY_REGION_NAME);

            do {
                data.add(new City(
                        cursor.getString(gismeteoCodeIndex),
                        cursor.getString(cityNameIndex),
                        cursor.getString(regionCodeIndex),
                        cursor.getString(regionNameIndex)));

            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getAllData: курсор не нашел данных в бд");
        }
        cursor.close();
        return data;
    }

    @Override
    public void setAllData(List<City> cities) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        for (City city : cities) {
            values.put(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, city.getGismeteoCode());
            values.put(CitiesProvider.CitiesMetaData.KEY_CITY_NAME, city.getCityName());
            values.put(CitiesProvider.CitiesMetaData.KEY_REGION_CODE, city.getRegionCode());
            values.put(CitiesProvider.CitiesMetaData.KEY_REGION_NAME, city.getRegionName());
            database.insert(CitiesProvider.CitiesMetaData.TABLE_NAME, null, values);
        }
    }

    @Override
    public boolean isHasTable() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + CitiesProvider.CitiesMetaData.TABLE_NAME, null);

        boolean answer = !cursor.getString(1).isEmpty() || !(cursor.getString(1) == null);
        cursor.close();

        Log.d(TAG, "Таблица " + CitiesProvider.CitiesMetaData.TABLE_NAME + " есть ?= " + answer);
        return answer;
    }

    public String getQueryCreateTable() {
        return "CREATE TABLE " + CitiesProvider.CitiesMetaData.TABLE_NAME + "( "
                + CitiesProvider.CitiesMetaData.KEY_ID + " integer primary key autoincrement, "
                + CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE + " text not null, "
                + CitiesProvider.CitiesMetaData.KEY_CITY_NAME + " text not null, "
                + CitiesProvider.CitiesMetaData.KEY_REGION_CODE + " text not null, "
                + CitiesProvider.CitiesMetaData.KEY_REGION_NAME + " text not null);";
    }

    @Override
    public Cursor getCursorWithAllData() {
        final String query = "SELECT * FROM " + CitiesProvider.CitiesMetaData.TABLE_NAME +" ORDER BY city_name ASC";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor;

        try {
            cursor = database.rawQuery(query, null);
        } catch (IOError e) {
            e.printStackTrace();
            Log.e(TAG, "getAllData: не удалось получить курсор");
            return null;
        }

        return cursor;
    }

    @Override
    public Cursor getCursorWithDataByQuery(String name) {
        final String query = "SELECT * FROM " + CitiesProvider.CitiesMetaData.TABLE_NAME +" WHERE city_name " +
                "LIKE \'%" + name + "%\' ORDER BY city_name ASC";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor;

        try {
            cursor = database.rawQuery(query, null);
        } catch (IOError e) {
            e.printStackTrace();
            Log.e(TAG, "getAllData: не удалось получить курсор");
            return null;
        }

        return cursor;
    }
}
