package p;

import java.io.File;
import java.util.Scanner;

public class Products {
public  BST<Product> products;
	
	public Products(BST<Product> products) {
		this.products=products;
	}

	public Products() { 
		products = new BST<Product>();
	   
	}

	public void addProduct(Product p) {
			if(products.insert(p.getProductId(),p))      //insert method already checks if the product id(key) was added before or not 
				System.out.println(p.getName()+" is added successfully. Product ID: "+p.getProductId());
			else 
				System.out.println("Product is already added.");
	}
	
	public void removeProduct(int id) {
	    if (products.removeKey(id)) {
	        System.out.println("Product ID: " + id + " is removed successfully.");
	    } else {
	        System.out.println("Product with this Id does not exist.");
	    }
	}
	
	public void updateProduct(int id , Product p) {
		Product prod = searchProductById(id);
		if(prod!=null) {
			prod.updateProduct(p);
			System.out.println("Product is updated successfully into Name: "+p.getName()+" ,Price:"+ p.getPrice()+" ,Stock: "+p.getStock());}
	}
	
	public  Product searchProductById(int id) {
		if(products.empty())
			return null;    //No products in the list
		else {
			if(products.findkey(id))     //sets the current in the desired product
				return products.retrieve();
			return null;
		}
	}
	
	public Product getSearchProductByName(String name) {
	    if (products.empty()) {
	        return null;
	    }
	    return searchProductByName(products.getRoot(), name);
	}
	
	private Product searchProductByName(BSTNode<Product> p,String name) {
		if(p==null)
			return null;
		
		if (p.data.getName().equalsIgnoreCase(name)) 
			return p.data; 
		    
		Product foundInLeft = searchProductByName(p.left, name);
	    if (foundInLeft != null) 
	        return foundInLeft; // If found in the left side, return it immediately
	    
	    return searchProductByName(p.right, name);     //only goes to the right side if not found in the left
	    
	}
	
	public void getOutOfStockProducts() {
		if(products.empty()) {
			System.out.println("No products exist");
			return;
		}
		else {
			System.out.println("Out of stock products:");
			outOfStockProducts(products.getRoot());}
	}

	private void outOfStockProducts(BSTNode<Product>p) {
		if (p==null) 
			return;
		outOfStockProducts(p.left);
		
		if(p.data.getStock()==0) 
			System.out.println(p.data.toString()); 
		
		outOfStockProducts(p.right);

	}
	
	
    public void printProductsByPriceRange(double min, double max) {
        if (products.empty()) {
            System.out.println("No products found.");
            return;
        }
        System.out.println("Products with price between " + min + " and " + max + ":");
        productsByPriceRange(products.getRoot(), min, max);
    }

    private void productsByPriceRange(BSTNode<Product> p, double min, double max) {
        if (p == null) return;
        
        productsByPriceRange(p.left, min, max);
        
        if (p.data.getPrice() >= min && p.data.getPrice() <= max) {
            System.out.println(p.data.toString());
        }
        
        productsByPriceRange(p.right, min, max);
    }
	
	public void loadProducts(String fileName) {
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
					String productInfo[]=line.split(",");
					
					// 1. Get the ID (Key)
					int id = Integer.parseInt(productInfo[0]);
					
					// 2. Create the Product (Value)
					Product p = new Product(id, productInfo[1] ,Double.parseDouble(productInfo[2]) ,Integer.parseInt(productInfo[3]));
					
					products.insert(id, p);
				}
			}
			read.close();
	        System.out.println("Products File loaded successfully.");
		}catch (Exception e) {
			System.out.println("Error in reading file: "+e.getMessage());
		}
	}
	
	public void displayAllProducts() {
		if(products.empty()) {
			System.out.println("No products found. ");
			return;
		}	
		else {
			displayProducts(products.getRoot());
		}
	}
	
	public void displayProducts(BSTNode<Product>p) {		
		if(p==null)
			return;
		displayProducts(p.left);
		
		System.out.println("---------------------------------------------");
		System.out.println(p.data.toString());
		
		displayProducts(p.right);
	}

	public BST<Product> getProducts() {
		return products;
	}

	
	
	
}
