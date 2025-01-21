import javax.swing.*;
import java.sql.*;

public class crud {
    public JPanel mainPanel; // Cambiar acceso para permitir integración con otras clases
    private JTextField int_id;
    private JTextField varchar_nombre;
    private JTextField decimal_precio;
    private JTextField varchar_img;
    private JButton leerButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton crearButton;

    // Conexión a la base de datos
    private final String url = "jdbc:mysql://localhost:3306/smart_shop"; // Cambia según tu configuración
    private final String user = "root"; // Cambia a tu usuario
    private final String password = ""; // Cambia a tu contraseña

    private Runnable actualizarVistaProductos; // Callback para actualizar la vista en productos.java

    public crud() {
        leerButton.addActionListener(e -> leerProducto());
        actualizarButton.addActionListener(e -> actualizarProducto());
        eliminarButton.addActionListener(e -> eliminarProducto());
        crearButton.addActionListener(e -> crearProducto());
    }

    // Método para establecer el callback de actualización
    public void setActualizarVistaProductos(Runnable actualizarVistaProductos) {
        this.actualizarVistaProductos = actualizarVistaProductos;
    }

    private void leerProducto() {
        String id = int_id.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Por favor, ingresa el ID del producto.");
            return;
        }

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM productos WHERE id = ?")) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                varchar_nombre.setText(rs.getString("nombre"));
                decimal_precio.setText(rs.getString("precio"));
                varchar_img.setText(rs.getString("imagen"));
                JOptionPane.showMessageDialog(mainPanel, "Producto encontrado.");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Producto no encontrado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "Error al leer el producto.");
        }
    }

    private void actualizarProducto() {
        String id = int_id.getText();
        String nombre = varchar_nombre.getText();
        String precio = decimal_precio.getText();
        String img = varchar_img.getText();

        if (id.isEmpty() || nombre.isEmpty() || precio.isEmpty() || img.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Por favor, completa todos los campos.");
            return;
        }

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE productos SET nombre = ?, precio = ?, imagen = ? WHERE id = ?")) {
            ps.setString(1, nombre);
            ps.setBigDecimal(2, new java.math.BigDecimal(precio)); // Usamos BigDecimal para el precio
            ps.setString(3, img);
            ps.setInt(4, Integer.parseInt(id));

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(mainPanel, "Producto actualizado exitosamente.");
                if (actualizarVistaProductos != null) {
                    actualizarVistaProductos.run(); // Actualizar vista
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "No se encontró el producto con el ID proporcionado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "Error al actualizar el producto.");
        }
    }

    private void eliminarProducto() {
        String id = int_id.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Por favor, ingresa el ID del producto.");
            return;
        }

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement("DELETE FROM productos WHERE id = ?")) {
            ps.setInt(1, Integer.parseInt(id));

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(mainPanel, "Producto eliminado exitosamente.");
                if (actualizarVistaProductos != null) {
                    actualizarVistaProductos.run(); // Actualizar vista
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "No se encontró el producto con el ID proporcionado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "Error al eliminar el producto.");
        }
    }

    private void crearProducto() {
        // Obtener los valores de los campos
        String nombre = varchar_nombre.getText();
        String precio = decimal_precio.getText();
        String img = varchar_img.getText();

        // Validación de campos
        if (nombre.isEmpty() || precio.isEmpty() || img.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Por favor, completa todos los campos.");
            return;
        }

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement("INSERT INTO productos (nombre, precio, imagen) VALUES (?, ?, ?)")) {

            ps.setString(1, nombre);
            ps.setBigDecimal(2, new java.math.BigDecimal(precio)); // Usamos BigDecimal para el precio
            ps.setString(3, img);

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(mainPanel, "Producto creado exitosamente.");
                if (actualizarVistaProductos != null) {
                    actualizarVistaProductos.run(); // Actualizar vista
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Error al crear el producto.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "Error al conectar con la base de datos.");
        }
    }
}
