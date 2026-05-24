/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Clase encargada de reproducir texto mediante voz
 * Implementa el patrón Singleton para garantizar una única instancia del lector de voz
 * 
 * @author isabe
 */
public class LectorVoz implements IReproducible {

    private static LectorVoz instancia;
    private boolean activo = true;
    private volatile boolean hablando = false;
    private Process procesoActual;

    private LectorVoz() {
    }

    /**
     * Obtiene la instancia única del lector de voz
     * @return Instancia del lector de voz
     */
    public static synchronized LectorVoz getInstance() {
        if (instancia == null) {
            instancia = new LectorVoz();
        }
        return instancia;
    }

    /**
     * Reproduce un texto mediante voz
     * @param texto Texto que será reproducido
     */
    @Override
    public synchronized void hablar(String texto) {
        if (!activo || texto == null || texto.isBlank()) {
            return;
        }

        detener();

        new Thread(() -> {
            try {
                hablando = true;
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    String textoLimpio = texto.replace("\"", "\\\"");

                    File tempScript = File.createTempFile("speech", ".ps1");
                    tempScript.deleteOnExit();

                    String script
                            = "Add-Type -AssemblyName System.Speech\n"
                            + "$speech = New-Object System.Speech.Synthesis.SpeechSynthesizer\n"
                            + "$speech.Rate = 1\n"
                            + "try { $speech.SelectVoiceByHints('es-CO') } catch { }\n"
                            + "$speech.Speak('" + textoLimpio + "')\n";

                    try (FileWriter fw = new FileWriter(tempScript)) {
                        fw.write(script);
                    }
                    ProcessBuilder pb = new ProcessBuilder(
                            "powershell.exe",
                            "-ExecutionPolicy", "Bypass",
                            "-File", tempScript.getAbsolutePath()
                    );

                    pb.redirectErrorStream(true);
                    procesoActual = pb.start();
                    procesoActual.waitFor();
                    tempScript.delete();

                } else if (os.contains("mac")) {
                    ProcessBuilder pb = new ProcessBuilder("say", "-v", "Paulina", texto);
                    pb.redirectErrorStream(true);
                    procesoActual = pb.start();
                    procesoActual.waitFor();

                } else if (os.contains("nix") || os.contains("nux") || os.contains("linux")) {
                    ProcessBuilder pb = new ProcessBuilder("espeak", "-v", "es", "-s", "140", texto);
                    pb.redirectErrorStream(true);
                    procesoActual = pb.start();
                    procesoActual.waitFor();
                }

            } catch (Exception e) {
                System.err.println("[LectorVoz] Error: " + e.getMessage());
            
            } finally {
                hablando = false;
            }
        }).start();
    }

    /**
     * Detiene la reproducción de voz actual. 
     */
    @Override
    public synchronized void detener() {
        if (procesoActual != null && procesoActual.isAlive()) {
            procesoActual.destroyForcibly();
        }
        hablando = false;
    }
    
    /**
     * Verifica si actualmente se stá reproduciendo voz
     * @return True si el lector está hablando, false en caso contrario
     */
    public boolean isHablando() {
        return hablando;
    }
    
    /**
     * Activa o desactiva el lector de voz
     * @param activo Estado de activación del lector
     */
    @Override
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Verifica si el lector de voz está activo
     * @return True si el lector está activo, false en caso contrario
     */
    @Override
    public boolean isActivo() {
        return activo;
    }
}
