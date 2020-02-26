package slogo.View;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Model.ModelParser;


public class SlogoView extends Application {

    private static final int SCENE_WIDTH = 1000;
    private static final int SCENE_HEIGHT = 600;

    private static final String MODELPARSER_LANGUAGE = "English";

    private static final String STYLESHEET_FILE = "default.css";

    private BorderPane myBorderPane;
    private ModelParser myModelParser;
    private InputView myInputView;
    private Pane myBackgroundPane;
    private TurtleView myTurtleView;

    public SlogoView() {}

    public SlogoView(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initModel();
        initView();
        initStage(primaryStage);
        bindProperties();
    }

    private void initModel() {
        myModelParser = new ModelParser(MODELPARSER_LANGUAGE);
    }

    private void initView() {
        myBackgroundPane = new Pane();
        myTurtleView = new TurtleView(myModelParser.getMyTurtle(), myBackgroundPane);
        CommandBox myCommandLine = new CommandBox(myModelParser, myTurtleView);
        myInputView = new InputView();
        InfoView myInfo = new InfoView();

        VBox commandAndInput = new VBox();
        commandAndInput.getChildren().addAll(myInputView.getInputPanel(), myCommandLine.getCommandLine());
        BackgroundFill commandBackground = new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY);
        commandAndInput.setBackground(new Background(commandBackground));

        myBorderPane = new BorderPane();
        myBorderPane.setBottom(commandAndInput);
        myBorderPane.setCenter(myBackgroundPane);
        myBorderPane.setRight(myInfo.getInfoPanel());
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
    }

    // Inspiration from https://stackoverflow.com/questions/33999728/binding-colorpicker-in-javafx-to-label-background-property
    // TODO: once we understand bindings better, refactor
    private void createBindableBackground() {
        myBackgroundPane.backgroundProperty().bind(Bindings.createObjectBinding(() -> {
            BackgroundFill fill = new BackgroundFill(myInputView.getBackgroundColorValue(), CornerRadii.EMPTY, Insets.EMPTY);
            return new Background(fill);
        }, myInputView.getBackgroundProperty()));
    }


    private void createBindablePen() {
        myTurtleView.getPenColorProperty().bind(myInputView.getPenPropertyColor());
        myTurtleView.getPenColorProperty().getValue();
    }

    private void createBindableImage() {
        myTurtleView.getImageProperty().bind(myInputView.getTurtleImage());
    }
}

