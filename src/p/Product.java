package p;

public class Product {
	private int productId;
	private String name;
	private double price;
	private int stock;
	private AVL<Review> reviews;  //each product has its own reviews
	
	public Product(int productId, String name, double price, int stock) {   //a constructor
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.stock = stock;
		reviews = new AVL<>();	   
	}
	
	public void updateProduct(Product p) {       
	this.name = p.name;
	this.price = p.price;
	this.stock = p.stock;
	}
	
	public int getReviewCount() {
		if(reviews.empty())
			return 0;
		
		return revCount(reviews.getRoot());
	}
	private int revCount(BSTNode<Review> p) {
		if (p == null) 
	        return 0;
	    
	    return 1 + revCount(p.left) + revCount(p.right);
	}
	
	public double getAverageRating() {
	    if (reviews.empty())
	        return 0;

	    int count = getReviewCount();
	    
	    if (count == 0) 
	        return 0;

	    double totalSum = sumRating(reviews.getRoot());
	    return totalSum / count;
	}
	
	private double sumRating(BSTNode<Review> p) {
		if (p == null) 
	        return 0;
	    
	    return p.data.getRating() + sumRating(p.left) + sumRating(p.right);
	}

	public void insertReview(Review r){
	    reviews.insert(r.getReviewID(),r);
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
