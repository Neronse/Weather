package ru.whalemare.weather.models.forecast;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class REPORT {

    @Element(name="TOWN")
    TOWN tOWN;

    @Attribute(name="type")
    String type;

    public TOWN getTOWN() {return this.tOWN;}
    public void setTOWN(TOWN value) {this.tOWN = value;}

    public String getType() {return this.type;}
    public void setType(String value) {this.type = value;}

}
