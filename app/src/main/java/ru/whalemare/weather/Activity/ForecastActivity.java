package ru.whalemare.weather.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ru.whalemare.weather.R;
import ru.whalemare.weather.database.CitiesProvider;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forecast, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.action_chart) {
            Log.d(TAG, "onOptionsItemSelected: статистика");
            Toast.makeText(this, "Chart", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ChartActivity.class);
            if (gismeteo_code != null) {
                Log.d(TAG, "onOptionsItemSelected: send gismeteoCode " + gismeteo_code);
                intent.putExtra(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, gismeteo_code);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendForecast(FORECAST forecast) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, new FullForecastFragment().newInstance(forecast, gismeteo_code))
                .addToBackStack(null)
                .commit();
    }
}
