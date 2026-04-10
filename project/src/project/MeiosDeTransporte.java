package project;

import java.io.Serializable;

public abstract class MeiosDeTransporte implements Multa, Serializable {
	protected String marca;
	protected String modelo;
	protected String cor;
	protected String placa;
	protected int nMultas;
	
	public abstract String toString();
	public abstract String getTipo();
	public abstract double valorMulta();
	
	public int getNMultas() {
		return this.nMultas;
	}
	
	public String getModelo() {
		return this.modelo;
	}
	
}
