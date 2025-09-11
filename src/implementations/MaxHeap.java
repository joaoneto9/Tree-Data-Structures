package implementations;

import java.util.Arrays;

public class MaxHeap {

    private int[] tree;
    private int tail; // index de adição

    public MaxHeap (int capacity) {
        this.tree = new int[capacity];
        this.tail = -1;
    }

    public MaxHeap (int[] array) {
        this.tree = array;
        this.tail = array.length - 1;
        this.buildHeap();
    }

    // começa pelo pai do ultimo nó e realiza o Heapfy até a raiz
    private void buildHeap() {
        for (int i = parent(this.tail); i >= 0; i--) {
            heapify(i);
        }
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

    // sempre remove a raiz
    public void remove() {
        swap(0, this.tail); // realiza o swap do ultimo elemento adicionado e a raiz (max)
        this.tail--; // diminui o indice do tail, pois ele não esta mais no heap
        heapify(0);
    }

    private void heapify(int index) {
        if (isLeaf(index) || !isValidIndex(index))
            return; // para se for indices invalidos

        int maxIndex = maxIndex(index, left(index), right(index));

        // existe um maior que o index
        if (maxIndex != index) {
            swap(index, maxIndex);
            heapify(maxIndex); // chama recursivo para o elemente que realizou o swap (inicial)
        }

    }

    private int maxIndex(int index, int left, int right) {
        if (this.tree[index] > this.tree[left]) {
            if (isValidIndex(right)) {
                if (this.tree[index] < this.tree[right])
                    return right;
            }

            return index;

        } else {
            if (isValidIndex(right)) {
                if (this.tree[left] < this.tree[right])
                    return right;
            }

            return left;
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index <= tail;
    }

    private boolean isLeaf(int index) {
        return index > parent(tail) && index <= tail;
    }

    @Override
    public String toString() {
        return Arrays.toString(tree);
    }

    // just to testing
    public Integer getNode(int i) {
        return this.tree[i] == 0 ? null : this.tree[i];
    }

    public int getPeek() {
        return this.tree[0];
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(10);

        assert maxHeap.isEmpty();

        maxHeap.add(100);

        assert !maxHeap.isEmpty();
        assert maxHeap.getNode(maxHeap.left(0)) == null;
        assert maxHeap.getNode(maxHeap.right(0)) == null;
        assert maxHeap.getPeek() == 100;

        maxHeap.add(90);

        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == null;
        assert maxHeap.getPeek() == 100;

        maxHeap.add(80);

        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 80;
        assert maxHeap.getPeek() == 100;

        maxHeap.add(70);

        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 80;
        assert maxHeap.getNode(maxHeap.left(maxHeap.left(0))) == 70;
        assert maxHeap.getNode(maxHeap.right(maxHeap.left(0))) == null;
        assert maxHeap.getPeek() == 100;

        maxHeap.add(50);

        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 80;
        assert maxHeap.getNode(maxHeap.left(maxHeap.left(0))) == 70;
        assert maxHeap.getNode(maxHeap.right(maxHeap.left(0))) == 50;
        assert maxHeap.getPeek() == 100;

        maxHeap.add(100);


        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 100;
        assert maxHeap.getNode(maxHeap.left(maxHeap.left(0))) == 70;
        assert maxHeap.getNode(maxHeap.right(maxHeap.left(0))) == 50;
        assert maxHeap.getNode(maxHeap.left(maxHeap.right(0))) == 80;
        assert maxHeap.getPeek() == 100;

        maxHeap.add(120); // esta funcionando corretamente

        assert maxHeap.getNode(0) == 120;
        assert maxHeap.getNode(maxHeap.left(0)) == 90;
        assert maxHeap.getNode(maxHeap.right(0)) == 100;
        assert maxHeap.getNode(maxHeap.left(maxHeap.left(0))) == 70;
        assert maxHeap.getNode(maxHeap.right(maxHeap.left(0))) == 50;
        assert maxHeap.getNode(maxHeap.left(maxHeap.right(0))) == 80;
        assert maxHeap.getNode(maxHeap.right(maxHeap.right(0))) == 100;
        assert maxHeap.getPeek() == 120;

        maxHeap.remove();

        assert maxHeap.getPeek() == 100;

        maxHeap.remove();

        assert maxHeap.getPeek() == 100;

        maxHeap.remove();

        assert maxHeap.getPeek() == 90;

        System.out.println("PASSOU NOS TESTES!!!");
        System.out.println(maxHeap);
    }


}
