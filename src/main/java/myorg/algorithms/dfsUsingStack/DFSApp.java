package myorg.algorithms.dfsUsingStack;

/*
 
What are Graphs?
- A Graph in general constitutes of Vertices and Edges connecting the Vertices(Singular Vertex).
- Graphs have a shape dictated by a physical problem/Real world problem or abstract problem.
For Example, 
	Vertices of a graph may represent cities. And edges may represent Airline flight routes connecting the cities.
	
Adjacency :
	Two vertices are said to be adjacent/neighbor to each other if they are connected by a single edge.

 * */

//demonstrates depth-first search implementing Stack operations
////////////////////////////////////////////////////////////////
class StackX
{
	private final int SIZE = 20;
	private int[] st;
	private int top;
	//-----------------------------------------------------------
	public StackX() // constructor
	{
		st = new int[SIZE]; // make array
		top = -1;
	}
	//-----------------------------------------------------------
	public void push(int j) // put item on stack
	{ st[++top] = j; }
	//-----------------------------------------------------------
	public int pop() // take item off stack
	{ return st[top--]; }
	//------------------------------------------------------------
	public int peek() // peek at top of stack

	{ return st[top]; }
	//------------------------------------------------------------
	public boolean isEmpty() // true if nothing on stack-
	{ return (top == -1); }
	//------------------------------------------------------------
} // end class StackX
////////////////////////////////////////////////////////////////
class Vertex
{
	public char label; // label (e.g. ‘A’)
	public boolean wasVisited;
	//------------------------------------------------------------
	public Vertex(char lab) // constructor
	{
		label = lab;
		wasVisited = false;
	}
	//------------------------------------------------------------
} // end class Vertex
////////////////////////////////////////////////////////////////
class Graph
{
	private final int MAX_VERTS = 20;
	private int nVerts; // current number of vertices
	private Vertex vertexList[]; // list of vertices
	private int adjMat[][]; // adjacency matrix
	private StackX theStack;
	//-----------------------------------------------------------
	public Graph() // constructor
	{
		nVerts = 0;
		vertexList = new Vertex[MAX_VERTS];
		//adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		
		//Initialize all the elements in the Adjacency Matrix to 0
		for(int j=0; j<MAX_VERTS; j++){ // set adjacency
			for(int k=0; k<MAX_VERTS; k++) // matrix to 0
				adjMat[j][k] = 0;
		}
		theStack = new StackX();
	} // end constructor
	//-----------------------------------------------------------
	public void addVertex(char lab)

	{
		vertexList[nVerts++] = new Vertex(lab);
	}
	// -----------------------------------------------------------
	public void addEdge(int start, int end)
	{
		adjMat[start][end] = 1;
		adjMat[end][start] = 1;
	}
	// ------------------------------------------------------------
	public void displayVertex(int v)
	{
		System.out.print(vertexList[v].label);
	}
	// ------------------------------------------------------------
	// returns an unvisited vertex adj to v

	public int getAdjUnvisitedVertex(int v)
	{
		for(int j=0; j<nVerts; j++){
			//adjMat[v][j]==1 : this verifies if an edge exists between vertex v and j
			//vertexList[j].wasVisited==false : This verifies if the vertex j was already not visited
			if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
				return j;
		}
		
		//-1 is returned when there are no unvisited vertex adjacent to the given vertex v
		return -1;
	} // end getAdjUnvisitedVertex()
	// ------------------------------------------------------------

	public void dfs() // depth-first search
	{ // begin at vertex 0
		vertexList[0].wasVisited = true; // mark it
		displayVertex(0); // display it
		theStack.push(0); // push it
		while( !theStack.isEmpty() ) // until stack empty,
		{
			// get an unvisited vertex adjacent to stack top
			int v = getAdjUnvisitedVertex( theStack.peek() );
			if(v == -1) // if no such vertex,
				theStack.pop();
			else // if it exists,
			{
				vertexList[v].wasVisited = true; // mark it
				displayVertex(v); // display it
				theStack.push(v); // push it
			}
		} // end while
		// stack is empty, so we’re done
		for(int j=0; j<nVerts; j++) // reset flags
			vertexList[j].wasVisited = false;
	} // end dfs

} // end class Graph
////////////////////////////////////////////////////////////////
class DFSApp
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
		theGraph.dfs(); // depth-first search
		System.out.println();
	} // end main()
} // end class DFSApp
////////////////////////////////////////////////////////////////