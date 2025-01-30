import javax.swing.*;
import java.util.ArrayList;

public class carrito {
    public JPanel mainPanel;
    private JList<String> listaProductos; // Cambiado de JTextArea a JList para selección
    private JTextArea mostrar_precio;
    private JButton irAPagarButton;
    private JButton quitarProductoButton; // Botón para quitar producto
    private DefaultListModel<String> modeloLista; // Modelo para JList

    // Constructor para inicializar el carrito
    public carrito(ArrayList<String> productos, ArrayList<Double> precios) {
        modeloLista = new DefaultListModel<>();
        listaProductos.setModel(modeloLista);
        cargarDatosCarrito(productos, precios);

        // Verifica que el botón no sea null antes de agregar la acción
        if (quitarProductoButton != null) {
            quitarProductoButton.addActionListener(e -> quitarProducto(productos, precios));
        } else {
            System.err.println("Error: quitarProductoButton es null. Verifica que está correctamente vinculado en el .form");
        }

        irAPagarButton.addActionListener(e -> abrirPagos(productos, precios));
    }

    // Método para cargar los datos del carrito
    private void cargarDatosCarrito(ArrayList<String> productos, ArrayList<Double> precios) {
        modeloLista.clear();
        StringBuilder preciosTexto = new StringBuilder();
        double subtotal = 0.0;

        // Construir las cadenas de texto para productos y precios
        for (int i = 0; i < productos.size(); i++) {
            modeloLista.addElement(productos.get(i)); // Agregar productos a la lista
            preciosTexto.append("$").append(precios.get(i)).append("\n");
            subtotal += precios.get(i); // Sumar los precios
        }

        // Agregar el subtotal al final del área de precios
        preciosTexto.append("\nSubtotal: $").append(String.format("%.2f", subtotal));

        // Establecer el texto en el JTextArea de precios
        mostrar_precio.setText(preciosTexto.toString());
        mostrar_precio.setEditable(false);
    }

    // Método para quitar un producto seleccionado del carrito
    private void quitarProducto(ArrayList<String> productos, ArrayList<Double> precios) {
        int selectedIndex = listaProductos.getSelectedIndex(); // Obtener índice seleccionado
        if (selectedIndex != -1) {
            productos.remove(selectedIndex); // Eliminar producto de la lista
            precios.remove(selectedIndex);
            cargarDatosCarrito(productos, precios); // Recargar la vista
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un producto para quitar.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para abrir la ventana de pagos
    private void abrirPagos(ArrayList<String> productos, ArrayList<Double> precios) {
        JFrame pagosFrame = new JFrame("Pago");
        pagosFrame.setContentPane(new pagos(productos, precios).mainPanel);
        pagosFrame.setSize(600, 400);
        pagosFrame.setVisible(true);
    }
}
