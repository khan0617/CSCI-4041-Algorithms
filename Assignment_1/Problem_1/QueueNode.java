
public class QueueNode{

    //this class will be used in Problem 1 when adding people to the queue.
    //it's a simple object with 'name' and 'queue position' class attributes

    private int queuePosition;
    private String queueName;

    public QueueNode(String name, int queuePos){
        queueName = name;
        queuePosition = queuePos;
    }

    public String getQueueName(){
        return queueName;
    }

    public int getQueuePosition(){
        return queuePosition;
    }

    public void setQueuePosition(int newPos){
        queuePosition = newPos;
    }

    public String toString(){
        return queueName + " in Position: " + queuePosition;
    }

}
