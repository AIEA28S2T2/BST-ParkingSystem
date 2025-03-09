package DataStructures;

public class Node {
    int spotID;
    Node parent, left, right;
    String regNo;

    Node(int number)
    {
        spotID = number;
        parent = left = right = null;
        regNo = null;
    }
    protected void setRegNo(String regNo){
        this.regNo = regNo;
    }
}