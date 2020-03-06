package slogo.View;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.ModelDatabase;
import slogo.Model.ModelParser;
import slogo.View.Input.InputView;


public class SlogoView {

    private static final int SCENE_WIDTH = 1000;
    private static final int SCENE_HEIGHT = 600;

    private static final String MODELPARSER_LANGUAGE = "English";

    private static final String STYLESHEET_FILE = "default.css";

    private BorderPane myBorderPane;
    private ModelDatabase myModelDatabase;
    private CommandDatabase myCommandDatabase;
    private ModelParser myModelParser;
    private InputView myInputView;
    private Pane myBackgroundPane;
    private TurtleView myTurtleView;
    private InfoView myInfoView;


    public SlogoView(Stage displayedStage) {
        initModel();
        initView();
        initStage(displayedStage);
        bindProperties();
    }



    private void initModel() {
        myModelDatabase = new ModelDatabase();
        myCommandDatabase = new CommandDatabase(myModelDatabase.getMyTurtle());
        myModelParser = new ModelParser(MODELPARSER_LANGUAGE, myCommandDatabase);
    }

    private void initView() {
        myBackgroundPane = new Pane();
        myTurtleView = new TurtleView(myModelDatabase.getMyTurtle(), myBackgroundPane);
        CommandBox myCommandLine = new CommandBox(myModelParser, myTurtleView);
        myInputView = new InputView();
        myInfoView = new InfoView();

        VBox commandAndInput = new VBox();
        commandAndInput.getChildren().addAll(myInputView.getInputPanel(), myCommandLine.getCommandLine());
        BackgroundFill commandBackground = new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY);
        commandAndInput.setBackground(new Background(commandBackground));

        myBorderPane = new BorderPane();
        myBorderPane.setBottom(commandAndInput);
        myBorderPane.setCenter(myBackgroundPane);
        myBorderPane.setRight(myInfoView.getCompletePanel());
    }

    private void initStage(Stage primaryStage) {
        Scene myScene = new Scene(myBorderPane, SCENE_WIDTH,SCENE_HEIGHT);
        myScene.getStylesheets()
                .add(getClass().getResource("/" + STYLESHEET_FILE).toExternalForm());
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    private void bindProperties() {
        createBindableBackground();
        createBindablePen();
        createBindableImage();
        createBindableLanguage();
        createBindableInfoPanel();
    }

    // Inspiration from https://stackoverflow.com/questions/33999728/binding-colorpicker-in-javafx-to-label-background-property
    // TODO: once we understand bindings better, refactor
    private void createBindableBackground() {
        myBackgroundPane.backgroundProperty().bind(Bindings.createObjectBinding(() -> {
            BackgroundFill fill = new BackgroundFill((Paint)myInputView.getBackgroundPropertyColor().getValue(), CornerRadii.EMPTY, Insets.EMPTY);
            return new Background(fill);
        }, myInputView.getBackgroundPropertyColor()));
    }


    private void createBindablePen() {
        myTurtleView.getPenColorProperty().bind(myInputView.getPenPropertyColor());
        myTurtleView.getPenColorProperty().getValue();
    }

    private void createBindableImage() {
        System.out.println(myInputView.getTurtleImage());
        myTurtleView.getImageProperty().bind(myInputView.getTurtleImage());
    }

    private void createBindableLanguage() {myModelParser.getParserLanguageProperty().bind(myInputView.getLanguage());}

    private void createBindableInfoPanel() {
        myCommandDatabase.bindHistory(myInfoView.getHistoryProperty());
        myCommandDatabase.bindCommands(myInfoView.getCommandProperty());
        myCommandDatabase.bindVariables(myInfoView.getVariableProperty());
    }
}


