import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;

public class GraphNode {

    private int d = 0; //"d" value represents gray time or "discovery time"
    private int f = 0; // "f" represents "finish time", or when node is colored black.
    private String color = "white"; //all nodes start as white by default.
    private GraphNode pi = null; //points to the "previous" node when traversing the graph
    private String data = ""; //this is basically the data field for the node. For problem2, this will be the course numbers (e.g. 1933, 4041....)
    public Map<GraphNode, Integer> map = new HashMap<GraphNode, Integer>(); //create a HashMap to store k/v to keep track of distance to nodes.

    public GraphNode(String dataIn) {
        data = dataIn;
    }


    public String getData(){
        return data;
    }

    public int getD(){
        return d;
    }

    public void setD(int val){
        d = val;
    }

    public void setF(int val){
        f = val;
    }

    public int getF(){
        return f;
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

    public void setColor(String c){
        color = c;
    }

    public void insertPair(GraphNode G, int edgeWeight){ //insert a GraphNode/edge pair to the HashMap.
        //For example, if we insertPair(Node G, 5), we are saying that "this"/"self" node is adjacent to node G with an edge of weight 5.
        map.put(G, edgeWeight);
    }
}
