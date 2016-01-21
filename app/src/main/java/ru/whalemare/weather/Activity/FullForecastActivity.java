package ru.whalemare.weather.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ru.whalemare.weather.R;

public class FullForecastActivity extends AppCompatActivity {

    TextView nowTemperature;
    TextView nowDate;
    TextView todWeekday;
    TextView cloudiness;
    TextView pressure;
    TextView wind;
    TextView relwet;
    TextView heat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_forecast);

        String textNowTemperature = getIntent().getStringExtra("t"); //
        String textData = "на " + getIntent().getStringExtra("date"); // на 21.09.2016
        String textTod = getIntent().getStringExtra("tod"); // Утро, Четверг
        String textCloudiness = getIntent().getStringExtra("cloud"); // Ясно: без осадков
        String textPressure = "Атмосферное давление: " + getIntent().getStringExtra("pressure"); // 776-788 мм.рт.ст.
        String textWind = "Ветер: " + getIntent().getStringExtra("wind"); // 2-4 м/c
        String textRelwet = "Относительная влажность воздуха: " + getIntent().getStringExtra("relwet"); // 77-78%
        String textHeat = "По ощущениям: " + getIntent().getStringExtra("heat");

        nowTemperature = (TextView) findViewById(R.id.now_temperature_full);
        nowDate = (TextView) findViewById(R.id.now_data_full);
        todWeekday = (TextView) findViewById(R.id.tod_weekday);
        cloudiness = (TextView) findViewById(R.id.cloudiness);
        pressure = (TextView) findViewById(R.id.pressure);
        wind = (TextView) findViewById(R.id.wind);
        relwet = (TextView) findViewById(R.id.relwet);
        heat = (TextView) findViewById(R.id.heat);

        nowTemperature.setText(textNowTemperature);
        nowDate.setText(textData);
        todWeekday.setText(textTod);
        cloudiness.setText(textCloudiness);
        pressure.setText(textPressure);
        wind.setText(textWind);
        relwet.setText(textRelwet);
        heat.setText(textHeat);
    }
}
