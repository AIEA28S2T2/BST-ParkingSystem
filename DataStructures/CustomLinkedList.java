package DataStructures;

/*
    * Since we're reusing the same node datatype for this queue, we'll make the parent point to null,
    * and the right pointer point to the next node in the queue. 
    * I don't think we'll even have to use two pointers, one should be fine for our application;
    * We'll only be searching from top to bottom anyway
*/

public class CustomLinkedList {
    protected Node head = null;
    protected Node tail = null;

    protected void enqueue(Node node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.right = node;
            tail = node;
        }
    }

    protected Node findAndPop(String regno) {
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.regNo.equals(regno)) {
                if (previous == null) {
                    if (current.right != null) {
                        head = current.right;
                    } else {
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

    protected void clearQueue() {
        if (head == null) {
            return; // Queue is already empty
        }
        
        Node current = head;
        while (current != null) {
            Node next = current.right;
            current.right = null;
            current = next;
        }
        
        head = null;
        tail = null;
    }
    public void printQueue() {
        System.out.println("Printing Queue Contents:");
        if (head == null) {
            System.out.println("The queue is empty");
            return;
        }
        
        int position = 1;
        Node current = head;
        while (current != null) {
            System.out.println("Position " + position + ": Spot ID: " + current.spotID + 
                              ", Registration: " + (current.regNo != null ? current.regNo : "None"));
            current = current.right;
            position++;
        }
        System.out.println("End of queue");
    }
}