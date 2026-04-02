import java.io.BufferedReader;
import java.io.FileReader;
import java.util.PriorityQueue;
//I wrote this Heap and Node Classes from the textbook


class Node{ 
    private int iData; //data item (key)

    public Node(int key){ //constructor
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
    private int maxSize; //size of array
    private int currentSize; //number of nodes in array

    public Heap(int mx){ //constructor
        maxSize = mx;
        currentSize = 0;
        heapArray = new Node[maxSize]; //create the array
    }

    public boolean isEmpty(){
        return currentSize==0;
    }

    public boolean insert(int key){ //insert a node
        if(currentSize==maxSize)
            return false;
        Node newNode = new Node(key);
        heapArray[currentSize] = newNode;
        trickleUp(currentSize++);
        return true;
    }

    public void trickleUp(int index){ //move a node up in the tree
        int parent = (index-1) / 2;
        Node bottom = heapArray[index];

        while(index>0 && heapArray[parent].getKey() < bottom.getKey()){
            heapArray[index] = heapArray[parent];
            index = parent;
            parent = (parent-1) / 2;
        }
        heapArray[index] = bottom;
    }

    public Node remove(){ //remove node with max key
        Node root = heapArray[0];
        heapArray[0] = heapArray[--currentSize];
        trickleDown(0);
        return root;
    }

    public void trickleDown(int index){ //move a node down in the tree
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

    public boolean change(int index, int newValue){ //change the key in a node
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

    public void displayHeap(){ //display the heap
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
class MainField implements Comparable<MainField>{
    private String name;
    private String subfield;
    private String definition;
    private String appareas;

    public MainField(String name, String subfield, String definition, String appareas) {
        this.name = name;
        this.subfield = subfield;
        this.definition = definition;
        this.appareas = appareas;
    }

    public String getName() {
        return name;
    }
    public String getSubfield() {
        return subfield;
    }
    public String getDefinition() {
        return definition;
    }
    public String getAppareas() {
        return appareas;
    }

    public int compareTo(MainField other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    public String toString(){
        return String.format("Main Field: %-20s | Subfield: %s | Definition: %-40s | Application Areas: %s", name, subfield, definition, appareas);
    }
}  

public class MainFieldMinHeapExtract{
    public static void main(String[] args) {
        
        PriorityQueue<MainField> minHeap = new PriorityQueue<>(); // Min-heap for MainField objects
        
        String filePath = "artificialintelligence.csv"; //file path
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            br.readLine(); //skip header line

            while((line = br.readLine()) != null){ 
                if(line.trim().isEmpty()) continue; // Skip empty lines

                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");  //Split by comma, include empty strings
                
                for(int i=0; i<parts.length; i++){
                    parts[i] = parts[i].replace("\"", "").trim();   
                }

                if(parts.length >= 4){
                    String mainFieldName = parts[0]; //parsing main field name
                    String subfieldName = parts[1]; //parsing subfield name
                    String definition = parts[2]; //parsing definition
                    String appareas = parts[3]; //parsing application areas
                    
                    minHeap.add(new MainField(mainFieldName, subfieldName, definition, appareas)); // Add MainField object to min-heap
                }
                    
            }           
        } catch (Exception e) {
            e.printStackTrace();   
    }
        System.out.println("Extract first 5 objects from the min heap: ");
        int count = 0;
        while(!minHeap.isEmpty() && count < 5){
            
            System.out.println(count + 1 + ". " + minHeap.poll());
            count++;
        }
    }
}





