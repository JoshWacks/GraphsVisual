package mainPackage;

import java.util.ArrayList;

public class Vertex {

    private int vertexNumber;
    private int colour;
    private ArrayList<Vertex>adjacencies =new ArrayList<>();
    private double xPos;
    private double yPos;
    private double xCentre;
    private double yCentre;



    public Vertex(int num,double x,double y){
        vertexNumber=num;
        xPos=x;
        yPos=y;
        colour=-1;//Sets the colour of that vertex to -1 representing uncoloured
        xCentre=-1;
        yCentre=-1;
    }
    public ArrayList<Vertex>getAdjacencies(){
        ArrayList<Vertex>newAdj =new ArrayList<>();
        for(Vertex v:adjacencies){
            if(v!=null){
                newAdj.add(v);
            }
        }
        return newAdj;
    }

    public void removeAdjacency(Vertex vertex){
        adjacencies.remove(vertex);
    }

    public void addAdjacency(Vertex v){
        adjacencies.add(v);
    }

    public int getDegree(){
        return adjacencies.size();//returns the degree(number of edges) of current vertex
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour){
        this.colour=colour;
    }

    public int getVertexNumber(){
        return vertexNumber;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public double getxCentre() {
        return xCentre;
    }

    public void setxCentre(double xCentre) {
        this.xCentre = xCentre;
    }

    public double getyCentre() {
        return yCentre;
    }

    public void setyCentre(double yCentre) {
        this.yCentre = yCentre;
    }

    public boolean liesInVertex(double x, double y){
        return xCentre - 20 < x && xCentre + x > x && yCentre - 20 < y && yCentre + 20 > y;//Tells us if the selected point is within the vertex
    }

    @Override
    public String toString(){
        return "Vertex number: "+vertexNumber+" xPos: "+xPos+" yPos: "+yPos+"\n"+
                "xCentre: "+ xCentre+" yCentre: "+ yCentre+" Colour: "+colour;
    }

}

