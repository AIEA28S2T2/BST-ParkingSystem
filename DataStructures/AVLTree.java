package DataStructures;

public class AVLTree implements Tree {
    protected Node Root = null;
    
    // AVL Tree specific height methods
    private int height(Node node) {
        if (node == null) return -1;
        
        int leftHeight = (node.left == null) ? -1 : height(node.left);
        int rightHeight = (node.right == null) ? -1 : height(node.right);
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    private int getBalance(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }
    
    // Rotation methods for balancing
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        
        // Perform rotation
        x.right = y;
        y.left = T2;
        
        // Update parent pointers
        x.parent = y.parent;
        y.parent = x;
        if (T2 != null) {
            T2.parent = y;
        }
        
        // If y was the root, update the root
        if (Root == y) {
            Root = x;
        } else if (x.parent != null) {
            // Update the parent's child pointer
            if (x.parent.left == y) {
                x.parent.left = x;
            } else {
                x.parent.right = x;
            }
        }
        
        return x;
    }
    
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        
        // Perform rotation
        y.left = x;
        x.right = T2;
        
        // Update parent pointers
        y.parent = x.parent;
        x.parent = y;
        if (T2 != null) {
            T2.parent = x;
        }
        
        // If x was the root, update the root
        if (Root == x) {
            Root = y;
        } else if (y.parent != null) {
            // Update the parent's child pointer
            if (y.parent.left == x) {
                y.parent.left = y;
            } else {
                y.parent.right = y;
            }
        }
        
        return y;
    }
    
    public void createTree(int numberOfNodes) {
        int[] spots = new int[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            spots[i] = i + 1;
        }
        createBalancedAVL(spots, 0, numberOfNodes - 1);
    }
    
    private void createBalancedAVL(int[] spots, int start, int end) {
        Root = createBalancedAVLRecursive(spots, start, end);
    }
    
    private Node createBalancedAVLRecursive(int[] spots, int start, int end) {
        if (start > end) {
            return null;
        }
        
        int mid = (start + end) / 2;
        Node node = new Node(spots[mid]);
        
        Node leftSubtree = createBalancedAVLRecursive(spots, start, mid - 1);
        Node rightSubtree = createBalancedAVLRecursive(spots, mid + 1, end);
        
        node.left = leftSubtree;
        if (leftSubtree != null) {
            leftSubtree.parent = node;
        }
        
        node.right = rightSubtree;
        if (rightSubtree != null) {
            rightSubtree.parent = node;
        }
        
        return node;
    }
    
    // Tree interface methods
    @Override
    public Node searchForFreeSpot() {
        Node current = Root;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }
    
    @Override
    public Node removeSpot(Node node) {
        if (node == null) return null;
        
        Node returnNode = new Node(node.spotID);
        returnNode.setRegNo(node.regNo);
        
        // Perform standard BST delete
        // Case 1: Node is a leaf
        if (node.left == null && node.right == null) {
            if (node == Root) {
                Root = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                // Start rebalancing from the parent
                rebalanceAfterRemoval(node.parent);
            }
            node.parent = null;
            
        // Case 2: Node has only one child
        } else if (node.left == null) {
            if (node == Root) {
                Root = node.right;
                Root.parent = null;
            } else {
                node.right.parent = node.parent;
                if (node.parent.left == node) {
                    node.parent.left = node.right;
                } else {
                    node.parent.right = node.right;
                }
                // Start rebalancing from the parent
                rebalanceAfterRemoval(node.parent);
            }
            node.right = null;
            node.parent = null;
            
        } else if (node.right == null) {
            if (node == Root) {
                Root = node.left;
                Root.parent = null;
            } else {
                node.left.parent = node.parent;
                if (node.parent.left == node) {
                    node.parent.left = node.left;
                } else {
                    node.parent.right = node.left;
                }
                // Start rebalancing from the parent
                rebalanceAfterRemoval(node.parent);
            }
            node.left = null;
            node.parent = null;
            
        // Case 3: Node has two children
        } else {
            // Find the inorder successor (smallest in right subtree)
            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            
            // Create a copy of the successor's data
            Node successorCopy = new Node(successor.spotID);
            successorCopy.setRegNo(successor.regNo);
            
            // Remove the successor from the tree
            removeSpot(successor);
            
            // Replace node's data with successor's data
            node.spotID = successorCopy.spotID;
            node.regNo = successorCopy.regNo;
            
            // We don't need to rebalance here as removeSpot on successor already did that
        }
        
        return returnNode;
    }
    
    private void rebalanceAfterRemoval(Node node) {
        Node current = node;
        
        while (current != null) {
            int balance = getBalance(current);
            
            // Left heavy
            if (balance > 1) {
                // Left-Right case
                if (getBalance(current.left) < 0) {
                    current.left = leftRotate(current.left);
                }
                // Left-Left case
                rightRotate(current);
            }
            // Right heavy
            else if (balance < -1) {
                // Right-Left case
                if (getBalance(current.right) > 0) {
                    current.right = rightRotate(current.right);
                }
                // Right-Right case
                leftRotate(current);
            }
            
            current = current.parent;
        }
    }
    
    @Override
    public void insertSpot(Node node) {
        if (Root == null) {
            Root = node;
            node.parent = null;
            return;
        }
        
        // First do a standard BST insert
        Node current = Root;
        Node parent = null;
        
        while (current != null) {
            parent = current;
            if (node.spotID < current.spotID) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        // Attach the node
        node.parent = parent;
        if (node.spotID < parent.spotID) {
            parent.left = node;
        } else {
            parent.right = node;
        }
        
        // Rebalance the tree
        rebalanceAfterInsertion(node);
    }
    
    private void rebalanceAfterInsertion(Node node) {
        Node current = node.parent;
        
        while (current != null) {
            int balance = getBalance(current);
            
            // Left heavy
            if (balance > 1) {
                // Left-Right case
                if (node.spotID > current.left.spotID) {
                    current.left = leftRotate(current.left);
                }
                // Left-Left case
                rightRotate(current);
            }
            // Right heavy
            else if (balance < -1) {
                // Right-Left case
                if (node.spotID < current.right.spotID) {
                    current.right = rightRotate(current.right);
                }
                // Right-Right case
                leftRotate(current);
            }
            
            current = current.parent;
        }
    }
    
    @Override
    public void clearTree() {
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
    
    @Override
    public void printTree() {
        System.out.println("Printing AVL Tree Structure:");
        if (Root == null) {
            System.out.println("Tree is empty");
            return;
        }
        printTreeRecursive(Root, 0, "Root: ");
    }
    
    private void printTreeRecursive(Node node, int level, String prefix) {
        if (node == null) return;
        String indent = "  ".repeat(level);
        System.out.println(indent + prefix + "Spot ID: " + node.spotID + " (Height: " + height(node) + ", Balance: " + getBalance(node) + ")");
        
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
