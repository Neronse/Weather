package ru.whalemare.weather.models;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class Stats {

    private final String gismeteo_code;
    private final String tod;
    private final String date;
    private final int temperature;
    private int min;
    private int max;

    public Stats(String gismeteo_code, String tod, String date, int t_max) {
        this.gismeteo_code = gismeteo_code;
        this.tod = tod;
        this.date = date;
        this.temperature = t_max;
    }

    public String getGismeteo_code() {
        return gismeteo_code;
    }

    public String getTod() {
        return tod;
    }

    public String getDate() {
        return date;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
