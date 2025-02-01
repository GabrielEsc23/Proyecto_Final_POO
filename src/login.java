import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class login {
    public JPanel mainPanel;
    private JTextField txt_email;
    private JTextField txt_contra;
    private JComboBox<String> txt_tipo_user;
    private JButton ingresar_button;

    public login() {
        // Configurar JComboBox con los tipos de usuario disponibles


        // Acción del botón "Ingresar"
        ingresar_button.addActionListener(e -> {
            String email = txt_email.getText();
            String password = txt_contra.getText();
            String tipoUsuario = (String) txt_tipo_user.getSelectedItem();

            if (validarCredenciales(email, password, tipoUsuario)) {
                JOptionPane.showMessageDialog(mainPanel, "Bienvenido, " + tipoUsuario);

                // Abrir la ventana de productos según el tipo de usuario
                JFrame productosFrame = new JFrame("Productos");
                productos productosPanel = new productos(tipoUsuario);
                productosFrame.setContentPane(productosPanel.mainPanel);
                productosFrame.setSize(800, 800);
                productosFrame.setVisible(true);

                // Cerrar la ventana de login
                SwingUtilities.getWindowAncestor(mainPanel).dispose();
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Credenciales incorrectas.");
            }
        });
    }

    private boolean validarCredenciales(String email, String password, String tipoUsuario) {
        String url = "jdbc:mysql://localhost:3306/smart_shop"; // Cambia a tu base de datos
        String user = "root"; // Cambia a tu usuario
        String dbPassword = ""; // Cambia a tu contraseña
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Conexión a la base de datos
            con = DriverManager.getConnection(url, user, dbPassword);

            // Consulta SQL para verificar el email, contraseña y tipo de usuario
            String query = "SELECT * FROM usuarios WHERE email = ? AND password= ? AND tipo_usuario = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, tipoUsuario);

            rs = ps.executeQuery();

            // Si se encuentra un registro, las credenciales son válidas
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "Error al conectar con la base de datos.");
        } finally {
            // Cerrar los recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false; // Si no se encuentra un registro, las credenciales son inválidas
    }
}
