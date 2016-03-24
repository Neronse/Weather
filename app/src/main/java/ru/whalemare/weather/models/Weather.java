package ru.whalemare.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {

    private String day = null; // число
    private String month = null; // месяц
    private String year = null; // год

    private String humanAboutWeather = null; // Ясно: без осадков
    private String humanTod = null; // время суток в человеческое строке: утро, день, вечер, ночь
    private String humanWeekday = null;

    private int tod = -1; // время суток

    private int weekday = -1; // день недели

    private int cloudiness = -1; // облачность
    private int precipitation = -1; // тип осадков
    private int rpower = -1; // интенсивность осадков
    private int spower = -1; // вероятность грозы

    private int pressure_max = -1; // давление максимальное
    private int pressure_min = -1; // давление минимальное

    private int temperature_max; // температура максимальная
    private int temperature_min; // температура минимальная

    private int wind_max = -1; // максимальное значение средней скорости ветра
    private int wind_min = -1; // минимальное значение средней скорости ветра
    private int wind_direction = -1; // направление ветра

    private int relwet_max = -1; // макс. относительная влажность воздуха
    private int relwet_min = -1; // мин. относительная влажность воздуха

    private int heat_max; // макс. температура воздуха по ощущениям
    private int heat_min; // мин. температура воздуха по ощущениям

    public Weather() {
    }

    protected Weather(Parcel in) {
        day = in.readString();
        month = in.readString();
        year = in.readString();
        humanAboutWeather = in.readString();
        humanTod = in.readString();
        humanWeekday = in.readString();
        tod = in.readInt();
        weekday = in.readInt();
        cloudiness = in.readInt();
        precipitation = in.readInt();
        rpower = in.readInt();
        spower = in.readInt();
        pressure_max = in.readInt();
        pressure_min = in.readInt();
        temperature_max = in.readInt();
        temperature_min = in.readInt();
        wind_max = in.readInt();
        wind_min = in.readInt();
        wind_direction = in.readInt();
        relwet_max = in.readInt();
        relwet_min = in.readInt();
        heat_max = in.readInt();
        heat_min = in.readInt();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHumanWeekday() {
        return humanWeekday;
    }

    public int getTod() {
        return tod;
    }

    public void setTod(int tod) {
        this.tod = tod;
        this.humanTod = getStringTodfromInt(tod);
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
        this.humanWeekday = getHumanWeekday(weekday);
    }

    private String getHumanWeekday(int weekday) {
        switch (weekday) {
            case 1:
                return "Воскресенье";
            case 2:
                return "Понедельник";
            case 3:
                return "Вторник";
            case 4:
                return "Среда";
            case 5:
                return "Четверг";
            case 6:
                return "Пятница";
            case 7:
                return "Суббота";
        }

        return null;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
        this.humanAboutWeather = getAboutWeatherCloudines(cloudiness);
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
        this.humanAboutWeather = getAboutWeatherPrecipitation(precipitation);
    }

    public void setRpower(int rpower) {
        this.rpower = rpower;
    }

    public void setSpower(int spower) {
        this.spower = spower;
    }

    public int getPressure_max() {
        return pressure_max;
    }

    public void setPressure_max(int pressure_max) {
        this.pressure_max = pressure_max;
    }

    public int getPressure_min() {
        return pressure_min;
    }

    public void setPressure_min(int pressure_min) {
        this.pressure_min = pressure_min;
    }

    public int getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(int temperature_max) {
        this.temperature_max = temperature_max;
    }

    public String getHumanAboutWeather() {
        return humanAboutWeather;
    }

    public String getHumanTod() {
        return humanTod;
    }

    public int getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(int temperature_min) {
        this.temperature_min = temperature_min;
    }

    public int getWind_max() {
        return wind_max;
    }

    public void setWind_max(int wind_max) {
        this.wind_max = wind_max;
    }

    public int getWind_min() {
        return wind_min;
    }

    public void setWind_min(int wind_min) {
        this.wind_min = wind_min;
    }

    public int getRelwet_max() {
        return relwet_max;
    }

    public void setRelwet_max(int relwet_max) {
        this.relwet_max = relwet_max;
    }

    public int getRelwet_min() {
        return relwet_min;
    }

    public void setRelwet_min(int relwet_min) {
        this.relwet_min = relwet_min;
    }

    public int getHeat_max() {
        return heat_max;
    }

    public void setHeat_max(int heat_max) {
        this.heat_max = heat_max;
    }

    public int getHeat_min() {
        return heat_min;
    }

    public void setHeat_min(int heat_min) {
        this.heat_min = heat_min;
    }

    private String getStringTodfromInt(int type) {
        final String tods[] = {"Ночь", "Утро", "День", "Вечер"};

        if (type < tods.length)
            return tods[type];
        else
            return "Cегодня";
    }

    private String getAboutWeatherCloudines(int type) {
        final String cloudinesses[] = {"Ясно: ", "Малооблачно: ", "Облачно: ", "Пасмурно: "};

        if (type < cloudinesses.length)
            return cloudinesses[type];
        else
            return "Неопределенно";
    }

    private String getAboutWeatherPrecipitation(int type) {
        type = type - 4;
        final String precepitations[] = {"дождь", "ливень", "снег", "метель", "гроза", "без осадков"};

        if (type < precepitations.length)
            return humanAboutWeather += precepitations[type];
        else
            return humanAboutWeather;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(day);
        parcel.writeString(month);
        parcel.writeString(year);
        parcel.writeString(humanAboutWeather);
        parcel.writeString(humanTod);
        parcel.writeString(humanWeekday);
        parcel.writeInt(tod);
        parcel.writeInt(weekday);
        parcel.writeInt(cloudiness);
        parcel.writeInt(precipitation);
        parcel.writeInt(rpower);
        parcel.writeInt(spower);
        parcel.writeInt(pressure_max);
        parcel.writeInt(pressure_min);
        parcel.writeInt(temperature_max);
        parcel.writeInt(temperature_min);
        parcel.writeInt(wind_max);
        parcel.writeInt(wind_min);
        parcel.writeInt(wind_direction);
        parcel.writeInt(relwet_max);
        parcel.writeInt(relwet_min);
        parcel.writeInt(heat_max);
        parcel.writeInt(heat_min);
    }
}
