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
        toolbar.setLogo(R.mipmap.ic_launcher);

//        shared = getSharedPreferences(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, MODE_PRIVATE);
//        final String gismeteoCode = shared.getString(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, null);
//        final String cityName = shared.getString(CitiesProvider.CitiesMetaData.KEY_CITY_NAME, null);
//        Log.d(TAG, "onCreate: gismeteoCode = " + gismeteoCode + "; cityName = " + cityName);
//        if (gismeteoCode != null && cityName != null)
//        {
//            Intent intent = new Intent(this, ForecastActivity.class);
//            intent.putExtra(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, gismeteoCode);
//            intent.putExtra(CitiesProvider.CitiesMetaData.KEY_CITY_NAME, cityName);
//            startActivity(intent);
        if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_city, new CityFragment().newInstance())
                        .commit();
        }
    }
}
