package mainPackage;

import javafx.util.Pair;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphMethods {
    private static Graph graph;
    private static boolean[] visited;

    public GraphMethods(Graph g) {
        graph = g;

    }

    public boolean checkComplete() {
        int n = graph.getVertices().size();

        for (Vertex v : graph.getVertices()) {
            if (v.getAdjacencies().size() < n - 1) {
                return false;
            }
        }
        return true;
    }

    public boolean checkConnectedGraph( ) {
        visited=new boolean[graph.getVertices().size()];
        Arrays.fill(visited, false);

        DFS(graph.getVertex(0));

        for (boolean b:visited) {
           if(!b){
               return false;
           }
        }
        return true;

    }

    private void DFS(Vertex v){

        visited[v.getVertexNumber()]=true;
        for (Vertex vertex:v.getAdjacencies()) {
            if(!visited[vertex.getVertexNumber()]){
                DFS(vertex);
            }
        }
    }
    private boolean validSpanningTree(){
        if(!checkConnectedGraph()){//first checks if it is connected
            JOptionPane.showMessageDialog(null,"This graph is not connected, thus a MWSP cannot be made");
            return false;
        }

        if(Graph.getEdges().size()>0){
            JOptionPane.showMessageDialog(null,"All the edges must be weighted to create a MWSP");
            return false;
        }

        if(graph.getVertices().size()==0){
            JOptionPane.showMessageDialog(null,"Please Create A Graph First");
            return false;
        }
        return true;
    }

    public ArrayList<WeightedEdge> constructMWSP(){
        ArrayList<WeightedEdge> usedWeightedEdges =new ArrayList<>();
        if(!validSpanningTree()){
            return usedWeightedEdges;
        }

        ArrayList<Vertex>vertices=graph.getVertices();
        ArrayList<WeightedEdge> weightedEdges =Graph.getWeightedEdges();

        ArrayList<Vertex>visited=new ArrayList<>();

        WeightedEdge nextWeightedEdge = null;

        int cheapest;
        visited.add(vertices.get(0));


        while (visited.size()<vertices.size()){
            cheapest=9000000;


             for(Vertex v:visited){
                 for(WeightedEdge e: weightedEdges){
                     if(e.getVertexA().equals(v)){
                         if(e.getWeight()<cheapest && (!visited.contains(e.getVertexB()))){
                             cheapest=e.getWeight();
                             nextWeightedEdge =e;
                         }
                     }
                     else if(e.getVertexB().equals(v)){
                         if(e.getWeight()<cheapest && (!visited.contains(e.getVertexA()))){
                             cheapest=e.getWeight();
                             nextWeightedEdge =e;
                         }
                     }
                 }
             }
             usedWeightedEdges.add(nextWeightedEdge);
             if(!visited.contains(nextWeightedEdge.getVertexA())){
                 visited.add(nextWeightedEdge.getVertexA());
             }
             if(!visited.contains(nextWeightedEdge.getVertexB())){
                 visited.add(nextWeightedEdge.getVertexB());
             }
             weightedEdges.remove(nextWeightedEdge);
             nextWeightedEdge =null;
        }


        return usedWeightedEdges;

    }

    public ArrayList<Pair<Vertex,Integer>> colourGraph(){
        for(Vertex vertex:graph.getVertices()){
            vertex.setColour(-1);
        }

        ArrayList<Pair<Vertex,Integer>> pairs=new ArrayList<>();

        int largestVertexPos;
        Vertex largestVertex;
        Integer[] colours=new Integer[10];
        Arrays.fill(colours,-1);//sets all elements to -1 to say not used
        Integer selectedColour=-1;
        while (!graph.checkAllColoured()){

            largestVertexPos=graph.getBiggestVertex();
            largestVertex=graph.getVertex(largestVertexPos);

            for (Vertex v:largestVertex.getAdjacencies()){
                if(v.getColour()!=-1){
                    colours[v.getColour()]=1;//To indicate we have used that colour
                }
            }

            for(int i=0;i<colours.length;i++){
                if(colours[i]==-1){
                    selectedColour=i;
                    break;
                }
            }
            largestVertex.setColour(selectedColour);
            Pair<Vertex,Integer>pair=new Pair<>(largestVertex,selectedColour);
            pairs.add(pair);
            Arrays.fill(colours,-1);

        }
        return pairs;
    }
}
