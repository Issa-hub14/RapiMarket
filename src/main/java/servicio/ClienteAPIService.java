/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author isabe
 */
import modelo.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 * Servicio encargado de consultar información de cliente
 * Mediante una API externa
 * 
 * @author isabe
 */
public class ClienteAPIService {

    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String API_URL = "https://dummyjson.com/users/";

    /**
     * Obtiene la infromación del cliente utilizando su ID
     * @param id Identificación del cliente
     * @return Cliente registrado obtenido desde la API o null si no se encuentra
     * @throws IOException Error durante la conexión con la API
     * @throws InterruptedException Error durante la ejecucución de la petición
     */
    public ClienteRegistrado getCliente(int id) throws IOException, InterruptedException {

        // Construir petición
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(API_URL + id))
                .header("Accept", "application/json")
                .build();

        // Enviar petición
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Error: " + response.statusCode());
            return null;
        }

        String jsonString = response.body();

        JSONObject jsonObject = new JSONObject(jsonString);
        String nombre = jsonObject.getString("firstName") + " " + jsonObject.getString("lastName");

        int identificacion = jsonObject.getInt("id");

        String direccion = jsonObject.getJSONObject("address").getString("address");

        String telefono = jsonObject.getString("phone");

        String correo = jsonObject.getString("email");
        // Crear cliente
        ClienteRegistrado cliente = new ClienteRegistrado(
                nombre,
                identificacion,
                direccion,
                telefono,
                correo
        );

        return cliente;
    }
}
