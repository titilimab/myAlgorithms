package myorg.algorithms.sort;

import java.util.Scanner;

public class QuickSortApp {

	public static void main(String[] args)
	{
		
		/*
		int maxSize = 5; // array size
		ArrayIns arr;
		arr = new ArrayIns(maxSize); // create array
		for(int j=0; j<maxSize; j++) // fill array with
		{ // random numbers
			long n = (int)(java.lang.Math.random()*99);
			arr.insert(n);
		}
		arr.display(); // display items
		arr.quickSort(); // quicksort them
		arr.display(); // display them again
		
		*/
		
		//
		int maxSize;
		long[] values;
		ArrayIns arr;
		
		//Accept user input through Scanner class in java.util package
		System.out.println("Enter the max size of the array : ");
		Scanner maxSizeObj = new Scanner(System.in);
		maxSize = maxSizeObj.nextInt();
		
		arr = new ArrayIns(maxSize);
		
		values = new long[maxSize];
		System.out.println("Enter all the elements in the array :");
		Scanner valuesObj = new Scanner(System.in);
		for(int j=0; j<maxSize; j++){
			
			values[j] = valuesObj.nextInt();
			arr.insert(values[j]); 
			
		}
		System.out.println("Array Before Sorting : ");
		arr.display();
		arr.quickSort();
		System.out.println("Array After Sorting : ");
		arr.display();
		
		
		
		
	} // end main()
} // end class QuickSort1App

class ArrayIns
{
	private long[] theArray; // ref to array theArray
	private int nElems; // number of data items
	//--------------------------------------------------------------
	public ArrayIns(int max) // constructor
	{
		theArray = new long[max]; // create the array
		nElems = 0; // no items yet
	}
	//--------------------------------------------------------------
	public void insert(long value) // put element into array
	{
		theArray[nElems] = value; // insert it
		nElems++; // increment size
	}
	//--------------------------------------------------------------
	public void display() // displays array contents
	{
		System.out.print("A= ");
		for(int j=0; j<nElems; j++) // for each element,
			System.out.print(theArray[j] + " "); // display it
		System.out.println(" ");
	}
	//--------------------------------------------------------------
	public void quickSort()
	{
		recQuickSort(0, nElems-1);
	}
	//--------------------------------------------------------------
	public void recQuickSort(int left, int right)
	{
		
		//System.out.println("left : "+left);
		//System.out.println("right : "+right);
		if(right-left <= 0){ // if size <= 1, already sorted
			//System.out.println("The array holds only 1 element or the left and right pointer have crossed each other and sorting is done");
			return;
		}
		else // size is 2 or larger
		{
			long pivot = theArray[right]; // rightmost item
			//System.out.println("#################################");
			//System.out.println("pivot element : "+pivot);
			// partition range
			int partition = partitionIt(left, right, pivot);
			//System.out.println("Partition Index : "+partition);
			//System.out.println("Perform Quick Sort from index : "+left+" to index : "+(partition-1));
			recQuickSort(left, partition-1); // sort left side
			//System.out.println("Perform Quick Sort from index : "+(partition+1)+" to index : "+right);
			recQuickSort(partition+1, right); // sort right side
		}
		
		//System.out.println("End of function recQuickSort");
		
	} // end recQuickSort()
	//--------------------------------------------------------------
	public int partitionIt(int left, int right, long pivot)
	{
		int leftPtr = left-1; // left (after ++)
		int rightPtr = right; // right-1 (after --)
		
		//System.out.println("********Start of partitionIt function ***********");
		//System.out.println("Find the Partition Index for array from left index : "+leftPtr+" to right index : "+rightPtr);
		while(true)
		{ // find the item bigger than the pivot
			while( theArray[++leftPtr] < pivot )
				; // (nop)
			// find the item smaller than the pivot
			while(rightPtr > 0 && theArray[--rightPtr] > pivot)
				; // (nop)
			if(leftPtr >= rightPtr) // if pointers cross,
				break; // partition done
			else // not crossed, so
				swap(leftPtr, rightPtr); // swap elements
		} // end while(true)
		swap(leftPtr, right); // restore pivot
		return leftPtr; // return pivot location
	} // end partitionIt()
	//--------------------------------------------------------------
	public void swap(int dex1, int dex2) // swap two elements
	{
		//System.out.println("Swapping elements : "+theArray[dex1]+" and "+theArray[dex2]);
		long temp = theArray[dex1]; // A into temp
		theArray[dex1] = theArray[dex2]; // B into A
		theArray[dex2] = temp; // temp into B
	} // end swap(
	//--------------------------------------------------------------
} // end class ArrayIns
