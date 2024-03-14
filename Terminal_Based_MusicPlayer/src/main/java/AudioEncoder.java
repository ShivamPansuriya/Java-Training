import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class AudioEncoder {
    public static void main(String[] args) {
        String filename = "./src/main/resources/Audio/mini.wav"; // Path to your audio file
        try {
            byte[] fileContent = Files.readAllBytes(new File(filename).toPath());
            String encodedAudio = Base64.getEncoder().encodeToString(fileContent);
            System.out.println("Encoded audio data: " + encodedAudio);

            byte[] audioData = java.util.Base64.getDecoder().decode(encodedAudio);

            // Write audio data to a file
            FileOutputStream fos = new FileOutputStream("new.wav");
            fos.write(audioData);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
