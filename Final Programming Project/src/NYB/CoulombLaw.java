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
import static main.PhysicsConstants.Zero;

public class CoulombLaw implements PhysicsConstants {

    private static CoulombClass cc = new CoulombClass();

    private static TextField q1Field = new TextField();
    private static Label q1Label = new Label("Charge of object 1 (C): ");
    private static TextField q2Field = new TextField();
    private static Label q2Label = new Label("Charge of object 2 (C): ");
    private static TextField distanceField = new TextField();
    private static Label distanceLabel = new Label("Distance between charged objects (m): ");
    private static TextField eForceField = new TextField();
    private static Label eForceLabel = new Label("Magnitude of electric force (N): ");

    private static Circle c1 = new Circle();
    private static Circle c2 = new Circle();

    private static Image ar = new Image("file:///C:/Users/sagar/Desktop/Final Programming Project/src/NYB/crightarrow.png");
    private static Image arLeft = new Image("file:///C:/Users/sagar/Desktop/Final Programming Project/src/NYB/cleftarrow.png");

    private static ImageView arrow = new ImageView(ar);
    private static ImageView arrowLeft = new ImageView(arLeft);

    private static Text fQ1 = new Text("F by Q2 on Q1");
    private static Text fQ2 = new Text("F by Q1 on Q2");

    private static NumberAxis chargeAxis = new NumberAxis();
    private static NumberAxis distAxis = new NumberAxis();
    private static LineChart<Number, Number> coulombLaw = new LineChart<Number, Number>(chargeAxis, distAxis);

    private static TranslateTransition translateC1 = new TranslateTransition();
    private static TranslateTransition translateC2 = new TranslateTransition();
    private static TranslateTransition translateFQ1 = new TranslateTransition();
    private static TranslateTransition translateFQ2 = new TranslateTransition();
    private static TranslateTransition translateAr = new TranslateTransition();
    private static TranslateTransition translateArLeft = new TranslateTransition();

    private static ScaleTransition scaleA1 = new ScaleTransition();
    private static ScaleTransition scaleA2 = new ScaleTransition();
    private static ScaleTransition scaleC1 = new ScaleTransition();
    private static ScaleTransition scaleC2 = new ScaleTransition();

    private static ParallelTransition par = new ParallelTransition();

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
                    par.play();
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
                helpMessage += "In addition to that, user can only leave electric force OR distance blank.\n"
                        + "Distance or electric force cannot be negative.\n"
                        + "If you wish to input a number is scientific notation, use E followed by the exponent of 10 (ex. 5E-6 for 5*10^-6)";
                showHelpDialog();
            }
        });
    }

    public static void addTextFields() {
        animationPaused = false;
        ClsStart.textFieldBox.setConstraints(q1Label, 0, 0);
        ClsStart.textFieldBox.setConstraints(q1Field, 1, 0);
        ClsStart.textFieldBox.setConstraints(q2Label, 0, 1);
        ClsStart.textFieldBox.setConstraints(q2Field, 1, 1);
        ClsStart.textFieldBox.setConstraints(distanceLabel, 0, 2);
        ClsStart.textFieldBox.setConstraints(distanceField, 1, 2);
        ClsStart.textFieldBox.setConstraints(eForceLabel, 0, 3);
        ClsStart.textFieldBox.setConstraints(eForceField, 1, 3);
        ClsStart.textFieldBox.getChildren().addAll(q1Label, q1Field, q2Label, q2Field, distanceLabel, distanceField, eForceLabel, eForceField);
        eForceField.setEditable(false);
    }

    public static void setAllData() {

        double q1, q2, distance, eForce;
        blankCount = 4;
        if (!q1Field.getText().equals(empty)) {
            q1 = Double.parseDouble(q1Field.getText());
            cc.setQ1(q1);
            blankCount--;
        }
        if (!q2Field.getText().equals(empty)) {
            q2 = Double.parseDouble(q2Field.getText());
            cc.setQ2(q2);
            blankCount--;
        }
        if (!distanceField.getText().equals(empty)) {
            distance = Double.parseDouble(distanceField.getText());
            cc.setDistance(distance);
            blankCount--;
        }
        if (!eForceField.getText().equals(empty)) {
            eForce = Double.parseDouble(eForceField.getText());
            cc.setEForce(eForce);
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
            if (eForceField.getText().equals(empty)) {
                cc.calculateEForce();
                eForceField.setText(Double.toString(cc.getEForce()));
            } else if (distanceField.getText().equals(empty)) {
                cc.calculateDistance();
                distanceField.setText(Double.toString(cc.getDistance()));
            }
            q1Field.setEditable(false);
            q2Field.setEditable(false);
            distanceField.setEditable(false);
            animate();
            graph();
        }
    }

    public static void addGraphics() {

        c1.setCenterX(350);
        c1.setCenterY(250);
        c1.setRadius(50);
        c2.setCenterX(590);
        c2.setCenterY(250);
        c2.setRadius(50);

        c1.setFill(Color.BLACK);
        c2.setFill(Color.RED);

        ClsStart.animationBox.getChildren().addAll(c1, c2);

        chargeAxis.setLabel("Product of Charges (C^2)");
        distAxis.setLabel("Force by Distance^2 (m^2)");
        coulombLaw.setTitle("Coulomb's Law");
        coulombLaw.setPrefHeight(250);
        coulombLaw.setPrefWidth(250);
        coulombLaw.setCreateSymbols(false);
        graphBox1.getChildren().add(coulombLaw);
    }

    public static void animate() {

        double Dist;
        double eForce;
        double charge;
        double charge2;

        if (cc.getQ1() < 0) {
            c1.setFill(Color.RED);
        } else {
            c1.setFill(Color.BLACK);
        }

        if (cc.getQ2() < 0) {
            c2.setFill(Color.RED);
        } else {
            c2.setFill(Color.BLACK);
        }

        arrow.setFitWidth(100);
        arrow.setPreserveRatio(true);
        arrow.setCache(true);
        arrowLeft.setFitWidth(100);
        arrowLeft.setPreserveRatio(true);
        arrowLeft.setCache(true);

        if (cc.getDistance() > 10) {
            Dist = 10;
        } else {
            Dist = cc.getDistance();
        }

        if (cc.getQ1() * cc.getQ2() > 0) {
            arrow.setX(c1.getCenterX() + c2.getRadius() + 230);
            arrow.setY(c1.getCenterY() - 9);

            arrowLeft.setX(c2.getCenterX() - c2.getRadius() - 330);
            arrowLeft.setY(c2.getCenterY() - 9);

            translateAr.setDuration(Duration.millis(1500));
            translateAr.setNode(arrow);
            translateAr.setByX(Dist * 160 / 10);
            translateAr.setCycleCount(1);

            translateArLeft.setDuration(Duration.millis(1500));
            translateArLeft.setNode(arrowLeft);
            translateArLeft.setByX(-Dist * 160 / 10);
            translateArLeft.setCycleCount(1);
        } else {
            arrowLeft.setX(c1.getCenterX() + c2.getRadius() + 40);
            arrowLeft.setY(c1.getCenterY() - 9);

            arrow.setX(c2.getCenterX() - c2.getRadius() - 150);
            arrow.setY(c2.getCenterY() - 9);

            translateAr.setDuration(Duration.millis(1500));
            translateAr.setNode(arrow);
            translateAr.setByX(-Dist * 160 / 10);
            translateAr.setCycleCount(1);

            translateArLeft.setDuration(Duration.millis(1500));
            translateArLeft.setNode(arrowLeft);
            translateArLeft.setByX(Dist * 160 / 10);
            translateArLeft.setCycleCount(1);
        }

        fQ1.setX(312);
        fQ1.setY(150);
        fQ2.setX(552);
        fQ2.setY(150);

        translateC1.setDuration(Duration.millis(1500));
        translateC1.setNode(c1);
        translateC1.setByX(-Dist * 160 / 10);
        translateC1.setCycleCount(1);

        translateC2.setDuration(Duration.millis(1500));
        translateC2.setNode(c2);
        translateC2.setByX(Dist * 160 / 10);
        translateC2.setCycleCount(1);

        translateFQ1.setDuration(Duration.millis(1500));
        translateFQ1.setNode(fQ1);
        translateFQ1.setByX(-(Dist * 160 / 10));
        translateFQ1.setCycleCount(1);

        translateFQ2.setDuration(Duration.millis(1500));
        translateFQ2.setNode(fQ2);
        translateFQ2.setByX(Dist * 160 / 10);
        translateFQ2.setCycleCount(1);

        if (cc.getEForce() > 1 * 1E-2) {
            eForce = 1;
        } else {
            eForce = -0.5;
        }

        if (Math.abs(cc.getQ1() * 1E6) > 5) {
            charge = 0.6;
        } else {
            charge = -0.2;
        }

        if (Math.abs(cc.getQ2() * 1E6) > 5) {
            charge2 = 0.6;
        } else {
            charge2 = -0.2;
        }

        scaleA1.setDuration(Duration.millis(1500));
        scaleA1.setNode(arrow);
        scaleA1.setByX(eForce);
        scaleA1.setCycleCount(1);

        scaleA2.setDuration(Duration.millis(1500));
        scaleA2.setNode(arrowLeft);
        scaleA2.setByX(eForce);
        scaleA2.setCycleCount(1);

        scaleC1.setDuration(Duration.millis(1500));
        scaleC1.setNode(c1);
        scaleC1.setByX(charge);
        scaleC1.setByY(charge);
        scaleC1.setCycleCount(1);

        scaleC2.setDuration(Duration.millis(1500));
        scaleC2.setNode(c2);
        scaleC2.setByX(charge2);
        scaleC2.setByY(charge2);
        scaleC2.setCycleCount(1);

        par.getChildren().addAll(translateC1, translateC2, translateFQ1, translateFQ2, translateAr, translateArLeft, scaleA1, scaleA2, scaleC1, scaleC2);
        par.playFromStart();

        ClsStart.animationBox.getChildren().addAll(arrow, arrowLeft, fQ1, fQ2);

        par.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showFinishDialog();
                ClsFunctions.disableButton(ClsStart.start, true);
                ClsFunctions.disableButton(ClsStart.pause, true);
            }
        });
    }

    public static void pauseAnimation() {
        animationPaused = true;
        par.pause();
    }

    public static void resetAll() {
        blankCount = 4;
        animationPaused = false;
        coulombLaw.getData().clear();
        q1Field.setText(empty);
        q1Field.setEditable(true);
        q2Field.setText(empty);
        q2Field.setEditable(true);
        distanceField.setText(empty);
        distanceField.setEditable(true);
        eForceField.setText(empty);
        eForceField.setEditable(true);
        disableButton(ClsStart.start, false);
        disableButton(ClsStart.pause, true);
    }

    public static void graph() {
        XYChart.Series series = new XYChart.Series();
        series.setName("Slope: 9E-9 (Coulomb's constant)");
        for (double t = Zero; t <= 10; t++) {
            series.getData().add(new XYChart.Data(t * cc.getProductOfCharges(cc.getQ1(), cc.getQ2()), t * cc.getForceByDistance2(cc.getEForce(), cc.getDistance())));
        }
        coulombLaw.getData().add(series);
    }

    public static void reverseAnimations() {
        par.stop();
        par.getChildren().clear();

        ParallelTransition revPar = new ParallelTransition();

        TranslateTransition reverseC1 = new TranslateTransition(Duration.seconds(0.2), c1);
        TranslateTransition reverseC2 = new TranslateTransition(Duration.seconds(0.2), c2);
        TranslateTransition reverseFQ1 = new TranslateTransition(Duration.seconds(0.2), fQ1);
        TranslateTransition reverseFQ2 = new TranslateTransition(Duration.seconds(0.2), fQ2);
        TranslateTransition reverseAr = new TranslateTransition(Duration.seconds(0.2), arrow);
        TranslateTransition reverseArLeft = new TranslateTransition(Duration.seconds(0.2), arrowLeft);

        ScaleTransition reverse1 = new ScaleTransition(Duration.seconds(0.2), arrow);
        ScaleTransition reverse2 = new ScaleTransition(Duration.seconds(0.2), arrowLeft);
        ScaleTransition reverse3 = new ScaleTransition(Duration.seconds(0.2), c1);
        ScaleTransition reverse4 = new ScaleTransition(Duration.seconds(0.2), c2);

        reverseC1.setToX(Zero);
        reverseC1.setCycleCount(One);
        reverseC1.setAutoReverse(false);

        reverseC2.setToX(Zero);
        reverseC2.setCycleCount(One);
        reverseC2.setAutoReverse(false);

        reverseFQ1.setToX(Zero);
        reverseFQ1.setCycleCount(One);
        reverseFQ1.setAutoReverse(false);

        reverseFQ2.setToX(Zero);
        reverseFQ2.setCycleCount(One);
        reverseFQ2.setAutoReverse(false);

        reverseAr.setToX(Zero);
        reverseAr.setCycleCount(One);
        reverseAr.setAutoReverse(false);

        reverseArLeft.setToX(Zero);
        reverseArLeft.setCycleCount(One);
        reverseArLeft.setAutoReverse(false);

        reverse1.setToX(One);
        reverse1.setCycleCount(One);
        reverse1.setAutoReverse(false);

        reverse2.setToX(One);
        reverse2.setCycleCount(One);
        reverse2.setAutoReverse(false);

        reverse3.setToY(One);
        reverse3.setToX(One);
        reverse3.setCycleCount(One);
        reverse3.setAutoReverse(false);

        reverse4.setToY(One);
        reverse4.setToX(One);
        reverse4.setCycleCount(One);
        reverse4.setAutoReverse(false);

        revPar.getChildren().addAll(reverseC1, reverseC2, reverseFQ1, reverseFQ2, reverseAr, reverseArLeft, reverse1, reverse2, reverse3, reverse4);
        ClsStart.animationBox.getChildren().removeAll(arrow, arrowLeft, fQ1, fQ2);
        revPar.playFromStart();

        revPar.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetAll();
            }
        });
    }

    public static boolean validate() {
        if (cc.getDistance() < 0) {
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
        invalid.setContentText("One or more variables are out of range or empty.\nClick OK to try again.");
        invalid.showAndWait();
    }
}
