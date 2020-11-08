package mainPackage;

import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class GraphMethods {
    private static Graph graph;
    private static boolean[] visited;
    private static ArrayList<Vertex> visitedVertex;
    private static ArrayList<Edge>usedEdges;

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
        if(graph.getVertices().size()==0){
            return true;
        }
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

        if(graph.getEdges().size()>0){
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
        ArrayList<WeightedEdge> weightedEdges =graph.getWeightedEdges();

        ArrayList<Vertex>visited=new ArrayList<>();

        WeightedEdge nextWeightedEdge = null;

        int cheapest;
        visited.add(vertices.get(0));


        while (visited.size()<vertices.size()){
            cheapest=9000000;


             for(Vertex v:visited){
                 for(WeightedEdge e: weightedEdges){
                     if(e.getVertexA().equals(v)){
                         if(e.getWeight()<=cheapest && (!visited.contains(e.getVertexB()))){
                             cheapest=e.getWeight();
                             nextWeightedEdge =e;
                         }
                     }
                     if(e.getVertexB().equals(v)){
                         if(e.getWeight()<=cheapest && (!visited.contains(e.getVertexA()))){
                             cheapest=e.getWeight();
                             nextWeightedEdge =e;
                         }
                     }
                 }
             }


             usedWeightedEdges.add(nextWeightedEdge);

            if(nextWeightedEdge!=null && !visited.contains(nextWeightedEdge.getVertexA())){
                 visited.add(nextWeightedEdge.getVertexA());
             }
             if(nextWeightedEdge!=null && !visited.contains(nextWeightedEdge.getVertexB())){
                 visited.add(nextWeightedEdge.getVertexB());
             }

             nextWeightedEdge =null;
        }


        return usedWeightedEdges;

    }

    public  ArrayList<Pair<Vertex,Integer>> colourGraph(){
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

    public ArrayList<Vertex> callDFS(){
        visitedVertex=new ArrayList<>();
        usedEdges=new ArrayList<>();//resets all the arrays

        Vertex root=graph.getRoot();
        dfs(root);
        return visitedVertex;
    }

    private void dfs(Vertex currentVertex){

        visitedVertex.add(currentVertex);
        Vertex nextVertex;
        if (currentVertex.isDest()){
            return;
        }
        while (unvisitedAdjacency(currentVertex).getxPos()!=-1 ){//It has an unvisited adjacency and there is an edge to it

            nextVertex=unvisitedAdjacency(currentVertex);
            usedEdges.add(getEdge(currentVertex,nextVertex));

            dfs(nextVertex);
        }


        
    }

    private boolean isEdge(Vertex v,Vertex w){//Checks if there is an edge between two adjacent vertexes
        for(WeightedEdge weightedEdge:graph.getWeightedEdges()){
            if(weightedEdge.containsVertex(v) && weightedEdge.containsVertex(w)){
                return  true;
            }
        }

        for(Edge edge:graph.getEdges()){
            if(edge.containsVertex(v) && edge.containsVertex(w)){
                return  true;
            }
        }
        return false;
    }

    private Vertex unvisitedAdjacency(Vertex vertex){
        for(Vertex adj:vertex.getAdjacencies()){
            if(!visitedVertex.contains(adj)){//If there is an unvisited adjacency at that current vertex
               if(isEdge(vertex,adj)){//And there is a path to that unvisited vertex
                   return adj;
               }
            }
        }
        return new Vertex(-1,-1,-1);//A vertex to indicate no adj vertex
    }

    private Edge getEdge(Vertex v,Vertex w){
        for(WeightedEdge weightedEdge:graph.getWeightedEdges()){
            if(weightedEdge.containsVertex(v) && weightedEdge.containsVertex(w)){
                return  weightedEdge;
            }
        }

        for(Edge edge:graph.getEdges()){
            if(edge.containsVertex(v) && edge.containsVertex(w)){
                return edge;
            }
        }
        return new Edge(new Vertex(-1,-1,-1),new Vertex(-1,-1,-1));
    }

    public ArrayList<Vertex> callBFS(){
        visitedVertex=new ArrayList<>();
        usedEdges=new ArrayList<>();//resets all the arrays

        BFS();
        return visitedVertex;
    }

    private void BFS(){
        Vertex[] parent =new Vertex[graph.getNumberVertices()];

        Arrays.fill(parent,new Vertex(-1,-1,-1));

        boolean[] marked =new boolean[graph.getNumberVertices()];
        Arrays.fill(marked,false);

        Vertex currentVertex=graph.getRoot();
        Queue<Vertex>list=new LinkedList<>() ;
        list.add(currentVertex);
        marked[currentVertex.getVertexNumber()]=true;

        Vertex nextVertex;
        while (!list.isEmpty() && !visitedVertex.contains(graph.getDestination())){
            nextVertex=list.remove();
            visitedVertex.add(nextVertex);//adds it to the visited array so we get the order it is visited in
            for(Vertex vertex:nextVertex.getAdjacencies()){//For each vertex in the neighbourhood of the next vertex
                if(!marked[vertex.getVertexNumber()]){//if we have not been there before
                    marked[vertex.getVertexNumber()]=true;
                    parent[vertex.getVertexNumber()]=nextVertex;
                    list.add(vertex);
                }
            }

        }
    }
}
