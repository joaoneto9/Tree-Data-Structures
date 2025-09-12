package implementations;

public class RedBlackTree {

    private BlackTreeNode root;

    public boolean isEmpty() {
        return root == null;
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        tree.add(10);
        // caso um
        tree.add(20);
        // nao faz nada
        tree.add(5);
        // nao faz nada
        tree.add(15);
        // tio vermelho e nao faz nada
        tree.add(17);
        // rotação para a esquerda e rotação para a direita
        tree.add(16);
        // tio vermelho e acima do avo == preto -> para por ai
        tree.add(7);
        // pai preto, nao faz nada
        tree.add(8); // -> erro
        // pai vermelho, tio preto -> rotaciona para a esquerda apanas -> filho externo
        tree.add(2);
        // tio vermelho e pai do avo preto -> para
        tree.add(1);
        // right rotation Caso Quatro, filho externo a esquerda
    }

    public void add(int n) {
        BlackTreeNode newNode = new BlackTreeNode(n, Color.RED); // sempre inserimos com cor vermelha

        // first scenario
        if (isEmpty()) {
            this.root = newNode;
            newNode.recolor();
            System.out.println("CASO UM");
            return;
        }

        add(newNode, this.root);
        lookUpRotation(newNode);
        root.setColor(Color.BLACK);
    }

    private void lookUpRotation(BlackTreeNode newNode) {
        // pai é preto
        if (newNode.parent == null || newNode.parent.isBlack()) {
            System.out.println("CASO PAI PRETO -> nao faz nada");
            return; // nada a fazer
        }

        // tio é vermelho
        if (!newNode.uncleIsBlack()) {
            newNode.uncle().recolor();
            newNode.parent.recolor();
            newNode.parent.parent.recolor(); // se o tio e o pai são vermelho, por construção eu garanto que o avô é preto
            System.out.println("CASO DOIS -> TIO VERMELHO");
            lookUpRotation(newNode.parent.parent);
            return;
        }
        // filho interno e pai vermelho
        else if (newNode.isLeft() && newNode.parent.isRight()) {
            rightRotation(newNode.parent); // ao fazer isso o newNode agora não é mais a "cauda"
            newNode = newNode.right;
            System.out.println("CASO TRES -> filho interno (esquerda), tio preto e pai vermelho");
        }
        // filho interno e pai vermelho
        else if (newNode.isRight() && newNode.parent.isLeft()) {
            leftRotation(newNode.parent);
            newNode = newNode.left;
            System.out.println("CASO TRES -> filho interno (direita), tio preto e pai vermelho");
        }

        newNode.parent.parent.recolor();
        newNode.parent.recolor();

        // alinhado para a direita
        if (newNode.isRight()) {
            leftRotation(newNode.parent.parent);
            System.out.println("CASO QUATRO -> filho externo para direita");
        } else {
            rightRotation(newNode.parent.parent);
            System.out.println("CASO QUATRO -> filho externo para esquerda");
        }
    }

    private void add(BlackTreeNode newNode, BlackTreeNode node) {
        if (newNode.value > node.value) {
            if (node.right == null) {
                node.right = newNode;
                newNode.parent = node;
                return;
            }

            add(newNode, node.right);
            return;
        }

        if (node.left == null) {
            node.left = newNode;
            newNode.parent = node;
            return;
        }

        add(newNode, node.left);
    }

    private void rightRotation(BlackTreeNode node) {
        BlackTreeNode nodeUp = node.left;

        // passo 1: mover subárvore à direita de nodeUp
        node.left = nodeUp.right;
        if (nodeUp.right != null) {
            nodeUp.right.parent = node;
        }

        // passo 2: ligar nodeUp ao pai de node
        nodeUp.parent = node.parent; // o pai do no que vai subir tem que ser o pai do no que estava em cima
        if (node.isRoot())
            this.root = nodeUp;
        else if (node.isRight())
            node.parent.right = nodeUp;
        else
            node.parent.left = nodeUp;

        // passo 3: girar
        nodeUp.right = node;
        node.parent = nodeUp;
    }

    private void leftRotation(BlackTreeNode node) {
        BlackTreeNode nodeUp = node.right;

        // passo 1: mover subárvore à esquerda de nodeUp
        node.right = nodeUp.left;
        if (nodeUp.left != null) {
            nodeUp.left.parent = node;
        }

        // passo 2: ligar nodeUp ao pai de node
        nodeUp.parent = node.parent; // o pai do no que vai subir tem que ser o pai do no que estava em cima
        if (node.isRoot())
            this.root = nodeUp;
        else if (node.isLeft())
            node.parent.left = nodeUp;
        else
            node.parent.right = nodeUp;

        // passo 3: girar
        nodeUp.left = node;
        node.parent = nodeUp;
    }

    public BlackTreeNode search(int n) {
        if (isEmpty())
            return null;

        return search(n, this.root);
    }

    private BlackTreeNode search(int n, BlackTreeNode node) {
        if (node == null)
            return null;

        if (n == node.value)
            return node;

        if (n > node.value)
            return search(n, node.right);

        return search(n, node.left);
    }
}

class BlackTreeNode {

    int value;
    BlackTreeNode parent;
    BlackTreeNode right;
    BlackTreeNode left;
    Color color;

    BlackTreeNode(int n, Color color) {
        this.value = n;
        this.color = color;
    }

    void recolor() {
        if (isBlack()) this.color = Color.RED;
        else this.color = Color.BLACK;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    int blackHeight() {
        return blackHeight(this.left);
    }

    private int blackHeight(BlackTreeNode node) {
        if (node == null)
            return 1; // -> se for null => contabiliza

        int leftHeight = blackHeight(node.left);

        return node.isBlack() ? 1 + leftHeight : leftHeight; // apenas um caminho ja e suficiente
    }

    boolean isRoot() {
        return this.parent == null;
    }

    boolean isLeft() {
        if (isRoot()) return false;
        return this.parent.value > this.value;
    }

    boolean isRight() {
        if (isRoot()) return false;
        return this.parent.value < this.value;
    }

    BlackTreeNode uncle() {
        if (isRoot() || this.parent.parent == null)
            return null;

        if (this.parent.isLeft())
            return this.parent.parent.right;

        return this.parent.parent.left;
    }

    boolean isBlack() {
        return color.equals(Color.BLACK);
    }

    boolean uncleIsBlack() {
        BlackTreeNode uncle = uncle();
        return uncle == null || uncle.isBlack();
    }

    public static void main(String[] args) {
        BlackTreeNode node = new BlackTreeNode(5, Color.BLACK);

        node.left = new BlackTreeNode(2, Color.RED);

        assert node.blackHeight(node.right) == node.blackHeight();
        assert node.blackHeight() == 1;

        node.right = new BlackTreeNode(7, Color.RED);

        assert node.blackHeight(node.right) == node.blackHeight();
        assert node.blackHeight() == 1;
    }
}

enum Color {
    RED, BLACK
}
