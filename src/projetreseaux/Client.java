package chatavecchiffrage;

import java.io.*;
import java.net.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;


public class Client {
    
   public static void main(String[] args) throws Exception {
        boolean isReading = !Serveur.isReading;

        try (Socket client = new Socket("localhost", Serveur.PORT);
            DataInputStream clientIn = new DataInputStream(client.getInputStream());
            DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());
            BufferedReader messageInput = new BufferedReader(new InputStreamReader(System.in))) {
           
           SecretKey key = CleAES.importKey(Serveur.KEY_FILE);
           Cipher cipher = Cipher.getInstance("AES");
           
           boolean isStopped = false;
           String message = "";
           while (!isStopped && !message.equals("bye")) {
               if(isReading){
                   cipher.init(Cipher.DECRYPT_MODE, key);
                   
                   int messageSize = clientIn.readInt();
                   byte[] encryptedMessage = new byte[messageSize];
                   
                   clientIn.readFully(encryptedMessage);
                   
                   byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
                   
                   System.out.println(new String(encryptedMessage)+" -> "+new String(decryptedMessage));
                   
                   isStopped = new String(decryptedMessage).equals("bye");
               }else{
                   cipher.init(Cipher.ENCRYPT_MODE, key);
                   
                   message = messageInput.readLine();
                   
                   byte[] encryptedMessage = cipher.doFinal(message.getBytes());
                   
                   System.out.println(message+" -> "+new String(encryptedMessage));
                   
                   clientOut.writeInt(encryptedMessage.length);
                   clientOut.write(encryptedMessage);
               }
               isReading = !isReading;
           }
           
       }
   }
   
}
