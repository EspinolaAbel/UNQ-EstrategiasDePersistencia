package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

@Entity(name="Campeones_historicos")
public class CampeonHistorico {
	
	@Id
	private Long fechaCoronadoCampeon;
	private Long fechaDepuesto;
	@OneToOne //(cascade= CascadeType.ALL)
	private Bicho bichoCampeon;
	@OneToOne
	private Dojo lugarDondeEsCampeon;
	
	public CampeonHistorico() {
		super();
	}
	
	public CampeonHistorico(Dojo dojo, Bicho bichoCampeon) {
		this.lugarDondeEsCampeon = dojo;
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

	public Dojo getLugarDondeEsCampeon() {
		return lugarDondeEsCampeon;
	}

	public void setLugarDondeEsCampeon(Dojo lugarDondeFueCoronadoCampeon) {
		this.lugarDondeEsCampeon = lugarDondeFueCoronadoCampeon;
	}

}
