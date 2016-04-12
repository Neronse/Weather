package ru.whalemare.weather.models;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class Stats {

    private final String gismeteo_code;
    private final String tod;
    private final String date;
    private final int t_max;
    private final int t_min;

    public Stats(String gismeteo_code, String tod, String date, int t_max, int t_min) {
        this.gismeteo_code = gismeteo_code;
        this.tod = tod;
        this.date = date;
        this.t_max = t_max;
        this.t_min = t_min;
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

    public int getT_max() {
        return t_max;
    }

    public int getT_min() {
        return t_min;
    }
}
