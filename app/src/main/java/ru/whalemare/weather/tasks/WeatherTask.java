package ru.whalemare.weather.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import ru.whalemare.weather.Fragments.ForecastFragment;
import ru.whalemare.weather.adapters.WeathersAdapter;
import ru.whalemare.weather.objects.Weather;

public class WeatherTask extends AsyncTask<Void, Void, ArrayList<Weather>> {

    private final String TAG = "WHALETAG";
    private String SITE = "http://informer.gismeteo.ru/xml/";

    // теги
    private final String FORECAST = "FORECAST";
    private final String PHENOMENA = "PHENOMENA";
    private final String PRESSURE = "PRESSURE";
    private final String TEMPERATURE = "TEMPERATURE";
    private final String WIND = "WIND";
    private final String RELWET = "RELWET";
    private final String HEAT = "HEAT";

    RecyclerView recyclerView;
    ForecastFragment.OnChooseForecastListener listener;

    int countWeathers = -1; // количество уже занесенных в объекты прогнозов
    XmlPullParser parser; // парсер
    ArrayList<Weather> weathers = new ArrayList<>(4); // 4 объекта внутри списка прогноза

    public WeatherTask(RecyclerView recyclerView, ForecastFragment.OnChooseForecastListener listener, String weatherCode){
        this.recyclerView = recyclerView;
        this.listener = listener;
        this.SITE += weatherCode + ".xml";
    }

    @Override
    protected ArrayList<Weather> doInBackground(Void... voids) {
        try{
            URL url = new URL(SITE);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false); // ??!
            parser = factory.newPullParser();
            parser.setInput(getInputStream(url), "UTF-8");

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) // пока не конец документа
            {
                if (eventType == XmlPullParser.START_TAG) // если новый тег
                {
                    String nameTag = parser.getName();
                    Log.d(TAG, "Новый тег: " + nameTag);
                    switch(nameTag){
                        case FORECAST:
                            countWeathers++; // увеличим счетчик занесенных прогнозов
                            setAttrOfForecast(); // занесем атрибуты в объект
                          //  Log.d(TAG, "Занесли день: " + weathers.get(countWeathers).getDay() + "." + weathers.get(countWeathers).getMonth() + "." + weathers.get(countWeathers).getYear());
                            break;
                        case PHENOMENA:
                            setAttrOfPhenomena();
                           // Log.d(TAG, "Облачность: " + weathers.get(countWeathers).getCloudiness());
                            break;
                        case PRESSURE:
                            setAttrOfPressure();
                            //Log.d(TAG, "Max Pressure: " + weathers.get(countWeathers).getPressure_max());
                            break;
                        case TEMPERATURE:
                            setAttrOfTemperature();
                            break;
                        case WIND:
                            setAttrOfWind();
                            break;
                        case RELWET:
                            setAttrOfRelwet();
                            break;
                        case HEAT:
                            setAttrOfHeat();
                            break;
                        default:
                            Log.d(TAG, "Совпадений нет");
                            break;
                    }
                }
                eventType = parser.next();
            }

            // Небольшой тест на наличие записей в ArrayList
            for (int i=0; i<=countWeathers; i++)
                Log.d(TAG, "День: " + weathers.get(i).getDay() + ". Температура макс: " + weathers.get(i).getTemperature_max() +
                            "tod: " + weathers.get(i).getTod());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weathers;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);

        RecyclerView.Adapter adapter = new WeathersAdapter(weathers, listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Т.к. это FORECAST это первый атрибут, который будет находится в .xml файле, то именно в нем
     * создадим новый объект в ArrayList и будем изменять количество уже занесенных объектов.
     */
    private void setAttrOfForecast() {
        weathers.add(new Weather()); // добавим новый элемент в список;
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String s = parser.getAttributeName(i);
            switch (s) {
                case "day":
                    weathers.get(countWeathers).setDay(parser.getAttributeValue(i));
                    break;
                case "month":
                    weathers.get(countWeathers).setMonth(parser.getAttributeValue(i));
                    break;
                case "year":
                    weathers.get(countWeathers).setYear(parser.getAttributeValue(i));
                    break;
                case "tod":
                    int tod = Integer.parseInt(parser.getAttributeValue(i)); // переведем в число и занесем в объект
                    weathers.get(countWeathers).setTod(tod);
                    break;
                case "weekday":
                    int weekday = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setWeekday(weekday);
                    break;
            }
        }
    }

    private void setAttrOfPhenomena() {
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String phenomena = parser.getAttributeName(i);
            switch(phenomena){
                case "cloudiness":
                    int cloudiness = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setCloudiness(cloudiness);
                    break;
                case "precipitation":
                    int precipitation = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setPrecipitation(precipitation);
                    break;
                case "rpower":
                    int rpower = Integer.parseInt(parser.getAttributeValue(i)); // переведем в число и занесем в объект
                    weathers.get(countWeathers).setRpower(rpower);
                    break;
                case "spower":
                    int spower = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setSpower(spower);
                    break;
            }
        }
    }

    private void setAttrOfPressure(){
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String pressure = parser.getAttributeName(i);
            switch(pressure){
                case "max":
                    int max = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setPressure_max(max);
                    break;
                case "min":
                    int min = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setPressure_min(min);
                    break;
            }
        }
    }

    private void setAttrOfTemperature() {
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String temperature = parser.getAttributeName(i);
            switch(temperature){
                case "max":
                    int max = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setTemperature_max(max);
                    break;
                case "min":
                    int min = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setTemperature_min(min);
                    break;
            }
        }
    }

    private void setAttrOfWind(){
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String wind = parser.getAttributeName(i);
            switch(wind){
                case "max":
                    int max = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setWind_max(max);
                    break;
                case "min":
                    int min = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setWind_min(min);
                    break;
            }
        }
    }

    private void setAttrOfRelwet(){
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String relwet = parser.getAttributeName(i);
            switch(relwet){
                case "max":
                    int max = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setRelwet_max(max);
                    break;
                case "min":
                    int min = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setRelwet_min(min);
                    break;
            }
        }
    }

    private void setAttrOfHeat(){
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String heat = parser.getAttributeName(i);
            switch(heat){
                case "max":
                    int max = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setHeat_max(max);
                    break;
                case "min":
                    int min = Integer.parseInt(parser.getAttributeValue(i));
                    weathers.get(countWeathers).setHeat_min(min);
                    break;
            }
        }
    }

    private InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
