package ru.whalemare.weather.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.whalemare.weather.Fragments.ForecastFragment;
import ru.whalemare.weather.Fragments.FullForecastFragment;
import ru.whalemare.weather.R;
import ru.whalemare.weather.objects.Weather;

public class ForecastActivity extends AppCompatActivity implements ForecastFragment.OnChooseForecastListener {

    public static final String KEY_GISMETEO = "KEY_GISMETEO";
    private static final String TAG = "WHALETAG";
    public static String gismeteo_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gismeteo_code = getIntent().getStringExtra(KEY_GISMETEO);

        getSupportFragmentManager().beginTransaction().replace(R.id.activityMain, new ForecastFragment().newInstance(gismeteo_code)).commit();
    }

    @Override
    public void sendForecast(Weather weather) {
        Fragment fullForecast = new FullForecastFragment().newInstance(weather);
        getSupportFragmentManager().beginTransaction().replace(R.id.activityMain, fullForecast).commit();
        Toast.makeText(ForecastActivity.this, "Оп", Toast.LENGTH_SHORT).show();
    }
}
