package myorg.algorithms.bfsUsingQueue;

/*

 ********** Breadth First Search(BFS) Algorithm **********
 
Graph Details : 

Vertices :
theGraph.addVertex(‘A’); // 0 (start for dfs)
theGraph.addVertex(‘B’); // 1
theGraph.addVertex(‘C’); // 2
theGraph.addVertex(‘D’); // 3
theGraph.addVertex(‘E’); // 4
theGraph.addVertex(‘F’); // 5
theGraph.addVertex(‘G’); // 6
theGraph.addVertex(‘H’); // 7
theGraph.addVertex(‘I’); // 8

Edges :
(Starting Vertex - A)
theGraph.addEdge(0, 1); // AB
theGraph.addEdge(0, 2); // AC
theGraph.addEdge(0, 3); // AD
theGraph.addEdge(0, 4); // AE

(Starting Vertex - B)
theGraph.addEdge(1, 5); // BF

(Starting Vertex - D)
theGraph.addEdge(3, 6); //DG

(Starting Vertex - F)
theGraph.addEdge(5, 7); //FH

(Starting Vertex - G)
theGraph.addEdge(6, 8); //GI

The Order in which the Vertices are visited are as below sequence :

1st - A
2nd - B
3rd - C
4th - D
5th - E
6th - F
7th - G
8th - H
9th - I

Note : Plot the above Graph before going through the below topic.

Breadth First Search :

The fundamental difference between Depth First Search(DFS) and BFS(Breadth First Search) :

	1. DFS is implemented using Stack(Last In First Out Logic). DFS algorithm acts as though it wants to get as far away from the starting point
	as quickly as possible.

	2. BFS is implemented using Queue(First In First Out Logic). BFS algorithm acts as though it wants to stay as close as possible 
	to the starting point. It visits all the vertices adjacent to the starting Vertex, and then only goes further afield.

Rules for BFS algorithm to be followed iteratively:

A is the starting Vertex, so you visit it and make it the current Vertex. Then you follow these rules.

RULE 1:
Visit the next unvisited vertex(if there is one) that is adjacent to the current vertex., mark it and insert it into the queue.
Note - In a Queue, the elements are always inserted at the rear end and removed at the front end. It follows First In First Out Logic.

RULE 2:
If you can not carry out RULE 1 because there are no more unvisited vertices adjacent to the current vertex, remove a vertex from the Queue if possible and make it the current vertex.
Note - Removal of items from a Queue is always done at the Front end.

RULE 3:
If you can not carry out RULE 2 because the Queue is empty, you are done.

 */

////////////////////////////////////////////////////////////////
class Queue
{
	private final int SIZE = 20;
	private int[] queArray;
	private int front;
	private int rear;
	// -------------------------------------------------------------
	public Queue() // constructor
	{
		queArray = new int[SIZE];
		front = 0;
		rear = -1;
	}
	// -------------------------------------------------------------
	public void insert(int j) // put item at rear of queue
	{
		/*Before Inserting an element into the queue first verify if the queue is full
		 * if the Queue is full then rear == SIZE-1 and reinitialize rear to -1 for inserting next element
		 * Then increment the rear pointer and insert the value j into queArray
		 */
		
		if(rear == SIZE-1)
			rear = -1;
		queArray[++rear] = j;
	}
	// -------------------------------------------------------------
	public int remove() // take item from front of queue
	{
		/*Before removing an element from the queue first verify if the queue is empty. 
		 * Keep the element from front of the queue in a temporary variable temp and then increment the front pointer
		 * After removal , verify if Queue is empty by the condition front == SIZE is true. Then initialize front pointer to 0.
		 * At last return the temp variable which holds the elements that got removed from the Queue
		 */
		int temp = queArray[front++];
		if(front == SIZE)
			front = 0;
		return temp;
	}
	//-------------------------------------------------------------
	public boolean isEmpty() // true if queue is empty
	{
		return ( rear+1==front || (front+SIZE-1==rear) );
	}
	//-------------------------------------------------------------
} // end class Queue
////////////////////////////////////////////////////////////////
class Vertex
{
	public char label; // label (e.g. ‘A’)
	public boolean wasVisited;
	//-------------------------------------------------------------
	public Vertex(char lab) // constructor
	{
		label = lab;
		wasVisited = false;
	}
	//-------------------------------------------------------------
} // end class Vertex
////////////////////////////////////////////////////////////////

class Graph
{
	private final int MAX_VERTS = 20;
	private Vertex vertexList[]; // list of vertices
	private int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private Queue theQueue;
	// ------------------
	public Graph() // constructor
	{
		vertexList = new Vertex[MAX_VERTS];
		// adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for(int j=0; j<MAX_VERTS; j++) // set adjacency
			for(int k=0; k<MAX_VERTS; k++) // matrix to 0
				adjMat[j][k] = 0;
		theQueue = new Queue();
	} // end constructor

	//-------------------------------------------------------------
	public void addVertex(char lab)
	{
		vertexList[nVerts++] = new Vertex(lab);
	}
	//-------------------------------------------------------------
	public void addEdge(int start, int end)
	{
		adjMat[start][end] = 1;
		adjMat[end][start] = 1;
	}
	//-------------------------------------------------------------
	public void displayVertex(int v)
	{
		System.out.print(vertexList[v].label);
	}
	//-------------------------------------------------------------
	//returns an unvisited vertex adj to v
	public int getAdjUnvisitedVertex(int v)
	{
		for(int j=0; j<nVerts; j++)
			if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
				return j;
		return -1;
	} // end getAdjUnvisitedVertex()
	//-------------------------------------------------------------
	public void bfs() // breadth-first search
	{ // begin at vertex 0
		vertexList[0].wasVisited = true; // mark it
		displayVertex(0); // display it
		theQueue.insert(0); // insert at tail
		int v2;
		while( !theQueue.isEmpty() ) // until queue empty,
		{
			int v1 = theQueue.remove(); // remove vertex at head
			//until it has no unvisited neighbors
			while( (v2=getAdjUnvisitedVertex(v1)) != -1 )
			{ // get one,
				vertexList[v2].wasVisited = true; // mark it
				displayVertex(v2); // display it
				theQueue.insert(v2); // insert it
			} // end while
		} // end while(queue not empty)
		//queue is empty, so we’re done
		for(int j=0; j<nVerts; j++) // reset flags
			vertexList[j].wasVisited = false;
	} // end bfs()
	//-------------------------------------------------------------

} // end class Graph
////////////////////////////////////////////////////////////////
class BFSApp
{
	public static void main(String[] args)
	{
		Graph theGraph = new Graph();
		theGraph.addVertex('A'); // 0 (start for dfs)
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4
		theGraph.addEdge(0, 1); // AB
		theGraph.addEdge(1, 2); // BC
		theGraph.addEdge(0, 3); // AD
		theGraph.addEdge(3, 4); // DE
		System.out.print("Visits: ");
		theGraph.bfs(); // breadth-first search
		System.out.println();
	} // end main()
} // end class BFSApp
////////////////////////////////////////////////////////////////