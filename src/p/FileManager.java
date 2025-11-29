package p;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

// NOTE: Assumes BST<T> exposes a getRoot() method and BSTNode<T> is available.

public class FileManager {
    
    private static final String PRODUCTS_FILE = "dataset/prodcuts.csv";
    private static final String CUSTOMERS_FILE = "dataset/customers.csv";
    private static final String ORDERS_FILE = "dataset/orders.csv";
    private static final String REVIEWS_FILE = "dataset/reviews.csv";

    /**
     * Public method to call all save functions, passing the BSTs for all collections.
     */
    public void saveAllData(AVL<Product> products, 
                            AVL<Customer> customers,
                            AVL<Order> orders, 
                            AVL<Review> reviews) { // All four are now BSTs
        
    	saveAVL(PRODUCTS_FILE, "productId,name,price,stock", products);
    	saveAVL(CUSTOMERS_FILE, "customerId,name,email", customers);
    	saveAVL(ORDERS_FILE, "orderId,customerId,productIds,totalPrice,orderDate,status", orders);
        saveAVL(REVIEWS_FILE, "reviewID,productID,customerID,rating,comment", reviews);
    }
        
    /**
     * [BST METHOD] Generic method to save any BST using recursive In-Order Traversal.
     */
    private <T> void saveAVL(String fileName, String header, AVL<T> tree) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, false))) {
            
            pw.println(header); 
            
            if (tree.empty()) return; 
            
            // Start the recursive In-Order traversal from the tree's root
            saveInOrder(tree.getRoot(), pw);

        } catch (IOException e) {
            System.err.println("Error writing data to file " + fileName + ": " + e.getMessage());
        } catch (Exception e) {
             System.err.println("Error processing data in FileManager for " + fileName + ": " + e.getMessage());
        }
    }
    
    /**
     * Recursive helper method for In-Order Traversal (Left -> Root -> Right).
     */
    private <T> void saveInOrder(BSTNode<T> p, PrintWriter pw) {
        if (p == null) {
            return;
        }

        // 1. Visit Left Subtree
        saveInOrder(p.left, pw);
        
        // 2. Visit Root (Save the data)
        Object item = p.data;
        String csvLine = "";
        
        // Determine the correct toCSV() method based on object type
        if (item instanceof Customer) {
            csvLine = ((Customer) item).toCSV();
        } else if (item instanceof Product) {
            csvLine = ((Product) item).toCSV();
        } else if (item instanceof Order) {
            csvLine = ((Order) item).toCSV();
        } else if (item instanceof Review) { // Review is also saved recursively now
            csvLine = ((Review) item).toCSV();
        } 
        
        if (!csvLine.isEmpty()) {
             pw.println(csvLine);
        }

        // 3. Visit Right Subtree
        saveInOrder(p.right, pw);
    }
}