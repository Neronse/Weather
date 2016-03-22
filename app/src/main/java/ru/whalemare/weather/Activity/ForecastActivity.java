package ru.whalemare.weather.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ru.whalemare.weather.Fragments.ForecastFragment;
import ru.whalemare.weather.Fragments.FullForecastFragment;
import ru.whalemare.weather.R;
import ru.whalemare.weather.objects.Weather;

public class ForecastActivity extends AppCompatActivity implements ForecastFragment.OnChooseForecastListener {

    private static final String TAG = "WHALETAG";
    public static String gismeteo_code, cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String KEY_GISMETEO = getApplicationContext().getResources().getString(R.string.KEY_GISMETEO);
        final String KEY_CITYNAME = getApplicationContext().getResources().getString(R.string.KEY_CITYNAME);
        gismeteo_code = getIntent().getStringExtra(KEY_GISMETEO);
        cityName = getIntent().getStringExtra(KEY_CITYNAME);

        Log.d(TAG, "onCreate: cityName " + cityName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(cityName);
        toolbar.setLogo(R.mipmap.ic_launcher);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ForecastFragment().newInstance(gismeteo_code))
                .commit();
    }

    @Override
    public void sendForecast(Weather weather) {
        Fragment fullForecast = new FullForecastFragment().newInstance(weather);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fullForecast)
                .addToBackStack(null)
                .commit();
    }
}
