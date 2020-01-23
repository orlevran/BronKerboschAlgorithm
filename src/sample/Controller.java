package sample;

public class Controller {

    private GraphPane graphPane;
    private DisplayPanel displayPanel;

    public Controller(GraphPane graphPane, DisplayPanel displayPanel) {
        this.graphPane = graphPane;
        this.displayPanel = displayPanel;
    }

    public DisplayPanel getDisplayPanel() {
        return this.displayPanel;
    }

    public GraphPane getGraphPane() {
        return this.graphPane;
    }
}
