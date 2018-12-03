package projetreseaux;

import java.io.*;
import java.net.*;


public class Client {
   static final int port = 4444;
   


   public static void main(String[] args) throws Exception {
        boolean isReading = true;
        Socket socket = new Socket("localhost", port);

        BufferedReader plec = new BufferedReader(
                       new InputStreamReader(socket.getInputStream())
                       );

                
        PrintWriter pred = new PrintWriter(
                             new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                             true);
       
        
        BufferedReader input = new BufferedReader(
                       new InputStreamReader(System.in)
                       );

    
        String str = "";
        while(!str.equals("bye")){
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
        socket.close();
   }
}