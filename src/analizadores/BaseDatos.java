package analizadores;

import java.util.HashMap;
import java.util.Map;

public class BaseDatos {
    private String nombre;
    private Map<String, Tabla> tablas;
    private String rutaArchivo;

    public BaseDatos(String nombre, String rutaArchivo) {
        this.nombre = nombre;
        this.rutaArchivo = rutaArchivo;
        this.tablas = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public void agregarTabla(Tabla tabla) {
        tablas.put(tabla.getNombre(), tabla);
    }

    public Tabla buscarTabla(String nombreTabla) {
        return tablas.get(nombreTabla);
    }

    public boolean existeTabla(String nombreTabla) {
        return tablas.containsKey(nombreTabla);
    }

    public Map<String, Tabla> getTablas() {
        return tablas;
    }

    public void mostrarTablas() {
        System.out.println("Base de datos: " + nombre);
        System.out.println("Ruta de persistencia: " + rutaArchivo);
        System.out.println("Tablas registradas:");

        if (tablas.isEmpty()) {
            System.out.println(" (sin tablas)");
            return;
        }

        for (String nombreTabla : tablas.keySet()) {
            System.out.println(" - " + nombreTabla);
        }
    }
}