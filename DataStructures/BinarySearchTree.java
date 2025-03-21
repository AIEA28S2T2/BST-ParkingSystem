package DataStructures;

public class BinarySearchTree implements Tree {
    protected Node Root = null;

    @Override
    public Node searchForFreeSpot() {
        Node current = Root;
        while (current.left != null){
            current = current.left;
        }
        return current;
    }

    public void createTree(int numberOfNodes){
        int[] spots = new int[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            spots[i] = i + 1;
        }
        createBalancedBST(spots, 0, numberOfNodes - 1);
    }
    
    private void createBalancedBST(int[] spots, int start, int end) {
        Root = createBalancedBSTRecursive(spots, start, end);
    }

    private Node createBalancedBSTRecursive(int[] spots, int start, int end) {
        if (start > end) {
            return null;
        }
        
        int mid = (start + end) / 2;
        Node node = new Node(spots[mid]);
        
        Node leftSubtree = createBalancedBSTRecursive(spots, start, mid - 1);
        Node rightSubtree = createBalancedBSTRecursive(spots, mid + 1, end);
        
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
    
    @Override
    public Node removeSpot(Node node) {
        //1. if both children are empty
        if (node.left == null && node.right == null) {
            if (node == Root) {
                Root = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
            }
            node.parent = null;
            return node;
    
        //2. if left child is empty
        } else if (node.left == null) {
            if (node == Root) {
                Root = node.right;
                Root.parent = null;
            } else {
                if (node.parent.left == node) {
                    node.right.parent = node.parent;
                    node.parent.left = node.right;
                } else {
                    node.right.parent = node.parent;
                    node.parent.right = node.right;
                }
            }
            node.right = null;
            node.parent = null;
            return node;
        //3. if right child is empty
        } else if (node.right == null) {
            if (node == Root) {
                Root = node.left;
                Root.parent = null;
            } else {
                if (node.parent.left == node) {
                    node.left.parent = node.parent;
                    node.parent.left = node.left;
                } else {
                    node.left.parent = node.parent;
                    node.parent.right = node.left;
                }
            }
            node.left = null;
            node.parent = null;
            return node;
        } else { //both children are present
            Node theChosenOne = node.right;
            while (theChosenOne.left != null) {
                theChosenOne = theChosenOne.left;
            } //now, theChosenOne is the inorder successor of the node to be deleted
            Node temp = removeSpot(theChosenOne); //removes the inorder successor from its original position AND connects its child to the inorder successor's parent
            
            if (node == Root) {
                Root = temp;
                temp.parent = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = temp;
                } else {
                    node.parent.right = temp;
                }
                temp.parent = node.parent;
            }
            
            temp.left = node.left;
            if (node.left != null) {
                node.left.parent = temp;
            }
            node.left = null;
    
            temp.right = node.right;
            if (node.right != null) {
                node.right.parent = temp;
            }
            node.right = null;
    
            node.parent = null;
            return node;
        }
    }            

    @Override
    public void insertSpot(Node node) {
        if (Root == null) {
            Root = node;
            node.parent = null;
        } else {
            Node current = Root;
            Node parent = null;
            while (true) {
                parent = current;
                if (node.spotID < current.spotID) {
                    current = current.left;
                    if (current == null) {
                        parent.left = node;
                        node.parent = parent;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = node;
                        node.parent = parent;
                        return;
                    }
                }
            }
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
        System.out.println("Printing Tree Structure:");
        if (Root == null) {
            System.out.println("Tree is empty");
            return;
        }
        printTreeRecursive(Root, 0, "Root: ");
    }

    private void printTreeRecursive(Node node, int level, String prefix) {
        if (node == null) return;
        String indent = "  ".repeat(level);
        System.out.println(indent + prefix + "Spot ID: " + node.spotID);
        
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
