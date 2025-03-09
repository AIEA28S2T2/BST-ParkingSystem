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

    protected void inOrder(Node node){
        if (node != null){
            if(node.left != null){inOrder(node.left);}
            System.out.println(node.spotID);
            System.out.println(node.regNo);
            System.out.println();
            if(node.right != null){inOrder(node.right);}
        }
    }
    
    protected abstract Node searchForFreeSpot(); //returns spotID of the first free spot
    protected abstract Node removeSpot(Node node);
    protected abstract void insertSpot(Node node);

}
