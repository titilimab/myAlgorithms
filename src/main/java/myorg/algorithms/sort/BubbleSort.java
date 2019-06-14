package myorg.algorithms.sort;

/*
 *****What is Bubble Sort?*********
 Bubble sort is a sorting mechanism to sort elements in an array in a specific order(ascending or descending)
 There are 3 steps:
 	1. Compare two consecutive elements in the array starting from the first element
 	2. Swap the elements - If the element on the left is greater than the element on the right
 	3. Move one position right
 
 ******Anatomy of Bubble Sort*****
 Class 1 : ArrayBubbleSort :
 			Data Members(2) : long[] a - reference to array "a" which stores all the elements in the array
 			 				  int nElems - number of data elements to be inserted into the array
 			Method Members : ArrayBubbleSort(int max) - Constructor to create the Array
 							 insert(long value) - put elements into the array
 							 display() - display all the elements in the array
 							 bubbleSort() - Perform the bubble sort by swapping
 							 swap(int one, int two) - Swap 2 elements based on comparison
 							 
 Class 2 : BubbleSort :
 			Method Member : public static void main(String args[]) - This function is the entry point of the program 
 																	It calls all the below functions for driving the end-to-end bubble sorting operations
 																	1. ArrayBubbleSort(int max) : Calls the constructor to create the array
 																	2. insert(long value) : insert elements into the array
 																	3. display() : Display all the elements of the array
 																	4. bubbleSort() : Perform Bubble sort operation of the Array
 																	5. display() : Display all the elements of the sorted array
 																	
*/

class ArrayBubbleSort{
	private long[] a;				//reference to Array a
	private int nElems; 			//Number of data items that the Array can hold
	
//------------------------------------------
	
	public ArrayBubbleSort(int max) {	//Constructor : Parameter max holds the maximum size of the array
		a = new long[max];				//Create the array
		nElems = 0;						//no Items yet
	}
	
//-------------------------------------------
	
	public void insert(int value) {		//put Elements into the Array
		a[nElems]=value;				//Insert the value into the Array
		nElems++;						//Increment the pointer to the next index in the array for next value insertion into the array
	}
//--------------------------------------------
	
	public void display() {				//Display array contents
		for(int j=0; j<nElems ; j++){
			System.out.println(a[j]+" ");	//Display each element in the array
		}	
	}
//--------------------------------------------
/*
The concept of bubble sort algorithm is relying on
	1. Putting the smallest item at the beginning of the array(index 0)and the largest item at the end(index nElems-1)
	2. The loop counter "out" in the outer for loop starts at the end of the array, at nElems-1, and decrements itself each time through the loop
	3. The items at indices greater than "out" are always completely sorted
	4. The "out" variable moves left after each pass by "in" so that items that are already sorted are no longer involved in the sorting algorithm
	5. The inner loop counter "in" starts at the beginning of the array and increments itself each cycle of the inner loop, exiting when it reaches out.
	6. Within the inner loop, the two array cells pointed to by "in" and "in+1" are compared and swapped if the one at "in" is greater than the one in "in+1"
	7. swap() method is used to carry out the swap. It simply exchanges the 2 values in the 2 array cells, using a temporary variable
	  
*/
	
	public void bubbleSort() {
		int out, in;
		for(out=nElems-1; out>1; out--){		//outer loop(backward)
			for(in=0; in<out; in++)	{			//inner loop(forward)
				if(a[in]>a[in+1]){				//Swap if left element is greater than the right element
					swap(in, in+1);
				}
			}	
		}//End bubble sort
	}
 
//--------------------------------------------	
	
/*
 	1. swap() method is used to carry out the swap. 
 	It simply exchanges the 2 values in the 2 array cells, using a temporary variable "temp" to hold the value of the first cell while 
 	the first cell takes on the value of the second and then setting the second cell to the temporary variable.
 */
	
	private void swap(int one, int two) {
			long temp = a[one];
			a[one] = a[two];
			a[two] = temp;	
	}
}

//*****************	End of class ArrayBubbleSort **********************

public class BubbleSort {
	 public static void main(String[] args) {
		 int maxSize = 10;	 										//Array Size
		 ArrayBubbleSort arr;										//Reference to Array
		 arr = new ArrayBubbleSort(maxSize);						//Create the Array
		 
		 arr.insert(77);											//insert 10 items
		 arr.insert(80);
		 arr.insert(44);
		 arr.insert(55);
		 arr.insert(22);
		 arr.insert(32);
		 arr.insert(42);
		 arr.insert(57);
		 arr.insert(39);
		 arr.insert(69);
		 
		 System.out.println("Display the Array before sort : ");
		 arr.display();												//Display items
		 arr.bubbleSort();											//BubbleSort all the items
		 System.out.println("Display the Array after sort : ");
		 arr.display();												//Display the sorted items
		 	
	}

}

/*
************* Understanding Invariants of the Algorithm ********************
	1. Invariants of an algorithm are defined as the conditions that remain unchanged as the algorithm proceeds.
	2. Recognizing invariants can be useful in understanding and debugging the algorithm as you can repeatedly check that the invariant is true
	 and signal an error if it isn’t.
	3. In the above Bubblesort Algorithm, the invariant is that the data items to the right of the "out" index are always sorted.
	This remains true through out running of the algorithm. On the first pass, nothing has been sorted yet, and there are no items 
	to the right of "out" index because it starts from the right most element.
	
************* Efficiency of Bubblesort - Big O ********************

	1. While finding the complexity, the first deciding factor is the number of inputs.
	2. Here we have N number of elements in the array and there are N-1 comparisons on the first pass then N-2 comparisons on the 2nd pass and so on.
	The formula for the sum of such a series(Arithematic Progression) is :{n/2[2a+(n-1)d]  where n is the total number of elements}
	(N-1)+(N-2)+(N-3)+..........+1 
	={N/2[2(N-1)+(N-1)(-1)]}
	=N/2[2(N-1)-(N-1)]
	=N/2(N-1)
	
	Thus, the algorithm makes about N2⁄2 comparisons (ignoring the –1, which doesn’t make much difference, especially if N is large).
	3. Note : There are less number of swaps than there are comparisons because two consecutive numbers are swapped only if they need to be. 
	If the data is random, a swap is necessary about half the time, so there will be about N2⁄4 swaps. (Although in the worst case, with the 
	initial data inversely sorted, a swap is necessary with every comparison.)
	
	4. Both swaps and comparisons are proportional to N2. Because constants don’t count in Big O notation, 
	we can ignore the 2 and the 4 and say that the bubble sort runs in O(N2) time.
	5. Whenever you see one loop nested within another, such as those in the bubble sort and the other sorting algorithms in this chapter, 
	you can suspect that an algorithm runs in O(N2) time. The outer loop executes N times, and the inner loop executes N 
	(or perhaps N divided by some constant) times for each cycle of the outer loop. 
	This means you’re doing something approximately N*N or N2 times.

 */

/*############## Output ########################

Display the Array before sort : 
77 
80 
44 
55 
22 
32 
42 
57 
39 
69 

Display the Array after sort : 
22 
32 
39 
42 
44 
55 
57 
69 
77 
80 
  
 * */
