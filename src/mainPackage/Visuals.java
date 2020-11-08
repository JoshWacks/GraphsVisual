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

    private int weight = -1;

    public Visuals(){

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

        canvas.setOnMouseClicked(event -> callCorrectDrawMethod(event.getX(), event.getY()));

        graphMethods = new GraphMethods(graph);

    }

    private void callCorrectDrawMethod(double x, double y) {

        switch (ConfigureScreen.getBtnSelected()) {
            case 0:
                JOptionPane.showMessageDialog(null, "Please select an option from the draw menu first");
                return;
            case 1:
                drawVertex(x - 5, y - 5, gc);//we offset it by 5 to find the a more precise location of the pointer
                return;
            case 4:
                deleteVertex(x,y);
                return;
            case 5:
                deleteEdge(x,y);
                return;
            case 6:
                selectRoot(x,y);
                return;
            case 7:
                selectDest(x,y);
                return;

        }
        if (ConfigureScreen.getBtnSelected() == 2 || ConfigureScreen.getBtnSelected() == 3) {
            if (getSelectedVertex(x - 5, y - 5).getVertexNumber() != -1) {
                Vertex v = getSelectedVertex(x - 5, y - 5);
                if (numSelectedVertices == 0) {
                    selectedVertices[0] = v;
                    highlightVertex(v);
                    numSelectedVertices = 1;
                } else if (numSelectedVertices == 1) {
                    selectedVertices[1] = v;
                    highlightVertex(v);
                    unhighlightVertex(selectedVertices[0]);
                    unhighlightVertex(selectedVertices[1]);
                    if(!graph.validEdge(selectedVertices[0],selectedVertices[1])){
                        JOptionPane.showMessageDialog(null,"An edge already exists between these 2 vertices");
                        numSelectedVertices = 0;
                        return;
                    }


                    if (ConfigureScreen.getBtnSelected() == 2) {//2 stands for normal edge
                        if (selectedVertices[0].equals(selectedVertices[1])) {
                            drawArcEdge(selectedVertices[0]);
                        } else {
                            drawEdge(selectedVertices);
                        }
                    } else if(ConfigureScreen.getBtnSelected() == 3) {//3 is a weighted
                        if (selectedVertices[0].equals(selectedVertices[1])) {
                            drawWeightedArcEdge(selectedVertices[0]);
                        } else {
                            drawWeightedEdge(selectedVertices);
                        }

                    }
                    numSelectedVertices = 0;
                }
            } else {

                JOptionPane.showMessageDialog(null, "Please select a vertex");
            }
        }

    }
    //Draws a vertex at a required x and y position
    private void drawVertex(double x, double y, GraphicsContext gc) {

        if (validVertexPos(x, y)) {

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
            graph.addVertex(vertex);
            vertexCount++;
        } else {
            JOptionPane.showMessageDialog(null, "Please choose a valid position on the board \nNot too close to the end of the board or another vertex");
        }
    }

    //Ensures the selected vertex is in the correct position
    private boolean validVertexPos(double x, double y) {
        Double tempX, tempY;
        if ((y + 40) > canvasHeight || (x - 40) < 0 || (x + 40) > canvasWidth || (y - 40) < 0) {//basic bounds checking
            return false;
        }

        for (Vertex v : graph.getVertices()) {
            tempX = v.getxPos();
            tempY = v.getyPos();
            if ((tempX - 40 < x && x < tempX + 40) && (tempY - 40 < y && y < tempY + 40)) {
                return false;
            }
        }

        return true;
    }

    //Returns a Vertex at a specific x y position
    private Vertex getSelectedVertex(double x, double y) {
        double tempX, tempY;
        for (Vertex v : graph.getVertices()) {
            tempX = v.getxPos();
            tempY = v.getyPos();
            if ((tempX - 40 < x && x < tempX + 40) && (tempY - 40 < y && y < tempY + 40)) {
                return v;
            }
        }

        return new Vertex(-1, -1, -1);

    }

    //Highlights a specific vertex on the board
    private void highlightVertex(Vertex v) {
        if(v.isRoot()){
            return;
        }
        gc.setStroke(Color.LIME);
        gc.setLineWidth(4.0D);
        gc.strokeOval(v.getxPos(), v.getyPos(), 40, 40);
    }

    //Unhighlight a specific vertex on the board
    private void unhighlightVertex(Vertex v) {
        if(v.isRoot() || v.isDest()){
            return;
        }
        gc.setStroke(Color.WHITE);

        gc.strokeOval(v.getxPos(), v.getyPos(), 40, 40);
        gc.strokeOval(v.getxPos(), v.getyPos(), 41, 41);
    }

    //Allows the user to select which root they would like to be the vertex
    private void selectRoot(double x,double y){
        Vertex vertex=getSelectedVertex(x,y);
        if(vertex.getxPos()==-1){
            JOptionPane.showMessageDialog(null,"Please select a valid vertex");
            return;
        }
        Vertex root=graph.getRoot();

        if(vertex.equals(root)){
            root.setRoot(false);
            unhighlightVertex(root);
            graph.setRoot(null);
            return;
        }

        if(root!=null){
            root.setRoot(false);
            unhighlightVertex(root);
            graph.setRoot(null);
        }
        Vertex dest=graph.getDestination();

        if(dest!=null && dest.equals(vertex)){
            dest.setDest(false);
            graph.setDestination(null);
        }

        vertex.setRoot(true);
        graph.setRoot(vertex);

        gc.setStroke(Color.FORESTGREEN);
        gc.setLineWidth(4.0D);
        gc.strokeOval(vertex.getxPos(), vertex.getyPos(),40, 40);
    }

    private void selectDest(Double x,Double y){
        Vertex vertex=getSelectedVertex(x,y);
        if(vertex.getxPos()==-1){
            JOptionPane.showMessageDialog(null,"Please select a valid vertex");
            return;
        }
        Vertex dest=graph.getDestination();

        if( vertex.equals(dest)){//deselecting a destination
            dest.setDest(false);
            unhighlightVertex(dest);
            graph.setDestination(null);
            return;
        }

        if(dest!=null){
            dest.setDest(false);
            unhighlightVertex(dest);
            graph.setDestination(null);
        }

        Vertex root=graph.getRoot();

        if(root != null && root.equals(vertex)){
            root.setRoot(false);
            graph.setRoot(null);
        }

        vertex.setDest(true);
        graph.setDestination(vertex);

        gc.setStroke(Color.BLUEVIOLET);
        gc.setLineWidth(4.0D);
        gc.strokeOval(vertex.getxPos(), vertex.getyPos(),40, 40);
    }

    private void drawEdge(Vertex[] arr) {
        Vertex v0 = arr[0];
        Vertex v1 = arr[1];

        Double[] points ;

        if (v0.getxPos() < v1.getxPos() || v0.getxPos() == v1.getxPos()) {
            points = findCorrectPoints(arr);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3.0D);
            gc.strokeLine(points[0], points[1], points[2], points[3]);

        } else {
            Vertex[] newArr = new Vertex[2];
            newArr[0] = v1;
            newArr[1] = v0;
            points = findCorrectPoints(newArr);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3.0D);
            gc.strokeLine(points[0], points[1], points[2], points[3]);
        }
        Pair<Double,Double> pair1=new Pair<>(points[0],points[1]);
        Pair<Double,Double> pair2=new Pair<>(points[2],points[3]);

        Edge edge=new Edge(v0,v1,pair1,pair2);
        graph.addEdge(edge);
    }

    private void drawArcEdge(Vertex vertex) {
        double cx = vertex.getxCentre();
        double cy = vertex.getyCentre();
        gc.setStroke(Color.BLACK);


        gc.strokeArc(cx, cy + 2, 40, 35, 180, 270, ArcType.OPEN);
        Pair<Double,Double> pair1=new Pair<>(vertex.getxPos(),vertex.getyPos());
        Pair<Double,Double> pair2=new Pair<>(vertex.getxPos(),vertex.getyPos());
        Edge edge=new Edge(vertex,vertex,pair1,pair2);
        graph.addEdge(edge);
    }

    private void drawWeightedArcEdge(Vertex vertex) {
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
        gc.fillRect(cx+15, cy+18, 25, 25);

        gc.setFill(Color.WHITE);
        int incr;
        if (temp.length() == 1) {
            incr = 3;
        } else {
            incr = 8;
        }
        gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 17));
        gc.fillText(weight + "", cx+25 - incr, cy+28 + 7);

        Pair<Double,Double> pair1=new Pair<>(vertex.getxPos(),vertex.getyPos());

        WeightedEdge weightedEdge=new WeightedEdge(vertex,vertex,weight,pair1,pair1);
        graph.addWeightedEdge(weightedEdge);
        weight = -1;
    }

    private void drawWeightedEdge(Vertex[] arr) {

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
        points = findCorrectPoints(newArr);
        Pair<Double,Double> pair1=new Pair<>(points[0],points[1]);
        Pair<Double,Double> pair2=new Pair<>(points[2],points[3]);
        if (v0.getVertexNumber() < v1.getVertexNumber()) {//making sure the smallest vertex is always first
            weightedEdge = new WeightedEdge(v0, v1, weight,pair1,pair2);
        } else {
            weightedEdge = new WeightedEdge(v1, v0, weight,pair1,pair2);
        }
        double[] midpoint = getMidpoint(v0, v1);
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

    private Double[] findCorrectPoints(Vertex[] arr) {
        Double[] correctPoints = new Double[4];
        Double shortestDistance = 5000D;
        Double tempDistance;
        Vertex v0 = arr[0];
        Vertex v1 = arr[1];

        double x1 = v0.getxPos();
        double y1 = v0.getyPos();
        double x2 = v1.getxPos();
        double y2 = v1.getyPos();

        double c1x = v0.getxCentre();
        double c1y = v0.getyCentre();
        double c2x = v1.getxCentre();
        double c2y = v1.getyCentre();


        ArrayList<Pair<Double, Double>> points1 = new ArrayList<>();
        ArrayList<Pair<Double, Double>> points2 = new ArrayList<>();

        if (x1 < x2 || x1 == x2) {
            for (Double i = x1; i < x1 + 40; i = i + 0.5) {
                for (Double j = y1; j < y1 + 45; j = j + 0.5) {
                    if (onCircle(c1x, c1y, i, j)) {
                        Pair<Double, Double> pair = new Pair<>(i, j);
                        points1.add(pair);
                    }

                }
            }

            for (Double i = x2; i < x2 + 40; i = i + 0.5) {
                for (Double j = y2; j < y2 + 45; j = j + 0.5) {
                    if (onCircle(c2x, c2y, i, j)) {

                        Pair<Double, Double> pair = new Pair<>(i, j);
                        points2.add(pair);
                    }
                }
            }

            for (Pair<Double, Double> p1 : points1) {
                for (Pair<Double, Double> p2 : points2) {
                    tempDistance = getDistance(p1.getKey(), p1.getValue(), p2.getKey(), p2.getValue());

                    if (tempDistance < shortestDistance) {

                        shortestDistance = tempDistance;
                        correctPoints[0] = p1.getKey();
                        correctPoints[1] = p1.getValue();
                        correctPoints[2] = p2.getKey();
                        correctPoints[3] = p2.getValue();
                    }
                }
            }
        } else {
            Vertex[] newArr = new Vertex[2];
            newArr[0] = v1;
            newArr[1] = v0;
            correctPoints = findCorrectPoints(newArr);
        }
        return correctPoints;


    }

    public Double getDistance(Double x1, Double y1, Double x2, Double y2) {
        Double firstSqaure = Math.pow((x2 - x1), 2);
        Double secondSqaure = Math.pow((y2 - y1), 2);
        Double sum = firstSqaure + secondSqaure;


        return Math.sqrt(sum);
    }

    private boolean onCircle(Double cx, Double cy, Double x, Double y) {
        Double b1 = Math.pow((x - cx), 2);
        Double b2 = Math.pow((y - cy), 2);

        return (b1 + b2) == 400;
    }

    private static double[] getMidpoint(Vertex v1, Vertex v2) {
        double[] midpoint = new double[2];

        double xMid = (v1.getxCentre() + v2.getxCentre()) / 2.0;
        double yMid = (v1.getyCentre() + v2.getyCentre()) / 2.0;

        midpoint[0] = xMid;
        midpoint[1] = yMid;
        return midpoint;
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
            displayGC.fillText(v.getVertexNumber() + "", 20, y);
            displayGC.fillText("|", 49, y);
            x = 55;
            for (Vertex vertex : v.getAdjacencies()) {
                displayGC.fillText(vertex.getVertexNumber() + "", x, y);
                int incr = 0;
                if (vertex.getVertexNumber() > 9 || vertex.getVertexNumber() < -9) {
                    incr = 8;
                } else {
                    incr = 0;
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
        int count =-1;
        for (int i = 0; i < n; i++) {
            displayGC.setFill(Color.BLACK);
            Vertex temp=graph.getVertex(i);

            if(temp==null){
                continue;
            }
            count++;//Keeps track of the actual vertex we are on
            int num = temp.getVertexNumber();
            displayGC.fillText(num + "", 30 + (count * 25), 15);//horizontal number
            displayGC.fillText("|", 45 + (count * 25), 15);

            displayGC.fillText(num + "", 10, 40 + (count * 25));//vertical numbers
            displayGC.fillText("____", 0, 43 + (count * 25));
            displayGC.fillText("|", 22, 40 + (count * 25));

            //Adjustments for the vertical and horizontal headings
            for (int j = 0; j < graph.getVertices().size(); j++) {//we only to fill in points of vertices that still exits
                if (adj_matrix[i][j]) {
                    displayGC.setFill(Color.BLUE);//blue when there is an edge

                    if (isWeightedEdge(i, j)[0] == 1) {//If it is a weighted edge we want to display the weight in the matrix
                        int incr = 0;
                        if (isWeightedEdge(i, j)[1] > 9 || isWeightedEdge(i, j)[1] < -9) {//more than 2 digits so we need to account for that spacing
                            incr = -3;
                        }
                        displayGC.fillText(isWeightedEdge(i, j)[1] + "", 30 + (j * 25) + incr, 40 + (count * 25));
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

    private static int[] isWeightedEdge(int i, int j) {
        int[] results = new int[2];
        for (WeightedEdge e : graph.getWeightedEdges()) {
            Vertex v1 = graph.getVertex(i);
            Vertex v2 = graph.getVertex(j);
            if (e.getVertexA().equals(v1) && e.getVertexB().equals(v2)) {
                results[0] = 1;
                results[1] = e.getWeight();
                return results;

            } else if (e.getVertexA().equals(v2) && e.getVertexB().equals(v1)) {
                results[0] = 1;
                results[1] = e.getWeight();
                return results;

            }
        }
        return results;
    }

    public static void callAlg(){


        switch (ConfigureScreen.getAlgorithmSelected()){
            case "Select An Algorithm":
                JOptionPane.showMessageDialog(null,"Please Select An Algorithm");
                return;

            case "Connected Graph?":
                checkConnected();
                return;

            case "Complete Graph?":
                checkCompleted();
                return;

            case "Make MWSP":
                makeMWST();
                return;

            case "Colour Graph":
                colourGraph();
                return;

        }
    }

    private static void checkCompleted() {
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

    private static void checkConnected() {
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

    private static void makeMWST() {
        ArrayList<WeightedEdge> usedWeightedEdges = graphMethods.constructMWSP();
        if(usedWeightedEdges.size()==0){
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("View Created Graph");
        Group group=new Group();
        stage.setResizable(false);

        Canvas generatedCanvas=new Canvas(canvasWidth,canvasHeight);
        generatedCanvas.setLayoutX(0);
        generatedCanvas.setLayoutY(100);
        group.getChildren().add(generatedCanvas);
        GraphicsContext generatedGC=generatedCanvas.getGraphicsContext2D();
        generatedGC.setFill(Color.WHITE);
        generatedGC.setLineWidth(2.0D);
        generatedGC.fillRect(0, 0, canvasWidth, canvasHeight);

        Vertex v0;
        Vertex v1;
        Pair<Double,Double>pair0;
        Pair<Double,Double>pair1;

        for(WeightedEdge weightedEdge:usedWeightedEdges){
            v0=weightedEdge.vertexA;
            v1=weightedEdge.vertexB;

            generatedGC.setLineWidth(2.0D);
            generatedGC.setFill(Color.BURLYWOOD);
            generatedGC.fillOval(v0.getxPos(),v0.getyPos(),40,40);
            generatedGC.fillOval(v1.getxPos(),v1.getyPos(),40,40);

            generatedGC.setFill(Color.BLACK);
            generatedGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
            if ((v0.getVertexNumber() + "").length() == 1) {
                generatedGC.fillText(v0.getVertexNumber() + "", v0.getxPos() + 15, v0.getyPos()+ 25);
            } else {
                generatedGC.fillText(v0.getVertexNumber() + "", v0.getxPos() + 10, v0.getyPos()+ 25);
            }

            if ((v1.getVertexNumber() + "").length() == 1) {
                generatedGC.fillText(v1.getVertexNumber() + "", v1.getxPos() + 15, v1.getyPos()+ 25);
            } else {
                generatedGC.fillText(v1.getVertexNumber() + "", v1.getxPos() + 10, v1.getyPos()+ 25);
            }

            pair0=weightedEdge.firstXY;
            pair1=weightedEdge.secondXY;
            generatedGC.setLineWidth(3.0D);
            generatedGC.strokeLine(pair0.getKey(),pair0.getValue(),pair1.getKey(),pair1.getValue());

            double[] midpoint = getMidpoint(v0, v1);

            generatedGC.fillRect(midpoint[0] - 15, midpoint[1] - 15, 35, 35);

            generatedGC.setFill(Color.WHITE);
            int incr;
            String temp=weightedEdge.getWeight()+"";
            if (temp.length() == 1) {
                incr = 3;
            } else {
                incr = 8;
            }
            generatedGC.fillText(temp + "", midpoint[0] - incr, midpoint[1] + 7);

        }



        Scene scene = new Scene(group, canvasWidth-10, canvasHeight+90);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);

        stage.show();


    }

    private static void colourGraph(){
        resetColors();
        ArrayList<Pair<Vertex,Integer>> pairs=graphMethods.colourGraph();
        ScheduledExecutorService executorService= Executors.newSingleThreadScheduledExecutor();
        Runnable colour=() ->{

            for (int i = 0; i < pairs.size(); i++) {
                Pair<Vertex, Integer> pair=pairs.get(i);
                if(!pair.getValue().equals(-1)){
                    gc.setLineWidth(2.0D);
                    gc.setFill(getColour(pair.getValue()));
                    gc.fillOval(pair.getKey().getxPos(),pair.getKey().getyPos(),40,40);

                    gc.setFill(Color.BLACK);
                    gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
                    if ((pair.getKey().getVertexNumber() + "").length() == 1) {
                        gc.fillText(pair.getKey().getVertexNumber() + "", pair.getKey().getxPos() + 15, pair.getKey().getyPos() + 25);
                    } else {
                        gc.fillText(pair.getKey().getVertexNumber() + "", pair.getKey().getxPos() + 10, pair.getKey().getyPos() + 25);
                    }

                    Pair<Vertex, Integer> tempPair=new Pair<>(pair.getKey(),-1);
                    pairs.set(i,tempPair);
                    break;
                }
            }
            Pair<Vertex, Integer> pair=pairs.get(pairs.size()-1);
            if(pair.getValue()==-1){//if the last vertex has been coloured

                executorService.shutdown();
            }
        };


            Platform.runLater(() -> {//Method of running on the UI thread
                executorService.scheduleAtFixedRate(colour, 10, 1700, TimeUnit.MILLISECONDS);//every 1700 milliseconds these methods are run

            });

    }

    protected static void resetColors(){
        for(Vertex vertex:graph.getVertices()){
            gc.setLineWidth(2.0D);
            gc.setFill(Color.BURLYWOOD);
            gc.fillOval(vertex.getxPos(),vertex.getyPos(),40,40);

            gc.setFill(Color.BLACK);
            gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
            if ((vertex.getVertexNumber() + "").length() == 1) {
                gc.fillText(vertex.getVertexNumber() + "", vertex.getxPos() + 15, vertex.getyPos() + 25);
            } else {
                gc.fillText(vertex.getVertexNumber() + "", vertex.getxPos() + 10, vertex.getyPos() + 25);
            }
        }
    }

    private static Color getColour(int num){
        switch (num){
            case 0:
                return Color.MAGENTA;
            case 1:
                return Color.CYAN;
            case 2:
                return Color.LIMEGREEN;
            case 3:
                return Color.CRIMSON;
            case 4:
                return Color.PLUM;
            case 5:
                return Color.GOLD;
            default:
                return Color.GREENYELLOW;
        }
    }

    private static void repaint(){
        gc.setFill(Color.WHITE);
        gc.setLineWidth(4.0D);
        gc.fillRect(canvas.getLayoutX(), 0, canvasWidth, canvasHeight);

            for(Vertex v:graph.getVertices()){
                    gc.setFill(Color.BURLYWOOD);
                    gc.setLineWidth(2.0D);

                    gc.fillOval(v.getxPos(), v.getyPos(), 40, 40);
                    gc.setFill(Color.BLACK);
                    gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
                    if ((v.getVertexNumber() + "").length() == 1) {
                        gc.fillText(v.getVertexNumber() + "", v.getxPos() + 15, v.getyPos() + 25);
                    } else {
                        gc.fillText(v.getVertexNumber() + "", v.getxPos() + 10, v.getyPos() + 25);
                    }

            }

        for(Edge e: graph.getEdges()) {

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3.0D);
            gc.strokeLine(e.firstXY.getKey(), e.firstXY.getValue(), e.secondXY.getKey(), e.secondXY.getValue());


        }

        for(WeightedEdge w:graph.getWeightedEdges()) {

            redrawWeightedEdge(w);

        }



    }

    private static void redrawWeightedEdge(WeightedEdge weightedEdge){
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3.0D);
        gc.strokeLine(weightedEdge.firstXY.getKey(), weightedEdge.firstXY.getValue(), weightedEdge.secondXY.getKey(), weightedEdge.secondXY.getValue());

        gc.setFill(Color.BLACK);
        gc.setLineWidth(2.0D);
        gc.fillRect(weightedEdge.getMidPoint()[0] - 15, weightedEdge.getMidPoint()[1] - 15, 35, 35);

        gc.setFill(Color.WHITE);
        int incr;
        String temp=weightedEdge.getWeight()+"";
        if (temp.length() == 1) {
            incr = 3;
        } else {
            incr = 8;
        }
        gc.fillText(weightedEdge.getWeight() + "", weightedEdge.getMidPoint()[0] - incr, weightedEdge.getMidPoint()[1] + 7);


    }

    private static void deleteEdge(double x,double y){
        if(getSelectedEdge(x,y).vertexA.getVertexNumber()==-1){
            JOptionPane.showMessageDialog(null,"Please make sure to select an edge");
            return;
        }
        if(!getSelectedEdge(x,y).isWeightedEdge()){
            Edge selectedEdge=getSelectedEdge(x,y);
            int option=JOptionPane.showConfirmDialog(null,"You have selected the edge between "+ selectedEdge.vertexA.getVertexNumber()+" and "+ selectedEdge.vertexB.getVertexNumber()+
                    "\nPress yes to delete this edge");

            if(option==JOptionPane.YES_OPTION){
              graph.deleteEdge(selectedEdge);
                repaint();
            }
        }else{
            WeightedEdge selectedEdge= (WeightedEdge) getSelectedEdge(x,y);
            int option=JOptionPane.showConfirmDialog(null,"You have selected the edge between "+ selectedEdge.vertexA.getVertexNumber()+" and "+ selectedEdge.vertexB.getVertexNumber()+
                    "\nPress yes to delete this edge");

            if(option==JOptionPane.YES_OPTION){
                graph.deleteWeightedEdge(selectedEdge);
                repaint();
            }
        }


    }

    private static Edge getSelectedEdge(double x,double y){
        for(Edge edge:graph.getEdges()){
            if(edge.liesOnEdge(x,y)){
                return edge;
            }
        }

        for (WeightedEdge weightedEdge:graph.getWeightedEdges()){

            if(weightedEdge.liesOnEdge(x,y)){
                return weightedEdge;
            }
        }
        Vertex vertex=new Vertex(-1,-1,-1);

        return new Edge(vertex,vertex);
    }

    private static Vertex getSelectedVertx(double x,double y){
        for(Vertex v:graph.getVertices()){

            if(v.liesInVertex(x,y)){
                return v;
            }
        }

        return new Vertex(-1,-1,-1);
    }

    private static void deleteVertex(double x, double y){
        Vertex vertex=getSelectedVertx(x,y);
        if(vertex.getxPos()==-1){
            JOptionPane.showMessageDialog(null,"Please select a vertex");
            return;
        }
        int option=JOptionPane.showConfirmDialog(null,"You have selected Vertex "+ vertex.getVertexNumber()+
                        "\nYou will delete all the edges connected to this Vertex"+
                "\nPress yes to delete this Vertex");

        if(option==JOptionPane.YES_OPTION){
            graph.deleteVertex(vertex);
            repaint();
        }



    }

    public static void callSearch(){
        SearchVisual searchVisual=new SearchVisual();

        switch (ConfigureScreen.getSearchSelected()){
            case "Select A Search":
                JOptionPane.showMessageDialog(null,"Please select a search type first");
                return;
            case "DFS":
                searchVisual.search("DFS");
                return;

            case "BFS":
                searchVisual.search("BFS");
                return;
        }

    }

}
