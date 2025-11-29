package p;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ECommerceSystem {
	static Scanner input = new Scanner(System.in);

	static BST<Review> ReviewsTree;
	static BST<Product> ProductsTree;
	static BST<Customer> CustomersTree;
	static BST<Order> OrdersTree;

	static Reviews AllReviews;
	static Products AllProducts;
	static Customers AllCustomers;
	static Orders AllOrders;

	static int nextProductId = 0;
	static int nextOrderId   = 0;
	static int nextReviewId  = 0;
	static int nextCustomerId=0;

	private static final String PRODUCTS_FILE = "dataset/prodcuts.csv";
	private static final String CUSTOMERS_FILE = "dataset/customers.csv";
	private static final String ORDERS_FILE = "dataset/orders.csv";
	private static final String REVIEWS_FILE = "dataset/reviews.csv";
	





	static int getNextProductId() {
		if (nextProductId == 0) {
			try {
				File file = new File(PRODUCTS_FILE);
				Scanner sc = new Scanner(file);

				if (sc.hasNextLine()) sc.nextLine();
				while (sc.hasNextLine()) {
					String line = sc.nextLine().trim();
					if (line.isEmpty()) continue;

					String[] row = line.split(",");
					nextProductId = Integer.parseInt(row[0].replace("\"", "").trim());
				}
				sc.close(); 
				nextProductId++;  
			} catch (Exception e) {
				System.out.println("Error reading product file: " + e.getMessage());
				nextProductId = 1;
			}
		}
		return nextProductId;
	}

	// =======================================
	//	            Order ID
	// =======================================
	static int getNextOrderId() {
		if (nextOrderId == 0) {
			try {
				File file = new File(ORDERS_FILE);
				Scanner sc = new Scanner(file);

				if (sc.hasNextLine()) sc.nextLine();
				while (sc.hasNextLine()) {
					String line = sc.nextLine().trim();
					if (line.isEmpty()) continue;

					String[] row = line.split(",");
					nextOrderId = Integer.parseInt(row[0].replace("\"", "").trim());
				}
				sc.close();
				nextOrderId++;
			} catch (Exception e) {
				System.out.println("Error reading order file: " + e.getMessage());
				nextOrderId = 1;
			}
		}
		return nextOrderId;
	}

	// =======================================
	//	            Review ID
	// =======================================
	static int getNextReviewId() {
		if (nextReviewId == 0) {
			try {
				File file = new File(REVIEWS_FILE);
				Scanner sc = new Scanner(file);

				if (sc.hasNextLine()) sc.nextLine();
				while (sc.hasNextLine()) {
					String line = sc.nextLine().trim();
					if (line.isEmpty()) continue;

					String[] row = line.split(",");
					nextReviewId = Integer.parseInt(row[0].replace("\"", "").trim());
				}
				sc.close();
				nextReviewId++;
			} catch (Exception e) {
				System.out.println("Error reading review file: " + e.getMessage());
				nextReviewId = 1;
			}
		}
		return nextReviewId;
	}

	// =======================================
	//  Customer ID
	//=======================================
	static int getNextCustomerId() {
		if (nextCustomerId == 0) {
			try {
				File file = new File(CUSTOMERS_FILE);
				Scanner sc = new Scanner(file);

				if (sc.hasNextLine()) sc.nextLine();
				int last = 0;

				while (sc.hasNextLine()) {
					String line = sc.nextLine().trim();
					if (line.isEmpty()) continue;

					String[] row = line.split(",");
					int current = Integer.parseInt(row[0].replace("\"", "").trim());
					if (current > last)
						last = current;
				}
				sc.close();
				nextCustomerId = last + 1; 
			} catch (Exception e) {
				System.out.println("Error reading customers file: " + e.getMessage());
				nextCustomerId = 1; 
			}
		}
		return nextCustomerId; 
	}
	public ECommerceSystem() {
		ReviewsTree = new BST<Review>();
		ProductsTree = new BST<Product>();
		CustomersTree = new BST<Customer>();
		OrdersTree = new BST<Order>();

		// creates an objects and initializes its LinkedList

		AllProducts = new Products(ProductsTree);
		AllCustomers = new Customers(CustomersTree);
		AllReviews = new Reviews(ReviewsTree, ProductsTree, CustomersTree); 
		AllOrders = new Orders(CustomersTree, OrdersTree);	}





	public static void main(String[] args) {
		ECommerceSystem ES = new ECommerceSystem();
		ES.ReadData();
		int choice1;
		int choice;   
		System.out.println("-----------------------------------------------");
		System.out.println("*****************Welcome to the E-Commerce System*****************");
		do {
			System.out.println();
			System.out.println("Pick your role: ");
			System.out.println("1- Manager.");
			System.out.println("2- Customer.");
			System.out.println("3- Exit.");
			choice1= input.nextInt();

			switch(choice1) {
			case 1: //Manager
				do {
					System.out.println();
					System.out.println("===============================================");
					System.out.println("1- Add a Product");
					System.out.println("2- Remove product");
					System.out.println("3- Update product");
					System.out.println("4- Update order status");
					System.out.println("5- View Customer Reviews");
					System.out.println("6- Show the Top 3 Most Reviewed or Highest Rated Products");
					System.out.println("7- Show Orders Between Two Dates");
					System.out.println("8- Show Common Products Reviewed by Two Customers(Rated above 4)");
					System.out.println("9- Display all Products");
					System.out.println("10- Display all customers");
					System.out.println("11- Display all orders");
					System.out.println("12- Display all reviews");
					System.out.println("13- Get average product rating");
					System.out.println("14- Search for a product by its name");
					System.out.println("15- track out of stock products");
					System.out.println("16- Display Customers Who Reviewed a Product( Sorted by Customer ID )");
					System.out.println("17- List All Products Within a Price Range.");
					System.out.println("18- Return to main menu.");
					System.out.print("Enter your choice: ");
					choice=input.nextInt();
					System.out.println("===============================================");
					System.out.println();
					switch (choice) {
					case 1:// add product
						Product p;

						int id = getNextProductId();
						System.out.print(" Product ID: "+ id +"\n");

						if(AllProducts.searchProductById(id)!=null) {
							System.out.println("Product with ID:"+id+" Already Exist");
						}
						else {
							input.nextLine();//remove spaces
							System.out.print("Enter Product Name: ");
							String name = input.nextLine(); 
							System.out.print("Enter Product Price: ");
							double price = input.nextDouble();
							System.out.print("Enter Product Stock: ");
							int stock = input.nextInt();

							p = new Product(id, name, price, stock);
							AllProducts.addProduct(p);}
						break;
					case 2:// remove product 
						System.out.println("Enter product id to remove: ");
						id = input.nextInt();

						AllReviews.removeReviewsByProduct(id);  //  remove product's reviews
						AllProducts.removeProduct(id);          // then delete the product
						break;

					case 3:// update product
						System.out.println("Enter product id you want to update: ");
						int prodId = input.nextInt();
						if(AllProducts.searchProductById(prodId)==null) {
							System.out.println("Product with ID:"+prodId+" does not Exist");
						}else {
							System.out.print("Enter new product name : ");
							input.nextLine();
							String name = input.nextLine();
							System.out.print("Enter new stock : ");
							int stock =input.nextInt(); 
							System.out.print("Enter new Price: ");
							double price = input.nextDouble();

							p = new Product(prodId,name , price, stock);
							AllProducts.updateProduct(prodId , p);}
						break;
					case 4: { // Update order status
						System.out.print("Enter Order ID to update its status: ");
						id = input.nextInt();
						input.nextLine(); 
						Order o = AllOrders.SearchOrderByID(id);
						if (o == null) {
							System.out.println("No order found with this ID.");
							break;
						}
						System.out.println("Current status: " + o.getStatus());
						System.out.print("Enter new status: ");
						String newStatus = input.nextLine().trim().toLowerCase();

						if (newStatus.equals("cancelled") || newStatus.equals("canceled")) {
							AllOrders.CancelOrder(id);
							System.out.println("Order moved to CANCELLED.");
							}
						
						o.UpdateOrderStatus(newStatus);
						break;

					}

					case 5: // View Customer Reviews
						System.out.print("Enter Customer ID to view its reviews: ");
						int customerID = input.nextInt();
						input.nextLine(); 

						Customer c = AllCustomers.SearchCustomerById(customerID);
						if (c == null) {
							System.out.println("Customer Not Found!");
							break;
						}

						AllReviews.displayCustomerReviews(customerID, AllProducts);
						break;

					case 6:// Show Top 3 Products (Based on Rating)
						ES.displayTop3Products();
						break;
					case 7:  //Show orders between two dates
					    System.out.print("Enter start date (yyyy-MM-dd): ");
					    LocalDate d1 = LocalDate.parse(input.next());

					    System.out.print("Enter end date (yyyy-MM-dd): ");
					    LocalDate d2 = LocalDate.parse(input.next());

					    displayOrdersBetween2Dates(d1, d2);
					    break;

					case 8:// Show Common Products Reviewed by Two Customers(Rated above 4)

						System.out.print("Enter first customer ID: ");
						int c1 = input.nextInt();
						System.out.print("Enter second customer ID: ");
						int c2 = input.nextInt();

						ES.showCommonProductsAbove4(c1, c2);
						break;


					case 9 : //Display all Products
						AllProducts.displayAllProducts();
						break;

					case 10 : //Display all customers
						AllCustomers.displayAllCustomers();
						break;

					case 11 : //Display all orders
						AllOrders.displayAllOrders();
						break;

					case 12 : //Display all reviews
						AllReviews.displayReview();
						break;

					case 13: // get average rating
						System.out.println("Enter product id: ");
						id= input.nextInt();
						p = AllProducts.searchProductById(id);
						if(p!=null)
							System.out.println("Average rating: "+ p.getAverageRating());
						else
							System.out.println("No product with this Id found");	
						break;
					case 14://search for a product by its name 
						System.out.println("Enter The name of product you want to Search for: ");
						input.nextLine();
						String proName=input.nextLine();
						Product n=AllProducts.getSearchProductByName(proName);
						if(n!= null)
							System.out.println(n);
						else
							System.out.println("No product with this name found");
						break;
					case 15:// show is there out of stock products
						AllProducts.getOutOfStockProducts();
						break;
					case 16://Display Customers Who Reviewed a Product
						System.out.print("Enter product ID: ");
					    int pid = input.nextInt();
					    displayCustomersWhoReviewedProduct(pid);
						break;
					case 17: //List All Products Within a Price Range
					    System.out.print("Enter minimum price: ");
					    double min = input.nextDouble();
					    System.out.print("Enter maximum price: ");
					    double max = input.nextDouble();
					    
					    AllProducts.printProductsByPriceRange(min, max);
					    break;
					case 18:// exit
						System.out.println("Thank you! ");
						System.out.println("===============================================");
						break;
					}// end of manager switch
				}while(choice!=18);


				break; //end of case manager switch1 

			case 2: //customer
				do {
				System.out.println();
				System.out.println("===============================================");
				System.out.println("1- Register");
				System.out.println("2- Place an order");
				System.out.println("3- View order history");
				System.out.println("4- Cancel order");
				System.out.println("5- Add new review or update ");
				System.out.println("6- Get average rating");
				System.out.println("7- Show the Top 3 Most Reviewed or Highest Rated Products");
				System.out.println("8- Return to main menu");
				System.out.print("Enter your choice: ");
				choice=input.nextInt();
				System.out.println("===============================================");
				System.out.println();
				switch(choice) {
				case 1:// add customer
					int id = getNextCustomerId();
					System.out.println("Assigned Customer ID: " + id);
					input.nextLine();//remove spaces
					System.out.print("Enter Customer Name: ");
					String name = input.next(); 
					System.out.print("Enter Customer Email: ");
					String email = input.next();

					Customer C = new Customer(id, name, email);
					AllCustomers.registerNewCustomer(C);
					break;
				case 2: // Place an order

					System.out.print("Enter Customer ID: ");
					int cusID = input.nextInt();
					input.nextLine(); 
					// Allow multiple products separated by ';'
					System.out.print("Enter Product IDs separated by ';' ");
					String proID = input.nextLine();

					id = getNextOrderId();
					System.out.println("Order ID: " + id);
					// Default order status is 'pending'
					String status = "pending";
					// System automatically sets today's date
					java.time.format.DateTimeFormatter fmt =java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String dateStr = java.time.LocalDate.now().format(fmt);

					Order O = new Order(id, cusID, proID, 0.0, dateStr, status);

					// Add the order 
					AllOrders.CreateOrder(O);
					if (AllOrders.SearchOrderByID(id) != null) {
						System.out.println(" Order created successfully!");
						nextOrderId++; 
						} else {
						System.out.println("️ Order not created due to invalid data.");
					}

					break;

				case 3: //view order history
					System.out.println("Enter customer id");
					id= input.nextInt();
					Customer cus = AllCustomers.SearchCustomerById(id);
					if(cus!=null)
						cus.viewOrdersHistory();
					else
						System.out.println("No customer found with this id");	
					break;

				case 4:// cancel order
					System.out.println("Enter order id to cancel");
					id= input.nextInt();
					AllOrders.CancelOrder(id);
					break;
				case 5: // Add or update a review
					// Ask for review details

					id = getNextReviewId();
					System.out.print(" review ID: " + id);


					System.out.print("Enter Customer ID: ");
					cusID = input.nextInt();

					System.out.print("Enter Product ID: ");
					int productID = input.nextInt();

					// Read rating (must be between 1 and 5)
					System.out.print("Enter rating (1..5): ");
					int rating = input.nextInt();
					if (rating < 1 || rating > 5) {
						System.out.println("Invalid rating. Must be between 1 and 5.");
						break;
					}

					input.nextLine();
					System.out.print("Enter comment: ");
					String comment = input.nextLine();

					Review rev = new Review(id, cusID, productID, rating, comment);

					AllReviews.addNewReview(rev);
					break;

				case 6:// get average rating	
					System.out.println("Enter product id");
					id= input.nextInt();
					Product p = AllProducts.searchProductById(id);
					if(p!=null)
						System.out.println("Average rating: "+ p.getAverageRating());
					else
						System.out.println("product with this id not found");
					break;


				case 7:// Show Top 3 Products (Based on Rating)
					ES.displayTop3Products();
					break;

				case 8:// exit
					System.out.println("Thank you!");
					System.out.println("===============================================");
					break;
				}  //end of customer switch choices 
				}while(choice!=8);

				break; //end of case customer switch1
			case 9 :
				AllCustomers.displayAllCustomersAlphabetically();


			case 3: //Exit 
				ES.WriteData();
				System.out.println("Thank you! , Good bye");
				System.out.println("-----------------------------------------------");
				break; //end of exit switch1 
			}  //end of switch1 
			


		}while(choice1 !=3); //end of ' outer do' picking role 

	} // End of main

	public void ReadData() {
		AllProducts.loadProducts(PRODUCTS_FILE);
		AllCustomers.loadCustomers(CUSTOMERS_FILE);
		AllOrders.loadOrders(ORDERS_FILE);
		AllReviews.loadReviews(REVIEWS_FILE, AllProducts); 

		AllOrders.setProducts(AllProducts);
		AllReviews.setProducts(AllProducts); 
		AllReviews.setCustomers(AllCustomers); 
	}

	public void WriteData() {
		System.out.println("\n--- Saving all data to files before exit ---");
		FileManager fm = new FileManager();
		fm.saveAllData(ProductsTree, CustomersTree, OrdersTree, ReviewsTree);
		System.out.println("--- Data saving complete ---");
	}
	

	public void displayTop3Products() {
        if (ProductsTree.empty()) {
            System.out.println("No products in the list.");
            return;
        }

        Product[] top3 = new Product[3]; 
        
        top3[0] = new Product(-1, "-", -1, -1);
        top3[1] = new Product(-1, "-", -1, -1);
        top3[2] = new Product(-1, "-", -1, -1);

        Top3Products(ProductsTree.getRoot(), top3);

        System.out.println("Top Products (Based on Rating and Review Count)");

        if (top3[0].getProductId() != -1)
            System.out.println("Top 1: " + top3[0].getName() + ", ID: " + top3[0].getProductId()
            + ", Avg Rating: " + top3[0].getAverageRating()
            + ", Reviews: " + top3[0].getReviewCount());

        if (top3[1].getProductId() != -1)
            System.out.println("Top 2: " + top3[1].getName() + ", ID: " + top3[1].getProductId()
            + ", Avg Rating: " + top3[1].getAverageRating()
            + ", Reviews: " + top3[1].getReviewCount());

        if (top3[2].getProductId() != -1)
            System.out.println("Top 3: " + top3[2].getName() + ", ID: " + top3[2].getProductId()
            + ", Avg Rating: " + top3[2].getAverageRating()
            + ", Reviews: " + top3[2].getReviewCount());

        if (top3[0].getProductId() == -1) 
            System.out.println("No rated products found.");
    }
	
	private void Top3Products(BSTNode<Product> p, Product[] top3) {
        if (p == null) return;

        Top3Products(p.left, top3);

        Product current = p.data;
        double r = current.getAverageRating();
        int c = current.getReviewCount();

        if (r > 0) {
            if (r > top3[0].getAverageRating() || 
               (r == top3[0].getAverageRating() && c > top3[0].getReviewCount())) {
                top3[2] = top3[1]; 
                top3[1] = top3[0]; 
                top3[0] = current; 
            } 
            else if (r > top3[1].getAverageRating() || 
                    (r == top3[1].getAverageRating() && c > top3[1].getReviewCount())) {
                top3[2] = top3[1]; 
                top3[1] = current; 
            } 
            else if (r > top3[2].getAverageRating() || 
                    (r == top3[2].getAverageRating() && c > top3[2].getReviewCount())) {
                top3[2] = current; 
            }
        }

        // 3. Visit Right
        Top3Products(p.right, top3);
    }


	// to check if any order was found in the range
	private static boolean foundInRange;

	// Display all orders between two dates 
	public static void displayOrdersBetween2Dates(LocalDate d1, LocalDate d2) {
	    if (OrdersTree.empty()) {
	        System.out.println("No orders found.");
	        return;
	    }

	    foundInRange = false; 

	    // start from the root of the Orders BST
	    OrdersBetween2Dates(OrdersTree.getRoot(), d1, d2);

	    if (!foundInRange) {
	        System.out.println("No orders found in this date range.");
	    }
	}
	private static void OrdersBetween2Dates(BSTNode<Order> node, LocalDate d1, LocalDate d2) {
	    if (node == null) return;

	    // left subtree
	    OrdersBetween2Dates(node.left, d1, d2);

	    // check current node
	    LocalDate date = node.data.getOrderDate();
	    if (date.compareTo(d1) >= 0 && date.compareTo(d2) <= 0) {
	        System.out.println("---------------------------------------------");
	        System.out.println(node.data.toString());
	        foundInRange = true;
	    }

	    // right subtree
	    OrdersBetween2Dates(node.right, d1, d2);
	}
	public static void displayCustomersWhoReviewedProduct(int productId) {

	    if (ReviewsTree.empty()) {
	        System.out.println("No reviews found.");
	        return;
	    }

	 // Temporary BST to sort matching reviews by customer ID
	    BST<Review> temp = new BST<>();

	    // Collect all reviews for this product into the temporary tree
	    collectReviewsForProduct(ReviewsTree.getRoot(), productId, temp);

	    if (temp.empty()) {
	        System.out.println("No reviews found for product ID: " + productId);
	        return;
	    }

	    System.out.println("--- Customers who reviewed product " + productId + " (sorted by customer ID) ---");

	 // Print the sorted reviews using in-order traversal
	    printSorted(temp.getRoot());
	}

	private static void collectReviewsForProduct(BSTNode<Review> node, int productId, BST<Review> temp) {
	    if (node == null) return;

	    // Left
	    collectReviewsForProduct(node.left, productId, temp);

	    Review r = node.data;

	    if (r.getProductID() == productId) {
	        
	        temp.insert(r.getCustomerID(), r);
	    }

	    // Right
	    collectReviewsForProduct(node.right, productId, temp);
	}
	private static void printSorted(BSTNode<Review> node) {
	    if (node == null) return;

	    printSorted(node.left);
	    node.data.display();  
	    printSorted(node.right);
	}


	// Show common products reviewed by two customers with Avg > 4
    private void showCommonProductsAbove4(int c1, int c2) {
        if (AllReviews == null || AllProducts == null) {
            System.out.println("Data not loaded!");
            return;
        }
        
        BST<Integer> c1Prods = new BST<>();
        BST<Integer> c2Prods = new BST<>();

        fillCustomerProductLists(ReviewsTree.getRoot(), c1, c2, c1Prods, c2Prods);

        if (c1Prods.empty() || c2Prods.empty()) {
            System.out.println("No common products reviewed by both customers with Avg > 4.");
            return;
        }

        System.out.println("Common products with Avg Rating > 4:");
        findAndPrintIntersection(c1Prods.getRoot(), c2Prods);
    }

    private void fillCustomerProductLists(BSTNode<Review> p, int c1, int c2, 
                                                 BST<Integer> list1, BST<Integer> list2) {
        if (p == null) return;

        fillCustomerProductLists(p.left, c1, c2, list1, list2);

        Review r = p.data;
        // Check if this review belongs to one of the customers
        if (r.getCustomerID() == c1 || r.getCustomerID() == c2) {
            Product prod = AllProducts.searchProductById(r.getProductID());
            
            if (prod != null && prod.getAverageRating() > 4.0) {
                if (r.getCustomerID() == c1) {
                    list1.insert(r.getProductID(), r.getProductID());
                } else {
                    list2.insert(r.getProductID(), r.getProductID());
                }
            }
        }
        fillCustomerProductLists(p.right, c1, c2, list1, list2);
    }

    private void findAndPrintIntersection(BSTNode<Integer> p, BST<Integer> q) {
        if (p == null) return;

        findAndPrintIntersection(p.left, q);

        // Check if this Product ID (p.key) exists in the OTHER customer's list
        if (q.findkey(p.key)) {
            Product match = AllProducts.searchProductById(p.key);
            if (match != null) {
                System.out.println("Product ID: " + match.getProductId()
                        + ", Name: " + match.getName()
                        + ", Avg Rating: " + match.getAverageRating());
            }
        }

        findAndPrintIntersection(p.right, q);
    }


}
