package p;

public class Product {
	private int productId;
	private String name;
	private double price;
	private int stock;
	private LinkedList<Review> reviews;  //each product has its own reviews
	
	public Product(int productId, String name, double price, int stock) {   //a constructor
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.stock = stock;
		reviews = new LinkedList<>();	   
	}
	
	public void updateProduct(Product p) {       
	this.name = p.name;
	this.price = p.price;
	this.stock = p.stock;
	}
	
	public double getAverageRating() {
		if(reviews.empty())
			return 0;
		double sum = 0;
		int count =0;
		reviews.findfirst();
		while(!reviews.last()) {
			sum+=reviews.retrieve().getRating();
			count++;
			reviews.findnext();
		}
		sum+=reviews.retrieve().getRating();
		count++;
		
		return sum/count;
	}
	// Returns how many reviews this product has
	public int getReviewCount() {
	    if (reviews == null || reviews.empty())
	        return 0;

	    int count = 0;
	    reviews.findfirst();
	    while (true) {
	        if (reviews.retrieve() != null)
	            count++;

	        if (reviews.last())
	            break;
	        reviews.findnext();
	    }

	    return count;
	}

	public void insertReview(Review r){
	    reviews.insert(r);
	}
	
	public int getProductId() {
		return productId;
	}


	public String getName() {
		return name;
	}

	public int getStock() {
		return stock;
	}

	public double getPrice() { return price; }

	
	public void setStock(int stock) {
		this.stock = stock;
	}

	public String toCSV() {
	    return productId + "," + name + "," + price + "," + stock;
	}
	public String toString() {
		return "Product Id: " + productId + "\nname: " + name + "\nprice: " + price + "\nstock: " + stock;
	}
	
	
	
}
