package ru.whalemare.weather;

public class Weather {

    String day = null; // число
    String month = null; // месяц
    String year = null; // год

    int tod = -1; // время суток
    int weekday = -1; // день недели

    int cloudiness = -1; // облачность
    int precipitation = -1; // тип осадков
    int rpower = -1; // интенсивность осадков
    int spower = -1; // вероятность грозы

    int pressure_max = -1; // давление максимальное
    int pressure_min = -1; // давление минимальное

    int temperature_max; // температура максимальная
    int temperature_min; // температура минимальная

    int wind_max = -1; // максимальное значение средней скорости ветра
    int wind_min = -1; // минимальное значение средней скорости ветра
    int wind_direction = -1; // направление ветра

    int relwet_max = -1; // макс. относительная влажность воздуха
    int relwet_min = -1; // мин. относительная влажность воздуха

    int heat_max; // макс. температура воздуха по ощущениям
    int heat_min; // мин. температура воздуха по ощущениям

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

    public int getTod() {
        return tod;
    }

    public void setTod(int tod) {
        this.tod = tod;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public int getRpower() {
        return rpower;
    }

    public void setRpower(int rpower) {
        this.rpower = rpower;
    }

    public int getSpower() {
        return spower;
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

    public int getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(int wind_direction) {
        this.wind_direction = wind_direction;
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
}
