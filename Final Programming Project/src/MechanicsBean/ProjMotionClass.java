package MechanicsBean;

import javafx.beans.property.*;
import main.*;

public class ProjMotionClass implements PhysicsConstants {

    private DoubleProperty xDisplacement=new SimpleDoubleProperty();
    private DoubleProperty initialHeight=new SimpleDoubleProperty();
    private DoubleProperty xVelocity=new SimpleDoubleProperty();
    private DoubleProperty initialYVelocity=new SimpleDoubleProperty();
    private DoubleProperty finalYVelocity=new SimpleDoubleProperty();
    private DoubleProperty initialVelocityMag=new SimpleDoubleProperty();
    private DoubleProperty finalVelocityMag=new SimpleDoubleProperty();
    private DoubleProperty initialVelAngle=new SimpleDoubleProperty();
    private DoubleProperty finalVelAngle=new SimpleDoubleProperty();
    private DoubleProperty time=new SimpleDoubleProperty();

    public void calculateXDisplacement() {
        xDisplacement.set(this.getTime()*this.getXVelocity());
    }

    public double getXDisplacement() {
        return xDisplacement.get();
    }

    public void setInitialHeight(double initH) {
        initialHeight.set(initH);
    }

    public double getInitialHeight() {
        return initialHeight.get();
    }

    public double getHeightOverTime(double t,double iv,double ih) {
        return ((iv*t)+(0.5*ProjectileMotionYAcceleration*Math.pow(t,2))+ih);
    }

    public void setXVelocity(double mag,double angle) {
        xVelocity.set(mag*Math.cos(angle*DegToRad));
    }

    public double getXVelocity() {
        return xVelocity.get();
    }

    public void setInitialYVelocity(double mag,double angle) {
        initialYVelocity.set(mag*Math.sin(angle*DegToRad));
    }

    public void calculateInitialYVelocity() {
        initialYVelocity.set(Math.sqrt(Math.abs(Math.pow(this.getFinalYVelocity(),2)+2*ProjectileMotionYAcceleration*this.getInitialHeight())));
    }

    public double getInitialYVelocity() {
        return initialYVelocity.get();
    }

    public void setFinalYVelocity(double mag,double angle) {
        finalYVelocity.set(-1*mag*Math.sin(angle*DegToRad));
    }

    public void calculateFinalYVelocity() {
        finalYVelocity.set(-1*Math.sqrt(Math.pow(this.getInitialYVelocity(),2)+2*ProjectileMotionYAcceleration*-1*this.getInitialHeight()));
    }

    public double getFinalYVelocity() {
        return finalYVelocity.get();
    }

    public void setInitialVelocityMagnitude(double initVMag) {
        initialVelocityMag.set(initVMag);
    }

    public void calculateInitialVelocityMagnitude() {
        initialVelocityMag.set(Math.sqrt(Math.pow(this.getInitialYVelocity(),2)+Math.pow(this.getXVelocity(),2)));
    }

    public double getInitialVelocityMagnitude() {
        return initialVelocityMag.get();
    }

    public double getVelocityMagnitudeOverTime(double t,double vx,double viy) {
        double vy=viy+ProjectileMotionYAcceleration*t;
        return Math.sqrt(Math.pow(vx,2)+Math.pow(vy,2));
    }

    public void setFinalVelocityMagnitude(double finalVMag) {
        finalVelocityMag.set(finalVMag);
    }

    public void calculateFinalVelocityMagnitude() {
        finalVelocityMag.set(Math.sqrt(Math.pow(this.getFinalYVelocity(),2)+Math.pow(this.getXVelocity(),2)));
    }

    public double getFinalVelocityMagnitude() {
        return finalVelocityMag.get();
    }
    
    public void setInitialVelocityAngle(double initVAngle) {
        initialVelAngle.set(initVAngle);
    }

    public void calculateInitialVelocityAngle() {
        initialVelAngle.set(Math.atan(this.getInitialYVelocity()/this.getXVelocity())*RadToDeg);
    }

    public double getInitialVelocityAngle() {
        return initialVelAngle.get();
    }

    public void setFinalVelocityAngle(double finalVAngle) {
        finalVelAngle.set(finalVAngle);
    }

    public void calculateFinalVelocityAngle() {
        finalVelAngle.set(Math.atan(this.getFinalYVelocity()/this.getXVelocity())*RadToDeg);
    }

    public double getFinalVelocityAngle() {
        return finalVelAngle.get();
    }

    public void calculateTime() {
        time.set((this.getFinalYVelocity()-this.getInitialYVelocity())/ProjectileMotionYAcceleration);
    }

    public double getTime() {
        return time.get();
    }
}