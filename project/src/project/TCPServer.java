package project;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer{
    public static void main(String args[]){
        LocadoraDeVeiculos locadora = new LocadoraDeVeiculos();
        ArrayList<MeiosDeTransporte> transportes = new ArrayList<>();

        transportes.add(new Moto("Kawasaki", "H2", "Preta", "BL4CK"));
        transportes.add(new CarroDePasseio("Ferrari", "812superfast", "Vermelho Maranelo", "1234ABC"));
        transportes.add(new Onibus("Wolskwagem", "Scolarship", "Amarelo", "5CH00L"));
        transportes.add(new Caminhao("Mercedes", "Bigger", "Prata", "B1GG3R"));

        for(int i = 0; i < transportes.size(); i++){
            locadora.novoVeiculo(transportes.get(i));            
        }

        System.out.println("Seja bem vindo ao sistema de aluguel de carros");
        System.out.println("Selecione uma opção");
        System.out.println("1. Listar todos os veiculos");
        System.out.println("2. Alugar veiculo");
        System.out.println("3. Devolver veiculo");

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
    OutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket){
        try{
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e){
            System.out.println("Connection: " + e.getMessage());
        }
    }

    public void run(){
        try{
            String command = in.readUTF();
            System.out.println("~" + command);

            switch(command){
                case "1":
                    System.out.println(toString());
                    break;
                case "2":
                    System.out.println("Qual veiculo você deseja alugar?");
                    

                    break;
                case "3":
                    break;
                default:
                    break;
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