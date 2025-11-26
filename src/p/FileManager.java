package p;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class FileManager {
    
    private static final String PRODUCTS_FILE = "dataset/prodcuts.csv";
    private static final String CUSTOMERS_FILE = "dataset/customers.csv";
    private static final String ORDERS_FILE = "dataset/orders.csv";
    private static final String REVIEWS_FILE = "dataset/reviews.csv";

    
    public void saveAllData(BST<Product> products, 
                            BST<Customer> customers,
                            BST<Order> orders, 
                            LinkedList<Review> reviews) { 
        
        saveBST(PRODUCTS_FILE, "productId,name,price,stock", products);
        saveBST(CUSTOMERS_FILE, "customerId,name,email", customers);
        saveBST(ORDERS_FILE, "orderId,customerId,productIds,totalPrice,orderDate,status", orders);
        
        saveLinkedList(REVIEWS_FILE, "reviewID,productID,customerID,rating,comment", reviews);
    }
        
   
    private <T> void saveBST(String fileName, String header, BST<T> tree) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, false))) {
            
            pw.println(header); 
            
            if (tree.empty()) return; 
            
            saveInOrder(tree.getRoot(), pw);

        } catch (IOException e) {
            System.err.println("Error writing data to file " + fileName + ": " + e.getMessage());
        } catch (Exception e) {
             System.err.println("Error processing data in FileManager for " + fileName + ": " + e.getMessage());
        }
    }
    
   
    private <T> void saveLinkedList(String fileName, String header, LinkedList<T> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, false))) {
            
            pw.println(header); 
            
            if (list.empty()) return; 
            
            list.findfirst();
            while (true) {
                Object item = list.retrieve();
                
                // Determine the correct toCSV() method based on object type
                String csvLine = "";
                if (item instanceof Review) {
                    csvLine = ((Review) item).toCSV(); // Reviews are the specific T here
                } else {
                    csvLine = item.toString(); // Fallback if necessary
                }

                if (!csvLine.isEmpty()) {
                    pw.println(csvLine);
                }

                if (list.last()) break;
                list.findnext();
            }
        } catch (IOException e) {
            System.err.println("Error writing data to file " + fileName + ": " + e.getMessage());
        } catch (Exception e) {
             System.err.println("Error processing data in FileManager for " + fileName + ": " + e.getMessage());
        }
    }
    
   
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
        } 
        
        if (!csvLine.isEmpty()) {
             pw.println(csvLine);
        }

        // 3. Visit Right Subtree
        saveInOrder(p.right, pw);
    }
}

