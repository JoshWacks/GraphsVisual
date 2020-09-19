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

        for(Edge e:edges){
            System.out.println(e.getVertexA().getVertexNumber()+" "+e.getVertexB().getVertexNumber()+" "+e.getWeight());
        }
        System.out.println("\n\n");


        ArrayList<Vertex>visited=new ArrayList<>();
        ArrayList<Edge>usedEdges=new ArrayList<>();


        int currentVertexPos=0;
        Vertex currentVertex;
        Edge nextEdge = null;

        int cheapest;


        while (visited.size()<vertices.size()){
            cheapest=9000000;

            for (Edge e:edges){
                for(Vertex v:visited) {
                    if ((e.getVertexA().equals(v) || e.getVertexB().equals(v)) && (!usedEdges.contains(e)) && (e.getWeight() < cheapest) && (!visited.contains(v))) {
                        nextEdge = e;
                        cheapest = e.getWeight();//making sure we get the cheapest edge
                    }
                }
            }
                if(cheapest!=9000000) {
                    usedEdges.add(nextEdge);
                    currentVertexPos = nextEdge.getVertexB().getVertexNumber();//we are now at the next vertex
                    Vertex temp=nextEdge.getVertexA();
                    if(!visited.contains(temp)){
                        visited.add(temp);
                    }
                    currentVertex = vertices.get(currentVertexPos);
                    if(!visited.contains(currentVertex)){
                        visited.add(currentVertex);
                    }


                }
        }


        return usedEdges;

    }
}
