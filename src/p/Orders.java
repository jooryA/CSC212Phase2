package p;

import java.io.File;
import java.util.Scanner;

public class Orders {

    private BST<Order> orders;
	private Customers customers; 
	public Products products;
	
	
	public Orders(BST<Customer> customersTree ,BST<Order> ordersTree) {
		this.orders = ordersTree;
	   this.customers=new Customers(customersTree); 
	}
	public Orders(BST<Customer> customersTree , BST<Order> ordersTree, BST<Product>productsTree) {
	    this.orders = ordersTree;
	    this.customers = new Customers(customersTree);
	    this.products = new Products (productsTree);
	}
	
	public Orders() {
		this.orders = new BST<>();
	    this.customers = new Customers();
	    this.products = new Products();
	}
	
		// Search for an order by its ID 
		public Order SearchOrderByID(int id) {
			// If the orders BST is empty, nothing to search
		    if (orders.empty()) {
		        return null;
		    }
		    if (orders.findkey(id)) {
		        return orders.retrieve();// Returns the Order object if found
		    }
		    // Not found
		    return null;
		}

	
		// Create a new order and insert it into the Orders BST.
		public void CreateOrder(Order ord) {
		    if (ord == null) {
		        System.out.println("Invalid order (null).");
		        return;
		    }

		    // Check for duplicate order ID 
		    if (SearchOrderByID(ord.getOrderId()) != null) {
		        System.out.println("Order already exists with ID: " + ord.getOrderId());
		        return;
		    }

		    //Make sure the customer exists 
		    Customer c = customers.SearchCustomerById(ord.getCustomerId());
		    if (c == null) {
		        System.out.println("Customer not found with ID: " + ord.getCustomerId());
		        return;
		    }

		    // Get the list of product IDs in this order
		    LinkedList<Integer> ids = ord.getProductIds();
		    if (ids.empty()) {
		        System.out.println("Order must contain at least one product.");
		        return;
		    }

		    //check that every product exists and has stock > 0
		    ids.findfirst();
		    while (true) {
		        int pid = ids.retrieve();

		        // Search product in the Products 
		        Product p = products.searchProductById(pid);

		        if (p == null) {
		            System.out.println("Product " + pid + " not found.");
		            return; // Stop if there isn't any product
		        }

		        if (p.getStock() <= 0) {
		            System.out.println("Product " + pid + " is out of stock.");
		            return; // Stop if any product is unavailable
		        }

		        if (ids.last()) break;
		        ids.findnext();
		    }

		    // Calculate total price 
		    double total = OrderTotalPrice(ord);
		    ord.setTotalPrice(total);

		    // Insert the order into the Orders BST and attach it to the customer
		    orders.insert(ord.getOrderId(), ord); 
		    AttachOrderToCustomer(ord); // Link it to the customer

		    // decrease stock for each product in this order
		    ids.findfirst();
		    while (true) {
		        int pid = ids.retrieve();
		        Product p = products.searchProductById(pid);
		        if (p != null) {
		            p.setStock(p.getStock() - 1);
		        }
		        if (ids.last()) break;
		        ids.findnext();
		    }

		}

	public void AttachOrderToCustomer(Order newOrd) {// Every order must be linked to the customer who made it
		Customer c=customers.SearchCustomerById(newOrd.getCustomerId());
		if(c==null)
			System.out.println("No Customers Found");
		else
			c.PlaceOrder(newOrd);	//// Place a new order for this specific customer
	}

	// Cancel an order only if its status is PENDING.
	public void CancelOrder(int id) {

	    // Search for the order using BST 
	    Order o = SearchOrderByID(id);
	    if (o == null) {
	        System.out.println("No order found with ID: " + id);
	        return;
	    }

	    //  Check the current status
	    String status = o.getStatus();

	    // Orders already shipped or delivered cannot be cancelled
	    if (status.equalsIgnoreCase("shipped") || status.equalsIgnoreCase("delivered")) {
	        System.out.println("This order cannot be cancelled because it has already been " + status + ".");
	        return;
	    }

	    // If the order is not pending (already cancelled)
	    if (!status.equalsIgnoreCase("pending")) {
	        System.out.println("Only PENDING orders can be cancelled.");
	        return;
	    }

	    // Update order status
	    o.UpdateOrderStatus("Cancelled");
	    System.out.println("Order has been cancelled successfully.");

	    // Restore stock for all products in this order
	    LinkedList<Integer> productIDs = o.getProductIds();

	    if (productIDs == null || productIDs.empty()) {
	        return; // nothing to restore
	    }

	    productIDs.findfirst();
	    while (true) {
	        int pid = productIDs.retrieve();
	        Product p = products.searchProductById(pid);

	        if (p != null) {
	            p.setStock(p.getStock() + 1); // restore stock
	        }

	        if (productIDs.last()) break;
	        productIDs.findnext();
	    }
	}


	// LinkedList is used to store the products actually purchased in this order.
	private double OrderTotalPrice(Order ord) {
	    double sum = 0.0;
	    if (ord == null) return sum;
	    
	    LinkedList<Integer> ids = ord.getProductIds();
	    if (ids == null || ids.empty()) return sum;

	    ids.findfirst();
	    while (true) {
	        int pid = ids.retrieve();
	        Product p = products.searchProductById(pid);
	        if (p != null) sum += p.getPrice();

	        if (ids.last()) break;
	        ids.findnext();
	    }
	    return sum;
	}

	// Display all orders using in-order traversal.
	public void displayAllOrders() {
	    if (orders.empty()) {
	        System.out.println("No orders found.");
	        return;
	    }

	    // Start from the root of the Orders BST
	    displayOrders(orders.getRoot());
	}

	// Recursive (Left - Node - Right)
	private void displayOrders(BSTNode<Order> node) {
	    if (node == null) {
	        return;
	    }

	    // left subtree
	    displayOrders(node.left);

	    // current node
	    System.out.println("---------------------------------------------");
	    System.out.println(node.data.toString());

	    // right subtree
	    displayOrders(node.right);
	}

	public void loadOrders(String fileName) {
	    try {
	        File file = new File(fileName);
	        Scanner read = new Scanner(file);
	        System.out.println("Loading orders from: " + fileName);
	        
	        if (read.hasNextLine()) {
	            read.nextLine(); // Skip the header line
	        }

	        while (read.hasNextLine()) {
	            String line = read.nextLine().trim();
	            if (!line.isEmpty()) {//read the data 

	            String[] data = line.split(",");

	            int orderId = Integer.parseInt(data[0].trim().replace("\"", ""));
	            int customerId = Integer.parseInt(data[1].trim().replace("\"", ""));
	            String productId = data[2].trim().replace("\"", "");
	            double totalPrice = Double.parseDouble(data[3]);
	            
	            String orderDate = data[4].trim();
	            String status = data[5].trim();

	            Order o = new Order(orderId, customerId, productId, totalPrice, orderDate, status);
	            orders.insert(orderId,o); // Insert order into the BST
	         // Attach this order to the customer so history will include it
	            AttachOrderToCustomer(o);
	        }}

	        read.close();
	        System.out.println("Orders File loaded successfully from file: " + fileName);

	    } catch (Exception e) {
	        System.out.println("Error reading orders file: " + e.getMessage());
	    }
	}
	public Products getProducs() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}
	
	
	
 
}
