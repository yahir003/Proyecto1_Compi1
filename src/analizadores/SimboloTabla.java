/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

public class SimboloTabla {
    private String baseDatos;
    private String tabla;
    private String campo;
    private String tipo;

    public SimboloTabla(String baseDatos, String tabla, String campo, String tipo) {
        this.baseDatos = baseDatos;
        this.tabla = tabla;
        this.campo = campo;
        this.tipo = tipo;
    }

    public String getBaseDatos() {
        return baseDatos;
    }

    public String getTabla() {
        return tabla;
    }

    public String getCampo() {
        return campo;
    }

    public String getTipo() {
        return tipo;
    }
}