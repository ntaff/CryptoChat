package projetreseaux;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CleAES {
    
    public void exportKey(SecretKey key) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("cle2.txt"));
        writer.write(Arrays.toString(key.getEncoded()));
        writer.close();
    }
    
    public SecretKey generateKey(){
        KeyGenerator keyGen;
        
        try {
          keyGen = KeyGenerator.getInstance("AES");
          keyGen.init(128);
          
          SecretKey cle = keyGen.generateKey();
          
          return cle;

        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }

    }
    
    public static void main(String[] args) throws IOException {
        CleAES aes = new CleAES();
        SecretKey cle = aes.generateKey();
        System.out.println(cle);
        aes.exportKey(cle);

  }
}
