public class HeapSortTest {

    private void MaxHeapify(int[] A, int heapSize, int i){
        int left = 2*i + 1;
        int right = 2*i + 2;
        int largest = i;
        if(left < heapSize && A[left] > A[largest]){ //this will sort from lowest to highest once calling heapSort
            largest = left;
        }
        if(right < heapSize && A[right] > A[largest]){
            largest = right;
        }
        if(largest != i){
            int temp = A[i];
            A[i] = A[largest];
            A[largest] = temp;
            MaxHeapify(A, heapSize, largest);
        }

    }

    private void BuildMaxHeap(int[] A){
        for(int i = A.length/2 - 1; i >= 0; i--){
            MaxHeapify(A, A.length, i);
        }
    }

    public void HeapSort(int[] A){
        BuildMaxHeap(A);
        for(int i = A.length - 1; i >= 1; i--){
            int temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            MaxHeapify(A, i, 0);
        }
    }

    public static void main(String[] args){
        int[] test = {5, 13, 2, 25, 7, 17, 20, 8, 4};
        //int[] test = {1,2,3,4,5};
        HeapSortTest sortTest = new HeapSortTest();
        String s = "";
        for(int i = 0; i < test.length; i++){
            s += test[i] + " ";
        }
        System.out.println("Before BuildMaxHeap: " + s);

/*        sortTest.BuildMaxHeap(test);
        s= "";
        for(int i = 0; i < test.length; i++){
            s += test[i] + " ";
        }
        System.out.println("After BuildMaxHeap: " + s);*/


        sortTest.BuildMaxHeap(test);
        s= "";
        for(int i = 0; i < test.length; i++){
            s += test[i] + " ";
        }
        System.out.println("After BuildMaxHeap: " + s);

    }

}
