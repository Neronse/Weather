package ru.whalemare.weather.interfaces;

import ru.whalemare.weather.objects.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface DatabaseHandler {

    void getData();

    void setData();

    void setVersion(int version);

    int getCountCities();

    int getVersion();

    City getCity(int position);

}
