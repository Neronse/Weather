package ru.whalemare.weather.objects;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class City {

    private String code; // год города в гисметео
    private String name; // имя города
    private String region; // название области

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
