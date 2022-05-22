public class Node {

    private String name;
    private int key;
    private Node next = null;
    private Node previous = null;

    Node(int k, String n){
        key = k;
        name = n;
    }

    public void setNext(Node n){
        next = n;
    }

    public Node getNext(){
        return next;
    }

    public void setPrevious(Node n){
        previous = n;
    }

    public Node getPrevious(){
        return previous;
    }

    public String getName(){
        return name;
    }

    public int getKey(){
        return key;
    }

    public void setKey(int k){
        key = k;
    }

    public void setName(String n){
        name = n;
    }

}
