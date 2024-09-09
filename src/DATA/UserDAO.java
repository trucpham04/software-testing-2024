package DATA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.ConnectDataBase;
import DTO.DocGia;
import DTO.NhanVien;
import DTO.User;

public class UserDAO {
	private ConnectDataBase cn = new ConnectDataBase();
	private ArrayList<User> dsus = new ArrayList<>();

	public UserDAO() {

	}

	public ArrayList<User> list() {
		ArrayList<User> dsus = new ArrayList<>();
		try {

			String sql = "SELECT * FROM users WHERE TRANG_THAI=1";
			ResultSet rs = cn.executeQuery(sql);
			while (rs.next()) {
				String userID = rs.getString("MA_NV");
				String username = rs.getString("USERNAME");
				String pass = rs.getString("PASSWORD");
				String macv = rs.getString("MA_QUYEN");
				int trangthai = rs.getInt("TRANG_THAI");

				User us = new User(userID, username, pass, macv, trangthai);
				dsus.add(us);
			}
			rs.close();
			cn.disConnect();

		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return dsus;
	}

	public void set(User us) {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "UPDATE users SET ";
		sql += "USERNAME='" + us.getUsername() + "', ";
		sql += "PASSWORD='" + us.getPassword() + "', ";
		sql += "MA_QUYEN='" + us.getMacv() + "', ";
		sql += "TRANG_THAI='" + us.getTrangthai() + "' ";
		sql += " WHERE MA_NV='" + us.getManv() + "'";
		System.out.println(sql);

		cn.executeUpdate(sql);
	}

	public void add(User us) {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "INSERT INTO users VALUES (";
		sql += "'" + us.getManv() + "',";
		sql += "'" + us.getUsername() + "',";
		sql += "'" + us.getPassword() + "',";
		sql += "'" + us.getMacv() + "',";
		sql += "'" + us.getTrangthai() + "')";
		System.out.println(sql);
		cn.executeUpdate(sql);
	}

	public void delete(User userID) {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "DELETE users WHERE MA_NV='" + userID + "'";
		cn.executeUpdate(sql);
		System.out.println(sql);
	}

	public void delete2(String userID) {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "UPDATE users SET TRANG_THAI=0 WHERE MA_NV='" + userID + "'";
		cn.executeUpdate(sql);
		System.out.println(sql);
	}

	public void Insert_NhieuDocGia(ArrayList<User> us) throws SQLException {
		// Tạo thể hiện
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "INSERT INTO `quanlithuvien`.`users` (`MA_NV`, `USERNAME`, `PASSWORD`, `MA_QUYEN`, `TRANG_THAI`) VALUES (?,?, ?, ?, ?);";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (User obj : us) {
			statement.setString(1, obj.getManv());
			statement.setString(2, obj.getUsername());
			statement.setString(3, obj.getPassword());
			statement.setString(4, obj.getMacv());
			statement.setInt(5, obj.getTrangthai());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}

		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);

		System.out.println("Thêm dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

	}

	public void Edit_NhieuDocGia(ArrayList<User> us) throws SQLException {
		// Tạo thể hiện
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE users  SET USERNAME=? ,PASSWORD=?  , MA_QUYEN=?, TRANG_THAI=? WHERE MA_NV=?;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (User obj : us) {
			statement.setString(1, obj.getUsername());
			statement.setString(2, obj.getPassword());
			statement.setString(3, obj.getMacv());
			statement.setInt(4, obj.getTrangthai());
			statement.setString(5, obj.getManv());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}

		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);

		System.out.println("Sửa dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

	}

}
