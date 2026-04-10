package project;

import project.MeiosDeTransporte;
import project.MeiosDeTransporteOutputStream;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;

public class Demo{
    public static void main(String[] args){
        MeiosDeTransporte[] m = new MeiosDeTransporte[4];

        m[0] = new CarroDePasseio("Ferrari", "812superfast", "Vermelho Maranelo", "1234ABC");
        m[1] = new Moto("Kawasaki", "H2", "Preta", "BL4CK");
        m[2] = new Onibus("Wolskwagem", "Scolarship", "Amarelo", "5CH00L");
        m[3] = new Caminhao("Mercedes", "Bigger", "Prata", "B1GG3R");

        try{
            OutputStream os = new FileOutputStream("arquivo.txt");
            MeiosDeTransporteOutputStream op = new MeiosDeTransporteOutputStream(m,os);

            //op.writeFile();
            //op.writeSystem();
            op.writeTCP();

            // desserialização
            try(ObjectInputStream objInStrm = new ObjectInputStream(new FileInputStream("serial"))){
                try{
                MeiosDeTransporte veiculo = (MeiosDeTransporte) objInStrm.readObject();
                
                while(veiculo != null){
                    System.out.println(veiculo);
                    System.out.println(":)");
                    veiculo = (MeiosDeTransporte) objInStrm.readObject();
                }

                } catch(ClassNotFoundException e){
                    System.out.println("Classe não encontrada " + e);
                }
            } catch(IOException e){
                System.out.println("Ocorreu um erro ao tentar recuperar o objeto serializado " + e);
            } 
        } catch(FileNotFoundException e){
            System.out.println("O arquivo não foi encontrado");
        }
    }
}