package ru.whalemare.weather.database;

import android.database.Cursor;

import java.util.List;

import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface DatabaseHandler {

    String DATABASES_FOLDER = "/data/data/ru.whalemare.weather/databases/";

    List<City> getAllData();

    void setAllData(List<City> cities);

    String getQueryCreateTable();

    void initializeDatabaseFromAPK();

    void openReadOnlyDatabase();

    void close();

    /**
     * @return <b>true</b> if the database has a items <br>
     *         else <b>false</b>.
     */
    boolean isHasTable();

    Cursor getCursorWithAllData();

    Cursor getCursorWithDataByQuery(String name);

}
