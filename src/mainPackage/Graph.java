package mainPackage;


import java.util.ArrayList;


public class Graph {

    private static ArrayList<Vertex>vertices=new ArrayList<>();
    private static ArrayList<WeightedEdge> weightedEdges =new ArrayList<>();
    private static ArrayList<Edge>edges=new ArrayList<>();
    private static Vertex root;
    private static Vertex destination;

    public Graph(){
        root=null;
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

    public ArrayList<WeightedEdge> getWeightedEdges(){
        ArrayList<WeightedEdge>newWEdges=new ArrayList<>();
        for(WeightedEdge wEdge:weightedEdges){
            if(wEdge!=null){
                newWEdges.add(wEdge);
            }
        }
        return  newWEdges;
    }

    public void addEdge(Edge e){
        edges.add(e);
        int pos1=e.vertexA.getVertexNumber();
        int pos2=e.vertexB.getVertexNumber();
        addEdgeAdjacencies(pos1,pos2);

    }

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge>newEdges=new ArrayList<>();
        for(Edge edge:edges){
            if(edge!=null){
                newEdges.add(edge);
            }
        }
        return newEdges;
    }

    public boolean validEdge(Vertex v0, Vertex v1){
        for(Vertex vertex:v0.getAdjacencies()){
            if(vertex.equals(v1)){
                return false;
            }
        }

        return true;
    }

    public Vertex getVertex(int pos){
        return vertices.get(pos);//returns the Vertex in that position
    }

    public ArrayList<Vertex> getVertices(){
        ArrayList<Vertex>newVertices=new ArrayList<>();
        for(Vertex v:vertices){
            if(v!=null){
                newVertices.add(v);
            }
        }
        return newVertices;
    }

    private void addEdgeAdjacencies(int pos1,int pos2){
        if (pos1 != pos2) {
            getVertex(pos1).addAdjacency(getVertex(pos2));//These 2 vertices are adjacent so we need to add them to each others adjacencies list
        }
        getVertex(pos2).addAdjacency(getVertex(pos1));//Circular edge only needs to be added once
    }

    public int getBiggestVertex(){
        int maxAdjacencies=-1;
        int maxAdjcenciesPos=-1;


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

    public void deleteEdge(Edge e){

        e.getVertexA().removeAdjacency(e.vertexB);
        e.getVertexB().removeAdjacency(e.vertexA);
        edges.set(edges.indexOf(e),null);
    }

    public void deleteWeightedEdge(WeightedEdge w)
    {
        w.getVertexA().removeAdjacency(w.vertexB);
        w.getVertexB().removeAdjacency(w.vertexA);
        weightedEdges.set(weightedEdges.indexOf(w),null);
    }

    public void deleteVertex(Vertex vertex){
        for(Vertex v:getVertices()){
            v.removeAdjacency(vertex);
        }
        for(Edge e:getEdges()){
            if(e.containsVertex(vertex)){
                deleteEdge(e);
            }
        }
        for (WeightedEdge w:getWeightedEdges()){
            if(w.containsVertex(vertex)){
                deleteWeightedEdge(w);
            }
        }

        vertices.set(vertices.indexOf(vertex),null);
    }

    public int getNumberVertices(){
        return vertices.size();
    }

    public void setRoot(Vertex v){
        root=v;
    }

    public Vertex getRoot(){
        return root;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }



    public WeightedEdge getWeightedEdgeBetween(Vertex a,Vertex b){
        for(WeightedEdge weightedEdge:getWeightedEdges()){
            if(weightedEdge.betweenVertices(a,b)){
                return weightedEdge;
            }
        }
        return null;
    }

    public WeightedEdge getWeightedEdgeBetweenNumbers(int a,int b){
        for(WeightedEdge weightedEdge:getWeightedEdges()){
            if(weightedEdge.betweenVerticesNums(a,b)){
                return weightedEdge;
            }
        }
        return null;
    }
}

