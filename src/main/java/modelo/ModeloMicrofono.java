/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isabe
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.*;

public class ModeloMicrofono implements Runnable {

    private TargetDataLine microfono;
    private boolean grabando;
    private AudioFormat formato;
    private ByteArrayOutputStream flujoSalida;
    private Thread hiloGrabacion;

    public ModeloMicrofono() {
        formato = new AudioFormat(44100.0f, 16, 1, true, false);
        grabando = false;
    }

    public void iniciarCaptura() throws LineUnavailableException {
        if (grabando) {
            return;
        }
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, formato);
        microfono = (TargetDataLine) AudioSystem.getLine(info);

        microfono.open(formato);
        microfono.start();
        flujoSalida = new ByteArrayOutputStream();
        grabando = true;

        hiloGrabacion = new Thread(this);
        hiloGrabacion.start();
        System.out.println("Grabación iniciada...");
    }

    public void detenerCaptura() {
        if (!grabando) {
            return;
        }

        grabando = false;
        if (microfono != null && microfono.isOpen()) {
            microfono.stop();
            microfono.close();
        }
        System.out.println("Grabación detenida.");
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        while (grabando) {
            int bytesLeidos = microfono.read(buffer, 0, buffer.length);
            if (bytesLeidos > 0) {
                flujoSalida.write(buffer, 0, bytesLeidos);
            }
        }
    }

    public void reproducirAudio() throws LineUnavailableException, IOException {
        if (flujoSalida == null) {
            System.out.println("No hay nada grabado aún.");
            return;
        }
        byte[] datosAudio = flujoSalida.toByteArray();
        
        ByteArrayInputStream flujoEntrada = new ByteArrayInputStream(datosAudio);
        AudioInputStream audioStream = new AudioInputStream(
                flujoEntrada, formato, datosAudio.length / formato.getFrameSize());
        DataLine.Info infoSalida = new DataLine.Info(SourceDataLine.class, formato);
        SourceDataLine altavoces = (SourceDataLine) AudioSystem.getLine(infoSalida);
        altavoces.open(formato);
        altavoces.start();
        
        int tamanoBuffer = 1024;
        byte[] buffer = new byte[tamanoBuffer];
        int bytesLeidos;
        
        while ((bytesLeidos = audioStream.read(buffer, 0, buffer.length)) != -1) {
            altavoces.write(buffer, 0, bytesLeidos);
        }
        altavoces.drain();
        altavoces.stop();
        altavoces.close();

        audioStream.close();
        System.out.println("Reproducción finalizada.");
    }

    public byte[] getBytesGrabados() {
        if (flujoSalida == null) {
            return new byte[0];
        }
        return flujoSalida.toByteArray();
    }

    public boolean isGrabando() {
        return grabando;
    }
}
