package myorg.algorithms.bst;

/*We can do 4 operations with a Binary Tree
 * 1. Insert a node
 * 2. Traverse through the Tree : Inorder, Preorder and Postorder traversal
 * 3. Search a Node
 * 4. Delete a Node
 * */

/* Here we deal with 4 types of Node variables
 * rootNode : To store the information of the rootNode and it indicates the starting point of Tree Traversal
 * newNode : To store the new incoming node Information(int key, String name, Node rightChild, Node leftChild)
 * focusNode : This node is very important in the algorithm. It acts as a movable pointer and stores the information of current node that is taken for comparison with the incoming newNode
 * 				Once the comparison is done this moves to the next right or left child based on the comparison result
 * parentNode : To store the information of parentNode of the incoming newNode. 
 * 				focusNode is first assigned to parentNode. 
 * 				Then after newNode key has been compared with focusNode key, newNode is assigned either as the leftChild or rightChild of parentNode
 * 
 */
public class BinarySearchTree {
	
	Node rootNode;
	
	//Operation 1 : Add a node 
	public void addNode(int key, String name){
		
		Node newNode = new Node(key, name);
		
		/*If root is null, it means we do not have an existing  root Node currently. So,we need to create a root node by assigning newNode to it
		 * Else if root is not null, it means a root node already exists then we can assign the root node to focusNode for further comparison operations
		*/
		if(rootNode == null){
			System.out.println("The Root Node is null. So, make the newNode as the Root Node");
			rootNode = newNode;
		}
		else{
			
			//focusNode is like a temporary Node to store the current rootNode information for further comparison and acts as a pointer that can move to the next left/right child node inorder to traverse through the tree
			Node focusNode = rootNode;
			
			//Create a Node to store the future parent of an incoming new Node
			Node parentNode;
			
			while(true){//This creates infinite loop which we jump out at some point
				
				parentNode = focusNode;
				
				//If key that is value of the newNode is less than key of focusNode then we assign leftChild of the focusNode to focusNode
				if(key < focusNode.key){
					focusNode = focusNode.leftChild;
					
					//If now, focusNode is null means the focusNode does not have children, we assign newNode to the parent.leftChild
					if(focusNode == null){
						parentNode.leftChild = newNode;
						return;// Come out of the while loop
					}
				}
				else{
					//If key is greater than the key of focusNode assign the rightChild of focusNode to focusNode
					focusNode = focusNode.rightChild;
					
					//If now, the focusNode is null, it means focusNode does not have right child. So, we assign newNode to the right child of the parentNode 
					if(focusNode == null){
						parentNode.rightChild = newNode;
						return;//Come out of the while loop
					}
					
				}
				
			}
		}
	}//End of addNode method
	
//	
	
	public void inorderTraverseTree(Node focusNode) {
		if(focusNode != null){
			
			inorderTraverseTree(focusNode.leftChild);
			System.out.println(focusNode);
			inorderTraverseTree(focusNode.rightChild);
		}
		
	}
	
	public static void main(String args[]) {
		BinarySearchTree bst1 = new BinarySearchTree();
		//Add first node
		System.out.println("Add node - 50, Boss");
		bst1.addNode(50, "Boss");
		bst1.addNode(25, "VP");
		bst1.addNode(15, "Office Manager");
		bst1.addNode(30, "Secretary");
		bst1.addNode(75, "Sales Manager");
		bst1.addNode(85, "Salesman");
		
		bst1.inorderTraverseTree(bst1.rootNode);
		
		
	}

}

/* ---1st----
 * Define a class Node with 4 Data members and 2 method members
 * key(Type int) : To hold the value of Node
 * name(Type String) : To hold name of the Node
 * leftChild(Type Node) : To hold the left Node whose value/key is less than its parent/root Node
 * rightChild(Type Node) : To hold the right Node whose value/key is more than its parent/root Node
 * Node(int key, String name) : Constructor to initialize its members - key and name
 * public String toString(): Method to return the name and key of the Node it is invoked by.
 * */
class Node{
	int key;
	String name;
	
	Node leftChild;
	Node rightChild;
	
	Node(int key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public String toString() {
		return name +" has key : "+key;
		
	}
}

/*
############# Output ###############

Add node - 50, Boss
The Root Node is null. So, make the newNode as the Root Node
Office Manager has key : 15
VP has key : 25
Secretary has key : 30
Boss has key : 50
Sales Manager has key : 75
Salesman has key : 85

* */