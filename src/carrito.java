import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase que representa la interfaz del carrito de compras.
 */
public class carrito {
    /** Panel principal de la interfaz gráfica. */
    public JPanel mainPanel;
    /** Lista que muestra los productos agregados al carrito. */
    private JList<String> listaProductos;
    /** Área de texto que muestra los precios de los productos y el subtotal. */
    private JTextArea mostrar_precio;
    /** Botón para proceder al pago. */
    private JButton irAPagarButton;
    /** Botón para quitar un producto del carrito. */
    private JButton quitarProductoButton;
    /** Botón para regresar a la pantalla anterior. */
    private JButton regresarButton;
    /** Modelo de lista para gestionar los productos en el carrito. */
    private DefaultListModel<String> modeloLista;

    /**
     * Constructor de la clase carrito.
     * Inicializa la interfaz y carga los productos y precios en la lista.
     *
     * @param productos Lista de nombres de los productos en el carrito.
     * @param precios   Lista de precios de los productos en el carrito.
     */
    public carrito(ArrayList<String> productos, ArrayList<Double> precios) {
        modeloLista = new DefaultListModel<>();
        listaProductos.setModel(modeloLista);
        cargarDatosCarrito(productos, precios);

        if (quitarProductoButton != null) {
            quitarProductoButton.addActionListener(e -> quitarProducto(productos, precios));
        } else {
            System.err.println("Error: quitarProductoButton es null. Verifica que está correctamente vinculado en el .form");
        }

        irAPagarButton.addActionListener(e -> abrirPagos(productos, precios));

        if (regresarButton != null) {
            regresarButton.addActionListener(e -> cerrarVentana());
        } else {
            System.err.println("Error: regresarButton es null. Verifica que está correctamente vinculado en el .form");
        }
    }

    /**
     * Carga los productos y precios en la lista del carrito.
     *
     * @param productos Lista de productos en el carrito.
     * @param precios   Lista de precios de los productos.
     */
    private void cargarDatosCarrito(ArrayList<String> productos, ArrayList<Double> precios) {
        modeloLista.clear();
        StringBuilder preciosTexto = new StringBuilder();
        double subtotal = 0.0;

        for (int i = 0; i < productos.size(); i++) {
            modeloLista.addElement(productos.get(i));
            preciosTexto.append("$").append(precios.get(i)).append("\n");
            subtotal += precios.get(i);
        }

        preciosTexto.append("\nSubtotal: $").append(String.format("%.2f", subtotal));
        mostrar_precio.setText(preciosTexto.toString());
        mostrar_precio.setEditable(false);
    }

    /**
     * Elimina un producto seleccionado de la lista del carrito.
     *
     * @param productos Lista de productos en el carrito.
     * @param precios   Lista de precios de los productos.
     */
    private void quitarProducto(ArrayList<String> productos, ArrayList<Double> precios) {
        int selectedIndex = listaProductos.getSelectedIndex();
        if (selectedIndex != -1) {
            productos.remove(selectedIndex);
            precios.remove(selectedIndex);
            cargarDatosCarrito(productos, precios);
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un producto para quitar.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Abre la ventana de pagos para proceder con la compra.
     *
     * @param productos Lista de productos en el carrito.
     * @param precios   Lista de precios de los productos.
     */
    private void abrirPagos(ArrayList<String> productos, ArrayList<Double> precios) {
        JFrame pagosFrame = new JFrame("Pago");
        pagosFrame.setContentPane(new pagos(productos, precios).mainPanel);
        pagosFrame.setSize(800, 600);
        pagosFrame.setVisible(true);
    }

    /**
     * Cierra la ventana del carrito y regresa a la pantalla anterior.
     */
    private void cerrarVentana() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        if (frame != null) {
            frame.dispose(); // Cierra la ventana actual del carrito
        }
    }
}
