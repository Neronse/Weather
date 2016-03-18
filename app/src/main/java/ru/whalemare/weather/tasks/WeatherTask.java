package ru.whalemare.weather.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import ru.whalemare.weather.ForecastsCallback;
import ru.whalemare.weather.ParserConfig;
import ru.whalemare.weather.objects.Weather;

public class WeatherTask extends AsyncTask<Void, Void, ArrayList<Weather>> {

    private final String TAG = "WHALETAG";
    private String SITE = "http://informer.gismeteo.ru/xml/";

    private final ParserConfig config;

    ForecastsCallback callback;

    int countWeathers = -1; // количество уже занесенных в объекты прогнозов
    XmlPullParser parser; // парсер
    ArrayList<Weather> weathers = new ArrayList<>(4); // 4 объекта внутри списка прогноза

    public WeatherTask(ParserConfig config){
        this.config = config;
        this.callback = config.getCallback();
        this.SITE += config.FORECAST_CODE_XML;
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


                    if (nameTag.equals(config.FORECAST)) {
                        countWeathers++; // увеличим счетчик занесенных прогнозов
                        setAttrOfForecast(); // занесем атрибуты в объект

                    } else if (nameTag.equals(config.PHENOMENA)) {
                        setAttrOfPhenomena();

                    } else if (nameTag.equals(config.PRESSURE)) {
                        setAttrOfPressure();

                    } else if (nameTag.equals(config.TEMPERATURE)) {
                        setAttrOfTemperature();

                    } else if (nameTag.equals(config.WIND)) {
                        setAttrOfWind();

                    } else if (nameTag.equals(config.RELWET)) {
                        setAttrOfRelwet();

                    } else if (nameTag.equals(config.HEAT)) {
                        setAttrOfHeat();

                    } else {
                        Log.d(TAG, "Совпадений нет");
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weathers;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        callback.onForecastsRetrieved(weathers);
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
            if (s.equals(config.day)) {
                weathers.get(countWeathers).setDay(parser.getAttributeValue(i));

            } else if (s.equals(config.month)) {
                weathers.get(countWeathers).setMonth(parser.getAttributeValue(i));

            } else if (s.equals(config.year)) {
                weathers.get(countWeathers).setYear(parser.getAttributeValue(i));

            } else if (s.equals(config.tod)) {
                int tod = Integer.parseInt(parser.getAttributeValue(i)); // переведем в число и занесем в объект
                weathers.get(countWeathers).setTod(tod);

            } else if (s.equals(config.weekday)) {
                int weekday = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setWeekday(weekday);

            }
        }
    }

    private void setAttrOfPhenomena() {
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String phenomena = parser.getAttributeName(i);
            if (phenomena.equals(config.cloudiness)) {
                int cloudiness = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setCloudiness(cloudiness);

            } else if (phenomena.equals(config.precipitation)) {
                int precipitation = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setPrecipitation(precipitation);

            } else if (phenomena.equals(config.rpower)) {
                int rpower = Integer.parseInt(parser.getAttributeValue(i)); // переведем в число и занесем в объект
                weathers.get(countWeathers).setRpower(rpower);

            } else if (phenomena.equals(config.spower)) {
                int spower = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setSpower(spower);

            }
        }
    }

    private void setAttrOfPressure(){
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String pressure = parser.getAttributeName(i);
            if (pressure.equals(config.max)) {
                int max = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setPressure_max(max);

            } else if (pressure.equals(config.min)) {
                int min = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setPressure_min(min);

            }
        }
    }

    private void setAttrOfTemperature() {
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String temperature = parser.getAttributeName(i);
            if (temperature.equals(config.max)) {
                int max = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setTemperature_max(max);

            } else if (temperature.equals(config.min)) {
                int min = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setTemperature_min(min);

            }
        }
    }

    private void setAttrOfWind(){
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String wind = parser.getAttributeName(i);
            if (wind.equals(config.max)) {
                int max = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setWind_max(max);

            } else if (wind.equals(config.min)) {
                int min = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setWind_min(min);

            }
        }
    }

    private void setAttrOfRelwet(){
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String relwet = parser.getAttributeName(i);
            if (relwet.equals(config.max)) {
                int max = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setRelwet_max(max);

            } else if (relwet.equals(config.min)) {
                int min = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setRelwet_min(min);

            }
        }
    }

    private void setAttrOfHeat(){
        for (int i=0; i<parser.getAttributeCount(); i++) // пройдемся по всем атрибутам
        {
            String heat = parser.getAttributeName(i);
            if (heat.equals(config.max)) {
                int max = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setHeat_max(max);

            } else if (heat.equals(config.min)) {
                int min = Integer.parseInt(parser.getAttributeValue(i));
                weathers.get(countWeathers).setHeat_min(min);

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
