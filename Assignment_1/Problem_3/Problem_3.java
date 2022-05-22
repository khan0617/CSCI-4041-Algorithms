import java.io.*;
import java.lang.Math;

public class Problem_3 {

    //randomizedSelect is avg O(n). Takes in array to select from, p and r are start and end indexes respectively, and i is ith smallest index to select
    public int randomizedSelect(int[] A, int p, int r, int i){
        if(p == r){
            return A[p];
        }
        int q = randomizedPartition(A, p, r);
        int k = q - p + 1; //k should be the length of the subarray from the low index 'p' to randomized partition pivot 'q'
        if(i == k){
            return A[q];
        }
        else if(i < k){
            return randomizedSelect(A, p, q-1, i); //use recursion on the left subarray since our ith element to select is < the pivot element k
        }
        else{
            return randomizedSelect(A, q+1, r, i-k); //else use recursion on right subarray since i > k
        }
    }

    public int randomizedPartition(int[]A, int p, int r){
        int rand = (int) ((Math.random() * (r - p)) + p); //Choose a random number between p (the min) and r (the max), inclusive.
        int temp = A[r]; //swap A[r] and A[rand]
        A[r] = A[rand];
        A[rand] = temp;
        return partition(A, p, r);
    }

    public int partition(int[] A, int p, int r){ //p is the lower index, r is the upper index of A[].
        int x = A[r]; //x is the value of the pivot index
        int i = p - 1; //i starts as invalid, 1 index out of bounds before p.
        for(int j = p; j <= r-1; j++){
            if(A[j] <= x){
                i = i + 1;
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        int temp = A[i+1];
        A[i+1] = A[r];
        A[r] = temp;
        return (i + 1); //this is the spot the pivot is moved into
    }

    public static void main(String[] args){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String s;
            String[] sArray;
            Problem_3 select = new Problem_3();
            int i = Integer.parseInt(reader.readLine()); // Assumes the first line of the file is the 'ith' element we want to select.
            s = reader.readLine(); //'s' should be the line containing the array now
            sArray = s.split(" ");
            int[] A = new int[sArray.length];

            for(int j = 0; j < sArray.length; j++){ //Fill out A[] so that it may be passed to randomizedSelect.
                A[j]= Integer.parseInt(sArray[j]);
            }

            int ithElement = select.randomizedSelect(A, 0, A.length - 1, i); //Find the ith element
            writer.write(Integer.toString(ithElement));
            reader.close();
            writer.close();
        }
        catch(Exception e){
            System.out.println("Invalid filepath.");
        }
    }
}
