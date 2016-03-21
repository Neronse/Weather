package ru.whalemare.weather;

import java.util.List;

import ru.whalemare.weather.objects.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface CitiesCallback {
    void onCitiesRetrieved(List<City> cities);
}
