/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author isabe
 */
public class LectorVoz implements IReproducible {

    private static LectorVoz instancia;
    private boolean activo = true;
    private Process procesoActual;    

    
    private LectorVoz() {
    }

    public static synchronized LectorVoz getInstance() {
        if (instancia == null) {
            instancia = new LectorVoz();
        }
        return instancia;
    }

    @Override
    public void hablar(String texto) {
        if (!activo || texto == null || texto.isBlank()) {
            return;
        }

        detener();

        new Thread(() -> {
            try {
                String os = System.getProperty("os.name").toLowerCase();
                ProcessBuilder pb = construirComando(os, texto);

                if (pb != null) {
                    pb.redirectErrorStream(true);
                    procesoActual = pb.start();
                    procesoActual.waitFor();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                
            } catch (Exception e) {
                System.err.println("[LectorVoz] TTS no disponible: " + e.getMessage());
                
            }
        }).start();
    }

    @Override
    public void detener() {
        if (procesoActual != null && procesoActual.isAlive()) {
            procesoActual.destroyForcibly();
        }
    }

    private ProcessBuilder construirComando(String os, String texto) {
        String textoLimpio = texto
                .replace("\"", "")
                .replace("'", "")
                .replace("$", "");

        if (os.contains("win")) {
            String script = String.format(
                    "Add-Type -AssemblyName System.Speech; "
                    + "$s = New-Object System.Speech.Synthesis.SpeechSynthesizer; "
                    + "$s.Rate = 1; "
                    + "try { $s.SelectVoiceByHints('es-CO') } catch { "
                    + "  try { $s.SelectVoiceByHints('es') } catch {} }; "
                    + "$s.Speak(\"%s\");", textoLimpio
            );
            return new ProcessBuilder("powershell", "-NoProfile", "-Command", script);

        } else if (os.contains("mac")) {
            
            return new ProcessBuilder("say", "-v", "Paulina", textoLimpio);

        } else if (os.contains("nix") || os.contains("nux") || os.contains("linux")) {
            
            return new ProcessBuilder("espeak", "-v", "es", "-s", "140", textoLimpio);
        }

        return null;    
    }

    @Override
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean isActivo() {
        return activo;
    }
}
