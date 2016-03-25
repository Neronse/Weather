package ru.whalemare.weather.interfaces;

import java.util.List;

import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface CitiesCallback {
    void onCitiesRetrieved(List<City> cities);
}
