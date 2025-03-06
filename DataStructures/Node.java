package DataStructures;

public final class Node {
    final int spotID;
    Node parent, left, right;
    String regNo;

    Node(int key)
    {
        spotID = key;
        parent = left = right = null;
        regNo = null;
 
    }
}