package slogo;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.Model.ModelDatabase;

/**
 * Tester class
 * @author erikgregorio
 */
public class tester extends Application {
    @Override
    public void start(Stage primaryStage) {
      ModelDatabase modelDatabase = new ModelDatabase();
       // ModelParser modelParser = new ModelParser("English");

        //TurtleView myTurtle = new TurtleView(modelParser.getMyTurtle());
        //CommandBox myCommandLine = new CommandBox(modelParser, myTurtle);
        //InputPanel myButtons = new InputPanel();
        //BorderPane mypane = new BorderPane();

        //Pane myGroup = new Pane();
        //myGroup.setPrefSize(200,200);
        //myGroup.getChildren().addAll(myTurtle.getTurtle());
        //mypane.setTop(myButtons.getInputPanel());
        mypane.setBottom(myCommandLine.getCommandLine());
        mypane.setCenter(p);

        Scene myScene = new Scene(mypane, 600,600);

        primaryStage.setScene(myScene);
        primaryStage.show();
        //primaryStage

        ObservableList<ArrayList<Double>> mylist = FXCollections.observableArrayList();

    }
}

