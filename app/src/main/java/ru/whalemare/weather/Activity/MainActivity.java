package ru.whalemare.weather.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ru.whalemare.weather.R;
import ru.whalemare.weather.WeatherTask;

public class MainActivity extends AppCompatActivity {
    WeatherTask weatherTask;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    TextView pressRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_weathers);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        pressRefresh = (TextView) findViewById(R.id.press_refresh);

        // Проверка на подключение к интернету
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internetInfo = cm.getActiveNetworkInfo();
        if (internetInfo == null || !internetInfo.isConnectedOrConnecting())
            pressRefresh.setText("Нет подключения к интернету");

        //Закомменчено для соответствия ТЗ
        //weatherTask = new WeatherTask(getApplicationContext(), recyclerView);
        //weatherTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_refresh:
                pressRefresh.setVisibility(View.GONE); // уберем TextView с layout
                weatherTask = new WeatherTask(getApplicationContext(), recyclerView);
                weatherTask.execute();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
