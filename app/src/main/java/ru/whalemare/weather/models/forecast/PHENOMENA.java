package ru.whalemare.weather.models.forecast;

import org.simpleframework.xml.Attribute;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class PHENOMENA {

    @Attribute(name="precipitation")
    Integer precipitation;

    @Attribute(name="rpower")
    Integer rpower;

    @Attribute(name="spower")
    Integer spower;

    @Attribute(name="cloudiness")
    Integer cloudiness;

    public Integer getPrecipitation() {return this.precipitation;}
    public void setPrecipitation(Integer value) {this.precipitation = value;}

    public Integer getRpower() {return this.rpower;}
    public void setRpower(Integer value) {this.rpower = value;}

    public Integer getSpower() {return this.spower;}
    public void setSpower(Integer value) {this.spower = value;}

    public Integer getCloudiness() {return this.cloudiness;}
    public void setCloudiness(Integer value) {this.cloudiness = value;}

    public String getHumanAboutWeather(){
        final String cloudinesses[] = {"Ясно: ", "Малооблачно: ", "Облачно: ", "Пасмурно: "};
        int type = getCloudiness();

        String finish;

        if (type < cloudinesses.length)
            finish = cloudinesses[type];
        else
            finish = "Неопределенно";

        type = getPrecipitation() - 4;
        final String precepitations[] = {"дождь", "ливень", "снег", "метель", "гроза", "без осадков"};

        if (type < precepitations.length)
            finish += precepitations[type];
        else
            return finish;

        return finish;
    }

}
