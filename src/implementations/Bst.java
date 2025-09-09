package implementations;

public class Bst {

    private Node root;
    
    public boolean isEmpty() {
        return this.root == null;
    }

    public void add(int n) {
        if (isEmpty())
            this.root = new Node(n);
        else add(n, this.root);
    } 

    private void add(int n, Node node) {
        if (node == null) {
            node = new Node(n);
            return;
        }

        // n deve ser adicionado a direita do no
        if (n > node.value) {
            add(n, node.right);
            return;
        }
        
        // n deve ser adicionado a esquerda do no
        add(n, node.left);
    }

    public int height() {
        return height(this.root);
    }

    private int height(Node node) {
        if (node == null)
            return -1;

        return 1 + Math.max(height(node.left), height(node.right));
    }

    public static void main(String[] args) {
        Bst bst = new Bst();

        assert bst.height() == -1;
        assert bst.isEmpty();

        bst.add(10);

        assert bst.height() == 0;
        assert !bst.isEmpty();

        bst.add(5);

        assert bst.height() == 1;
        
        bst.add(15);

        assert bst.height() == 1;

        bst.add(2);

        assert bst.height() == 2;

        System.out.println("PASSOU NOS TESTES!!");
    }


}
