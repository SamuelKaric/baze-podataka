package org.unibl.etf.entity;

import java.util.List;

import org.unibl.etf.util.DBUtil;

public class Drzava {

	private Integer id;
	private String naziv;
	private List<Grad> gradovi;
	
	public Drzava() {
	}
	
	public Drzava(Integer id, String naziv, List<Grad> gradovi) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.naziv =  DBUtil.EmptyToNull(naziv);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Grad> getGradovi() {
		return gradovi;
	}

	public void setGradovi(List<Grad> gradovi) {
		this.gradovi = gradovi;
	}

	@Override
	public String toString() {
		String text = naziv;
		if(gradovi != null){
			for(Grad s: gradovi)
				text += s;
		}
		return text;
	}

}
