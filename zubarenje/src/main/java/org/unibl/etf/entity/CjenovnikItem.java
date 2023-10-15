package org.unibl.etf.entity;

public class CjenovnikItem {
	private Tretman tretman;
	private Double cijena;
	
	public CjenovnikItem(Tretman tretman, Double cijena) {
		super();
		this.tretman = tretman;
		this.cijena = cijena;
	}

	public Tretman getTretman() {
		return tretman;
	}

	public void setTretman(Tretman tretman) {
		this.tretman = tretman;
	}

	public Double getCijena() {
		return cijena;
	}

	public void setCijena(Double cjena) {
		this.cijena = cjena;
	}
	
	public Integer getID() {
		return tretman.getId();
	}
	
	public String getTreatement() {
		return tretman.getNaziv();
	}
	
	public String getDesc() {
		return tretman.getOpis();
	}

	@Override
	public String toString() {
		return "CjenovnikItem [tretman=" + tretman + ", cjena=" + cijena + "]";
	}


}
