package ru.whalemare.weather;

import java.util.List;

import ru.whalemare.weather.objects.Weather;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface ForecastsCallback {
    void onForecastsRecieved(List<Weather> weathers);
}
