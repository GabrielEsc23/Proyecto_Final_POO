import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Clase que gestiona el proceso de pago en la aplicación.
 */
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
    private JButton imprimirButton;
    private JButton RegresarButton; // Corregido el nombre del botón para que coincida con el diseño

    private ArrayList<String> productos;
    private ArrayList<Double> precios;

    /**
     * Constructor de la clase pagos.
     * @param productos Lista de productos en el carrito.
     * @param precios Lista de precios de los productos en el carrito.
     */
    public pagos(ArrayList<String> productos, ArrayList<Double> precios) {
        this.productos = productos;
        this.precios = precios;
        cargarDatosPago();
        agregarValidacionBotonPagar();
        configurarBotonImprimir();

        if (RegresarButton != null) {
            RegresarButton.addActionListener(e -> cerrarVentana());
        } else {
            System.err.println("Error: regresarButton es null. Verifica que está correctamente vinculado en el .form");
        }
    }

    /**
     * Carga los productos y precios en los campos de texto.
     */
    private void cargarDatosPago() {
        StringBuilder productosTexto = new StringBuilder();
        StringBuilder preciosTexto = new StringBuilder();
        double subtotal = 0.0;

        for (int i = 0; i < productos.size(); i++) {
            productosTexto.append(productos.get(i)).append("\n");
            preciosTexto.append("$").append(precios.get(i)).append("\n");
            subtotal += precios.get(i);
        }

        double iva = subtotal * 0.15;
        double totalConIva = subtotal + iva;

        preciosTexto.append("\nSubtotal: $").append(String.format("%.2f", subtotal));
        preciosTexto.append("\nIVA (15%): $").append(String.format("%.2f", iva));
        preciosTexto.append("\nTotal (con IVA): $").append(String.format("%.2f", totalConIva));

        mostrar_productos2.setText(productosTexto.toString());
        mostrar_precio2.setText(preciosTexto.toString());

        mostrar_productos2.setEditable(false);
        mostrar_precio2.setEditable(false);
    }

    /**
     * Agrega validación al botón de pago para asegurarse de que los campos estén llenos.
     */
    private void agregarValidacionBotonPagar() {
        pagarButton.addActionListener(e -> {
            if (nombre_factura.getText().isEmpty() ||
                    num_cedula.getText().isEmpty() ||
                    num_tarjeta.getText().isEmpty() ||
                    nombre_titular.getText().isEmpty() ||
                    num_cvv.getText().isEmpty() ||
                    fecha_vencimiento.getText().isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "Por favor, llene todos los campos antes de proceder con el pago.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Pago realizado con éxito.",
                        "Pago",
                        JOptionPane.INFORMATION_MESSAGE
                );
                imprimirButton.setEnabled(true);
            }
        });
    }

    /**
     * Configura la funcionalidad del botón de impresión para generar una factura en PDF.
     */
    private void configurarBotonImprimir() {
        imprimirButton.setEnabled(false);
        imprimirButton.addActionListener(e -> {
            try {
                String rutaArchivo = "Factura_" + nombre_factura.getText().replace(" ", "_") + ".pdf";
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
                documento.open();

                documento.add(new Paragraph("Factura"));
                documento.add(new Paragraph("------------------------------"));
                documento.add(new Paragraph("Nombre: " + nombre_factura.getText()));
                documento.add(new Paragraph("Número de cédula: " + num_cedula.getText()));
                documento.add(new Paragraph("------------------------------"));
                documento.add(new Paragraph("Productos:\n" + mostrar_productos2.getText()));
                documento.add(new Paragraph("------------------------------"));
                documento.add(new Paragraph("Precios:\n" + mostrar_precio2.getText()));
                documento.add(new Paragraph("------------------------------"));

                documento.close();

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

    /**
     * Cierra la ventana de pago y regresa al carrito.
     */
    private void cerrarVentana() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        if (frame != null) {
            frame.dispose(); // Cierra la ventana actual de pagos
        }
    }
}
