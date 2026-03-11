package analizadores;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void guardarEnArchivo() {
    if (rutaArchivo == null || rutaArchivo.isEmpty()) {
        System.out.println("Error: la base de datos no tiene ruta de persistencia.");
        return;
    }

    try {
        java.io.PrintWriter writer = new java.io.PrintWriter(rutaArchivo, "UTF-8");
        writer.println("{");
        writer.println("  \"database\": \"" + nombre + "\",");
        writer.println("  \"tables\": [");

        int i = 0;
        for (Tabla tabla : tablas.values()) {
            writer.println("    {");
            writer.println("      \"name\": \"" + tabla.getNombre() + "\",");
            writer.println("      \"schema\": {");

            int j = 0;
            for (Map.Entry<String, String> campo : tabla.getSchema().entrySet()) {
                writer.print("        \"" + campo.getKey() + "\": \"" + campo.getValue() + "\"");
                if (j < tabla.getSchema().size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
                j++;
            }

            writer.println("      },");
            writer.println("      \"records\": [");

            int k = 0;
            for (Map<String, Object> registro : tabla.getRegistros()) {
                writer.print("        {");

                int m = 0;
                for (Map.Entry<String, Object> entry : registro.entrySet()) {
                    writer.print("\"" + entry.getKey() + "\": ");
                    Object valor = entry.getValue();

                    if (valor == null) {
                        writer.print("null");
                    } else if (valor instanceof String) {
                        writer.print("\"" + valor.toString().replace("\"", "\\\"") + "\"");
                    } else {
                        writer.print(valor);
                    }

                    if (m < registro.size() - 1) {
                        writer.print(", ");
                    }
                    m++;
                }

                writer.print("}");
                if (k < tabla.getRegistros().size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
                k++;
            }

            writer.println("      ]");
            writer.print("    }");

            if (i < tablas.size() - 1) {
                writer.println(",");
            } else {
                writer.println();
            }
            i++;
        }

        writer.println("  ]");
        writer.println("}");
        writer.close();

        System.out.println("Base de datos guardada en: " + rutaArchivo);
    } catch (Exception e) {
        System.out.println("Error al guardar base de datos: " + e.getMessage());
    }
}
    public void cargarDesdeArchivo() {
    if (rutaArchivo == null || rutaArchivo.isEmpty()) {
        return;
    }

    try {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return;
        }

        String contenido = new String(
            Files.readAllBytes(Paths.get(rutaArchivo)),
            StandardCharsets.UTF_8
        );

        tablas.clear();

        Pattern tablaPattern = Pattern.compile(
            "\\{\\s*\"name\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"schema\"\\s*:\\s*\\{(.*?)\\}\\s*,\\s*\"records\"\\s*:\\s*\\[(.*?)\\]\\s*\\}",
            Pattern.DOTALL
        );

        Matcher tablaMatcher = tablaPattern.matcher(contenido);

        while (tablaMatcher.find()) {
            String nombreTabla = tablaMatcher.group(1);
            String schemaTexto = tablaMatcher.group(2);
            String registrosTexto = tablaMatcher.group(3);

            Tabla tabla = new Tabla(nombreTabla);

            Pattern campoPattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"([^\"]+)\"");
            Matcher campoMatcher = campoPattern.matcher(schemaTexto);

            while (campoMatcher.find()) {
                String nombreCampo = campoMatcher.group(1);
                String tipoCampo = campoMatcher.group(2);
                tabla.agregarCampo(nombreCampo, tipoCampo);
            }

            Pattern registroPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
            Matcher registroMatcher = registroPattern.matcher(registrosTexto);

            while (registroMatcher.find()) {
                String registroTexto = registroMatcher.group(1).trim();
                Map<String, Object> registro = new LinkedHashMap<>();

                if (!registroTexto.isEmpty()) {
                    Pattern valorPattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(\"[^\"]*\"|-?\\d+\\.\\d+|-?\\d+|true|false|null)");
                    Matcher valorMatcher = valorPattern.matcher(registroTexto);

                    while (valorMatcher.find()) {
                        String clave = valorMatcher.group(1);
                        String valorTexto = valorMatcher.group(2);
                        Object valor;

                        if (valorTexto.equals("null")) {
                            valor = null;
                        } else if (valorTexto.equals("true")) {
                            valor = true;
                        } else if (valorTexto.equals("false")) {
                            valor = false;
                        } else if (valorTexto.startsWith("\"")) {
                            valor = valorTexto.substring(1, valorTexto.length() - 1);
                        } else if (valorTexto.contains(".")) {
                            valor = Double.parseDouble(valorTexto);
                        } else {
                            valor = Integer.parseInt(valorTexto);
                        }

                        registro.put(clave, valor);
                    }
                }

                tabla.agregarRegistro(registro);
            }

            agregarTabla(tabla);
        }

        System.out.println("Base de datos cargada desde: " + rutaArchivo);

    } catch (Exception e) {
        System.out.println("Error al cargar base de datos: " + e.getMessage());
    }
}
}