package project;

public interface Locacao {
	void novoVeiculo(MeiosDeTransporte v);
	void Alugar(MeiosDeTransporte v);
	void Devolver(MeiosDeTransporte v);
	void venderVeiculo(MeiosDeTransporte v);
}
