/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import java.sql.*;

/**
 * Servicio encargado de gestionar los puntos acumulados de los cleintes en las bases de datos.
 * 
 * @author isabe
 */
public class PuntosDBService {

    private static final String URL = "jdbc:mysql://localhost:3306/rapimarket_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Actualiza los puntos de un cliente despues de una compra
     * @param idCliente Identificación del cliente
     * @param nombreCliente Nombre del cliente
     * @param totalCompra Valor total de la compra
     * @return Total de puntos acumulados por el cliente
     */
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

    /**
     * Calcula la cantidad de puntos obtenidos según el valor de la compra
     * @param totalCompra Valor total de la compra 
     * @return Cantidad de puntos ganados
     */
    private int calcularPuntos(double totalCompra) {
        return (int) totalCompra / 1000;
    }

    /**
     * Verifica si un cliente ya existe en la base de datos
     * @param conn Conexión activa a la base de datos
     * @param idCliente Identificación del cliente
     * @return True si el cliente existe, False en caso contrario
     * @throws SQLException Error durante la consulta sql
     */
    private boolean clienteExiste(Connection conn, int idCliente) throws SQLException {

        String sql = "SELECT 1 FROM clientes_puntos WHERE id_cliente = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Actualiza la información y puntos de un cliente existente
     * @param conn Conexión activa a la base de datos
     * @param idCliente Identificación del cliente
     * @param nombreCliente Nombre del cliente
     * @param totalCompra Valor total de la compra
     * @param puntosGanados Puntos obtenidos en la compra
     * @return Total de puntos acumulados
     * @throws SQLException Error durante la actualización SQL
     */
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

    /**
     * Inserta un nuevo cliente en la base de datos
     * @param conn Conexión activa a la base de datos
     * @param idCliente Identificación del cliente
     * @param nombreCliente Nombre del cliente
     * @param totalCompra Valor total de la compra
     * @param puntosGanados Puntos obtenidos en la compra
     * @return Cantidad de puntos acumulados
     * @throws SQLException Error durante la inserción SQL
     */
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