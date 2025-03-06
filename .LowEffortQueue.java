// Node class representing a single node in the linked list
class Node {
    int data;
    Node next;

    Node(int new_data) {
        this.data = new_data;
        this.next = null;
    }
}

// Class to implement queue operations using a linked list
class LowEffortQueue {
  
    // Pointer to the front and the rear of the linked list
    Node front, rear;

    // Constructor to initialize the front and rear
    LowEffortQueue() { front = rear = null; }

    // Function to check if the queue is empty
    boolean isEmpty() {
      
        // If the front and rear are null, then the queue is
        // empty, otherwise it's not
        return front == null && rear == null;
    }

    // Function to add an element to the queue
    void enqueue(int new_data) {
      
        // Create a new linked list node
        Node new_node = new Node(new_data);

        // If queue is empty, the new node is both the front
        // and rear
        if (rear == null) {
            front = rear = new_node;
            return;
        }

        // Add the new node at the end of the queue and
        // change rear
        rear.next = new_node;
        rear = new_node;
    }

    // Function to remove an element from the queue
    void dequeue() {
      
        // If queue is empty, return
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            return;
        }

        // Store previous front and move front one node
        // ahead
        Node temp = front;
        front = front.next;

        // If front becomes null, then change rear also
        // to null
        if (front == null) {
            rear = null;
        }
    }

    // Function to get the front element of the queue
    int getFront() {
      
        // Checking if the queue is empty
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return Integer.MIN_VALUE;
        }
        return front.data;
    }

    // Function to get the rear element of the queue
    int getRear() {
      
        // Checking if the queue is empty
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return Integer.MIN_VALUE;
        }
        return rear.data;
    }
}