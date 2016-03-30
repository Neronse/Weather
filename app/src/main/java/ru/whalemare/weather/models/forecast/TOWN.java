package ru.whalemare.weather.models.forecast;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class TOWN {

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
