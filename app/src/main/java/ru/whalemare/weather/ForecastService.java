package ru.whalemare.weather;

import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.whalemare.weather.models.MMWEATHER;
import rx.Observable;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface ForecastService {

    @GET("{code}.xml")
    Observable<MMWEATHER> getData(@Path("code") String code);

}
