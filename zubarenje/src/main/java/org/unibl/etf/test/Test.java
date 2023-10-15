package org.unibl.etf.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.entity.Drzava;
import org.unibl.etf.entity.Grad;
import org.unibl.etf.entity.Klijent;
import org.unibl.etf.entity.Klinika;
import org.unibl.etf.entity.Rezervacija;
import org.unibl.etf.entity.Tretman;
import org.unibl.etf.wrapper.WrapperDrzava;
import org.unibl.etf.wrapper.WrapperGrad;
import org.unibl.etf.wrapper.WrapperKlijent;
import org.unibl.etf.wrapper.WrapperKlinika;
import org.unibl.etf.wrapper.WrapperRezervacija;
import org.unibl.etf.wrapper.WrapperTretman;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Klijent k = new Klijent(0, "Im23e61", "Prezime234111", new Date());
////		System.out.println(WrapperKlijent.selectAll());
////		System.out.println(WrapperKlijent.selectBy(new Klijent(3, null, null,null)));
////		System.out.println(WrapperKlijent.insert(k));
//		
//		
//		Klinika kl = new Klinika(null, "Somethi23ng31", "+1222123335", null);
////		System.out.println(WrapperKlinika.selectAll());
////		System.out.println(WrapperKlinika.selectBy(new Klinika(3, null, null,null)));
////		System.out.println(WrapperKlinika.selectByCity(1));
////		System.out.println(WrapperKlinika.insert(kl, 1));
//		
//		List<Klinika> list = new ArrayList<Klinika>();
//		list.add(kl);
//		Grad g = new Grad(null, "ban23ja luka1", list, "78000");
////		System.out.println(WrapperGrad.selectAll());
////		System.out.println(WrapperGrad.selectBy(new Grad(3, null, null,null),null));
////		System.out.println(WrapperGrad.insert(g, 1));
//		
//		List<Grad> list2 = new ArrayList<Grad>();
//		list2.add(g);
//		Drzava d = new Drzava(null, "Kong23o11", list2);
////		System.out.println(WrapperDrzava.selectAll());
////		System.out.println(WrapperDrzava.selectBy(new Drzava(2, null, null)));
////		System.out.println(WrapperDrzava.insert(d));
//		
//		Tretman tr = new Tretman(null, "Lijecenj231e1", "Lijecenje ja sva23sta nest1o!");
////		System.out.println(WrapperTretman.selectAll());
////		System.out.println(WrapperTretman.selectBy(new Tretman(3, null, null)));
////		System.out.println(WrapperTretman.insert(k));
		
//		Rezervacija r = new Rezervacija(null, new Date(), Integer.valueOf(2), WrapperKlijent.selectBy(new Klijent(3, null, null,null)).get(0), WrapperTretman.selectBy(new Tretman(3, null, null)).get(0), null, WrapperKlinika.selectBy(new Klinika(3, null, null,null)).get(0));
//		System.out.println(WrapperRezervacija.selectAll());
//		System.out.println(WrapperRezervacija.selectBy(new Rezervacija(1,null, null, null, null, null, null)));
//		System.out.println(WrapperRezervacija.insert(r));
//		
	}
}
