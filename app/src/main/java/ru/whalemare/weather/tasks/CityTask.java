package ru.whalemare.weather.tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.DatabaseHandlerImpl;
import ru.whalemare.weather.R;
import ru.whalemare.weather.interfaces.CitiesCallback;
import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityTask extends AsyncTask<Void, Void, List<City>> {

    private static final String TAG = "WHALETAG";

    private final CitiesCallback callback;
    private List<City> cities;
    private final XmlPullParser parser;

    private final Context context;

    public CityTask(CitiesCallback callback, Context context) {
        this.callback = callback;
        this.parser = context.getResources().getXml(R.xml.gismeteo);
        this.context = context;
        this.cities = new ArrayList<>();
    }

    private int count = 0;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<City> doInBackground(Void... voids) {
        DatabaseHandlerImpl database = new DatabaseHandlerImpl(context);
        database.initializeDatabaseFromAPK();

        try {
            database.openDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cities = database.getAllData();

        database.close();

        return cities;
    }

    @Override
    protected void onPostExecute(List<City> cities) {
        super.onPostExecute(cities);
        callback.onCitiesRetrieved(cities);
    }

    @Deprecated
    void parsingData() {
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.TEXT) {
                    count++;

                    switch (count) {
                        case 1:
                            cities.add(new City(parser.getText()));
//                            Log.d(TAG, "gismeteo code = " + parser.getText());
                            break;
                        case 2:
                            cities.get(cities.size() - 1).setCityName(parser.getText());
//                            Log.d(TAG, "city name = " + parser.getText());
                            break;
                        case 3:
                            cities.get(cities.size() - 1).setRegionCode(parser.getText());
//                            Log.d(TAG, "region code = " + parser.getText());
                            break;
                        case 4:
                            cities.get(cities.size() - 1).setRegionName(parser.getText());
//                            Log.d(TAG, "region name = " + parser.getText());
                            count = 0;
                            break;
                    }
                }
                parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}
