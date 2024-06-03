/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenparte1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adriansobrevela
 */
public class VehiculoDAO {
    private Connection connection;

    public VehiculoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Vehiculo> consultarTodos() throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM vehiculos";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Vehiculo vehiculo = new Vehiculo(
                rs.getInt("id"),
                rs.getString("tipo"),
                rs.getString("marca"),
                rs.getInt("potencia"),
                rs.getDate("fecha_compra")
            );
            vehiculos.add(vehiculo);
        }
        return vehiculos;
    }

    public Vehiculo consultarPorId(int id) throws SQLException {
        String query = "SELECT * FROM vehiculos WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return new Vehiculo(
                rs.getInt("id"),
                rs.getString("tipo"),
                rs.getString("marca"),
                rs.getInt("potencia"),
                rs.getDate("fecha_compra")
            );
        }
        return null;
    }

    public void insertarVehiculo(Vehiculo vehiculo) throws SQLException {
        String query = "INSERT INTO vehiculos (tipo, marca, potencia, fecha_compra) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, vehiculo.getTipo());
        pstmt.setString(2, vehiculo.getMarca());
        pstmt.setInt(3, vehiculo.getPotencia());
        pstmt.setDate(4, new java.sql.Date(vehiculo.getFechaCompra().getTime()));
        pstmt.executeUpdate();
    }

    public void eliminarVehiculo(int id) throws SQLException {
        String query = "DELETE FROM vehiculos WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public void actualizarVehiculo(Vehiculo vehiculo) throws SQLException {
        String query = "UPDATE vehiculos SET tipo = ?, marca = ?, potencia = ?, fecha_compra = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, vehiculo.getTipo());
        pstmt.setString(2, vehiculo.getMarca());
        pstmt.setInt(3, vehiculo.getPotencia());
        pstmt.setDate(4, new java.sql.Date(vehiculo.getFechaCompra().getTime()));
        pstmt.setInt(5, vehiculo.getId());
        pstmt.executeUpdate();
    }

    public double obtenerPotenciaMedia(String tipo) throws SQLException {
        String query = "SELECT AVG(potencia) FROM vehiculos WHERE tipo = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, tipo);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0.0;
    }

    public int obtenerPotenciaMaxima(String tipo) throws SQLException {
        String query = "SELECT MAX(potencia) FROM vehiculos WHERE tipo = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, tipo);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
