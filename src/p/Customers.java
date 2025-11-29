package p;
import java.io.File;
import java.util.Scanner;
public class Customers {
	



		private  AVL<Customer> Customers;

		public Customers() {
		
			Customers = new AVL<>();
		}
		public Customers(AVL<Customer> customerTree) {
		Customers=customerTree;	
		}
		public Customer SearchCustomerById(int id) {
			if(Customers.empty())
				return null;    //No products in the list
			else {
				if(Customers.findkey(id))     //sets the current in the desired product
					return Customers.retrieve();
				return null;
			}	
		}
		
		
		public void registerNewCustomer(Customer c) {
			if(SearchCustomerById(c.getCustomerId())==null) {
				Customers.insert(c.getCustomerId(),c);
				
			System.out.println("Added Successfully, Customer ID: "+ c.getCustomerId());}
			else
				System.out.println("Customer ID: "+c.getCustomerId()+" Already exists");	
			
		}
			
		
		
		public void displayAllCustomers() {
	        if (Customers.empty()) {
	            System.out.println("No customers found");
	            return;
	        }
	        System.out.println("=== All customers ===");
	        inOrderAllCus(Customers.getRoot());
	       
	    }
		
		private void inOrderAllCus(BSTNode<Customer> node) {
		    if (node == null) {
		        return;
		    }

		    inOrderAllCus(node.left);             
		    System.out.println("---------------------------------------------");
		    node.data.display();              
		    inOrderAllCus(node.right);           
		}
		
		
		public void loadCustomers(String fileName) {
			try {
				File file = new File(fileName);
				Scanner read = new Scanner(file);
				System.out.println("File: "+fileName);
				
				if (read.hasNextLine()) {   //for the header if there's one 
		            read.nextLine(); 
		        }
				while(read.hasNextLine()) {
					String line = read.nextLine().trim();
					
					if(!line.isEmpty()) {
						String a[]=line.split(",");
						int id = Integer.parseInt(a[0].trim());
						String name = a[1].trim();
						String email = a[2].trim();
						
						Customer c = new Customer( id , name, email);
						Customers.insert(c.getCustomerId() , c);
					}
				}
				read.close();
		        System.out.println("Customers File loaded successfully.");
			}catch (Exception e) {
				System.out.println("Error in reading file: "+e.getMessage());
			}
		}
		// Root of the temporary BST used for alphabetical sorting by customer name
		private NameNode rootByName = null;
		
		// Internal node class used for building the name based BST
		private static class NameNode {
		    String key;        
		    Customer data;     
		    NameNode left;
		    NameNode right;

		    NameNode(String key, Customer data) {
		        this.key = key;
		        this.data = data;
		    }
		}
		private void buildNameTree() {
		    rootByName = null;
		    fillNameTree(Customers.getRoot());
		}

		private void fillNameTree(BSTNode<Customer> node) {
		    if (node == null) return;

		    fillNameTree(node.left);

		    String key = node.data.getName() + "#" + node.data.getCustomerId();
		    insertByName(key, node.data);

		    fillNameTree(node.right);
		}
		
		private void insertByName(String key, Customer c) {
		    if (rootByName == null) {
		        rootByName = new NameNode(key, c);
		        return;
		    }

		    NameNode current = rootByName;
		    while (true) {       
		    	// Compare alphabetically (case-insensitive)
		        int cmp = key.compareToIgnoreCase(current.key);
		        
	            // Go left if the new key is alphabetically smaller
		        if (cmp < 0) {
		            if (current.left == null) {
		                current.left = new NameNode(key, c);
		                return;
		            }
		            current = current.left;
		            
		            // Go right if the new key is alphabetically larger
		        } else if (cmp > 0) {
		            if (current.right == null) {
		                current.right = new NameNode(key, c);
		                return;
		            }
		            current = current.right;

		        } else {
		            return;
		        }
		    }
		}
		private void inorderByName(NameNode node) {
		    if (node == null) return;

		    inorderByName(node.left);

		    System.out.println("---------------------------------------------");
		    node.data.display();// print the customer info

		    inorderByName(node.right);
		}

		public void displayAllCustomersAlphabetically() {
		    if (Customers.empty()) {
		        System.out.println("No customers found");
		        return;
		    }

		    System.out.println("=== All customers sorted alphabetically ===");
		    buildNameTree();     // Build the alphabetical BST from the main ID based BST

		    inorderByName(rootByName);
		}

		
	}



