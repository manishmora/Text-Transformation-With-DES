import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class DESUtility {

    private static final String KEY_FILE = "des_encryption/key.txt";
    private static final String INPUT_FILE = "des_encryption/input.txt";
    private static final String OUTPUT_FILE = "des_encryption/output.txt";

    public static void main(String[] args) {
        if (args.length != 1 || (!args[0].equalsIgnoreCase("encrypt") && !args[0].equalsIgnoreCase("decrypt"))) {
            System.out.println("Usage: java DESUtility <encrypt|decrypt>");
            return;
        }

        String mode = args[0];

        try {
            Cipher cipher = Cipher.getInstance("DES");
            SecretKey key;

            if (mode.equalsIgnoreCase("encrypt")) {
                // Generate new key
                KeyGenerator keyGen = KeyGenerator.getInstance("DES");
                key = keyGen.generateKey();

                // Save key to file (Base64 encoded)
                String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(KEY_FILE))) {
                    writer.write(encodedKey);
                }

                // Read input message
                String message = readFromFile(INPUT_FILE);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] encrypted = cipher.doFinal(message.getBytes());
                String encryptedText = Base64.getEncoder().encodeToString(encrypted);
                writeToFile(OUTPUT_FILE, encryptedText);

            } else {
                // Read key from file
                String encodedKey = readFromFile(KEY_FILE);
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");

                // Read encrypted message
                String encryptedText = readFromFile(INPUT_FILE);
                byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
                cipher.init(Cipher.DECRYPT_MODE, originalKey);
                byte[] decrypted = cipher.doFinal(encryptedBytes);
                writeToFile(OUTPUT_FILE, new String(decrypted));
            }

        } catch (Exception e) {
            e.printStackTrace();
            writeToFile(OUTPUT_FILE, "Error: " + e.getMessage());
        }
    }

    private static String readFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }
        reader.close();
        return result.toString().trim();
    }

    private static void writeToFile(String filePath, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
