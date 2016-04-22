package ru.whalemare.weather.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.RemoteViews;

import java.util.Random;

import ru.whalemare.weather.R;
import ru.whalemare.weather.database.CitiesProvider;

/**
 * Implementation of App Widget functionality.
 */


public class ForecastWidget extends AppWidgetProvider {

    private static SharedPreferences shared;
    static String stringTod,
            humanTod,
            cityName,
            gismeteoCode;

    static int temperature;

    final static String tods[] = {
            "ночью",
            "утром",
            "днем",
            "вечером"
    };

    static void updateAppWidget(Context context) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        Intent updateIntent = new Intent(context, ForecastWidget.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, updateIntent, 0);

        shared = context.getSharedPreferences(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, Context.MODE_PRIVATE);

        gismeteoCode = shared.getString(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, null);
        cityName = shared.getString(CitiesProvider.CitiesMetaData.KEY_CITY_NAME, null);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.forecast_widget);
        if (gismeteoCode != null) {
            Cursor cursor = context.getContentResolver().query(CitiesProvider.STATS_CONTENT_URI, null, CitiesProvider.StatsMetaData.KEY_GISMETEO_CODE + " = ?", new String[]{gismeteoCode}, CitiesProvider.StatsMetaData.KEY_DATE);

            if (cursor != null) {
                Random random = new Random();
                int r = random.nextInt(3)+1;
                cursor.moveToPosition(cursor.getCount() - r);

                temperature = cursor.getInt(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_T_MAX));
                stringTod = cursor.getString(cursor.getColumnIndex(CitiesProvider.StatsMetaData.KEY_TOD));
                humanTod = tods[Integer.parseInt(stringTod)];

                cursor.close();
            }

        } else {
            cityName = "Город не выбран";
            views.setViewVisibility(R.id.textview_widget_temperature, 0);
        }
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

        views.setTextViewText(R.id.textview_widget_city_name, cityName);
        views.setTextViewText(R.id.textview_widget_temperature, Integer.toString(temperature) + context.getString(R.string.celcium));
        views.setTextViewText(R.id.textview_widget_tod, humanTod);

        ComponentName thisWidget = new ComponentName(context, ForecastWidget.class);

        appWidgetManager.updateAppWidget(thisWidget, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            updateAppWidget(context);
        }
    }

}

