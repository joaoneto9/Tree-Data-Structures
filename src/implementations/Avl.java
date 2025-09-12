package implementations;

import java.util.Scanner;

public class Avl {

    private Node root;

    public boolean isEmpy() {
        return this.root == null;
    }

    public int height() {
        return height(this.root);
    }

    private int height(Node node) {
        if (node == null)
            return -1;

        return 1 + Math.max(height(node.left), height(node.right));
    }

    private void checkUpBalance(Node newNode, Node node) {
        if (node == null) { // chegou depois da raiz -> parou
            System.out.println("NÃO TEVE ROTAÇÂO");
            return;
        }

        // no não esta balanceado
        if (!node.isBalance()) {
            // pendendo para a esquerda, não esta balanceado e o último no adicionado foi para a esquerda
            if (node.isPendingLeft() && newNode.isLeft()) {
                rotateRight(node); // rotaciona para a direita o no desbalanceado
            } else if (node.isPendingRight() && newNode.isRight()) {
                rotateLeft(node);
            } else if (node.isPendingLeft() && newNode.isRight()) {
                rotateLeft(node.left);
                rotateRight(node);
            } else if (node.isPendingRight() && newNode.isLeft()) {
                rotateRight(node.right);
                rotateLeft(node);
            }

            return;
        }

        checkUpBalance(newNode, node.parent);
    }

    private void rotateRight(Node n) {
        System.out.println("ROTAÇÂO PARA A DIREITA!");
        Node b = n.left;
        b.parent = n.parent;
        n.parent = b;
        n.left = b.right;
        if(b.right != null)
            b.right.parent = n;
        b.right = n;

        if(b.parent != null){
            if(b.parent.left == n){
                b.parent.left = b;
            } else{
                b.parent.right = b;
            }
        } else {
            this.root = b;
        }
    }

    private void rotateLeft(Node n) {
        System.out.println("ROTAÇÂO PARA A ESQUERDA!");
        Node b = n.right;
        b.parent = n.parent;
        n.parent = b;
        n.right = b.left;
        if(b.left != null)
            b.left.parent = n;
        b.left = n;

        if(b.parent != null){
            if(b.parent.left == n)
                b.parent.left = b;
            else {
                b.parent.right = b;
            }
        } else {
            this.root = b;
        }
    }

    public void add(int n) {
        Node newNode = new Node(n);
        if (isEmpy())
            this.root = newNode;
        else {
            add(this.root, newNode);
            // implementar o check up balance
            checkUpBalance(newNode, newNode.parent);
        }
    }

    private void add(Node node, Node newNode) {
        if (newNode.value > node.value) {
            if (node.right == null) {
                node.right = newNode;
                node.right.parent = node;
                return;
            }

            add(node.right, newNode);
            return;
        }

        if (node.left == null) {
            node.left = newNode;
            node.left.parent = node;
            return;
        }

        add(node.left, newNode);
    }

    public void fixTree(Node[] array) {
        // como so tem tres nos, apenas a raiz pode estar desbalanceada
        if (array[0].isBalance())
            return; // ja esta balanceado

        // pendendo pra esquerda e ultimo adicionado esta a esquerda
        if (array[0].isPendingLeft() && array[2].isLeft()) {
            rotateRightLimitNodes(array);
        } else if (array[0].isPendingRight() && array[2].isRight()) {
            rotateLeftLimitNodes(array);
        } else if (array[0].isPendingLeft() && array[2].isRight()) {
            rotateLeftLimitNodes(array);
            rotateRightLimitNodes(array);
        } else if (array[0].isPendingRight() && array[2].isLeft()) {
            rotateRightLimitNodes(array);
            rotateLeftLimitNodes(array);
        }
    }


    private void rotateRightLimitNodes(Node[] nodes) {
    }

    private void rotateLeftLimitNodes(Node[] nodes) {

    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Avl tree = new Avl();

            while (true) {
                String comando = sc.nextLine();

                if (comando.equals("exit"))
                    break;

                tree.add(Integer.parseInt(comando));
            }

            System.out.println("Fim dos testes!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
