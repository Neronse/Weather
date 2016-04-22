package ru.whalemare.weather.models.forecast;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="MMWEATHER")
public class MMWEATHER {

    @Element(name="REPORT")
    REPORT report;

    public REPORT getREPORT() {return this.report;}
    public void setREPORT(REPORT value) {this.report = value;}

}