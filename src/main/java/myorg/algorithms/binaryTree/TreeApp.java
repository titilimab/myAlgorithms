package myorg.algorithms.binaryTree;

//demonstrates binary tree
import java.io.*;
import java.util.*; // for Stack class
////////////////////////////////////////////////////////////////
class Node
{
	public int iData; // data item (key)
	public double dData; // data item (value)
	public Node leftChild; // this node"s left child
	public Node rightChild; // this node"s right child
	public void displayNode() // display ourself
	{
		System.out.print("{");
		System.out.print(iData);
		System.out.print(", ");
		System.out.print(dData);
		System.out.print("} ");
	}
} // end class Node
////////////////////////////////////////////////////////////////

/*********Class Tree contains Total of 10 methods including Constructor method to perform below operations***********
 1.	public Tree() - Constructor 
 2. public Node find(int key) - Find Node containing the specific key
 3. public void insert(int id, double dd) - Insert a Node into the Tree with specific key(id) and value(dd)
 4. public boolean delete(int key) - Delete Node with the given specific key
 5. private Node getSuccessor(Node delNode) - To get the successor of a given Node after deletion of a specific node
 6. public void traverse(int traverseType) - Traverse through the Tree based on type of traversal(inorder, preorder, postorder)
 7. private void preOrder(Node localRoot)
 8. private void inOrder(Node localRoot)
 9. private void postOrder(Node localRoot)
 10.public void displayTree()
 
 ***************************/
class Tree
{
	private Node root; // first node of tree
	// -------------------------------------------------------------
	public Tree() // constructor
	{ root = null; } // no nodes in tree yet

	//-------------------------------------------------------------
	public Node find(int key) // find node with given key
	{ // (assumes non-empty tree)
		Node current = root; // start at root
		while(current.iData != key) // while no match,
		{
			if(key < current.iData) // go left?
				current = current.leftChild;
			else // or go right?
				current = current.rightChild;
			if(current == null) // if no child,
				return null; // didn"t find it
		}
		return current; // found it
	} // end find()
	//-------------------------------------------------------------
	public void insert(int id, double dd)
	{
		Node newNode = new Node(); // make new node
		newNode.iData = id; // insert data
		newNode.dData = dd;
		if(root==null) // no node in root : This is when we start a new Tree from scratch by making the newNode as the root node
			root = newNode;
		else // root occupied : This is when a Tree already exists and we need to find the position to insert the newNode and when position is found we perform insertion
		{
			Node current = root; // start at root
			Node parent;
			while(true) // (exits internally)
			{
				parent = current;
				if(id < current.iData) // go left?
				{
					current = current.leftChild;
					if(current == null) // if end of the line,
					{ // insert on left
						parent.leftChild = newNode;
						return;
					}
				} // end if go left
				else // or go right?
				{
					current = current.rightChild;
					if(current == null) // if end of the line
					{ // insert on right
						parent.rightChild = newNode;
						return;
					}
				} // end else go right
			} // end while
		} // end else not root
	} // end insert()
	// -------------------------------------------------------------
	
	/* Step 1 : First find the node to be deleted
	 * Step 2 : When you have found the node, consider the below 3 cases and sub-cases of the 3 cases.
	 * 	1. The node to be deleted is a leaf(has no children)
	 *	2. The node to be deleted has one child
	 *			- When the child of the node to be deleted is a left child
	 *			- When the child of the node to be deleted is a right child
	 *	3. The node to be deleted has two children(one left and one right child as obvious)  
	 * */

	public boolean delete(int key) // delete node with given key
	{ // (assumes non-empty list)
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;
		while(current.iData != key) // search for node
		{
			parent = current;
			if(key < current.iData) // go left?
			{
				isLeftChild = true;
				current = current.leftChild;
			}
			else // or go right?
			{
				isLeftChild = false;
				current = current.rightChild;
			}
			if(current == null) // end of the line,
				return false; // didn"t find it
		} // end while
		// found node to delete
		// if no children, simply delete it
		
		/*Case 1 : When node to be deleted has no children
		 * 	1. When the node to be deleted itself is a root node it means the tree contains only 1 node that is root node
		 * 	2. When the node to be deleted itself is the left child of its parent
		 * 	3. When the node to be deleted itself is the right child of its parent
		 * */
		
		if(current.leftChild==null &&
				current.rightChild==null)
		{
			if(current == root) // if root,
				root = null; // tree is empty
			else if(isLeftChild)
				parent.leftChild = null; // disconnect
			else // from parent
				parent.rightChild = null;
		}
		// if no right child, replace with left subtree
		/* Case 2 : When node to be deleted has only one child and the child is a left child
		 * 	1. When the node to be deleted itself is a root node 
		 *	2. When the node to be deleted itself is the left child of its parent 
		 *	3. When the node to be deleted itself is the right child of its parent
		 * */
		else if(current.rightChild==null)
			if(current == root)
				root = current.leftChild;
			else if(isLeftChild)
				parent.leftChild = current.leftChild;
			else
				parent.rightChild = current.leftChild;
		// if no left child, replace with right subtree
		
		/* Case 2 : When node to be deleted has only one child and the child is a right child
		 * 	1. When the node to be deleted itself is a root node 
		 *	2. When the node to be deleted itself is the left child of its parent 
		 *	3. When the node to be deleted itself is the right child of its parent
		 * */
		else if(current.leftChild==null)
			if(current == root)
				root = current.rightChild;
			else if(isLeftChild)
				parent.leftChild = current.rightChild;
			else
				parent.rightChild = current.rightChild;
		
		/* Case 3 : When node to be deleted has two children(left and right child nodes) : Find the inorder successor 
		 * 1. When the node to be deleted itself is a root node
		 * 2. When the node to be deleted itself is the left child of its parent
		 * 3. When the node to be deleted itself is the right child of its parent
		 */
		else // two children, so replace with inorder successor
		{
			// get successor of node to delete (current)
			Node successor = getSuccessor(current);
			// connect parent of current to successor instead
			if(current == root)
				root = successor;
			else if(isLeftChild)
				parent.leftChild = successor;
			else
				parent.rightChild = successor;
			// connect successor to current"s left child
			successor.leftChild = current.leftChild;
		} // end else two children
		// (successor cannot have a left child)
		return true; // success
	} // end delete()
	// -------------------------------------------------------------
	/*To Find inOrder Successor:Inorder Successor of a delNode is that node whose value is the immediate next higher value to the delNode value
	- returns node with next-highest value after delNode
	- go to right child, then right child's left descendents untill you find a node which has no more left child. It may/may not have right child
		There are 2 conditions to above
		1. When the Inorder Successor itself is the right child. It means it does not have left child. It may/may not have right child
		2. When the Inorder successor is a node which is the lowest descendant of the immediate right node of the delNode. It does not have left child. It may/may have right child
	*/
	private Node getSuccessor(Node delNode)
	{
		Node successorParent = delNode;
		Node successor = delNode;
		Node current = delNode.rightChild; // go to right child
		while(current != null) // until no more
		{ // left children,
			successorParent = successor;
			successor = current;
			current = current.leftChild; // go to left child
		}
		// if successor not
		if(successor != delNode.rightChild) // If successor itself is not the direct right child of the delNode
		{ // make connections
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = delNode.rightChild;
		}
		return successor;
	}
	// -------------------------------------------------------------

	public void traverse(int traverseType)
	{
		switch(traverseType)
		{
		case 1: System.out.print("\nPreorder traversal: ");
		preOrder(root);
		break;
		case 2: System.out.print("\nInorder traversal: ");
		inOrder(root);
		break;
		case 3: System.out.print("\nPostorder traversal: ");
		postOrder(root);
		break;
		}
		System.out.println();
	}

	//-------------------------------------------------------------
	private void preOrder(Node localRoot)
	{
		if(localRoot != null)
		{
			System.out.print(localRoot.iData + " ");
			preOrder(localRoot.leftChild);
			preOrder(localRoot.rightChild);
		}
	}
	//-------------------------------------------------------------
	private void inOrder(Node localRoot)
	{
		if(localRoot != null)
		{
			inOrder(localRoot.leftChild);
			System.out.print(localRoot.iData + " ");
			inOrder(localRoot.rightChild);
		}
	}
	//-------------------------------------------------------------
	private void postOrder(Node localRoot)
	{
		if(localRoot != null)
		{
			postOrder(localRoot.leftChild);
			postOrder(localRoot.rightChild);
			System.out.print(localRoot.iData + " ");
		}
	}

	//-------------------------------------------------------------
	
/*
Expected Display of Tree as below :
.................................................
                                50                                                              
                25                              75                              
        12              37              --              87              
    --      --      30      43      --      --      --      93      
  --  --  --  --  --  33  --  --  --  --  --  --  --  --  --  97  
......................................................

Understanding of display method with below display of Tree:

......................................................
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^50**************************************************************
^^^^^^^^^^^^^^^^25******************************75******************************
^^^^^^^^12**************37**************--**************87**************
^^^^--******--******30******43******--******--******80******93******
^^--**--**--**--**--**33**--**--**--**--**--**--**--**--**--**97**
......................................................

Steps :
1. First Visualize the Tree to decide how we need to display the Nodes
2. The entire Tree to be displayed in a block of pair of dotted lines
	Hence, write System.out.println("......................................................"); before printing the tree in the form of while loop.
3. We First need to check if each of the row has nodes right from the first row which has Root node. We need to display nodes in while loop until the row is empty.
	This is implemented by using a flag called boolean isRowEmpty.
	boolean isRowEmpty = false;
	while(isRowEmpty == false) {
		isRowEmpty = true;
		|
		|
		|
		
		if(temp.leftChild != null ||temp.rightChild != null){
						isRowEmpty = false;
		}
		|
		|
	}
	Everytime this while loop gets executed, isRowEmpty is first set to true. 
	Then the pointer moves to the next node to check if left or right child are not null.
	If both are not null, isRowEmpty is set to true
4. The First row looks like below
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^50**************************************************************
In real : 
                                50 	                                                          
Hence before printing first node, there are 32 spaces. So, declare nBlanks as integer and print.
	int nBlanks = 32
	
	Verify the first row is not empty :
	while(isRowEmpty == false){
		
		//Loop 1: To print the space from beginning to the first node
			for(int j=0; j<nBlanks; j++){
				//System.out.print("^");
				System.out.print(" ");
			}
	
	}
For the 2nd Row :
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^50**************************************************************
^^^^^^^^^^^^^^^^25******************************75******************************
In Real :
                                50                                                              
                25                              75                              
                
For the 2nd row- The space from the beginning to the first node 25 gets reduced to half incomparison to the First row first 32 spaces.
Also the spaces between 2 nodes space should be equal to nBlanks*2-2

So, to the end of while(isRowEmpty == false)
	int nBlanks = 32
	
	Verify the first row is not empty :
	while(isRowEmpty == false){
		
		//Loop 1: To print the space from beginning to the first node
			for(int j=0; j<nBlanks; j++){
				//System.out.print("^");
				System.out.print(" ");
			}
			
			//Loop 2: To print the space between 2 nodes
				for(int j=0; j<nBlanks*2-2; j++){
					//System.out.print("*");
					System.out.print(" ");
					}
			
			// Reduce the Number of spaces to its half for the next row first node
			nBlanks /= 2;	
	}

5. globalStack : Starts from root. First root is pushed into the globalStack
	localStack : starts from temp.leftChild and temp.rightChild. temp for the first node refers to root.
	





 * */
	
	
	public void displayTree()
	{
		Stack globalStack = new Stack();
		globalStack.push(root);
		int nBlanks = 32;
		
		//Note :isRowEmpty is a flag that determines if there is any node present in the current row to display
		
		boolean isRowEmpty = false;
		System.out.println("......................................................");
		while(isRowEmpty==false)
		{
			isRowEmpty = true;
			
			//Loop 1: To print the space from beginning to the first node
			for(int j=0; j<nBlanks; j++){
				//System.out.print("^");
				System.out.print(" ");
			}
			
			//While Loop : Continue until the globalStack is empty
			//Note : Understand the concept of globalStack and localStack for push and pop operations of nodes to display the tree
			Stack localStack = new Stack();
			while(globalStack.isEmpty()==false)
			{
				Node temp = (Node)globalStack.pop();
				if(temp != null)
				{
					System.out.print(temp.iData);
					localStack.push(temp.leftChild);
					localStack.push(temp.rightChild);
					if(temp.leftChild != null ||
							temp.rightChild != null)
						isRowEmpty = false;
				}
				else
				{
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				
				//Loop 2: To print the space between 2 nodes
				for(int j=0; j<nBlanks*2-2; j++){
					//System.out.print("*");
					System.out.print(" ");
					}
			} // end while globalStack not empty
			System.out.println();
			// Reduce the Number of spaces to its half for the next row first node
			nBlanks /= 2;
			while(localStack.isEmpty()==false)
				globalStack.push( localStack.pop() );
		} // end while isRowEmpty is false
		System.out.println("......................................................");
	} // end displayTree()
	// -------------------------------------------------------------
} // end class Tree
////////////////////////////////////////////////////////////////

public class TreeApp {

	public static void main(String[] args) throws IOException
	{
		int value;
		Tree theTree = new Tree();
		theTree.insert(50, 1.5);
		theTree.insert(25, 1.2);
		theTree.insert(75, 1.7);
		theTree.insert(12, 1.5);
		theTree.insert(37, 1.2);
		theTree.insert(43, 1.7);
		theTree.insert(30, 1.5);
		theTree.insert(33, 1.2);
		theTree.insert(87, 1.7);
		theTree.insert(93, 1.5);
		theTree.insert(97, 1.5);
		while(true)
		{
			System.out.print("Enter first letter of show, ");
			System.out.print("insert, find, delete, or traverse: ");
			int choice = getChar();
			switch(choice)
			{
			case 's':
				theTree.displayTree();
				break;
			case 'i':
				System.out.print("Enter value to insert: ");
				value = getInt();
				theTree.insert(value, value + 0.9);
				break;
			case 'f':
				System.out.print("Enter value to find: ");
				value = getInt();
				Node found = theTree.find(value);
				if(found != null)
				{
					System.out.print("Found: ");
					found.displayNode();
					System.out.print("\n");
				}
				else
					System.out.print("Could not find ");
				System.out.print(value + "\n");
				break;
			case 'd':
				System.out.print("Enter value to delete: ");
				value = getInt();
				boolean didDelete = theTree.delete(value);
				if(didDelete)
					System.out.print("Deleted " + value + "\n");
				else
					System.out.print("Could not delete ");
				System.out.print(value + "\n");
				break;
			case 't':
				System.out.print("Enter type 1, 2 or 3: ");
				value = getInt();
				theTree.traverse(value);
				break;
			default:
				System.out.print("Invalid entry\n");
			} // end switch
		} // end while
	} // end main()
	// -------------------------------------------------------------
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	// -------------------------------------------------------------
	public static char getChar() throws IOException
	{
		String s = getString();
		return s.charAt(0);
	}
	//-------------------------------------------------------------
	public static int getInt() throws IOException
	{
		String s = getString();
		return Integer.parseInt(s);
	}
	// -------------------------------------------------------------
} // end class TreeApp
////////////////////////////////////////////////////////////////

/******* Expected Output ***************/
 /* 
Enter first letter of show, insert, find, delete, or traverse: s
......................................................
                                50 	                                                             
                25                              75                              
        12              37              --              87              
    --      --      30      43      --      --      --      93      
  --  --  --  --  --  33  --  --  --  --  --  --  --  --  --  97  
......................................................
Enter first letter of show, insert, find, delete, or traverse: 32
Invalid entry
Enter first letter of show, insert, find, delete, or traverse: i
Enter value to insert: 32
Enter first letter of show, insert, find, delete, or traverse: s
......................................................
                                50                                                              
                25                              75                              
        12              37              --              87              
    --      --      30      43      --      --      --      93      
  --  --  --  --  --  33  --  --  --  --  --  --  --  --  --  97  
 --------------------32------------------------------------------
......................................................
Enter first letter of show, insert, find, delete, or traverse: f
Enter value to find: 43
Found: {43, 1.7} 
43
Enter first letter of show, insert, find, delete, or traverse: f
Enter value to find: 35
Could not find 35
Enter first letter of show, insert, find, delete, or traverse: d
Enter value to delete: 30
Deleted 30
30
Enter first letter of show, insert, find, delete, or traverse: s
......................................................
                                50                                                              
                25                              75                              
        12              37              --              87              
    --      --      33      43      --      --      --      93      
  --  --  --  --  32  --  --  --  --  --  --  --  --  --  --  97  
......................................................
Enter first letter of show, insert, find, delete, or traverse: d
Enter value to delete: 55
Could not delete 55
Enter first letter of show, insert, find, delete, or traverse: s
......................................................
                                50                                                              
                25                              75                              
        12              37              --              87              
    --      --      33      43      --      --      --      93      
  --  --  --  --  32  --  --  --  --  --  --  --  --  --  --  97  
......................................................
Enter first letter of show, insert, find, delete, or traverse: t
Enter type 1, 2 or 3: 1

Preorder traversal: 50 25 12 37 33 32 43 75 87 93 97 
Enter first letter of show, insert, find, delete, or traverse: t
Enter type 1, 2 or 3: 2

Inorder traversal: 12 25 32 33 37 43 50 75 87 93 97 
Enter first letter of show, insert, find, delete, or traverse: t
Enter type 1, 2 or 3: 3

Postorder traversal: 12 32 33 43 37 25 97 93 87 75 50 
Enter first letter of show, insert, find, delete, or traverse: s
......................................................
                                50                                                              
                25                              75                              
        12              37              --              87              
    --      --      33      43      --      --      --      93      
  --  --  --  --  32  --  --  --  --  --  --  --  --  --  --  97  
......................................................
Enter first letter of show, insert, find, delete, or traverse: 
*/
