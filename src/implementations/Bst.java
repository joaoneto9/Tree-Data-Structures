package implementations;

import java.util.Deque;
import java.util.LinkedList;

public class Bst {

    private Node root;
    
    public boolean isEmpty() {
        return this.root == null;
    }

    // método mais complexo de se implementar em uma BST
    public void remove(int n) {
        Node nodeToRemove = getNode(n);

        if (nodeToRemove == null)
            return;

        remove(nodeToRemove);
    }

    public boolean isAvl() {
        return isAvl(this.root);
    }

    private boolean isAvl(Node node) {
        if (node == null)
            return true;

        if (!node.isBalance())
            return false;

        return isAvl(node.left) && isAvl(node.right);
    }

    private void remove(Node nodeToRemove) {

        // Caso 1: caso mais simples -> Nó é uma folha
        if (nodeToRemove.isLeaf()) {
            if (nodeToRemove.isRoot())
                this.root = null;
            else if (nodeToRemove.isRight())
                nodeToRemove.parent.right = null;
            else
                nodeToRemove.parent.left = null;

            return;
        }

        // Caso 2: o no tem apenas um filho: -> fazer para esquerda e para a direita
        if (nodeToRemove.hasOnlyRightChild()) {
            // verificar se é raiz
            if (nodeToRemove.isRoot()) {
                this.root = nodeToRemove.right; // seta a raiz.
                nodeToRemove.right.parent = null; // como é a raiz, não apresenta mais pai.
                return;
            }

            nodeToRemove.right.parent = nodeToRemove.parent; // seta o novo pai

            if (nodeToRemove.isRight())
                nodeToRemove.right.parent.right = nodeToRemove.right; // seta o novo filho do pai
            else
                nodeToRemove.right.parent.left = nodeToRemove.right;
            return;
        }

        if (nodeToRemove.hasOnlyLeftChild()) {
            if (nodeToRemove.isRoot()) {
                this.root = nodeToRemove.left; // seta a raiz.
                nodeToRemove.left.parent = null; // como é a raiz, não apresenta mais pai.
                return;
            }

            nodeToRemove.left.parent = nodeToRemove.parent;

            if (nodeToRemove.isRight())
                nodeToRemove.left.parent.right = nodeToRemove.left;
            else
                nodeToRemove.left.parent.left = nodeToRemove.left;

            return;
        }
        // Caso 3: tem dois filhos -> pegar o sucessor, fazer a troca e chamar recursivamnete a remoção dele
        Node sucessor = sucessor(nodeToRemove);
        nodeToRemove.value = sucessor.value;
        remove(sucessor);
        // garantimos que vai entrar no caso um ou dois, pois o sucessor ou é folha ou tem apenas um filho
    }

    public void add(int n) {
        if (isEmpty())
            this.root = new Node(n);
        else add(n, this.root);
    }

    public int max() {
        if (isEmpty())
            return -1;

        return max(this.root).value;
    }

    private Node max(Node node) {
        if (node.right == null)
            return node;

        return max(node.right);
    }

    public int min() {
        if (isEmpty())
            return -1; // should be an exception

        return min(this.root).value;
    }

    private Node min(Node node) {
        if (node.left == null)
            return node;

        return min(node.left);
    }

    public Node sucessor(Node node) {
        if (node == null)
            return null; // -> isso indica que não apresenta sucessor

        if (node.right == null) { // não apresenta arvore a direita -> subir...
            Node aux = node.parent;

            while (aux != null && aux.value < node.value)
                aux = aux.parent; // ele vai subindo ate achar alguem maior que ele

            return aux;
        }

        return min(node.right); // retorna o mínimo da àrvore a direita: sucessor;
    }

    public Node predecessor(Node node) {
        if (node == null)
            return null;

        if (node.left == null) {
            Node aux = node.parent;

            while (aux != null && aux.value > node.value) // se for maior, continue.
                aux = aux.parent;

            return aux;
        }

        return max(node.left); // retorna o maximo da arvore a esquerda: predecessor!
    }

    public Node getNode(int n) {
        return getNode(n, this.root);
    }

    private Node getNode(int n, Node node) {
        if (node == null)
            return null;

        if (n == node.value)
            return node;

        if (n > node.value)
            return getNode(n, node.right);

        return getNode(n, node.left);
    }

    private void add(int n, Node node) {
        // n deve ser adicionado a direita do no
        if (n > node.value) {
            if (node.right == null) {
                node.right = new Node(n);
                node.right.parent = node;
                return;
            }
            add(n, node.right);
            return;
        }

        if (node.left == null) {
            node.left = new Node(n);
            node.left.parent = node;
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

    // Uso de uma linkedList garante a ordem dos nos e seus niveis, assim -> remove, printa o que removeu e adiciona os filhos
    public void printBFS() {
        Deque<Node> fila = new LinkedList<>();

        if (isEmpty())
            return;

        fila.addLast(this.root);

        while (!fila.isEmpty()) {
            Node current = fila.removeFirst();

            System.out.println(current.value);

            if (current.left != null)
                fila.addLast(current.left);
            if (current.right != null)
                fila.addLast(current.right);
        }
    }

    public int contarFolhas() {
        return contarFolhas(this.root);
    }

    private int contarFolhas(Node node) {
        if (node == null)
            return 0;
        if (node.isLeaf())
            return 1;

        return contarFolhas(node.left) + contarFolhas(node.right);
    }

    public void posOrdem() {
        posOrdem(this.root);
    }

    private void posOrdem(Node node) {
        if (node == null)
            return;

        posOrdem(node.left);
        posOrdem(node.right);
        System.out.println(node.value);
    }

    // grava os nos em ordem sequencial
    public void inOrder() {
        inOrder(this.root);
    }

    private void inOrder(Node node) {
        if (node == null)
            return;

        inOrder(node.left);
        System.out.println(node.value);
        inOrder(node.right);
    }

    // uma chave única para a árvore
    public String preOrder() {
        return preOrder(this.root);
    }

    private String preOrder(Node node) {
        if (node == null)
            return "";

        return node.value + preOrder(node.left) + preOrder(node.right);
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (!(o instanceof Bst))
            return false;

        return ((Bst) o).preOrder().equals(this.preOrder());
    }

    public int maxHeightDiference() {
        if (isEmpty())
            return 0;

        return maxHeightDiference(this.root);

    }

    private int maxHeightDiference(Node node) {
        if (node == null)
            return 0;

        return Math.max(Math.abs(node.balance()), Math.max(maxHeightDiference(node.left), maxHeightDiference(node.right)));
    }

    public static void main(String[] args) {
        Bst bst = new Bst();

        assert bst.height() == -1;
        assert bst.isEmpty();

        bst.add(10);

        assert bst.height() == 0;
        assert !bst.isEmpty();
        assert bst.getNode(10) != null;
        assert bst.max() == 10;
        assert bst.min() == bst.max();
        assert bst.sucessor(bst.getNode(10)) == null;
        assert bst.predecessor(bst.getNode(10)) == null;
        assert bst.contarFolhas() == 1;

        bst.add(5);

        assert bst.height() == 1;
        assert bst.getNode(5) != null;
        assert bst.getNode(5).parent.value == 10;
        assert bst.min() == 5;
        assert bst.max() == 10;
        assert bst.sucessor(bst.getNode(5)).value == 10;
        assert bst.predecessor(bst.getNode(5)) == null;
        assert bst.sucessor(bst.getNode(10)) == null;
        assert bst.predecessor(bst.getNode(10)).value == 5;
        assert bst.contarFolhas() == 1;

        bst.add(15);

        assert bst.height() == 1;
        assert bst.getNode(15) != null;
        assert bst.getNode(15).parent.value == 10;
        assert bst.min() == 5;
        assert bst.max() == 15;
        assert bst.sucessor(bst.getNode(10)).value == 15;
        assert bst.predecessor(bst.getNode(10)).value == 5;
        assert bst.contarFolhas() == 2;

        bst.add(2);

        assert bst.height() == 2;
        assert bst.getNode(2) != null;
        assert bst.getNode(2).parent.value == 5;
        assert bst.min() == 2;
        assert bst.max() == 15;
        assert bst.sucessor(bst.getNode(5)).value == 10;
        assert bst.predecessor(bst.getNode(5)).value == 2;
        assert bst.predecessor(bst.getNode(2)) == null;
        assert bst.sucessor(bst.getNode(15)) == null;
        assert bst.predecessor(bst.getNode(15)).value == 10;
        assert bst.contarFolhas() == 2;

        assert bst.maxHeightDiference() == 1;

        // casos de testes de remoção

        // Caso 1: caso da folha
        assert !bst.getNode(5).isLeaf(); // antes da remoção não é folha

        bst.remove(2);

        assert bst.getNode(2) == null;  // o no com esse valor foi removido
        assert bst.getNode(5) != null;
        assert bst.getNode(10) != null;
        assert bst.getNode(15) != null;
        assert bst.getNode(5).isLeaf(); // se torna folha
        assert bst.height() == 1; // altura da árvore mudou
        assert bst.contarFolhas() == 2;

        assert bst.maxHeightDiference() == 0;

        // Caso 2: caso de um filho

        bst.add(2); // adicionando novamnete o 2 e t;estando com o 5 que agora é hasOnlyOneChildLeft

        assert bst.getNode(5).hasOnlyLeftChild(); // caso dois -> filho a esquerda e esta a esquerda do pai
        assert bst.height() == 2;
        assert bst.contarFolhas() == 2;

        assert bst.maxHeightDiference() == 1;

        bst.remove(5);

        assert bst.getNode(2).parent.value == 10; // o pai agora é 10 que é a raiz
        assert bst.getNode(2).parent.isRoot();
        assert bst.height() == 1;
        assert bst.getNode(5) == null;
        assert bst.getNode(2) != null;
        assert bst.getNode(10) != null;
        assert bst.getNode(15) != null;
        assert bst.contarFolhas() == 2;

        assert bst.maxHeightDiference() == 0;


        // Caso 2: caso de um filho a direita e o no esta a esquerda do pai.

        bst.remove(2);

        assert bst.maxHeightDiference() == 1;

        bst.add(5);

        assert bst.maxHeightDiference() == 0;

        bst.add(7);

        assert bst.maxHeightDiference() == 1;

        assert bst.getNode(5).hasOnlyRightChild();
        assert bst.getNode(7).isLeaf();
        assert bst.height() == 2;

        bst.remove(5);

        assert bst.getNode(7).parent.value == 10; // o pai agora é 10 que é a raiz
        assert bst.getNode(7).parent.isRoot();
        assert bst.height() == 1;
        assert bst.getNode(5) == null;
        assert bst.getNode(7) != null;
        assert bst.getNode(10) != null;
        assert bst.getNode(15) != null;

        // caso 2: caso de um filho a esquerda e o no esta a direita do pai

        bst.add(12);

        assert bst.getNode(15).hasOnlyLeftChild();
        assert bst.getNode(15).isRight();
        assert bst.height() == 2;

        bst.remove(15);

        assert bst.getNode(12).parent.value == 10;
        assert bst.getNode(12).parent.isRoot();
        assert bst.height() == 1;
        assert bst.getNode(15) == null;
        assert bst.getNode(7) != null;
        assert bst.getNode(10) != null;
        assert bst.getNode(12) != null;

        // Caso 2: caso de um filho a direita e o no esta a direita do pai

        bst.add(17);

        assert bst.getNode(12).hasOnlyRightChild();
        assert bst.getNode(12).isRight();
        assert bst.height() == 2;

        bst.remove(12);

        assert bst.getNode(17).parent.value == 10;
        assert bst.getNode(17).parent.isRoot();
        assert bst.height() == 1;
        assert bst.getNode(12) == null;
        assert bst.getNode(7) != null;
        assert bst.getNode(10) != null;
        assert bst.getNode(17) != null;

        // Caso 3: caso de um No que tem dois filhos -> teste da raiz

        assert bst.getNode(17).isLeaf();
        assert bst.getNode(17).isRight();

        bst.remove(10);

        assert bst.getNode(10) == null;
        assert bst.getNode(7) != null;
        assert bst.getNode(17) != null;
        assert bst.getNode(17).isRoot();
        assert bst.getNode(17).hasOnlyLeftChild();
        assert bst.getNode(17).left.value == 7;

        Bst bst2 = new Bst();

        bst2.add(17); // raiz
        bst2.add(7);

        assert bst.equals(bst2);

        bst.remove(7);

        assert !bst.equals(bst2);

        bst2.remove(7);

        assert bst.equals(bst2);

        bst.add(10);
        bst.add(11);

        bst2.add(11);
        bst2.add(10);

        assert !bst.equals(bst2);

        System.out.println("PASSOU NOS TESTES!!");
    }


}
