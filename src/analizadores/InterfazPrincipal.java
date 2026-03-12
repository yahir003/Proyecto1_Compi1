/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.StringReader;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class InterfazPrincipal extends JFrame {

    private JTextArea txtEntrada;
    private JTextArea txtSalida;
    private File archivoActual;

    public InterfazPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("ELI-NOSQL");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtEntrada = new JTextArea();
        txtEntrada.setFont(new Font("Monospaced", Font.PLAIN, 14));

        txtSalida = new JTextArea();
        txtSalida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtSalida.setEditable(false);

        JScrollPane scrollEntrada = new JScrollPane(txtEntrada);
        JScrollPane scrollSalida = new JScrollPane(txtSalida);

        scrollEntrada.setPreferredSize(new Dimension(900, 320));
        scrollSalida.setPreferredSize(new Dimension(900, 200));

        JPanel panelCentro = new JPanel(new BorderLayout());

        JPanel panelEtiquetas = new JPanel(new BorderLayout());
        panelEtiquetas.add(new JLabel(" Entrada ELI-NOSQL"), BorderLayout.NORTH);

        JPanel panelSalida = new JPanel(new BorderLayout());
        panelSalida.add(new JLabel(" Salida"), BorderLayout.NORTH);
        panelSalida.add(scrollSalida, BorderLayout.CENTER);

        panelCentro.add(panelEtiquetas, BorderLayout.NORTH);
        panelCentro.add(scrollEntrada, BorderLayout.CENTER);
        panelCentro.add(panelSalida, BorderLayout.SOUTH);

        JButton btnEjecutar = new JButton("Ejecutar");
        JButton btnAbrir = new JButton("Abrir");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnTokens = new JButton("Reporte Tokens");
        JButton btnErrores = new JButton("Reporte Errores");
        JButton btnSimbolos = new JButton("Reporte Simbolos");

        btnEjecutar.addActionListener(e -> ejecutarAnalisis());
        btnAbrir.addActionListener(e -> abrirArchivo());
        btnGuardar.addActionListener(e -> guardarArchivo());
        btnLimpiar.addActionListener(e -> limpiarAreas());
        btnTokens.addActionListener(e -> abrirReporte("reporte_tokens.html"));
        btnErrores.addActionListener(e -> abrirReporte("reporte_errores.html"));
        btnSimbolos.addActionListener(e -> abrirReporte("reporte_simbolos.html"));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnAbrir);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEjecutar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnTokens);
        panelBotones.add(btnErrores);
        panelBotones.add(btnSimbolos);

        add(panelBotones, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);

        setJMenuBar(crearMenu());
    }

    private JMenuBar crearMenu() {
        JMenuBar barra = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemAbrir = new JMenuItem("Abrir");
        JMenuItem itemGuardar = new JMenuItem("Guardar");
        JMenuItem itemSalir = new JMenuItem("Salir");

        itemAbrir.addActionListener(e -> abrirArchivo());
        itemGuardar.addActionListener(e -> guardarArchivo());
        itemSalir.addActionListener(e -> dispose());

        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);
        menuArchivo.add(itemSalir);

        barra.add(menuArchivo);
        return barra;
    }

    private void ejecutarAnalisis() {
        String entrada = txtEntrada.getText();

        if (entrada.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay codigo para analizar.");
            return;
        }

        try {
            txtSalida.setText("");

            Lexico lexer = new Lexico(new StringReader(entrada));
            Sintactico parser = new Sintactico(lexer);

            PrintStream consolaOriginal = System.out;

            PrintStream nuevaSalida = new PrintStream(new java.io.OutputStream() {
                @Override
                public void write(int b) {
                    txtSalida.append(String.valueOf((char) b));
                }
            });

            System.setOut(nuevaSalida);

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

            System.setOut(consolaOriginal);

        } catch (Exception e) {
            txtSalida.append("Error en el analisis:\n" + e.getMessage() + "\n");
        }
    }

    private void abrirArchivo() {
        JFileChooser chooser = new JFileChooser();
        int opcion = chooser.showOpenDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            archivoActual = chooser.getSelectedFile();

            try (BufferedReader br = new BufferedReader(new FileReader(archivoActual))) {
                StringBuilder contenido = new StringBuilder();
                String linea;

                while ((linea = br.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }

                txtEntrada.setText(contenido.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al abrir archivo: " + e.getMessage());
            }
        }
    }

    private void guardarArchivo() {
        try {
            if (archivoActual == null) {
                JFileChooser chooser = new JFileChooser();
                int opcion = chooser.showSaveDialog(this);

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    archivoActual = chooser.getSelectedFile();
                } else {
                    return;
                }
            }

            FileWriter writer = new FileWriter(archivoActual);
            writer.write(txtEntrada.getText());
            writer.close();

            JOptionPane.showMessageDialog(this, "Archivo guardado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + e.getMessage());
        }
    }

    private void limpiarAreas() {
        txtEntrada.setText("");
        txtSalida.setText("");
    }

    private void abrirReporte(String ruta) {
        try {
            File archivo = new File(ruta);

            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(this, "El reporte aun no ha sido generado.");
                return;
            }

            java.awt.Desktop.getDesktop().browse(archivo.toURI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir el reporte: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazPrincipal().setVisible(true);
        });
    }
}