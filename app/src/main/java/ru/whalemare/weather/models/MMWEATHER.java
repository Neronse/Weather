package ru.whalemare.weather.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="MMWEATHER")
public class MMWEATHER {

//    @Text(required=false)
//    String textValue;

    @Element(name="REPORT")
    REPORT rEPORT;

//    public String getTextValue() {return this.textValue;}
//    public void setTextValue(String value) {this.textValue = value;}

    public REPORT getREPORT() {return this.rEPORT;}
    public void setREPORT(REPORT value) {this.rEPORT = value;}

    public static class WIND {

        @Attribute(name="min")
        Integer min;

        @Attribute(name="max")
        Integer max;

        @Attribute(name="direction")
        Integer direction;

        public Integer getMin() {return this.min;}
        public void setMin(Integer value) {this.min = value;}

        public Integer getMax() {return this.max;}
        public void setMax(Integer value) {this.max = value;}

        public Integer getDirection() {return this.direction;}
        public void setDirection(Integer value) {this.direction = value;}

    }

    public static class RELWET {

        @Attribute(name="min")
        Integer min;

        @Attribute(name="max")
        Integer max;

        public Integer getMin() {return this.min;}
        public void setMin(Integer value) {this.min = value;}

        public Integer getMax() {return this.max;}
        public void setMax(Integer value) {this.max = value;}

    }

    public static class PRESSURE {

        @Attribute(name="min")
        Integer min;

        @Attribute(name="max")
        Integer max;

        public Integer getMin() {return this.min;}
        public void setMin(Integer value) {this.min = value;}

        public Integer getMax() {return this.max;}
        public void setMax(Integer value) {this.max = value;}

    }

    public static class HEAT {

        @Attribute(name="min")
        Integer min;

        @Attribute(name="max")
        Integer max;

        public Integer getMin() {return this.min;}
        public void setMin(Integer value) {this.min = value;}

        public Integer getMax() {return this.max;}
        public void setMax(Integer value) {this.max = value;}

    }

    public static class TEMPERATURE {

        @Attribute(name="min")
        Integer min;

        @Attribute(name="max")
        Integer max;

        public Integer getMin() {return this.min;}
        public void setMin(Integer value) {this.min = value;}

        public Integer getMax() {return this.max;}
        public void setMax(Integer value) {this.max = value;}

    }

    public static class TOWN {

        @Attribute(name="sname")
        String sname;

        @Attribute(name="latitude")
        Integer latitude;

        @Attribute(name="index")
        Integer index;

        @ElementList(name="FORECAST", entry="FORECAST", inline=true)
        List<FORECAST> fORECAST;

        @Attribute(name="longitude")
        Integer longitude;

        public String getSname() {return this.sname;}
        public void setSname(String value) {this.sname = value;}

        public Integer getLatitude() {return this.latitude;}
        public void setLatitude(Integer value) {this.latitude = value;}

        public Integer getIndex() {return this.index;}
        public void setIndex(Integer value) {this.index = value;}

        public List<FORECAST> getFORECAST() {return this.fORECAST;}
        public void setFORECAST(List<FORECAST> value) {this.fORECAST = value;}

        public Integer getLongitude() {return this.longitude;}
        public void setLongitude(Integer value) {this.longitude = value;}

    }

    public static class PHENOMENA {

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

    }

    public static class REPORT {

        @Element(name="TOWN")
        TOWN tOWN;

        @Attribute(name="type")
        String type;

        public TOWN getTOWN() {return this.tOWN;}
        public void setTOWN(TOWN value) {this.tOWN = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class FORECAST {

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

    }

}