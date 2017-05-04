package ENMBean;

import javafx.beans.property.*;
import main.*;

public class CoulombClass implements PhysicsConstants {
    private DoubleProperty q1 = new SimpleDoubleProperty();
    private DoubleProperty q2 = new SimpleDoubleProperty();
    private DoubleProperty distance = new SimpleDoubleProperty();
    private DoubleProperty eForce = new SimpleDoubleProperty();
    
    public void setQ1(double ch1) {
        q1.set(ch1);
    }
    public void setQ2(double ch2) {
        q2.set(ch2);
    }
    public void setDistance(double d) {
        distance.set(d);
    }
    public void setEForce(double e) {
        eForce.set(e);
    }
    public double getQ1() {
        return q1.get();
    }
    public double getQ2() {
        return q2.get();
    }
    public double getDistance() {
        return distance.get();
    }
    public double getEForce() {
        return eForce.get();
    }
    public void calculateEForce() {
        eForce.set(Math.abs(CoulombConstant*(this.getQ1() * this.getQ2()))/(this.getDistance()*this.getDistance()));
    }
    public void calculateDistance() {
        distance.set(Math.sqrt(CoulombConstant*((this.getQ1() * this.getQ2())/this.getEForce())));
    }
    
    public double getProductOfCharges(double q1,double q2) {//x of graph
        return Math.abs(q1*q2);
    }
    
    public double getForceByDistance2(double f,double r) {//y of graph
        return (f*Math.pow(r,2));
    }
}