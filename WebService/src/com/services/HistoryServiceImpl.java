package com.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.jws.WebService;

import com.models.History;

@WebService(endpointInterface = "com.services.HistoryService")
public class HistoryServiceImpl implements HistoryService {

	@Override
	public History[] getHistoryAsCustomer(int id) {
		ArrayList<History> ans = new ArrayList<History>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("SELECT * FROM orderhistory WHERE id_customer=? AND hidden_c=0");
			ps.setString(1, String.valueOf(id));

			// Execute query
			rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // rs is empty
				
			} else {
				rs.next();
				while (!rs.isAfterLast()) {
					History his = new History(rs);
					ans.add(his);
					rs.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ans.clear();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ans.toArray(new History[ans.size()]);
	}

	@Override
	public History[] getHistoryAsDriver(int id) {
		ArrayList<History> ans = new ArrayList<History>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("SELECT * FROM orderhistory WHERE id_driver=? AND hidden_d=0");
			ps.setString(1, String.valueOf(id));

			// Execute query
			rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // rs is empty
				
			} else {
				rs.next();
				while (!rs.isAfterLast()) {
					History his = new History(rs);
					ans.add(his);
					rs.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ans.clear();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ans.toArray(new History[ans.size()]);
	}

	@Override
	public boolean hideHistoryAsDriver(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("UPDATE orderhistory SET hidden_d=1 WHERE id_order=?");
			ps.setString(1, String.valueOf(id));

			// Execute query
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean hideHistoryAsCustomer(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("UPDATE orderhistory SET hidden_c=1 WHERE id_order=?");
			ps.setString(1, String.valueOf(id));

			// Execute query
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}