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

    private boolean checkUpBalance(Node node) {
        if (node == null)
            return true;

        if (!node.isBalance())
            return false;

        return checkUpBalance(node.parent);
    }

    public void add(int n) {
        Node newNode = new Node(n);
        if (isEmpy())
            this.root = newNode;
        else {
            add(this.root, newNode);
            // implementar o check up balance
            System.out.println(checkUpBalance(newNode));
        }
    }

    private void add(Node node, Node newNode) {
        if (newNode.value > node.value) {
            if (node.right == null) {
                node.right = newNode;
                newNode.parent = node;
                return;
            }

            add(node.right, newNode);
            return;
        }

        if (node.left == null) {
            node.left = newNode;
            newNode.parent = node;
            return;
        }

        add(node.left, newNode);
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
