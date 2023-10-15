package org.unibl.etf.entity;

import java.util.Date;

public class Rezervacija {

	private Integer id;
	private Date VrijemeRezervacije;
	private Date VrijemeOdlaska;
	private Klijent klijent;
	private Tretman tretman;
	private Double CijenaTretman; 
	private Klinika klinika;
	private Grad lokacija;
	
	public Rezervacija() {
		// TODO Auto-generated constructor stub
	}

	public Rezervacija(Integer id,Date vrijemeRezervacije,  Date VrijemeOdlaska, Klijent klijent, Tretman tretman,
			Double cijenaTretman, Klinika klinika) {
		super();
		VrijemeRezervacije = vrijemeRezervacije;
		this.id = id;
		this.VrijemeOdlaska = VrijemeOdlaska;
		this.klijent = klijent;
		this.tretman = tretman;
		CijenaTretman = cijenaTretman;
		this.klinika = klinika;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getVrijemeRezervacije() {
		return VrijemeRezervacije;
	}

	public void setVrijemeRezervacije(Date vrijemeRezervacije) {
		VrijemeRezervacije = vrijemeRezervacije;
	}

	public Date getVrijemeOdlaska() {
		return VrijemeOdlaska;
	}

	public void serVrijemeOdlaska(Date VrijemeOdlaska) {
		this.VrijemeOdlaska = VrijemeOdlaska;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public Tretman getTretman() {
		return tretman;
	}

	public void setTretman(Tretman tretman) {
		this.tretman = tretman;
	}

	public Double getCijenaTretman() {
		return CijenaTretman;
	}

	public void setCijenaTretman(Double cijenaTretman) {
		CijenaTretman = cijenaTretman;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Grad getLokacija() {
		return lokacija;
	}

	public void setLokacija(Grad lokacija) {
		this.lokacija = lokacija;
	}

	@Override
	public String toString() {
		return "Rezervacija [VrijemeRezervacije=" + VrijemeRezervacije + ", VrijemeOdlaska=" + VrijemeOdlaska + ", klijent="
				+ klijent + ", tretman=" + tretman + ", CijenaTretman=" + CijenaTretman + ", klinika=" + klinika
				+ ", lokacija=" + lokacija + "]\n";
	}

}
