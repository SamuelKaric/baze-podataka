package org.unibl.etf.entity;

import java.util.List;

import org.unibl.etf.util.DBUtil;

public class Klinika {
	
	private Integer id;
	private String naziv;
	private String telefon;
	private List<CjenovnikItem> cijenovnik;
	private Grad lokacija;

	public Klinika() {
		// TODO Auto-generated constructor stub
	}

	public Klinika(Integer id, String naziv, String telefon, List<CjenovnikItem> cijenovnik, Grad lokacija) {
		super();
		this.id = id;
		this.naziv = DBUtil.EmptyToNull(naziv);
		this.telefon =  DBUtil.EmptyToNull(telefon);
		this.cijenovnik = cijenovnik;
		this.lokacija = lokacija;
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

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public  List<CjenovnikItem> getCijenovnik() {
		return cijenovnik;
	}

	public void setCijenovnik(List<CjenovnikItem> cijenovnik) {
		this.cijenovnik = cijenovnik;
	}

	public Grad getLokacija() {
		return lokacija;
	}

	public void setLokacija(Grad lokacija) {
		this.lokacija = lokacija;
	}

	@Override
	public String toString() {
		return "Klinika [naziv=" + naziv + "]";
	}
	

}
