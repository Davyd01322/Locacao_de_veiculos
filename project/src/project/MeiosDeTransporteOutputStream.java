package project;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

//import com.thougthworks.xstream.XStream;
//import com.thougthworks.xstream.io.json.JettinsonMappedXmlDriver;
//import com.thougthworks.xstream.security.AnyTypePermission;

import project.MeiosDeTransporte;

public class MeiosDeTransporteOutputStream {
	private OutputStream op;
	private MeiosDeTransporte[] transportes;
	
	public MeiosDeTransporteOutputStream() {}
	
	public MeiosDeTransporteOutputStream(MeiosDeTransporte[] m, OutputStream os) {
		this.transportes = m;
		this.op = os;
	}
	
	public void writeSystem() {
		PrintStream opLocal = new PrintStream(System.out);
		
		int qtdVeiculos = this.transportes.length;
		opLocal.println("Número de veiculos: " + qtdVeiculos);
		
		for(MeiosDeTransporte transporte : transportes) {
			if(transporte != null) {
				opLocal.println(transporte.toString());
			}
		}
	}
	
	public void writeFile() {
		try{
			byte buffer[] = new byte[this.transportes.length];
			for(int i = 0; i < this.transportes.length; i++){
				buffer = this.transportes[i].toString().getBytes();
				this.op.write(buffer);
			}
		} catch(IOException e){
			System.out.println("ERRO ao tentar escrever o arquivo binário arquivo.txt");
		} finally{
			try{
				if(op != null){
					op.close();
				}
			} catch(IOException e){
				System.out.println("ERROR Closing arquivo.txt");
			}
		}
	}
	
	/*
	public void writeJSON(){
		for(int i = 0; i < this.transportes.length; i++){
			ObjectToJson(this.transportes[i]);
		}
	}

	private static String ObjectToJson(MeiosDeTransporte transporte){
		try{
			XStream xstream = new XStream(new JettinsonMappedXmlDriver());
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.alias("transporte", MeiosDeTransporte.class);

			return xstream.toXML();
		} catch(Exception e){
			System.err.println("Erro ao converter para JSON");
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	*/

	public void writeTCP() {
		// Serialização de um vetor de objetos
		gravarArquivoBinario(transportes, "serial");

		int serverPort = 7896;
		
		try(
			Socket s = new Socket("localhost", serverPort);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			FileInputStream fileStr = new FileInputStream("serial");
		){
			byte[] buffer = new byte[4096];
			int bytesRead;

			while((bytesRead = fileStr.read(buffer)) != -1){
				out.write(buffer, 0, bytesRead);
			}

			out.flush();
		} catch(IOException e){
			System.out.println("Erro: " + e.getMessage());
		}

	}

	private void gravarArquivoBinario(MeiosDeTransporte[] m, String nomeArq){
		File arq = new File(nomeArq);

		try{
			arq.delete();
			arq.createNewFile();

			ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(arq));
			
			for(int i = 0; i < m.length; i++){
				objOutput.writeObject(m[i]);
			}
			
			objOutput.flush();
			objOutput.close();

		} catch(IOException e){
			System.out.println("Ocorreu um erro durante a serialização " + e.getMessage());
		}
	}
}
