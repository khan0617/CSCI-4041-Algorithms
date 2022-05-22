import java.io.*;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class P3 {
    private PriorityQueue<GraphNode> queue;
    private GraphNode[] g;

    public P3(GraphNode[] nodes){
        g = nodes;
        queue = new PriorityQueue<GraphNode>(g.length, new NodeComparator());
    }

    private boolean relax(GraphNode u, GraphNode v, int edgeWeight){ //u is the starting node, v is the destination, edgeWeight is the weight between them.
        if(v.getD() > (u.getD() + edgeWeight)){
            v.setD(u.getD() + edgeWeight);
            v.setPi(u); //set v's pi pointer to u
            return true;
        }
        return false;
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

    public static void main(String[] args){

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String s = "";
            String[] sArray;
            boolean readingFirstLine = true;
            int lineCount = 0; //this represents the # of the line being read, NOT including the first line. For example, after reading line 2, lineCount should == 1.
            int startVertex= -1, endVertex = -1, numVertices = 0;
            GraphNode[] g = new GraphNode[0];
            ArrayList<Integer> path = new ArrayList<Integer>();

            while((s = reader.readLine()) != null){
                sArray = s.split(" ");

                if(readingFirstLine){ //we are reading the first line of the file which contains start/end vertices.
                    startVertex = Integer.parseInt(sArray[0]);
                    endVertex = Integer.parseInt(sArray[1]);
                    readingFirstLine = false;
                }

                else{ //
                    if(numVertices == 0){ // need to know how many vertices there are to initialize the GraphNode array
                        numVertices = sArray.length; //number of vertices is equivalent to the number of columns in the square input matrix.
                        g = new GraphNode[numVertices];
                        for(int i = 0; i < numVertices; i++){ //initialize the graphNode array so the objects are not all null
                            g[i] = new GraphNode(i);
                        }
                    }
                    for (int i = 0; i < sArray.length; i++) { //iterate over the file line, adding appropriate vertex/edge pairs to the HashMap of the node represented by "lineCount"
                        int edgeWeight = Integer.parseInt(sArray[i]);
                        if (edgeWeight != 0) { //only add pairs to the HashMap when the edge weight is not zero
                            g[lineCount].insertPair(g[i], edgeWeight); //link the current node: g[lineCount] to the column we're looking at: g[i], by its edgeWeight
                        }
                    }
                    lineCount++;
                }
            } //end while
            if(startVertex == -1 || endVertex == -1){ //this means the start/end vertices were not initialized properly. Something went wrong
                throw new IOException();
            }
            else{
                g[startVertex].setD(0);   //initialize the startVertex to have a ".d" value of 0, so it's prioritized in the min PriorityQueue
            }

            P3 problem3 = new P3(g);
            problem3.Dijkstra(g[startVertex]);
            int shortestPathLength = g[endVertex].getD();

            GraphNode pi = g[endVertex].getPi();
            path.add(0, g[endVertex].getVertexNumber());
            while(pi != null){ //add the vertices of the shortest path to an ArrayList so they can easily be output to a file.
                path.add(0, pi.getVertexNumber());
                pi = pi.getPi();
            }

            String output = "";
            int size = path.size();
            for(int i = 0; i < size; i++){
                if(i <= path.size() - 1){
                    output += path.remove(0) + " ";
                }
                else{//we're looking at the last element of the arraylist here, so don't add an extra space to the end of the output string.
                    output += path.remove(0);
                }
            }

            output = shortestPathLength + ": " + output;
            writer.write(output);

            reader.close();
            writer.close();
        }
        catch(Exception e){
            System.out.println("Invalid filename/path.");
        }
    }

}
