package projetreseaux;

import java.io.*;
import java.net.*;

public class Serveur {
   static final int port = 4444;
   

   public static void main(String[] args) throws Exception {
        boolean isReading = false;
        ServerSocket serveur = new ServerSocket(port);
        Socket client = serveur.accept();

        BufferedReader plec = new BufferedReader(
                               new InputStreamReader(client.getInputStream())
                              );

        PrintWriter pred = new PrintWriter(
                             new BufferedWriter(
                                new OutputStreamWriter(client.getOutputStream())), 
                             true);
        
        BufferedReader input = new BufferedReader(
                               new InputStreamReader(System.in)
                              );
        
        
        System.out.println("connexion de "+client.getInetAddress().toString()+" sur le port "+client.getPort());
        
        String str = "";
        while (!str.equals("bye")) {
            if(isReading){
                str = plec.readLine();
                System.out.println(str);
            }else{
                str = input.readLine();
                pred.println(str);
            }
            isReading = !isReading;
        }
        plec.close();
        pred.close();
        client.close();
   }
}