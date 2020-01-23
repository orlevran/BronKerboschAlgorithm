package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class DisplayPanel {
    private VBox vBox;
    private Label pLabel, rLabel, xLabel, headlineLabel;
    private ArrayList<Label> maximalCliques;

    public DisplayPanel(){
        this.vBox = new VBox(5);
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox = new VBox(5);
        this.vBox.setAlignment(Pos.TOP_CENTER);

        this.pLabel = new Label("P = {}");
        this.pLabel.setFont(Font.font("ariel", FontWeight.BOLD, 30));
        this.pLabel.setTextFill(Color.WHITE);
        this.pLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        this.rLabel = new Label("R = {}");
        this.rLabel.setFont(Font.font("ariel", FontWeight.BOLD, 30));
        this.rLabel.setTextFill(Color.WHITE);
        this.rLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        this.xLabel = new Label("X = {}");
        this.xLabel.setFont(Font.font("ariel", FontWeight.BOLD, 30));
        this.xLabel.setTextFill(Color.WHITE);
        this.xLabel.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        this.headlineLabel = new Label("All maximal cliques:");
        this.headlineLabel.setFont(Font.font("ariel", FontWeight.BOLD, 30));

        this.maximalCliques = new ArrayList<>();

        this.vBox.getChildren().addAll(this.pLabel,this.rLabel,this.xLabel, this.headlineLabel);
    }

    public VBox getvBox(){
        return this.vBox;
    }

    public void setrLabel(String string){
        this.rLabel.setText(string);
    }

    public void setpLabel(String string){
        this.pLabel.setText(string);
    }

    public void setxLabel(String string){
        this.xLabel.setText(string);
    }

    public void addNewMaximalClique(String string){
        this.maximalCliques.add(new Label(string));
        this.maximalCliques.get(this.maximalCliques.size()-1).setFont(Font.font("ariel", FontWeight.BOLD, 30));
        this.vBox.getChildren().add(this.maximalCliques.get(this.maximalCliques.size()-1));
    }
}
