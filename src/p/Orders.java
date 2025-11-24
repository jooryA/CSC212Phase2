package p;

import java.io.File;



public class Orders {
/*
    private BST<Order> orders;
	private Customers customers; 
	public Products productList;
	
	
	public Orders(BST<Customer> customers ,BST<Order> orders) {
		this.orders = orders;
	   this.customers=new Customers(customers); 
	}
	public Orders(BST<Customer> customers , BST<Order> orders, BST<Product>productList) {
	    this.orders = orders;
	    this.customers = new Customers(customers);
	    this.productList = new Products (productList);
	}
	
	public Orders() {
		customers=new Customers(); 
		orders=new BST<>();
	
	}
	public Order SearchOrderByID(int id) {
		if(!orders.empty()) { // if it is not empty check orders
		
			boolean Found=orders.findkey(id);
			if(Found)
			return orders.retrieve();
			else
			return null;
		}
		return null; //no order found with same ID	
	}
	
	public void CreateOrder(Order ord) {
	    // Check for duplicate order ID
	    if (SearchOrderByID(ord.getOrderId()) != null) {
	        System.out.println("Order already exists!");
	        return;
	    }

	    //  Make sure main lists are ready before proceeding
	    if (customers == null) {
	        System.out.println("Customers list is not initialized");
	        return;
	    }
	    if (productList == null) {
	        System.out.println("Products list is not initialized");
	        return;
	    }

	    //  Validate that the customer exists in the system
	    Customer c = customers.SearchCustomerById(ord.getCustomerId());
	    if (c == null) {
	        System.out.println("Customer not found: " + ord.getCustomerId());
	        return;
	    }

	    // Make sure every product in this order exists and is in stock
	    LinkedList<Integer> ids = ord.getProductIds();
	    if (!ids.empty()) {
	        ids.findfirst();
	        while (true) {
	            int pid = ids.retrieve();
	            Product p = productList.searchProductById(pid);

	            if (p == null) {
	                System.out.println("Product " + pid + " not found.");
	                return; // Stop immediately if any product doesn’t exist
	            }

	            if (p.getStock() <= 0) {
	                System.out.println("Product " + pid + " is out of stock.");
	                return; // Stop immediately if any product is unavailable
	            }

	            if (ids.last()) break;
	            ids.findnext();
	        }
	    }
	 // Calculate total price 
	    double total = OrderTotalPrice(ord);
	    ord.setTotalPrice(total);

	    
	    // Add the order, link it to the customer, and then decrement product stock
	    orders.insert(ord.getOrderId(),ord);
	    AttachOrderToCustomer(ord);
	    System.out.println("Added Successfully, Order ID: " + ord.getOrderId());
	    System.out.println("Total: " + total + " , Date: " + ord.getOrderDate());

	    // decrement stock for each product in the order
	    if (!ids.empty()) {
	        ids.findfirst();
	        while (true) {
	            int pid = ids.retrieve();
	            Product p = productList.searchProductById(pid);
	            p.setStock(p.getStock() - 1); // Decrease stock 
	            if (ids.last()) break;
	            ids.findnext();
	        }
	    }
	}

	public void AttachOrderToCustomer(Order newOrd) {// Every order must be linked to the customer who made it
		Customer c=customers.SearchCustomerById(newOrd.getCustomerId());
		if(c==null)
			System.out.println("No Customers Found");
		else
			c.PlaceOrder(newOrd);	//// Place a new order for this specific customer
	}

	public void CancelOrder(int id) {
	    
	    Order o = SearchOrderByID(id);
	    if (o == null) {
	        System.out.println("No order found with this ID to cancel");
	        return;
	    }

	    String status = o.getStatus();

	    if (status.equalsIgnoreCase("shipped") || status.equalsIgnoreCase("delivered")) {
	        System.out.println("Cannot cancel this order. It’s already " + status + ".");
	        return;
	    }

	    if (!status.equalsIgnoreCase("pending")) {
	        System.out.println("Only PENDING orders can be cancelled (current: " + status + ").");
	        return;
	    }

	    //update status to cancelled
	    o.UpdateOrderStatus("Cancelled");
	    System.out.println(" Order " + id + " has been cancelled successfully.");

	    if (productList == null) {
	        System.out.println("Products list is not initialized.");
	        return;
	    }

	    LinkedList<Integer> productIDs = o.getProductIds();
	    if (!productIDs.empty()) {
	        productIDs.findfirst();
	        while (true) {
	            int pid = productIDs.retrieve();
	            Product p = productList.searchProductById(pid);
	            if (p != null) {
	                p.setStock(p.getStock() + 1); 
	            }
	            if (productIDs.last()) break;
	            productIDs.findnext();
	        }
	    }
	}


	private double OrderTotalPrice(Order ord) {
	    double sum = 0.0;
	    if (ord == null) return sum;
	    
	    LinkedList<Integer> ids = ord.getProductIds();
	    if (ids == null || ids.empty()) return sum;

	    ids.findfirst();
	    while (true) {
	        int pid = ids.retrieve();
	        Product p = productList.searchProductById(pid);
	        if (p != null) sum += p.getPrice();

	        if (ids.last()) break;
	        ids.findnext();
	    }
	    return sum;
	}

		public void displayOrders() {
		    if (orders.empty()) {
		        System.out.println("No orders found ");
		        return;
		    }

		    orders.findfirst();
		    while (!orders.last()) {
		        Order ord = orders.retrieve();
		        System.out.println("---------------------------------------------");
		        System.out.println(ord.toString());

		        //alert the user there are some products were deleted
		        if (productList != null) {
		            LinkedList<Integer> ids = ord.getProductIds();
		            if (ids != null && !ids.empty()) {
		                ids.findfirst();
		                boolean foundDeleted = false;
		                while (true) {
		                    Product p = productList.searchProductById(ids.retrieve());
		                    if (p == null) { foundDeleted = true; break; }
		                    if (ids.last()) break;
		                    ids.findnext();
		                }
		                if (foundDeleted)
		                    System.out.println("Some products in this order are no longer available..");
		            }
		        }

		        orders.findnext();
		    }

		    System.out.println("---------------------------------------------");
		    Order ord = orders.retrieve();
		    System.out.println(ord.toString());
		}


	public BST<Order> getOrders() {
		return orders;
	}
	public void loadOrders(String fileName) {
	    try {
	        File file = new File(fileName);
	        Scanner read = new Scanner(file);
	        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-M-d");
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
	            orders.insert(orderId,o); // Add order to LinkedList
	        }}

	        read.close();
	        System.out.println("Orders File loaded successfully from file: " + fileName);

	    } catch (Exception e) {
	        System.out.println("Error reading orders file: " + e.getMessage());
	    }
	}
	public Products getProductList() {
		return productList;
	}
	public void setProductList(Products productList) {
		this.productList = productList;
	}
	
	
	
 */
}
