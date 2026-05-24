/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import org.vosk.Model;
import org.vosk.Recognizer;
import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase encargada de reconocer comandos y texto por voz utilizando el moelo vosk.
 * Implementa el patrón Singleton para mantener una única instancia de Receptor de voz
 * 
 * @author isabe
 */
public class ReceptorVozVosk {

    private static ReceptorVozVosk instancia;
    private boolean grabando = false;
    private TargetDataLine microphone;
    private Model model;
    private Recognizer recognizer;
    private Thread grabacionThread;

    private Consumer<String> manejadorTexto;
    private Consumer<Comando> manejadorComando;

    private Map<String, Comando> mapaComandos = new HashMap<>();

    private boolean enviarProductoParaAgregar = false;
    private boolean enviarProductoParaEliminar = false;

    /**
     * Enumeración de comandos de voz disponibles
     */
    public enum Comando {
        BUSCAR, SIGUIENTE, ANTERIOR, REPETIR, AGREGAR, ELIMINAR, VOLVER, LEER_CARRITO, CONFIRMAR, LEER, CONTINUAR
    }

    /**
     * Constructor privado para implementar Singleton
     */
    private ReceptorVozVosk() {
        cargarModelo();
        inicializarComandos();
    }

    /**
     * Define si enviarán productos para agregar
     * @param enviar Estado de envio de productos
     */
    public void setEnviarProductoParaAgregar(boolean enviar) {
        this.enviarProductoParaAgregar = enviar;
    }
    /**
     * Define si enviarán productos para eliminar
     * @param enviar Estado de envio de productos
     */
    public void setEnviarProductoParaEliminar(boolean enviar) {
        this.enviarProductoParaEliminar = enviar;
    }

    /**
     * Obtiene la instancia única del receptor de voz
     * @return Instancia de receptorVozVosk
     */
    public static synchronized ReceptorVozVosk getInstance() {
        if (instancia == null) {
            instancia = new ReceptorVozVosk();
        }
        return instancia;
    }

    /**
     * Inicializa el mapa de comandos disponibles
     */
    private void inicializarComandos() {
        mapaComandos.put("agregar", Comando.AGREGAR);
        mapaComandos.put("agrega", Comando.AGREGAR);
        mapaComandos.put("buscar", Comando.BUSCAR);
        mapaComandos.put("busca", Comando.BUSCAR);
        mapaComandos.put("siguiente", Comando.SIGUIENTE);
        mapaComandos.put("repetir", Comando.REPETIR);
        mapaComandos.put("repite", Comando.REPETIR);
        mapaComandos.put("quitar", Comando.ELIMINAR);
        mapaComandos.put("eliminar", Comando.ELIMINAR);
        mapaComandos.put("volver", Comando.VOLVER);
        mapaComandos.put("regresar", Comando.VOLVER);
        mapaComandos.put("carrito", Comando.LEER_CARRITO);
        mapaComandos.put("confirmar", Comando.CONFIRMAR);
        mapaComandos.put("leer", Comando.LEER);
        mapaComandos.put("lee", Comando.LEER);
        mapaComandos.put("continuar", Comando.CONTINUAR);
        mapaComandos.put("continua", Comando.CONTINUAR);
    }

    /**
     * Carga el modelo de reconocimiento vosk
     */
    private void cargarModelo() {
        try {
            String rutaModelo = "C:/Users/isabe/Downloads/VozATextoApp/src/modelos/vosk-model-small-es-0.42/vosk-model-small-es-0.42";
            model = new Model(rutaModelo);
            System.out.println("[Vosk] Modelo cargado");
        } catch (Exception e) {
            System.err.println("[Vosk] Error: " + e.getMessage());
        }
    }

    /**
     * Define el manejador para procesar texto reconocido
     * @param manejador Función encargada de procesar texto
     */
    public void setManejadorTexto(Consumer<String> manejador) {
        this.manejadorTexto = manejador;
    }

    /**
     * Define el manejador para procesar comandos reconocidos 
     * @param manejador Función encargada de procesar comandos 
     */
    public void setManejadorComando(Consumer<Comando> manejador) {
        this.manejadorComando = manejador;
    }

    /**
     * Extraer texto reconocido de un Json
     * @param json Texto Json generado por vosk
     * @return texto reconocido
     */
    private String extraerTextoDelJson(String json) {
        Pattern pattern = Pattern.compile("\"text\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /**
     * Convierte un texto en un comando reconocido
     * @param texto Texto reconocido por vosk
     * @return Correspondiente o null
     */
    private Comando textoAComando(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }
        String textoLower = texto.toLowerCase().trim();
        for (Map.Entry<String, Comando> entry : mapaComandos.entrySet()) {
            if (textoLower.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Extrae el nombre de un producto desde un comando
     * @param texto texto reconocido
     * @param cmd comando indicado
     * @return nombre del producto extraido
     */
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

    /**
     * Inicia la grabación y reconocimiento de voz 
     */
    public void iniciarGrabacion() {
        if (grabando || model == null) {
            return;
        }

        try {
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            recognizer = new Recognizer(model, 16000);
            grabando = true;

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
                    } catch (Exception e) {
                    }
                }

                String json = recognizer.getResult();
                String texto = extraerTextoDelJson(json);

                if (!texto.isEmpty()) {
                    System.out.println("[Vosk] Texto: " + texto);
                    Comando cmd = textoAComando(texto);

                    if (cmd != null && manejadorComando != null) {
                        System.out.println("[Vosk] Comando: " + cmd);
                        manejadorComando.accept(cmd);

                        boolean enviarProducto = false;

                        if (cmd == Comando.BUSCAR) {
                            enviarProducto = true;  
                        } else if (cmd == Comando.AGREGAR) {
                            enviarProducto = enviarProductoParaAgregar; 
                        } else if (cmd == Comando.ELIMINAR) {
                            enviarProducto = enviarProductoParaEliminar;  
                        }

                        if (enviarProducto) {
                            String producto = extraerProducto(texto, cmd);
                            if (!producto.isEmpty() && manejadorTexto != null) {
                                manejadorTexto.accept(producto);
                            }
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

    /**
     * Detinene la grabación y libera recursos del micrófono
     */
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
        } catch (Exception e) {
        }
    }

    /**
     * Verifica si actualmente se está grabando un audio
     * @return True si se está grabando, false en caso contrario
     */
    public boolean isGrabando() {
        return grabando;
    }

}
