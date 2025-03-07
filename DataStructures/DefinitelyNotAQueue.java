package DataStructures;

/*
    * Since we're reusing the same node datatype for this queue, we'll make the parent point to null,
    * and the right pointer point to the next node in the queue. 
    * I don't think we'll even have to use two pointers, one should be fine for our application;
    * We'll only be searching from top to bottom anyway
*/

public class DefinitelyNotAQueue {
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
                    if (current.right != null){
                        head = current.right;
                    }
                    else{
                        head = null;
                        tail = null;
                    }
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

    protected void clearQueue(){
        Node current = head;
        head = null;
        tail = null;
        Node next = current.right;
        while (current.right != null) {
            current.right = null;
            current = next;
            if (current.right != null){
                next = current.right;
            }
        }
    }
    protected void showQueue(){
        if(head==null){
            System.out.println("oops the queue is empty");
            return;
        }
        Node current = head;
        while(current.right != null){
            System.out.println(current.regNo);
            System.out.println(current.spotID);
            System.out.println();
            current = current.right;
        }
        System.out.println(current.regNo);
        System.out.println(current.spotID);
        System.out.println();
    }

}