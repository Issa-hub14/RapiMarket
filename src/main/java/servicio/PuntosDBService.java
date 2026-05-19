/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author isabe
 */
import java.sql.*;

public class PuntosDBService {

    private static final String URL = "jdbc:mysql://localhost:3306/rapimarket";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    
    public int actualizarPuntos(int idCliente, String nombreCliente, double totalCompra) {

        int puntosGanados = calcularPuntos(totalCompra);
        int puntosTotales = 0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (clienteExiste(conn, idCliente)) {
                puntosTotales = actualizarCliente(conn, idCliente, nombreCliente, totalCompra, puntosGanados);
            } else {
                puntosTotales = insertarCliente(conn, idCliente, nombreCliente, totalCompra, puntosGanados);
            }

        } catch (Exception e) {
            System.out.println("Error manejando puntos: " + e.getMessage());
        }

        return puntosTotales;
    }

    
    private int calcularPuntos(double totalCompra) {
        return (int) totalCompra / 1000;
    }

   
    private boolean clienteExiste(Connection conn, int idCliente) throws SQLException {

        String sql = "SELECT 1 FROM clientes_puntos WHERE id_cliente = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    
    private int actualizarCliente(Connection conn, int idCliente,String nombreCliente,double totalCompra,int puntosGanados) throws SQLException {

        String sqlSelect = """
                SELECT puntos, total_compras FROM clientes_puntos WHERE id_cliente = ?
                """;

        int puntosActuales;
        double totalActual;

        try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                puntosActuales = rs.getInt("puntos");
                totalActual = rs.getDouble("total_compras");
            }
        }

        int puntosTotales = puntosActuales + puntosGanados;
        double nuevoTotal = totalActual + totalCompra;

        String sqlUpdate = """
                UPDATE clientes_puntos SET nombre_cliente = ?, total_compras = ?, puntos = ? WHERE id_cliente = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            ps.setString(1, nombreCliente);
            ps.setDouble(2, nuevoTotal);
            ps.setInt(3, puntosTotales);
            ps.setInt(4, idCliente);

            ps.executeUpdate();
        }

        return puntosTotales;
    }

    
    private int insertarCliente(Connection conn, int idCliente, String nombreCliente,double totalCompra, int puntosGanados) throws SQLException {

        String sqlInsert = """
                INSERT INTO clientes_puntos(id_cliente, nombre_cliente, total_compras, puntos)VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setInt(1, idCliente);
            ps.setString(2, nombreCliente);
            ps.setDouble(3, totalCompra);
            ps.setInt(4, puntosGanados);

            ps.executeUpdate();
        }
        return puntosGanados;
    }
}