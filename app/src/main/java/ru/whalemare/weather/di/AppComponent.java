package ru.whalemare.weather.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.whalemare.weather.fragments.CityFragment;
import ru.whalemare.weather.fragments.ForecastFragment;
import ru.whalemare.weather.models.ForecastRestApiModel;
import ru.whalemare.weather.tasks.CityLoader;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
@Singleton
@Component(modules = {NetworkModule.class, AppModule.class})
public interface AppComponent {

    void inject(ForecastFragment forecastFragment);

    void inject(ForecastRestApiModel forecastRestApiModel);

    void inject(CityLoader cityLoader);

    void inject(CityFragment cityFragment);

}
