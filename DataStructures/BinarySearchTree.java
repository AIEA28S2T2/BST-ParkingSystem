package DataStructures;

public class BinarySearchTree extends Tree {

    protected Node searchForFreeSpot() {
        
    }


    protected Node removeSpot(Node node) {
        
    }


    protected void insertSpot(Node node) {
        if (Root == null) {
            Root = node;
        } else {
            Node current = Root;
            Node parent = null;
            while (true) {
                parent = current;
                if (node.spotID < current.spotID) {
                    current = current.left;
                    if (current == null) {
                        parent.left = node;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = node;
                        return;
                    }
                }
            }
        }
        
    }
    
}
