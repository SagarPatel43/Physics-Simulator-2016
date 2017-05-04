package WavesBean;

import javafx.beans.property.*;

public class SHMClass {

    private DoubleProperty amplitude = new SimpleDoubleProperty();
    private DoubleProperty time = new SimpleDoubleProperty();
    private DoubleProperty phaseConstant = new SimpleDoubleProperty();
    private DoubleProperty frequency = new SimpleDoubleProperty();
    private DoubleProperty period = new SimpleDoubleProperty();
    private DoubleProperty angularVelocity = new SimpleDoubleProperty();

    public void setAmplitude(double a) {
        amplitude.set(a);
    }

    public double getAmplitude() {
        return amplitude.get();
    }

    public double getTime() {
        return time.get();
    }

    public void setPhaseConstant(double phi) {
        phaseConstant.set(phi);
    }

    public double getPhaseConstant() {
        return phaseConstant.get();
    }

    public void setFrequency(double f) {
        frequency.set(f);
    }

    public void calculateFrequency() {
        frequency.set(1 / this.getPeriod());
    }

    public double getFrequency() {
        return frequency.get();
    }

    public void setPeriod(double p) {
        period.set(p);
    }

    public void calculatePeriod() {
        angularVelocity.set(1 / this.getFrequency());
    }

    public double getPeriod() {
        return period.get();
    }

    public void setAngularVelocity(double w) {
        angularVelocity.set(w);
    }

    public void calculateAngularVelocity() {
        angularVelocity.set(2 * Math.PI * this.getFrequency());
    }

    public double getAngularVelocity() {
        return angularVelocity.get();
    }

    public double calculateYDisplacement(double t,double a,double w,double p) {
        return (a * Math.sin(w * t) + p);
    }

    public double calculateLinearVelocity(double t,double a,double w,double p) {
        return (w * a * Math.cos(w * t) + p);
    }
}