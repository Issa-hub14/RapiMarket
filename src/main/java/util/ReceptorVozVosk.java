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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceptorVozVosk {

    private static ReceptorVozVosk instancia;
    private boolean grabando = false;
    private TargetDataLine microphone;
    private Model model;
    private Recognizer recognizer;
    private Thread grabacionThread;
    
    private StringBuilder textoAcumulado = new StringBuilder();
    private Consumer<String> manejadorTexto;
    private Consumer<Comando> manejadorComando;

    private Map<String, Comando> mapaComandos = new HashMap<>();

    public enum Comando {
        BUSCAR, SIGUIENTE, ANTERIOR, REPETIR, AGREGAR, ELIMINAR, VOLVER, LEER_CARRITO, CONFIRMAR
    }

    private ReceptorVozVosk() {
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
        mapaComandos.put("agregar", Comando.AGREGAR);
        mapaComandos.put("agrega", Comando.AGREGAR);
        mapaComandos.put("buscar", Comando.BUSCAR);
        mapaComandos.put("busca", Comando.BUSCAR);
        mapaComandos.put("siguiente", Comando.SIGUIENTE);
        mapaComandos.put("anterior", Comando.ANTERIOR);
        mapaComandos.put("repetir", Comando.REPETIR);
        mapaComandos.put("repite", Comando.REPETIR);
        mapaComandos.put("quitar", Comando.ELIMINAR);
        mapaComandos.put("eliminar", Comando.ELIMINAR);
        mapaComandos.put("volver", Comando.VOLVER);
        mapaComandos.put("regresar", Comando.VOLVER);
        mapaComandos.put("carrito", Comando.LEER_CARRITO);
        mapaComandos.put("confirmar", Comando.CONFIRMAR);
    }

    private void cargarModelo() {
        try {
            String rutaModelo = "C:/Users/isabe/Downloads/VozATextoApp/src/modelos/vosk-model-small-es-0.42/vosk-model-small-es-0.42";
            model = new Model(rutaModelo);
            System.out.println("[Vosk] Modelo cargado");
        } catch (Exception e) {
            System.err.println("[Vosk] Error: " + e.getMessage());
        }
    }

    public void setManejadorTexto(Consumer<String> manejador) {
        this.manejadorTexto = manejador;
    }

    public void setManejadorComando(Consumer<Comando> manejador) {
        this.manejadorComando = manejador;
    }

    private String extraerTextoDelJson(String json) {
        Pattern pattern = Pattern.compile("\"text\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private Comando textoAComando(String texto) {
        if (texto == null || texto.isBlank()) return null;
        String textoLower = texto.toLowerCase().trim();
        for (Map.Entry<String, Comando> entry : mapaComandos.entrySet()) {
            if (textoLower.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private String extraerProducto(String texto, Comando cmd) {
        if (cmd == Comando.AGREGAR) {
            return texto.replaceFirst("(?i)agregar|agrega", "").trim();
        } else if (cmd == Comando.BUSCAR) {
            return texto.replaceFirst("(?i)buscar|busca", "").trim();
        } else if (cmd == Comando.ELIMINAR) {
            return texto.replaceFirst("(?i)quitar|eliminar", "").trim();
        }
        return "";
    }

    public void iniciarGrabacion() {
        if (grabando || model == null) return;

        try {
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            recognizer = new Recognizer(model, 16000);
            grabando = true;
            textoAcumulado = new StringBuilder();

            grabacionThread = new Thread(() -> {
                byte[] data = new byte[4096];
                long inicioGrabacion = System.currentTimeMillis();
                int duracion = 5000; // 5 segundos

                while (grabando && System.currentTimeMillis() - inicioGrabacion < duracion) {
                    try {
                        if (microphone.available() > 0) {
                            int bytesLeidos = microphone.read(data, 0, data.length);
                            if (bytesLeidos > 0 && recognizer != null) {
                                recognizer.acceptWaveForm(data, bytesLeidos);
                            }
                        }
                        Thread.sleep(10);
                    } catch (Exception e) {}
                }
                
                
                String json = recognizer.getResult();
                String texto = extraerTextoDelJson(json);
                
                if (!texto.isEmpty()) {
                    System.out.println("[Vosk] Texto: " + texto);
                    Comando cmd = textoAComando(texto);
                    
                    if (cmd != null && manejadorComando != null) {
                        System.out.println("[Vosk] Comando: " + cmd);
                        manejadorComando.accept(cmd);
                        
                        String producto = extraerProducto(texto, cmd);
                        if (!producto.isEmpty() && manejadorTexto != null) {
                            manejadorTexto.accept(producto);
                        }
                    } else if (manejadorTexto != null) {
                        manejadorTexto.accept(texto);
                    }
                }
                
                grabando = false;
                detenerGrabacion();
            });
            
            grabacionThread.start();
        } catch (Exception e) {
            System.err.println("[Vosk] Error: " + e.getMessage());
        }
    }

    public void detenerGrabacion() {
        grabando = false;
        try {
            if (recognizer != null) {
                recognizer.close();
                recognizer = null;
            }
            if (microphone != null) {
                microphone.close();
                microphone = null;
            }
        } catch (Exception e) {}
    }

    public boolean isGrabando() {
        return grabando;
    }

    public void cerrar() {
        detenerGrabacion();
        if (model != null) {
            model.close();
        }
    }  
}