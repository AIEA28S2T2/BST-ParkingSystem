package DataStructures;

/*
In the constructor, get int n as input and create the new tree upon initialization

Create the following methods here:
 1. add a car to the parking lot:
    find a free spot(spotID)
    add the node into the queue
    get input for regNo and make changes to the node
    
2. remove a car from the parking lot:
    get input for regNo
    search for the node with the regNo
    remove the node from the queue
    add the node back to the tree
    
 */

public class ParkingSystem {
    private BinarySearchTree tree = new BinarySearchTree();
    private DefinitelyNotAQueue queue = new DefinitelyNotAQueue();
    
    public ParkingSystem(int n){
        tree.createTree(n);
    }

    public void addCar(String RegNo){
        int spotID = tree.searchForFreeSpot();
        Node node = tree.removeSpot(tree.Root, spotID);
        queue.enqueue(node);
        node.setRegNo(RegNo);
    }

    public void removeCar(String RegNo){
        Node node = queue.findAndPop(RegNo);
        node.setRegNo(null);
        tree.insertSpot(node);
    }
    
    public void showTreeAndQueue(){
        tree.inOrder(tree.Root);
        queue.showQueue();
    }
}
