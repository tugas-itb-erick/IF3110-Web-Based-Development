package com.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.jws.WebService;

import com.models.Location;
import com.models.User;

@WebService(endpointInterface = "com.services.UserService")
public class UserServiceImpl implements UserService {

	@Override
	public User getUser(String token, int id) throws TokenException {
		if (!TokenValidator.validateToken(token)) {
			throw new TokenException();
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			ps.setString(1, String.valueOf(id));

			// Execute query
			rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // rs is empty
				return new User();
			} else {
				rs.next();
				User user = new User(rs);
				loadPreferredLocations(token, user);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return new User();
	}
	
	@Override
	public User getUserByToken(String token) throws TokenException {
		if (!TokenValidator.validateToken(token)) {
			throw new TokenException();
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gaussianlord_acc", "root", "");

			ps = conn.prepareStatement("SELECT id FROM account_token WHERE token=?");
			ps.setString(1, token);

			// Execute query
			rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // rs is empty
				return new User();
			} else {
				rs.next();
				int idActive = rs.getInt(1);
				return getUser(token, idActive);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return new User();
	}
	
	@Override
	public User getPreferredDriver(String token, String username, String pickup, String dest) throws TokenException {
		if (!TokenValidator.validateToken(token)) {
			throw new TokenException();
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("SELECT * FROM user NATURAL JOIN preferredlocation WHERE username=? AND is_driver=1 AND (location=? OR location=?) ");
			ps.setString(1, username);
			ps.setString(2, pickup);
			ps.setString(3, dest);

			// Execute query
			rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // rs is empty
				return new User();
			} else {
				rs.next();
				return new User(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return new User();
	}
	
	@Override
	public User[] getDriver(String token, String pickup, String dest) throws TokenException {
		if (!TokenValidator.validateToken(token)) {
			throw new TokenException();
		}
		ArrayList<User> drivers = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("SELECT DISTINCT id, username, email, phone_num, img_path, fullname, is_driver, star, vote FROM user NATURAL JOIN preferredlocation WHERE is_driver=1 AND (location=? OR location=?) ");
			ps.setString(1, pickup);
			ps.setString(2, dest);

			// Execute query
			rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // rs is empty
				
			} else {
				rs.next();
				while (!rs.isAfterLast()) {
					User user = new User(rs);
					drivers.add(user);
					rs.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			drivers.clear();
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
		return drivers.toArray(new User[drivers.size()]);
	}
	
	@Override
	public ArrayList<Location> loadPreferredLocations(String token, User user) throws TokenException {
		if (!TokenValidator.validateToken(token)) {
			throw new TokenException();
		}
		int id = user.getId();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("SELECT * FROM preferredlocation WHERE id=?");
			ps.setString(1, String.valueOf(id));

			// Execute query
			rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // rs is empty
				return new ArrayList<Location>();
			} else {
				rs.next();
				user.loadPreferredLocations(rs);
				return user.getPreferredLocations();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return new ArrayList<Location>();
	}

	@Override
	public boolean saveUser(String token, User user) throws TokenException {
		if (!TokenValidator.validateToken(token)) {
			throw new TokenException();
		}
		int id = user.getId();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("UPDATE user SET username=?, email=?, phone_num=?, img_path=?, fullname=?, is_driver=?, star=?, vote=? WHERE id=?");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhoneNumber());
			ps.setString(4, user.getImagePath());
			ps.setString(5, user.getName());
			if (user.isDriver()) {
				ps.setString(6, String.valueOf(1));	
			} else {
				ps.setString(6, String.valueOf(0));
			}
			ps.setString(7, String.valueOf(user.getStar()));
			ps.setString(8, String.valueOf(user.getVote()));
			ps.setString(9, String.valueOf(id));


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
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int createUser(String token, User user) throws TokenException {
		if (!TokenValidator.validateToken(token)) {
			throw new TokenException();
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Setting up
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gaussianlord_main", "root", "");

			ps = conn.prepareStatement("insert into user(username, email, phone_num, fullname, is_driver) "
					+ "values(?,?,?,?,?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhoneNumber());
			ps.setString(4, user.getName());
			if (user.isDriver()) {
				ps.setString(5, String.valueOf(1));	
			} else {
				ps.setString(5, String.valueOf(0));
			}
			
			ps.executeUpdate();
			
			PreparedStatement ps2 = conn.prepareStatement("select * from user where username=?");
			ps2.setString(1, user.getUsername());
			rs = ps2.executeQuery();
			
			rs.next();
			int id = rs.getInt(1);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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

	}
}