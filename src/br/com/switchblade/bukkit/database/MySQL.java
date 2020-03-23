package br.com.switchblade.bukkit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {

	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/switchblade", "switchblade", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		try {
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createTables(String[] table) {
		PreparedStatement ps;
		try {
			for (String tables : table) {
				ps = getConnection().prepareStatement(tables);
				ps.executeUpdate();
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeQuery(String query) {
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement(query);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean contains(String table, String where, String whereResult) {
		PreparedStatement ps;
		try {
			ps = getConnection()
					.prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';");
			ResultSet rs = ps.executeQuery();
			if (!rs.next())
				return false;
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public String getString(String table, String where, String whereResult, String column) {
		PreparedStatement ps;
		try {
			ps = getConnection()
					.prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';");
			ResultSet rs = ps.executeQuery();
			rs.next();
			String s = rs.getString(column);
			rs.close();
			ps.close();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public long getLong(String table, String where, String whereResult, String column) {
		PreparedStatement ps;
		try {
			ps = getConnection()
					.prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';");
			ResultSet rs = ps.executeQuery();
			rs.next();
			long l = rs.getLong(column);
			rs.close();
			ps.close();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getInt(String table, String where, String whereResult, String column) {
		PreparedStatement ps;
		try {
			ps = getConnection()
					.prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';");
			ResultSet rs = ps.executeQuery();
			rs.next();
			int i = rs.getInt(column);
			rs.close();
			ps.close();
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void update(String table, String where, String whereResult, String update, String updateResult) {
		PreparedStatement ps;
		try {
			String query = "UPDATE `" + table + "` SET `" + update + "` = '" + updateResult + "' WHERE `" + where
					+ "` = '" + whereResult + "';";
			ps = getConnection().prepareStatement(query);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(String table, String where, String whereResult, String update, long updateResult) {
		PreparedStatement ps;
		try {
			String query = "UPDATE `" + table + "` SET `" + update + "` = '" + updateResult + "' WHERE `" + where
					+ "` = '" + whereResult + "';";
			ps = getConnection().prepareStatement(query);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(String table, String where, String whereResult) {
		PreparedStatement ps;
		try {
			String query = "DELETE FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';";
			ps = getConnection().prepareStatement(query);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}