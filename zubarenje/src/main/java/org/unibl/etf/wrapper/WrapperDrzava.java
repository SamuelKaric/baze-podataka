package org.unibl.etf.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.entity.Drzava;
import org.unibl.etf.entity.Grad;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.util.DBUtil;

public class WrapperDrzava {

	private static final String SQL_SELECT_ALL = "SELECT * FROM Drzava";
	private static final String SQL_INSERT = "INSERT INTO Drzava (Naziv) values (?)";
	private static final String SQL_UPDATE ="UPDATE Drzava SET Naziv =? WHERE IdDrzava = ?";

	public static List<Drzava> selectAll(){
		List<Drzava> result = new ArrayList<>();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()){
				result.add(new Drzava(rs.getInt(1), rs.getString("Naziv"), WrapperGrad.selectBy(null, rs.getInt(1))));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, s, rs);
		}
		return result;
	}

	public static List<Drzava> selectBy(Drzava in) throws InputException {
		List<Drzava> result = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> temp = new ArrayList<>();
		try {
			c = DBUtil.getConnection();
			String query = SQL_SELECT_ALL + " WHERE true";
			if(in.getId() != null) {
				query += " AND IdDrzava = ?";
				temp.add(in.getId());
			}
			if(in.getNaziv() != null) {
				query += " AND Naziv = ?";
				temp.add(in.getNaziv());
			}
			ps = DBUtil.prepareStatment(c, query, temp.toArray());
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(new Drzava(rs.getInt(1), rs.getString("Naziv"), WrapperGrad.selectBy(null, rs.getInt(1))));
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

	public static int insert(Drzava in) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_INSERT, new Object[] {in.getNaziv()});
			if(in.getGradovi()!=null) {
			for(Grad g:in.getGradovi()) {
				List<Grad> temp = WrapperGrad.selectBy(null, in.getId());
				if(temp.size()!=0) {
					WrapperGrad.insert(g,in.getId());
				}else {
					WrapperGrad.update(g, in.getId());
				}
			}
			}
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

	public static int update(Drzava in) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_UPDATE, new Object[] {in.getNaziv(), in.getId()});
			if(in.getGradovi() != null) {
				for(Grad g: in.getGradovi()) {
					WrapperGrad.update(g, in.getId());
				}
			}
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
