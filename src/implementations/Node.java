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
        return this.parent.value < this.value;
    }

    boolean isLeft() {
        return !isRight();
    }


}
