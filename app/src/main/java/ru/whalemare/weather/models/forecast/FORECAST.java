package ru.whalemare.weather.models.forecast;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class FORECAST{

    public FORECAST() {
    }

    @Element(name="WIND")
    WIND wIND;

    @Attribute(name="year")
    Integer year;

    @Element(name="PHENOMENA")
    PHENOMENA pHENOMENA;

    @Attribute(name="tod")
    Integer tod;

    @Attribute(name="weekday")
    Integer weekday;

    @Element(name="RELWET")
    RELWET rELWET;

    @Element(name="PRESSURE")
    PRESSURE pRESSURE;

    @Element(name="HEAT")
    HEAT hEAT;

    @Attribute(name="hour")
    Integer hour;

    @Attribute(name="month")
    Integer month;

    @Element(name="TEMPERATURE")
    TEMPERATURE tEMPERATURE;

    @Attribute(name="predict")
    Integer predict;

    @Attribute(name="day")
    Integer day;

    String humanTod = null;
    private String humanAboutWeather;

    public WIND getWIND() {return this.wIND;}
    public void setWIND(WIND value) {this.wIND = value;}

    public Integer getYear() {return this.year;}
    public void setYear(Integer value) {this.year = value;}

    public PHENOMENA getPHENOMENA() {return this.pHENOMENA;}
    public void setPHENOMENA(PHENOMENA value) {this.pHENOMENA = value;}

    public Integer getTod() {return this.tod;}
    public void setTod(Integer value) {this.tod = value;}

    public Integer getWeekday() {return this.weekday;}
    public void setWeekday(Integer value) {this.weekday = value;}

    public RELWET getRELWET() {return this.rELWET;}
    public void setRELWET(RELWET value) {this.rELWET = value;}

    public PRESSURE getPRESSURE() {return this.pRESSURE;}
    public void setPRESSURE(PRESSURE value) {this.pRESSURE = value;}

    public HEAT getHEAT() {return this.hEAT;}
    public void setHEAT(HEAT value) {this.hEAT = value;}

    public Integer getHour() {return this.hour;}
    public void setHour(Integer value) {this.hour = value;}

    public Integer getMonth() {return this.month;}
    public void setMonth(Integer value) {this.month = value;}

    public TEMPERATURE getTEMPERATURE() {return this.tEMPERATURE;}
    public void setTEMPERATURE(TEMPERATURE value) {this.tEMPERATURE = value;}

    public Integer getPredict() {return this.predict;}
    public void setPredict(Integer value) {this.predict = value;}

    public Integer getDay() {return this.day;}
    public void setDay(Integer value) {this.day = value;}

    public String getHumanTod() {
        int type = getTod();
        final String tods[] = {
                "Ночью",
                "Утром",
                "Днем",
                "Вечером"
        };

        if (type < tods.length)
            return tods[type];
        else
            return "Cегодня";
    }

    // FIXME: 30.03.2016 add parcelable methods
    public String getHumanWeekday() {
        int type = getWeekday();
        final String[] days = {
                "Воскресенье",
                "Понедельник",
                "Вторник",
                "Среда",
                "Четверг",
                "Пятница",
                "Суббота"
        };

        return days[type-1];
    }
}