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
}
