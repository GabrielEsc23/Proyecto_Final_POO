import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class productos {
    public JPanel mainPanel;
    private JButton verCarritoButton;
    private JButton crud_button; // El botón para abrir el menú CRUD

    // Lista para almacenar los productos del carrito
    private ArrayList<String> carritoProductos = new ArrayList<>();
    private ArrayList<Double> carritoPrecios = new ArrayList<>();

    private JPanel productosPanel; // Panel donde se mostrarán los productos

    public productos() {
        // Inicializar el productosPanel antes de usarlo
        productosPanel = new JPanel();
        productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS)); // Organizar productos verticalmente

        // Configurar el mainPanel para contener productosPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(productosPanel), BorderLayout.CENTER); // Asegura que se pueda hacer scroll

        // Llamar al método para cargar los datos del catálogo automáticamente
        cargarDatosDesdeBD();

        // Acción del botón "Ver carrito"
        verCarritoButton.addActionListener(e -> mostrarCarrito());

        // Acción del botón "CRUD" para abrir el menú de operaciones CRUD
        crud_button.addActionListener(e -> mostrarMenuCrud());
    }

    private void agregarAlCarrito(String producto, String precio) {
        carritoProductos.add(producto);
        carritoPrecios.add(Double.parseDouble(precio.replace("Precio: $", ""))); // Extraer el valor numérico del precio
        JOptionPane.showMessageDialog(mainPanel, "Producto agregado al carrito: " + producto);
    }

    private void mostrarCarrito() {
        // Crear una nueva ventana de tipo "carrito"
        JFrame carritoFrame = new JFrame("Carrito de compras");
        carritoFrame.setContentPane(new carrito(carritoProductos, carritoPrecios).mainPanel);

        carritoFrame.setSize(700, 300);
        carritoFrame.setVisible(true);
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
            System.out.println("Conexión exitosa");

            // Consulta SQL para obtener los productos, incluyendo la ruta de la imagen
            String query = "SELECT id, nombre, precio, imagen FROM productos"; // Consulta para todos los productos
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            // Recorrer los resultados y crear dinámicamente los productos
            while (rs.next()) {
                // Crear panel para cada producto
                JPanel productoPanel = new JPanel();
                productoPanel.setLayout(new FlowLayout()); // Layout para cada producto (puedes cambiarlo)

                // Crear etiquetas para el nombre, precio e imagen
                String nombre = rs.getString("nombre");
                String precio = "$" + rs.getDouble("precio");
                String rutaImagen = rs.getString("imagen");

                JLabel nombreLabel = new JLabel("Producto: " + nombre);
                JLabel precioLabel = new JLabel("Precio: " + precio);

                // Cargar la imagen desde la ruta
                ImageIcon imageIcon = new ImageIcon(rutaImagen);
                Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(image));

                // Agregar los componentes al panel del producto
                productoPanel.add(nombreLabel);
                productoPanel.add(precioLabel);
                productoPanel.add(imgLabel);

                // Botón de agregar al carrito
                JButton agregarButton = new JButton("Agregar al carrito");
                agregarButton.addActionListener(e -> agregarAlCarrito(nombre, precio));
                productoPanel.add(agregarButton);

                // Agregar el panel del producto al panel principal
                productosPanel.add(productoPanel);
            }

            // Actualizar la interfaz
            productosPanel.revalidate();
            productosPanel.repaint();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "Error al conectar con la base de datos.");
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Método para mostrar el menú CRUD
    private void mostrarMenuCrud() {
        // Crear una nueva ventana para el CRUD
        JFrame crudFrame = new JFrame("Menú CRUD");
        crudFrame.setContentPane(new crud().mainPanel); // Usamos el panel de la clase crud.java
        crud crudPanel = new crud();
        crudPanel.setActualizarVistaProductos(this::cargarDatosDesdeBD); // Establecer el callback

        crudFrame.setSize(400, 400); // Tamaño del CRUD
        crudFrame.setVisible(true);
    }
}
