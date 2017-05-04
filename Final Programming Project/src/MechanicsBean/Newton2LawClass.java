package MechanicsBean;

import javafx.beans.property.*;

public class Newton2LawClass {

    private DoubleProperty mass=new SimpleDoubleProperty();
    private DoubleProperty force=new SimpleDoubleProperty();
    private DoubleProperty frictionForce=new SimpleDoubleProperty();
    private DoubleProperty netForce=new SimpleDoubleProperty();
    private DoubleProperty acceleration=new SimpleDoubleProperty();

    public void setMass(double m) {
        mass.set(m);
    }

    public void calculateMass() {
        mass.set(this.getNetForce()/this.getAcceleration());
    }

    public double getMass() {
        return mass.get();
    }

    public void setForce(double f) {
        force.set(f);
    }

    public void getForceFromFriction() {
        force.set(this.getNetForce()+this.getFrictionForce());
    }

    public double getForce() {
        return force.get();
    }

    public void setFrictionForce(double fr) {
        frictionForce.set(fr);
    }

    public void getFrictionFromForce() {
        frictionForce.set(this.getForce()-this.getNetForce());
    }

    public double getFrictionForce() {
        return frictionForce.get();
    }

    public void calculateNetForce() {
        netForce.set(this.getForce()-this.getFrictionForce());
    }

    public void calculateNetForceWithUndefinedForce() {
        netForce.set(this.getMass()*this.getAcceleration());
    }

    public double getNetForce() {
        return netForce.get();
    }

    public void setAcceleration(double a) {
        acceleration.set(a);
    }

    public void calculateAcceleration() {
        acceleration.set(this.getNetForce()/this.getMass());
    }

    public double getAcceleration() {
        return acceleration.get();
    }

    public double getVelocityOverTime(double t,double a) {
        return t*a;
    }
}