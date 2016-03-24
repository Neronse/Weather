package ru.whalemare.weather;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.interfaces.DatabaseHandler;
import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class DatabaseHandlerImpl extends SQLiteOpenHelper implements DatabaseHandler {

    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase database;
    private final Context context;

    private final String DATABASES_FOLDER = "/data/data/ru.whalemare.weather/databases/";
    private final String DATABASE_NAME;
    private final int DATABASE_VERSION;

    private final String TABLE_NAME = "cities";
    private final String KEY_ID = "id";
    private final String KEY_GISMETEO_CODE = "gismeteo_code";
    private final String KEY_CITY_NAME = "city_name";
    private final String KEY_REGION_CODE = "region_code";
    private final String KEY_REGION_NAME = "region_name";

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
//        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(database);
    }

    public void initializeDatabaseFromAPK() {
        if (!isInitialized()){
            Log.d(TAG, "initializeDatabaseFromAPK: создадим бд из апк");
            // creating empty database for rewriting
            getReadableDatabase();

            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        } else {
            Log.d(TAG, "initializeDatabaseFromAPK: база данных уже есть в телефоне");
        }
    }

    private boolean isInitialized() {

        SQLiteDatabase checkDatabase = null;

        try {
            checkDatabase = SQLiteDatabase.openDatabase(DATABASES_FOLDER+DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (checkDatabase != null)
                checkDatabase.close();
        }

        return checkDatabase != null;
    }

    private void copyDatabaseFromAssets() throws IOException{

        OutputStream output;

        //open database how input stream
        InputStream input = new BufferedInputStream(context.getAssets().open(DATABASE_NAME));

        // open empty database how output stream
        output = new BufferedOutputStream(new FileOutputStream(DATABASES_FOLDER+DATABASE_NAME));

        // перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght = input.read(buffer)) > 0)
            output.write(buffer, 0, lenght);

        output.flush();
        output.close();
        input.close();
    }

    public void openDatabase() throws SQLException {
        String path = DATABASES_FOLDER + DATABASE_NAME;
        this.database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (this.database != null){
            database.close();
        }
        super.close();
    }

    @Override
    public List<City> getAllData() {
        final String query = "SELECT * FROM " + TABLE_NAME;
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
            int gismeteoCodeIndex = cursor.getColumnIndex(KEY_GISMETEO_CODE); // FIXME: 24.03.2016 really needed?
            int cityNameIndex = cursor.getColumnIndex(KEY_CITY_NAME);
            int regionCodeIndex = cursor.getColumnIndex(KEY_REGION_CODE);
            int regionNameIndex = cursor.getColumnIndex(KEY_REGION_NAME);

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
            values.put(KEY_GISMETEO_CODE, city.getGismeteoCode());
            values.put(KEY_CITY_NAME, city.getCityName());
            values.put(KEY_REGION_CODE, city.getRegionCode());
            values.put(KEY_REGION_NAME, city.getRegionName());
            database.insert(TABLE_NAME, null, values);
        }
    }

    @Override
    public void setVersion(int version) {

    }

    @Override
    public int getCountCities() {
        return 0;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public City getCity(int position) {
        return null;
    }

    @Override
    public boolean checkTable() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        boolean answer = !cursor.getString(1).isEmpty() || !(cursor.getString(1) == null) ;
        cursor.close();

        Log.d(TAG, "Таблица " + TABLE_NAME + " есть ?= " + answer);
        return answer;
    }

    public String getQueryCreateTable() {
        return "CREATE TABLE " + TABLE_NAME + "( "
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_GISMETEO_CODE + " text not null, "
                + KEY_CITY_NAME + " text not null, "
                + KEY_REGION_CODE + " text not null, "
                + KEY_REGION_NAME + " text not null);";
    }
}
