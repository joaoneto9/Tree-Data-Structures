package implementations;

public class Node {

    int value;
    Node left;
    Node right;
    Node parent;

    Node(int n) {
        this.value = n;
    }

    boolean isRight() {
        return this.parent.value <= this.value;
    }

    boolean isLeft() {
        return !isRight();
    }

    boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    boolean hasOnlyLeftChild() {
        return this.left != null && this.right == null;
    }

    boolean hasOnlyRightChild() {
        return this.right != null && this.left == null;
    }

    boolean isRoot() {
        return this.parent == null;
    }

    int balance() {
        return height(this.left) - height(this.right);
    }

    boolean isPendingRight() {
        return balance() <= -1;
    }

    boolean isPendingLeft() {
        return balance() >= 1;
    }

    boolean isBalance() {
        return Math.abs(balance()) <= 1;
    }

    int height() {
        return height(this);
    }

    private int height(Node node) {
        if (node == null)
            return -1;

        return 1 + Math.max(height(node.left), height(node.right));
    }

    public static void main(String[] args) {
        Node node = new Node(10);
        // altura 0

        assert node.height() == 0;

        node.left = new Node(5);

        assert node.balance() == 1; // balance
        assert node.isPendingLeft(); // esta pendendo para a esquerda

        node.right = new Node(15);

        assert node.balance() == 0;
        assert !node.isPendingLeft();
        assert !node.isPendingRight();

        // altura 1

        assert node.height() == 1;
        assert node.left.height() == 0;
        assert node.right.height() == 0;

        node.left.left = new Node(2);

        assert node.balance() == 1;
        assert node.isPendingLeft();
        assert node.left.isPendingLeft();

        node.left.right = new Node(7);

        assert node.balance() == 1;
        assert node.isPendingLeft();
        assert !node.left.isPendingLeft();

        node.right.left = new Node(12);

        assert node.balance() == 0;
        assert !node.isPendingLeft();
        assert node.right.isPendingLeft();

        node.right.right = new Node(17);

        assert node.balance() == 0;
        assert !node.right.isPendingLeft();

        assert node.height() == 2;
        assert node.left.height() == 1;
        assert node.right.height() == 1;
        assert node.left.left.height() == 0;
        assert node.left.right.height() == 0;
        assert node.right.left.height() == 0;
        assert node.right.right.height() == 0;


        System.out.println("PASSOU NOS TESTES!!");
    }
}
