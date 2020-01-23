package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
private Scene scene;
private GraphPane graphPane;
private BorderPane borderPane;
private ControlPane controllPane;
private DisplayPanel displayPanel;
private Controller controller;
    @Override
    public void start(Stage primaryStage){
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("East is up, i'm fearless when I hear this on the low");
        //primaryStage.setResizable(false);
        this.borderPane = new BorderPane();
        this.graphPane = new GraphPane();
        this.displayPanel = new DisplayPanel();
        this.controller = new Controller(this.graphPane, this.displayPanel);
        this.controllPane = new ControlPane(this.controller);
        this.borderPane.setCenter(this.graphPane.getPane());
        this.borderPane.setRight(this.controllPane.getVBox());
        this.borderPane.setLeft(this.displayPanel.getvBox());
        this.scene = new Scene(this.borderPane);
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
