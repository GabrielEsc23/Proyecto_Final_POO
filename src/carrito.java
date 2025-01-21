import javax.swing.*;
import java.util.ArrayList;

public class carrito {
    public JPanel mainPanel;
    private JTextArea mostrar_productos;
    private JTextArea mostrar_precio;

    // Constructor para inicializar el carrito
    public carrito(ArrayList<String> productos, ArrayList<Double> precios) {
        cargarDatosCarrito(productos, precios);
    }

    // Método para cargar los datos del carrito
    private void cargarDatosCarrito(ArrayList<String> productos, ArrayList<Double> precios) {
        StringBuilder productosTexto = new StringBuilder();
        StringBuilder preciosTexto = new StringBuilder();
        double subtotal = 0.0;

        // Construir las cadenas de texto para productos y precios
        for (int i = 0; i < productos.size(); i++) {
            productosTexto.append(productos.get(i)).append("\n"); // Agregar salto de línea
            preciosTexto.append("$").append(precios.get(i)).append("\n");
            subtotal += precios.get(i); // Sumar los precios
        }

        // Agregar el subtotal al final del área de precios
        preciosTexto.append("\nSubtotal: $").append(subtotal);

        // Establecer los textos en los JTextArea
        mostrar_productos.setText(productosTexto.toString());
        mostrar_precio.setText(preciosTexto.toString());

        // Hacer los JTextArea no editables
        mostrar_productos.setEditable(false);
        mostrar_precio.setEditable(false);
    }
}
