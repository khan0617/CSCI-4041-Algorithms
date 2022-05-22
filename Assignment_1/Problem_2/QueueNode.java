import java.util.Queue;

public class QueueNode {

    private int frequency = 0;
    private char c;
    private QueueNode left;
    private QueueNode right;

    public QueueNode(char character, int freq){
        frequency = freq;
        c = character;
    }

    public QueueNode(int freq){
        frequency = freq;
    }

    public QueueNode(){}

    public int getFrequency(){
        return frequency;
    }

    public void setFrequency(int freq){
        frequency = freq;
    }

    public char getChar(){
        return c;
    }

    public void setChar(char ch){
        c = ch;
    }

    public String toString(){
        if(((Character) c).equals(Character.MIN_VALUE)){ //this means the char was not initialized.
            return null;
        }
        return Character.toString(c);
    }

    public void setLeft(QueueNode l){
        left = l;
    }

    public void setRight(QueueNode r){
        right = r;
    }

    public QueueNode getRight(){
        return right;
    }

    public QueueNode getLeft(){
        return left;
    }
}
