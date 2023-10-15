package org.unibl.etf.entity;

import org.unibl.etf.util.DBUtil;

public class Tretman {
	
	private Integer id;
	private String Naziv;
	private String Opis;

	public Tretman() {
		// TODO Auto-generated constructor stub
	}

	public Tretman(Integer id, String naziv, String opis) {
		super();
		this.id = id;
		Naziv =  DBUtil.EmptyToNull(naziv);
		Opis =  DBUtil.EmptyToNull(opis);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return Naziv;
	}

	public void setNaziv(String naziv) {
		Naziv = naziv;
	}

	public String getOpis() {
		return Opis;
	}

	public void setOpis(String opis) {
		Opis = opis;
	}

	@Override
	public String toString() {
		return "Tretman [Naziv=" + Naziv + ", Opis=" + Opis + "]";
	}

}
