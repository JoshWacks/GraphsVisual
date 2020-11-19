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
    private boolean isRoot;
    private boolean isDest;
    private String vertexValue;


    public Vertex(int num,double x,double y){
        vertexNumber=num;
        vertexValue=num+"";//sets the default value of the vertex to its number in the case they change it later
        xPos=x;
        yPos=y;
        colour=-1;//Sets the colour of that vertex to -1 representing uncoloured
        xCentre=-1;
        yCentre=-1;
        isRoot=false;
        isDest=false;
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

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean isDest() {
        return isDest;
    }

    public void setDest(boolean dest) {
        isDest = dest;
    }

    public boolean liesInVertex(double x, double y){
        return xCentre - 24 < x && xCentre + 24 > x && yCentre - 24 < y && yCentre + 24 > y;//Tells us if the selected point is within the vertex
    }

    public String getVertexValue() {
        return vertexValue;
    }

    public void setVertexValue(String vertexValue) {
        this.vertexValue = vertexValue;
    }

    @Override
    public String toString(){
        return "Vertex number: "+vertexNumber+" xPos: "+xPos+" yPos: "+yPos+"\n"+
                "xCentre: "+ xCentre+" yCentre: "+ yCentre+" Colour: "+colour;
    }

}

