package mainPackage;

import javax.swing.*;
import java.util.ArrayList;
//TODO move everything from GraphMethods into here

public class Graph {

    private ArrayList<Vertex>vertices=new ArrayList<>();
    private static ArrayList<WeightedEdge> weightedEdges =new ArrayList<>();
    private static ArrayList<Edge>edges=new ArrayList<>();

    public Graph(){

    }

    public void addVertex(Vertex v){

        vertices.add(v);

    }

    public void addWeightedEdge(WeightedEdge e){
        weightedEdges.add(e);
        int pos1=e.vertexA.getVertexNumber();
        int pos2=e.vertexB.getVertexNumber();
        addEdgeAdjacencies(pos1,pos2);
    }

    public static ArrayList<WeightedEdge> getWeightedEdges(){
        return weightedEdges;
    }

    public void addEdge(Edge e){
        edges.add(e);
        int pos1=e.vertexA.getVertexNumber();
        int pos2=e.vertexB.getVertexNumber();
        addEdgeAdjacencies(pos1,pos2);

    }

    public static ArrayList<Edge> getEdges() {
        return edges;
    }

    public boolean validEdge(Vertex v0, Vertex v1){
        for(Vertex vertex:v0.getAdjacencies()){
            if(vertex.equals(v1)){
                return false;
            }
        }
        for(WeightedEdge we:weightedEdges){
            if(we.getVertexA().equals(v0) && we.getVertexB().equals(v1)){
                return false;
            }
            else if(we.getVertexB().equals(v0) && we.getVertexB().equals(v1)){
                return false;
            }
        }
        return true;
    }

    public Vertex getVertex(int pos){
        return vertices.get(pos);//returns the Vertex in that position
    }

    public ArrayList<Vertex> getVertices(){
        return vertices;
    }

    public void addEdgeAdjacencies(int pos1,int pos2){
        if (pos1 != pos2) {
            getVertex(pos1).addAdjacency(getVertex(pos2));//These 2 vertices are adjacent so we need to add them to each others adjacencies list
        }
        getVertex(pos2).addAdjacency(getVertex(pos1));//Circular edge only needs to be added once
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

    public boolean checkAllColoured(){
        for(Vertex vertex:vertices){
            if(vertex.getColour()==-1){
                return false;
            }
        }
        return true;
    }





}

