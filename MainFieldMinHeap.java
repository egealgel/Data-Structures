import java.io.BufferedReader;
import java.io.FileReader;
import java.util.PriorityQueue;
//I wrote this Heap and Node Classes from the textbook
class Node{
    private int iData;

    public Node(int key){
        iData = key;
    }

    public int getKey(){
        return iData;
    }

    public void setKey(int id){
        iData = id;
    }
}

class Heap{
    private Node[] heapArray;
    private int maxSize;
    private int currentSize;

    public Heap(int mx){
        maxSize = mx;
        currentSize = 0;
        heapArray = new Node[maxSize];
    }

    public boolean isEmpty(){
        return currentSize==0;
    }

    public boolean insert(int key){
        if(currentSize==maxSize)
            return false;
        Node newNode = new Node(key);
        heapArray[currentSize] = newNode;
        trickleUp(currentSize++);
        return true;
    }

    public void trickleUp(int index){
        int parent = (index-1) / 2;
        Node bottom = heapArray[index];

        while(index>0 && heapArray[parent].getKey() < bottom.getKey()){
            heapArray[index] = heapArray[parent];
            index = parent;
            parent = (parent-1) / 2;
        }
        heapArray[index] = bottom;
    }

    public Node remove(){
        Node root = heapArray[0];
        heapArray[0] = heapArray[--currentSize];
        trickleDown(0);
        return root;
    }

    public void trickleDown(int index){
        int largerChild;
        Node top = heapArray[index];
        while(index < currentSize/2){
            int leftChild = 2*index+1;
            int rightChild = leftChild+1;

            if(rightChild < currentSize && heapArray[leftChild].getKey() < heapArray[rightChild].getKey())
                largerChild = rightChild;
            else 
                largerChild = leftChild;

            if(top.getKey() >= heapArray[largerChild].getKey())
                break;

            heapArray[index] = heapArray[largerChild];
            index = largerChild;
        }
        heapArray[index] = top;
    }

    public boolean change(int index, int newValue){
        if(index<0 || index>=currentSize)
            return false;
        int oldValue = heapArray[index].getKey();
        heapArray[index].setKey(newValue);

        if(oldValue < newValue)
            trickleUp(index);
        else
            trickleDown(index);
        return true;
    }

    public void displayHeap(){
        System.out.println("HeapArray: ");
        for(int m=0; m<currentSize; m++)
            if(heapArray[m] != null)
                System.out.print( heapArray[m].getKey() + " ");
            else
                System.out.print( "- - ");
        System.out.println();

        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0;
        String dots = "...............................";
        System.out.println(dots + dots);

        while(currentSize > 0){
            if(column == 0)
                for(int k=0; k<nBlanks; k++)
                    System.out.print(' ');

            System.out.print(heapArray[j].getKey());
            if(++j == currentSize)
                break;
            if(++column ==itemsPerRow){
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            } else {
                for(int k=0; k<nBlanks*2-2; k++)
                    System.out.print(' ');
            }
        System.out.println("\n" + dots + dots);
            }
        }

    }
class MainField implements Comparable<MainField>{ // MainField class implementing Comparable
    private String name;


    public MainField(String name) { // Constructor
        this.name = name;
    }

    public String getName() { // Getter for name
        return name;
    }

    public int compareTo(MainField other) { // Comparison based on name
        return this.name.compareToIgnoreCase(other.name);
    }

    public String toString(){ 
        return name;
    }
}  

public class MainFieldMinHeap{
    public static void main(String[] args) {
        
        PriorityQueue<MainField> minHeap = new PriorityQueue<>(); // Min-heap for MainField objects
        
        String filePath = "artificialintelligence.csv"; //file path
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            br.readLine(); //skip header line

            while((line = br.readLine()) != null){
                String[] parts = line.split(","); //Split by comma
                if(parts.length >= 1){ 
                    String mainFieldName = parts[0].trim(); //parsing main field name
                    
                    minHeap.add(new MainField(mainFieldName)); // Add MainField object to min-heap
                }
                    
            }           
        } catch (Exception e) {
            e.printStackTrace();   
    }
        System.out.println("Main Fields Order to Min Heap: ");
        while(!minHeap.isEmpty()){
            MainField mainField = minHeap.poll();
            System.out.println(mainField);

        }
    }
}





