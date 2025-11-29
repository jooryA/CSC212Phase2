package p;


public class AVL <T> {
	private BSTNode<T> root;
    private BSTNode<T> current;

    public AVL() {
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
        
        public boolean removeKey(int k) {
        	BSTNode<T> p = root;
        	BSTNode<T> q = null; 
            
            while (p != null && p.key != k) {
                q = p;
                if (k < p.key) {
                    p = p.left;
                } else {
                    p = p.right;
                }
            }

            if (p == null) {
                return false;
            }

            if (p.left != null && p.right != null) {

            	BSTNode<T> min = p.right;
            	BSTNode<T> qMin = p;
                
                while (min.left != null) {
                    qMin = min;
                    min = min.left;
                }

                p.key = min.key;
                p.data = min.data;

                p = min;
                q = qMin;
            }

            BSTNode<T> child;
            if (p.left != null) {
                child = p.left;
            } else {
                child = p.right;
            }

            if (q == null) {
                root = child; 
            } else {
                if (q.left == p) {
                    q.left = child;
                } else {
                    q.right = child;
                }
            }
            
            current = root; 
            return true;
        }
        
      
    	
       

        public BSTNode<T> getRoot(){
        	return root;
        }
        
        
        private int height(BSTNode<T> node) {
            if (node == null) return 0;
            return 1 + Math.max(height(node.left), height(node.right));
        }
        private int balanceFactor(BSTNode<T> node) {
            if (node == null) return 0;
            return height(node.right) - height(node.left);
        }
        private BSTNode<T> rotateLeft(BSTNode<T> y) {
        	BSTNode<T> x = y.right;
        	BSTNode<T> T2 = x.left;

            x.left = y;
            y.right = T2;

            return x;
        }
        private BSTNode<T> rotateRight(BSTNode<T> y ) {
        	BSTNode<T> x = y.left;
        	BSTNode<T> T2 = x.right;

            x.right = y;
            y.left = T2;
            return x;
        }
        private BSTNode<T>  rotateLeftRight(BSTNode<T>  node) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        private BSTNode<T>  rotateRightLeft(BSTNode<T>  node) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        private BSTNode<T> rebalance(BSTNode<T> node) {
            int bf = balanceFactor(node);
          
            if (bf > 1) {
                if (balanceFactor(node.right) >= 0)
                    return rotateLeft(node);       // RR
                else
                    return rotateRightLeft(node);  // RL
            }

        
            if (bf < -1) {
                if (balanceFactor(node.left) <= 0)
                    return rotateRight(node);      // LL
                else
                    return rotateLeftRight(node);  // LR
            }

            return node; 
        }
        
        
        public boolean insertm(int k, T val) {
            if (findkey(k)) return false; // key exists
            root = insertAVL(root, k, val);
            return true;
        }


    private BSTNode<T> insertAVL(BSTNode<T>node, int k, T val) {
        if (node == null)
            return new BSTNode<T>(k, val);

        if (k<node.key)
            node.left = insertAVL(node.left, k, val);
        else
            node.right = insertAVL(node.right, k, val);

        
        return rebalance(node);
    }
        
    }



