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
}
