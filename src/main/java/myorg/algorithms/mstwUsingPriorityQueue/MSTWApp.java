package myorg.algorithms.mstwUsingPriorityQueue;

//demonstrates minimum spanning tree with weighted graphs implementing a priority queue
////////////////////////////////////////////////////////////////
class Edge
{
	public int srcVert; // index of a vertex starting edge
	public int destVert; // index of a vertex ending edge
	public int distance; // distance from src to dest
	//-------------------------------------------------------------
	public Edge(int sv, int dv, int d) // constructor
	{
		srcVert = sv;
		destVert = dv;
		distance = d;
	}
	//-------------------------------------------------------------
} // end class Edge
////////////////////////////////////////////////////////////////
class Vertex
{
	public char label; // label (e.g. ‘A’)
	public boolean isInTree;
	//-------------------------------------------------------------
	public Vertex(char lab) // constructor
	{
		label = lab;
		isInTree = false;
	}
	//-------------------------------------------------------------
} // end class Vertex
////////////////////////////////////////////////////////////////
class PriorityQ
{

	// array in sorted order, from max at 0 to min at size-1
	private final int SIZE = 20;
	private Edge[] queArray;
	private int size;
	// -------------------------------------------------------------
	public PriorityQ() // constructor
	{
		queArray = new Edge[SIZE];
		size = 0;
	}
	// -------------------------------------------------------------
	public void insert(Edge item) // insert item in sorted order
	{
		/* Inorder to insert an item we need to do the following 4 actions
		 1. Find the location/index(j) you want to insert the item in a for loop based on condition item.distance >= current edge. distance
		 2. Move all the elements up by one index
		 3. insert the item once j index is found out
		 4. increment the size pointer as size++ since the size of the que increases after one element is added
		 */

		int j;
		for(j=0; j<size; j++) // find place to insert
			if( item.distance >= queArray[j].distance )
				break;
		for(int k=size-1; k>=j; k--) // move items up
			queArray[k+1] = queArray[k];
		queArray[j] = item; // insert item
		size++;
	}
	// -------------------------------------------------------------
	public Edge removeMin() // remove minimum item
	{ return queArray[--size]; }
	//-------------------------------------------------------------
	public void removeN(int n) // remove item at n
	{
		for(int j=n; j<size-1; j++) // move items down
			queArray[j] = queArray[j+1];
		size--;
	}
	//-------------------------------------------------------------
	public Edge peekMin() // peek at minimum item
	{ return queArray[size-1]; }
	//-------------------------------------------------------------
	public Edge peekN(int n) // peek at item n
	{ return queArray[n]; }		
	//-------------------------------------------------------------
	public int size() // return number of items
	{ return size; }
	//-------------------------------------------------------------
	public boolean isEmpty() // true if queue is empty
	{ return (size==0); }

	//-------------------------------------------------------------
	public int find(int findDex) // find the Edge item with specified destVert value
	{ 
		for(int j=0; j<size; j++)
			if(queArray[j].destVert == findDex)
				return j;
		return -1;
	}
	//-------------------------------------------------------------
} // end class PriorityQ

class Graph
{
	private final int MAX_VERTS = 20;
	private final int INFINITY = 1000000;
	private Vertex vertexList[]; // list of vertices
	private int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private int currentVert;
	private PriorityQ thePQ;
	private int nTree; // number of verts in tree
	//-------------------------------------------------------------
	public Graph() // constructor
	{
		vertexList = new Vertex[MAX_VERTS];
		//adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for(int j=0; j<MAX_VERTS; j++) // set adjacency
			for(int k=0; k<MAX_VERTS; k++) // matrix to 0
				adjMat[j][k] = INFINITY;
		thePQ = new PriorityQ();
	} // end constructor
	//-------------------------------------------------------------
	public void addVertex(char lab)
	{
		vertexList[nVerts++] = new Vertex(lab);
	}
	//-------------------------------------------------------------
	public void addEdge(int start, int end, int weight)
	{
		adjMat[start][end] = weight;
		adjMat[end][start] = weight;
	}
	// -------------------------------------------------------------
	public void displayVertex(int v)
	{
		System.out.print(vertexList[v].label);
	}
	// -------------------------------------------------------------
	public void putInPQ(int newVert, int newDist)
	{
		/*There are 2 conditions for which an Edge can be added to the PriorityQ "thePQ"
		1. When there is no Edge with the same Destination Vertex exists, make the new distance as the new Edge create it and insert it into the Priority queue(Implemented in the Else Block)
		2. If an Edge already exists with the same Destination Vertex, verify the distance of the Edge(Implemented inside if block if(queueIndex != -1)).
		 	a.	If the Distance of the new Edge is less than the distance of the existing Edge, then replace the existing Edge with the new Edge.
		 		So, Create a new Edge for the new Distance to new Destination Vertex and insert the Edge into the Priority Queue(Implemented inside if block if(oldDist > newDist)).
		 	b.	Else leave the existing Edge as it is(No Else block of code is required). 		
		 */
		//is there another edge with the same destination vertex?
		int queueIndex = thePQ.find(newVert);
		if(queueIndex != -1) // got edge’s index
		{
			Edge tempEdge = thePQ.peekN(queueIndex); // get edge
			int oldDist = tempEdge.distance;
			if(oldDist > newDist) // if new edge shorter,
			{
				thePQ.removeN(queueIndex); // remove old edge
				Edge theEdge = new Edge(currentVert, newVert, newDist);
				thePQ.insert(theEdge); // insert new edge
			}
			//else no action; just leave the old vertex there
		} // end if
		else // no edge with same destination vertex so insert new one
		{ 
			Edge theEdge = new Edge(currentVert, newVert, newDist);
			thePQ.insert(theEdge);
		}
	} // end putInPQ()
	//-------------------------------------------------------------

	/*What is difference between break, continue and return statements?

		The break statement results in the termination of the loop, it will come out of the loop and stops further iterations. 
		The continue statement stops the current execution of the iteration and proceeds to the next iteration. 
		The return statement takes you out of the method. It stops executing the method and returns from the method execution.
	 */

	/* mstw() method performs the below major 4 functions
	1. Put the current vertex in the Tree by setting "isInTree" flag as "true" and incrementing the counter "nTree"
	2. put the min Edges connecting the currentVertex and counter "j" in the priority queue based on various conditions
	3. Remove the min Edge from the Priority Queue and print the source and destination vertices of the Edge
	4. set the "isInTree" flag of all the vertices back to "false"

	 */
	public void mstw() // minimum spanning tree
	{
		currentVert = 0; // start at 0

		while(nTree < nVerts-1) // while not all verts in tree
		{ // put currentVert in tree
			/*
		 There are 2 actions inorder to put a vertex in the tree
			1. set "isInTree" flag of the vertex to "true"
			2. increment the counter "nTree" 
			 */
			vertexList[currentVert].isInTree = true;
			nTree++;
			// insert edges adjacent to currentVert into PQ
			for(int j=0; j<nVerts; j++) // for each vertex,
			{
				if(j==currentVert) // skip if it’s us
					continue;
				if(vertexList[j].isInTree) // skip if in the tree
					continue;
				int distance = adjMat[currentVert][j];
				if( distance == INFINITY) // skip if no edge
					continue;
				putInPQ(j, distance); // put it in PQ (maybe)
			}
			if(thePQ.size()==0) // no vertices in PQ?
			{
				System.out.println("GRAPH NOT CONNECTED");
				return;
			}
			// remove edge with minimum distance, from PQ
			//In order to print the Edges with minimum distance use removeMin() method to fetch the minimum edge from the Priority Queue
			Edge theEdge = thePQ.removeMin();
			int sourceVert = theEdge.srcVert;
			//set the destination vertex of the edge having minimum distance as the current vertex for next iteration in while loop
			currentVert = theEdge.destVert; 
			// display edge from source to current
			System.out.print( vertexList[sourceVert].label ); 
			System.out.print( vertexList[currentVert].label );
			System.out.print("="+theEdge.distance);
			System.out.print(" ");
		} // end while(not all verts in tree)
		// mst is complete
		for(int j=0; j<nVerts; j++) // unmark vertices
			vertexList[j].isInTree = false;
	} // end mstw
	//-------------------------------------------------------------

} // end class Graph
////////////////////////////////////////////////////////////////
class MSTWApp
{
	public static void main(String[] args)
	{

		Graph theGraph = new Graph();
		theGraph.addVertex('A'); // 0 (start for mst)
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4
		theGraph.addVertex('F'); // 5


		theGraph.addEdge(0, 1, 6); // AB 6
		theGraph.addEdge(0, 3, 4); // AD 4
		theGraph.addEdge(1, 2, 10); // BC 10
		theGraph.addEdge(1, 3, 7); // BD 7
		theGraph.addEdge(1, 4, 7); // BE 7
		theGraph.addEdge(2, 3, 8); // CD 8
		theGraph.addEdge(2, 4, 5); // CE 5
		theGraph.addEdge(2, 5, 6); // CF 6
		theGraph.addEdge(3, 4, 12); // DE 12
		theGraph.addEdge(4, 5, 7); // EF 7

		/*
		theGraph.addEdge(0, 1, 1); // AB 6
		theGraph.addEdge(0, 3, 1); // AD 4
		theGraph.addEdge(1, 2, 1); // BC 10
		theGraph.addEdge(1, 3, 1); // BD 7
		theGraph.addEdge(1, 4, 1); // BE 7
		theGraph.addEdge(2, 3, 1); // CD 8
		theGraph.addEdge(2, 4, 1); // CE 5
		theGraph.addEdge(2, 5, 1); // CF 6
		theGraph.addEdge(3, 4, 1); // DE 12
		theGraph.addEdge(4, 5, 1); // EF 7
		 */
		System.out.print("Minimum spanning tree: ");
		theGraph.mstw(); // minimum spanning tree
		System.out.println();
	} // end main()
} // end class MSTWApp
///////////////////////////////////////////////////////////////