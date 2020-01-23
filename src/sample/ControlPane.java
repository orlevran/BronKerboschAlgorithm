package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ControlPane<T> {
    private VBox vBox;
    private HBox hBox;

    private ComboBox<Integer> numOfVertices, firstEdge, secondEdge;
    private Button confirmButton, createEdge, runAlgorithm;
    private Label edgeGenerator;
    private Controller controller;

    public ControlPane(Controller controller) {
        this.controller = controller;
        this.vBox = new VBox(20);
        this.vBox.setAlignment(Pos.CENTER);
        this.numOfVertices = new ComboBox<>();
        for (int i = 3; i <= 12; i++) {
            this.numOfVertices.getItems().add(i);
        }
        this.numOfVertices.getSelectionModel().selectFirst();

        this.confirmButton = new Button("Continue");
        this.confirmButton.setFont(Font.font("Ink Free", FontWeight.BOLD, 30));
        this.confirmButton.setOnMouseClicked(e -> {
            this.controller.getGraphPane().setNumOfVertices(this.numOfVertices.getValue());
            this.edgeGenerator = new Label("Create an edge");
            this.edgeGenerator.setFont(Font.font("Ink Free", FontWeight.BOLD, 30));
            this.hBox = new HBox(20);
            this.firstEdge = new ComboBox<>();
            this.secondEdge = new ComboBox<>();
            for (int i = 1; i <= this.numOfVertices.getValue(); i++) {
                this.firstEdge.getItems().add(i);
                this.secondEdge.getItems().add(i);
            }
            this.firstEdge.getSelectionModel().selectFirst();
            this.secondEdge.getSelectionModel().selectFirst();
            this.hBox.getChildren().addAll(this.firstEdge, this.secondEdge);
            this.hBox.setAlignment(Pos.CENTER);
            this.createEdge = new Button("OK");
            this.createEdge.setFont(Font.font("Ink Free", FontWeight.BOLD, 30));

            this.runAlgorithm = new Button("Run");
            this.runAlgorithm.setFont(Font.font("Ink Free", FontWeight.BOLD, 30));

            this.vBox.getChildren().clear();
            this.vBox.getChildren().addAll(this.edgeGenerator, this.hBox, this.createEdge, this.runAlgorithm);
            setButtonsMouseClicked();
        });

        this.vBox.getChildren().addAll(this.numOfVertices, this.confirmButton);
    }

    public void setButtonsMouseClicked() {
        this.createEdge.setOnMouseClicked(e -> {
            controller.getGraphPane().addEdge(this.firstEdge.getValue(), this.secondEdge.getValue());
        });

        this.runAlgorithm.setOnMouseClicked(e->{
            controller.getGraphPane().runAlgorithm(this.controller);
        });
    }

    public VBox getVBox() {
        return this.vBox;
    }
}
