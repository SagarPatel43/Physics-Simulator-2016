package main;

import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

public class ClsFunctions implements PhysicsConstants {

    public static String helpMessage=helpText2;

    public static void addButtons() {
        ClsStart.buttonBox.getChildren().addAll(ClsStart.start,ClsStart.done,ClsStart.pause,ClsStart.reset,ClsStart.help);
    }

    public static void disableButton(Button button,boolean b) {
        button.setDisable(b);
    }

    public static void disableMenus() {
        ClsStart.NYAMenu.setDisable(true);
        ClsStart.NYBMenu.setDisable(true);
        ClsStart.NYCMenu.setDisable(true);
    }

    public static void enableMenus() {
        ClsStart.NYAMenu.setDisable(false);
        ClsStart.NYBMenu.setDisable(false);
        ClsStart.NYCMenu.setDisable(false);
    }

    public static void doneButtonMethod() {
        ClsStart.removeEverything();
        ClsStart.showStartingElements();
    }

    public static void showHelpDialog() {
        Alert helpDialog=new Alert(AlertType.INFORMATION);
        helpDialog.setTitle("Help");
        helpDialog.setHeaderText(null);
        helpDialog.setContentText(helpMessage);
        helpDialog.showAndWait();
    }
} 