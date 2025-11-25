package p;

	import java.io.File;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.util.Scanner;
	public class Reviews {

	

		private LinkedList<Review> reviews;
		private Products products;
		private Customers Customers;
		
		
		public Reviews() {
			reviews = new LinkedList<>();
			products = new Products();
			Customers = new Customers();

		}
		public Reviews(LinkedList<Review> reviews, Products products, Customers customers) {
	        this.reviews = reviews;
	        this.products = products;
	        this.Customers = customers;
	    }
		
		public Reviews(LinkedList<Review> reviews, LinkedList<Product> Tproducts, LinkedList<Customer> Tcustomers) {

			this.reviews = reviews;
			products = new Products(Tproducts);
			Customers = new Customers(Tcustomers);
		}

		

		public Review searchReviewtById(int id) {
			if (reviews.empty())
				return null; // No Reviews in the list
			else {
				reviews.findfirst();
				while (!reviews.last()) { // Looping from the first Review till the one before the last
					if (reviews.retrieve().getReviewID() == id)
						return reviews.retrieve();
					reviews.findnext();
				}

				if (reviews.retrieve().getReviewID() == id) // For the last Review in the list
					return reviews.retrieve();

				return null; // If there's no Review in the list matches the id
			}
		}

		public void AttachReviewToproduct(Review r) {
			Product p = products.searchProductById(r.getProductID());
			if (p == null)
				System.out.println("Product not found for review " + r.getReviewID());
			else
				p.insertReview(r);
			;
		}

		public void AttachReviewToCustomer(Review r) {
			Customer c = Customers.SearchCustomerById(r.getCustomerID());
			if (c == null)
				System.out.println("Customer not found for review " + r.getReviewID());
			else
				c.addReview(r);
		}

		// Add a new review
		public void addNewReview(Review R) {
		    Review existing = searchReviewtById(R.getReviewID());

		    if (existing != null) {
		        // Review already exists ,update it
		        existing.updateReview(R);
		        System.out.println("Review ID " + R.getReviewID() + " updated successfully.");
		        return;
		    }

		    // Validate customer and product before adding new one
		    Product p = products.searchProductById(R.getProductID());
		    if (p == null) {
		        System.out.println("Product not found: " + R.getProductID());
		        return;
		    }

		    Customer c = Customers.SearchCustomerById(R.getCustomerID());
		    if (c == null) {
		        System.out.println("Customer not found: " + R.getCustomerID());
		        return;
		    }

		    // Add new review and link it to the customer and product
		    reviews.insert(R);
		    p.insertReview(R);
		    c.addReview(R);
		    System.out.println("Added Successfully, Review ID: " + R.getReviewID());
		}


		public void updateReview(int id, Review p) {
			Review rev = searchReviewtById(id);
			if (rev != null) {
				rev.updateReview(p);
				System.out.println("Review is updated successfully ");
			} else
				System.out.println("Review with this Id does not exist.");
		}

		public void displayReview() {
			if (reviews.empty()) {
				System.out.println("No Reviews found ");
				return;
			}

			reviews.findfirst();
			while (!reviews.last()) { // display all Reviews until the last Review ( last one not included)
				Review d = reviews.retrieve();
				System.out.println("---------------------------------------------");
				d.display();
				reviews.findnext();
			}
			System.out.println("---------------------------------------------");
			Review d = reviews.retrieve();// displays the last Review
			d.display();
		}
		//display reviews for all products for a specific customer
		public void displayCustomerReviews(int customerId, Products productList) {
		    if (reviews.empty()) {
		        System.out.println("No reviews in the system yet.");
		        return;
		    }

		    int count = 0;
		    reviews.findfirst();
		    while (true) {
		        Review r = reviews.retrieve();
		        if (r != null && r.getCustomerID() == customerId) {//check if this review actually belong to this specific customer
		            count++;
		            int pid = r.getProductID();// Get product information
		            Product p = (productList != null) ? productList.searchProductById(pid) : null;

		            System.out.println( "Product: " + pid +(p != null ? " - " + p.getName() : "") +" ,Rating: " + r.getRating() +" ,Comment: " + r.getComment());
		        }

		        if (reviews.last()) break;
		        reviews.findnext();
		    }

		    if (count == 0) {
		        System.out.println("No reviews found for customer ID: " + customerId);
		    }
		}


		public LinkedList<Review> getReviews() {
			return reviews;
		}

		public void setReviews(LinkedList<Review> reviews) {
			this.reviews = reviews;
		}

		public Products getProducts() {
			return products;
		}

		public void setProducts(Products products) {
			this.products = products;
		}

		public Customers getCustomers() {
			return Customers;
		}

		public void setCustomers(Customers customers) {
			Customers = customers;
		}
		// Remove all reviews that belong to a given product
		public void removeReviewsByProduct(int productId) {
		    if (reviews == null || reviews.empty()) return;
		    reviews.findfirst();
		    while (true) {
		        Review r = reviews.retrieve();
		        if (r != null && r.getProductID() == productId) {
		            reviews.remove();                 // delete current review
		            if (reviews.empty()) break;       // list became empty
		        } else {
		            if (reviews.last()) break;
		            reviews.findnext();
		        }
		    }
		}

		
		
		public void loadReviews(String fileName,Products productList) {
		    try {
		        File file = new File(fileName);
		        Scanner read = new Scanner(file);
		        System.out.println("Reading File: " + fileName);
		        
		        if (read.hasNextLine()) {
		            read.nextLine(); // Skip the header line
		        }

		        while (read.hasNextLine()) {
		            String line = read.nextLine().trim();
		            if (!line.isEmpty()) {//read the data 

		            String[] a = line.split("," , 5);
		            int reviewID= Integer.parseInt(a[0].trim());
		            int customerID =Integer.parseInt(a[2].trim());
		            int productID = Integer.parseInt(a[1].trim());
		            int rating = Integer.parseInt(a[3].trim());
		            String comment = a[4];


		        	Review r= new Review( reviewID,  customerID,  productID,  rating, comment);
		            reviews.insert(r);
		            
		            Product p = productList.searchProductById(productID);
	                if (p != null) {
	                    p.insertReview(r);
	                } else {
	                    System.out.println("Product " + productID + " not found for review " + reviewID);
	                }

		           	        }}

		        read.close();
		        System.out.println("Reviews File loaded successfully from file: " + fileName);

		    } catch (Exception e) {
		        System.out.println("Error reading Reviews file: " + e.getMessage());
		    }
		}
	}

