package mainPackage;

import java.util.ArrayList;
//TODO move everything from GraphMethods into here

public class Graph {

    private ArrayList<Vertex>vertices=new ArrayList<>();
    private static ArrayList<Edge>edges=new ArrayList<>();

    public Graph(){

    }

    public void addVertex(Vertex v){

        vertices.add(v);

    }

    public static void addEdge(Edge e){
        edges.add(e);
    }

    public static ArrayList<Edge> getEdges(){
        return edges;
    }

    public Vertex getVertex(int pos){
        return vertices.get(pos);//returns the Vertex in that position
    }

    public ArrayList<Vertex> getVertices(){
        return vertices;
    }

    public void addEdge(int pos1,int pos2){
        if(pos1==pos2){
            getVertex(pos2).addAdjacency(getVertex(pos1));//Circular edge only needs to be added once
        }
        else {
            getVertex(pos1).addAdjacency(getVertex(pos2));//These 2 vertices are adjacent so we need to add them to each others adjacencies list
            getVertex(pos2).addAdjacency(getVertex(pos1));
        }
    }

    public int getBiggestVertex(){
        int maxAdjacencies=-1;
        int maxAdjcenciesPos=-1;

        //TODO make sure it always selects the vertex with the lowest vertex number

        for(int i=0;i<vertices.size();i++){
            if(getVertex(i).getColour()==-1&&getVertex(i).getDegree()>maxAdjacencies){

                maxAdjacencies=getVertex(i).getDegree();
                maxAdjcenciesPos=i;
            }
        }
        return  maxAdjcenciesPos;

    }





}

