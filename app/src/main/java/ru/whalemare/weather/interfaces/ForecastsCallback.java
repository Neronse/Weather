package ru.whalemare.weather.interfaces;

import java.util.List;

import ru.whalemare.weather.objects.Weather;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface ForecastsCallback {
    void onForecastsRetrieved(List<Weather> weathers);
}
