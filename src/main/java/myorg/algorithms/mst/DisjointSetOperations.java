//Java Program to implement various DisjointSet Operations - makeSet, findSet, Union of Sets

//#########################################################################

/*
 Question - If relations are as given below, find if the relations in the question are possible

 Given : 
	If 2 is a friend of 3 (2-->3)
	If 5 is a friend of 3 (5-->3)
	If 6 is a friend of 4 (6-->4)

Find out using Disjoint Set Operations :
	Check if 5 is a friend of 2 (5-->2)
	Check if 6 is a friend of 2 (6-->2)

Expected Output:
	Since(2-->3 and 5-->3) Hence (5-->2) - Print ("5 is a friend of 2")
	Since(6-->4 and 4 is not related to any other member) Hence Print("6 is not a friend of 2") 

Console Output:
	5 is a friend of 2
	6 is not a friend of 2

 */
//#########################################################################
/*
 * @AUTHOR : Titilima Dash
 * 
Class: DisjointSetOperations
Data Members : 

	int[] rank : Integer Array to hold ranks of each parent node
	int[] parent : Integer Array to hold parent of each node
	int numOfNodes : Total number of Nodes(In reality - total number of persons)

Method Members:
	1. public DisjointSetOperations(int numOfNodes) : Parameterized constructor to initialize the Data Members(int[] rank, int[] parent, int numOfNodes)
	2. void makeSet() : This method initially creates the number of sets equal to the number of Nodes. And assigning every node in their own set.
						Hence, initially, every element is a representative/root/parent in their own Set.
	3. int findRepOfSet(int x) : This method aims at finding the Representative/Parent of the given member/node x or representative of the set of which x is an element
	4. void unionOfSets(int x, int y): This function takes 2 nodes/elements int x and int y as inputs and perform the union of the 2 sets of of which x and y are element.
										This union is performed on the basis of rank of the representative/root/parent of node/element and Path compression is implemented to minimize the path between the child nodes and the root nodes of the graph.
	5. main(String args[]) : This is the main method from where the program execution begins and drives the whole program									

 */
package myorg.algorithms.mst;

import java.util.*;
import java.io.*;

class DisjointSetOperations
{
	int[] rank, parent;
	int numOfNodes;

	// Constructor
	public DisjointSetOperations(int numOfNodes)
	{
		rank = new int[numOfNodes];
		parent = new int[numOfNodes];
		this.numOfNodes = numOfNodes;
		makeSet();
	}

	/*
	makeSet() method initially Creates number of sets equal to the number of Nodes with single element in each set. 
	Initially each element is in its own set. Hence, each element becomes the representative/Parent/Root of their own Set.
	 */
	void makeSet()
	{
		for (int i=0; i<numOfNodes; i++)
		{
			// Initially, all elements are in their own set.
			parent[i] = i;
		}
	}

	// Returns representative of x's set
	int findRepOfSet(int x)
	{
		// Finds the representative of the set that x is an element of

		if (parent[x]!=x)
		{
			/*
			if x is not the parent of itself,then x is not the representative of his set.
			Hence, we need to call this function findRepOf(int x) recursively till we find the parent of the set.
			Once we find the parent of the set, the recursion stops since at this point parent[x]==x and the execution control does not come into if block and parent[x] is returned.
			Finally we move i's node directly under the representative of this set
			 */
			parent[x] = findRepOfSet(parent[x]);
		}

		return parent[x];
	}

	//Perform the union of sets which has x and y as members by Rank and Path Compression.
	void unionOfSets(int x, int y)
	{
		//Find representatives of the Set of which x is an element and assign that to xRoot
		int xRoot = findRepOfSet(x);

		//Find representatives of the Set of which y is an element and assign that to yRoot
		int yRoot = findRepOfSet(y);

		/*
		When both the roots are equal(xRoot == yRoot), it signifies both the elements x and y are already in the same set.
		Hence, no need to perform the union operation.
		 */

		if (xRoot == yRoot)
			return;

		// If x's rank is less than y's rank
		if (rank[xRoot] < rank[yRoot]){
			// Then move x under y  by assigning yRoot as the new parent of xRoot . This way the depth  of tree remains less.
			parent[xRoot] = yRoot;
		}

		// Else if y's rank is less than x's rank
		else if (rank[yRoot] < rank[xRoot]){
			// Then move y under x  by assigning xRoot as the new parent of yRoot . This way the depth  of tree remains less.
			parent[yRoot] = xRoot;
		}

		else // if ranks are the same
		{
			// Then move y under x (doesn't matter which one goes where)
			parent[yRoot] = xRoot;

			// And increment the the result tree's rank by 1
			rank[xRoot] = rank[xRoot] + 1;
		}
	}


	public static void main(String[] args)
	{
		// Let there be 7 persons with ids as
		// 0, 1, 2, 3, 4, 5, and 6 (Total : 7 members Or Nodes/Vertices in terms of graph/Tree)
		int numOfNodes = 7;
		DisjointSetOperations djSetOp = new DisjointSetOperations(numOfNodes);

		System.out.println("Parent Array and Rank Array before Union Operation : ");
		for(int count=0; count<7 ; count++)
		{
			System.out.println("parent["+count+"] = "+djSetOp.parent[count]);
			System.out.println("rank["+count+"] = "+djSetOp.rank[count]);
		}

		/*
		djSetOp :numOfNodes,  parent, rank values :
		numOfNodes = 7
		parent[0]=0, parent[0]=1, parent[2]=2, parent[3]=3, parent[4]=4, parent[5]=5, parent[6]=6 --> Here every element is in its own set. So, each element is its own parent.
		rank[0]=0, rank[1]=0, rank[2]=0, rank[3]=0, rank[4]=0, rank[5]=0, rank[6]=0, Initially the rank of every element/node is set to 0
		 */

		// 2 is a friend of 3
		djSetOp.unionOfSets(2, 3);
		/*
		djSetOp :numOfNodes,  parent, rank values from unionOfSets(2, 3):
		numOfNodes = 7
		parent[0]=0, parent[0]=1, parent[2]=2, parent[3]=2, parent[4]=4, parent[5]=5, parent[6]=6 --> After union of Sets of which 2 and 3 are elements, the parent[3] is set to 2. It means now both 2 and 3 are in the same set since they have the same representative as 2.
		rank[0]=0, rank[1]=0, rank[2]=1, rank[3]=0, rank[4]=0, rank[5]=0, rank[6]=0 ---> Here the rank[2] is increased to 1 from 0. Since 3 is now a child of 2 and 2 is the representative of union set of 2,3. This is union by rank.
		 */

		// 5 is a friend of 3
		djSetOp.unionOfSets(5, 3);
		/*
		djSetOp :numOfNodes,  parent, rank values from unionOfSets(2, 3):
		numOfNodes = 7
		parent[0]=0, parent[0]=1, parent[2]=2, parent[3]=2, parent[4]=4, parent[5]=2, parent[6]=6 --> After union of Sets of which 5 and 3 are elements, the parent[5] is set to 2. It means now both 5 and 3 are in the same set since they have the same representative as 2.
		rank[0]=0, rank[1]=0, rank[2]=1, rank[3]=0, rank[4]=0, rank[5]=0, rank[6]=0 ---> Here the rank[2] is still 1 and no change in rank. 
		 */

		// 6 is a friend of 4
		djSetOp.unionOfSets(6, 4);
		/*
		djSetOp :numOfNodes,  parent, rank values from unionOfSets(2, 3):
		numOfNodes = 7
		parent[0]=0, parent[0]=1, parent[2]=2, parent[3]=2, parent[4]=6, parent[5]=2, parent[6]=6 --> After union of Sets of which 6 and 4 are elements, the parent[4] is set to 6. It means now both 6 and 4 are in the same set since they have the same representative as 2.
		rank[0]=0, rank[1]=0, rank[2]=1, rank[3]=0, rank[4]=0, rank[5]=0, rank[6]=1 --->  Here the rank[6] is increased to 1 from 0. Since 4 is now a child of 6 and 6 is the representative of union set of 6,4. This is union by rank. 
		 */

		System.out.println("Parent Array and Rank Array After Union Operation : ");
		for(int count=0; count<7 ; count++)
		{
			System.out.println("parent["+count+"] = "+djSetOp.parent[count]);
			System.out.println("rank["+count+"] = "+djSetOp.rank[count]);
		}

		//All the Elements having the same parent fall into the same set.Here 2,3 and 5 fall into one set having one Representative element. Next 4 and 6 fall into one set having one representative element.
		System.out.println("parent[2], parent[3], parent[5] : "+djSetOp.parent[5]+". Hence 2,3 and 5 fall into the same set");
		System.out.println("parent[4], parent[6] : "+djSetOp.parent[6]+". Hence 4 and 6 fall into the same set");

		// Check if 5 is a friend of 2
		if (djSetOp.findRepOfSet(5) == djSetOp.findRepOfSet(2))
			System.out.println("5 is a friend of 2");
		else
			System.out.println("5 is a not friend of 2");

		// Check if 6 is a friend of 2
		if (djSetOp.findRepOfSet(6) == djSetOp.findRepOfSet(2))
			System.out.println("6 is a friend of 2");
		else
			System.out.println("6 is not a friend of 2");

		/*
		 * Output :
		Parent Array and Rank Array before Union Operation : 
		parent[0] = 0
		rank[0] = 0
		parent[1] = 1
		rank[1] = 0	
		parent[2] = 2
		rank[2] = 0
		parent[3] = 3
		rank[3] = 0
		parent[4] = 4
		rank[4] = 0
		parent[5] = 5
		rank[5] = 0
		parent[6] = 6
		rank[6] = 0
		Parent Array and Rank Array After Union Operation : 
		parent[0] = 0
		rank[0] = 0
		parent[1] = 1
		rank[1] = 0
		parent[2] = 2
		rank[2] = 1
		parent[3] = 2
		rank[3] = 0
		parent[4] = 6
		rank[4] = 0
		parent[5] = 2
		rank[5] = 0
		parent[6] = 6
		rank[6] = 1
		parent[2], parent[3], parent[5] : 2. Hence 2,3 and 5 fall into the same set
		parent[4], parent[6] : 6. Hence 4 and 6 fall into the same set
		5 is a friend of 2
		6 is not a friend of 2

		 * */
	}
}
