/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.util.Map;

public class FiltroSimple implements ExpresionFiltro {
    private String campo;
    private String operador;
    private Object valor;

    public FiltroSimple(String campo, String operador, Object valor) {
        this.campo = campo;
        this.operador = operador;
        this.valor = valor;
    }

    public String getCampo() {
        return campo;
    }

    public String getOperador() {
        return operador;
    }

    public Object getValor() {
        return valor;
    }

    @Override
    public boolean evaluar(Map<String, Object> registro) {
        Object valorRegistro = registro.get(campo);

        if (valorRegistro == null) {
            return false;
        }

        if ((valorRegistro instanceof Integer || valorRegistro instanceof Double) &&
            (valor instanceof Integer || valor instanceof Double)) {

            double a = ((Number) valorRegistro).doubleValue();
            double b = ((Number) valor).doubleValue();

            switch (operador) {
                case "==":
                    return a == b;
                case "!=":
                    return a != b;
                case ">":
                    return a > b;
                case "<":
                    return a < b;
                case ">=":
                    return a >= b;
                case "<=":
                    return a <= b;
            }
        }

        if (valorRegistro instanceof String && valor instanceof String) {
            String a = (String) valorRegistro;
            String b = (String) valor;

            switch (operador) {
                case "==":
                    return a.equals(b);
                case "!=":
                    return !a.equals(b);
            }
        }

        if (valorRegistro instanceof Boolean && valor instanceof Boolean) {
            boolean a = (Boolean) valorRegistro;
            boolean b = (Boolean) valor;

            switch (operador) {
                case "==":
                    return a == b;
                case "!=":
                    return a != b;
            }
        }

        return false;
    }
}