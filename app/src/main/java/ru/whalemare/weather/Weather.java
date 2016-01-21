package ru.whalemare.weather;

public class Weather {

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

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
        this.humanWeekday = getHumanWeekday(weekday);
    }

    private String getHumanWeekday(int weekday) {
        switch (weekday)
        {
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

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
        this.humanAboutWeather = getAboutWeatherCloudines(cloudiness);
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
        this.humanAboutWeather = getAboutWeatherPrecipitation(precipitation);
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

    private String getStringTodfromInt(int tod) {
        switch (tod){
            case 0:
                return "Ночь";
            case 1:
                return "Утро";
            case 2:
                return "День";
            case 3:
                return "Вечер";
            default:
                return "Сегодня"; // или завтра :)
        }
    }

    private String getAboutWeatherCloudines(int cloudiness)
    {
        switch (cloudiness)
        {
            case 0:
                return "Ясно: ";
            case 1:
                return "Малооблачно: ";
            case 2:
                return "Облачно: ";
            case 3:
                return "Пасмурно: ";
            default:
                return "Неопределенно";
        }
    }

    private String getAboutWeatherPrecipitation(int precipitation){
        switch (precipitation)
        {
            case 4:
                return humanAboutWeather += "дождь";
            case 5:
                return humanAboutWeather += "ливень";
            case 6:
                return humanAboutWeather += "снег";
            case 7:
                return humanAboutWeather += "снег";
            case 8:
                return humanAboutWeather += "гроза";
            case 10:
                return humanAboutWeather += "без осадков";
            default:
                return humanAboutWeather += "";
        }
    }
}
