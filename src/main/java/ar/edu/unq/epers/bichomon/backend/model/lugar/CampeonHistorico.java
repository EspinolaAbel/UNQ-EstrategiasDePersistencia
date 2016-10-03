package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

@Entity(name="Campeones_historicos")
public class CampeonHistorico {
	
	@Id
	private Integer fechaCoronadoCampeon;
	private Integer fechaDepuesto;
	@OneToOne
	private Bicho bichoCampeon;
	@OneToOne
	private Dojo lugarDondeFueCoronadoCampeon;
	
	public CampeonHistorico() {
		super();
	}
	
	public CampeonHistorico(Dojo dojo, Bicho bichoCampeon) {
		this.lugarDondeFueCoronadoCampeon = dojo;
		this.bichoCampeon = bichoCampeon;
	}
	
	public Integer getFechaCoronadoCampeon() {
		return fechaCoronadoCampeon;
	}
	
	public void setFechaCoronadoCampeon(Integer fechaCoronadoCampeon) {
		this.fechaCoronadoCampeon = fechaCoronadoCampeon;
	}
	
	public Integer getFechaDepuesto() {
		return fechaDepuesto;
	}
	
	public void setFechaDepuesto(Integer fechaDepuesto) {
		this.fechaDepuesto = fechaDepuesto;
	}
	
	public Bicho getBichoCampeon() {
		return bichoCampeon;
	}
	
	public void setBichoCampeon(Bicho bichoCampeon) {
		this.bichoCampeon = bichoCampeon;
	}

	public Dojo getLugarDondeFueCoronadoCampeon() {
		return lugarDondeFueCoronadoCampeon;
	}

	public void setLugarDondeFueCoronadoCampeon(Dojo lugarDondeFueCoronadoCampeon) {
		this.lugarDondeFueCoronadoCampeon = lugarDondeFueCoronadoCampeon;
	}

}
