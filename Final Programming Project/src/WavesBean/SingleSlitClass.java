package WavesBean;

import javafx.beans.property.*;
import main.*;

public class SingleSlitClass implements PhysicsConstants {

    private DoubleProperty slitWidth=new SimpleDoubleProperty();
    private DoubleProperty angle=new SimpleDoubleProperty();
    private DoubleProperty wavelength=new SimpleDoubleProperty();
    private DoubleProperty distance=new SimpleDoubleProperty();
    private DoubleProperty yDisplacement=new SimpleDoubleProperty();
    private IntegerProperty order=new SimpleIntegerProperty();

    public void setSlitWidth(double sw) {
        slitWidth.set(sw);
    }

    public void calculateSlitWidth() {
        slitWidth.set((this.getOrder()*this.getWavelength())/(Math.sin(this.getAngle()*DegToRad)));
    }

    public double getSlitWidth() {
        return slitWidth.get();
    }

    public void setAngle(double a) {
        angle.set(a);
    }

    public void calculateAngle() {
        angle.set(((Math.atan(this.getYDisplacement()/this.getDistance()))*RadToDeg));
    }

    public double getAngle() {
        return angle.get();
    }

    public void setWavelength(double w) {
        wavelength.set(w);
    }

    public void calculateWavelength() {
        wavelength.set((this.getSlitWidth()*Math.sin(this.getAngle()*DegToRad))/(this.getOrder()));
    }

    public double getWavelength() {
        return wavelength.get();
    }

    public void setDistance(double d) {
        distance.set(d);
    }

    public void calculateDistance() {
        distance.set((this.getYDisplacement())/(Math.tan(this.getAngle()*DegToRad)));
    }

    public double getDistance() {
        return distance.get();
    }

    public void setYDisplacement(double y) {
        yDisplacement.set(y);
    }

    public void calculateYDisplacement() {
        yDisplacement.set(Math.tan(this.getAngle()*DegToRad)*this.getDistance());
    }

    public double getYDisplacement() {
        return yDisplacement.get();
    }

    public void setOrder(int m) {
        order.set(m);
    }

    public void calculateOrder() {
        order.set((int) Math.round((this.getSlitWidth()*Math.sin(this.getAngle()*DegToRad))/(this.getWavelength())));
    }

    public int getOrder() {
        return order.get();
    }
    
    public double getFirstOrderAngle(double wl,double sw) {
        return Math.asin(wl/sw)*RadToDeg;
    }
}