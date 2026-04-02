class ArrayBub{ 
    private int[] a; //ref to array a  
    private int nElems; //number of data items  
 
public ArrayBub(int max){ //constructor 
    a = new int[max]; //create the array 
    nElems = 0; //no items yet 
    } 
 
public void insert(int value){ //put element into array 
    a[nElems] = value; //insert it 
    nElems++; //increment size 
    } 
 
public void display(){ //display array contents 
    for(int j=0; j<nElems; j++) //for each element, 
    System.out.print(a[j] + " "); //display it 
    System.out.println(""); //new line 
    } 
 
public void bubbleSort(){ //bubble sort method 
    int out, in; 
    for(out=nElems-1; out>1; out--) //outer loop(backward) 
    for(in=0; in<out; in++) //inner loop(forward)  
    if( a[in] > a[in+1]) //if out of order, 
    swap(in, in+1); //swap them 
    } 
 
private void swap(int one,int two){ //swap two elements 
    int temp = a[one]; 
    a[one] = a[two]; 
    a[two] = temp;  
    } 
} 
 
class BubbleSortApp{ 
    public static void main(String[] args){ 
    int maxSize = 100; 
    ArrayBub arr;
    arr = new ArrayBub(maxSize); 
    
    arr.insert(10); 
    arr.insert(23); 
    arr.insert(33); 
    arr.insert(26); 
  
    arr.display(); 
    long startTime = System.currentTimeMillis(); 
    arr.bubbleSort(); 
    long endTime = System.currentTimeMillis(); 
    arr.display(); 
    System.out.println("Elapsed time: " + (endTime - startTime) + " milliseconds"); 
    }  
}  