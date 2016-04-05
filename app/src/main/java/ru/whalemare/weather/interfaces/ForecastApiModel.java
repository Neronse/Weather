package ru.whalemare.weather.interfaces;

import ru.whalemare.weather.models.forecast.MMWEATHER;
import rx.Observable;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface ForecastApiModel {

    Observable<MMWEATHER> getData(String code);

}
