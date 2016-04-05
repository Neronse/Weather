package ru.whalemare.weather.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.whalemare.weather.fragments.ForecastFragment;
import ru.whalemare.weather.models.ForecastRestApiModel;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {

    void inject(ForecastFragment forecastFragment);

    void inject(ForecastRestApiModel forecastRestApiModel);

}
