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
			ObjectOutputStream objOutput = new ObjectOutputStream(op);
			objOutput.writeObject(this.transportes);
			objOutput.flush();
			objOutput.close();
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

	public void writeTCP() {
		try(
			Socket s = new Socket("10.11.111.29", 7897);
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		){
			out.writeObject(this.transportes[0]);
			out.flush();
			out.close();
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
