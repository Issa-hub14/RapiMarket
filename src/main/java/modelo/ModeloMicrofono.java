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
    private boolean escuchando = false;
    private AudioFormat formato;
    private ByteArrayOutputStream flujoSalida; 

    public ModeloMicrofono() {
        formato = new AudioFormat(44100.0f, 16, 1, true, false);// CAMBIO DE TRUE
    }

    public void iniciarCaptura() throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, formato);
        microfono = (TargetDataLine) AudioSystem.getLine(info);
        microfono.open(formato);
        microfono.start();
        escuchando = true;
        flujoSalida = new ByteArrayOutputStream(); 
        Thread hiloAudio = new Thread(this);
        hiloAudio.start();
    }

    public void detenerCaptura() {
        escuchando = false;
        if (microfono != null) {
            microfono.stop();
            microfono.close();
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        while (escuchando) {
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
        System.out.println(" se cerro!");
    }

    public byte[] getBytesGrabados() {
        return flujoSalida != null ? flujoSalida.toByteArray() : new byte[0];
    }
}