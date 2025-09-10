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
        int rightHeight = this.right == null ? -1 : this.right.height();
        int leftHeight = this.left == null ? -1 : this.left.height();

        return rightHeight - leftHeight;
    }

    boolean isPendingRight() {
        return balance() >= -1;
    }

    boolean isPendingLeft() {
        return balance() <= 1;
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
}
