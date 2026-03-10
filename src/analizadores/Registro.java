/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.util.LinkedHashMap;
import java.util.Map;

public class Registro {
    private LinkedHashMap<String, Object> valores;

    public Registro() {
        valores = new LinkedHashMap<>();
    }

    public void agregarValor(String campo, Object valor) {
        valores.put(campo, valor);
    }

    public Object getValor(String campo) {
        return valores.get(campo);
    }

    public LinkedHashMap<String, Object> getValores() {
        return valores;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        boolean primero = true;

        for (Map.Entry<String, Object> entry : valores.entrySet()) {
            if (!primero) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append(": ").append(entry.getValue());
            primero = false;
        }

        sb.append(" }");
        return sb.toString();
    }
}