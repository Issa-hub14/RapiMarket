/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author isabe
 */
import org.vosk.Model;
import org.vosk.Recognizer;
import javax.sound.sampled.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import util.LectorVoz;

public class ReceptorVozVosk {

    private static ReceptorVozVosk instancia;
    private boolean grabando = false;
    private TargetDataLine microphone;
    private Model model;
    private Recognizer recognizer;
    private Thread grabacionThread;
    private LectorVoz lectorVoz;

    private Consumer<String> manejadorTexto;
    private Consumer<Comando> manejadorComando;

    private String ultimoTextoReconocido = "";

    private boolean activo = true;

    private Map<String, Comando> mapaComandos = new HashMap<>();

    public enum Comando {
        BUSCAR, SIGUIENTE, ANTERIOR, REPETIR, AGREGAR, ELIMINAR, VOLVER
    }

    private ReceptorVozVosk() {
        lectorVoz = LectorVoz.getInstance();
        cargarModelo();
        inicializarComandos();
    }

    public static synchronized ReceptorVozVosk getInstance() {
        if (instancia == null) {
            instancia = new ReceptorVozVosk();
        }
        return instancia;
    }

    private void inicializarComandos() {
        mapaComandos.put("buscar", Comando.BUSCAR);
        mapaComandos.put("busca", Comando.BUSCAR);
        mapaComandos.put("siguiente", Comando.SIGUIENTE);
        mapaComandos.put("siguiente producto", Comando.SIGUIENTE);
        mapaComandos.put("anterior", Comando.ANTERIOR);
        mapaComandos.put("anterior producto", Comando.ANTERIOR);
        mapaComandos.put("repetir", Comando.REPETIR);
        mapaComandos.put("repite", Comando.REPETIR);
        mapaComandos.put("agregar", Comando.AGREGAR);
        mapaComandos.put("añadir", Comando.AGREGAR);
        mapaComandos.put("quitar", Comando.ELIMINAR);
        mapaComandos.put("eliminar", Comando.ELIMINAR);
        mapaComandos.put("volver", Comando.VOLVER);
        mapaComandos.put("regresar", Comando.VOLVER);
        mapaComandos.put("atrás", Comando.VOLVER);
    }

    private void cargarModelo() {
        try {
            // Cargar el modelo (¡Cambia esta ruta por la ruta donde descomprimiste tu modelo!)
            String rutaModelo = "C:/Users/isabe/Downloads/VozATextoApp/src/modelos/vosk-model-small-es-0.42/vosk-model-small-es-0.42";

            if (!new File(rutaModelo).exists()) {
                System.err.println("[Vosk] Modelo no encontrado en: " + rutaModelo);
                return;
            }
            //editado
            File modeloDir = new File(rutaModelo);
            if (!modeloDir.exists() || !modeloDir.isDirectory()) {
                System.err.println("[Vosk] Modelo NO encontrado en: " + rutaModelo);
                return;
            }

            // Verificar que tenga archivos
            if (modeloDir.listFiles().length == 0) {
                System.err.println("[Vosk] El directorio del modelo está vacío");
                return;
            }
            //editado
            model = new Model(rutaModelo);
            System.out.println("[Vosk] Modelo cargado correctamente");
        } catch (Exception e) {
            System.err.println("[Vosk] Error al cargar modelo: " + e.getMessage());
        }
    }

    public void setManejadorTexto(Consumer<String> manejador) {
        this.manejadorTexto = manejador;
    }

    public void setManejadorComando(Consumer<Comando> manejador) {
        this.manejadorComando = manejador;
    }

    private Comando textoAComando(String texto) {
        String textoLower = texto.toLowerCase().trim();
        textoLower = textoLower.replace("por favor", "").replace("quiero", "").trim();

        for (Map.Entry<String, Comando> entry : mapaComandos.entrySet()) {
            if (textoLower.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void iniciarGrabacion() {
        if (grabando || model == null) {
            System.err.println("[Vosk] No inicia: grabando=" + grabando + " model=" + model);
            return;
        }

        try {
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.err.println("[Vosk] Micrófono no soportado o no se encuentra.");
                return;
            }

            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            recognizer = new Recognizer(model, 16000);
            grabando = true;

            grabacionThread = new Thread(() -> {
                byte[] data = new byte[4096];

                while (grabando) {
                    try {
                        if (lectorVoz.isHablando()) {
                            recognizer.reset();
                            Thread.sleep(100);
                            continue;
                        }

                        int bytesLeidos = microphone.read(data, 0, data.length);

                        if (bytesLeidos <= 0) {
                            continue;
                        }

                        if (bytesLeidos > 0) {
                            if (recognizer.acceptWaveForm(data, bytesLeidos)) {
                                String resultado = recognizer.getResult();
                                System.out.println(
                                        "[Vosk] JSON FINAL: "
                                        + resultado
                                );
                                String texto = extraerTexto(resultado);
                                procesarTexto(texto);
                            } else {
                                // RESULTADO PARCIAL
                                String parcial = recognizer.getPartialResult();
                                String textoParcial = extraerParcial(parcial);

                                if (textoParcial != null && !textoParcial.isBlank()) {
                                    System.out.println("[Vosk] Parcial: " + textoParcial);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("[Vosk] Error en reconocimiento: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

            grabacionThread.start();
            System.out.println("[Vosk] Grabación iniciada");

        } catch (Exception e) {
            System.err.println("[Vosk] Error al iniciar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void procesarTexto(String texto) {

        if (texto == null || texto.isBlank()) {
            return;
        }
        ultimoTextoReconocido = texto;
        System.out.println("[Vosk] Texto reconocido: " + texto);
        Comando cmd = textoAComando(texto);

        if (cmd != null && manejadorComando != null) {
            System.out.println("[Vosk] Comando detectado: " + cmd);
            manejadorComando.accept(cmd);

        } else if (manejadorTexto != null) {
            manejadorTexto.accept(texto);
        }
    }

    private String extraerTexto(String json) {

        try {
            int inicio = json.indexOf("\"text\":\"") + 8;
            int fin = json.indexOf("\"", inicio);
            if (inicio > 8 && fin > inicio) {
                return json.substring(inicio, fin);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private String extraerParcial(String json) {

        try {
            int inicio = json.indexOf("\"partial\":\"") + 11;
            int fin = json.indexOf("\"", inicio);
            if (inicio > 11 && fin > inicio) {
                return json.substring(inicio, fin);
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void detenerGrabacion() {
        grabando = false;
        try {
            if (microphone != null) {
                microphone.stop();
            }

            if (grabacionThread != null && grabacionThread.isAlive()) {
                grabacionThread.join();
            }

            if (microphone != null) {
                microphone.close();
                microphone = null;
            }

            if (recognizer != null) {
                recognizer.close();
                recognizer = null;
            }
            System.out.println("[Vosk] Grabación detenida");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isGrabando() {
        return grabando;
    }

    public String getUltimoTextoReconocido() {
        return ultimoTextoReconocido;
    }

    public void cerrar() {
        detenerGrabacion();
        if (model != null) {
            model.close();
        }
    }
}
