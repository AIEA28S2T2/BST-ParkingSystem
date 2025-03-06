import DataStructures.*;

/*
First
Get the input of how many parking spaces are available in the place, 
and create a new binary tree by calling the createTree method from the BinarySearchTree class.

Then the upcoming things should be in a loop:
    Ask if the user wants to park a car or remove a car from the parking lot.
        If the user wants to park:
            Find a free spot by calling the searchForFreeSpot method from the BinarySearchTree class.
            (If there is no free spot, print "No free spots available".)
            then pipe it into the enqueue function from DefinitelyNotAQueue class.
            And add the regNo to the node.

        If the user wants to remove a car:
            Ask for the registration number of the car.
            Find the node with the registration number by calling the findAndPop method from the DefinitelyNotAQueue class.
            If the node is found, remove the node from the DefinitelyNotAQueue by calling the findAndPop method then pipe it into insertSpot.
            If the node is not found, print "Car not found".
        
That's all I can think of at the moment; You guys can make necessary changes.

Maybe add an option to type 0 while inside the loop to enter a secret maintenance mode which clears both the tree and the DefinitelyNotAQueue. 
Just calling two functions.
            
*/

public class Main {
    
}
