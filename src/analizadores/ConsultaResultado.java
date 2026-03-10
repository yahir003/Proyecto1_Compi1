/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.util.ArrayList;

public class ConsultaResultado {
    private boolean todosLosCampos;
    private ArrayList<String> campos;

    public ConsultaResultado() {
        this.todosLosCampos = false;
        this.campos = new ArrayList<>();
    }

    public boolean isTodosLosCampos() {
        return todosLosCampos;
    }

    public void setTodosLosCampos(boolean todosLosCampos) {
        this.todosLosCampos = todosLosCampos;
    }

    public ArrayList<String> getCampos() {
        return campos;
    }

    public void setCampos(ArrayList<String> campos) {
        this.campos = campos;
    }
}