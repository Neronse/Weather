package ru.whalemare.weather.interfaces;

import java.util.List;

import ru.whalemare.weather.objects.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface IDatabaseHandler {

    void addCity(City city);

    void setCities(List<City> cities);

    void setVersion(int version);

    int getCountCities();

    int getVersion();

    City getCity(int position);

    List<City> getAllCities();
}
