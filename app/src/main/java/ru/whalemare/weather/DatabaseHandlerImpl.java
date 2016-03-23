package ru.whalemare.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ru.whalemare.weather.interfaces.DatabaseHandler;
import ru.whalemare.weather.objects.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class DatabaseHandlerImpl extends SQLiteOpenHelper implements DatabaseHandler {

    private final String TAG = getClass().getSimpleName();

    private final String DATABASE_NAME;
    private final int DATABASE_VERSION;

    private final String TABLE_NAME = "cities";
    private final String KEY_ID = "id";
    private final String KEY_GISMETEO_CODE = "gismeteo_code";
    private final String KEY_CITY_NAME = "city_name";
    private final String KEY_REGION_CODE = "region_code";
    private final String KEY_REGION_NAME = "region_name";

    public DatabaseHandlerImpl(Context context) {
        super(context, context.getString(R.string.database_name),
                null, context.getResources().getInteger(R.integer.database_version));
        this.DATABASE_NAME = context.getString(R.string.database_name);
        this.DATABASE_VERSION = context.getResources().getInteger(R.integer.database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(getQueryCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    @Override
    public void getData() {
        Log.d(TAG, "getData");
    }

    @Override
    public void setData() {
        Log.d(TAG, "setData");
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

    public String getQueryCreateTable() {
        return "CREATE TABLE " + TABLE_NAME + "( "
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_GISMETEO_CODE + " text not null, "
                + KEY_CITY_NAME + " text not null, "
                + KEY_REGION_CODE + " text not null, "
                + KEY_REGION_NAME + " text not null);";
    }
}
