Problem 1 README

USAGE: Ensure Java is installed and added to your PATH variable. <br />
Then: javac Problem_1.java <br />
      java Problem_1


QueueNode Class:
-The QueueNode Class is used to keep associate a QueuePosition with a QueueName. i.e. QueueNode('Sally', 15) creates a new QueueNode with the name and queuePos initialized respectively.
-In the main Problem_1 class, almost all of the methods are using QueueNode[] as parameters.
-Simple getQueueName() and getQueuePos() methods return the queueName as a String and queuePos as an int. 
-toString() prints the contents of a node as: "queueName in Position queuePos".
	Example: "Sally in Position 5".

Problem_1:

BuildMaxHeap - As part of the HeapSort algorithm, this method simply builds a maximum heap.

MaxHeapify - As part of the HeapSort algorithm, this maintains a max heap by comparing QueueNode 'queuePos' values.

HeapSort - Calls BuildMaxHeap as well as MaxHeapify to sort the queue from LOWEST to HIGHEST priority. Does not sort by name.
	Example of before and after function call: if list = [("Sally", 15), ("Joe", 1), ("Riley", 5)]
	AFTER HeapSort: list = [("Joe", 1), ("Riley", 5), ("Sally", 15)]

Maximum - Simply returns the root of the max heap as a QueueNode.

ExtractMax - returns the max of the heap as a QueueNode. Removes the max from the heap by moving it to the end of the QueueNode[] array and then calling MaxHeapify while excluding the last element.
	***IMPORTANT***: Since this implementation uses an array, the previous maximum will remain at the end of the array one calls ShrinkArray(QueueNode[A]).
	Example: BEFORE: list = [("Sally", 15), ("Joe", 1), ("Riley", 5)]
		 AFTER without calling ShrinkArray():  list = [("Joe", 1), ("Riley", 5), ("Sally", 15)]
	         AFTER WITH ShrinkArray(): list = [("Joe", 1), ("Riley", 5)]

ShrinkArray - Simply returns a QueueNode[] that is the same as input A[] but with A[]'s last element removed.

Insert - As part of maintaining the Priority Queue, this method inserts a QueueNode into its correct position in the queue by placing it at the end of the array and then calling IncreaseKey on it.

IncreaseKey(A[], i, key) - Changes the key of A[i] to the parameter key, and then shifts the position of A[i] until it satisfies the max heap property.


main():
-This part of the program simply reads from an "input.txt" file local to where the code is being run and creates and creates/manipulates a QueueNode[] accordingly.
-The program assumes only valid commands are input, only 1 per line, such as:
	-"max"
	-"quit"
	-"extract"
	-"insert Sally 15"
-BufferedReader is used to read "input.txt" and BufferedWriter is used to write to "output.txt", also local to where the code is executed.
-"extract" inputs are associated with a call to both ExtractMax and ShrinkArray.
-"quit" corresponds with a call to HeapSort. After this, a string representing the QueueNode[] is built from the end of the array to the beginning (largest to smallest key QueuePos values) and written to "output.txt".
-"max" means the maximum of the Priority Queue is written to the file on its own line.
	





