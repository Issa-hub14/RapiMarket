/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author isabe
 */
import modelo.ModeloMicrofono;
import java.util.*;
import java.util.function.Consumer;

public class ReceptorVoz {

    public enum Comando {
        BUSCAR, SIGUIENTE, ANTERIOR, REPETIR,
        AGREGAR, ELIMINAR, CONFIRMAR,
        VOLVER, LEER_CARRITO, DESCONOCIDO
    }

    private static final Map<String, Comando> VOCABULARIO = new LinkedHashMap<>();

    static {
        VOCABULARIO.put("buscar", Comando.BUSCAR);
        VOCABULARIO.put("siguiente", Comando.SIGUIENTE);
        VOCABULARIO.put("anterior", Comando.ANTERIOR);
        VOCABULARIO.put("repetir", Comando.REPETIR);
        VOCABULARIO.put("agregar", Comando.AGREGAR);
        VOCABULARIO.put("añadir", Comando.AGREGAR);
        VOCABULARIO.put("eliminar", Comando.ELIMINAR);
        VOCABULARIO.put("quitar", Comando.ELIMINAR);
        VOCABULARIO.put("confirmar", Comando.CONFIRMAR);
        VOCABULARIO.put("volver", Comando.VOLVER);
        VOCABULARIO.put("carrito", Comando.LEER_CARRITO);
    }

    private final ModeloMicrofono microfono;
    private final LectorVoz lectorVoz;

    private Consumer<Comando> manejadorComando;
    private Consumer<String> manejadorTexto;
    private boolean grabando = false;

    public ReceptorVoz() {
        this.microfono = new ModeloMicrofono();
        this.lectorVoz = LectorVoz.getInstance();
    }

    public void setManejadorComando(Consumer<Comando> m) {
        this.manejadorComando = m;
    }

    public void setManejadorTexto(Consumer<String> m) {
        this.manejadorTexto = m;
    }

    public void iniciarGrabacion() {
        if (grabando) {
            return;
        }
        try {
            microfono.iniciarCaptura();
            grabando = true;
            lectorVoz.hablar("Escuchando. Habla ahora.");
            System.out.println("[ReceptorVoz] Grabación iniciada.");
        } catch (Exception e) {
            System.err.println("[ReceptorVoz] Error al iniciar mic: " + e.getMessage());
            lectorVoz.hablar("No se pudo acceder al micrófono.");
        }
    }

    public void detenerYProcesar() {
        if (!grabando) {
            return;
        }
        microfono.detenerCaptura();
        grabando = false;
        System.out.println("[ReceptorVoz] Grabación detenida. Analizando...");

        byte[] bytes = microfono.getBytesGrabados();
        System.out.println("[ReceptorVoz] Bytes capturados: " + bytes.length);

        if (bytes.length == 0) {
            lectorVoz.hablar("No escuché nada. Intenta de nuevo.");
            return;
        }

        lectorVoz.hablar("Audio recibido. Escribe el comando en consola.");
        procesarDesdeConsolaUnaVez();
    }

    private void procesarDesdeConsolaUnaVez() {
        new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(">>> Comando escuchado (simula Sphinx): ");
                if (scanner.hasNextLine()) {
                    String texto = scanner.nextLine().toLowerCase().trim();
                    procesarTexto(texto);
                }
            } catch (Exception e) {
                System.err.println("[ReceptorVoz] Error leyendo consola: "
                        + e.getMessage());
            }
        }).start();
    }

    private void procesarTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return;
        }
        texto = texto.toLowerCase().trim();

        for (Map.Entry<String, Comando> entrada : VOCABULARIO.entrySet()) {
            String palabra = entrada.getKey();
            if (texto.startsWith(palabra)) {
                Comando cmd = entrada.getValue();
                String restante = texto.replaceFirst(palabra, "").trim();
                System.out.println("[Comando detectado]: " + cmd);

                if (manejadorComando != null) {
                    manejadorComando.accept(cmd);
                }
                // Si hay algo después → también lo procesa como texto
                if (!restante.isEmpty() && manejadorTexto != null) {
                    manejadorTexto.accept(restante);
                }
                return;
            }
        }

        System.out.println("[Texto libre]: " + texto);
        if (manejadorTexto != null) {
            manejadorTexto.accept(texto);
        }
    }

    public boolean isGrabando() {
        return grabando;
    }
}

