package NYB;

import ENMBean.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.image.*;
import main.*;
import static main.ClsFunctions.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.util.Duration;
import static main.ClsStart.graphBox1;
import static main.PhysicsConstants.One;
import static main.PhysicsConstants.Zero;
import static main.PhysicsConstants.empty;

public class OhmLaw {

    private static OhmClass oc = new OhmClass();

    private static TextField voltageField = new TextField();
    private static Label voltageLabel = new Label("Potential difference (V): ");
    private static TextField currentField = new TextField();
    private static Label currentLabel = new Label("Current (A): ");
    private static TextField resistanceField = new TextField();
    private static Label resistanceLabel = new Label("Resistance (Ω): ");

    private static Image r = new Image("file:///C:/Users/sagar/Desktop/Final Programming Project/src/NYB/resistor.png");
    private static Image b = new Image("file:///C:/Users/sagar/Desktop/Final Programming Project/src/NYB/battery.png");
    private static Image e = new Image("file:///C:/Users/sagar/Desktop/Final Programming Project/src/NYB/electron.png");

    private static ImageView resistor = new ImageView(r);
    private static ImageView battery = new ImageView(b);
    private static ImageView electron = new ImageView(e);

    private static Rectangle circuit = new Rectangle();

    private static Text bat = new Text("Battery");
    private static Text res = new Text("Resistor");

    private static SequentialTransition eTrans = new SequentialTransition();
    private static ParallelTransition scalePar = new ParallelTransition();

    private static TranslateTransition translateRight = new TranslateTransition();
    private static TranslateTransition translateDown = new TranslateTransition();
    private static TranslateTransition translateLeft = new TranslateTransition();
    private static TranslateTransition translateLeftFast = new TranslateTransition();
    private static TranslateTransition translateLeft2 = new TranslateTransition();
    private static TranslateTransition translateUp = new TranslateTransition();
    private static TranslateTransition translateRight2 = new TranslateTransition();

    private static ScaleTransition batScale = new ScaleTransition();
    private static ScaleTransition resScale = new ScaleTransition();

    private static NumberAxis currentAxis = new NumberAxis();
    private static NumberAxis voltageAxis = new NumberAxis();
    private static LineChart<Number, Number> ohmLaw = new LineChart<Number, Number>(currentAxis, voltageAxis);

    private static int blankCount;

    private static boolean animationPaused;

    public static void buttonMethods() {
        ClsStart.start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                disableButton(ClsStart.start, true);
                disableButton(ClsStart.pause, false);
                if (animationPaused) {
                    animationPaused = false;
                    scalePar.play();
                } else {
                    calculateAllData();
                }
            }
        });
        ClsStart.done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                ClsFunctions.doneButtonMethod();
            }
        });
        ClsStart.pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                pauseAnimation();
                ClsFunctions.disableButton(ClsStart.start, false);
                ClsFunctions.disableButton(ClsStart.pause, true);
            }
        });
        ClsStart.reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                reverseAnimations();
            }
        });
        ClsStart.help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                helpMessage += "In addition to that, user can only leave one field blank; No values can be negative.\nIf you wish to input an exponent, use scientific notation E (ex. 5E-6 for 5*10^-6)";
                showHelpDialog();
            }
        });
    }

    public static void addTextFields() {
        animationPaused = false;
        ClsStart.textFieldBox.setConstraints(voltageLabel, 0, 0);
        ClsStart.textFieldBox.setConstraints(voltageField, 1, 0);
        ClsStart.textFieldBox.setConstraints(currentLabel, 0, 1);
        ClsStart.textFieldBox.setConstraints(currentField, 1, 1);
        ClsStart.textFieldBox.setConstraints(resistanceLabel, 0, 2);
        ClsStart.textFieldBox.setConstraints(resistanceField, 1, 2);
        ClsStart.textFieldBox.getChildren().addAll(voltageLabel, voltageField, currentLabel, currentField, resistanceLabel, resistanceField);
    }

    public static void setAllData() {
        double v, c, r;
        blankCount = 3;
        if (!voltageField.getText().equals(empty)) {
            v = Double.parseDouble(voltageField.getText());
            oc.setVoltage(v);
            blankCount--;
        }
        if (!currentField.getText().equals(empty)) {
            c = Double.parseDouble(currentField.getText());
            oc.setCurrent(c);
            blankCount--;
        }
        if (!resistanceField.getText().equals(empty)) {
            r = Double.parseDouble(resistanceField.getText());
            oc.setResistance(r);
            blankCount--;
        }
    }

    public static void calculateAllData() {
        setAllData();
        if (!validate()) {
            showInvalidWarning();
            resetAll();
            setAllData();
        } else {
            if (voltageField.getText().equals(empty)) {
                oc.calculateVoltage();
                voltageField.setText(Double.toString(oc.getVoltage()));
            } else if (currentField.getText().equals(empty)) {
                oc.calculateIntensity();
                currentField.setText(Double.toString(oc.getCurrent()));
            } else if (resistanceField.getText().equals(empty)) {
                oc.calculateResistance();
                resistanceField.setText(Double.toString(oc.getResistance()));
            }
            voltageField.setEditable(false);
            currentField.setEditable(false);
            resistanceField.setEditable(false);
            animate();
            graph();
        }
    }

    public static void addGraphics() {
        circuit.setX(200);
        circuit.setY(55);
        circuit.setHeight(250);
        circuit.setWidth(550);
        circuit.setFill(Color.TRANSPARENT);
        circuit.setStroke(Color.BLACK);
        circuit.setStrokeWidth(5);

        resistor.setFitWidth(100);
        resistor.setPreserveRatio(true);
        resistor.setCache(true);
        resistor.setX(470);
        resistor.setY(280);

        battery.setFitWidth(100);
        battery.setPreserveRatio(true);
        battery.setCache(true);
        battery.setX(300);
        battery.setY(5);

        bat.setX(325);
        bat.setY(20);
        res.setX(495);
        res.setY(270);

        ClsStart.animationBox.getChildren().addAll(circuit, resistor, battery, bat, res);

        currentAxis.setLabel("Current (A)");
        voltageAxis.setLabel("Voltage (V)");
        ohmLaw.setTitle("Ohm's Law");
        ohmLaw.setPrefHeight(250);
        ohmLaw.setPrefWidth(250);
        ohmLaw.setCreateSymbols(false);
        graphBox1.getChildren().add(ohmLaw);

    }

    public static void animate() {
        double scaleB;
        double scaleR;
        double eSpeed;

        if (oc.getVoltage() >= 50) {
            scaleB = 1.7;
        } else if (oc.getVoltage() < 15) {
            scaleB = 0.5;
        } else {
            scaleB = oc.getVoltage() / 50 * 1.7;
        }

        if (oc.getResistance() >= 50) {
            scaleR = 1.7;
            eSpeed = 2500;
        } else if (oc.getResistance() < 15) {
            scaleR = 0.5;
            eSpeed = 750;
        } else {
            scaleR = oc.getResistance() / 50 * 1.7;
            eSpeed = oc.getResistance() / 50 * 2500;
        }

        electron.setFitWidth(100);
        electron.setPreserveRatio(true);
        electron.setCache(true);
        electron.setX(365);
        electron.setY(30);

        batScale.setDuration(Duration.millis(1500));
        batScale.setNode(battery);
        batScale.setToX(scaleB);
        batScale.setCycleCount(1);

        resScale.setDuration(Duration.millis(1500));
        resScale.setNode(resistor);
        resScale.setToX(scaleR);
        resScale.setCycleCount(1);

        translateRight.setDuration(Duration.millis(1500));
        translateRight.setNode(electron);
        translateRight.setToX(338);
        translateRight.setCycleCount(One);

        translateDown.setDuration(Duration.millis(1500));
        translateDown.setNode(electron);
        translateDown.setToY(250);
        translateDown.setCycleCount(One);

        translateLeft.setDuration(Duration.millis(1500));
        translateLeft.setNode(electron);
        translateLeft.setToX(170);
        translateLeft.setCycleCount(One);

        translateLeftFast.setDuration(Duration.millis(eSpeed));
        translateLeftFast.setNode(electron);
        translateLeftFast.setToX(50);
        translateLeftFast.setCycleCount(One);

        translateLeft2.setDuration(Duration.millis(1500));
        translateLeft2.setNode(electron);
        translateLeft2.setToX(-211);
        translateLeft2.setCycleCount(One);

        translateUp.setDuration(Duration.millis(1500));
        translateUp.setNode(electron);
        translateUp.setToY(Zero);
        translateUp.setCycleCount(One);

        translateRight2.setDuration(Duration.millis(1500));
        translateRight2.setNode(electron);
        translateRight2.setToX(-120);
        translateRight2.setCycleCount(One);

        eTrans.getChildren().addAll(translateRight, translateDown, translateLeft, translateLeftFast, translateLeft2, translateUp, translateRight2);

        scalePar.getChildren().addAll(batScale, resScale, eTrans);
        scalePar.playFromStart();

        ClsStart.animationBox.getChildren().addAll(electron);

        scalePar.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showFinishDialog();
                ClsFunctions.disableButton(ClsStart.start, true);
                ClsFunctions.disableButton(ClsStart.pause, true);
            }
        });
    }

    public static void reverseAnimations() {
        scalePar.stop();
        eTrans.getChildren().clear();
        scalePar.getChildren().clear();

        ParallelTransition revPar = new ParallelTransition();
        SequentialTransition revSeq = new SequentialTransition();

        ScaleTransition reverse1 = new ScaleTransition(Duration.seconds(0.2), battery);
        ScaleTransition reverse2 = new ScaleTransition(Duration.seconds(0.2), resistor);

        TranslateTransition reverseLeft = new TranslateTransition(Duration.seconds(0.2), electron);
        TranslateTransition reverseDown = new TranslateTransition(Duration.seconds(0.2), electron);
        TranslateTransition reverseRight = new TranslateTransition(Duration.seconds(0.2), electron);

        reverseLeft.setToX(Zero);
        reverseLeft.setCycleCount(One);
        reverseLeft.setAutoReverse(false);

        reverseDown.setToY(Zero);
        reverseDown.setCycleCount(One);
        reverseDown.setAutoReverse(false);

        reverseRight.setToX(Zero);
        reverseRight.setCycleCount(One);
        reverseRight.setAutoReverse(false);

        reverse1.setToX(One);
        reverse1.setCycleCount(One);
        reverse1.setAutoReverse(false);

        reverse2.setToX(One);
        reverse2.setCycleCount(One);
        reverse2.setAutoReverse(false);

        revSeq.getChildren().addAll(reverseLeft, reverseDown, reverseRight);
        revPar.getChildren().addAll(reverse1, reverse2, revSeq);
        ClsStart.animationBox.getChildren().remove(electron);
        revPar.playFromStart();

        revPar.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetAll();
            }
        });
    }

    public static void graph() {
        XYChart.Series series = new XYChart.Series();
        series.setName("Slope: " + oc.getResistance() + " (Resistance)");
        for (double t = Zero; t <= 10; t++) {
            series.getData().add(new XYChart.Data(t * oc.getCurrent(), t * oc.getVoltage()));
        }
        ohmLaw.getData().add(series);
    }

    public static void pauseAnimation() {
        animationPaused = true;
        scalePar.pause();
    }

    public static void resetAll() {
        blankCount = 3;
        animationPaused = false;
        ohmLaw.getData().clear();
        voltageField.setText(empty);
        voltageField.setEditable(true);
        currentField.setText(empty);
        currentField.setEditable(true);
        resistanceField.setText(empty);
        resistanceField.setEditable(true);
        disableButton(ClsStart.start, false);
        disableButton(ClsStart.pause, true);
    }

    public static boolean validate() {
        if (oc.getVoltage() < 0 || oc.getCurrent() < 0 || oc.getResistance() < 0) {
            return false;
        } else if (blankCount != 1) {
            return false;
        } else {
            return true;
        }
    }

    public static void showFinishDialog() {
        Alert end = new Alert(AlertType.INFORMATION);
        end.setTitle("End");
        end.setHeaderText("Animation finished");
        end.setContentText("Animation finished.\nClick OK to start over.");
        end.show();
    }

    public static void showInvalidWarning() {
        Alert invalid = new Alert(AlertType.WARNING);
        invalid.setTitle("Warning");
        invalid.setHeaderText("Invalid input");
        invalid.setContentText("One or more variables are out of range or empty. \nClick OK to try again.");
        invalid.showAndWait();
    }

}
