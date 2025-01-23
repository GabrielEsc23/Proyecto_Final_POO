import javax.swing.*;
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

    // Constructor para inicializar la ventana de pagos
    public pagos(ArrayList<String> productos, ArrayList<Double> precios) {
        cargarDatosPago(productos, precios);
    }

    // Método para cargar los datos en la interfaz de pagos
    private void cargarDatosPago(ArrayList<String> productos, ArrayList<Double> precios) {
        StringBuilder productosTexto = new StringBuilder();
        StringBuilder preciosTexto = new StringBuilder();
        double subtotal = 0.0;

        // Construir las cadenas de texto para productos y precios
        for (int i = 0; i < productos.size(); i++) {
            productosTexto.append(productos.get(i)).append("\n"); // Agregar salto de línea
            preciosTexto.append("$").append(precios.get(i)).append("\n");
            subtotal += precios.get(i); // Sumar los precios
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
}
