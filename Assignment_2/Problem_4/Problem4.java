import java.awt.font.GraphicAttribute;
import java.io.*;
import java.util.*;

public class Problem4 {

    private PriorityQueue<GraphNode> queue;
    private GraphNode[] g;
    private int[][] D;

    public Problem4(GraphNode[] nodes){
        g = nodes;
        queue = new PriorityQueue<GraphNode>(g.length, new NodeComparator());
        D = new int[g.length-1][g.length-1]; //D will store the shortest path lengths after running Dijkstra's within Johnson's
                                             //g.length - 1 because we are ignoring the "+1" from the "super node"
    }

    private void initializeGraph(){ //this method sets all nodes' ".d" values to inf and clears the .pi pointers.
        for(int i = 0; i < g.length; i ++){
            g[i].setD(2000000);
            g[i].setPi(null);
            g[i].setWhite();
        }
    }

    private boolean relax(GraphNode u, GraphNode v, int edgeWeight){ //u is the starting node, v is the destination, edgeWeight is the weight between them.
        if(v.getD() > (u.getD() + edgeWeight)){
            v.setD(u.getD() + edgeWeight); //v.d = u.d + w(u, v)
            v.setPi(u); //set v's pi pointer to u
            return true;
        }
        return false;
    }

    private void reweightEdge(GraphNode u, GraphNode v, int edgeWeight){ //same convention as relax: start at u, end at v, and edgeWeight is edge between u, v.
        //note that technically the ".d" value should be equivalent to the ".h" value at this step.
        int newWeight = edgeWeight + u.getD() - v.getD();
        u.map.replace(v, newWeight); //re-weight the edge from u -> v.
    }

    private void unweightPath(GraphNode u, GraphNode v){ //u is start node, v is end node
        int newPath = v.getD() - u.getH() + v.getH(); //actual-shortest = modified-shortest - start.h + end.h
        v.setD(newPath);
    }

    private void Dijkstra(GraphNode start){ //graph has been initialized already. Queue is populated with all the vertices, starting node has a ".d" value of 0.
        GraphNode u;
        queue.add(start);
        while(queue.size() != 0){
            u = queue.poll(); //remove the 1st element of the queue, and then color it black.
            u.setBlack();

            //next, we want to call relax() on all nodes adjacent to u. This is where we use the HashMap key/value pairs and iterate over those.
            for(Map.Entry<GraphNode, Integer> entry : u.map.entrySet()){
                relax(u, entry.getKey(), entry.getValue());
                if(!(entry.getKey().getColor().equalsIgnoreCase("black"))) { //if the key we're looking at is not colored black, add it to the queue
                    queue.add(entry.getKey());
                }
            }
        }
    }

    private boolean Johnson(){
        boolean n = BellmanFord(g.length - 1); //call BellmanFord on the last index of g, since in my implementation that is the "super node"
        if(n == false){ //this means there exists a negative cycle in the graph, return false.
            return false;
        }

        //now, re-weight every edge in the graph since we ran BellmanFord already.
        for(int i = 0; i < g.length - 1; i++){
            GraphNode u = g[i];
            for(Map.Entry<GraphNode, Integer> entry : u.map.entrySet()){
                GraphNode v = entry.getKey();
                int edgeWeight = entry.getValue();
                reweightEdge(u, v, edgeWeight);
            }
        }

        //now, after running BellmanFord, set each node's ".h" equivalent to its ".d" value. (except "super node") so we have these ".h" values for later.
        for(int i = 0; i < g.length; i++){
            GraphNode u = g[i];
            u.setH(u.getD()); //u.h = u.d
        }

        //next, run Dijkstra's on every vertex in the graph (NOT including the "super node").
        //Use the VxV matrix to store the shortest path values from Dijkstra's.
        g[g.length - 1].setD(2000000); //set the super node's .d value to inf
        for(int i = 0; i < g.length - 1; i++){
            initializeGraph(); //clear all ".d" and ".pi" values so Dijkstra's works
            g[i].setD(0); //set the starting node's ".d" value to zero for Dijkstra's alg
            Dijkstra(g[i]);
            for(int j = 0; j < g.length - 1; j++){ //populate the relevant row of the matrix, D, after un-weighting all the path values
                if(j != i){
                    unweightPath(g[i], g[j]); //starting at node g[i], going to g[j], un-weight g[j]'s ".d" value.
                    D[i][j] = g[j].getD();
                }
                else{ //j == i here
                    D[i][j] = 0; //shortest path from a node to itself, D[i][i] for example, is 0.
                }
            }
        }
        return true;
    }

    private String printSquareMatrix(){
        String output = "";
        for(int i = 0; i < g.length-1; i++){
            for(int j = 0; j < g.length - 1; j++){
                if(j < g.length - 2){ //we're not looking at the last column, so add another space to the output string.
                    output += D[i][j] + " ";
                }
                else if(i < g.length - 2){ //we're looking at the last column, so we may need a newline character to move onto the next row.
                    output += D[i][j] + "\n";
                }
                else{ //this is the very last element to write.
                    output += D[i][j];
                }
            }
        }
        return output;
    }

    private boolean BellmanFord(int startNodePosition){
        g[startNodePosition].setD(0); //set the ".d" value of the starting node to 0.
        GraphNode[] gCopy = g.clone(); //create a clone of g such that we can move the start position to index 0.
        GraphNode temp = gCopy[0];
        gCopy[0] = gCopy[startNodePosition]; //swap starting Index and g[0].
        gCopy[startNodePosition] = temp;

        for(int i = 0; i < gCopy.length - 1; i++){ //iterate i a total of |V| - 1 times (here, |V| includes the "super node" in Johnson's alg).
            for(int j = 0; j < gCopy.length; j++){ //this loop will iterate over all the nodes in the graph, so we get to relax all of the edges.
                GraphNode u = gCopy[j];
                for(Map.Entry<GraphNode, Integer> entry : u.map.entrySet()){ //loop over each node's HashMap (edge/vertex pairs) and relax those.
                    relax(u, entry.getKey(), entry.getValue());
                }

            }
        }
        for(int i = 0; i < gCopy.length; i++){ //now, iterate over all edges one more time and see if we have a negative cycle.
            GraphNode u = gCopy[i];
            for(Map.Entry<GraphNode, Integer> entry : u.map.entrySet()){ //loop over each node's HashMap (edge/vertex pairs) and check those.
                GraphNode v = entry.getKey();
                int edgeWeight = entry.getValue();
                if(v.getD() > (u.getD()) + edgeWeight){ //if v.d > u.d + edgeWeight(u -> v), there's a negative cycle.
                    return false;
                }
            }
        }
        return true;
    }

    public static void main (String[] args){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String s = "";
            String[] sArray;
            GraphNode[] g = new GraphNode[0];
            boolean readingFirstLine = true;
            int lineCount = 0, numVertices = 0;

            while((s = reader.readLine()) != null){
                sArray = s.split(" "); //read a line from the file and split it into a String array
                numVertices = sArray.length;

                if(readingFirstLine){ //if we're reading the first line, we need to initialize our GraphNodes and their adjacent edges
                    readingFirstLine = false;
                    g = new GraphNode[numVertices + 1]; //create an array of numVertices + 1. The +1 is for the last node we will add, the "super node"
                    for(int i = 0; i < numVertices; i++){
                        g[i] = new GraphNode(i);
                    }
                }

                for(int i = 0; i < sArray.length; i++){
                    int edgeWeight = Integer.parseInt(sArray[i]);
                    if(edgeWeight != 0){ //I'm not considering edge weights of "0" since in the matrix, those are just nodes pointing to themselves.
                        g[lineCount].insertPair(g[i], edgeWeight);
                    }
                }
                lineCount++;
            }

            //next, for implementing Johnson's alg, we need to add a "super vertex" with edges of weight zero to all vertices of graph.
            g[g.length - 1] = new GraphNode(); //I am implementing the "super vertex" as the last index of the array g.
            for(int i = 0; i < (g.length - 1); i++){ //insert GraphNode/edge pairs into the super node's HashMap
                g[g.length - 1].insertPair(g[i], 0);
            }

            Problem4 P4 = new Problem4(g);
            boolean n = P4.Johnson();
            if(n == false){
                writer.write("Negative cycle");
            }
            else{
                String output = P4.printSquareMatrix();
                writer.write(output);
            }

            writer.close();
            reader.close();
        }
        catch(Exception E){
            System.out.println("Invalid filepath/name.");
        }
    }

}
