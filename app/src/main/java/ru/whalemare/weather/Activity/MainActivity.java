package ru.whalemare.weather.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.whalemare.weather.Fragments.FullForecastFragment;
import ru.whalemare.weather.Fragments.MainFragment;
import ru.whalemare.weather.R;
import ru.whalemare.weather.Weather;

public class MainActivity extends AppCompatActivity implements MainFragment.OnChooseForecastListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.activityMain, new MainFragment()).commit();
    }

    @Override
    public void sendForecast(Weather weather) {
        Fragment fullForecast = new FullForecastFragment().newInstance(weather); // ??
        getSupportFragmentManager().beginTransaction().replace(R.id.activityMain, fullForecast).commit();
        Toast.makeText(MainActivity.this, "Оп", Toast.LENGTH_SHORT).show();
    }
}
