package p;
import java.io.File;
import java.util.Scanner;
public class Customers {
	



		private  BST<Customer> Customers;

		public Customers() {
		
			Customers = new BST<>();
		}
		public Customers(BST<Customer> customerTree) {
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
	        inOrderAll(Customers.getRoot());
	       
	    }
		
		private void inOrderAll(BSTNode<Customer> node) {
		    if (node == null) {
		        return;
		    }

		    inOrderAll(node.left);             
		    System.out.println("---------------------------------------------");
		    node.data.display();              
		    inOrderAll(node.right);           
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
		
	}



