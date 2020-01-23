package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class GraphPane<T> {
    private Pane pane;
    private KeyFrame keyFrame;
    private Timeline timeline;
    private Graph<Integer> graph;
    private ArrayList<Text> texts;
    private ArrayList<Circle> vertices;
    private ArrayList<Line> edges;
    private Set<HashSet<Integer>> edgesSet;
    private ArrayList<ArrayList<Double>> coordinates;
    private ArrayList<Set<? extends Integer>> allMaxCliques;
    private int numOfVertices, numOfEdges;
    private RandomAccessFile randomAccessFile;
    private final int RADIUS = 225;

    public GraphPane() {
        this.pane = new Pane();
        this.pane.setPrefSize(600, 600);
        this.numOfEdges = 0;
        this.graph = new Graph<>();
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.edgesSet = new HashSet<>();
        this.texts = new ArrayList<>();
        this.allMaxCliques = new ArrayList<>();
        this.coordinates = new ArrayList<>();
        try {
            this.randomAccessFile = new RandomAccessFile("vertices.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane getPane() {
        return this.pane;
    }

    public void setNumOfVertices(int numOfVertices) {
        this.numOfVertices = numOfVertices;
        for (int i = 1; i <= this.numOfVertices; i++) {
            this.vertices.add(new Circle(300 + RADIUS * Math.cos(i * (2 * Math.PI) / this.numOfVertices), 300 - RADIUS * Math.sin(i * (2 * Math.PI) / this.numOfVertices), 30, Color.BLACK));
            ArrayList<Double> tmp = new ArrayList<>();
            tmp.add(this.vertices.get(i - 1).getCenterX());
            tmp.add(this.vertices.get(i - 1).getCenterY());
            this.coordinates.add(tmp);
            this.texts.add(new Text(this.vertices.get(i - 1).getCenterX() + 10 * Math.cos(i * (2 * Math.PI) / this.numOfVertices), this.vertices.get(i - 1).getCenterY() - 10 * Math.sin(i * (2 * Math.PI) / this.numOfVertices), "" + i));
            this.texts.get(i - 1).setFont(Font.font("Ariel", FontWeight.BOLD, 20));
            this.texts.get(i - 1).setFill(Color.WHITE);
        }
        showGraph();
    }

    public void showGraph() {
        this.pane.getChildren().addAll(this.vertices);
        this.pane.getChildren().addAll(this.texts);
    }

    public void addEdge(int i, int j) {
        if (i != j && i >= 1 && i <= this.numOfVertices && j >= 1 && j <= this.numOfVertices && !isMaxEdges() && !isEdgeExists(i, j)) {
            HashSet<Integer> tmp = new HashSet<>();
            tmp.add(i);
            tmp.add(j);
            if (tmp.size() == 2) {
                this.edgesSet.add(tmp);
                this.numOfEdges++;
                this.graph.addEdge(i, j, true);
                this.edges.add(new Line(this.vertices.get(i - 1).getCenterX(), this.vertices.get(i - 1).getCenterY(), this.vertices.get(j - 1).getCenterX(), this.vertices.get(j - 1).getCenterY()));
                this.pane.getChildren().add(this.edges.get(this.edges.size() - 1));
            }
        }
    }


    public boolean isEdgeExists(int i, int j) {
        Set<Integer> tmp = new HashSet<>();
        tmp.add(i);
        tmp.add(j);
        Iterator<HashSet<Integer>> iterator = this.edgesSet.iterator();
        while (iterator.hasNext()) {
            HashSet<Integer> hashSet = iterator.next();
            if (hashSet.contains(i) && hashSet.contains(j))
                return true;
        }
        return false;
    }

    public boolean isMaxEdges() {
        int counter = 0;
        for (int i = 0; i <= this.numOfVertices; i++)
            counter += i;
        return this.numOfEdges >= counter;
    }


    public void runAlgorithm(Controller controller) {
        Set<Integer> R = new HashSet<>();
        Set<Integer> P = new HashSet<>();
        Set<Integer> X = new HashSet<>();
        for (Integer i : this.graph.getMap().keySet()) {
            P.add(i);
        }
        bronKerbosch(R, P, X);
        readData(controller);
        this.allMaxCliques.clear();
    }

    public void bronKerbosch(Set<Integer> currentClique, Set<Integer> currentVertices, Set<Integer> scannedVertices) {
        writeData(currentClique, currentVertices, scannedVertices);
        if (currentVertices.isEmpty() && scannedVertices.isEmpty()) {
            this.allMaxCliques.add(new HashSet<>(currentClique));
            return;
        }

        Set<Integer> newVertices = new HashSet<>(currentVertices);

        for (Integer vertex : currentVertices) {
            currentClique.add(vertex);
            Set<Integer> vertexNeighbors = new HashSet<>();
            for (int v : this.graph.getMap().get(vertex))
                vertexNeighbors.add(v);
            bronKerbosch(currentClique, intersection(newVertices, vertexNeighbors), intersection(scannedVertices, vertexNeighbors));
            currentClique.remove(vertex);
            newVertices.remove(vertex);
            scannedVertices.add(vertex);
        }
    }

    private void readData(Controller controller) {
        try {
            this.randomAccessFile.seek(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.keyFrame = new KeyFrame(Duration.seconds(2), e -> {
            paintVariation(controller);
        });
        this.timeline = new Timeline();
        this.timeline.getKeyFrames().add(this.keyFrame);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();

    }

    private HashSet<Integer> editVerticesFill(Controller controller, char ch) throws IOException {
        int size = this.randomAccessFile.readInt(), index;
        StringBuffer stringBuffer = new StringBuffer();
        switch (ch){
            case 'r':
                stringBuffer.append("R = ");
                break;
            case 'p':
                stringBuffer.append("P = ");
                break;
            case 'x':
                stringBuffer.append("X = ");
                break;
                default:
                    System.out.println("It's just me, the pouch and the heat of Israel");
        }
        stringBuffer.append("{ ");
        HashSet<Integer> set = new HashSet<>();
        while (size > 0) {
            index = this.randomAccessFile.readInt();
            set.add(index - 1);
            /*if (ch == 'r')
                this.tmpMaxClique.add(index);*/
            stringBuffer.append(index + " ");
            size--;
        }
        stringBuffer.append("}");
        switch (ch) {
            case 'r':
                controller.getDisplayPanel().setrLabel(stringBuffer.toString());
                break;
            case 'p':
                controller.getDisplayPanel().setpLabel(stringBuffer.toString());
                break;
            case 'x':
                controller.getDisplayPanel().setxLabel(stringBuffer.toString());
                break;
            default:
                System.out.println("Not Cool");
                break;
        }
        return set;
    }

    /*private int editVerticesFill(Color color, Controller controller, char set) throws IOException {
        int index;
        int size = this.randomAccessFile.readInt();
        StringBuffer stringBuffer = new StringBuffer("{ ");
        for (int i = 0; i < size; i++) {
            index = this.randomAccessFile.readInt();
            this.vertices.get(index - 1).setFill(color);
            if (set == 'r')
                this.tmpMaxClique.add(index);
            stringBuffer.append(index + " ");
        }
        stringBuffer.append("}");
        switch (set) {
            case 'r':
                controller.getDisplayPanel().setrLabel(stringBuffer.toString());
                break;
            case 'p':
                controller.getDisplayPanel().setpLabel(stringBuffer.toString());
                break;
            case 'x':
                controller.getDisplayPanel().setxLabel(stringBuffer.toString());
                break;
            default:
                System.out.println("What the hell is going on here???");
                break;
        }

        stringBuffer.setLength(0);
        return size;
    }*/

    private void paintVertices(HashSet<Integer> set, Color color) {
        for (int i : set)
            this.vertices.get(i).setFill(color);
    }

    private void paintVariation(Controller controller) {
        try {
            if (this.randomAccessFile.getFilePointer() < this.randomAccessFile.length()) {
                HashSet<Integer> r = editVerticesFill(controller, 'r'); //RED
                HashSet<Integer> p = editVerticesFill(controller, 'p'); //BLUE
                HashSet<Integer> x = editVerticesFill(controller, 'x'); //GREEN
                if (p.isEmpty() && x.isEmpty()) {
                    StringBuffer stringBuffer = new StringBuffer("{ ");
                    for (int i : r)
                        stringBuffer.append((i + 1) + " ");
                    stringBuffer.append("}");
                    controller.getDisplayPanel().addNewMaximalClique(stringBuffer.toString());
                    //.setLength(0);
                }
                paintVertices(r, Color.RED);
                paintVertices(p, Color.BLUE);
                paintVertices(x, Color.GREEN);
                for (int i = 0; i < this.vertices.size(); i++) {
                    if (!r.contains(i) && !p.contains(i) && !x.contains(i))
                        this.vertices.get(i).setFill(Color.BLACK);
                }
            } else {
                this.timeline.stop();
                this.randomAccessFile.setLength(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeData(Set<Integer> currentClique, Set<Integer> currentVertices, Set<Integer> scannedVertices) {
        try {
            this.randomAccessFile.writeInt(currentClique.size());
            for (int r : currentClique)
                this.randomAccessFile.writeInt(r);
            this.randomAccessFile.writeInt(currentVertices.size());
            for (int p : currentVertices)
                this.randomAccessFile.writeInt(p);
            this.randomAccessFile.writeInt(scannedVertices.size());
            for (int x : scannedVertices)
                this.randomAccessFile.writeInt(x);
        } catch (IOException ioe) {
            Platform.exit();
        }
    }

    private Set<Integer> intersection(Set<? extends Integer> first, Set<? extends Integer> second) {
        Set<Integer> tmp = new HashSet<>(first);
        tmp.retainAll(second);
        return tmp;
    }
}
