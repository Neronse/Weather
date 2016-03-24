package ru.whalemare.weather.interfaces;

import java.util.List;

import ru.whalemare.weather.objects.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface DatabaseHandler {

    List<City> getAllData();

    void setAllData(List<City> cities);

    void setVersion(int version);

    int getCountCities();

    int getVersion();

    City getCity(int position);

    String getQueryCreateTable();

    /**
     * @return <b>true</b> if the database has a items <br>
     *         else <b>false</b>.
     */
    boolean checkTable();

}
