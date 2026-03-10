/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.util.ArrayList;

public class BaseDatos {
    private String nombre;
    private String rutaArchivo;
    private ArrayList<Tabla> tablas;

    public BaseDatos(String nombre, String rutaArchivo) {
        this.nombre = nombre;
        this.rutaArchivo = rutaArchivo;
        this.tablas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public ArrayList<Tabla> getTablas() {
        return tablas;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public void agregarTabla(Tabla tabla) {
        tablas.add(tabla);
    }

    public Tabla buscarTabla(String nombreTabla) {
        for (Tabla tabla : tablas) {
            if (tabla.getNombre().equals(nombreTabla)) {
                return tabla;
            }
        }
        return null;
    }

    public void mostrarTablas() {
        System.out.println("Base de datos: " + nombre);
        System.out.println("Ruta de persistencia: " + rutaArchivo);
        System.out.println("Tablas registradas:");

        if (tablas.isEmpty()) {
            System.out.println(" (sin tablas)");
            return;
        }

        for (Tabla tabla : tablas) {
            System.out.println(" - " + tabla.getNombre());
        }
    }
}