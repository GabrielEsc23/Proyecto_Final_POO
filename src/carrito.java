import javax.swing.*;
import java.util.ArrayList;

public class carrito {
    public JPanel mainPanel;
    private JList<String> listaProductos;
    private JTextArea mostrar_precio;
    private JButton irAPagarButton;
    private JButton quitarProductoButton;
    private JButton regresarButton;
    private DefaultListModel<String> modeloLista;

    // Constructor
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

    // Cargar datos en la lista del carrito
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

    // Método para quitar producto del carrito
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

    // Método para abrir la ventana de pagos
    private void abrirPagos(ArrayList<String> productos, ArrayList<Double> precios) {
        JFrame pagosFrame = new JFrame("Pago");
        pagosFrame.setContentPane(new pagos(productos, precios).mainPanel);
        pagosFrame.setSize(600, 400);
        pagosFrame.setVisible(true);
    }

    // Método para cerrar la ventana del carrito y volver a productos sin abrir una nueva
    private void cerrarVentana() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        if (frame != null) {
            frame.dispose(); // Cierra la ventana actual del carrito
        }
    }
}
