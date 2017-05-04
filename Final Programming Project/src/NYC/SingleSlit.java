package NYC;

import javafx.event.*;
import WavesBean.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import main.*;
import static main.ClsStart.*;
import static main.ClsFunctions.*;
import javafx.animation.*;
import javafx.scene.paint.*;
import javafx.util.*;

public class SingleSlit implements PhysicsConstants {

    private static SingleSlitClass ss=new SingleSlitClass();

    private static TextField slitwidthField=new TextField();
    private static Label slitwidthLabel=new Label("Slit Width (m): ");
    private static TextField angleField=new TextField();
    private static Label angleLabel=new Label("Angle (˚): ");
    private static TextField wavelengthField=new TextField();
    private static Label wavelengthLabel=new Label("Wavelength (m): ");
    private static TextField distanceField=new TextField();
    private static Label distanceLabel=new Label("Distance (m): ");
    private static TextField yDisplacementField=new TextField();
    private static Label yDisplacementLabel=new Label("Y-displacement (m): ");
    private static TextField orderField=new TextField();
    private static Label orderLabel=new Label("Order: ");

    private static Line distance=new Line();
    private static Line wall=new Line();
    private static Line slitTop=new Line();
    private static Line slitBot=new Line();
    private static Line laserBeam=new Line();
    
    private static Image lp=new Image("file:///Volumes/An/Programming/Vanier/420-204-RE/Final Project/src/NYC/Laser pointer.png");
    private static ImageView laserPointer=new ImageView(lp);

    private static boolean animationPaused;

    private static int blankCount;

    public static void buttonMethods() {
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                disableButton(start,true);
                disableButton(pause,false);
                if (animationPaused) {
                    animationPaused=false;
                    //allAnimations.play();
                } else {
                    calculateAllData();
                }
            }
        });
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                reverseAnimations();
                ClsFunctions.doneButtonMethod();
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                pauseAnimation();
                ClsFunctions.disableButton(start,false);
                ClsFunctions.disableButton(pause,true);
            }
        });
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                reverseAnimations();
            }
        });
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                helpMessage+="In addition to that, no variable can have a negative value or out be out of graphing range";
                showHelpDialog();
            }
        });
    }

    public static void addTextFields() {
        ClsStart.textFieldBox.setConstraints(slitwidthLabel,0,0);
        ClsStart.textFieldBox.setConstraints(slitwidthField,1,0);
        ClsStart.textFieldBox.setConstraints(angleLabel,0,1);
        ClsStart.textFieldBox.setConstraints(angleField,1,1);
        ClsStart.textFieldBox.setConstraints(wavelengthLabel,0,2);
        ClsStart.textFieldBox.setConstraints(wavelengthField,1,2);
        ClsStart.textFieldBox.setConstraints(distanceLabel,0,3);
        ClsStart.textFieldBox.setConstraints(distanceField,1,3);
        ClsStart.textFieldBox.setConstraints(yDisplacementLabel,0,4);
        ClsStart.textFieldBox.setConstraints(yDisplacementField,1,4);
        ClsStart.textFieldBox.setConstraints(orderLabel,0,5);
        ClsStart.textFieldBox.setConstraints(orderField,1,5);

        ClsStart.textFieldBox.getChildren().addAll(slitwidthLabel,slitwidthField,angleLabel,angleField,wavelengthLabel,wavelengthField,distanceLabel,distanceField,yDisplacementLabel,yDisplacementField,orderLabel,orderField);

    }

    public static void addGraphics() {
        animationPaused=false;
        wall.setStartX(600);
        wall.setEndX(600);
        wall.setStartY(0);
        wall.setEndY(250);
        slitTop.setStartX(100);
        slitTop.setEndX(100);
        slitTop.setStartY(50);
        slitTop.setEndY(110);
        slitBot.setStartX(100);
        slitBot.setEndX(100);
        slitBot.setStartY(140);
        slitBot.setEndY(200);
        laserPointer.setX(25);
        laserPointer.setY(117);
        laserPointer.setFitWidth(50);
        laserPointer.setPreserveRatio(true);
        laserPointer.setCache(true);
        distance.setStartX(110);
        distance.setEndX(600);
        distance.setStartY(125);
        distance.setEndY(125);

        ClsStart.animationBox.getChildren().addAll(wall,slitTop,slitBot,laserPointer,distance);

    }

    public static void setAllData() {
        double slitwidth, angle, wavelength, dist, yDisplacement;
        int order;
        blankCount=6;
        if (!slitwidthField.getText().equals(empty)) {
            slitwidth=Double.parseDouble(slitwidthField.getText());
            ss.setSlitWidth(slitwidth);
            blankCount--;
        }
        if (!angleField.getText().equals(empty)) {
            angle=Double.parseDouble(angleField.getText());
            ss.setAngle(angle);
            blankCount--;
        }
        if (!wavelengthField.getText().equals(empty)) {
            wavelength=Double.parseDouble(wavelengthField.getText());
            ss.setWavelength(wavelength);
            blankCount--;
        }
        if (!distanceField.getText().equals(empty)) {
            dist=Double.parseDouble(distanceField.getText());
            ss.setDistance(dist);
            blankCount--;
        }
        if (!yDisplacementField.getText().equals(empty)) {
            yDisplacement=Double.parseDouble(yDisplacementField.getText());
            ss.setYDisplacement(yDisplacement);
            blankCount--;
        }
        if (!orderField.getText().equals(empty)) {
            order=Integer.parseInt(orderField.getText());
            ss.setOrder(order);
            blankCount--;
        }
    }

    public static boolean validate() {
        if (ss.getAngle()<0||ss.getSlitWidth()<0||ss.getWavelength()<0||ss.getDistance()<0||ss.getOrder()<0||ss.getYDisplacement()<0) {
            return false;
        } else if (ss.getAngle()>45) {
            return false;
        } else if (blankCount!=2) {
            return false;
        } else {
            return true;
        }
    }

    public static void calculateAllData() {
        setAllData();
        if (!validate()) {
            showInvalidWarning();
            resetAll();
            setAllData();
        } else {
            if (slitwidthField.getText().equals(empty)) {
                ss.calculateSlitWidth();
                slitwidthField.setText(Double.toString(ss.getSlitWidth()));
            } if (angleField.getText().equals(empty)) {
                ss.calculateAngle();
                angleField.setText(Double.toString(ss.getAngle()));
            } if (wavelengthField.getText().equals(empty)) {
                ss.calculateWavelength();
                wavelengthField.setText(Double.toString(ss.getWavelength()));
            } if (distanceField.getText().equals(empty)) {
                ss.calculateDistance();
                distanceField.setText(Double.toString(ss.getDistance()));
            } if (yDisplacementField.getText().equals(empty)) {
                ss.calculateYDisplacement();
                yDisplacementField.setText(Double.toString(ss.getYDisplacement()));
            } if (orderField.getText().equals(empty)) {
                ss.calculateOrder();
                orderField.setText(Integer.toString(ss.getOrder()));
            }
            slitwidthField.setEditable(false);
            angleField.setEditable(false);
            wavelengthField.setEditable(false);
            distanceField.setEditable(false);
            yDisplacementField.setEditable(false);
            orderField.setEditable(false);
        }
    }

    public static void animate() {

    }

    public static void pauseAnimation() {

    }

    public static void reverseAnimations() {
        resetAll();
    }

    public static void resetAll() {
        slitwidthField.setText(empty);
        slitwidthField.setEditable(true);
        angleField.setText(empty);
        angleField.setEditable(true);
        wavelengthField.setText(empty);
        wavelengthField.setEditable(true);
        distanceField.setText(empty);
        distanceField.setEditable(true);
        yDisplacementField.setText(empty);
        yDisplacementField.setEditable(true);
        orderField.setText(empty);
        orderField.setEditable(true);
        disableButton(ClsStart.start,false);
    }

    public static void showInvalidWarning() {
        Alert invalid=new Alert(AlertType.WARNING);
        invalid.setTitle("Warning");
        invalid.setHeaderText("Invalid input");
        invalid.setContentText("One or more variables are out of range or empty. \nClick OK to try again. ");
        invalid.showAndWait();
    }
}