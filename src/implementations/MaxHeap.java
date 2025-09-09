package implementations;

import java.util.Arrays;

public class MaxHeap {

    private int[] tree;
    private int tail; // index de adição

    public MaxHeap (int capacity) {
        this.tree = new int[capacity];
        this.tail = -1;
    }

    public int left(int i) {
        return 2 * i + 1;
    }

    public int right(int i) {
        return 2 * (i + 1);
    }

    public int parent(int i) {
        return Math.floorDiv(i - 1, 2);
    }

    public boolean isEmpty() {
        return this.tail == -1;
    }

    public void add(int n) {
        if (tail >= (tree.length - 1))
            resize();

        this.tree[++this.tail] = n; // adiciona depois verifica se precisa trocar
        lookUp(parent(tail), tail); // verificação de trocas e trocas
    }

    private void resize() {
        int[] newTree = new int[this.tree.length * 2];

        for (int i = 0; i < this.tree.length; i++) {
            newTree[i] = this.tree[i];
        }

        this.tree = newTree;
    }

    private void lookUp(int parent, int child) {
        if (parent <= -1 || tree[parent] >= tree[child])
            return;

        swap(parent, child);
        lookUp(parent(parent), parent);
    }

    private void swap(int i, int j) {
        int aux = this.tree[i];
        this.tree[i] = this.tree[j];
        this.tree[j] = aux;
    }

    @Override
    public String toString() {
        return Arrays.toString(tree);
    }

    // just to testing
    public Integer getNode(int i) {
        return this.tree[i] == 0 ? null : this.tree[i];
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(10);

        assert maxHeap.isEmpty();

        maxHeap.add(100);

        assert !maxHeap.isEmpty();
        assert maxHeap.getNode(maxHeap.left(0)) == null;
        assert maxHeap.getNode(maxHeap.right(0)) == null;

        maxHeap.add(90);

        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == null;


        maxHeap.add(80);

        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 80;

        maxHeap.add(70);

        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 80;
        assert maxHeap.getNode(maxHeap.left(maxHeap.left(0))) == 70;
        assert maxHeap.getNode(maxHeap.right(maxHeap.left(0))) == null;

        maxHeap.add(50);

        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 80;
        assert maxHeap.getNode(maxHeap.left(maxHeap.left(0))) == 70;
        assert maxHeap.getNode(maxHeap.right(maxHeap.left(0))) == 50;

        maxHeap.add(100);


        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 100;
        assert maxHeap.getNode(maxHeap.left(maxHeap.left(0))) == 70;
        assert maxHeap.getNode(maxHeap.right(maxHeap.left(0))) == 50;
        assert maxHeap.getNode(maxHeap.left(maxHeap.right(0))) == 80;

        maxHeap.add(120); // esta funcionando corretamente

        assert maxHeap.getNode(0) == 120;
        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 100;
        assert maxHeap.getNode(maxHeap.left(maxHeap.left(0))) == 70;
        assert maxHeap.getNode(maxHeap.right(maxHeap.left(0))) == 50;
        assert maxHeap.getNode(maxHeap.left(maxHeap.right(0))) == 80;
        assert maxHeap.getNode(maxHeap.right(maxHeap.right(0))) == 100;

        System.out.println("PASSOU NOS TESTES!!!");
        System.out.println(maxHeap);
    }


}
