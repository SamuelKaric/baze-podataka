package org.unibl.etf.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.entity.Grad;
import org.unibl.etf.entity.Klinika;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.util.DBUtil;

public class WrapperKlinika {

	private static final String SQL_SELECT_ALL = "SELECT * FROM zubarska_klinika";
	private static final String SQL_INSERT = "INSERT INTO zubarska_klinika (Naziv, Aktivan, Telefon, idGrad) values (?,true,?,?)";
	private static final String SQL_UPDATE ="UPDATE zubarska_klinika SET Naziv =?, Telefon=? WHERE IdZK = ?";
	private static final String SQL_UPDATE_CITY ="UPDATE zubarska_klinika SET idGrad=? WHERE IdZK = ?";

	public static List<Klinika> selectAll(){
		List<Klinika> result = new ArrayList<Klinika>();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL+" WHERE Aktivan = 1");
			while(rs.next()){
				result.add(new Klinika(rs.getInt(1), rs.getString("Naziv"), rs.getString("Telefon"), WrapperCjenovnik.selectBy(rs.getInt(1)),
						WrapperGrad.selectBy(new Grad(rs.getInt("idGrad"), null, null, null), null).get(0)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, s, rs);
		}
		return result;
	}
	
	public static List<Klinika> selectBy(Klinika in) {
		List<Klinika> result = new ArrayList<Klinika>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> temp = new ArrayList<Object>();
		try {
			c = DBUtil.getConnection();
			String query = SQL_SELECT_ALL + " WHERE aktivan = 1";
			if(in.getId() != null) {
				query += " AND IdZK = ?";
				temp.add(in.getId());
			}
			if(in.getNaziv() != null) {
				query += " AND Naziv = ?";
				temp.add(in.getNaziv());
			}
			if(in.getTelefon() != null) {
				query += " AND Telefon = ?";
				temp.add(in.getTelefon());
			}
			if(in.getLokacija() != null) {
				query += " AND idGrad = ?";
				temp.add(in.getLokacija().getId());
			}
			ps = DBUtil.prepareStatment(c, query, temp.toArray());
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(new Klinika(rs.getInt(1), rs.getString("Naziv"), rs.getString("Telefon"), WrapperCjenovnik.selectBy(rs.getInt(1)), 
						WrapperGrad.selectBy(new Grad(rs.getInt("idGrad"), null, null, null), null).get(0)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, rs);
		}
		return result;
	}
	
	public static int insert(Klinika in, Integer idGrad) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_INSERT, new Object[] {in.getNaziv(),in.getTelefon(),idGrad});
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
	
	public static int update(Klinika in) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_UPDATE, new Object[] {in.getNaziv(),in.getTelefon(), in.getId()});
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
	
	
	public static int updateCity(Klinika in, Integer idCity) {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_UPDATE_CITY, new Object[] {idCity, in.getId()});
			index = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, null);
		}
		return index;
	}

	public static List<Klinika> selectByCity(Integer idCity) {
		List<Klinika> result = new ArrayList<Klinika>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			String query = SQL_SELECT_ALL + " WHERE aktivan = 1 AND idGrad = ?";
			ps = DBUtil.prepareStatment(c, query, new Object[] {idCity});
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(new Klinika(rs.getInt(1), rs.getString("Naziv"), rs.getString("Telefon"), null, null));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, rs);
		}
		return result;
	}

}
