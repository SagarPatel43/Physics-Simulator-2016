package NYC;

import javafx.event.*;
import WavesBean.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import main.*;
import static main.ClsFunctions.*;
import javafx.animation.*;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.*;
import javafx.util.*;
import static main.ClsStart.*;


public class SimpleHarmonicMotion implements PhysicsConstants {

    private static SHMClass shm=new SHMClass();

    private static TextField amplitudeField=new TextField();
    private static Label amplitudeLabel=new Label("Amplitude (m): ");
    private static TextField phaseconstantField=new TextField();
    private static Label phaseconstantLabel=new Label("Phase Constant: ");
    private static TextField frequencyField=new TextField();
    private static Label frequencyLabel=new Label("Frequency (Hz): ");
    private static TextField periodField=new TextField();
    private static Label periodLabel=new Label("Period (s): ");
    private static TextField angularvelocityField=new TextField();
    private static Label angularvelocityLabel=new Label("Angular Velocity (rad/s): ");
    
    private static NumberAxis timeAxis = new NumberAxis();
    private static NumberAxis amplitudeAxis = new NumberAxis();
    private static LineChart<Number,Number> amplitudeVSTime = new LineChart<Number,Number>(timeAxis,amplitudeAxis);
    
    private static Line wall=new Line();
    private static Rectangle block=new Rectangle();

    private static Image s=new Image("file:///D:Spring.jpg");
    private static ImageView spring=new ImageView(s);

    
    
    private static SequentialTransition allTranslate = new SequentialTransition();
    private static SequentialTransition allScale = new SequentialTransition();
   
    
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
                    allTranslate.play();
                    allScale.play();
                } else {
                    calculateAllData();
                    animate();
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
        ClsStart.textFieldBox.setConstraints(amplitudeLabel, 0, 0);
        ClsStart.textFieldBox.setConstraints(amplitudeField, 1, 0);
        ClsStart.textFieldBox.setConstraints(phaseconstantLabel, 0, 1);
        ClsStart.textFieldBox.setConstraints(phaseconstantField, 1, 1);
        ClsStart.textFieldBox.setConstraints(frequencyLabel, 0, 2);
        ClsStart.textFieldBox.setConstraints(frequencyField, 1, 2);
        ClsStart.textFieldBox.setConstraints(periodLabel, 0, 3);
        ClsStart.textFieldBox.setConstraints(periodField, 1, 3);
        ClsStart.textFieldBox.setConstraints(angularvelocityLabel, 0, 4);
        ClsStart.textFieldBox.setConstraints(angularvelocityField, 1, 4);
        ClsStart.textFieldBox.getChildren().addAll(amplitudeLabel, amplitudeField, phaseconstantLabel, phaseconstantField, frequencyLabel, frequencyField, periodLabel, periodField, angularvelocityLabel, angularvelocityField);
    }
    public static void addGraphics(){
        animationPaused=false;
        wall.setStartX(0);
        wall.setEndX(0);
        wall.setStartY(0);
        wall.setEndY(250);
        block.setX(425);
        block.setY(75);
        block.setWidth(100);
        block.setHeight(100);
        block.setFill(Color.BROWN);
        
        
        spring.setY(75);
        spring.setPreserveRatio(true);
        spring.setCache(true);
        ClsStart.animationBox.getChildren().addAll(wall, spring, block);
        
        timeAxis.setLabel("Time (s)");
        amplitudeAxis.setLabel("Amplitude (m)");
        amplitudeVSTime.setTitle("Amplitude vs Time");
        amplitudeVSTime.setPrefHeight(250);
        amplitudeVSTime.setPrefWidth(250);
        amplitudeVSTime.setCreateSymbols(false);
        graphBox1.getChildren().add(amplitudeVSTime);
        
        
        
    }
    public static void setAllData() {
        double amplitude, phaseconstant, frequency, period, angularvelocity;
        blankCount=5;
        if (!amplitudeField.getText().equals(empty)) {
            amplitude=Double.parseDouble(amplitudeField.getText());
            shm.setAmplitude(amplitude);
            blankCount--;
        }
        if (!phaseconstantField.getText().equals(empty)) {
            phaseconstant=Double.parseDouble(phaseconstantField.getText());
            shm.setPhaseConstant(phaseconstant);
            blankCount--;
        }
        if (!frequencyField.getText().equals(empty)) {
            frequency=Double.parseDouble(frequencyField.getText());
            shm.setFrequency(frequency);
            blankCount--;
        }
        if (!periodField.getText().equals(empty)) {
            period=Double.parseDouble(periodField.getText());
            shm.setPeriod(period);
            blankCount--;
        }

        if (!angularvelocityField.getText().equals(empty)) {
            angularvelocity=Double.parseDouble(angularvelocityField.getText());
            shm.setAngularVelocity(angularvelocity);
            blankCount--;
        }
    }
    
    public static boolean validate() {
        if (blankCount==0||blankCount>3) {
            return false;
        } else if (shm.getAmplitude()<0||shm.getFrequency()<0||shm.getPeriod()<0) {
            return false;
        } else if (frequencyField.getText().equals(empty)&&angularvelocityField.getText().equals(empty)&&periodField.getText().equals(empty)) {
            return false;
        } else if (!frequencyField.getText().equals(empty)&&!angularvelocityField.getText().equals(empty)&&!periodField.getText().equals(empty)) {
            return false;
        } else if (!frequencyField.getText().equals(empty)&&!angularvelocityField.getText().equals(empty)&&periodField.getText().equals(empty)) {
            return false;
        } else if (!frequencyField.getText().equals(empty)&&angularvelocityField.getText().equals(empty)&&!periodField.getText().equals(empty)) {
            return false;
        } else if (frequencyField.getText().equals(empty)&&!angularvelocityField.getText().equals(empty)&&!periodField.getText().equals(empty)) {
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
            if (phaseconstantField.getText().equals(empty)) {
                showPhaseConstantZeroWarning();
                shm.setPhaseConstant(0);
            } else if (frequencyField.getText().equals(empty)) {
                shm.calculateFrequency();
                frequencyField.setText(Double.toString(shm.getFrequency()));
            } else if (periodField.getText().equals(empty)) {
                shm.calculatePeriod();
                periodField.setText(Double.toString(shm.getPeriod()));
            } else if (angularvelocityField.getText().equals(empty)) {
                shm.calculateAngularVelocity();
                angularvelocityField.setText(Double.toString(shm.getAngularVelocity()));
            }
            phaseconstantField.setEditable(false);
            frequencyField.setEditable(false);
            periodField.setEditable(false);
            amplitudeField.setEditable(false);
            angularvelocityField.setEditable(false);
        }
    }

    public static void animate() {
        double period = shm.getPeriod();
        double amplitude = shm.getAmplitude();
        
        TranslateTransition blockTranslation1=new TranslateTransition(Duration.seconds(period/4),block);
        TranslateTransition blockTranslation2=new TranslateTransition(Duration.seconds(period/2),block);
        TranslateTransition blockTranslation3=new TranslateTransition(Duration.seconds(period/4),block);
        
        ScaleTransition springTransition1 = new ScaleTransition(Duration.seconds(period/4),spring);
        ScaleTransition springTransition2 = new ScaleTransition(Duration.seconds(period/2),spring);
        ScaleTransition springTransition3 = new ScaleTransition(Duration.seconds(period/4),spring);
        
        springTransition1.setToX(40*amplitude);
        springTransition2.setToX(-40*amplitude);
        springTransition3.setToX(0);
        
        springTransition1.setCycleCount(1);
        springTransition1.setAutoReverse(false);
        springTransition2.setCycleCount(1);
        springTransition2.setAutoReverse(false);
        springTransition3.setCycleCount(1);
        springTransition3.setAutoReverse(false);
        
     
        blockTranslation1.setToX(40*amplitude);
        blockTranslation2.setToX(-40*amplitude);
        blockTranslation3.setToX(0);
        
        blockTranslation1.setCycleCount(1);
        blockTranslation1.setAutoReverse(false);
        blockTranslation2.setCycleCount(1);
        blockTranslation2.setAutoReverse(false);
        blockTranslation3.setCycleCount(1);
        blockTranslation3.setAutoReverse(false);
        
        allTranslate.getChildren().addAll(blockTranslation1,blockTranslation2,blockTranslation3);
        allTranslate.playFromStart();
        
        allScale.getChildren().addAll(springTransition1,springTransition2,springTransition3);
        allScale.playFromStart();
        
        
        
        
    }

    public static void resetAnimation() {
        resetAll();
    }
    public static void pauseAnimation(){
        animationPaused=true;
        allTranslate.pause();
        allScale.pause();
    }
    public static void reverseAnimations(){
        allTranslate.stop();
        allTranslate.getChildren().clear();
        allScale.stop();
        allScale.getChildren().clear();
        
        TranslateTransition resetBlock= new TranslateTransition(Duration.seconds(1),block);
        resetBlock.setToX(0);
        resetBlock.setCycleCount(1);
        resetBlock.setAutoReverse(false);
        
        ScaleTransition resetSpring = new ScaleTransition(Duration.seconds(1),spring);
        resetSpring.setToX(0);
        resetSpring.setCycleCount(1);
        resetSpring.setAutoReverse(false);
        
        ParallelTransition reverseAll = new ParallelTransition();
        reverseAll.getChildren().addAll(resetBlock,resetSpring);
        reverseAll.playFromStart();
        reverseAll.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                resetAll();
            }
        });
        
        
        
        
    }
    public static void graph() {
        XYChart.Series series = new XYChart.Series();
        series.setName("Amplitude vs time");
        for( double t=Zero; t<=10; t++){
            series.getData().add(new XYChart.Data(t,shm.getAmplitude()));
    }
    }

    public static void resetAll() {
        phaseconstantField.setText(empty);
        phaseconstantField.setEditable(true);
        frequencyField.setText(empty);
        frequencyField.setEditable(true);
        periodField.setText(empty);
        periodField.setEditable(true);
        amplitudeField.setText(empty);
        amplitudeField.setEditable(true);
        angularvelocityField.setText(empty);
        angularvelocityField.setEditable(true);
        disableButton(ClsStart.start, false);
    }

    public static void showInvalidWarning() {
        Alert invalid=new Alert(AlertType.WARNING);
        invalid.setTitle("Warning");
        invalid.setHeaderText("Invalid input");
        invalid.setContentText("One or more variables are out of range or empty. \nClick OK to try again. ");
        invalid.showAndWait();
    }

    public static void showPhaseConstantZeroWarning() {
        Alert pc0=new Alert(AlertType.WARNING);
        pc0.setTitle("Warning");
        pc0.setHeaderText("Phase constant field left blank");
        pc0.setContentText("Click OK to assume zero phase constant");
        pc0.showAndWait();
    }
}