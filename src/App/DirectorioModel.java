/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mlope
 */
public class DirectorioModel {

    private Connection conn;

    public DirectorioModel() throws ClassNotFoundException {
        this.conn = Conexion.conectart();
    }

    public boolean guardar(String nombre, String apellidos, String correo, String celular) {
        try {
            String sql = "INSERT INTO directorios (nombre, apellidos, correo, celular) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, correo);
            statement.setString(4, celular);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean eliminar(int id) {
        try {
            String sql = "DELETE from directorios WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(int id, String nombre, String apellidos, String correo, String celular) {
        try {
            String sql = "UPDATE directorios SET nombre = ?, apellidos = ?, correo = ?, celular = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, correo);
            statement.setString(4, celular);
            statement.setInt(5, id);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<IDirectorio> listar() {
        String sql = "SELECT * FROM directorios";
        List<IDirectorio> data = new ArrayList<IDirectorio>();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString(1));
                IDirectorio fila = new IDirectorio();
                fila.setId(rs.getInt(1));
                fila.setNombre(rs.getString(2));
                fila.setApellidos(rs.getString(3));
                fila.setCorreo(rs.getString(4));
                fila.setCelular(rs.getString(5));
                data.add(fila);
            }
            return data;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
