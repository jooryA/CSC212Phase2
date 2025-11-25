package p;


public class Customer {
		private int CustomerId;
		private String name;
		private String email;
		private LinkedList<Order> orders;
		private LinkedList<Review> reviews;
		
		public Customer(int customerId, String name, String email) {
			
			CustomerId = customerId;
			this.name = name;
			this.email = email;
			orders = new LinkedList<>();
			reviews = new LinkedList<>();

		}
		public void PlaceOrder(Order order) {// Place a new order for this specific customer
			orders.insert(order);
		}
		public void addReview(Review r) {
			reviews.insert(r);
		}
		public void viewOrdersHistory() { 
			if(!orders.empty()) {
				System.out.println("Orders for Customer : "+name );
				orders.findfirst();
				while(!orders.last()) {// display all orders until the last order ( last one not included)
					System.out.println(orders.retrieve().toString());
					orders.findnext();
				}
				System.out.println(orders.retrieve().toString());// displays the last order
			}else
			System.out.println("No Orders Found for Customer : "+name);
		}
		
		
		
		public int getCustomerId() {
			return CustomerId;
		}
		public void setCustomerId(int customerId) {
			CustomerId = customerId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		public String toCSV() {
		    return CustomerId + "," + name + "," + email;
		}
		
	public void display() {
			System.out.println("Customer ID :" + CustomerId +"\nname  :" + name+"\nemail :" + email);
		}
		


	}
