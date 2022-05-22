import java.io.*;
import java.lang.Math;

public class Problem_1 {



    public void BuildMaxHeap(QueueNode[] A){
        for(int i = A.length/2 - 1; i >= 0; i--){
            MaxHeapify(A, A.length, i);
        }
    }

    public void HeapSort(QueueNode[] A){
        BuildMaxHeap(A);
        for(int i = A.length - 1; i >= 1; i--){
            QueueNode temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            MaxHeapify(A,i,0);
        }
    }

    private void MaxHeapify(QueueNode[] A, int heapSize, int i){
        int left = 2*i + 1; //2*i + 1 because of 0-indexing of arrays
        int right = 2*i + 2;
        int largest = i;

        if(left < heapSize && A[left].getQueuePosition() > A[largest].getQueuePosition()){
            largest = left;
        }

        if(right < heapSize && A[right].getQueuePosition() > A[largest].getQueuePosition()){
            largest = right;
        }

        if(largest != i){
            QueueNode temp = A[i];
            A[i] = A[largest];
            A[largest] = temp;
            MaxHeapify(A, heapSize, largest);
        }
    }

    public QueueNode ExtractMax(QueueNode[] A){
        QueueNode max = A[0];
        int heapSize = A.length;

        QueueNode temp = A[0];
        A[0] = A[heapSize - 1];
        A[heapSize - 1] = temp;

        MaxHeapify(A, heapSize - 1, 0);
        return max;
    }

    public QueueNode Maximum(QueueNode[] A){
        return A[0];
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

    private void IncreaseKey(QueueNode[] A, int i, QueueNode key){
        A[i] = key;
        while(i > 0 && A[(int) (i-1)/2].getQueuePosition() < A[i].getQueuePosition()){ //Check if the i's parent's key is smaller than i's key, and swap accordingly
            QueueNode temp = A[i];
            A[i] = A[(int) (i-1)/2];
            A[(int) (i-1)/2] = temp;
            i = (int) (i-1)/2;
        }
    }

    public QueueNode[] Insert(QueueNode[] A, QueueNode key){
        QueueNode[] largerArray = new QueueNode[A.length + 1]; //Create an array larger than the input array by 1, so we have space to insert
        largerArray[largerArray.length - 1] = new QueueNode("Dummy", Integer.MIN_VALUE);
        for(int i = 0; i < A.length; i++){
            largerArray[i] = A[i];
        }
        IncreaseKey(largerArray, largerArray.length - 1, key);
        return largerArray;
    }

    public String extractString(QueueNode[] A){

        return null;
    }


    public static void main(String[] args){

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            Problem_1 heap = new Problem_1();
            QueueNode[] list = new QueueNode[0];
            String s;
            while((s = reader.readLine()) != null){
                String[] a = s.split(" ");
                if(a[0].equalsIgnoreCase("insert")){
                    list = heap.Insert(list, new QueueNode(a[1], Integer.parseInt(a[2]))); //This assumes that an insert command is called as: insert(string) + NAME(string) + QueuePosition(int)
                }
                else if(a[0].equalsIgnoreCase("max")){ //This means we want to find the max and print it to the 'output.txt' file
                    QueueNode max = heap.Maximum(list);
                    writer.write(max.getQueueName() + "\n");
                }
                else if(a[0].equalsIgnoreCase("extract")){ //We want to find the max, print it, and remove the max from the priority queue
                    QueueNode max = heap.ExtractMax(list);
                    //writer.write(max.getQueueName() + "\n");
                    list = heap.ShrinkArray(list); //This method call removes the last element of the list, which after calling ExtractMax should be the previous Max.
                }
                else if(a[0].equalsIgnoreCase("quit")){ //Exit the while loop and print the rest of the queue in reverse heap-sorted order.
                    String end = "";
                    heap.HeapSort(list); //Calling this sorts the remaining PriorityQueue from lowest to highest QueuePosition. Thus, the list has to be read backwards.
                    for(int i = list.length - 1; i >= 0; i--){
                        if(i != 0){
                            end += list[i].getQueueName() + " ";
                        }
                        else{
                            end += list[i].getQueueName();
                        }
                    }
                    writer.write(end);
                    break;
                }
            }
            writer.close();
            reader.close();
        }
        catch(Exception e){
            System.out.println("Invalid filepath.");
        }
    }
}
