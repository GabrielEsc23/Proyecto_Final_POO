import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que representa la ventana de productos en la aplicación.
 * Muestra los productos disponibles y permite al usuario agregar productos al carrito.
 */
public class productos {
    public JPanel mainPanel;
    private JButton verCarritoButton;
    private JButton crud_button; // Botón para abrir el menú CRUD
    private ArrayList<String> carritoProductos = new ArrayList<>();
    private ArrayList<Double> carritoPrecios = new ArrayList<>();
    private JPanel productosPanel; // Panel donde se mostrarán los productos

    /**
     * Constructor de la clase productos.
     * @param tipoUsuario Tipo de usuario (determina si se muestra el botón CRUD).
     */
    public productos(String tipoUsuario) {
        productosPanel = new JPanel();
        productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));

        verCarritoButton = new JButton("Ver carrito");
        crud_button = new JButton("CRUD");

        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new FlowLayout());
        botonesPanel.add(verCarritoButton);

        if ("administrador".equals(tipoUsuario)) {
            botonesPanel.add(crud_button);
        }

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(productosPanel), BorderLayout.CENTER);
        mainPanel.add(botonesPanel, BorderLayout.SOUTH);

        cargarDatosDesdeBD();

        verCarritoButton.addActionListener(e -> mostrarCarrito());
        crud_button.addActionListener(e -> mostrarMenuCrud());
    }

    /**
     * Carga los productos desde la base de datos y los muestra en la interfaz gráfica.
     */
    private void cargarDatosDesdeBD() {
        String url = "jdbc:mysql://localhost:3306/smart_shop";
        String user = "root";
        String password = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            String query = "SELECT id, nombre, precio, imagen FROM productos";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                JPanel productoPanel = new JPanel(new FlowLayout());
                String nombre = rs.getString("nombre");
                String precio = "$" + rs.getDouble("precio");
                String rutaImagen = rs.getString("imagen");

                JLabel nombreLabel = new JLabel("Producto: " + nombre);
                JLabel precioLabel = new JLabel("Precio: " + precio);
                JLabel imgLabel = new JLabel();

                if (rutaImagen != null) {
                    ImageIcon imageIcon = new ImageIcon(rutaImagen);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imgLabel.setIcon(new ImageIcon(image));
                }

                JButton agregarButton = new JButton("Agregar al carrito");
                agregarButton.addActionListener(e -> agregarAlCarrito(nombre, precio));

                productoPanel.add(nombreLabel);
                productoPanel.add(precioLabel);
                productoPanel.add(imgLabel);
                productoPanel.add(agregarButton);
                productosPanel.add(productoPanel);
            }

            productosPanel.revalidate();
            productosPanel.repaint();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "Error al cargar productos.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Agrega un producto al carrito de compras.
     * @param producto Nombre del producto a agregar.
     * @param precio Precio del producto en formato String.
     */
    private void agregarAlCarrito(String producto, String precio) {
        carritoProductos.add(producto);
        carritoPrecios.add(Double.parseDouble(precio.replace("$", "")));
        JOptionPane.showMessageDialog(mainPanel, "Producto agregado al carrito: " + producto);
    }

    /**
     * Muestra la ventana del carrito de compras.
     */
    private void mostrarCarrito() {
        JFrame carritoFrame = new JFrame("Carrito de compras");
        carritoFrame.setContentPane(new carrito(carritoProductos, carritoPrecios).mainPanel);
        carritoFrame.setSize(600, 300);
        carritoFrame.setVisible(true);
    }

    /**
     * Muestra el menú CRUD para administrar los productos.
     */
    private void mostrarMenuCrud() {
        JFrame crudFrame = new JFrame("Menú CRUD");
        crudFrame.setContentPane(new crud().mainPanel);
        crudFrame.setSize(600, 400);
        crudFrame.setVisible(true);
    }
}
