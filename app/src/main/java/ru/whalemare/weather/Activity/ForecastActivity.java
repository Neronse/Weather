package ru.whalemare.weather.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.whalemare.weather.R;
import ru.whalemare.weather.database.CitiesProvider;
import ru.whalemare.weather.fragments.ForecastFragment;
import ru.whalemare.weather.fragments.FullForecastFragment;
import ru.whalemare.weather.models.forecast.FORECAST;

public class ForecastActivity extends AppCompatActivity implements ForecastFragment.OnChooseForecastListener {

    private static final String TAG = "WHALETAG";
    public static String gismeteoCode, cityName;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared = getSharedPreferences(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, MODE_PRIVATE);

        gismeteoCode = getIntent().getStringExtra(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE);
        cityName = getIntent().getStringExtra(CitiesProvider.CitiesMetaData.KEY_CITY_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(cityName);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, new ForecastFragment().newInstance(gismeteoCode))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forecast, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            shared.edit().putString(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, null).commit();
            shared.edit().putString(CitiesProvider.CitiesMetaData.KEY_CITY_NAME, null).commit();
        }
        if (item.getItemId() == R.id.action_chart) {
            Log.d(TAG, "onOptionsItemSelected: статистика");
            Intent intent = new Intent(this, ChartActivity.class);
            if (gismeteoCode != null) {
                Log.d(TAG, "onOptionsItemSelected: send gismeteoCode " + gismeteoCode);
                intent.putExtra(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, gismeteoCode);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendForecast(FORECAST forecast) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, new FullForecastFragment().newInstance(forecast, gismeteoCode))
                .addToBackStack(null)
                .commit();
    }
}
