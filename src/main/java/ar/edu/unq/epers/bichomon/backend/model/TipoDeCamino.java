package ar.edu.unq.epers.bichomon.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TipoDeCamino {
	private int costo;
	@Id
	private String tipo;
	public TipoDeCamino() {
		}
	
	public TipoDeCamino(int costo, String tipo ){
		this.costo=costo;
		this.tipo=tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getCosto() {
		return costo;
	}
	public String getTipoDeCamino(){
		return this.getTipoDeCamino();
	}
	public void setCosto(int costo){
		this.costo=costo;
	}

}
