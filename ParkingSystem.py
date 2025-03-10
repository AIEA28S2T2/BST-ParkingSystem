# Node.py
class Node:
    def __init__(self, number):
        self.spot_id = number
        self.parent = None
        self.left = None
        self.right = None
        self.reg_no = None
    
    def set_reg_no(self, reg_no):
        self.reg_no = reg_no


# Tree.py
class Tree:
    def __init__(self):
        self.root = None
    
    def clear_tree(self):
        self._delete_sub_tree(self.root)
        self.root = None
    
    def _delete_sub_tree(self, node):
        if node is None:
            return
        self._delete_sub_tree(node.left)
        self._delete_sub_tree(node.right)
        
        node.left = None
        node.right = None
        node.parent = None
    
    def search_for_free_spot(self):
        # Abstract method to be implemented by subclasses
        pass
    
    def remove_spot(self, node):
        # Abstract method to be implemented by subclasses
        pass
    
    def insert_spot(self, node):
        # Abstract method to be implemented by subclasses
        pass
    
    def print_tree(self):
        print("Printing Tree Structure:")
        if self.root is None:
            print("Tree is empty")
            return
        self._print_tree_recursive(self.root, 0, "Root: ")
    
    def _print_tree_recursive(self, node, level, prefix):
        if node is None:
            return
        
        indent = "  " * level
        print(f"{indent}{prefix}Spot ID: {node.spot_id}")
        
        if node.left is not None or node.right is not None:
            if node.left is not None:
                self._print_tree_recursive(node.left, level + 1, "L-> ")
            else:
                print(f"{indent}  L-> null")
            
            if node.right is not None:
                self._print_tree_recursive(node.right, level + 1, "R-> ")
            else:
                print(f"{indent}  R-> null")


# BinarySearchTree.py
class BinarySearchTree(Tree):
    def search_for_free_spot(self):
        current = self.root
        while current.left is not None:
            current = current.left
        return current
    
    def create_tree(self, number_of_nodes):
        spots = [i + 1 for i in range(number_of_nodes)]
        self._create_balanced_bst(spots, 0, number_of_nodes - 1)
    
    def _create_balanced_bst(self, spots, start, end):
        self.root = self._create_balanced_bst_recursive(spots, start, end)
    
    def _create_balanced_bst_recursive(self, spots, start, end):
        if start > end:
            return None
        
        mid = (start + end) // 2
        node = Node(spots[mid])
        
        left_subtree = self._create_balanced_bst_recursive(spots, start, mid - 1)
        right_subtree = self._create_balanced_bst_recursive(spots, mid + 1, end)
        
        node.left = left_subtree
        if left_subtree is not None:
            left_subtree.parent = node
        
        node.right = right_subtree
        if right_subtree is not None:
            right_subtree.parent = node
        
        return node
    
    def remove_spot(self, node):
        # 1. if both children are empty
        if node.left is None and node.right is None:
            if node == self.root:
                self.root = None
            else:
                if node.parent.left == node:
                    node.parent.left = None
                else:
                    node.parent.right = None
            node.parent = None
            return node
        
        # 2. if left child is empty
        elif node.left is None:
            if node == self.root:
                self.root = node.right
                self.root.parent = None
            else:
                if node.parent.left == node:
                    node.right.parent = node.parent
                    node.parent.left = node.right
                else:
                    node.right.parent = node.parent
                    node.parent.right = node.right
            node.right = None
            node.parent = None
            return node
        
        # 3. if right child is empty
        elif node.right is None:
            if node == self.root:
                self.root = node.left
                self.root.parent = None
            else:
                if node.parent.left == node:
                    node.left.parent = node.parent
                    node.parent.left = node.left
                else:
                    node.left.parent = node.parent
                    node.parent.right = node.left
            node.left = None
            node.parent = None
            return node
        
        # 4. both children are present
        else:
            the_chosen_one = node.right
            while the_chosen_one.left is not None:
                the_chosen_one = the_chosen_one.left
            
            # Remove the inorder successor from its original position
            temp = self.remove_spot(the_chosen_one)
            
            if node == self.root:
                self.root = temp
                temp.parent = None
            else:
                if node.parent.left == node:
                    node.parent.left = temp
                else:
                    node.parent.right = temp
                temp.parent = node.parent
            
            temp.left = node.left
            if node.left is not None:
                node.left.parent = temp
            node.left = None
            
            temp.right = node.right
            if node.right is not None:
                node.right.parent = temp
            node.right = None
            
            node.parent = None
            return node
    
    def insert_spot(self, node):
        if self.root is None:
            self.root = node
            node.parent = None
        else:
            current = self.root
            parent = None
            while True:
                parent = current
                if node.spot_id < current.spot_id:
                    current = current.left
                    if current is None:
                        parent.left = node
                        node.parent = parent
                        return
                else:
                    current = current.right
                    if current is None:
                        parent.right = node
                        node.parent = parent
                        return


# CustomLinkedList.py
class CustomLinkedList:
    def __init__(self):
        self.head = None
        self.tail = None
    
    def enqueue(self, node):
        if self.head is None:
            self.head = node
            self.tail = node
        else:
            self.tail.right = node
            self.tail = node
    
    def find_and_pop(self, reg_no):
        current = self.head
        previous = None
        while current is not None:
            if current.reg_no == reg_no:
                if previous is None:
                    if current.right is not None:
                        self.head = current.right
                    else:
                        self.head = None
                        self.tail = None
                else:
                    previous.right = current.right
                    if current == self.tail:
                        self.tail = previous
                return current
            previous = current
            current = current.right
        return None
    
    def clear_queue(self):
        if self.head is None:
            return  # Queue is already empty
        
        current = self.head
        while current is not None:
            next_node = current.right
            current.right = None
            current = next_node
        
        self.head = None
        self.tail = None
    
    def print_queue(self):
        print("Printing Queue Contents:")
        if self.head is None:
            print("The queue is empty")
            return
        
        position = 1
        current = self.head
        while current is not None:
            print(f"Position {position}: Spot ID: {current.spot_id}, Registration: {current.reg_no if current.reg_no is not None else 'None'}")
            current = current.right
            position += 1
        print("End of queue")


# ParkingSystem.py
class ParkingSystem:
    def __init__(self, n):
        self.tree = BinarySearchTree()
        self.queue = CustomLinkedList()
        self.tree.create_tree(n)
    
    def add_car(self, reg_no):
        node = self.tree.remove_spot(self.tree.search_for_free_spot())
        self.queue.enqueue(node)
        node.set_reg_no(reg_no)
    
    def remove_car(self, reg_no):
        node = self.queue.find_and_pop(reg_no)
        if node:
            node.set_reg_no(None)
            self.tree.insert_spot(node)
        else:
            print(f"Car with registration {reg_no} not found")
    
    def print_tree(self):
        self.tree.print_tree()
    
    def print_queue(self):
        self.queue.print_queue()


# Main.py
def main():
    n = int(input("Enter the number of parking spots: "))
    parking_system = ParkingSystem(n)
    parking_system.print_tree()
    
    while True:
        print("Enter 1 to add a car, 2 to remove a car, or 0 to exit:")
        choice = int(input())
        
        if choice == 0:
            break
        
        reg_no = input("Enter the registration number: ")
        
        if choice == 1:
            print("Before:")
            parking_system.print_tree()
            parking_system.print_queue()
            parking_system.add_car(reg_no)
            print("After:")
            parking_system.print_tree()
            parking_system.print_queue()
            print("Car added successfully.")
        elif choice == 2:
            print("Before:")
            parking_system.print_tree()
            parking_system.print_queue()
            parking_system.remove_car(reg_no)
            print("After:")
            parking_system.print_tree()
            parking_system.print_queue()
            print("Car removed successfully.")
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()