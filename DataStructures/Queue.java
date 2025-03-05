package DataStructures;

/*
    * Since we're reusing the same node datatype for this queue, we'll make the parent point to null,
    * and the left and right pointers point to the next and previous nodes in the queue respectively. 
    * I don't think we'll even have to use two pointers, one should be fine for our application;
    * We'll only be searching from top to bottom anyway
*/

public class Queue {
    protected Node head = null;
    protected Node tail = null;

    protected void enqueue(Node node){
        node.parent = null;
        node.left = null;
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.right = node;
            tail = node;
        }
    }

    protected Node findAndPop(String regno){
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.regNo.equals(regno)) {
                if (previous == null) {
                    head = current.right;
                } else {
                    previous.right = current.right;
                }
                return current;
            }
            previous = current;
            current = current.right;
        }
        return null;
    }


}
