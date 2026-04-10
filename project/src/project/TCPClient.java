package project;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient{
    public static void main(String args[]){
        Socket s = null;

        try{
            int serverPort = 7896;
            s = new Socket("10.11.111.1", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            Scanner reader = new Scanner(System.in);
            String line;

            while(true){
                line = reader.nextLine();
                out.writeUTF(line);
            }

        } catch(UnknownHostException e){
            System.out.println("Socket: " + e.getMessage());
        } catch(EOFException e){
            System.out.println("EOF: " + e.getMessage());
        } catch(IOException e){
            System.out.println("readline: " + e.getMessage());
        } finally{
            if(s != null){
                try{
                    s.close();
                } catch(IOException e){
                    System.out.println("close: " + e.getMessage());
                }
            }
        }

    }
}