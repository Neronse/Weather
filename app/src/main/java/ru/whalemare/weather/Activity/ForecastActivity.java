package ru.whalemare.weather.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ru.whalemare.weather.R;
import ru.whalemare.weather.fragments.ForecastFragment;
import ru.whalemare.weather.fragments.FullForecastFragment;
import ru.whalemare.weather.models.forecast.FORECAST;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(cityName);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, new ForecastFragment().newInstance(gismeteo_code))
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendForecast(FORECAST forecast) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, new FullForecastFragment().newInstance(forecast))
                .addToBackStack(null)
                .commit();
    }
}
