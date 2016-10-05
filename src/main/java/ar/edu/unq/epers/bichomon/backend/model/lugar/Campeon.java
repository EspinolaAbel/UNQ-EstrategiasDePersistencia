package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

@Entity(name="Campeones")
public class Campeon {
	
	@Id
	private Long fechaCoronadoCampeon;
	private Long fechaDepuesto;
	@OneToOne
	private Bicho bichoCampeon;
	@OneToOne
	private Dojo lugarDondeFueCoronadoCampeon;
	
	public Campeon() {
		super();
	}
	
	public Campeon(Dojo dojo, Bicho bichoCampeon) {
		this.lugarDondeFueCoronadoCampeon = dojo;
		this.bichoCampeon = bichoCampeon;
		this.setFechaCoronadoCampeon(System.nanoTime());
	}
	
	public Long getFechaCoronadoCampeon() {
		return fechaCoronadoCampeon;
	}
	
	public void setFechaCoronadoCampeon(Long fechaCoronadoCampeon) {
		this.fechaCoronadoCampeon = fechaCoronadoCampeon;
	}
	
	public Long getFechaDepuesto() {
		return fechaDepuesto;
	}
	
	public void setFechaDepuesto(Long fechaDepuesto) {
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
