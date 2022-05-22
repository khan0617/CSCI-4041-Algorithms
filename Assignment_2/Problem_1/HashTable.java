import java.lang.Math;
import java.io.*;
import java.util.LinkedList;
import java.util.ArrayList;

public class HashTable {
    private Node[] t; //this will be the "table" which will handle collisions using linked nodes

    public HashTable(int tableSize){ //when passed in, tableSize should be a prime number. By default, I will pass in 7.
         t = new Node[tableSize];
    }

    private int hash(String s){ //take in a node (with key / name attributes) and create a hash value for it
        //this hash function uses the very simple "division" method from lecture
        int stringVal = 0; //If Strings are used as the key, find the ASCII equivalent of all the characters together
        char[] c = s.toCharArray();
        for(int i = 0; i < c.length; i++){
            stringVal += c[i];
        }

        return (stringVal % t.length);
    }

    private void insert(Node n){
        Node object = search(n.getName()); //first, check to see if the node with the specific key already exists in the table
        if(object == null){ //this means the node with this specific name does not exist in the list already, so let's insert it.
            int index = hash(n.getName()); //generate a hash value so we can insert this new node, n
            n.setNext(t[index]); //add this new node to the front of the linked list. Thus, we add the already existing list to n's "next" field
            if(t[index] != null){ //t[index] will be null the first time we insert an element into this spot, check it's not null before calling a function on it.
                t[index].setPrevious(n); //set this new node as the previous of the old one
            }
            t[index] = n;
        }
        else{ //the node already exists, so update its key (int / grade) value instead of making a new node.
            object.setKey(n.getKey());
        }
    }

    private Node search(String s){
        int index = hash(s);
        Node data = t[index];
        while(data != null){
            if(data.getName().equals(s)){ //check the linked list and if data's "name" attribute matches s, we've found the right node. Return data else keep looping.
                return data;
            }
            data = data.getNext();
        }
        return null;
    }

    public static void main(String[] args){
        HashTable h = new HashTable(7);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String s;
            String[] a;
            String output = "";

            while((s = reader.readLine()) != null){
                a = s.split(" ");
                if(a[0].equalsIgnoreCase("put")){
                    String name = a[1];
                    int grade = Integer.parseInt(a[2]);
                    h.insert(new Node(grade, name));
                }
                else if(a[0].equalsIgnoreCase("get")){
                    Node n = h.search(a[1]); //look for the node in the hash table
                    if(n == null){
                        output += "none\n";
                    }
                    else{
                        output += n.getKey() + "\n";
                    }
                }
            }
            writer.write(output);
            reader.close();
            writer.close();
        }
        catch(Exception e){
            System.out.println("Invalid filename/filepath.");
        }
    } //end main()
}
