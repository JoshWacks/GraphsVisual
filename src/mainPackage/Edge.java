package mainPackage;

import javafx.util.Pair;

public class Edge {
    protected Vertex vertexA;
    protected Vertex vertexB;
    protected Pair<Double,Double> firstXY;
    protected Pair<Double,Double> secondXY;

    public Edge(){}


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



    public String toSring(){
        return "Vertex A: " + vertexA.getVertexNumber()+ " Vertex B: " + vertexB.getVertexNumber()+" FirstXY: ("+firstXY.getKey()+";"+firstXY.getValue()+
                ")  SecondXY:  ("+ secondXY.getKey()+";"+secondXY.getValue()+")\n";
    }

}
