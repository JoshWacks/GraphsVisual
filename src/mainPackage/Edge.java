package mainPackage;

import javafx.util.Pair;

public class Edge {
    protected Vertex vertexA;
    protected Vertex vertexB;
    protected Pair<Double,Double> firstXY;
    protected Pair<Double,Double> secondXY;

    public Edge(){}

    public Edge(Vertex vA,Vertex vB){
        vertexA=vA;
        vertexB=vB;
    }

    public Edge(Vertex vA,Vertex vB,Pair<Double,Double> firstXY,Pair<Double,Double> secondXY){
        vertexA=vA;
        vertexB=vB;
        this.firstXY=firstXY;
        this.secondXY=secondXY;
    }


    public Vertex getVertexA() {
        return vertexA;
    }

    public void setVertexA(Vertex vertexA) {
        this.vertexA = vertexA;
    }

    public Vertex getVertexB() {
        return vertexB;
    }

    public void setVertexB(Vertex vertexB) {
        this.vertexB = vertexB;
    }

    public boolean liesOnEdge(double x,double y){
        double prod=x*getGradient();
        double sum=prod+getC();

        double diff=y-getC();
        double quotient=diff/getGradient();

        return sum - 9 < y && y< sum+9 || quotient-9<x && x<quotient+9;

    }

    private double getGradient(){
        double dy=firstXY.getValue()-secondXY.getValue();
        double dx=firstXY.getKey()-secondXY.getKey();

        return (dy/dx);
    }

    private double getC(){
        return (firstXY.getValue()-(getGradient()*firstXY.getKey()));
    }

    public boolean isWeightedEdge(){
        return false;
    }

    public boolean containsVertex(Vertex v){
        return v.equals(vertexA) || v.equals(vertexB);
    }

    public boolean betweenVertices(Vertex a,Vertex b){//Methods to check if it is between these two vertices

        return (vertexA.equals(a) && vertexB.equals(b)) || (vertexA.equals(b) && vertexB.equals(a));

    }

    public boolean betweenVerticesNums(int a,int b){//Methods to check if it is between these two vertices

        return (vertexA.getVertexNumber()==a && vertexB.getVertexNumber()==b || vertexA.getVertexNumber()==b && vertexB.getVertexNumber()==a);

    }



    public String toSring(){
        return "Vertex A: " + vertexA.getVertexNumber()+ " Vertex B: " + vertexB.getVertexNumber()+" FirstXY: ("+firstXY.getKey()+";"+firstXY.getValue()+
                ")  SecondXY:  ("+ secondXY.getKey()+";"+secondXY.getValue()+")\n";
    }

}
