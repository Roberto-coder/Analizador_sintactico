
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class TablaSimbolos {
    private Stack<Map<String, Object>> alcance = new Stack<>();
    private Map<String, Object> registroMetodos = new HashMap<>();

    public TablaSimbolos() {
        Map<String, Object> alcanceGlobal = new HashMap<>();
        alcance.push(alcanceGlobal);
    }

    boolean existeID(String identificador) {
        for (int i = alcance.size() - 1; i >= 0; i--) {
            if (alcance.get(i).containsKey(identificador)) {
                return true;
            }
        }
        return alcance.get(0).containsKey(identificador);
    }


    Object obtenerID(String identificador) {//Nombre de la funcion o variable
        for (int i = alcance.size() - 1; i >= 0; i--) {
            if (alcance.get(i).containsKey(identificador)) {
                return alcance.get(i).get(identificador);
            }
        }
        if (registroMetodos.containsKey(identificador)) {
            return registroMetodos.get(identificador);
        }
        if (alcance.get(0).containsKey(identificador)) {
            return alcance.get(0).get(identificador);
        }
        throw new RuntimeException("Identificador no definido '" + identificador + "'.");
    }


    void declarar(String identificador, Object valorInicial) {
        Map<String, Object> alcanceActual = alcance.peek();
        // System.out.println("Declarando: " + identificador + " = " + valorInicial);
        alcanceActual.put(identificador, valorInicial);
    }

    void asignar(String identificador, Object valor) {
        for (int i = alcance.size() - 1; i >= 0; i--) {//Buscar y reemplazar
            Map<String, Object> alcanceActual = alcance.get(i);
            if (alcanceActual.containsKey(identificador)) {
                alcanceActual.put(identificador, valor);
                //    System.out.println("Variable " + identificador + " actualizada a " + valor + " en el alcance " + i);
                return;
            }
        }
        throw new RuntimeException("La variable: '" + identificador + "' a la cual intentas asignarle un valor, no está declarada. Asegúrate de que está declarada.");
    }




    void registrarFuncion(String nombreFuncion, Object definicionFuncion) {
        // System.out.println("Funcion declarada");
        registroMetodos.put(nombreFuncion, definicionFuncion);
    }


    public Object obtenerFuncion(String nombreFuncion) {
        if (registroMetodos.containsKey(nombreFuncion)) {
            return registroMetodos.get(nombreFuncion);
        }
        throw new RuntimeException("Función no definida '" + nombreFuncion + "'.");
    }

}
