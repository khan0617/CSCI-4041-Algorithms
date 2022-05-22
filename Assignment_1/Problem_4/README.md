Problem 4: Recursive BucketSort
KNOWN BUG: Doesn't work 100% correctly when using recursion, overall order is preserved but ordering within a specific letter (like if we have Alan, Avery, etc..) may not work.

USAGE: Assuming Java is installed and added to PATH variable
	javac Problem_4.java
	java Problem_4

Summary: 
	The program reads the first line from "input.txt", which should be a list of names separated by spaces. Then, it implements a bucketSort algorithm to 
	sort these names and then print them to an "output.txt" local to where the code is run. To easily implement the creation of buckets, I used ArrayLists.
	To sort each individual bucket, insertionSort is used, and then the buckets are merged simply using nested for loops.

Methods:

bucketSort():
	Takes in an ArrayList<String> which should be a list of names, as well as an int X which represents which char of the strings to compare (designed to be called with X = 0 on the first time). Then, by examining only the first character of each name in the ArrayList, I determine
	a max and min ASCII char value to create a range. The range = (max - min) + 1. I add the one because when placing the Strings into buckets, we cannot
	have a value of exactly n*1, because if an array is length n, then A[n*1] is out of bounds. Then, I create an array of ArrayList<String>, which becomes 
	the buckets. The number of buckets (n) created is equivalent to the number of names found in the input file. I place the items into buckets by scaling the
	first char of each name by doing: (charValue - min) / (range). When charValue == min, the fraction = 0. When charValue == max, the fraction is:
	(max - min)/(max - min + 1), which is almost 1 but not quite. Then simply calculate the index to place the bucket as: floor(n * firstChar * scalingFactor).
	Once the buckets are populated, it's time to sort them using insertionSort. However, if any of the buckets have a size larger than 10 elements, I recursively
	call bucketSort on that individual bucket (bucketSort(buckets[i], X+1) recursively calls the sort on the ith bucket and moves increases the comparing character by 1). Else, I simply call insertionSort on that bucket.
	The method returns a sorted ArrayList<String>, so I simply iterate over the number of buckets, and then over each sorted bucket, to populate the sorted ArrayList.
	
insertionSort():
	Takes in an ArrayList<String> and simply implements the book's Insertion Sort algorithm. Returns a sorted ArrayList.