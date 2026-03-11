/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.util.Map;

public class FiltroNot implements ExpresionFiltro {
    private ExpresionFiltro expresion;

    public FiltroNot(ExpresionFiltro expresion) {
        this.expresion = expresion;
    }

    @Override
    public boolean evaluar(Map<String, Object> registro) {
        return !expresion.evaluar(registro);
    }
}