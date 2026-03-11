/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.util.Map;

public class FiltroOr implements ExpresionFiltro {
    private ExpresionFiltro izquierda;
    private ExpresionFiltro derecha;

    public FiltroOr(ExpresionFiltro izquierda, ExpresionFiltro derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    public boolean evaluar(Map<String, Object> registro) {
        return izquierda.evaluar(registro) || derecha.evaluar(registro);
    }
}