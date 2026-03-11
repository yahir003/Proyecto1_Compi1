package analizadores;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tabla {
    private String nombre;
    private Map<String, String> schema;
    private List<Map<String, Object>> registros;

    public Tabla(String nombre) {
        this.nombre = nombre;
        this.schema = new LinkedHashMap<>();
        this.registros = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Map<String, String> getSchema() {
        return schema;
    }

    public List<Map<String, Object>> getRegistros() {
        return registros;
    }

    public void agregarCampo(String campo, String tipo) {
        schema.put(campo, tipo);
    }

    public void agregarRegistro(Map<String, Object> registro) {
        registros.add(registro);
    }

    public boolean existeCampo(String campo) {
        return schema.containsKey(campo);
    }

    public String getTipoCampo(String campo) {
        return schema.get(campo);
    }

    public void limpiarRegistros() {
        registros.clear();
    }

    public void mostrarEstructura() {
        System.out.println("Tabla: " + nombre);
        System.out.println("Campos:");
        for (Map.Entry<String, String> entry : schema.entrySet()) {
            System.out.println(" - " + entry.getKey() + " : " + entry.getValue());
        }
    }

    public void mostrarRegistros() {
        System.out.println("Registros de la tabla " + nombre + ":");
        if (registros.isEmpty()) {
            System.out.println(" (sin registros)");
            return;
        }

        for (Map<String, Object> registro : registros) {
            System.out.println(registro);
        }
    }
}