import javax.annotation.processing.Filer;
import java.io.*;
import java.util.*;

public class Problem_4 {

    private ArrayList<String> insertionSort(ArrayList<String> A){ //Insertion sort using textbook's algorithm
        for(int j = 1; j < A.size(); j++){
            int i = j - 1;
            String key = A.get(j);
            while( i >= 0 && A.get(i).compareTo(key) > 0){ //when compareTo returns a positive
                A.set(i+1, A.get(i)); //Set A[i+1] = A[i] in the ArrayList
                i = i - 1;
            }
            A.set(i+1, key);
        }
        return A;
    }

    private ArrayList<String> bucketSort(ArrayList<String> A, int X) { //Designed to be called with X == 0 initially
        //First, determine the range the values the buckets will need by looking at each string's first char.
        int n = A.size(); //n should be the number of buckets we will eventually make
        int min = (int) A.get(0).charAt(X); //let min, max represent the min/max ASCII values of the first letters of the strings in A[]
        int max = (int) A.get(0).charAt(X);
        int minStringLength = A.get(0).length(); //Keep track of the length of the smallest String/name. This will be useful when recursively calling bucketSort.

        for (int i = 0; i < A.size(); i++) { //loop through all strings in A to find the max and min ASCII values of the "Xth" char of each string, denoted by parameter int X, as well as shortest name size.
            if(A.get(i).length() < minStringLength){
                minStringLength = A.get(i).length();
            }
            char currentChar = A.get(i).charAt(X);
            if (currentChar > max) {
                max = currentChar;
            }
            if (currentChar < min) {
                min = currentChar;
            }
        }
        max = max + 1; //increase max by 1 to make sure data range will be [0,1), or NOT inclusive of 1.
        int range = (max - min);

        //next, make an array of arrayLists, which will serve as the buckets.
        ArrayList<String>[] buckets = new ArrayList[n];
        for(int i = 0; i < n; i++){
            buckets[i] = new ArrayList<String>();
        }

        //populate buckets bu scaling the value of the first char in each string in [0,1) using the 'range' value.
        for(int i = 0; i < n; i++){ //by subtracting min from A[i].charAt(0) and then dividing by range, we guarantee a scaling between [0, 1);
            //multiplying this index value by n and then taking the floor guarantees it will go into a bucket.
            char currentChar = A.get(i).charAt(X);
            int index = (int) (n * (currentChar - min) / range);
            buckets[index].add(A.get(i)); //Add the string from A[i] to the correct bucket.
        }

        //now all of the buckets have been populated. Check if any of them have more than 10 elements.
        //If so, recursively call bucketSort on that particular bucket, otherwise use insertion sort on each bucket.
        for(int i = 0; i < n; i++){
            if(buckets[i].size() > 10 && X < minStringLength - 1){ //ONLY recursively call when X < minStringLength-1, otherwise out of bounds errors occur. If X == minStringLength, this means all the names are actually identical in this bucket.
                bucketSort(buckets[i], X + 1); //call bucketSort again but this time allocate buckets using the next character.
            }
            else{
                buckets[i] = insertionSort(buckets[i]);
            }
        }
        //finally, repopulate the original arraylist using the sorted buckets.
        A.clear();
        for(int i = 0; i < buckets.length; i++){
            A.addAll(buckets[i]);
        }
        return A;
    }

    public static void main(String[] args){
        Problem_4 bucket = new Problem_4();

        try{ //Program will read the first line of the file, call bucketSort on those names, and then write them in order to output.txt
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            String[] s = (reader.readLine()).split(" ");  //Read in the names from the file, assuming they're all on the first line of the file.
            ArrayList<String> A = bucket.bucketSort(new ArrayList<String>(Arrays.asList(s)), 0); //Call bucketSort with s[] passed in to fill an ArrayList

            String output = ""; //Build the output string to write to output.txt
            for(int i = 0; i < A.size(); i++){
                if(i != A.size() - 1){
                    output += A.get(i) + " ";
                }
                else{ //appending the final name to s, so do NOT add an extra space at the end
                    output += A.get(i);
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
