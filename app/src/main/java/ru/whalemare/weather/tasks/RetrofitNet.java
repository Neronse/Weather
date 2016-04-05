package ru.whalemare.weather.tasks;

import retrofit2.Retrofit;
import ru.whalemare.weather.interfaces.ForecastClient;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public interface RetrofitNet {

    Retrofit getRetrofit();

    ForecastClient getClient();

}
