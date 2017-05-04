package main;

import NYA.*;
import NYB.*;
import NYC.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class ClsStart extends Application {

    protected static Image bg=new Image("file:///E:/Programming/Vanier/420-204-RE/Final Project/src/main/high-school-physics-curriculum-resource-lesson-plans_138308_large.jpg");
    protected static ImageView background=new ImageView(bg);
    protected static BorderPane root=new BorderPane();
    protected MenuBar mb=new MenuBar();
    protected VBox topVBox=new VBox();
    protected static VBox centerBox=new VBox();
    protected BorderPane titleAndAnimationBox=new BorderPane();
    protected static HBox titleBox=new HBox();
    public static Pane animationBox=new Pane();
    public static HBox buttonAndGraphBox=new HBox();
    protected static VBox buttonAndTextFieldBox=new VBox();
    public static HBox buttonBox=new HBox();
    public static GridPane textFieldBox=new GridPane();
    public static Pane graphBox1=new Pane();
    public static Pane graphBox2=new Pane();
    protected static Label messageLabel=new Label();

    protected static Menu NYAMenu=new Menu("Mechanics");
    protected static Menu NYBMenu=new Menu("Electricity and Magnetism");
    protected static Menu NYCMenu=new Menu("Waves and Modern Physics");

    public static Button start=new Button("Start");
    public static Button done=new Button("Done");
    public static Button pause=new Button("Pause");
    public static Button reset=new Button("Reset");
    public static Button help=new Button("Help");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        createMenuBar();
        createCenter();
        root.setTop(topVBox);
        root.setCenter(centerBox);
        titleAndAnimationBox.setTop(titleBox);
        titleAndAnimationBox.setCenter(animationBox);
        titleBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(titleAndAnimationBox,buttonAndGraphBox);
        animationBox.setPrefHeight(350);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        textFieldBox.setPadding(new Insets(10,10,10,10));
        textFieldBox.setAlignment(Pos.CENTER);
        buttonAndTextFieldBox.getChildren().addAll(buttonBox,textFieldBox);
        buttonAndGraphBox.getChildren().addAll(buttonAndTextFieldBox,graphBox1);
        background.setFitWidth(950);
        background.setPreserveRatio(true);
        background.setCache(true);
        stage.setTitle("Physics Simulator");
        stage.setScene(new Scene(root,960,640));
        stage.setResizable(false);
        stage.show();
        showStartingElements();
    }

    public static void showStartingElements() {
        Text startMsg=new Text("Welcome to use the Physics Simulator.\nPlease select a category above to begin.");
        startMsg.setFont(new Font("Impact",24));
        startMsg.setFill(Color.BLUE);
        startMsg.setTextAlignment(TextAlignment.CENTER);
        titleBox.getChildren().add(startMsg);
        animationBox.getChildren().add(background);
    }

    public void createMenuBar() {
        topVBox.setSpacing(10);
        MenuItem newton2Law=new MenuItem("Newton's Second Law");
        MenuItem projectileMotion=new MenuItem("Projectile Motion");
        MenuItem constructionNYA=new MenuItem("In construction...");
        NYAMenu.getItems().addAll(newton2Law,projectileMotion,constructionNYA);
        newton2Law.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeEverything();
                setMessage("Newton's Second Law");
                ClsFunctions.addButtons();
                ClsFunctions.disableButton(ClsStart.pause,true);
                Newton2ndLaw.addTextFields();
                Newton2ndLaw.addGraphics();
                Newton2ndLaw.buttonMethods();
            }
        });
        projectileMotion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                removeEverything();
                setMessage("Projectile Motion");
                buttonAndGraphBox.getChildren().add(graphBox2);
                ClsFunctions.addButtons();
                ClsFunctions.disableButton(ClsStart.pause,true);
                ProjectileMotion.addTextFields();
                ProjectileMotion.addGraphics();
                ProjectileMotion.buttonMethods();
            }
        });
        constructionNYA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                inConstruction();
            }
        });
        MenuItem coulombLaw=new MenuItem("Coulomb's Law");
        MenuItem ohmLaw=new MenuItem("Ohm's Law");
        MenuItem constructionNYB=new MenuItem("In construction...");
        NYBMenu.getItems().addAll(coulombLaw,ohmLaw,constructionNYB);
        coulombLaw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                removeEverything();
                setMessage("Coulomb's Law");
                ClsFunctions.addButtons();
                ClsFunctions.disableButton(ClsStart.pause,true);
                CoulombLaw.addTextFields();
                CoulombLaw.addGraphics();
                CoulombLaw.buttonMethods();
            }
        });
        ohmLaw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                removeEverything();
                setMessage("Ohm's Law");
                ClsFunctions.addButtons();
                ClsFunctions.disableButton(ClsStart.pause,true);
                OhmLaw.addTextFields();
                OhmLaw.addGraphics();
                OhmLaw.buttonMethods();
            }
        });
        constructionNYB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                inConstruction();
            }
        });
        MenuItem shm=new MenuItem("Simple Harmonic Motion");
        MenuItem ssd=new MenuItem("Single Slit Diffraction");
        MenuItem constructionNYC=new MenuItem("In construction...");
        NYCMenu.getItems().addAll(shm,ssd,constructionNYC);
        shm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                removeEverything();
                setMessage("Simple Harmonic Motion");
                buttonAndGraphBox.getChildren().add(graphBox2);
                ClsFunctions.addButtons();
                ClsFunctions.disableButton(ClsStart.pause,true);
                SimpleHarmonicMotion.addTextFields();
                SimpleHarmonicMotion.addGraphics();
                SimpleHarmonicMotion.buttonMethods();
            }
        });
        ssd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                removeEverything();
                setMessage("Single Slit Diffraction");
                ClsFunctions.addButtons();
                ClsFunctions.disableButton(ClsStart.pause,true);
                SingleSlit.addTextFields();
                SingleSlit.addGraphics();
                SingleSlit.buttonMethods();
            }
        });
        constructionNYC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                inConstruction();
            }
        });
        Menu exitMenu=new Menu("Exit");
        MenuItem exitItem=new MenuItem("Exit");
        exitMenu.getItems().addAll(exitItem);
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                removeEverything();
                Alert ex=new Alert(AlertType.INFORMATION);
                ex.setTitle("Goodbye");
                ex.setHeaderText(null);
                ex.setContentText("Thank you for using the Physics Simulator.\nHope to see you soon.");
                ex.showAndWait();
                System.exit(0);
            }
        });
        mb.getMenus().addAll(NYAMenu,NYBMenu,NYCMenu,exitMenu);
        topVBox.getChildren().add(mb);
    }

    public void createCenter() {
        centerBox.setPadding(new Insets(10,10,10,10));
    }

    public static void setMessage(String str) {
        messageLabel.setText(str);
    }

    public void inConstruction() {
        removeEverything();
        Alert cons=new Alert(AlertType.WARNING);
        cons.setTitle("Warning");
        cons.setHeaderText("Topic under construction");
        cons.setContentText("Click OK to choose another topic.");
        cons.showAndWait();
        showStartingElements();
    }

    public static void removeEverything() {
        titleBox.getChildren().clear();
        titleBox.getChildren().add(messageLabel);
        setMessage("");
        animationBox.getChildren().clear();
        buttonBox.getChildren().clear();
        textFieldBox.getChildren().clear();
        graphBox1.getChildren().clear();
        buttonAndGraphBox.getChildren().remove(background);
        buttonAndGraphBox.getChildren().remove(graphBox2);
    }
}