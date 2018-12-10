package chatavecchiffrage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CleAES {
    
    public static SecretKey importKey(String inputFile) throws Exception{
        byte[] byteKey = Files.readAllBytes(Paths.get(inputFile));
        
        SecretKey importedKey;
        
        try (FileInputStream keyIn = new FileInputStream(inputFile)) {
            importedKey = new SecretKeySpec(byteKey, "AES");
        }
        
        return importedKey;
    }
    
    public static void exportKey(SecretKey key, String outputFile) throws Exception{
        try (FileOutputStream keyOut = new FileOutputStream(outputFile)) {
            keyOut.write(key.getEncoded());
        }
    }
    
    public static SecretKey generateKey() throws NoSuchAlgorithmException{       
          KeyGenerator keyGen = KeyGenerator.getInstance("AES");
          keyGen.init(256);
          
          SecretKey key = keyGen.generateKey();

          return key;
    }
    
    public static void main(String[] args) throws IOException, Exception {
        SecretKey generatedKey = CleAES.generateKey();
        
        CleAES.exportKey(generatedKey,"cle.txt");
        
        SecretKey importedKey = CleAES.importKey("cle.txt");
        
        System.out.println(Arrays.equals(generatedKey.getEncoded(), importedKey.getEncoded()));
  }
}
