class Node {
    int key;
    Node left, right;

    public Node(int item)
    {
        key = item;
        left = right = null;
    }
}
public class LowEffortBST {

    static Node insert(Node root, int key)
    {

        // If the tree is empty, return a new node
        if (root == null)
            return new Node(key);

        // If the key is already present in the tree,
        // return the node
        if (root.key == key)
            return root;

        // Otherwise, recur down the tree
        if (key < root.key)
            root.left = insert(root.left, key);
        else
            root.right = insert(root.right, key);

        // Return the (unchanged) node pointer
        return root;
    }

    static Node search(Node root, int key)
    {
        // Base Cases: root is null or key is present at
        // root
        if (root == null || root.key == key)
            return root;

        // Key is greater than root's key
        if (root.key < key)
            return search(root.right, key);

        // Key is smaller than root's key
        return search(root.left, key);
    }

    static Node delNode(Node root, int x) {
      
        // Base case
        if (root == null) {
            return root;
        }

        // If key to be searched is in a subtree
        if (root.key > x) {
            root.left = delNode(root.left, x);
        } else if (root.key < x) {
            root.right = delNode(root.right, x);
        } else {
            // If root matches with the given key

            // Cases when root has 0 children or
            // only right child
            if (root.left == null) {
                return root.right;
            }

            // When root has only left child
            if (root.right == null) {
                return root.left;
            }

            // When both children are present
            Node succ = getSuccessor(root);
            root.key = succ.key;
            root.right = delNode(root.right, succ.key);
        }
        return root;
    }
 
    // Note that it is not a generic inorder successor 
    // function. It mainly works when the right child
    // is not empty, which is the case we need in BST
    // delete. 
    static Node getSuccessor(Node curr) {
        curr = curr.right;
        while (curr != null && curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }  

        // Inorder Traversal
        public static void printInorder(Node node)
        {
            if (node == null)
                return;
    
            // Traverse left subtree
            printInorder(node.left);
    
            // Visit node
            System.out.print(node.data + " ");
    
            // Traverse right subtree
            printInorder(node.right);
        }
        
    
}
