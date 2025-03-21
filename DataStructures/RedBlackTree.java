package DataStructures;

public class RedBlackTree implements Tree {
    protected Node Root = null;
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    
    // Node extension for Red-Black Tree
    private boolean getColor(Node node) {
        // Null nodes are considered BLACK
        if (node == null) return BLACK;
        
        // We'll use the spotID to store color information
        // If spotID is negative, the node is RED
        return node.spotID < 0;
    }
    
    private void setColor(Node node, boolean color) {
        if (node == null) return;
        
        if (color == RED && node.spotID > 0) {
            // Store the negative value to represent RED
            node.spotID = -node.spotID;
        } else if (color == BLACK && node.spotID < 0) {
            // Restore the positive value to represent BLACK
            node.spotID = -node.spotID;
        }
    }
    
    // This method gets the actual spotID regardless of color
    private int getActualSpotId(Node node) {
        if (node == null) return 0;
        return Math.abs(node.spotID);
    }
    
    // Rotation methods
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
        createBalancedRBT(spots, 0, numberOfNodes - 1);
    }
    
    private void createBalancedRBT(int[] spots, int start, int end) {
        Root = createBalancedRBTRecursive(spots, start, end);
        if (Root != null) {
            setColor(Root, BLACK); // Root is always BLACK
        }
    }
    
    private Node createBalancedRBTRecursive(int[] spots, int start, int end) {
        if (start > end) {
            return null;
        }
        
        int mid = (start + end) / 2;
        Node node = new Node(spots[mid]);
        setColor(node, BLACK); // Start with all nodes black
        
        Node leftSubtree = createBalancedRBTRecursive(spots, start, mid - 1);
        Node rightSubtree = createBalancedRBTRecursive(spots, mid + 1, end);
        
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
        
        // Create a copy of the node with positive spotID
        if (current != null) {
            Node result = new Node(getActualSpotId(current));
            result.setRegNo(current.regNo);
            return result;
        }
        
        return null;
    }
    
    @Override
    public Node removeSpot(Node node) {
        if (node == null) return null;
        
        // Find the actual node in the tree with the same spotID
        Node actualNode = findNodeBySpotId(getActualSpotId(node));
        if (actualNode == null) return null;
        
        // Create a copy to return
        Node returnNode = new Node(getActualSpotId(actualNode));
        returnNode.setRegNo(actualNode.regNo);
        
        // If node to be deleted has two children
        if (actualNode.left != null && actualNode.right != null) {
            // Find successor (smallest node in right subtree)
            Node successor = actualNode.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            
            // Copy successor data to the node
            actualNode.spotID = successor.spotID; // This preserves color
            actualNode.regNo = successor.regNo;
            
            // Now delete the successor node instead
            actualNode = successor;
        }
        
        // At this point, actualNode has at most one child
        Node replacement = (actualNode.left != null) ? actualNode.left : actualNode.right;
        boolean removingBlackNode = !getColor(actualNode);
        
        if (replacement != null) {
            // Connect replacement node with parent
            replacement.parent = actualNode.parent;
            
            if (actualNode.parent == null) {
                Root = replacement;
            } else if (actualNode == actualNode.parent.left) {
                actualNode.parent.left = replacement;
            } else {
                actualNode.parent.right = replacement;
            }
            
            // Clear links from node being removed
            actualNode.left = actualNode.right = actualNode.parent = null;
            
            // Fix red-black tree properties if needed
            if (removingBlackNode) {
                fixRedBlackAfterRemoval(replacement);
            }
            
            // Root must be black
            setColor(Root, BLACK);
        } else if (actualNode.parent == null) {
            // This was the last node in the tree
            Root = null;
        } else {
            // No replacement node
            if (removingBlackNode) {
                fixRedBlackAfterRemoval(actualNode);
            }
            
            // Remove actualNode from parent
            if (actualNode.parent != null) {
                if (actualNode == actualNode.parent.left) {
                    actualNode.parent.left = null;
                } else if (actualNode == actualNode.parent.right) {
                    actualNode.parent.right = null;
                }
                actualNode.parent = null;
            }
        }
        
        return returnNode;
    }
    
    private Node findNodeBySpotId(int spotId) {
        Node current = Root;
        while (current != null) {
            int currentSpotId = getActualSpotId(current);
            if (spotId == currentSpotId) {
                return current;
            } else if (spotId < currentSpotId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }
    
    private void fixRedBlackAfterRemoval(Node node) {
        Node current = node;
        
        while (current != Root && !getColor(current)) {
            if (current == current.parent.left) {
                Node sibling = current.parent.right;
                
                // Case 1: Sibling is red
                if (getColor(sibling)) {
                    setColor(sibling, BLACK);
                    setColor(current.parent, RED);
                    leftRotate(current.parent);
                    sibling = current.parent.right;
                }
                
                // Case 2: Both of sibling's children are black
                if (!getColor(sibling.left) && !getColor(sibling.right)) {
                    setColor(sibling, RED);
                    current = current.parent;
                } else {
                    // Case 3: Sibling's right child is black
                    if (!getColor(sibling.right)) {
                        setColor(sibling.left, BLACK);
                        setColor(sibling, RED);
                        rightRotate(sibling);
                        sibling = current.parent.right;
                    }
                    
                    // Case 4: Sibling's right child is red
                    setColor(sibling, getColor(current.parent));
                    setColor(current.parent, BLACK);
                    setColor(sibling.right, BLACK);
                    leftRotate(current.parent);
                    current = Root; // Exit the loop
                }
            } else {
                // Mirror cases for when current is a right child
                Node sibling = current.parent.left;
                
                // Case 1: Sibling is red
                if (getColor(sibling)) {
                    setColor(sibling, BLACK);
                    setColor(current.parent, RED);
                    rightRotate(current.parent);
                    sibling = current.parent.left;
                }
                
                // Case 2: Both of sibling's children are black
                if (!getColor(sibling.right) && !getColor(sibling.left)) {
                    setColor(sibling, RED);
                    current = current.parent;
                } else {
                    // Case 3: Sibling's left child is black
                    if (!getColor(sibling.left)) {
                        setColor(sibling.right, BLACK);
                        setColor(sibling, RED);
                        leftRotate(sibling);
                        sibling = current.parent.left;
                    }
                    
                    // Case 4: Sibling's left child is red
                    setColor(sibling, getColor(current.parent));
                    setColor(current.parent, BLACK);
                    setColor(sibling.left, BLACK);
                    rightRotate(current.parent);
                    current = Root; // Exit the loop
                }
            }
        }
        
        setColor(current, BLACK);
    }
    
    @Override
    public void insertSpot(Node node) {
        // Create a new node with positive spotID (BLACK by default)
        Node newNode = new Node(Math.abs(node.spotID));
        newNode.setRegNo(node.regNo);
        
        // Perform standard BST insert
        Node y = null;
        Node x = Root;
        
        while (x != null) {
            y = x;
            if (getActualSpotId(newNode) < getActualSpotId(x)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        
        newNode.parent = y;
        
        if (y == null) {
            Root = newNode; // Tree was empty
        } else if (getActualSpotId(newNode) < getActualSpotId(y)) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }
        
        // New node is red
        setColor(newNode, RED);
        
        // Fix any violations
        fixRedBlackAfterInsertion(newNode);
    }
    
    private void fixRedBlackAfterInsertion(Node node) {
        Node current = node;
        
        // While we have a red node with a red parent
        while (current != Root && getColor(current.parent)) {
            if (current.parent == current.parent.parent.left) {
                Node uncle = current.parent.parent.right;
                
                // Case 1: Uncle is red
                if (getColor(uncle)) {
                    setColor(current.parent, BLACK);
                    setColor(uncle, BLACK);
                    setColor(current.parent.parent, RED);
                    current = current.parent.parent;
                } else {
                    // Case 2: Uncle is black, current is a right child
                    if (current == current.parent.right) {
                        current = current.parent;
                        leftRotate(current);
                    }
                    
                    // Case 3: Uncle is black, current is a left child
                    setColor(current.parent, BLACK);
                    setColor(current.parent.parent, RED);
                    rightRotate(current.parent.parent);
                }
            } else {
                // Mirror cases for when parent is a right child
                Node uncle = current.parent.parent.left;
                
                // Case 1: Uncle is red
                if (getColor(uncle)) {
                    setColor(current.parent, BLACK);
                    setColor(uncle, BLACK);
                    setColor(current.parent.parent, RED);
                    current = current.parent.parent;
                } else {
                    // Case 2: Uncle is black, current is a left child
                    if (current == current.parent.left) {
                        current = current.parent;
                        rightRotate(current);
                    }
                    
                    // Case 3: Uncle is black, current is a right child
                    setColor(current.parent, BLACK);
                    setColor(current.parent.parent, RED);
                    leftRotate(current.parent.parent);
                }
            }
        }
        
        // Root must be black
        setColor(Root, BLACK);
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
        System.out.println("Printing Red-Black Tree Structure:");
        if (Root == null) {
            System.out.println("Tree is empty");
            return;
        }
        printTreeRecursive(Root, 0, "Root: ");
    }
    
    private void printTreeRecursive(Node node, int level, String prefix) {
        if (node == null) return;
        String indent = "  ".repeat(level);
        String color = getColor(node) ? "RED" : "BLACK";
        
        System.out.println(indent + prefix + "Spot ID: " + getActualSpotId(node) + " (" + color + ")");
        
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
