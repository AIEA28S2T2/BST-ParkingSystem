package DataStructures;

public class ParkingSystem {
    private Tree tree;
    private CustomLinkedList queue = new CustomLinkedList();
    
    // Tree type constants
    public static final int BST = 0;
    public static final int AVL = 1;
    public static final int RED_BLACK = 2;
    
    public ParkingSystem(int n) {
        this(n, BST); // Default to BST
    }
    
    public ParkingSystem(int n, int treeType) {
        switch (treeType) {
            case AVL:
                tree = new AVLTree();
                ((AVLTree)tree).createTree(n);
                break;
            case RED_BLACK:
                tree = new RedBlackTree();
                ((RedBlackTree)tree).createTree(n);
                break;
            default: // BST
                tree = new BinarySearchTree();
                ((BinarySearchTree)tree).createTree(n);
                break;
        }
    }

    public void addCar(String RegNo) {
        Node node = tree.removeSpot(tree.searchForFreeSpot());
        queue.enqueue(node);
        node.setRegNo(RegNo);
    }

    public void removeCar(String RegNo) {
        Node node = queue.findAndPop(RegNo);
        if (node != null) {
            node.setRegNo(null);
            tree.insertSpot(node);
        } else {
            System.out.println("Car with registration number " + RegNo + " not found.");
        }
    }
    
    public void printTree() {
        tree.printTree();
    }
    
    public void printQueue() {
        queue.printQueue();
    }
}
