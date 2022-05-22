import java.util.*;
import java.io.*;

public class Problem_2 {

    public QueueNode[] BuildMinHeap(QueueNode[] A){
        for(int i = A.length/2 - 1; i >= 0; i--){
            MinHeapify(A, A.length, i);
        }
        return A;
    }

    public void HeapSort(QueueNode[] A){ //This heapsort will sort from largest to smallest: ex: [6,5,4,3,2,1]
        BuildMinHeap(A);
        for(int i = A.length - 1; i >= 1; i--){
            QueueNode temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            MinHeapify(A,i,0);
        }
    }

    private void MinHeapify(QueueNode[] A, int heapSize, int i){
        int left = 2*i + 1; //2*i + 1 because of 0-indexing of arrays
        int right = 2*i + 2;
        int smallest = i;

        if(left < heapSize && A[left].getFrequency() < A[smallest].getFrequency()){ //put the smaller items at the front
            smallest = left;
        }

        if(right < heapSize && A[right].getFrequency() < A[smallest].getFrequency()){
            smallest = right;
        }

        if(smallest != i){
            QueueNode temp = A[i];
            A[i] = A[smallest];
            A[smallest] = temp;
            MinHeapify(A, heapSize, smallest);
        }
    }

    public QueueNode Minimum(QueueNode[] A){
        return A[0];
    }

    private void IncreaseKey(QueueNode[] A, int i, QueueNode key){
        A[i] = key;
        while(i > 0 && A[(int) (i-1)/2].getFrequency() > A[i].getFrequency()){ //For min priority queue, swap parent and child if child's frequency is < parent's frequency.
            QueueNode temp = A[i];
            A[i] = A[(int) (i-1)/2];
            A[(int) (i-1)/2] = temp;
            i = (int) (i-1)/2;
        }
    }

    public QueueNode[] Insert(QueueNode[] A, QueueNode key){ //insert a queuenode into the min priority queue in the correctlocation
        QueueNode[] largerArray = new QueueNode[A.length + 1]; //Create an array larger than the input array by 1, so we have space to insert
        largerArray[largerArray.length - 1] = new QueueNode(Integer.MAX_VALUE);
        for(int i = 0; i < A.length; i++){
            largerArray[i] = A[i];
        }
        IncreaseKey(largerArray, largerArray.length - 1, key);
        return largerArray;
    }

    public QueueNode[] ShrinkArray(QueueNode[] A){ //To be called after ExtractMax. 'Remove' the last item of the array and return it since the node has been 'extracted'
        if(A.length > 0) { //prevents invalid indexing of shrunkArray
            QueueNode[] shrunkArray = new QueueNode[A.length - 1];
            for (int i = 0; i < shrunkArray.length; i++) {
                shrunkArray[i] = A[i];
            }
            return shrunkArray;
        }
        return null;
    }

    public QueueNode ExtractMin(QueueNode[] A){ //extract and return the minimum of the min priority queue. meant to be used in tandem with ShrinkArray() to actually remove the last element.
        QueueNode min = A[0];
        int heapSize = A.length;

        QueueNode temp = A[0];
        A[0] = A[heapSize - 1];
        A[heapSize - 1] = temp;

        MinHeapify(A, heapSize - 1, 0);
        return min;
    }

    public QueueNode[] Huffman(QueueNode[] C){ //this method simply implements the Huffman(C) algorithm shown on pg 431 of the textbook.
        int n = C.length;
        QueueNode[] Q = BuildMinHeap(C);
        for(int i = 1; i <= n-1; i++){
            QueueNode z = new QueueNode();
            QueueNode left = ExtractMin(Q);
            Q = ShrinkArray(Q);
            QueueNode right = ExtractMin(Q);
            Q = ShrinkArray(Q);
            z.setFrequency(left.getFrequency() + right.getFrequency());
            z.setLeft(left);
            z.setRight(right);
            Q = Insert(Q, z);
        }
        return Q;
    }

    public String encode(QueueNode node, char c){
        //use this method to find the position and encoding of char c within node's left/right branches.
        //The way I set up my huffmann encoding I can assume that the value of char c should always exist within this tree.
        //Assuming that the first node passed in does not have a valid char value. Another efficient way of finding the encoding could be to traverse the tree once and return some sort of collection/array of encodings.
        if(node != null) {
            //make sure node.getRight is not null before calling getChar() on it
            if(node.getLeft() != null) {
                if (node.getLeft().getChar() == c) { //see if the desired node is the left child of the current node
                    return "0";
                }
            }
            if (node.getRight() != null) {
                if(node.getRight().getChar() == c){
                    return "1";
                }
            }
            //if we get here, this means that we haven't found the correct spot in the tree yet. Call encode recursively again on left and right nodes
            String r = encode(node.getRight(), c);
            if (r != null) { //if r is null that means the node with char c is not on the right of our current node
                r = "1" + r;//we've found where r is recursively, just add 1 last "1" to the front of the string for the final traversal to the right
                return r;
            }
            String l = encode(node.getLeft(), c);
            if (l != null) {
                l = "0" + l;
                return l;
            }
        }
        return null; //if this occurs, this means that both node.right and node.left are null, so the desired char value cannot be on this side.
    }

    public static void main(String[] args){
        Problem_2 P2 = new Problem_2();
        try{
            //in charFreq[], index 0 corresponds to 'a', index 1 to 'b', ...., index 25 to 'z'.
            QueueNode[] chars = new QueueNode[26];//this will keep track of the frequencies of every possible char in the input file.
            int nonNullNodeCounter = 0; //this keeps track of how many unique chars the input file has (if only 'a,b,c,d' are in the file, then nonNullNodeCounter = 4.
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String s;

            while((s = reader.readLine()) != null){
                //iterate over each char of the input string and increase the frequency of its corresponding queueNode in charFreq[]
                for(int i = 0; i < s.length(); i++){
                    int charValue = (int) s.charAt(i);
                    if(chars[charValue - 97] == null){//no node exists for this char yet, so make one. minimum ASCII value is for 'a' == 97, so subtract 97 to scale it into the array.
                        chars[charValue - 97] = new QueueNode((char) charValue, 1); //create a new node, place it in the array, and set its frequency to 1.
                        nonNullNodeCounter += 1;
                    }
                    else{
                        QueueNode currNode = chars[charValue - 97];
                        currNode.setFrequency(currNode.getFrequency() + 1); //increase the frequency attribute of the QueueNode by 1
                    }
                }
            }

            QueueNode[] detectedChars = new QueueNode[nonNullNodeCounter]; //create another array that has no null nodes (meaning it only has characters that appeared in 'input.txt')
            for(int i = 0; i < chars.length; i++){ //the way that this works, detectedChars is filled in alphabetical order, which allows outputting to the file later much easier.
                if(chars[i] != null){
                    detectedChars[i] = chars[i];
                }
            }

            QueueNode[] copiedArray = new QueueNode[detectedChars.length];
            for(int i = 0; i < copiedArray.length; i++){ //create a copy of detectedChars[] so that Huffman() may be called on it without losing the alphabetical ordering.
                copiedArray[i] = detectedChars[i];
            }

            QueueNode[] node = P2.Huffman(copiedArray); //my huffman alg implementation will reduce copiedArray down to a single node, the root of the tree.
            String output = "";
            for(int i = 0; i < detectedChars.length; i++){ //iterate over detectedChars[], calling encode() to create our output string to the file.
                char currentChar = detectedChars[i].getChar();
                String currentEncoding = P2.encode(node[0], currentChar);
                if( i < detectedChars.length - 1){ //if statement checks to see if we should add a \n or not
                    output += currentChar + ": " + currentEncoding + "\n";
                }
                else{ //loop is on its last iteration, so don't add one more newline character.
                    output += currentChar + ": " + currentEncoding;
                }
            }
            writer.write(output);
            writer.close();
            reader.close();
        }
        catch(Exception e){
            System.out.println("Invalid filepath.");
        }
    }
}
