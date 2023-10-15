package org.unibl.etf.wrapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.entity.Rezervacija;
import org.unibl.etf.entity.Tretman;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.entity.Grad;
import org.unibl.etf.entity.Klijent;
import org.unibl.etf.entity.Klinika;
import org.unibl.etf.util.DBUtil;

public class WrapperRezervacija {
	private static final String SQL_SELECT_ALL = "SELECT * FROM Rezervacija";
	private static final String SQL_INSERT = "INSERT INTO Rezervacija (VrijemeRezeracije,VrijemeOdlaska, Status, Klijent, idTretman, idZK, CijenaTretmana) values (?,?,?,?,?,?,?)";
//	private static final String SQL_UPDATE ="UPDATE Rezervacija SET VrijemeRezeracije = ?, Trajanje = ?, Status = ? WHERE IdRezervacije = ?";
	private static final String SQL_DELETE ="DELETE FROM Rezervacija WHERE IdRezervacije = ?";
	private static final String OVERLAP_CALL = "CALL Busy_Date_Overlap( ? , ? , ?)";

	public static List<Rezervacija> selectAll(){
		List<Rezervacija> result = new ArrayList<Rezervacija>();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()){
				List<Klijent> klijent = WrapperKlijent.selectBy(new Klijent(rs.getInt("Klijent"),null,null,null));
				List<Tretman> tretman = WrapperTretman.selectBy(new Tretman(rs.getInt("idTretman"), null, null));
				List<Klinika> klinika = WrapperKlinika.selectBy(new Klinika(rs.getInt("idZK"),null,null, null, null));
				result.add(new Rezervacija(rs.getInt(1), 
						new Date(rs.getDate("VrijemeRezeracije").getTime()), 
						new Date(rs.getDate("VrijemeOdlaska").getTime()),
						klijent.isEmpty()? null: klijent.get(0),
						tretman.isEmpty()? null: tretman.get(0),
						rs.getDouble("CijenaTretmana"), 
						klinika.isEmpty()? null: klinika.get(0)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, s, rs);
		}
		return result;
	}
	
	public static List<Rezervacija> selectBy(Rezervacija in) throws InputException {
		List<Rezervacija> result = new ArrayList<Rezervacija>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> temp = new ArrayList<Object>();
		try {
			c = DBUtil.getConnection();
			String query = SQL_SELECT_ALL + " WHERE true";
			if(in.getId() != null) {
				query += " AND IdRezervacije = ?";
				temp.add(in.getId());
			}
			if(in.getKlijent() != null) {
				query += " AND Klijent = ?";
				temp.add(in.getKlijent().getId());
			}
			if(in.getKlinika() != null) {
				query += " AND idZK = ?";
				temp.add(in.getKlinika().getId());
			}
			System.out.println("asd"+ query +in+ temp);
			ps = DBUtil.prepareStatment(c, query, temp.toArray());
			rs = ps.executeQuery();
			while(rs.next()){
				List<Klijent> klijent = WrapperKlijent.selectBy(new Klijent(rs.getInt("Klijent"),null,null,null));
				List<Tretman> tretman = WrapperTretman.selectBy(new Tretman(rs.getInt("idTretman"), null, null));
				List<Klinika> klinika = WrapperKlinika.selectBy(new Klinika(rs.getInt("idZK"),null,null, null, null));
				result.add(new Rezervacija(rs.getInt(1), 
						new Date(rs.getDate("VrijemeRezeracije").getTime()), 
						new Date(rs.getDate("VrijemeOdlaska").getTime()),
						klijent.isEmpty()? null: klijent.get(0),
						tretman.isEmpty()? null: tretman.get(0),
						rs.getDouble("CijenaTretmana"), 
						klinika.isEmpty()? null: klinika.get(0)));
			}
		}catch(SQLIntegrityConstraintViolationException e) {
			throw new InputException(e.getMessage());
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, rs);
		}
		return result;
	}

	public static int insert(Rezervacija in) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			if(in.getKlijent() == null)
				throw new InputException("Missing klijent.");
			if(in.getKlinika() == null)
				throw new InputException("Missing klinika.");
			ps = DBUtil.prepareStatment(c, SQL_INSERT, new Object[] {in.getVrijemeRezervacije(),in.getVrijemeOdlaska(),Integer.valueOf(1), in.getKlijent().getId(),in.getTretman().getId(),in.getKlinika().getId(),in.getCijenaTretman()});
			index = ps.executeUpdate();
		}catch(SQLIntegrityConstraintViolationException e) {
			throw new InputException(e.getMessage());
		}catch(SQLException e) {
			throw new InputException(e.getMessage());
			//e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, rs);
		}
		return index;
	}
	
	public static void overlap(Date start, Date end, Integer idZk) throws InputException {
		int index = 0;
		Connection c = null;
		CallableStatement cs = null;
		try {
			c = DBUtil.getConnection();
			cs = c.prepareCall(OVERLAP_CALL);
			cs.setInt(1, idZk);
			cs.setDate(2, new java.sql.Date(start.getTime()));
	        cs.setDate(3, new java.sql.Date(end.getTime()));
			index = cs.executeUpdate();
		}catch(SQLException e) {
			throw new InputException(e.getMessage());
		}finally {
			DBUtil.close(c, cs, null);
		}
	}

	public static int delete(Rezervacija in) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBUtil.getConnection();
			//ON UPDATE CASCADE Weard Behavior
//			ps = DBUtil.prepareStatment(c, SQL_UPDATE, new Object[] {in.getVrijemeRezervacije(), in.getTrajanje(), status == false? Integer.valueOf(0):Integer.valueOf(1), in.getId()});
//			index = ps.executeUpdate();
			ps = DBUtil.prepareStatment(c, SQL_DELETE, new Object[] { in.getId()});
			index = ps.executeUpdate();
		}catch(SQLIntegrityConstraintViolationException e) {
			throw new InputException(e.getMessage());
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, null);
		}
		return index;
	}
}
