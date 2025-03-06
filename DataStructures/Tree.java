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

    protected void createTree(int numberOfNodes){
        for (int i = 1; i <= numberOfNodes; i++) {
            Node node = new Node(i);
            insertSpot(node);
        }
    }
    protected abstract int searchForFreeSpot(); //returns spotID of the first free spot
    protected abstract Node removeSpot(Node root, int x);
    protected abstract void insertSpot(Node node);

}
