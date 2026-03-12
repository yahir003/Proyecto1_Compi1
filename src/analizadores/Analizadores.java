package analizadores;

import java.io.StringReader;

public class Analizadores {
    public static void main(String[] args) {
        try {
          String entrada = """
    database tienda {
        store at "tienda.json";
    }

    use tienda;

    read productos {
        fields: nombre, precio;
    };
    """;

            Lexico lexer = new Lexico(new StringReader(entrada));
            Sintactico parser = new Sintactico(lexer);

            parser.parse();
            
            ReporteHTML.generarReporteTokens(lexer.listaTokens, "reporte_tokens.html");
            ReporteHTML.generarReporteErrores(lexer.listaErrores, "reporte_errores.html");
               
            if (Sintactico.baseActual != null) {
    ReporteHTML.generarReporteSimbolos(
        Sintactico.baseActual.obtenerSimbolos(),
        "reporte_simbolos.html"
    );
}
            
            System.out.println("Analisis realizado correctamente.");

        } catch (Exception e) {
            System.out.println("Error en el analisis:");
            e.printStackTrace();
        }
    }
}