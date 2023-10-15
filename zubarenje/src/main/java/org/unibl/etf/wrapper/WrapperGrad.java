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

public class WrapperGrad {

	private static final String SQL_SELECT_ALL = "SELECT * FROM grad";
	private static final String SQL_INSERT = "INSERT INTO grad (Naziv, PostanskiBroj, idDrzava) values (?,?,?)";
	private static final String SQL_UPDATE ="UPDATE grad SET Naziv =?, PostanskiBroj=? WHERE IdGrad = ?";
	private static final String SQL_UPDATE_DRZAVA ="UPDATE grad SET Naziv =?, PostanskiBroj=?, idDrzava=? WHERE IdGrad = ?";

	public static List<Grad> selectAll(){
		List<Grad> result = new ArrayList<>();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			s = c.createStatement();
			rs = s.executeQuery(SQL_SELECT_ALL);
			while(rs.next()){
				result.add(new Grad(rs.getInt(1), rs.getString("Naziv"), WrapperKlinika.selectByCity(rs.getInt(1)), rs.getString("PostanskiBroj")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, s, rs);
		}
		return result;
	}

	public static List<Grad> selectBy(Grad in, Integer idDrzava) {
		List<Grad> result = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> temp = new ArrayList<>();
		try {
			c = DBUtil.getConnection();
			String query = SQL_SELECT_ALL + " WHERE true";
			if(in!=null) {
				if(in.getId() != null) {
					query += " AND IdGrad = ?";
					temp.add(in.getId());
				}
				if(in.getNaziv() != null) {
					query += " AND Naziv = ?";
					temp.add(in.getNaziv());
				}
				if(in.getPostanskiBroj() != null) {
					query += " AND PostanskiBroj = ?";
					temp.add(in.getPostanskiBroj());
				}
			}
			if(idDrzava != null) {
				query += " AND idDrzava = ?";
				temp.add(idDrzava);
			}
			ps = DBUtil.prepareStatment(c, query, temp.toArray());
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(new Grad(rs.getInt(1), rs.getString("Naziv"), WrapperKlinika.selectByCity(rs.getInt(1)), rs.getString("PostanskiBroj")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, rs);
		}
		return result;
	}

	public static int insert(Grad in, Integer drzava) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_INSERT, new Object[] {in.getNaziv(), in.getPostanskiBroj(),drzava});
			if(in.getKlinike() != null) {
				for(Klinika k:in.getKlinike()) {
					List<Klinika> temp = WrapperKlinika.selectBy(k);
					if(temp.size()!=0) {
						WrapperKlinika.insert(k,in.getId());
					}else {
						WrapperKlinika.updateCity(k,in.getId());
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

	public static int update(Grad in, Integer drzava) throws InputException {
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBUtil.getConnection();
			if(drzava==null) {
				ps = DBUtil.prepareStatment(c, SQL_UPDATE, new Object[] {in.getNaziv(),in.getPostanskiBroj(), in.getId()});
			}else {
				ps = DBUtil.prepareStatment(c, SQL_UPDATE_DRZAVA, new Object[] {in.getNaziv(),in.getPostanskiBroj(), drzava, in.getId()});
			}
			if(in.getKlinike() != null) {
				for(Klinika k: in.getKlinike()) {
					WrapperKlinika.update(k);
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
