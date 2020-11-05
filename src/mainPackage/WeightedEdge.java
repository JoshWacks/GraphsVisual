package mainPackage;

import javafx.util.Pair;

public class WeightedEdge extends Edge{
    private int weight;
    private double[] midPoint;

    public WeightedEdge(Vertex v1, Vertex v2, int w, Pair<Double,Double> firstXY, Pair<Double,Double> secondXY){

        vertexA=v1;
        vertexB=v2;
        weight=w;

        this.firstXY=firstXY;
        this.secondXY=secondXY;

    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean isWeightedEdge(){
        return true;
    }

    public void setMidPoint(double[] midPoint) {
        this.midPoint = midPoint;
    }

    public double[] getMidPoint() {
        return midPoint;
    }

    public String toString(){
        return "Vertex A: " + vertexA.getVertexNumber()+ " Vertex B: " + vertexB.getVertexNumber()+" Weight: "+ weight+
                " FirstXY: ("+firstXY.getKey()+";"+firstXY.getValue()+
                ")  SecondXY:  ("+ secondXY.getKey()+";"+secondXY.getValue()+")\n";
    }
}
