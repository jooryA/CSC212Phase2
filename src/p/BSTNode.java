package p;

	public class BSTNode <T> {
		public int key;        
	    public T data;          
	    public BSTNode<T> left;
	    public BSTNode<T> right;
	    
	    
	    public BSTNode(int key, T data) {
	        this.key = key;
	        this.data = data;
	        this.left = null;
	        this.right = null;
	    }

	}
	