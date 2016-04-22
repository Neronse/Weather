package ru.whalemare.weather.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

import ru.whalemare.weather.R;
import ru.whalemare.weather.database.CitiesProvider;

/**
 * Implementation of App Widget functionality.
 */


public class ForecastWidget extends AppWidgetProvider {

    private final static String TAG = "ForecastWidget";
    private final String ACTION_WIDGET_UPDATE = "ActionWidgetUpdate";
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

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.d(TAG, "updateAppWidget");

        shared = context.getSharedPreferences(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, Context.MODE_PRIVATE);

        gismeteoCode = shared.getString(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, null);
        cityName = shared.getString(CitiesProvider.CitiesMetaData.KEY_CITY_NAME, null);

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
        }

        Log.d(TAG, "updateAppWidget: gismeteoCode = " + gismeteoCode);
        Log.d(TAG, "updateAppWidget: cityName = " + cityName);
        Log.d(TAG, "updateAppWidget: tempr = " + temperature);


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.forecast_widget);
        views.setTextViewText(R.id.textview_widget_city_name, cityName);
        views.setTextViewText(R.id.textview_widget_temperature, Integer.toString(temperature) + context.getString(R.string.celcium));
        views.setTextViewText(R.id.textview_widget_tod, humanTod);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Intent intent = new Intent(context, ForecastWidget.class);
        intent.setAction(ACTION_WIDGET_UPDATE);


        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

}

