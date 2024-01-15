import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TablaSimbolos {
    private Stack<Map<String, Object>> alcance;
    private Map<String, Object> registroMetodos;

    public TablaSimbolos() {
        alcance = new Stack<>();
        registroMetodos = new HashMap<>();
        alcance.push(new HashMap<>()); // Inicializa el alcance global
    }

    public boolean existeID(String identificador) {
        for (int i = alcance.size() - 1; i >= 0; i--) {
            if (alcance.get(i).containsKey(identificador)) {
                return true;
            }
        }
        return registroMetodos.containsKey(identificador);
    }

    Object obtenerID(String identificador) {//Nombre de la funcion o variable
        for (int i = alcance.size() - 1; i >= 0; i--) {
            if (alcance.get(i).containsKey(identificador)) {//Verificar si esta en el bloque
                return alcance.get(i).get(identificador);
            }
        }
        if (registroMetodos.containsKey(identificador)) {//verificar si esta en metodos
            return registroMetodos.get(identificador);
        }
        if (alcance.get(0).containsKey(identificador)) {//verificar si esta global
            return alcance.get(0).get(identificador);
        }
        throw new RuntimeException("Identificador no definido '" + identificador + "'.");
    }

    public void declarar(String identificador, Object valorInicial) {
        Map<String, Object> alcanceActual = alcance.peek();
        alcanceActual.put(identificador, valorInicial);
    }

    public void asignar(String identificador, Object valor) {
        for (int i = alcance.size() - 1; i >= 0; i--) {//verificar que existe
            Map<String, Object> alcanceActual = alcance.get(i);
            if (alcanceActual.containsKey(identificador)) {
                alcanceActual.put(identificador, valor);
                return;
            }
        }
        throw new RuntimeException("La variable '" + identificador + "' no está declarada.");
    }

    public void registrarFuncion(String nombreFuncion, Object definicionFuncion) {
        registroMetodos.put(nombreFuncion, definicionFuncion);
    }

    public Object obtenerFuncion(String nombreFuncion) {
        if (registroMetodos.containsKey(nombreFuncion)) {
            return registroMetodos.get(nombreFuncion);
        }
        throw new RuntimeException("La función '" + nombreFuncion + "' no está definida.");
    }
}
