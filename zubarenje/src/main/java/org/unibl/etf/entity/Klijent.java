package org.unibl.etf.entity;

import java.util.Date;

import org.unibl.etf.util.DBUtil;

public class Klijent {
	private Integer id;
	private String Ime;
	private String Email;
	private Date DatumPrijavljivanja;

	public Klijent() {
		// TODO Auto-generated constructor stub
	}
	 
	public Klijent(Integer id, String Ime, String Email, Date DatumPrijavljivanja) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.Ime =  DBUtil.EmptyToNull(Ime);
		this.Email =  DBUtil.EmptyToNull(Email);
		this.DatumPrijavljivanja = DatumPrijavljivanja;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return Ime;
	}

	public void setIme(String ime) {
		Ime = ime;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Date getDatumPrijavljivanja() {
		return DatumPrijavljivanja;
	}

	public void setDatumPrijavljivanja(Date datumPrijavljivanja) {
		DatumPrijavljivanja = datumPrijavljivanja;
	}

	@Override
	public String toString() {
		return "[Ime: "+Ime+", Email:"+Email+", Made at:" + DatumPrijavljivanja +"]";
		
	}
	

}
