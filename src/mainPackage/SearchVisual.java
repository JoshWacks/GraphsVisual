package mainPackage;

import javafx.application.Platform;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SearchVisual extends Visuals {

    protected void DFS(){
        if(graph.getRoot()==null){
            JOptionPane.showMessageDialog(null,"Please select a root first");
            return;
        }
       ArrayList<Vertex> visitedOrder=graphMethods.callDFS();

       displayGC.setFill(Color.ANTIQUEWHITE);
       displayGC.fillRect(0, 0, displayCanvas.getWidth(), displayCanvas.getHeight());//resets the display canvas

        displayGC.setFill(Color.BLACK);
        displayGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 23));
        displayGC.fillText("Below Is The DFS Traversal Of The Graph",30,30 );
        int count=0;
        displayGC.setFont(javafx.scene.text.Font.font(Font.SERIF, 18));
        for(Vertex vertex:visitedOrder){
            displayGC.fillText(vertex.getVertexNumber()+"",10,70+(count*20) );//Increments goes down the page
            count++;
        }
        showDFS(visitedOrder);
    }

    private void showDFS( ArrayList<Vertex> visitedOrder){

        ScheduledExecutorService executorService= Executors.newSingleThreadScheduledExecutor();
        final int[] pos = {0};//keeps track of where we are in the arrayList


        Runnable dfs=() ->{


            if(pos[0] >=visitedOrder.size()){
                resetColors();
                executorService.shutdown();
            }
            Vertex vertex=visitedOrder.get(pos[0]);
            gc.setLineWidth(2.0D);
            gc.setFill(Color.FORESTGREEN);
            gc.fillOval(vertex.getxPos(),vertex.getyPos(),40,40);

            gc.setFill(Color.BLACK);
            gc.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));

            if ((vertex.getVertexNumber() + "").length() == 1) {
                gc.fillText(vertex.getVertexNumber() + "", vertex.getxPos() + 15, vertex.getyPos() + 25);
            } else {
                gc.fillText(vertex.getVertexNumber() + "", vertex.getxPos() + 10, vertex.getyPos() + 25);
            }

            pos[0]++;
        };

        Platform.runLater(() -> {//Method of running on the UI thread
            executorService.scheduleAtFixedRate(dfs, 10, 1700, TimeUnit.MILLISECONDS);//every 1700 milliseconds these methods are run

        });




    }
}
