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

    table productos {
        id : int;
        nombre : string;
        precio : float;
    }

    add productos {
        id: 1,
        nombre: "Laptop",
        precio: 3500.50
    };

    add productos {
        id: 2,
        nombre: "Mouse",
        precio: 150.00
    };

    clear productos;

    read productos {
        fields: nombre, precio;
    };
    """;

            Lexico lexer = new Lexico(new StringReader(entrada));
            Sintactico parser = new Sintactico(lexer);

            parser.parse();

            System.out.println("Analisis realizado correctamente.");

        } catch (Exception e) {
            System.out.println("Error en el analisis:");
            e.printStackTrace();
        }
    }
}