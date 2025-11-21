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
class BST<T> {

    private BSTNode<T> root;
    private BSTNode<T> current;

    public BST() {
        root = current = null;}
        
        public boolean empty() {
            return root == null;
        }

        public boolean full() {
            return false;
        }

        public T retrieve() {
            return current.data;
        }
        
        public boolean findkey(int tkey) {
            BSTNode<T> p = root;
            	


            while (p != null) {
            	current=p;
                if (tkey == p.key) {
                  
                    return true;
                } else if (tkey < p.key) {
                    p = p.left;
                } else {
                    p = p.right;
                }
            }

            return false;}
            
            
        public boolean insert(int k, T val) {
            if (root == null) {
                current = root = new BSTNode<T>(k, val);
                return true;
            }

            BSTNode<T> p = current;
            if (findkey(k)) {
                current = p;
                return false;
            }

            BSTNode<T> tmp = new BSTNode<T>(k, val);
            if (k < current.key) {
                current.left = tmp;
            } else {
                current.right = tmp;
            }
            current = tmp;
            return true;
        }
       

        
        
        
        
        
    }