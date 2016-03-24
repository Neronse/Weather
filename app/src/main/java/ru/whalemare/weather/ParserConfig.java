package ru.whalemare.weather;

import android.content.Context;

import ru.whalemare.weather.fragments.ForecastFragment;
import ru.whalemare.weather.interfaces.ForecastsCallback;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class ParserConfig {

    private final ForecastsCallback callback;

    public final String FORECAST_CODE_XML;

    public final Context context;

    // теги
    public final String FORECAST;
    public final String PHENOMENA;
    public final String PRESSURE;
    public final String TEMPERATURE;
    public final String WIND;
    public final String RELWET;
    public final String HEAT;

    public final String day;
    public final String month;
    public final String year;
    public final String tod;
    public final String weekday;

    public final String cloudiness;
    public final String precipitation;
    public final String rpower;
    public final String spower;

    public final String max;
    public final String min;

    public ParserConfig(Context context, String forecastCode,
                        ForecastsCallback callback,
                        ForecastFragment.OnChooseForecastListener listener) {

        this.FORECAST_CODE_XML = forecastCode + ".xml";
        this.callback = callback;
        this.context = context;

        FORECAST = context.getResources().getString(R.string.FORECAST);
        PHENOMENA = context.getResources().getString(R.string.PHENOMENA);
        PRESSURE = context.getResources().getString(R.string.PRESSURE);
        TEMPERATURE = context.getResources().getString(R.string.TEMPERATURE);
        WIND = context.getResources().getString(R.string.WIND);
        RELWET = context.getResources().getString(R.string.RELWET);
        HEAT = context.getResources().getString(R.string.HEAT);
        day = context.getResources().getString(R.string.day);
        month = context.getResources().getString(R.string.month);
        year = context.getResources().getString(R.string.year);
        tod = context.getResources().getString(R.string.tod);
        weekday = context.getResources().getString(R.string.weekday);
        cloudiness = context.getResources().getString(R.string.cloudiness);
        precipitation = context.getResources().getString(R.string.precipitation);
        rpower = context.getResources().getString(R.string.rpower);
        spower = context.getResources().getString(R.string.spower);
        max = context.getResources().getString(R.string.max);
        min = context.getResources().getString(R.string.min);
    }

    public Context getContext() {
        return context;
    }

    public ForecastsCallback getCallback() {
        return callback;
    }
}