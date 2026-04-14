package project;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer{
    public static void main(String args[]){
        try{
            System.out.println("Servidor inicializado");
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);

            while(true){
                Socket clientSocket = listenSocket.accept();
                System.out.println(clientSocket.getInetAddress());
                System.out.println("conexão estabelecida");
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e){
            System.out.println("Listen socket: " + e.getMessage());
        }
    }
}

class Connection extends Thread{
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    LocadoraDeVeiculos locadora;
    ArrayList<MeiosDeTransporte> transportes;

    public Connection(Socket aClientSocket){
        try{
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();

            this.locadora = new LocadoraDeVeiculos();
            this.transportes = new ArrayList<>();

            transportes.add(new Moto("Honda", "cgFan160", "Vermelha", "BL4CK"));
            transportes.add(new Moto("Honda", "NRX160", "Preta", "D4RT2"));
            transportes.add(new Moto("Yamaha", "xj6", "Azul", "M4D4R4"));
            transportes.add(new CarroDePasseio("Subaru", "ImprezaWRX", "Azul", "L1GTH"));
            transportes.add(new CarroDePasseio("Wolkswagen", "Golf", "Branco", "J0H4N"));
            transportes.add(new CarroDePasseio("Honda", "Civic", "Prata", "M4R1K"));
            transportes.add(new CarroDePasseio("Chevrolet", "Camaro", "Amarelo", "ER3N"));
            transportes.add(new Onibus("Wolskwagem", "Scolarship", "Amarelo", "5CH00L"));
            transportes.add(new Caminhao("Mercedes", "Axor", "Prata", "154BEL4"));
            transportes.add(new Caminhao("Ford", "Torqshift", "Prata", "D4K1"));
            
            for(int i = 0; i < transportes.size(); i++){
                locadora.novoVeiculo(transportes.get(i));            
            }

        } catch (IOException e){
            System.out.println("Connection: " + e.getMessage());
        }
    }

    public void run(){
        try{
            while(true){        
                out.writeUTF("6");
                out.writeUTF("Seja bem vindo ao sistema de aluguel de carros");
                out.writeUTF("Selecione uma opção");
                out.writeUTF("1. Listar todos os veiculos");
                out.writeUTF("2. Alugar veiculo");
                out.writeUTF("3. Devolver veiculo");
                out.writeUTF("4. Limpar a tela");
                out.writeUTF("end");

                String command = in.readUTF();
                System.out.println("~" + command);

                switch(command){
                    case "1":
                        out.writeUTF("1");
                        out.writeUTF(locadora.toString());
                        out.writeUTF("end1");
                        break;
                    case "2":
                        out.writeUTF("2");
                        out.writeUTF(locadora.listarDisponiveis());
                        out.writeUTF("Qual veiculo você deseja alugar?");
                        out.writeUTF("end2");
                        String answer = in.readUTF();

                        MeiosDeTransporte m[] = new MeiosDeTransporte[1];
                        m[0] = locadora.Alugar(answer);

                        File arq = new File("arquivo.txt");
                        arq.delete();
                        arq.createNewFile();
                        OutputStream os = new FileOutputStream(arq);
                        
                        MeiosDeTransporteOutputStream mos = new MeiosDeTransporteOutputStream(m,os);
                        mos.writeTCP();
                        break;
                    case "3":
                        out.writeUTF("1");
                        out.writeUTF("Aguardando o envio");
                        out.writeUTF("end3");

                        try{
                            ServerSocket serverSocket = new ServerSocket(7898);
                            Socket client = serverSocket.accept();
                            ObjectInputStream objInput = new ObjectInputStream(client.getInputStream());
                            try{
                                MeiosDeTransporte v = (MeiosDeTransporte) objInput.readObject();
                                System.out.println("Recebemos o veiculo: ");
                                System.out.println(v.toString());
                                locadora.Devolver(v);
                            } catch(ClassNotFoundException e){
                                System.out.println("ClassNotFoundException: " + e.getMessage());
                            }
                            
                            client.close();
                            serverSocket.close();
                            objInput.close();
                        } catch(IOException e){
                            System.out.println("IOException: " + e.getMessage());
                        }
                        out.writeUTF("1");
                        out.writeUTF("Obrigado!");
                        out.writeUTF("end4");
                        break;
                    case "4":
                        out.writeUTF("1");
                        out.writeUTF("\033[H\033[2j");
                        out.writeUTF("end4");
                        break;
                    default:
                        System.out.println("Comando inválido");
                        break;
                }
            }
        } catch(EOFException e){
            System.out.println("EOF: " + e.getMessage());
        } catch(IOException e){
            System.out.println("readline: " + e.getMessage());
        } finally{
            try{
                clientSocket.close();
            } catch(IOException e){
                System.out.println("Erro ao tentar fechar o socket");
            }
        }
    }
}
