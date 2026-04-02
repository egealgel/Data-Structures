import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Subfield Info
class SubfieldInfo {
    String definition;
    String applicationAreas;

    public SubfieldInfo(String definition, String applicationAreas) {
        this.definition = definition;
        this.applicationAreas = applicationAreas;
    }

    @Override
    public String toString() {
        return " | Definition: " + definition.substring(0, Math.min(definition.length(), 40)) + "..."
                + " | Application: " + applicationAreas.substring(0, Math.min(applicationAreas.length(), 30)) + "...";
    }
}

// Inner Tree Node (For Subfields)
class SubfieldNode {
    String subfieldName;
    SubfieldInfo details;
    SubfieldNode left, right;

    public SubfieldNode(String subfieldName, SubfieldInfo details) {
        this.subfieldName = subfieldName;
        this.details = details;
        this.left = this.right = null;
    }
}

// Inner Tree (Holds all Subfields of a Main Field)
class SubfieldBST {
    SubfieldNode root;

    public SubfieldBST() {
        this.root = null;
    }

    // Insert Subfield
    public SubfieldNode insert(String subfieldName, SubfieldInfo details) {
        root = insertRec(root, subfieldName, details);
        return find(subfieldName);
    }

    private SubfieldNode insertRec(SubfieldNode root, String subfieldName, SubfieldInfo details) {
        if (root == null) {
            return new SubfieldNode(subfieldName, details);
        }

        int cmp = subfieldName.compareTo(root.subfieldName);
        if (cmp < 0) {
            root.left = insertRec(root.left, subfieldName, details);
        } else if (cmp > 0) {
            root.right = insertRec(root.right, subfieldName, details);
        } else {
            root.details = details;
            return root;
        }
        return root;
    }

    // Finding Subfield
    public SubfieldNode find(String subfieldName) {
        SubfieldNode current = root;
        while (current != null) {
            int cmp = subfieldName.compareTo(current.subfieldName);
            if (cmp == 0) return current;
            else if (cmp < 0) current = current.left;
            else current = current.right;
        }
        return null;
    }

    // Inorder traversal
    public void inorder(SubfieldNode node, int level) {
        if (node != null) {
            inorder(node.left, level);
            String indent = "    ".repeat(level);
            System.out.println(indent + "- Subfield: " + node.subfieldName + node.details.toString());
            inorder(node.right, level);
        }
    }

    // ✔ Finding subtree height
    public int height() {
        return heightRec(root);
    }

    private int heightRec(SubfieldNode node) {
        if (node == null) return 0;
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }
}

// Outer Tree Node
class MainFieldNode {
    String mainFieldName;
    SubfieldBST innerTree;
    MainFieldNode left, right;

    public MainFieldNode(String mainFieldName) {
        this.mainFieldName = mainFieldName;
        this.innerTree = new SubfieldBST();
        this.left = this.right = null;
    }
}

// Outer Tree (All Main Fields)
class MainFieldBST {
    MainFieldNode root;

    public MainFieldBST() {
        this.root = null;
    }
    

    // Insert or Get Main Field
    public MainFieldNode insertOrGet(String mainFieldName) {
        MainFieldNode current = root;
        MainFieldNode parent = null;

        while (current != null) {
            parent = current;
            int cmp = mainFieldName.compareTo(current.mainFieldName);
            if (cmp == 0)
                return current;
            else if (cmp < 0)
                current = current.left;
            else
                current = current.right;
        }

        MainFieldNode newNode = new MainFieldNode(mainFieldName);

        if (parent == null) root = newNode;
        else if (mainFieldName.compareTo(parent.mainFieldName) < 0)
            parent.left = newNode;
        else
            parent.right = newNode;

        return newNode;
    }

    // Inorder traversal
    public void inorderTraversal(MainFieldNode node, int level) {
        if (node != null) {
            inorderTraversal(node.left, level);

            String indent = "    ".repeat(level);
            System.out.println("\n" + indent + "=== Main Field: " + node.mainFieldName + " ===");
            node.innerTree.inorder(node.innerTree.root, level + 1);

            inorderTraversal(node.right, level);
        }
    }

    // Finding the deepest Subfield tree
    public void findDeepestSubfieldTree() {
        Result result = new Result();
        findDeepestRec(root, result);

        if (result.deepestNode != null) {
            System.out.println("\n|||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("Deepest Subfield Tree:");
            System.out.println("Main Field: " + result.deepestNode.mainFieldName);
            System.out.println("Depth: " + result.maxDepth);
            System.out.println("Subfields:");
            listSubfields(result.deepestNode.innerTree.root);
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||\n");
        }
    }

    private static class Result {
        int maxDepth = -1;
        MainFieldNode deepestNode = null;
    }

    private void findDeepestRec(MainFieldNode node, Result result) {
        if (node == null) return;

        findDeepestRec(node.left, result);

        int depth = node.innerTree.height();
        if (depth > result.maxDepth) {
            result.maxDepth = depth;
            result.deepestNode = node;
        }

        findDeepestRec(node.right, result);
    }

    // Print subfields in alphabetical order
    private void listSubfields(SubfieldNode node) {
        if (node != null) {
            listSubfields(node.left);
            System.out.println(" - " + node.subfieldName);
            listSubfields(node.right);
        }
    }
}
///////////////////////////////////
///////////////////////////////////
///////////////////////////////////
// Question 1 E option 
class WordNode { 
    String word;
    int count;
    WordNode left, right;

    public WordNode(String word) {
        this.word = word;
        this.count = 1;
        this.left = this.right = null;
    }
}
// Descending Order BST for Words
class DescendingWordBST {
    WordNode root;

    public DescendingWordBST() {
        this.root = null;
    }

    // Insert Word (Descending Alphabetical Order)
    public void insert(String word) {
        root = insertRec(root, word);
    }
    
    private WordNode insertRec(WordNode root, String word) {
        if (root == null) {
            return new WordNode(word);
        }

        // Comparison: Words that are alphabetically greater go to the LEFT.
        int cmp = word.compareTo(root.word);

        if (cmp < 0) { // if new word is alphabetically smaller than root word
            root.right = insertRec(root.right, word); // go right
        } else if (cmp > 0) { // if the new word is alphabetically greater than root word
            root.left = insertRec(root.left, word); // go left
        } else { // Word already exists
            root.count++;
        }
        return root;
    }

    // List words and their frequencies in ascending order
    public void listAscendingOrder() {
        System.out.println("\n|||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("Word Frequencies (Ascending Alphabetical Order):");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||");
        reverseInorder(root);
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||");
    }

    private void reverseInorder(WordNode node) {
        if (node != null) {
            reverseInorder(node.right); // First go right (alphabetically smaller)
            System.out.println(" - Word: " + node.word + " | Frequency: " + node.count);
            reverseInorder(node.left);  // Then go left (alphabetically greater)
        }
    }
}

public class NestedBST {
    public static void main(String[] args) {

        //data
        String[][] data = {
            {"Machine Learning", "Supervised Learning", "The model learns from labeled data (input data paired with the correct output) to map inputs to outputs.", "Spam filtering, House price prediction, Medical diagnosis"},
            {"Machine Learning", "Unsupervised Learning", "The model learns from unlabeled data, looking for hidden structures, patterns, or groupings on its own.", "Customer segmentation, Anomaly detection (fraud), Recommendation systems"},
            {"Machine Learning", "Reinforcement Learning (RL)", "An agent learns to make decisions by performing actions in an environment and receiving feedback via rewards/penalties.", "Game playing (AlphaGo), Robot navigation, Optimizing trading strategies"},
            {"Natural Language Processing", "NLU", "The ability of a computer to comprehend the structure and meaning of sentences.", "Sentiment analysis, Spam detection, Email classification"},
            {"Natural Language Processing", "Machine Translation", "Automating the translation of text or speech from one language to another.", "Google Translate, Real-time conversation translation devices"},
            {"Natural Language Processing", "NLG", "The process of transforming structured data into readable human language.", "Automated report writing, Chatbots generating responses"},
            {"Computer Vision", "Object Detection", "Locating and identifying multiple objects in an image or video.", "Autonomous vehicles, Surveillance, Quality control"},
            {"Computer Vision", "Image Classification", "Assigning a specific label or category to an entire image.", "Plant disease detection, Medical imaging"},
            {"Computer Vision", "Facial Recognition", "Identifying or verifying a person's identity using their face.", "Phone unlocking, Airport security, Social media tagging"},
            {"Robotics", "Motion Planning", "Calculating the optimal path for a robot to move without hitting obstacles.", "Warehouse robots, Drone paths"},
            {"Robotics", "Robot Perception", "Using sensors to map and understand the environment.","Vacuum robots mapping rooms, Mars rover navigation"},
            {"Robotics", "Human-Robot Interaction (HRI)", "Studying how humans and robots communicate.","Factory Cobots, Therapy robots"},
            {"Speech Processing", "Automatic Speech Recognition (ASR)", "Converting speech into text.", "Virtual assistants, Closed captioning, Medical dictation"},
            {"Speech Processing", "Text-to-Speech (TTS)", "Converting text to speech.", "GPS voices, Screen readers"},
            {"Expert Systems", "Knowledge Base & Inference Engine", "Solving problems using logic rules.", "Equipment maintenance, Medical diagnostics"},
            {"Fuzzy Logic", "Fuzzy Logic", "Approximate reasoning instead of strict true/false.", "Washing machines, ABS braking systems"},
            {"Planning & Optimization", "Planning & Scheduling", "Algorithms that search states to achieve goals.", "Route optimization, Airport gate assignment"}
        };

        // Outer BST
        MainFieldBST outerTree = new MainFieldBST();
        
        

        for (String[] row : data) {
            MainFieldNode mainNode = outerTree.insertOrGet(row[0]);
            SubfieldInfo details = new SubfieldInfo(row[2], row[3]);
            mainNode.innerTree.insert(row[1], details);
        }
        ////////////////////////
        ////////////////////////
        //Hash Map Section Question 2 
        MainFieldHash mainFieldHashMap = new MainFieldHash();

        mainFieldHashMap.createMapFromBST(outerTree); // creating a hash map from the outer tree

        mainFieldHashMap.printMap();
        mainFieldHashMap.printMap();

        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter old main field name to update: ");
        String oldName = sc.nextLine().trim();
        
        System.out.print("Enter new main field name: ");
        String newName = sc.nextLine().trim();

        mainFieldHashMap.updateMainFieldName(oldName, newName);

        mainFieldHashMap.printMap();

        sc.close();

        // Print the entire BST
        System.out.println("\n\n||||||||||||||||||||||||||||||||||||||");
        System.out.println("Binary Search Tree (In-Order Traversal):");
        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
        outerTree.inorderTraversal(outerTree.root, 0);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||\n");
   

        // ✔ Find the deepest Subfield tree
        outerTree.findDeepestSubfieldTree();

///////////////////////////////////
///////////////////////////////////
///////////////////////////////////
        // Question 1 E option
        DescendingWordBST wordTree = new DescendingWordBST(); // Tree for words in descending order

        Map<String, SubfieldBST> allSubfieldTrees = new HashMap<>(); // To hold all subfield trees

        collectAllSubfieldTrees(outerTree.root, allSubfieldTrees); // Collect all subfield trees

        for(SubfieldBST subTree : allSubfieldTrees.values()) {
            processSubfieldTree(subTree.root, wordTree); 
        }

        wordTree.listAscendingOrder(); // List words in ascending order with frequencies

    }

    private static void collectAllSubfieldTrees(MainFieldNode node, Map<String, SubfieldBST> map) { // Collecting all subfield trees definition
        if (node != null) {
            collectAllSubfieldTrees(node.left, map);
            map.put(node.mainFieldName, node.innerTree);
            collectAllSubfieldTrees(node.right, map);
        }
    }

    private static void processSubfieldTree(SubfieldNode node, DescendingWordBST wordTree){ // Process each subfield tree to extract words definition
        if (node != null){
            processSubfieldTree(node.left, wordTree);
            
            String definition = node.details.definition.toLowerCase().replaceAll("[.,():]", ""); // Clean punctuation

            String[] words = definition.split("\\s+"); // Split into words
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordTree.insert(word);
                }
            }
            processSubfieldTree(node.right, wordTree);
        }
    }    
}
