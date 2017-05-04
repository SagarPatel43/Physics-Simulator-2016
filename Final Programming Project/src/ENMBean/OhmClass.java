package ENMBean;

import javafx.beans.property.*;

public class OhmClass {

    private DoubleProperty voltage = new SimpleDoubleProperty();
    private DoubleProperty current = new SimpleDoubleProperty();
    private DoubleProperty resistance = new SimpleDoubleProperty();

    public void setVoltage(double v) {
        voltage.set(v);
    }

    public void setCurrent(double c) {
        current.set(c);
    }

    public void setResistance(double r) {
        resistance.set(r);
    }

    public double getVoltage() {
        return voltage.get();
    }

    public double getCurrent() {
        return current.get();
    }

    public double getResistance() {
        return resistance.get();
    }

    public void calculateVoltage() {
        voltage.set(this.getCurrent() * this.getResistance());
    }

    public void calculateResistance() {
        resistance.set(this.getVoltage() / this.getCurrent());
    }

    public void calculateIntensity() {
        current.set(this.getVoltage() / this.getResistance());
    }

}
