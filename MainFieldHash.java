import java.util.HashMap;
import java.util.Map;

// Question 2: Hash Map Implementation
class MainFieldHash {

    private HashMap<String, SubfieldBST> fieldMap; 

    public MainFieldHash() { // Constructor
        this.fieldMap = new HashMap<>();
    }

    public void createMapFromBST(MainFieldBST tree) {
        traverseAndAdd(tree.root);
        System.out.println("Data inserted into Hash Map from BST successfully.");
    }

    // Helper Recursive Method (To traverse the tree)
    private void traverseAndAdd(MainFieldNode node) {
        if (node != null) {
            // Traverse left subtree first
            traverseAndAdd(node.left);
            
            // Add the current node to the Hash Map
            fieldMap.put(node.mainFieldName, node.innerTree);
            
            // Traverse right subtree
            traverseAndAdd(node.right);
        }
    }

    // 2. Print Data in Hash Map
    public void printMap() {
        System.out.println("\n============================================");
        System.out.println("HASH MAP CONTENT");
        System.out.println("============================================");
        
        if (fieldMap.isEmpty()) {
            System.out.println("Empty! ");
            return;
        }

        
        for (Map.Entry<String, SubfieldBST> entry : fieldMap.entrySet()) {
            String key = entry.getKey();
            SubfieldBST subTree = entry.getValue();

            System.out.println("Mainfield: " + key);
            System.out.println("Subfields: ");
        
            if (subTree.root != null) {
                 subTree.inorder(subTree.root, 1);
            } else {
                System.out.println("No subfields ");
            }
            System.out.println("--------------------------------------------");
        }
    }

    // 3. Update the Hash Map
    public void updateMainFieldName(String oldName, String newName) {
        System.out.println("\n>> Update : '" + oldName + "' -> '" + newName + "'");

        if (fieldMap.containsKey(oldName)) {
            // Step 2: Get the data (SubfieldBST) associated with the old key
            SubfieldBST existingData = fieldMap.get(oldName);

            // Step 3: Remove the old entry
            fieldMap.remove(oldName);

            // Step 4: Create a new entry with the new key and old data
            // Note: If newName already exists, it will overwrite.
            if(fieldMap.containsKey(newName)) {
                System.out.println("WARNING: '" + newName + "' already existed! ");
            }
            fieldMap.put(newName, existingData);

            System.out.println("Main field name updated! ");
        } else {
            System.out.println("No field found with the name '" + oldName + "'!");
        }
    }
}