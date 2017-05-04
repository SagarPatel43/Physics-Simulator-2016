package NYA;

import MechanicsBean.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.util.*;
import main.*;
import static main.ClsStart.*;
import static main.ClsFunctions.*;

public class ProjectileMotion implements PhysicsConstants {

    private static ProjMotionClass p=new ProjMotionClass();

    private static TextField heightField=new TextField();
    private static Label heightLabel=new Label("Height (m): ");
    private static TextField ivMagField=new TextField();
    private static Label ivMagLabel=new Label("Initial velocity magnitude (m/s): ");
    private static TextField ivDirField=new TextField();
    private static Label ivDirLabel=new Label("Initial velocity direction (°): ");
    private static TextField fvMagField=new TextField();
    private static Label fvMagLabel=new Label("Final velocity magnitude (m/s): ");
    private static TextField fvDirField=new TextField();
    private static Label fvDirLabel=new Label("Final velocity direction (°): ");
    private static TextField xDisField=new TextField();
    private static Label xDisLabel=new Label("Total X-displacement (m): ");
    private static TextField timeField=new TextField();
    private static Label timeLabel=new Label("Time (s): ");

    private static Line ground=new Line();
    private static Rectangle platform=new Rectangle();
    private static Circle object=new Circle();

    private static NumberAxis timeAxis1=new NumberAxis();
    private static NumberAxis heightAxis=new NumberAxis();
    private static LineChart<Number,Number> heightVsTime=new LineChart<Number,Number>(timeAxis1,heightAxis);

    private static NumberAxis timeAxis2=new NumberAxis();
    private static NumberAxis velocityMagnitudeAxis=new NumberAxis();
    private static LineChart<Number,Number> velocityMagnitudeVsTime=new LineChart<Number,Number>(timeAxis2,velocityMagnitudeAxis);

    private static SequentialTransition allAnimations=new SequentialTransition();

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
                    allAnimations.play();
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
                helpMessage+="\nFor the entry to be valid, user cannot leave height blank.\n"
                        +"User must input both magnitude and direction for either initial or final velocity, but not both velocities, either magnitude or time, or neither.\n"
                        +"X-displacement and time cannot be inputted and are only calculated.\n"
                        +"The direction must be between 0 and 90 degrees.\n"
                        +"Height and magnitudes must all be positive.";
                showHelpDialog();
            }
        });
    }

    public static void addTextFields() {
        textFieldBox.setConstraints(heightLabel,0,0);
        textFieldBox.setConstraints(heightField,1,0);
        textFieldBox.setConstraints(ivMagLabel,0,1);
        textFieldBox.setConstraints(ivMagField,1,1);
        textFieldBox.setConstraints(ivDirLabel,0,2);
        textFieldBox.setConstraints(ivDirField,1,2);
        textFieldBox.setConstraints(fvMagLabel,0,3);
        textFieldBox.setConstraints(fvMagField,1,3);
        textFieldBox.setConstraints(fvDirLabel,0,4);
        textFieldBox.setConstraints(fvDirField,1,4);
        textFieldBox.setConstraints(xDisLabel,0,5);
        textFieldBox.setConstraints(xDisField,1,5);
        textFieldBox.setConstraints(timeLabel,0,6);
        textFieldBox.setConstraints(timeField,1,6);
        xDisField.setEditable(false);
        timeField.setEditable(false);
        textFieldBox.getChildren().addAll(heightLabel,heightField,ivMagLabel,ivMagField,ivDirLabel,ivDirField,fvMagLabel,fvMagField,fvDirLabel,fvDirField,xDisLabel,xDisField,timeLabel,timeField);
    }

    public static void addGraphics() {
        animationPaused=false;
        ground.setStartX(0);
        ground.setEndX(950);
        ground.setStartY(325);
        ground.setEndY(325);
        platform.setX(0);
        platform.setY(180);
        platform.setWidth(100);
        platform.setHeight(20);
        platform.setFill(Color.GRAY);
        object.setCenterX(platform.getWidth()-10);
        object.setCenterY(platform.getY()-10);
        object.setRadius(10);
        object.setFill(Color.RED);
        animationBox.getChildren().addAll(ground,platform,object);

        timeAxis1.setLabel("Time (s)");
        heightAxis.setLabel("Height (m)");
        heightVsTime.setTitle("Height vs. time");
        heightVsTime.setPrefHeight(250);
        heightVsTime.setPrefWidth(250);
        heightVsTime.setCreateSymbols(false);
        graphBox1.getChildren().add(heightVsTime);

        timeAxis2.setLabel("Time (s)");
        velocityMagnitudeAxis.setLabel("Velocity magnitude (m/s)");
        velocityMagnitudeVsTime.setTitle("Velocity magnitude vs. time");
        velocityMagnitudeVsTime.setPrefHeight(250);
        velocityMagnitudeVsTime.setPrefWidth(250);
        velocityMagnitudeVsTime.setCreateSymbols(false);
        graphBox2.getChildren().add(velocityMagnitudeVsTime);
    }

    public static void setAllData() {
        double height, viMagnitude, viDirection, vfMagnitude, vfDirection;
        blankCount=3;
        if (!heightField.getText().equals(empty)) {
            height=Double.parseDouble(heightField.getText());
            p.setInitialHeight(height);
            blankCount--;
        }
        if (!ivMagField.getText().equals(empty)&&!ivDirField.getText().equals(empty)) {
            viMagnitude=Double.parseDouble(ivMagField.getText());
            viDirection=Double.parseDouble(ivDirField.getText());
            p.setInitialVelocityMagnitude(viMagnitude);
            p.setInitialVelocityAngle(viDirection);
            p.setXVelocity(p.getInitialVelocityMagnitude(),p.getInitialVelocityAngle());
            p.setInitialYVelocity(p.getInitialVelocityMagnitude(),p.getInitialVelocityAngle());
            blankCount--;
        }
        if (!fvMagField.getText().equals(empty)&&!fvDirField.getText().equals(empty)) {
            vfMagnitude=Double.parseDouble(fvMagField.getText());
            vfDirection=Double.parseDouble(fvDirField.getText());
            p.setFinalVelocityMagnitude(vfMagnitude);
            p.setFinalVelocityAngle(-1*vfDirection);
            p.setXVelocity(p.getFinalVelocityMagnitude(),p.getFinalVelocityAngle());
            p.setFinalYVelocity(p.getFinalVelocityMagnitude(),p.getFinalVelocityAngle());
            blankCount--;
        }
    }

    public static boolean validate() {
        if (blankCount==0||blankCount==3) {
            return false;
        } else if (p.getInitialVelocityMagnitude()<0||p.getFinalVelocityMagnitude()<0||p.getTime()<0||p.getXDisplacement()<0) {
            return false;
        } else if (p.getInitialVelocityAngle()<0||p.getInitialVelocityAngle()>90||Math.abs(p.getFinalVelocityAngle())<0||Math.abs(p.getFinalVelocityAngle())>90) {
            return false;
        } else if (heightField.getText().equals(empty)) {
            return false;
        } else if (ivMagField.getText().equals(empty)^ivDirField.getText().equals(empty)) {
            return false;
        } else if (fvMagField.getText().equals(empty)^fvDirField.getText().equals(empty)) {
            return false;
        } else if (ivMagField.getText().equals(empty)&&ivDirField.getText().equals(empty)&&fvMagField.getText().equals(empty)&&fvDirField.getText().equals(empty)) {
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
            if (ivMagField.getText().equals(empty)&&ivDirField.getText().equals(empty)) {
                p.calculateInitialYVelocity();
                p.calculateInitialVelocityMagnitude();
                p.calculateInitialVelocityAngle();
                ivMagField.setText(Double.toString(p.getInitialVelocityMagnitude()));
                ivDirField.setText(Double.toString(p.getInitialVelocityAngle()));
            }
            if (fvMagField.getText().equals(empty)&&fvDirField.getText().equals(empty)) {
                p.calculateFinalYVelocity();
                p.calculateFinalVelocityMagnitude();
                p.calculateFinalVelocityAngle();
                fvMagField.setText(Double.toString(p.getFinalVelocityMagnitude()));
                fvDirField.setText(Double.toString(-1*p.getFinalVelocityAngle()));
            }
            if (timeField.getText().equals(empty)) {
                p.calculateTime();
                timeField.setText(Double.toString(p.getTime()));
            }
            if (xDisField.getText().equals(empty)) {
                p.calculateXDisplacement();
                xDisField.setText(Double.toString(p.getXDisplacement()));
            }
            heightField.setEditable(false);
            ivMagField.setEditable(false);
            ivDirField.setEditable(false);
            fvMagField.setEditable(false);
            fvDirField.setEditable(false);
            disableMenus();
            animate();
            graph();
        }
    }

    public static void animate() {
        TranslateTransition translatePlatform=new TranslateTransition(Duration.seconds(0.25),platform);
        TranslateTransition translateObjectAcctoPlatform=new TranslateTransition(Duration.seconds(0.25),object);
        
        double toY=-1*p.getInitialHeight()+60;
        if (toY<-120) {
            toY=-120;
        } else if (toY>120) {
            toY=120;
        }
        translatePlatform.setToY(toY);
        translatePlatform.setCycleCount(One);
        translatePlatform.setAutoReverse(false);
        translateObjectAcctoPlatform.setToY(toY);
        translateObjectAcctoPlatform.setCycleCount(One);
        translateObjectAcctoPlatform.setAutoReverse(false);
        
        ParallelTransition translateAcctoHeight=new ParallelTransition();
        translateAcctoHeight.getChildren().addAll(translatePlatform,translateObjectAcctoPlatform);
        allAnimations.getChildren().add(translateAcctoHeight);
        
        double ctrlX=object.getCenterX(), ctrlY=object.getCenterY(), endX=object.getCenterX();
        if (p.getInitialVelocityAngle()==Zero) {
            ctrlX+=10*p.getXVelocity();
            endX+=20*p.getXVelocity();
        } else if (p.getInitialVelocityAngle()==90) {
            ctrlY-=10*p.getInitialYVelocity();
        } else {
            ctrlX+=10*p.getXVelocity();
            ctrlY-=10*p.getInitialYVelocity();
            endX+=20*p.getXVelocity();
        }
        if (ctrlX>480) {
            ctrlX=480;
        }
        if (ctrlY<Zero) {
            ctrlY=Zero;
        }
        if (endX>940) {
            endX=940;
        }

        double duration=0.5*p.getTime();
        if (duration>10) {
            duration=10;
        } else if (duration<0.5) {
            duration=0.5;
        }
        
        Path path=new Path();
        path.getElements().add(new MoveTo(object.getCenterX(),object.getCenterY()+toY));
        path.getElements().add(new QuadCurveTo(ctrlX,ctrlY,endX,ground.getEndY()-object.getRadius()-2));
        
        PathTransition translateObject=new PathTransition(Duration.seconds(duration),path,object);
        
        translateObject.setCycleCount(One);
        translateObject.setAutoReverse(false);

        allAnimations.getChildren().add(translateObject);
        allAnimations.playFromStart();
        allAnimations.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showFinishDialog();
                disableButton(start,true);
                disableButton(pause,true);
                disableButton(help,true);
            }
        });

    }

    public static void pauseAnimation() {
        animationPaused=true;
        allAnimations.pause();
    }

    public static void reverseAnimations() {
        allAnimations.stop();
        allAnimations.getChildren().clear();

        TranslateTransition reversePlatform=new TranslateTransition(Duration.seconds(ReverseDuration),platform);
        TranslateTransition reverseObject=new TranslateTransition(Duration.seconds(ReverseDuration),object);
        
        reversePlatform.setToY(Zero);
        reversePlatform.setCycleCount(One);
        reversePlatform.setAutoReverse(false);
        reverseObject.setToX(Zero);
        reverseObject.setToY(Zero);
        reverseObject.setCycleCount(One);
        reverseObject.setAutoReverse(false);

        ParallelTransition reverseAll=new ParallelTransition();
        reverseAll.getChildren().addAll(reversePlatform,reverseObject);
        reverseAll.playFromStart();
        reverseAll.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetAll();
            }
        });
    }

    public static void graph() {
        XYChart.Series series1=new XYChart.Series();
        series1.setName("Height vs. time");
        for (double t=Zero; t<=p.getTime(); t+=.1) {
            series1.getData().add(new XYChart.Data(t,p.getHeightOverTime(t,p.getInitialYVelocity(),p.getInitialHeight())));
        }
        heightVsTime.getData().add(series1);
        XYChart.Series series2=new XYChart.Series();
        series2.setName("Velocity magnitude vs. time");
        for (double t=Zero; t<=p.getTime(); t+=.1) {
            series2.getData().add(new XYChart.Data(t,p.getVelocityMagnitudeOverTime(t,p.getXVelocity(),p.getInitialYVelocity())));
        }
        velocityMagnitudeVsTime.getData().add(series2);
    }

    public static void resetAll() {
        heightVsTime.getData().clear();
        velocityMagnitudeVsTime.getData().clear();
        heightField.setText(empty);
        heightField.setEditable(true);
        ivMagField.setText(empty);
        ivMagField.setEditable(true);
        ivDirField.setText(empty);
        ivDirField.setEditable(true);
        fvMagField.setText(empty);
        fvMagField.setEditable(true);
        fvDirField.setText(empty);
        fvDirField.setEditable(true);
        xDisField.setText(empty);
        timeField.setText(empty);
        disableButton(start,false);
        disableButton(pause,true);
        disableButton(help,false);
        enableMenus();
    }

    public static void showFinishDialog() {
        Alert end=new Alert(AlertType.INFORMATION);
        end.setTitle("End");
        end.setHeaderText("Animation finished");
        end.setContentText("Click OK to close this dialog,\nthen click Done to choose another topic, or click Reset to remain in this topic and enter new values.");
        end.show();
    }

    public static void showInvalidWarning() {
        Alert invalid=new Alert(AlertType.WARNING);
        invalid.setTitle("Warning");
        invalid.setHeaderText("Invalid input");
        invalid.setContentText("One or more variables are out of range or empty. \nClick OK to try again.");
        invalid.showAndWait();
    }
}