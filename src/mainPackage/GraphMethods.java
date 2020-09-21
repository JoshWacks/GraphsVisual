package mainPackage;

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

    public ArrayList<Edge> constructMWSP(){
        //TODO make a MWSP that works for all types of trees even non-spanning
        ArrayList<Vertex>vertices=graph.getVertices();
        ArrayList<Edge>edges=Graph.getEdges();

        ArrayList<Vertex>visited=new ArrayList<>();
        ArrayList<Edge>usedEdges=new ArrayList<>();

        Edge nextEdge = null;

        int cheapest;
        visited.add(vertices.get(0));


        while (visited.size()<vertices.size()){
            cheapest=9000000;


             for(Vertex v:visited){
                 for(Edge e:edges){
                     if(e.getVertexA().equals(v)){
                         if(e.getWeight()<cheapest && (!visited.contains(e.getVertexB()))){
                             cheapest=e.getWeight();
                             nextEdge=e;
                         }
                     }
                     else if(e.getVertexB().equals(v)){
                         if(e.getWeight()<cheapest && (!visited.contains(e.getVertexA()))){
                             cheapest=e.getWeight();
                             nextEdge=e;
                         }
                     }
                 }
             }
             usedEdges.add(nextEdge);
             if(!visited.contains(nextEdge.getVertexA())){
                 visited.add(nextEdge.getVertexA());
             }
             if(!visited.contains(nextEdge.getVertexB())){
                 visited.add(nextEdge.getVertexB());
             }
             edges.remove(nextEdge);
             nextEdge=null;
        }


        return usedEdges;

    }
}
