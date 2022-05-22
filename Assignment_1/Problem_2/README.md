Problem 2: Huffman Encoding implementation

The program expects "input.txt" to exist in the same directory.
USAGE INSTRUCTIONS: Make sure Java is installed and has been added to your system PATH.
	javac Problem_2.java
	java Problem_2

Summary: 
	This program reads from an "input.txt" local to where the code is executed, performs Huffman encoding on the characters on the file (lowercase a-z), and writes the unique encodings to an "output.txt".
	I directly implemented the Huffman pseudocode on pg 431 from the textbook. Additionally, I was able to repurpose my Problem 1 Max Heap code to allow it to create min heaps instead, for use with Huffman().
	 

Implementation:
	The program features a Problem_2.java as well as a QueueNode.java. The QueueNodes are simple objects which have char, frequency, node.left, and node.right attributes. Basic nodes only have char and freq data.
	The program first creates a length 26 array and populates it with QueueNodes holding the frequency of chars in the input.txt. For example, if there are 5 'a's in the file, chars[0] = QueueNode('a', freq = 5).
	Or if there are 10 'z's in the file, chars[25] = QueueNode('z',freq = 10). The array is not guaranteed to be completely filled, so I copy over all of the non-null nodes to a smaller array.A copy of this 
	smaller array is then fed to my Huffman() method. The Huffman method will generally return an array consisting of a single QueueNode, but this QueueNode itself is a tree root with left/right node pointers. Next,
	I simply iterate through all the chars I want to generate an encoding for using an array, by calling encode(root). Use one last for loop to create the output string of all the encoding and write it to the output.txt.

Note: A lot of this code was recycled from my Problem_1, and my QueueNodes from problem one were also reused and slightly altered for this problem.
	