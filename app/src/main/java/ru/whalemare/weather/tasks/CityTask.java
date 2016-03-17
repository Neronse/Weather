package ru.whalemare.weather.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.CitiesCallback;
import ru.whalemare.weather.objects.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityTask extends AsyncTask<Void, Void, List<City>> {

    private static final String TAG = "WHALETAG";
    private final CitiesCallback callback;
    private final List<City> cities = new ArrayList<>();

    public CityTask(CitiesCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<City> doInBackground(Void... voids) {
        for (int i = 0; i < 100; i++) {
            cities.add(new City("Test" + i));
        }
        return cities;
    }

    @Override
    protected void onPostExecute(List<City> cities) {
        super.onPostExecute(cities);
        callback.onCitiesRetrieved(cities);
    }
}
