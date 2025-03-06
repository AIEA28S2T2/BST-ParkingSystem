package DataStructures;

public final class Node {
    int spotID;
    Node parent, left, right;
    String regNo;

    Node(int key)
    {
        spotID = key;
        parent = left = right = null;
        regNo = null;
 
    }
    protected void setRegNo(String regNo){
        this.regNo = regNo;
    }
}