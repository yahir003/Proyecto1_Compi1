/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

/**
 *
 * @author yahir
 */

import java.util.Map;

public interface ExpresionFiltro {
    boolean evaluar(Map<String, Object> registro);
}