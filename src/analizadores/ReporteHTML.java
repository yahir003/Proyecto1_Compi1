/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.io.PrintWriter;
import java.util.LinkedList;

public class ReporteHTML {

    public static void generarReporteTokens(LinkedList<TokenInfo> tokens, String ruta) {
        try {
            PrintWriter writer = new PrintWriter(ruta, "UTF-8");

            writer.println("<html>");
            writer.println("<head><meta charset='UTF-8'><title>Reporte de Tokens</title></head>");
            writer.println("<body>");
            writer.println("<h1>Reporte de Tokens</h1>");
            writer.println("<table border='1' cellspacing='0' cellpadding='5'>");
            writer.println("<tr><th>No.</th><th>Token</th><th>Lexema</th><th>Línea</th><th>Columna</th></tr>");

            int i = 1;
            for (TokenInfo token : tokens) {
                writer.println("<tr>");
                writer.println("<td>" + i + "</td>");
                writer.println("<td>" + escaparHTML(token.getToken()) + "</td>");
                writer.println("<td>" + escaparHTML(token.getLexema()) + "</td>");
                writer.println("<td>" + token.getLinea() + "</td>");
                writer.println("<td>" + token.getColumna() + "</td>");
                writer.println("</tr>");
                i++;
            }

            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
            writer.close();

            System.out.println("Reporte de tokens generado en: " + ruta);
        } catch (Exception e) {
            System.out.println("Error al generar reporte de tokens: " + e.getMessage());
        }
    }

    public static void generarReporteErrores(LinkedList<String> errores, String ruta) {
        try {
            PrintWriter writer = new PrintWriter(ruta, "UTF-8");

            writer.println("<html>");
            writer.println("<head><meta charset='UTF-8'><title>Reporte de Errores</title></head>");
            writer.println("<body>");
            writer.println("<h1>Reporte de Errores</h1>");
            writer.println("<table border='1' cellspacing='0' cellpadding='5'>");
            writer.println("<tr><th>No.</th><th>Descripción</th></tr>");

            int i = 1;
            for (String error : errores) {
                writer.println("<tr>");
                writer.println("<td>" + i + "</td>");
                writer.println("<td>" + escaparHTML(error) + "</td>");
                writer.println("</tr>");
                i++;
            }

            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
            writer.close();

            System.out.println("Reporte de errores generado en: " + ruta);
        } catch (Exception e) {
            System.out.println("Error al generar reporte de errores: " + e.getMessage());
        }
    }

    private static String escaparHTML(String texto) {
        return texto.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;");
    }
}
