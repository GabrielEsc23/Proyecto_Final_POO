import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class productos {
    public JPanel mainPanel;
    private JButton verCarritoButton;
    private JButton crud_button; // Botón para abrir el menú CRUD
    private ArrayList<String> carritoProductos = new ArrayList<>();
    private ArrayList<Double> carritoPrecios = new ArrayList<>();
    private JPanel productosPanel; // Panel donde se mostrarán los productos

    public productos(String tipoUsuario) {
        // Inicializar productosPanel
        productosPanel = new JPanel();
        productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));

        // Crear botones
        verCarritoButton = new JButton("Ver carrito");
        crud_button = new JButton("CRUD");

        // Panel para botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new FlowLayout());
        botonesPanel.add(verCarritoButton);

        // Mostrar el botón CRUD solo para administradores
        if ("administrador".equals(tipoUsuario)) {
            botonesPanel.add(crud_button);
        }

        // Configurar el mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(productosPanel), BorderLayout.CENTER);
        mainPanel.add(botonesPanel, BorderLayout.SOUTH);

        // Cargar productos desde la base de datos
        cargarDatosDesdeBD();

        // Acción del botón "Ver carrito"
        verCarritoButton.addActionListener(e -> mostrarCarrito());

        // Acción del botón "CRUD"
        crud_button.addActionListener(e -> mostrarMenuCrud());
    }

    private void cargarDatosDesdeBD() {
        String url = "jdbc:mysql://localhost:3306/smart_shop"; // Cambia a tu base de datos
        String user = "root"; // Cambia a tu usuario
        String password = ""; // Cambia a tu contraseña
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Conexión a la base de datos
            con = DriverManager.getConnection(url, user, password);

            // Consulta para obtener productos
            String query = "SELECT id, nombre, precio, imagen FROM productos";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            // Recorrer los resultados
            while (rs.next()) {
                JPanel productoPanel = new JPanel(new FlowLayout());

                String nombre = rs.getString("nombre");
                String precio = "$" + rs.getDouble("precio");
                String rutaImagen = rs.getString("imagen");

                JLabel nombreLabel = new JLabel("Producto: " + nombre);
                JLabel precioLabel = new JLabel("Precio: " + precio);
                JLabel imgLabel = new JLabel();

                // Cargar imagen
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

    private void agregarAlCarrito(String producto, String precio) {
        carritoProductos.add(producto);
        carritoPrecios.add(Double.parseDouble(precio.replace("$", "")));
        JOptionPane.showMessageDialog(mainPanel, "Producto agregado al carrito: " + producto);
    }

    private void mostrarCarrito() {
        JFrame carritoFrame = new JFrame("Carrito de compras");
        carritoFrame.setContentPane(new carrito(carritoProductos, carritoPrecios).mainPanel);
        carritoFrame.setSize(700, 300);
        carritoFrame.setVisible(true);
    }

    private void mostrarMenuCrud() {
        JFrame crudFrame = new JFrame("Menú CRUD");
        crudFrame.setContentPane(new crud().mainPanel);
        crudFrame.setSize(400, 400);
        crudFrame.setVisible(true);
    }
}