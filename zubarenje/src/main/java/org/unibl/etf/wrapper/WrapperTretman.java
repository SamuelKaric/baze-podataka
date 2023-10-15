package org.unibl.etf.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.entity.Tretman;
import org.unibl.etf.util.DBUtil;

public class WrapperTretman {
	private static final String SQL_SELECT_ALL = "SELECT * FROM tretman";
	private static final String SQL_INSERT = "INSERT INTO tretman (Naziv, Opis) values (?,?)";
	private static final String SQL_UPDATE ="UPDATE tretman SET Naziv=?, Opis=? WHERE IdTretman = ?";

	public static List<Tretman> selectAll(){
		List<Tretman> result = new ArrayList<Tretman>();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = DBUtil.getConnection();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()){
				result.add(new Tretman(rs.getInt(1), rs.getString("Naziv"),rs.getString("Opis")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, s, rs);
		}
		return result;
	}
	
	public static List<Tretman> selectBy(Tretman in) {
		List<Tretman> result = new ArrayList<Tretman>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> temp = new ArrayList<Object>();
		try {
			c = DBUtil.getConnection();
			String query = SQL_SELECT_ALL + " WHERE true";
			if(in.getId() != null) {
				query += " AND IdTretman = ?";
				temp.add(in.getId());
			}
			if(in.getNaziv() != null) {
				query += " AND Naziv = ?";
				temp.add(in.getNaziv());
			}
			if(in.getOpis() != null) {
				query += " AND Opis = ?";
				temp.add(in.getOpis());
			}
			ps = DBUtil.prepareStatment(c, query, temp.toArray());
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(new Tretman(rs.getInt(1), rs.getString("Naziv"),rs.getString("Opis")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, null);
		}
		return result;
	}
	
	public static int insert(Tretman in) {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_INSERT, new Object[] {in.getNaziv(), in.getOpis()});
			index = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, rs);
		}
		return index;
	}
	
	public static int update(Tretman in) {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_UPDATE, new Object[] {in.getNaziv(), in.getOpis(),in.getId()});
			index = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, null);
		}
		return index;
	}
}
