package ru.whalemare.weather.models;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class City {

    private String gismeteoCode;
    private String cityName;
    private String regionCode;
    private String regionName;

    public City() {
    }

    public City(String gismeteoCode, String cityName) {
        this.gismeteoCode = gismeteoCode;
        this.cityName = cityName;
    }

    public City(String gismeteoCode, String cityName, String regionCode, String regionName) {
        this.gismeteoCode = gismeteoCode;
        this.cityName = cityName;
        this.regionCode = regionCode;
        this.regionName = regionName;
    }

    public City(String gismeteoCode) {
        this.gismeteoCode = gismeteoCode;
    }

    public String getGismeteoCode() {
        return gismeteoCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
