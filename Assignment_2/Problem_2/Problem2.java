import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Problem2 {

    private int time;
    ArrayList<GraphNode> g;
    LinkedList<GraphNode> list;

    public Problem2(ArrayList<GraphNode> nodes){
        g = nodes;
        list = new LinkedList<>();
        time = 0;
    }

    public void DFS(){
        //note that the GraphNode class by default sets .color == "white" and .pi == NULL, so those don't have to be initialized here. Also, 'time' was also set to 0 in the constructor.
        for(int i = 0; i < g.size(); i++){
            GraphNode u = g.get(i);
            if(u.getColor().equalsIgnoreCase("white")){
                DFSVisit(u);
            }
        }
    }

    public void DFSVisit(GraphNode u){
        time = time + 1;
        u.setD(time);
        u.setColor("gray");
        //now, iterate over all nodes adjacent to u. This is done by iterating over u's HashMap.
        for(Map.Entry<GraphNode, Integer> entry : u.map.entrySet()){
            GraphNode v = entry.getKey();
            if(v.getColor().equalsIgnoreCase("white")){
                v.setPi(u);
                DFSVisit(v);
            }
        }
        u.setColor("black"); //we're done looking at u, so make its color black.
        list.add(0, u); //Add u to the front of the linked list.
        time = time + 1;
        u.setF(time);
    }

    public LinkedList<GraphNode> topologicalSort(){
        DFS();
        return list; //DFS and DFSVisit handle adding elements to the LinkedList. All we have to do is call DFS and return 'list'.
    }



    public static GraphNode findNode(ArrayList<GraphNode> arr, String course){ //given an ArrayList of GraphNodes, look for a specific one by checking its "data" String attribute
        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i).getData().equals(course)){
                return arr.get(i);
            }
        }
        return null;
    }

    public static void main(String[] args){
/*
        String t = "4041:1933 2011"; //"4041:" has a length of one when split by ":", whereas "4041:1933 2011" has a length 2 when split by ":"
        String[] testArr = t.split(":");
        String output = "";

        for(int i = 0; i < testArr.length; i++){
            output += testArr[i] + " ";
        }



        ArrayList<String> arr = new ArrayList<String>(Arrays.asList(testArr));
        //testArr = testArr[1].split(" ");
        System.out.println(testArr[0]);
        String[] testArr2 = testArr[1].split(" ");
        ArrayList<String> arr2 = new ArrayList<String>(Arrays.asList(testArr2));
        System.out.println(arr2);

 */

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String s = "";
            String[] sArray;
            ArrayList<String> courses = new ArrayList<>(); //this list will hold all the unique course codes present in the input file.
            ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();
            while((s = reader.readLine()) != null){
                sArray = s.split(":");
                if(sArray.length == 1){ //this means there are no pre-requisite courses listed after the first course, like: "1133: "
                    if(!courses.contains(sArray[0])){ //if sArray[0] is NOT in the 'courses' ArrayList, add it.
                        courses.add(sArray[0]);
                        nodes.add(new GraphNode(sArray[0]));
                    }
                }
                else{ //sArray is longer than 1 element. Example could be: "4041:1933 2011" for the file line
                    if(!courses.contains(sArray[0])){ //if sArray[0] is NOT in the 'courses' ArrayList, add it.
                        courses.add(sArray[0]);
                        nodes.add(new GraphNode(sArray[0]));
                    }
                    String[] sArray2 = sArray[1].split(" "); //for example, if input was "4041:1933 2011", sArray2 will be: [1933, 2011]
                    for(int i = 0; i < sArray2.length; i++){
                        if(!courses.contains(sArray2[i])){
                            courses.add(sArray2[i]);
                            nodes.add(new GraphNode(sArray2[i]));
                        }
                    }
                }
            } //after completing this while loop, 'courses' is a list of all unique course codes/strings.
            //Re-read the file to create links between these GraphNodes.
            reader = new BufferedReader(new FileReader("input.txt"));
            s = "";
            while((s = reader.readLine()) != null){
                sArray = s.split(":");
                if(sArray.length > 1){ //if sArray.length == 1, that means the course has NO pre-requisites
                    //Consider s = "4041:1933 2011". We want the 1933 and 2011 nodes to have a directed edge pointing towards 4041.
                    GraphNode course = findNode(nodes, sArray[0]); //'course' would == the "4041" node.
                    String[] prereqStrings = sArray[1].split(" ");
                    for(int i = 0; i < prereqStrings.length; i++){ //iterate over the pre-requisites and create links between nodes where appropriate.
                        GraphNode obj = findNode(nodes, prereqStrings[i]);
                        obj.insertPair(course, 1);
                    }
                }
            }
            Problem2 P2 = new Problem2(nodes);
            LinkedList<GraphNode> sortedNodes = P2.topologicalSort(); //Call topologicalSort on our list of nodes
            String output = "";
            int size = sortedNodes.size();
            for(int i = 0; i < size; i++){ //build the output string so we can write it to "output.txt"
                if(i < size - 1){
                    output += sortedNodes.get(i).getData() + " ";
                }
                else{
                    output += sortedNodes.get(i).getData();
                }
            }
            writer.write(output);

            writer.close();
            reader.close();
        }
        catch(Exception e){
            System.out.println("Invalid filename/path.");
        }

    }

}
