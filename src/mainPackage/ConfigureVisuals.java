package mainPackage;
//Sub-class to manage all the calling of methods, not directly associated with the visual aspects of the project

import javafx.scene.paint.Color;
import javafx.util.Pair;

import javax.swing.*;

import java.util.ArrayList;

public class ConfigureVisuals extends Visuals{

    public ConfigureVisuals(){
        canvas.setOnMouseClicked(event -> callCorrectDrawMethod(event.getX(), event.getY()));
    }

    protected void callCorrectDrawMethod(double x, double y) {

        switch (ConfigureScreen.getBtnSelected()) {
            case 0:
                JOptionPane.showMessageDialog(null, "Please select an option from the draw menu first");
                return;
            case 1:
                addVertex(x - 5, y - 5, gc);//we offset it by 5 to find the a more precise location of the pointer
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
            case 8:
                editVertex(x,y);
                return;

        }
        if (ConfigureScreen.getBtnSelected() == 2 || ConfigureScreen.getBtnSelected() == 3) {
            if (getSelectedVertex(x,y).getVertexNumber() != -1) {
                Vertex v = getSelectedVertex(x,y);
                if (numSelectedVertices == 0) {
                    selectedVertices[0] = v;
                    highlightVertex(v, Color.LIME);
                    numSelectedVertices = 1;
                } else if (numSelectedVertices == 1) {
                    selectedVertices[1] = v;
                    highlightVertex(v,Color.LIME);
                    unhighlightVertex(selectedVertices[0]);
                    unhighlightVertex(selectedVertices[1]);
                    if(!graph.validEdge(selectedVertices[0],selectedVertices[1])){
                        JOptionPane.showMessageDialog(null,"An edge already exists between these 2 vertices");
                        numSelectedVertices = 0;
                        return;
                    }


                    if (ConfigureScreen.getBtnSelected() == 2) {//2 stands for normal edge
                        if (selectedVertices[0].equals(selectedVertices[1])) {//it is the same vertex so it is an arc edge
                            addArcEdge(selectedVertices[0]);
                        } else {
                            addEdge(selectedVertices);
                        }
                    } else if(ConfigureScreen.getBtnSelected() == 3) {//3 is a weighted
                        if (selectedVertices[0].equals(selectedVertices[1])) {//it is the same vertex so it is a weighted arc edge
                            addWeightedArcEdge(selectedVertices[0]);
                        } else {
                            addWeightedEdge(selectedVertices);
                        }

                    }
                    numSelectedVertices = 0;
                }
            } else {

                JOptionPane.showMessageDialog(null, "Please select a vertex");
            }
        }

    }

    //Ensures the selected vertex is in a valid position
    protected boolean validVertexPos(double x, double y) {
        Double tempX, tempY;
        if ((y + 20) > canvasHeight || (x - 20) < 0 || (x + 20) > canvasWidth || (y - 20) < 0) {//basic bounds checking
            return false;
        }

        for (Vertex v : graph.getVertices()) {
            tempX = v.getxPos();
            tempY = v.getyPos();
            if ((tempX - 20 < x && x < tempX + 20) && (tempY - 20 < y && y < tempY + 20)) {
                return false;
            }
        }

        return true;
    }

    //Returns a Vertex at a specific x y position
    protected Vertex getSelectedVertex(double x, double y) {


        for (Vertex v : graph.getVertices()) {
            if(v.liesInVertex(x-5,y-5)){

                return v;
            }
        }

        return new Vertex(-1, -1, -1);

    }

    protected static Edge getSelectedEdge(double x,double y){
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

    //method to find where the edge should be drawn between two vertices
    protected Double[] findCorrectPoints(Vertex[] arr) {
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

    //Methods to get the distance between two vertices, used when finding the correct points for an edge
    protected Double getDistance(Double x1, Double y1, Double x2, Double y2) {
        Double firstSqaure = Math.pow((x2 - x1), 2);
        Double secondSqaure = Math.pow((y2 - y1), 2);
        Double sum = firstSqaure + secondSqaure;


        return Math.sqrt(sum);
    }

    //Methods to check if two points lie on a vertex(circle)
    protected boolean onCircle(Double cx, Double cy, Double x, Double y) {
        Double b1 = Math.pow((x - cx), 2);
        Double b2 = Math.pow((y - cy), 2);

        return (b1 + b2) == 400;
    }

    protected static double[] getMidpoint(Vertex v1, Vertex v2) {
        double[] midpoint = new double[2];

        double xMid = (v1.getxCentre() + v2.getxCentre()) / 2.0;
        double yMid = (v1.getyCentre() + v2.getyCentre()) / 2.0;

        midpoint[0] = xMid;
        midpoint[1] = yMid;
        return midpoint;
    }

    //Methods to check if an edge is a weighted edge
    protected static int[] isWeightedEdge(int i, int j) {
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
    //Utility function to call the correct algorithm
    protected  void callAlgMethod(){
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

            case "Make Shortest Path Tree\n(Dijkstra's Algorithm)":
                makeShortestPathTree();
                return;

        }
    }

    protected void callSearchMethod(){
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

    protected static void makeMWST() {
        ArrayList<WeightedEdge> usedWeightedEdges = graphMethods.constructMWSP();
        if(usedWeightedEdges.size()==0){
            return;
        }

        constructShortestPathTree(usedWeightedEdges);


    }

    protected static Color getColour(int num){
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





}
