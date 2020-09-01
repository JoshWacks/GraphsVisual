package mainPackage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.*;
import org.omg.CORBA.CODESET_INCOMPATIBLE;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


public class Visuals {
    private static Canvas canvas;
    private static double canvasWidth;
    private static double canvasHeight;

    private static int vertexCount=0;
    private static GraphicsContext gc;
    private static Graph graph=new Graph();

    private int numSelectedVertices=0;
    private Vertex[] selectedVertices=new Vertex[2];

    public Visuals(Canvas c){
        canvas=c;
        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setLineWidth(4.0D);
        gc.fillRect(0,0,canvasWidth,canvasHeight);

        canvas.setOnMouseClicked(event -> {
            callCorrectDrawMethod(event.getX(),event.getY());
        });
    }

    private void callCorrectDrawMethod(double x,double y){
        switch (ConfigureScreen.getBtnSelected()){
            case 0:
                JOptionPane.showMessageDialog(null,"Please select an option from the draw menu first");
                return;
            case 1:
                drawVertix(x-5,y-5);//we offset it by 5 to find the a more precise location of the pointer
                break;
            case 2:
                if(getSelectedVertex(x - 5, y - 5).getVertexNumber()!=-1) {
                    Vertex v=getSelectedVertex(x - 5, y - 5);
                    if(numSelectedVertices==0){
                        selectedVertices[0]=v;
                        highlightVertex(v);
                        numSelectedVertices=1;
                    }
                    else if(numSelectedVertices==1){
                        selectedVertices[1]=v;
                        highlightVertex(v);

                        graph.addEdge(selectedVertices[0].getVertexNumber(),selectedVertices[1].getVertexNumber());
                        drawEdge(selectedVertices);
                        unhighlightVertex(selectedVertices[0]);
                        unhighlightVertex(selectedVertices[1]);

                        numSelectedVertices=0;
                    }




                }
                else{

                    JOptionPane.showMessageDialog(null,"Please select a vertex");
                }



        }
    }

    private void drawVertix(double x,double y){

        if(validVertexPos(x,y)) {

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

            Vertex vertex = new Vertex(vertexCount,x,y);
            graph.addVertex(vertex);
            vertexCount++;
        }
        else{
            JOptionPane.showMessageDialog(null,"Please choose a valid position on the board \nNot too close to the end of the board or another vertex");
        }
    }

    private boolean validVertexPos(double x,double y){
        Double tempX,tempY;
        if(y+40>canvasHeight || x-40<0|| x+40>canvasWidth){//basic bounds checking
            return false;
        }

        for(Vertex v:graph.getVertices()){
            tempX=v.getxPos();
            tempY=v.getyPos();
            if ((tempX - 45 < x && x < tempX + 45) && (tempY - 45 < y && y < tempY + 45)) {
                return false;
            }
        }

        return true;
    }

    private Vertex getSelectedVertex(double x,double y){
        double tempX,tempY;
        for(Vertex v:graph.getVertices()){
            tempX=v.getxPos();
            tempY=v.getyPos();
            if ((tempX - 45 < x && x < tempX + 45) && (tempY - 45 < y && y < tempY + 45)) {
                return v;
            }
        }

        return new Vertex(-1,-1,-1);

    }

    private void highlightVertex(Vertex v){
        gc.setStroke(Color.LIME);
        gc.setLineWidth(4.0D);
        gc.strokeOval(v.getxPos(),v.getyPos(),40,40);
    }

    private void unhighlightVertex(Vertex v){
        gc.setStroke(Color.WHITE);
        gc.strokeOval(v.getxPos(),v.getyPos(),40,40);
    }

    private void drawEdge(Vertex[] arr){
        Vertex v0=arr[0];
        Vertex v1=arr[1];

        Double[]points=new Double[4];

        if(v0.getxPos()<v1.getxPos()||v0.getxPos()==v1.getxPos()){
            points=findCorrectPoints(arr);
            gc.setStroke(Color.BLACK);
            gc.strokeLine(points[0],points[1],points[2],points[3]);

        }else{
            Vertex[] newArr=new Vertex[2];
            newArr[0]=v1;
            newArr[1]=v0;
            drawEdge(newArr);
        }


    }

    private Double[] findCorrectPoints(Vertex[]arr){
        Double[] correctPoints=new Double[4];
        Double shortestDistance=5000D;
        Double tempDistance=0D;
        Vertex v0=arr[0];
        Vertex v1=arr[1];

        double x1=v0.getxPos();
        double y1=v0.getyPos();
        double x2=v1.getxPos();
        double y2=v1.getyPos();

        ArrayList<Pair<Double,Double>> points1=new ArrayList<Pair<Double, Double>>();
        ArrayList<Pair<Double,Double>> points2=new ArrayList<Pair<Double, Double>>();

        if(x1<x2||x1==x2){
            for (Double i = x1; i < x1+45; i=i+1) {
                for(Double j=y1; j<y1+45;j=j+1){
                    Pair<Double,Double>pair=new Pair<>(i,j);
                    points1.add(pair);
                }
            }

            for (Double i = x2; i <x2+45; i=i+1) {
                for(Double j=y2; j<y2+45;j=j+1){
                    Pair<Double,Double>pair=new Pair<>(i,j);
                    points2.add(pair);
                }
            }

            for(Pair<Double,Double>p1:points1){
                for(Pair<Double,Double>p2:points2){
                    tempDistance=getDistance(p1.getKey(),p1.getValue(),p2.getKey(),p2.getValue());

                    if(tempDistance<shortestDistance){

                        shortestDistance=tempDistance;
                        correctPoints[0]=p1.getKey();
                        correctPoints[1]=p1.getValue();
                        correctPoints[2]=p2.getKey();
                        correctPoints[3]=p2.getValue();
                    }
                }
            }
        }else{
            Vertex[] newArr=new Vertex[2];
            newArr[0]=v1;
            newArr[1]=v0;
            correctPoints=findCorrectPoints(newArr);
        }
        return correctPoints;


    }

    public Double getDistance(Double x1,Double y1,Double x2,Double y2){
        Double firstSqaure=Math.pow((x2-x1),2);
        Double secondSqaure=Math.pow((y2-y1),2);
        Double sum=firstSqaure+secondSqaure;


        return Math.sqrt(sum);
    }


}
