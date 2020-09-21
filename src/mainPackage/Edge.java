package mainPackage;

public class Edge {
    private Vertex vertexA;
    private Vertex vertexB;
    private int weight;


    public Edge(Vertex v1,Vertex v2,int w){
        vertexA=v1;
        vertexB=v2;
        weight=w;

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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String toSring(){
        return "Vertex A: " + vertexA.getVertexNumber()+ " Vertex B: " + vertexB.getVertexNumber()+" Weight: "+ weight;
    }
}
