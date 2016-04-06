package ru.whalemare.weather.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.DatabaseHandlerImpl;
import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityLoader extends AsyncTaskLoader<List<City>> {

    private String TAG = getClass().getSimpleName();

    public CityLoader(Context context) {
        super(context);
    }

    @Override
    public List<City> loadInBackground() {
        DatabaseHandlerImpl database = new DatabaseHandlerImpl(getContext());
        database.initializeDatabaseFromAPK();

        try {
            database.openDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<City> cities = new ArrayList<>(database.getAllData());

        Log.d(TAG, "loadInBackground: " + cities.size());

        database.close();
        return cities;
    }
}
