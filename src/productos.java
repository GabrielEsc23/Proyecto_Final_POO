import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class productos {
    public JPanel mainPanel;
    private JLabel nombre_1;
    private JLabel precio_1;
    private JLabel img_1;
    private JLabel nombre_2;
    private JLabel precio_2;
    private JLabel img_2;
    private JLabel nombre_3;
    private JLabel precio_3;
    private JLabel img_3;
    private JLabel nombre_4;
    private JLabel precio_4;
    private JLabel img_4;
    private JLabel nombre_5;
    private JLabel precio_5;
    private JLabel img_5;
    private JButton btnCargar;
    private JButton agregar_product1;
    private JButton agregar_product2;
    private JButton agregar_product3;
    private JButton agregar_product4;
    private JButton agregar_product5;
    private JButton verCarritoButton;

    // Lista para almacenar los productos del carrito
    private ArrayList<String> carritoProductos = new ArrayList<>();
    private ArrayList<Double> carritoPrecios = new ArrayList<>();

    public productos() {
        // Cargar datos desde la base de datos
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosDesdeBD();
            }
        });

        // Agregar acciones a los botones "Agregar al carrito"
        agregar_product1.addActionListener(e -> agregarAlCarrito(nombre_1.getText(), precio_1.getText()));
        agregar_product2.addActionListener(e -> agregarAlCarrito(nombre_2.getText(), precio_2.getText()));
        agregar_product3.addActionListener(e -> agregarAlCarrito(nombre_3.getText(), precio_3.getText()));
        agregar_product4.addActionListener(e -> agregarAlCarrito(nombre_4.getText(), precio_4.getText()));
        agregar_product5.addActionListener(e -> agregarAlCarrito(nombre_5.getText(), precio_5.getText()));

        // Acción del botón "Ver carrito"
        verCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCarrito();
            }
        });
    }

    private void agregarAlCarrito(String producto, String precio) {
        carritoProductos.add(producto);
        carritoPrecios.add(Double.parseDouble(precio.replace("Precio: $", ""))); // Extraer el valor numérico del precio
        JOptionPane.showMessageDialog(mainPanel, "Producto agregado al carrito: " + producto);
    }

    private void mostrarCarrito() {
        // Crear una nueva ventana de tipo "carrito"
        JFrame carritoFrame = new JFrame("Carrito de compras");
        carritoFrame.setContentPane(new carrito(carritoProductos,carritoPrecios).mainPanel);

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

            // Consulta SQL para obtener los primeros 5 productos
            String query = "SELECT nombre, precio FROM productos LIMIT 5";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            // Rutas específicas de las imágenes
            String[] rutasImagenes = {
                    "C:\\Users\\gabri\\IdeaProjects\\Proyecto_Final_POO\\imagenes\\camiseta.png",
                    "C:\\Users\\gabri\\IdeaProjects\\Proyecto_Final_POO\\imagenes\\collar.png",
                    "C:\\Users\\gabri\\IdeaProjects\\Proyecto_Final_POO\\imagenes\\chaqueta.png",
                    "C:\\Users\\gabri\\IdeaProjects\\Proyecto_Final_POO\\imagenes\\pantalon3.png",
                    "C:\\Users\\gabri\\IdeaProjects\\Proyecto_Final_POO\\imagenes\\camisa.png"
            };

            // Etiquetas para los productos
            JLabel[] nombres = {nombre_1, nombre_2, nombre_3, nombre_4, nombre_5};
            JLabel[] precios = {precio_1, precio_2, precio_3, precio_4, precio_5};
            JLabel[] imagenes = {img_1, img_2, img_3, img_4, img_5};

            int index = 0; // Índice para recorrer los labels
            while (rs.next() && index < nombres.length) {
                // Asignar nombre y precio
                nombres[index].setText("Producto: " + rs.getString("nombre"));
                precios[index].setText("Precio: $" + rs.getDouble("precio"));

                // Cargar la imagen desde la ruta específica
                ImageIcon imageIcon = new ImageIcon(rutasImagenes[index]);

                // Validar si la imagen existe
                if (imageIcon.getIconWidth() > 0) {
                    Image image = imageIcon.getImage();
                    Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Redimensionar
                    imagenes[index].setIcon(new ImageIcon(resizedImage)); // Establecer imagen redimensionada
                } else {
                    imagenes[index].setIcon(null);
                    System.out.println("Imagen no encontrada para el índice: " + index);
                }

                index++; // Incrementar el índice
            }

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
}
