package ru.whalemare.weather.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.whalemare.weather.R;
import ru.whalemare.weather.fragments.CityFragment;

public class CityActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private SharedPreferences shared;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null)
//            getSupportActionBar().setLogo(R.mipmap.ic_launcher);

        if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_city, new CityFragment().newInstance())
                        .commit();
        }
    }
}
