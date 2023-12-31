import java.util.*;

public class Node {
    private String value; // Valor del nodo
    private List<Node> children; // Lista de hijos

    // Getters, setters, etc.
    public Node() {

    }

    public Node(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }
}


