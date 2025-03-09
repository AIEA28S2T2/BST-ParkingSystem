Node deleteNode(Node root, int key) {
    if (root == null) {
        return root; // Or handle as needed, returning root is common
    }

    if (key < root.key) {
        root.left = deleteNode(root.left, key);
    } else if (key > root.key) {
        root.right = deleteNode(root.right, key);
    } else {
        // Node with only one child or no child
        if (root.left == null) {
            return root.right;
        } else if (root.right == null) {
            return root.left;
        }

        // Node with two children: Get the inorder successor (smallest
        // in the right subtree)
        Node temp = minValueNode(root.right);

        // Copy the inorder successor's content to this node
        root.key = temp.key;

        // Delete the inorder successor
        root.right = deleteNode(root.right, temp.key);
    }

    return root;
}

Node minValueNode(Node node) {
    Node current = node;

    // loop down to find the leftmost leaf
    while (current.left != null) {
        current = current.left;
    }

    return current;
} 