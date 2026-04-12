package project;

public interface Locacao {
	void novoVeiculo(MeiosDeTransporte v);
	MeiosDeTransporte Alugar(String s);
	void Devolver(MeiosDeTransporte v);
	void venderVeiculo(MeiosDeTransporte v);
}
