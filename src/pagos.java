import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class pagos {
    public JPanel mainPanel;
    private JTextField nombre_factura;
    private JTextField num_cedula;
    private JTextField num_tarjeta;
    private JTextField nombre_titular;
    private JTextField num_cvv;
    private JTextField fecha_vencimiento;
    private JTextArea mostrar_productos2;
    private JTextArea mostrar_precio2;
    private JButton pagarButton;
    private JButton imprimirButton; // Botón para imprimir la factura

    // Constructor para inicializar la ventana de pagos
    public pagos(ArrayList<String> productos, ArrayList<Double> precios) {
        cargarDatosPago(productos, precios);
        agregarValidacionBotonPagar();
        configurarBotonImprimir(); // Configurar el botón de imprimir
    }

    // Método para cargar los datos en la interfaz de pagos
    private void cargarDatosPago(ArrayList<String> productos, ArrayList<Double> precios) {
        StringBuilder productosTexto = new StringBuilder();
        StringBuilder preciosTexto = new StringBuilder();
        double subtotal = 0.0;

        // Construir las cadenas de texto para productos y precios
        for (int i = 0; i < productos.size(); i++) {
            productosTexto.append(productos.get(i)).append("\n");
            preciosTexto.append("$").append(precios.get(i)).append("\n");
            subtotal += precios.get(i);
        }

        // Calcular el IVA (15%) y el total con IVA
        double iva = subtotal * 0.15;
        double totalConIva = subtotal + iva;

        // Agregar el subtotal, IVA y total con IVA al área de precios
        preciosTexto.append("\nSubtotal: $").append(String.format("%.2f", subtotal));
        preciosTexto.append("\nIVA (15%): $").append(String.format("%.2f", iva));
        preciosTexto.append("\nTotal (con IVA): $").append(String.format("%.2f", totalConIva));

        // Establecer los textos en los JTextArea
        mostrar_productos2.setText(productosTexto.toString());
        mostrar_precio2.setText(preciosTexto.toString());

        // Hacer los JTextArea no editables
        mostrar_productos2.setEditable(false);
        mostrar_precio2.setEditable(false);
    }

    // Método para agregar la validación del botón "Pagar"
    private void agregarValidacionBotonPagar() {
        pagarButton.addActionListener(e -> {
            // Verificar si alguno de los campos está vacío
            if (nombre_factura.getText().isEmpty() ||
                    num_cedula.getText().isEmpty() ||
                    num_tarjeta.getText().isEmpty() ||
                    nombre_titular.getText().isEmpty() ||
                    num_cvv.getText().isEmpty() ||
                    fecha_vencimiento.getText().isEmpty()) {

                // Mostrar un mensaje de advertencia
                JOptionPane.showMessageDialog(
                        null,
                        "Por favor, llene todos los campos antes de proceder con el pago.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                // Mostrar mensaje de éxito en el pago
                JOptionPane.showMessageDialog(
                        null,
                        "Pago realizado con éxito.",
                        "Pago",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Habilitar el botón de imprimir
                imprimirButton.setEnabled(true);
            }
        });
    }

    // Método para configurar el botón "Imprimir"
    private void configurarBotonImprimir() {
        imprimirButton.setEnabled(false); // Deshabilitado al inicio

        // Agregar un ActionListener al botón "Imprimir"
        imprimirButton.addActionListener(e -> {
            try {
                // Crear el archivo PDF
                String rutaArchivo = "Factura_" + nombre_factura.getText().replace(" ", "_") + ".pdf";
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));

                // Abrir el documento para escribir en él
                documento.open();

                // Agregar contenido al documento
                documento.add(new Paragraph("Factura"));
                documento.add(new Paragraph("------------------------------"));
                documento.add(new Paragraph("Nombre: " + nombre_factura.getText()));
                documento.add(new Paragraph("Número de cédula: " + num_cedula.getText()));
                documento.add(new Paragraph("------------------------------"));
                documento.add(new Paragraph("Productos:\n" + mostrar_productos2.getText()));
                documento.add(new Paragraph("------------------------------"));
                documento.add(new Paragraph("Precios:\n" + mostrar_precio2.getText()));
                documento.add(new Paragraph("------------------------------"));

                // Cerrar el documento
                documento.close();

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(
                        null,
                        "Factura guardada como PDF en: " + rutaArchivo,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error al generar el PDF: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
