package DataStructures;

public class BinarySearchTree extends Tree {

    protected int searchForFreeSpot() {
        Node current = Root;
        while (current.left != null){
            current = current.left;
        }
        return current.spotID;
    }

    protected Node removeSpot(Node root, int x) {
        if (root == null){
            return root;
        }

        if (root.spotID > x){
            root.left = removeSpot(root.left, x);
        } else if (root.spotID < x){
            root.right = removeSpot(root.right, x);
        } else {
            if (root.left == null){
                return root.right;
            }
            if (root.right == null){
                return root.left;
            }
            Node succ = getSuccessor(root);
            root.spotID = succ.spotID;
            root.right = removeSpot(root.right, succ.spotID);
        }
        return root;
    }
    static Node getSuccessor(Node curr) {
        curr = curr.right;
        while (curr != null && curr.left != null) {
            curr = curr.left;
        }
        return curr;
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
