//Java Program to find out cycle in a connected undirected graph

/*
 1. Depth First Search(Acronym-DFS) can be used to detect cycle in a connected and undirected graph in O(V+E) time.
 2. DFS : Depth First Search is an algorithm for traversing and searching tree or graph Data Structures. 
 		- The searching can start by selecting some arbitrary node
 		- Then visiting all nodes on all possible branches/edges.
 		- After all edges/branches are visited the performing a backtracking.
 3. On the contrary, the time complexity of the union-find algorithm is O(ELogV).
 4. As per the DFS and below program, we say there is cycle in a graph if For every visited vertex ‘v’, if there is an adjacent ‘u’ such that u is already visited and u is not parent of v.
 5. If we don’t find such an adjacent for any vertex, we say that there is no cycle.
 6. This method is based on the assumption that there are no parallel edges between any two vertices.
 
 Reference : Wikipedia : https://en.wikipedia.org/wiki/Depth-first_search

##############################################################################################

 Question - Find out if the specified connected, undirected graph  contains a cycle.
			Graph1:	1 --> 0
					0 --> 2
					2 --> 1
					0 --> 3
					3 --> 4

			Graph2: 0 --> 1
					1 --> 2

Expected Output : 

 			Graph1 contains cycle (1-->0, 0-->2, 2-->1)
 			Graph2 doesn't contain cycle (0 --> 1, 1 --> 2)

Console Output :
			Graph1 contains cycle 
 			Graph2 doesn't contain cycle

##############################################################################################

  Class : CyclicGraphDetectionByAdjacencyList
  Data members : 
  	1. int V (number of Vertices/Nodes)
 	2. LinkedList<Integer> adjacencyVerticesList[] (Adjacency List Representation, It contains the list of all visited Vertices/Nodes)

 	Method Members :
 	1. CyclicGraphDetectionByAdjacencyList(int v)  : This is the contructor method which takes integer v(number of vertices) as the input and creates
 														as many LinkedList adj array object as the number of vertices indices from 0 to v-1.
 	2. void addEdge(int v,int w) : This is the method that takes 2 inputs int v and int w as the source and destination vertices that forms the edge
 	3. Boolean isCyclicUtil(int v, Boolean visited[], int parent) : This function takes input as int v(Vertex or Node), Boolean visited[](Array that stores boolean value as true if the vertex is already visited and false if the vertex is not already visited)
 													int parent(The parent of the vertex v). And this function returns Boolean as true if the input vertex v is already visited.
 	4. Boolean icCyclic() : This method finds out if the graph contains a cycle and returns true if it contains a cycle.

 */


package myorg.algorithms.mst;

import java.io.*;
import java.util.*;

//This class CyclicGraphDetectionByAdjacencyList represents a directed graph using adjacency list representation
public class CyclicGraphDetectionByAdjacencyList
{
	private int V;   // No. of Vertices/Nodes of the Graph
	private LinkedList<Integer> adjacencyVerticesList[]; // Adjacency List Representation

	/*
	Method1 : 
	Constructor with number of vertices v as input parameter
	 */
	CyclicGraphDetectionByAdjacencyList(int v) {
		V = v;
		adjacencyVerticesList = new LinkedList[v];
		for(int i=0; i<v; ++i)
			adjacencyVerticesList[i] = new LinkedList();
	}

	/*
	  Method2 : 
	  Function to add an edge to the graph : Vertex1-v and Vertex2-w as input parameters
	  This function creates the adjacency List.
	  Graph1 : Adjacency List: [[1, 2, 3], [0, 2], [0, 1], [0, 4], [3]]
	  Graph2 : Adjacency List: [[1], [0, 2], [1]]
	 */
	void addEdge(int v,int w) {
		adjacencyVerticesList[v].add(w);
		adjacencyVerticesList[w].add(v);
	}

	/*
	Method3 :
	A recursive function that uses visited[] and parent to detect cycle in subgraph reachable from vertex v.
	 */
	Boolean isCyclicUtil(int v, Boolean visited[], int parent)
	{
		// Mark the current node as visited
		System.out.println("Verify if Vertex : "+v+" is visited");
		visited[v] = true;
		System.out.println("adjacencyVerticesList[v] : "+adjacencyVerticesList[v]+" ---  visited["+v+"] : "+visited[v]+ " --- parent array : "+parent);
		Integer i;

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> it = adjacencyVerticesList[v].iterator();
		while (it.hasNext())
		{
			i = it.next();
			System.out.println("Adjacent Node of vertex : "+v+" is : "+i);

			// If an adjacent is not visited, then call the function-isCyclicUtil recursively for that adjacent
			if (!visited[i])
			{
				System.out.println("Adjacent Vertex of : "+v+" that is : "+i+" is already not visited. Hence call the isCyclicUtil function recursively.");
				if (isCyclicUtil(i, visited, v)){
					return true;
				}
			}

			// If an adjacent is visited and not parent of current vertex, then there is a cycle.
			else if (i != parent)
				return true;
		}
		return false;
	}

	// Returns true if the graph contains a cycle, else false.
	Boolean isCyclic()
	{
		// Mark all the vertices as not visited and not part of recursion stack
		Boolean visited[] = new Boolean[V];
		for (int i = 0; i < V; i++)
			visited[i] = false;

		// Call the recursive function to detect cycle in different DFS trees
		for (int u = 0; u < V; u++)
		{
			System.out.println("Verify if Vertex : "+u+" is already visited. If not, visit the vertex. If yes, do not visit the vertex");
			if (!visited[u]){ // Don't recur for u if already visited
				System.out.println("Vertex : "+u+" is not yet visited. Hence visit the vertex and find out if the Adjacent Vertices are visited");
				if (isCyclicUtil(u, visited, -1)){
					return true;
				}
			}
			else
				System.out.println("Vertex : "+u+" is already visited");
		}
		return false;
	}


	// Driver method to test above methods
	public static void main(String args[])
	{
		// Create a graph given in the above diagram
		//Graph1 :
		System.out.println("################ Graph 1 #####################");
		CyclicGraphDetectionByAdjacencyList g1 = new CyclicGraphDetectionByAdjacencyList(5);
		g1.addEdge(1, 0);
		g1.addEdge(0, 2);
		g1.addEdge(2, 1);
		g1.addEdge(0, 3);
		g1.addEdge(3, 4);
		/*
		Vertex Array: [0, 1, 2, 3, 4], Adjacency List: [[1, 2, 3], [0, 2], [0, 1], [0, 4], [3]]
		Adjacent Vertices to 0 : [1, 2, 3] (1-->0, 0-->2, 0-->3)
		Adjacent Vertices to 1 : [0, 2] (1-->0, 2-->1)
		Adjacent Vertices to 2 : [0, 1] (0-->2, 2-->1)
		Adjacent Vertices to 3 : [0, 4] (0-->3, 3-->4)
		Adjacent Vertices to 4 : [3] (3-->4)
		 */

		if (g1.isCyclic())
			System.out.println("Graph1 contains cycle");
		else
			System.out.println("Graph1 doesn't contains cycle");

		//Graph2 :
		System.out.println("################ Graph 2 #####################");
		CyclicGraphDetectionByAdjacencyList g2 = new CyclicGraphDetectionByAdjacencyList(3);
		g2.addEdge(0, 1);
		g2.addEdge(1, 2);
		/*
		Vertex Array: [0, 1, 2], Adjacency List: [[1], [0, 2], [1]]
		Adjacent Vertices to 0 : [1] (0-->1)
		Adjacent Vertices to 1 : [0, 2] (0-->1, 1-->2)
		Adjacent Vertices to 2 : [1] (1-->2)
		 */
		if (g2.isCyclic())
			System.out.println("Graph2 contains cycle");
		else
			System.out.println("Graph2 doesn't contain cycle");

		//Graph3 :
		System.out.println("################ Graph 3 #####################");
		CyclicGraphDetectionByAdjacencyList g3 = new CyclicGraphDetectionByAdjacencyList(6);
		g3.addEdge(0, 1);
		g3.addEdge(1, 2);
		g3.addEdge(2, 3);
		g3.addEdge(2, 4);
		g3.addEdge(3, 5);
		/*
				Vertex Array: [0, 1, 2], Adjacency List: [[1], [0, 2], [1]]
				Adjacent Vertices to 0 : [1] (0-->1)
				Adjacent Vertices to 1 : [0, 2] (0-->1, 1-->2)
				Adjacent Vertices to 2 : [1] (1-->2)
		 */
		if (g3.isCyclic())
			System.out.println("Graph3 contains cycle");
		else
			System.out.println("Graph3 doesn't contain cycle");
	}
}
