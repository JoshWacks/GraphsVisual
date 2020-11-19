package mainPackage;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Visuals {
    protected static Canvas canvas;
    protected static double canvasWidth;
    protected static double canvasHeight;

    protected static int vertexCount = 0;
    protected static GraphicsContext gc;
    protected static final Graph graph = new Graph();

    protected int numSelectedVertices = 0;
    protected final Vertex[] selectedVertices = new Vertex[2];


    protected static Canvas displayCanvas;
    protected static GraphicsContext displayGC;

    protected static GraphMethods graphMethods;
    private static ConfigureVisuals configureVisuals;

    protected int weight = -1;

    public Visuals() {

    }

    public Visuals(Canvas c, Canvas dc) {
        canvas = c;
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setLineWidth(4.0D);
        gc.fillRect(canvas.getLayoutX(), 0, canvasWidth, canvasHeight);


        displayCanvas = dc;
        displayGC = displayCanvas.getGraphicsContext2D();
        displayGC.setFill(Color.ANTIQUEWHITE);
        displayGC.fillRect(0, 0, displayCanvas.getWidth(), displayCanvas.getHeight());

        configureVisuals = new ConfigureVisuals();//Calls the sub-class here

        graphMethods = new GraphMethods(graph);

    }


    //Draws a vertex at a required x and y position
    protected void drawVertex(double x, double y, GraphicsContext gc) {

        if (configureVisuals.validVertexPos(x, y)) {

            gc.setFill(Color.BURLYWOOD);
            gc.setLineWidth(2.0D);

            gc.fillOval(x, y, 40, 40);
            gc.setFill(Color.BLACK);
            gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
            if ((vertexCount + "").length() == 1) {
                gc.fillText(vertexCount + "", x + 15, y + 25);
            } else {
                gc.fillText(vertexCount + "", x + 10, y + 25);
            }

            Vertex vertex = new Vertex(vertexCount, x, y);
            vertex.setxCentre(x + 20);
            vertex.setyCentre(y + 20);
            vertex.setVertexValue(vertexCount + "");//sets the default value of the vertexValue to its number
            graph.addVertex(vertex);
            vertexCount++;
        } else {
            JOptionPane.showMessageDialog(null, "Please choose a valid position on the board \nNot too close to the end of the board or another vertex");
        }
    }

    //Highlights a specific vertex on the board
    protected void highlightVertex(Vertex v, Color color) {
        if (v.isRoot() || v.isDest()) {
            return;
        }
        gc.setStroke(color);
        gc.setLineWidth(3.0D);

        gc.strokeOval(v.getxPos(), v.getyPos(), 40, 40);
        gc.strokeOval(v.getxPos(), v.getyPos(), 41, 41);
    }

    //Unhighlight a specific vertex on the board
    protected void unhighlightVertex(Vertex v) {
        if (v.isRoot() || v.isDest()) {
            return;
        }
        gc.setStroke(Color.WHITE);

        gc.strokeOval(v.getxPos(), v.getyPos(), 40, 40);
        gc.strokeOval(v.getxPos(), v.getyPos(), 41, 41);
    }

    //Methods to edit the value of a vertex
    protected void editVertex(double x, double y) {//gets the x and y of the selected vertex
        Vertex vertex = configureVisuals.getSelectedVertex(x, y);
        if (vertex.getxPos() == -1) {
            JOptionPane.showMessageDialog(null, "Please select a vertex");
            return;
        }
        highlightVertex(vertex, Color.LIME);

        String newValue = JOptionPane.showInputDialog("Please enter the new value of the vertex");
        boolean unique;

        do {//ensures the value they are entering now is not the same of any other vertices

            unique = true;
            for (Vertex v : graph.getVertices()) {
                if (v.getVertexValue().equals(newValue) && !v.equals(vertex) || newValue == null || newValue.equals("")) {
                    unique = false;//we need to ask them for a new value
                    newValue = JOptionPane.showInputDialog("There is already a vertex that exists with that value and the value cannot be null");
                    break;
                }
            }


        } while (!unique);

        vertex.setVertexValue(newValue);
        repaint();


    }

    //Allows the user to select which root they would like to be the vertex
    protected void selectRoot(double x, double y) {
        Vertex vertex = configureVisuals.getSelectedVertex(x, y);
        if (vertex.getxPos() == -1) {
            JOptionPane.showMessageDialog(null, "Please select a valid vertex");
            return;
        }
        Vertex root = graph.getRoot();

        if (vertex.equals(root)) {
            root.setRoot(false);
            unhighlightVertex(root);
            graph.setRoot(null);
            return;
        }

        if (root != null) {
            root.setRoot(false);
            unhighlightVertex(root);
            graph.setRoot(null);
        }
        Vertex dest = graph.getDestination();

        if (dest != null && dest.equals(vertex)) {
            dest.setDest(false);
            graph.setDestination(null);
        }

        highlightVertex(vertex, Color.FORESTGREEN);

        vertex.setRoot(true);
        graph.setRoot(vertex);
    }

    protected void selectDest(Double x, Double y) {
        Vertex vertex = configureVisuals.getSelectedVertex(x, y);
        if (vertex.getxPos() == -1) {
            JOptionPane.showMessageDialog(null, "Please select a valid vertex");
            return;
        }
        Vertex dest = graph.getDestination();

        if (vertex.equals(dest)) {//deselecting a destination
            dest.setDest(false);
            unhighlightVertex(dest);
            graph.setDestination(null);
            return;
        }

        if (dest != null) {
            dest.setDest(false);
            unhighlightVertex(dest);
            graph.setDestination(null);
        }

        Vertex root = graph.getRoot();

        if (root != null && root.equals(vertex)) {
            root.setRoot(false);
            graph.setRoot(null);
        }

        highlightVertex(vertex, Color.BLUEVIOLET);

        vertex.setDest(true);
        graph.setDestination(vertex);

    }

    protected void drawEdge(Vertex[] arr) {
        Vertex v0 = arr[0];
        Vertex v1 = arr[1];

        Double[] points;

        if (v0.getxPos() < v1.getxPos() || v0.getxPos() == v1.getxPos()) {
            points = configureVisuals.findCorrectPoints(arr);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3.0D);
            gc.strokeLine(points[0], points[1], points[2], points[3]);

        } else {
            Vertex[] newArr = new Vertex[2];
            newArr[0] = v1;
            newArr[1] = v0;
            points = configureVisuals.findCorrectPoints(newArr);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3.0D);
            gc.strokeLine(points[0], points[1], points[2], points[3]);
        }
        Pair<Double, Double> pair1 = new Pair<>(points[0], points[1]);
        Pair<Double, Double> pair2 = new Pair<>(points[2], points[3]);

        Edge edge = new Edge(v0, v1, pair1, pair2);
        graph.addEdge(edge);
    }

    protected void drawArcEdge(Vertex vertex) {
        double cx = vertex.getxCentre();
        double cy = vertex.getyCentre();
        gc.setStroke(Color.BLACK);


        gc.strokeArc(cx, cy + 2, 40, 35, 180, 270, ArcType.OPEN);
        Pair<Double, Double> pair1 = new Pair<>(vertex.getxPos(), vertex.getyPos());
        Pair<Double, Double> pair2 = new Pair<>(vertex.getxPos(), vertex.getyPos());
        Edge edge = new Edge(vertex, vertex, pair1, pair2);
        graph.addEdge(edge);
    }

    protected void drawWeightedArcEdge(Vertex vertex) {
        double cx = vertex.getxCentre();
        double cy = vertex.getyCentre();
        gc.setStroke(Color.BLACK);
        gc.strokeArc(cx, cy + 2, 40, 35, 180, 270, ArcType.OPEN);
        String temp = "";
        while (weight == -1) {
            temp = JOptionPane.showInputDialog("Please enter a weight for this edge");

            if ((temp != null) && temp.length() > 0) {
                weight = Integer.parseInt(temp);
            } else {
                weight = -1;
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
            }

        }

        gc.setFill(Color.BLACK);
        gc.setLineWidth(2.0D);
        gc.fillRect(cx + 15, cy + 18, 25, 25);

        gc.setFill(Color.WHITE);
        int incr;
        if (temp.length() == 1) {
            incr = 3;
        } else {
            incr = 8;
        }
        gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 17));
        gc.fillText(weight + "", cx + 25 - incr, cy + 28 + 7);

        Pair<Double, Double> pair1 = new Pair<>(vertex.getxPos(), vertex.getyPos());

        WeightedEdge weightedEdge = new WeightedEdge(vertex, vertex, weight, pair1, pair1);
        graph.addWeightedEdge(weightedEdge);
        weight = -1;
    }

    protected void drawWeightedEdge(Vertex[] arr) {

        String temp = "";
        while (weight == -1) {
            temp = JOptionPane.showInputDialog("Please enter a weight for this edge");

            if ((temp != null) && temp.length() > 0) {
                weight = Integer.parseInt(temp);
            } else {
                weight = -1;
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
            }

        }

        Vertex v0 = arr[0];
        Vertex v1 = arr[1];
        WeightedEdge weightedEdge;

        Double[] points;
        Vertex[] newArr = new Vertex[2];

        if (!(v0.getxPos() <= v1.getxPos())) {//correct order for drawing
            Vertex tempVertex = v1;
            v1 = v0;
            v0 = tempVertex;
            newArr[0] = v1;
            newArr[1] = v0;
        } else {
            newArr = arr;
        }

        gc.setStroke(Color.BLACK);
        points = configureVisuals.findCorrectPoints(newArr);
        Pair<Double, Double> pair1 = new Pair<>(points[0], points[1]);
        Pair<Double, Double> pair2 = new Pair<>(points[2], points[3]);

        weightedEdge = new WeightedEdge(v0, v1, weight, pair1, pair2);

        double[] midpoint = configureVisuals.getMidpoint(v0, v1);
        weightedEdge.setMidPoint(midpoint);
        graph.addWeightedEdge(weightedEdge);
        gc.setLineWidth(3.0D);
        gc.strokeLine(points[0], points[1], points[2], points[3]);


        gc.setFill(Color.BLACK);
        gc.setLineWidth(2.0D);
        gc.fillRect(midpoint[0] - 15, midpoint[1] - 15, 35, 35);

        gc.setFill(Color.WHITE);
        int incr;
        if (temp.length() == 1) {
            incr = 3;
        } else {
            incr = 8;
        }
        gc.fillText(weight + "", midpoint[0] - incr, midpoint[1] + 7);
        weight = -1;


    }

    public static void showList() {
        displayGC.setFill(Color.ANTIQUEWHITE);
        displayGC.fillRect(0, 0, displayCanvas.getWidth(), displayCanvas.getHeight());

        displayGC.setFill(Color.BLACK);
        displayGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 16));
        displayGC.fillText("Vertex |                                         Neighbourhood ", 2, 13);
        displayGC.fillText("_____________________________________________________________________________", 0, 14);

        int y = 30;
        int x;
        for (Vertex v : graph.getVertices()) {
            displayGC.fillText(v.getVertexValue() + "", 20, y);
            displayGC.fillText("|", 49, y);
            x = 55;
            for (Vertex vertex : v.getAdjacencies()) {
                displayGC.fillText(vertex.getVertexValue() + "", x, y);
                int incr = 0;
                if (vertex.getVertexValue().length() > 1) {
                    incr = 8;
                }
                displayGC.fillText(",", x + 10 + incr, y);
                x = x + 20 + incr;
            }
            displayGC.fillText("_____________________________________________________________________________", 0, y + 1);
            y = y + 16;


        }
    }

    public static void showMatrix() {

        displayGC.setFill(Color.ANTIQUEWHITE);
        displayGC.fillRect(0, 0, displayCanvas.getWidth(), displayCanvas.getHeight());//resets the display canvas
        int n = graph.getNumberVertices();

        boolean[][] adj_matrix = new boolean[n][n];//sets the adjacency matrix to how many vertices there are

        for (Vertex v : graph.getVertices()) {//makes the matrix based off the linked list we have
            for (Vertex adj : v.getAdjacencies()) {
                adj_matrix[v.getVertexNumber()][adj.getVertexNumber()] = true;//Sets all the adjacency's throughout the graph

            }
        }

        displayGC.setFill(Color.BLACK);
        displayGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 18));
        int count = -1;
        for (int i = 0; i < n; i++) {
            displayGC.setFill(Color.BLACK);
            Vertex temp = graph.getVertex(i);

            if (temp == null) {
                continue;
            }
            count++;//Keeps track of the actual vertex we are on
            String value = temp.getVertexValue();
            displayGC.fillText(value, 30 + (count * 25), 15);//horizontal number
            displayGC.fillText("|", 45 + (count * 25), 15);

            displayGC.fillText(value, 10, 40 + (count * 25));//vertical numbers
            displayGC.fillText("____", 0, 43 + (count * 25));
            displayGC.fillText("|", 22, 40 + (count * 25));


            //Adjustments for the vertical and horizontal headings
            for (int j = 0; j < graph.getVertices().size(); j++) {//we only to fill in points of vertices that still exits
                if (adj_matrix[i][j]) {
                    displayGC.setFill(Color.BLUE);//blue when there is an edge

                    if (ConfigureVisuals.isWeightedEdge(i, j)[0] == 1) {//If it is a weighted edge we want to display the weight in the matrix
                        int incr = 0;
                        if (ConfigureVisuals.isWeightedEdge(i, j)[1] > 9 || ConfigureVisuals.isWeightedEdge(i, j)[1] < -9) {//more than 2 digits so we need to account for that spacing
                            incr = -3;
                        }
                        displayGC.fillText(ConfigureVisuals.isWeightedEdge(i, j)[1] + "", 30 + (j * 25) + incr, 40 + (count * 25));
                    } else {
                        displayGC.fillText("T", 30 + (j * 25), 40 + (count * 25));
                    }

                } else {

                    displayGC.setFill(Color.RED);//Red colour when no edge
                    displayGC.fillText("F", 30 + (j * 25), 40 + (count * 25));
                }
                displayGC.setFill(Color.BLACK);
                displayGC.fillText("|", 45 + (j * 25), 40 + (count * 25));
                displayGC.fillText("_____", j * 27, 43 + (count * 25));


            }
        }

    }


    public static void callAlg() {
        ConfigureVisuals.callAlgMethod();
    }

    protected static void checkCompleted() {
        displayGC.setFill(Color.ANTIQUEWHITE);
        displayGC.fillRect(0, 0, displayCanvas.getWidth(), displayCanvas.getHeight());
        displayGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 28));
        displayGC.setFill(Color.DODGERBLUE);

        if (graphMethods.checkComplete()) {
            displayGC.fillText("This is a complete graph", 5, displayCanvas.getHeight() - 20);
        } else {
            displayGC.fillText("This is an incomplete graph", 5, displayCanvas.getHeight() - 20);

        }
    }

    protected static void checkConnected() {
        displayGC.setFill(Color.ANTIQUEWHITE);
        displayGC.fillRect(0, 0, displayCanvas.getWidth(), displayCanvas.getHeight());
        displayGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 28));
        displayGC.setFill(Color.DODGERBLUE);
        if (graphMethods.checkConnectedGraph()) {
            displayGC.fillText("This is a connected graph", 5, displayCanvas.getHeight() - 20);
        } else {
            displayGC.fillText("This is a non-connected graph", 5, displayCanvas.getHeight() - 20);

        }

    }


    protected static void colourGraph() {
        repaint();
        ArrayList<Pair<Vertex, Integer>> pairs = graphMethods.colourGraph();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Runnable colour = () -> {

            for (int i = 0; i < pairs.size(); i++) {
                Pair<Vertex, Integer> pair = pairs.get(i);
                if (!pair.getValue().equals(-1)) {
                    gc.setLineWidth(2.0D);
                    gc.setFill(ConfigureVisuals.getColour(pair.getValue()));
                    gc.fillOval(pair.getKey().getxPos(), pair.getKey().getyPos(), 40, 40);

                    gc.setFill(Color.BLACK);
                    gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
                    if ((pair.getKey().getVertexValue()).length() == 1) {
                        gc.fillText(pair.getKey().getVertexValue(), pair.getKey().getxPos() + 15, pair.getKey().getyPos() + 25);
                    } else {
                        gc.fillText(pair.getKey().getVertexValue(), pair.getKey().getxPos() + 10, pair.getKey().getyPos() + 25);
                    }

                    Pair<Vertex, Integer> tempPair = new Pair<>(pair.getKey(), -1);
                    pairs.set(i, tempPair);
                    break;
                }
            }
            Pair<Vertex, Integer> pair = pairs.get(pairs.size() - 1);
            if (pair.getValue() == -1) {//if the last vertex has been coloured

                executorService.shutdown();
            }
        };


        Platform.runLater(() -> {//Method of running on the UI thread
            executorService.scheduleAtFixedRate(colour, 1000, 1700, TimeUnit.MILLISECONDS);//every 1700 milliseconds these methods are run

        });

    }


    protected static void repaint() {
        gc.setFill(Color.WHITE);
        gc.setLineWidth(4.0D);
        gc.fillRect(canvas.getLayoutX(), 0, canvasWidth, canvasHeight);

        for (Vertex v : graph.getVertices()) {
            gc.setFill(Color.BURLYWOOD);
            gc.setLineWidth(2.0D);

            gc.fillOval(v.getxPos(), v.getyPos(), 40, 40);
            gc.setFill(Color.BLACK);
            gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
            if (v.getVertexValue().length() == 1) {
                gc.fillText(v.getVertexValue(), v.getxPos() + 15, v.getyPos() + 25);
            } else {
                gc.fillText(v.getVertexValue(), v.getxPos() + 10, v.getyPos() + 25);
            }

        }

        for (Edge e : graph.getEdges()) {

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3.0D);
            gc.strokeLine(e.firstXY.getKey(), e.firstXY.getValue(), e.secondXY.getKey(), e.secondXY.getValue());


        }

        for (WeightedEdge w : graph.getWeightedEdges()) {

            redrawWeightedEdge(w);

        }

    }

    protected static void redrawWeightedEdge(WeightedEdge weightedEdge) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3.0D);
        gc.strokeLine(weightedEdge.firstXY.getKey(), weightedEdge.firstXY.getValue(), weightedEdge.secondXY.getKey(), weightedEdge.secondXY.getValue());

        gc.setFill(Color.BLACK);
        gc.setLineWidth(2.0D);
        gc.fillRect(weightedEdge.getMidPoint()[0] - 15, weightedEdge.getMidPoint()[1] - 15, 35, 35);

        gc.setFill(Color.WHITE);
        int incr;
        String temp = weightedEdge.getWeight() + "";
        if (temp.length() == 1) {
            incr = 3;
        } else {
            incr = 8;
        }
        gc.fillText(weightedEdge.getWeight() + "", weightedEdge.getMidPoint()[0] - incr, weightedEdge.getMidPoint()[1] + 7);


    }


    protected static void makeShortestPathTree() {
        if (graph.getRoot() == null) {
            JOptionPane.showMessageDialog(null, "Please select a root first");
            return;
        }
        ArrayList<Integer[]> results = graphMethods.dijkstra();
        if (results == null) {
            return;
        }
        Integer[] parent = results.get(0);
        Integer[] cost = results.get(1);
        ArrayList<WeightedEdge> usedEdges = new ArrayList<>();


        for (int i = 1; i < parent.length; i++) {//We go through the parent array to determine which edges were used
            int parentNum = parent[i];
            usedEdges.add(graph.getWeightedEdgeBetweenNumbers(parentNum, i));
        }
        if (usedEdges.isEmpty()) {
            return;
        }
        constructShortestPathTree(usedEdges);
        showDistances(cost);
    }

    protected static void showDistances(Integer[] cost) {
        displayGC.setFill(Color.ANTIQUEWHITE);
        displayGC.fillRect(0, 0, displayCanvas.getWidth(), displayCanvas.getHeight());//Clears the display canvas


        displayGC.setFill(Color.BLACK);
        displayGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 16));
        displayGC.fillText("Vertex |                                         Cost From Vertex " + graph.getRoot().getVertexValue(), 2, 13);
        displayGC.fillText("_____________________________________________________________________________", 0, 14);

        int vertexNumber = 0;
        for (Integer integer : cost) {
            displayGC.fillText(vertexNumber + "      |", 20, 30 + vertexNumber * 20);
            displayGC.fillText(integer + "", 200, 30 + vertexNumber * 20);
            displayGC.fillText("_____________________________________________________________________________", 0, (31 + vertexNumber * 20));
            vertexNumber++;
        }


    }

    protected static void constructShortestPathTree(ArrayList<WeightedEdge> usedEdges) {
        Stage stage = new Stage();
        stage.setX(0);//set it on the top left of the screen
        stage.setY(0);
        stage.setTitle("View Created Graph");
        Group group = new Group();
        stage.setResizable(false);

        Canvas generatedCanvas = new Canvas(canvasWidth, canvasHeight);

        generatedCanvas.setLayoutX(0);
        generatedCanvas.setLayoutY(100);
        group.getChildren().add(generatedCanvas);
        GraphicsContext generatedGC = generatedCanvas.getGraphicsContext2D();
        generatedGC.setFill(Color.WHITE);
        generatedGC.setLineWidth(2.0D);
        generatedGC.fillRect(0, 0, canvasWidth, canvasHeight);

        Vertex v0;
        Vertex v1;
        Pair<Double, Double> pair0;
        Pair<Double, Double> pair1;

        for (WeightedEdge weightedEdge : usedEdges) {
            v0 = weightedEdge.vertexA;
            v1 = weightedEdge.vertexB;

            generatedGC.setLineWidth(2.0D);
            generatedGC.setFill(Color.BURLYWOOD);
            generatedGC.fillOval(v0.getxPos(), v0.getyPos(), 40, 40);
            generatedGC.fillOval(v1.getxPos(), v1.getyPos(), 40, 40);

            generatedGC.setFill(Color.BLACK);
            generatedGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
            if (v0.getVertexValue().length() == 1) {
                generatedGC.fillText(v0.getVertexValue(), v0.getxPos() + 15, v0.getyPos() + 25);
            } else {
                generatedGC.fillText(v0.getVertexValue(), v0.getxPos() + 10, v0.getyPos() + 25);
            }

            if (v1.getVertexValue().length() == 1) {
                generatedGC.fillText(v1.getVertexValue(), v1.getxPos() + 15, v1.getyPos() + 25);
            } else {
                generatedGC.fillText(v1.getVertexValue(), v1.getxPos() + 10, v1.getyPos() + 25);
            }

            pair0 = weightedEdge.firstXY;
            pair1 = weightedEdge.secondXY;
            generatedGC.setLineWidth(3.0D);
            generatedGC.strokeLine(pair0.getKey(), pair0.getValue(), pair1.getKey(), pair1.getValue());

            double[] midpoint = ConfigureVisuals.getMidpoint(v0, v1);

            generatedGC.fillRect(midpoint[0] - 15, midpoint[1] - 15, 35, 35);

            generatedGC.setFill(Color.WHITE);
            int incr;
            String temp = weightedEdge.getWeight() + "";
            if (temp.length() == 1) {
                incr = 3;
            } else {
                incr = 8;
            }
            generatedGC.fillText(temp + "", midpoint[0] - incr, midpoint[1] + 7);

        }


        Scene scene = new Scene(group, canvasWidth - 10, canvasHeight + 90);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);

        stage.show();


    }


    protected static void deleteEdge(double x, double y) {
        if (ConfigureVisuals.getSelectedEdge(x, y).vertexA.getVertexNumber() == -1) {
            JOptionPane.showMessageDialog(null, "Please make sure to select an edge");
            return;
        }
        if (!ConfigureVisuals.getSelectedEdge(x, y).isWeightedEdge()) {
            Edge selectedEdge = ConfigureVisuals.getSelectedEdge(x, y);
            int option = JOptionPane.showConfirmDialog(null, "You have selected the edge between " + selectedEdge.vertexA.getVertexValue() + " and " + selectedEdge.vertexB.getVertexValue() +
                    "\nPress yes to delete this edge");

            if (option == JOptionPane.YES_OPTION) {
                graph.deleteEdge(selectedEdge);
                repaint();
            }
        } else {
            WeightedEdge selectedEdge = (WeightedEdge) ConfigureVisuals.getSelectedEdge(x, y);
            int option = JOptionPane.showConfirmDialog(null, "You have selected the edge between " + selectedEdge.vertexA.getVertexValue() + " and " + selectedEdge.vertexB.getVertexValue() +
                    "\nPress yes to delete this edge");

            if (option == JOptionPane.YES_OPTION) {
                graph.deleteWeightedEdge(selectedEdge);
                repaint();
            }
        }
    }


    protected void deleteVertex(double x, double y) {
        Vertex vertex = configureVisuals.getSelectedVertex(x, y);
        if (vertex.getxPos() == -1) {
            JOptionPane.showMessageDialog(null, "Please select a vertex");
            return;
        }
        highlightVertex(vertex, Color.LIME);
        int option = JOptionPane.showConfirmDialog(null, "You have selected Vertex " + vertex.getVertexValue() +
                "\nYou will delete all the edges connected to this Vertex" +
                "\nPress yes to delete this Vertex");

        if (option == JOptionPane.YES_OPTION) {
            graph.deleteVertex(vertex);
            repaint();
        }


    }

    public static void callSearch() {
        configureVisuals.callSearchMethod();


    }
}
