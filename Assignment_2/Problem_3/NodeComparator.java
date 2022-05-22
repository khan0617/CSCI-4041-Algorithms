import java.util.*;

class NodeComparator implements Comparator<GraphNode>{
    //this class simply overrides the default compare() function used in the PriorityQueue's ordering so that it works with the GraphNode class.

    public int compare(GraphNode g1, GraphNode g2){
        if(g1.getD() < g2.getD()){ //g1 is < g2, so return -1
            return -1;
        }
        else if(g1.getD() > g2.getD()){
            return 1; //g1 has a larger .d value than g2
        }
        return 0; //they're the same .d value
    }

}
