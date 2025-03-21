package DataStructures;

public interface Tree {
    // Interface methods for tree operations
    Node searchForFreeSpot(); // returns spotID of the first free spot
    Node removeSpot(Node node);
    void insertSpot(Node node);
    void clearTree();
    void printTree();
}
