package org.unibl.etf.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.entity.CjenovnikItem;
import org.unibl.etf.entity.Tretman;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.util.DBUtil;

import javafx.util.Pair;

public class WrapperCjenovnik {
	private static final String SQL_SELECT = "SELECT * FROM cjenovnik_item WHERE idZK=?";
	private static final String SQL_INSERT = "INSERT INTO cjenovnik_item (IdTretman, idZK, Cijena) values (?,?,?)";
	private static final String CHEEPER_CAlL = "CALL Cheap_Procedure_Nearby( ? , ? , ?)";


	
	public static List<CjenovnikItem> selectBy(Integer idZK) {
		List<CjenovnikItem> result = new ArrayList<CjenovnikItem>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> temp = new ArrayList<Object>();
		try {
			c = DBUtil.getConnection();
			String query = SQL_SELECT;
			temp.add(idZK);
			ps = DBUtil.prepareStatment(c, query, temp.toArray());
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(new CjenovnikItem(WrapperTretman.selectBy(new Tretman(rs.getInt("idTretman"), null, null)).get(0), rs.getDouble("Cijena")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, null);
		}
		return result;
	}
	
	public static List<Pair<String, Double>> cheaper(Integer idTretman, Integer idZK, Double cijena) {
		List<Pair<String, Double>> result = new ArrayList<Pair<String, Double>>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> temp = new ArrayList<Object>();
		try {
			c = DBUtil.getConnection();
			temp.add(idTretman); temp.add(idZK); temp.add(cijena); 
			ps = DBUtil.prepareStatment(c, CHEEPER_CAlL, temp.toArray());
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(new Pair<String, Double>(rs.getString("naziv"), rs.getDouble("Cijena")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, null);
		}
		return result;
	}
	
	
	public static int insert(CjenovnikItem in, Integer idZK) throws InputException{
		int index = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DBUtil.getConnection();
			ps = DBUtil.prepareStatment(c, SQL_INSERT, new Object[] {in.getTretman().getId(), idZK, in.getCijena()});
			index = ps.executeUpdate();
		}catch(SQLIntegrityConstraintViolationException e) {
			throw new InputException("Already Exists");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(c, ps, rs);
		}
		return index;
	}
}
