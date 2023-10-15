package org.unibl.etf.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.entity.Klijent;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.util.DBUtil;

public class WrapperKlijent {
	
	private static final String SQL_SELECT_ALL = "SELECT * FROM korisnik";
	private static final String SQL_INSERT = "INSERT INTO korisnik (Ime, Password, Email, Aktivan, DatumPravljenja) values (?,'Jovanovic',?,true, ?)";
	private static final String SQL_UPDATE ="UPDATE korisnik SET Ime=?, Email=?,Aktivan=true WHERE IdKlijent = ?";

	public static List<Klijent> selectAll(){
		List<Klijent> result = new ArrayList<Klijent>();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = DBUtil.getConnection();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL+" WHERE Aktivan = 1");
			while(rs.next()){
				result.add(new Klijent(rs.getInt(1), rs.getString("Ime"),rs.getString("Email"), new Date(rs.getDate("DatumPravljenja").getTime())));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, s, rs);
		}
		return result;
	}
	
	public static List<Klijent> selectBy(Klijent in) {
		List<Klijent> result = new ArrayList<Klijent>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> temp = new ArrayList<Object>();
		try {
			c = DBUtil.getConnection();
			String query = SQL_SELECT_ALL + " WHERE aktivan = 1";
			if(in.getId() != null) {
				query += " AND IdKorisnik = ?";
				temp.add(in.getId());
			}
			if(in.getIme() != null) {
				query += " AND Ime = ?";
				temp.add(in.getIme());
			}
			if(in.getDatumPrijavljivanja() != null) {
				query += " AND DatumPravljenja = ?";
				temp.add(new java.sql.Date( in.getDatumPrijavljivanja().getTime()));
			}
			if(in.getEmail() != null) {
				query += " AND Email = ?";
				temp.add(in.getEmail());
			}
			ps = DBUtil.prepareStatment(c, query, temp.toArray());
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(new Klijent(rs.getInt(1), rs.getString("Ime"),rs.getString("Email"), new Date(rs.getDate("DatumPravljenja").getTime())));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, null);
		}
		return result;
	}
	
	public static int insert(Klijent in) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_INSERT, new Object[] {in.getIme(), in.getEmail(), new java.sql.Date(in.getDatumPrijavljivanja().getTime())});
			index = ps.executeUpdate();
		}catch(SQLIntegrityConstraintViolationException e) {
			throw new InputException(e.getMessage());
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, rs);
		}
		return index;
	}
	
	public static int update(Klijent in) {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_UPDATE, new Object[] {in.getIme(), in.getEmail(),new java.sql.Date(in.getDatumPrijavljivanja().getTime()), in.getId()});
			index = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, null);
		}
		return index;
	}

}
