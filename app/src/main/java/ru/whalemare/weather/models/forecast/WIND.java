package ru.whalemare.weather.models.forecast;

import org.simpleframework.xml.Attribute;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class WIND {

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
