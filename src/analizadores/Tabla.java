/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.util.ArrayList;

public class Tabla {
    private String nombre;
    private ArrayList<Campo> campos;
    private ArrayList<Registro> registros;

    public Tabla(String nombre) {
        this.nombre = nombre;
        this.campos = new ArrayList<>();
        this.registros = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Campo> getCampos() {
        return campos;
    }

    public ArrayList<Registro> getRegistros() {
        return registros;
    }

    public void agregarCampo(Campo campo) {
        campos.add(campo);
    }

    public void agregarRegistro(Registro registro) {
        registros.add(registro);
    }

    public void limpiarRegistros() {
        registros.clear();
    }

    public Campo buscarCampo(String nombreCampo) {
        for (Campo campo : campos) {
            if (campo.getNombre().equals(nombreCampo)) {
                return campo;
            }
        }
        return null;
    }

    public void mostrarEstructura() {
        System.out.println("Tabla: " + nombre);
        System.out.println("Campos:");
        for (Campo campo : campos) {
            System.out.println(" - " + campo);
        }
    }

    public void mostrarRegistros() {
        System.out.println("Registros de la tabla " + nombre + ":");
        if (registros.isEmpty()) {
            System.out.println(" (sin registros)");
            return;
        }

        for (Registro registro : registros) {
            System.out.println(registro);
        }
    }
}