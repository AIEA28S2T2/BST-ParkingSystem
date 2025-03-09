package DataStructures;

public abstract class Tree {
    protected Node Root = null;
    
    // Clears the tree by recursively calling deleteSubTree on the root node 
    public void clearTree(){
        deleteSubTree(Root);
        Root = null;
    } 
    private void deleteSubTree(Node node) {
        if (node == null) {
            return;
        }
        deleteSubTree(node.left);
        deleteSubTree(node.right);
        
        node.left = null;
        node.right = null;
        node.parent = null;
    } 
    
    protected abstract Node searchForFreeSpot(); //returns spotID of the first free spot
    protected abstract Node removeSpot(Node node);
    protected abstract void insertSpot(Node node);

public void printTree() {
    System.out.println("Printing Tree Structure:");
    if (Root == null) {
        System.out.println("Tree is empty");
        return;
    }
    printTreeRecursive(Root, 0, "Root: ");
}

private void printTreeRecursive(Node node, int level, String prefix) {
    if (node == null) return;
    
    String indent = "  ".repeat(level);
    System.out.println(indent + prefix + "Spot ID: " + node.spotID + 
                      (node.regNo != null ? ", RegNo: " + node.regNo : ", Available"));
    
    if (node.left != null || node.right != null) {
        if (node.left != null) {
            printTreeRecursive(node.left, level + 1, "L-> ");
        } else {
            System.out.println(indent + "  " + "L-> null");
        }
        
        if (node.right != null) {
            printTreeRecursive(node.right, level + 1, "R-> ");
        } else {
            System.out.println(indent + "  " + "R-> null");
        }
    }
}

}
