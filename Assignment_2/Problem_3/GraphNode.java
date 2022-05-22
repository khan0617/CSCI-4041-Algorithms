import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;

public class GraphNode {

    private int d = 2000000; //"d" value represents distance from starting node. Initialize it to an extremely high value for use in Dijkstra's
    private String color = "grey";
    private GraphNode pi = null; //points to the "previous" node when traversing the graph
    private int vertexNumber = 2000000;
    public Map<GraphNode, Integer> map = new HashMap<GraphNode, Integer>(); //create a HashMap to store k/v to keep track of distance to nodes.

    public GraphNode(int vertexNum) {
        vertexNumber = vertexNum;
    }


    public int getVertexNumber(){
        return vertexNumber;
    }

    public int getD(){
        return d;
    }

    public void setD(int val){
        d = val;
    }

    public void setBlack(){
        color = "black";
    }

    public void setPi(GraphNode g){
        pi = g;
    }

    public GraphNode getPi(){
        return pi;
    }

    public String getColor(){
        return color;
    }

    public void insertPair(GraphNode G, int edgeWeight){ //insert a GraphNode/edge pair to the HashMap.
        //For example, if we insertPair(Node G, 5), we are saying that "this"/"self" node is adjacent to node G with an edge of weight 5.
        map.put(G, edgeWeight);
    }
}
