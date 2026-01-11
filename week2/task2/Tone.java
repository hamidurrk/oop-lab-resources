package main;

import javax.sound.sampled.*;

public class Tone {

    public static void play(double freq) {
        try {
            AudioFormat format = new AudioFormat(44100, 8, 1, true, false);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start();

            int samples = (int)(44100 * 0.4);  // Sound duration
            for (int i = 0; i < samples; i++) {
                double angle = i * 2.0 * Math.PI * freq / 44100;
                
                // Volume
                double amplitude = 30;
                
                if (i < 1000) {
                    amplitude *= i / 1000.0;  
                } else if (i > samples - 1000) {
                    amplitude *= (samples - i) / 1000.0; 
                }
                
                line.write(new byte[]{(byte)(Math.sin(angle) * amplitude)}, 0, 1);
            }

            line.drain();
            line.close();
        } catch (Exception e) {}
    }
}
