package ru.whalemare.weather.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.whalemare.weather.fragments.ForecastFragment;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
@Singleton
@Component(modules = {RetrofitModule.class})
public interface AppComponent {

    void inject(ForecastFragment forecastFragment);

}
