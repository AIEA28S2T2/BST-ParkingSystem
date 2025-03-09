package DataStructures;

public class BinarySearchTree extends Tree {

    protected Node searchForFreeSpot() {
        Node current = Root;
        while (current.left != null){
            current = current.left;
        }
        return current;
    }

    protected void createTree(int numberOfNodes){
        int[] spots = new int[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            spots[i] = i + 1;
        }
        createBalancedBST(spots, 0, numberOfNodes - 1);
    }
    
    private void createBalancedBST(int[] spots, int start, int end) {
        if (start > end) {
            return;
        }
        int mid = (start + end) / 2;
        Node node = new Node(spots[mid]);
        if (Root == null) {
            Root = node;
        } else {
            insertSpot(node);
        }
        createBalancedBST(spots, start, mid - 1);
        createBalancedBST(spots, mid + 1, end);
    }

    protected Node removeSpot(Node node) {
        //1. if both children are empty
        if (node.left == null && node.right == null) {
            node.parent = null;
            return node;

        //2. if left child is empty
        } else if (node.left == null) {
            if (node.parent.left == node){
                node.right.parent = node.parent;
                node.parent.left = node.right;
            }else{
                node.right.parent = node.parent;
                node.parent.right = node.right;
            }
            node.right = null;
            node.parent = null;
            return node;
        //3. if right child is empty
        } else if (node.right == null){
            if (node.parent.left == node){
                node.left.parent = node.parent;
                node.parent.left = node.left;
            }else{
                node.left.parent = node.parent;
                node.parent.right = node.left;
            }
            node.left = null;
            node.parent = null;
            return node;
        } else { //both children are present
            Node theChosenOne = node.right;
            while (theChosenOne.left != null){
                theChosenOne = theChosenOne.left;
            } //now, theChosenOne is the inorder successor of the node to be deleted
            Node temp = removeSpot(theChosenOne); //removes the inorder successor from its original position AND connects its child to the inorder successor's parent
            if (node.parent.left == node){
                node.parent.left = temp;
            }else{
                node.parent.right = temp;
            } //detaches the node to be deleted, and connects the inorder successor to its spot
            temp.left = node.left;
            node.left = null;

            temp.right = node.right;
            node.right = null;

            temp.parent = node.parent;
            node.parent = null;

            return node;
        }
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
