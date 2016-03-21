package ru.whalemare.weather.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.whalemare.weather.Fragments.CityFragment;
import ru.whalemare.weather.R;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_city, new CityFragment().newInstance())
                    .commit();
    }
}
