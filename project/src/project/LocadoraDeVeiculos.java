package project;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class LocadoraDeVeiculos implements Locacao{
	private Map<String,ArrayList<MeiosDeTransporte>> disponiveis;
	private ArrayList<MeiosDeTransporte> alugados;
	
	LocadoraDeVeiculos(){
		disponiveis = new HashMap<>();
		alugados = new ArrayList<>();
		
		ArrayList<MeiosDeTransporte> carros = new ArrayList<>();
		disponiveis.put("carros", carros);
		
		ArrayList<MeiosDeTransporte> motos = new ArrayList<>();
		disponiveis.put("motos", motos);
		
		ArrayList<MeiosDeTransporte> onibus = new ArrayList<>();
		disponiveis.put("onibus", onibus);
		
		ArrayList<MeiosDeTransporte> caminhao = new ArrayList<>();
		disponiveis.put("caminhoes", caminhao);
	}
	
	public void novoVeiculo(MeiosDeTransporte v) {
		String tipo = v.getTipo();
		
		switch(tipo){
			case "carro de passeio":
				disponiveis.get("carros").add(v);
				break;
			case "caminhão":
				disponiveis.get("caminhoes").add(v);
				break;
			case "ônibus":
				disponiveis.get("onibus").add(v);
				break;
			case "moto":
				disponiveis.get("motos").add(v);
				break;
			default:
				System.out.println("Tipo de veiculo não identificado");
				break;
		}
	}

	public MeiosDeTransporte Alugar(String s){
		int index = Integer.parseUnsignedInt(s);
		int adjust = 0;

		adjust = index - this.disponiveis.get("carros").size();

		if(adjust < 0){
			getVeiculo(this.disponiveis.get("carros").get(index - 1));
		}
		else{
			if(adjust == 0){
				getVeiculo(this.disponiveis.get("carros").get(adjust));
			}
		}

		adjust = index - (this.disponiveis.get("carros").size() + this.disponiveis.get("motos").size());

		if(adjust < 0){
			adjust = index - this.disponiveis.get("carros").size();
			getVeiculo(this.disponiveis.get("motos").get(adjust - 1));
		}
		else{
			if(adjust == 0){
				getVeiculo(this.disponiveis.get("motos").get(adjust));
			}
		}

		adjust = index - (this.disponiveis.get("carros").size() + this.disponiveis.get("motos").size() + this.disponiveis.get("caminhoes").size());
		if(adjust < 0){
			adjust = index - (this.disponiveis.get("carros").size() + this.disponiveis.get("motos").size());
			getVeiculo(this.disponiveis.get("caminhoes").get(adjust - 1));
		}
		else{
			if(adjust == 0){
				getVeiculo(this.disponiveis.get("caminhoes").get(adjust));
			}
		}

		adjust = index - (this.disponiveis.get("carros").size() + this.disponiveis.get("motos").size() + this.disponiveis.get("caminhoes").size() + this.disponiveis.get("onibus").size());
		if(adjust < 0){
			adjust = index - (this.disponiveis.get("carros").size() + this.disponiveis.get("motos").size() + this.disponiveis.get("caminhoes").size());
			getVeiculo(this.disponiveis.get("onibus").get(adjust - 1));
		}
		else{
			if(adjust == 0){
				getVeiculo(this.disponiveis.get("onibus").get(adjust));
			}
		}

		return this.alugados.getLast();
	}
	
	private void getVeiculo(MeiosDeTransporte v){
		String tipo = v.getTipo();
		boolean aux = true;
		
		for(int i = 0; i < disponiveis.get(tipo).size() && aux; i++) {
			if(disponiveis.get(tipo).get(i).getModelo().equals(v.getModelo())) {
				alugados.add(disponiveis.get(tipo).get(i));
				disponiveis.get(tipo).remove(i);
				i = disponiveis.get(tipo).size();
				aux = false;
			}
		}
		
		if(aux) {
			System.out.println(String.format("O modelo %s não está disponivel",v.getModelo()));
		}
	}
	
	public void Devolver(MeiosDeTransporte v){
		String tipo = v.getTipo();
		boolean aux = true;
		
		for(int i = 0; i < alugados.size() && aux; i++) {
			if(v.getModelo().equals(alugados.get(i).getModelo())) {
				disponiveis.get(tipo).add(v);
				alugados.remove(i);
				aux = false;
			}
		}
		
		if(aux) {
			System.out.println(String.format("O veiculo de modelo %s não foi alugado, você cometeu um engano.", v.getModelo()));
		}
	}
	
	public void venderVeiculo(MeiosDeTransporte v) {
		String tipo = v.getTipo();
		boolean aux = true;
		
		for(int i = 0; i < disponiveis.get(tipo).size() && aux; i++) {
			if(disponiveis.get(tipo).get(i).getModelo().equals(v.getModelo())){
				disponiveis.get(tipo).remove(i);
				aux = false;
			}
		}
		
		if(aux) {
			System.out.println(String.format("O veículo de modelo %s que você está tentando vender já foi alugado. Por favor tente novamente mais tarde.", v.getModelo()));
		}
	}
	
	public String listarDisponiveis(){
		String text = "";
		int index = 1;

		text += "Veiculos disponiveis\n";
		text += "CARROS\n";
		for(int i = 0; i < disponiveis.get("carros").size(); i++) {
			text += String.valueOf(index) + ". ";
			text += disponiveis.get("carros").get(i).toString();
			index += 1;
		}
		text += "MOTOS\n";
		for(int i = 0; i < disponiveis.get("motos").size(); i++) {
			text += String.valueOf(index) + ". ";
			text += disponiveis.get("motos").get(i).toString();
			index += 1;
		}
		text += "CAMINHÕES\n";
		for(int i = 0; i < disponiveis.get("caminhoes").size(); i++) {
			text += String.valueOf(index) + ". ";
			text += disponiveis.get("caminhoes").get(i).toString();
			index += 1;
		}
		text += "ÔNIBUS\n";
		for(int i = 0; i < disponiveis.get("onibus").size(); i++) {
			text += String.valueOf(index) + ". ";
			text += disponiveis.get("onibus").get(i).toString();
			text += 1;
		}

		return text;
	}

	public String toString(){
		String text = "";
		
		text += "Veiculos disponiveis\n";
		text += "CARROS";
		for(int i = 0; i < disponiveis.get("carros").size(); i++) {
			text += disponiveis.get("carros").get(i).toString();
		}
		text += "MOTOS";
		for(int i = 0; i < disponiveis.get("motos").size(); i++) {
			text += disponiveis.get("motos").get(i).toString();
		}
		text += "CAMINHÕES";
		for(int i = 0; i < disponiveis.get("caminhoes").size(); i++) {
			text += disponiveis.get("caminhoes").get(i).toString();
		}
		text += "ÔNIBUS";
		for(int i = 0; i < disponiveis.get("onibus").size(); i++) {
			text += disponiveis.get("onibus").get(i).toString();
		}
		
		text += "Veiculos alugados";
		for(int i = 0; i < alugados.size(); i++) {
			text += alugados.get(i).toString();
		}
		text += "\n";
		
		return text;
	}
}
