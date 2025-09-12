package implementations;

import java.util.Arrays;
import java.util.Scanner;

public class MainTest {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Avl tree = new Avl();

            while (true) {
                String comando = sc.nextLine();
                Node[] array = (Node[]) Arrays.stream(comando.split(" "))
                        .map(elem -> new Node(Integer.parseInt(elem))).toArray();

                if (comando.equals("exit"))
                    break;

                tree.fixTree(array);
            }

            System.out.println("Fim dos testes!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
