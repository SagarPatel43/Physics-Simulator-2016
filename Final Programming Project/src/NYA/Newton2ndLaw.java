package NYA;

import MechanicsBean.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.util.*;
import main.*;
import static main.ClsStart.*;
import static main.ClsFunctions.*;

public class Newton2ndLaw implements PhysicsConstants {

    private static Newton2LawClass dyn=new Newton2LawClass();

    private static TextField forceField=new TextField();
    private static Label forceLabel=new Label("Force (N): ");
    private static TextField frictionField=new TextField();
    private static Label frictionLabel=new Label("Friction force (N): ");
    private static TextField massField=new TextField();
    private static Label massLabel=new Label("Mass: ");
    private static TextField accelerationField=new TextField();
    private static Label accelerationLabel=new Label("Acceleration: ");

    private static Line ground=new Line();
    private static Rectangle block=new Rectangle();
    private static Image lf=new Image("file:///E:/Programming/Vanier/420-204-RE/Final Project/src/NYA/leftarrow.png");
    private static ImageView leftForceArrow=new ImageView(lf);
    private static Image rf=new Image("file:///E:/Programming/Vanier/420-204-RE/Final Project/src/NYA/rightarrow.png");
    private static ImageView rightForceArrow=new ImageView(rf);
    private static Image lr=new Image("file:///E:/Programming/Vanier/420-204-RE/Final Project/src/NYA/redleftarrow.png");
    private static ImageView leftFrictionArrow=new ImageView(lr);
    private static Image rr=new Image("file:///E:/Programming/Vanier/420-204-RE/Final Project/src/NYA/redrightarrow.png");
    private static ImageView rightFrictionArrow=new ImageView(rr);

    private static NumberAxis timeAxis=new NumberAxis();
    private static NumberAxis velocityAxis=new NumberAxis();
    private static LineChart<Number,Number> velocityVsTime=new LineChart<Number,Number>(timeAxis,velocityAxis);

    private static TranslateTransition translateRightArrow=new TranslateTransition();
    private static TranslateTransition translateLeftArrow=new TranslateTransition();

    private static ScaleTransition scaleRightArrow=new ScaleTransition();
    private static ScaleTransition scaleLeftArrow=new ScaleTransition();

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
                helpMessage+="In addition to that, user can only leave one field blank and mass cannot be negative.";
                showHelpDialog();
            }
        });
    }

    public static void addTextFields() {
        textFieldBox.setConstraints(forceLabel,0,0);
        textFieldBox.setConstraints(forceField,1,0);
        textFieldBox.setConstraints(frictionLabel,0,1);
        textFieldBox.setConstraints(frictionField,1,1);
        textFieldBox.setConstraints(massLabel,0,2);
        textFieldBox.setConstraints(massField,1,2);
        textFieldBox.setConstraints(accelerationLabel,0,3);
        textFieldBox.setConstraints(accelerationField,1,3);
        textFieldBox.getChildren().addAll(forceLabel,forceField,frictionLabel,frictionField,massLabel,massField,accelerationLabel,accelerationField);
    }

    public static void addGraphics() {
        animationPaused=false;
        ground.setStartX(0);
        ground.setEndX(950);
        ground.setStartY(250);
        ground.setEndY(250);
        block.setX(425);
        block.setY(150);
        block.setWidth(100);
        block.setHeight(100);
        block.setFill(Color.BROWN);
        animationBox.getChildren().addAll(ground,block);

        timeAxis.setLabel("Time (s)");
        velocityAxis.setLabel("Velocity (m/s)");
        velocityVsTime.setTitle("Velocity vs. time");
        velocityVsTime.setPrefHeight(250);
        velocityVsTime.setPrefWidth(250);
        velocityVsTime.setCreateSymbols(false);
        graphBox1.getChildren().add(velocityVsTime);
    }

    public static void setAllData() {
        double mass, force, frictionForce, acceleration;
        blankCount=4;
        if (!massField.getText().equals(empty)) {
            mass=Double.parseDouble(massField.getText());
            dyn.setMass(mass);
            blankCount--;
        }
        if (!forceField.getText().equals(empty)) {
            force=Double.parseDouble(forceField.getText());
            dyn.setForce(force);
            blankCount--;
        }
        if (!frictionField.getText().equals(empty)) {
            frictionForce=Double.parseDouble(frictionField.getText());
            dyn.setFrictionForce(frictionForce);
            blankCount--;
        }
        if (!accelerationField.getText().equals(empty)) {
            acceleration=Double.parseDouble(accelerationField.getText());
            dyn.setAcceleration(acceleration);
            blankCount--;
        }
        if (!forceField.getText().equals(empty)||!frictionField.getText().equals(empty)) {
            dyn.calculateNetForce();
        }
    }

    public static boolean validate() {
        if (dyn.getMass()<0) {
            return false;
        } else if (blankCount!=1) {
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
            if (forceField.getText().equals(empty)) {
                dyn.calculateNetForceWithUndefinedForce();
                dyn.getForceFromFriction();
                forceField.setText(Double.toString(dyn.getForce()));
            } else if (frictionField.getText().equals(empty)) {
                dyn.calculateNetForceWithUndefinedForce();
                dyn.getFrictionFromForce();
                frictionField.setText(Double.toString(dyn.getFrictionForce()));
            } else if (massField.getText().equals(empty)) {
                dyn.calculateMass();
                massField.setText(Double.toString(dyn.getMass()));
            } else if (accelerationField.getText().equals(empty)) {
                dyn.calculateAcceleration();
                accelerationField.setText(Double.toString(dyn.getAcceleration()));
            }
            forceField.setEditable(false);
            frictionField.setEditable(false);
            massField.setEditable(false);
            accelerationField.setEditable(false);
            disableMenus();
            animate();
            graph();
        }
    }

    public static void animate() {
        leftForceArrow.setX(block.getX()-200);
        leftForceArrow.setY(155);
        leftForceArrow.setFitWidth(200);
        leftForceArrow.setPreserveRatio(true);
        leftForceArrow.setCache(true);
        leftFrictionArrow.setX(block.getX()-200);
        leftFrictionArrow.setY(205);
        leftFrictionArrow.setFitWidth(200);
        leftFrictionArrow.setPreserveRatio(true);
        leftFrictionArrow.setCache(true);
        rightForceArrow.setX(block.getX()+block.getWidth());
        rightForceArrow.setY(155);
        rightForceArrow.setFitWidth(200);
        rightForceArrow.setPreserveRatio(true);
        rightForceArrow.setCache(true);
        rightFrictionArrow.setX(block.getX()+block.getWidth());
        rightFrictionArrow.setY(205);
        rightFrictionArrow.setFitWidth(200);
        rightFrictionArrow.setPreserveRatio(true);
        rightFrictionArrow.setCache(true);

        double accelDuration=Math.abs(0.5/dyn.getAcceleration());
        if (accelDuration>5) {
            accelDuration=5;
        } else if (accelDuration<0.25) {
            accelDuration=0.25;
        }

        double forceScale=(dyn.getForce()/20), frictionScale=(dyn.getFrictionForce()/20);
        if (forceScale>2) {
            forceScale=2;
        } else if (forceScale<0.2) {
            forceScale=0.2;
        }
        if (frictionScale>2) {
            frictionScale=2;
        } else if (frictionScale<0.2) {
            frictionScale=0.2;
        }

        TranslateTransition blockTransition=new TranslateTransition(Duration.seconds(accelDuration),block);

        translateRightArrow.setDuration(Duration.seconds(accelDuration));
        translateLeftArrow.setDuration(Duration.seconds(accelDuration));
        scaleRightArrow.setDuration(Duration.seconds(0.25));
        scaleLeftArrow.setDuration(Duration.seconds(0.25));

        if (dyn.getForce()>0) {
            animationBox.getChildren().add(rightForceArrow);
            translateRightArrow.setNode(rightForceArrow);
            scaleRightArrow.setNode(rightForceArrow);
            scaleRightArrow.setToX(forceScale);
        } else if (dyn.getForce()<0) {
            animationBox.getChildren().add(leftForceArrow);
            translateLeftArrow.setNode(leftForceArrow);
            scaleLeftArrow.setNode(leftForceArrow);
            scaleLeftArrow.setToX(forceScale);
        }
        if (dyn.getFrictionForce()>0) {
            animationBox.getChildren().add(leftFrictionArrow);
            translateLeftArrow.setNode(leftFrictionArrow);
            scaleLeftArrow.setNode(leftFrictionArrow);
            scaleLeftArrow.setToX(frictionScale);
        } else if (dyn.getFrictionForce()<0) {
            animationBox.getChildren().add(rightFrictionArrow);
            translateRightArrow.setNode(rightFrictionArrow);
            scaleRightArrow.setNode(rightFrictionArrow);
            scaleRightArrow.setToX(frictionScale);
        }
        if (dyn.getAcceleration()<0) {
            blockTransition.setToX(-200*1.5-block.getX()-block.getWidth());
            translateRightArrow.setToX(-200*1.5-block.getX()-block.getWidth());
            translateLeftArrow.setToX(-200*1.5-block.getX()-block.getWidth());
        } else if (dyn.getAcceleration()>0) {
            blockTransition.setToX(950-block.getX()+200*1.5);
            translateRightArrow.setToX(950-block.getX()+200*1.5);
            translateLeftArrow.setToX(950-block.getX()+200*1.5);
        }

        blockTransition.setCycleCount(One);
        blockTransition.setAutoReverse(false);
        translateRightArrow.setCycleCount(One);
        translateRightArrow.setAutoReverse(false);
        translateLeftArrow.setCycleCount(One);
        translateLeftArrow.setAutoReverse(false);
        scaleLeftArrow.setCycleCount(One);
        scaleLeftArrow.setAutoReverse(false);
        scaleRightArrow.setCycleCount(One);
        scaleRightArrow.setAutoReverse(false);

        ParallelTransition scaleBothArrows=new ParallelTransition();
        scaleBothArrows.getChildren().addAll(scaleLeftArrow,scaleRightArrow);

        ParallelTransition translateEverything=new ParallelTransition();
        translateEverything.getChildren().addAll(blockTransition,translateRightArrow,translateLeftArrow);

        allAnimations.getChildren().addAll(scaleBothArrows,translateEverything);
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

        TranslateTransition reverseBlock=new TranslateTransition(Duration.seconds(ReverseDuration),block);
        TranslateTransition reverseLeftArrow=new TranslateTransition(Duration.seconds(ReverseDuration),translateLeftArrow.getNode());
        TranslateTransition reverseRightArrow=new TranslateTransition(Duration.seconds(ReverseDuration),translateRightArrow.getNode());
        ScaleTransition resetLeftScale=new ScaleTransition(Duration.seconds(ReverseDuration),scaleLeftArrow.getNode());
        ScaleTransition resetRightScale=new ScaleTransition(Duration.seconds(ReverseDuration),scaleRightArrow.getNode());

        reverseBlock.setToX(Zero);
        reverseBlock.setCycleCount(One);
        reverseBlock.setAutoReverse(false);
        reverseLeftArrow.setToX(Zero);
        reverseLeftArrow.setCycleCount(One);
        reverseLeftArrow.setAutoReverse(false);
        reverseRightArrow.setToX(Zero);
        reverseRightArrow.setCycleCount(One);
        reverseRightArrow.setAutoReverse(false);
        resetLeftScale.setToX(One);
        resetLeftScale.setCycleCount(One);
        resetLeftScale.setAutoReverse(false);
        resetRightScale.setToX(One);
        resetRightScale.setCycleCount(One);
        resetRightScale.setAutoReverse(false);

        ParallelTransition reverseAll=new ParallelTransition();
        reverseAll.getChildren().addAll(resetLeftScale,resetRightScale,reverseRightArrow,reverseLeftArrow,reverseBlock);
        animationBox.getChildren().removeAll(leftForceArrow,leftFrictionArrow,rightForceArrow,rightFrictionArrow);
        reverseAll.playFromStart();
        reverseAll.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetAll();
            }
        });
    }

    public static void graph() {
        XYChart.Series series=new XYChart.Series();
        series.setName("Velocity vs. time");
        for (double t=Zero; t<=10; t++) {
            series.getData().add(new XYChart.Data(t,dyn.getVelocityOverTime(t,dyn.getAcceleration())));
        }
        velocityVsTime.getData().add(series);
    }

    public static void resetAll() {
        animationPaused=false;
        velocityVsTime.getData().clear();
        forceField.setText(empty);
        forceField.setEditable(true);
        frictionField.setText(empty);
        frictionField.setEditable(true);
        massField.setText(empty);
        massField.setEditable(true);
        accelerationField.setText(empty);
        accelerationField.setEditable(true);
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
        invalid.setContentText("One or more variables are out of range or empty.\nClick OK to try again.");
        invalid.showAndWait();
    }
}