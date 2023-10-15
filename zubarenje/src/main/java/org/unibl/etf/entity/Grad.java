package org.unibl.etf.entity;

import java.util.List;

import org.unibl.etf.util.DBUtil;

public class Grad {
	
	private Integer id;
	private String naziv;
	private List<Klinika> klinike;
	private String PostanskiBroj;

	public Grad() {
		// TODO Auto-generated constructor stub
	}

	public Grad(Integer id, String naziv, List<Klinika> klinike, String PostanskiBroj) {
		super();
		this.id = id;
		this.naziv =  DBUtil.EmptyToNull(naziv);
		this.klinike = klinike;
		this.PostanskiBroj =  DBUtil.EmptyToNull(PostanskiBroj);
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

	public List<Klinika> getKlinike() {
		return klinike;
	}

	public void setKlinike(List<Klinika> klinike) {
		this.klinike = klinike;
	}

	public String getPostanskiBroj() {
		return PostanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		PostanskiBroj = postanskiBroj;
	}

	@Override
	public String toString() {
		String res = naziv;
		if(klinike != null) {
			for(Klinika k: klinike) {
				res+= k;
			}
		}
		return res;
	}


}
