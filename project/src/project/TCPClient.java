package project;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient{
    public static void main(String args[]){
        Socket s = null;
        DataInputStream in;
        DataOutputStream out;

        String ipLocal = "10.11.111.29";
        String ipRemoto = "10.11.111.27";

        try{
            int serverPort = 7896;
            s = new Socket(ipRemoto, serverPort);
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());

            Scanner reader = new Scanner(System.in);
            String line;

            while(true){
                int j = Integer.parseUnsignedInt(in.readUTF());
                for(int i = 0; i < j; i++){
                    String text = in.readUTF();
                    System.out.println(text);
                }
                j = 0;

                String lineCommand = in.readUTF();
                
                switch(lineCommand){
                    case "end":
                        line = reader.nextLine();
                        out.writeUTF(line);
                        break;
                    case "end1":
                        break;
                    case "end2":
                        line = reader.nextLine();
                        out.writeUTF(line);

                        try {
                            ServerSocket serverSocket = new ServerSocket(7897);
                            Socket client = serverSocket.accept();
                            ObjectInputStream client_in = new ObjectInputStream(client.getInputStream());
                            try{
                                MeiosDeTransporte veiculo = (MeiosDeTransporte) client_in.readObject();
                                System.out.println(veiculo.toString());
                                client_in.close();
                                
                                try{
                                    File arq = new File("serial");
                                    arq.delete();
                                    arq.createNewFile();
                                    ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(arq));

                                    objOutput.writeObject(veiculo);
                                    objOutput.flush();
                                    objOutput.close();
                                } catch(NullPointerException e){
                                    System.out.println("NullPointerException: " + e.getLocalizedMessage());
                                }
                            } catch(ClassNotFoundException e){
                                System.out.println("A classe não foi encontrada " + e.getMessage());
                            }
                            client.close();
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "end3":
                        try {
                            ObjectInputStream objInput = new ObjectInputStream(new FileInputStream("serial"));
                            MeiosDeTransporte v = (MeiosDeTransporte) objInput.readObject();

                            Socket soc = new Socket(ipRemoto,7898);
                            ObjectOutputStream objOutput = new ObjectOutputStream(soc.getOutputStream());

                            System.out.println("Você está devolvendo:");
                            System.out.println(v.toString());

                            objOutput.writeObject(v);
                            objOutput.flush();
                            objOutput.close();
                            objInput.close();
                            soc.close();
                        } catch (IOException e) {
                            System.out.println("IOException: " + e.getMessage());
                        } catch (ClassNotFoundException e){
                            System.out.println("ClassNotFoundException: " + e.getMessage());
                        }
                        break;
                    default:
                        break;
                }

                lineCommand = "";
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
