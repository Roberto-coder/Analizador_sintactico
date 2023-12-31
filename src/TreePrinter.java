import java.util.List;

public class TreePrinter {

    //Node root;
    public static void printTree(Node root) {
        printTree(root, 0);
    }

    private static void printTree(Node node, int depth) {
        if (node == null) {
            return;
        }

        // Imprimir el valor del nodo con indentación según la profundidad
        for (int i = 0; i < depth; i++) {
            System.out.print("  "); // Dos espacios por nivel
        }
        System.out.println(node.getValue());

        // Recorrer los hijos del nodo actual
        for (Node child : node.getChildren()) {
            printTree(child, depth + 1); // Llamada recursiva para imprimir los hijos
        }
    }
}
