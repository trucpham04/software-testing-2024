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

public class NhanVienDAO {
	private ConnectDataBase cn = new ConnectDataBase();
	private ArrayList<NhanVien> dsnv = new ArrayList<>();

	public NhanVienDAO() {
	}

	public ArrayList<NhanVien> list() {
		ArrayList<NhanVien> dsnv = new ArrayList<>();
		try {

			String sql = "SELECT * FROM nhan_vien WHERE TRANG_THAI = 1";
			ResultSet rs = cn.executeQuery(sql);
			while (rs.next()) {
				String maNV = rs.getString("MA_NV");
				String hoten = rs.getString("HO_TEN");
				String email = rs.getString("EMAIL");
				String SDT = rs.getString("SDT");
				int trangthai = rs.getInt("TRANG_THAI");
				NhanVien nv = new NhanVien(maNV, hoten, email, SDT, trangthai);
				dsnv.add(nv);
			}
			rs.close();
			cn.disConnect();

		} catch (SQLException ex) {
			Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
		}

		return dsnv;
	}

	public void set(NhanVien nv) {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "UPDATE nhan_vien SET ";

		sql += "HO_TEN='" + nv.getHoTen() + "', ";
		sql += "EMAIL='" + nv.getEmail() + "', ";
		sql += "SDT='" + nv.getSDT() + "', ";
		sql += "TRANG_THAI='" + nv.getTrangThai() + "'";
		sql += "WHERE MA_NV='" + nv.getMaNV() + "'";

		cn.executeUpdate(sql);
		System.out.println(sql);
	}

	public void add(NhanVien nv) {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "INSERT INTO nhan_vien VALUES (";
		sql += "'" + nv.getMaNV() + "',";
		sql += "'" + nv.getHoTen() + "',";
		sql += "'" + nv.getEmail() + "',";
		sql += "'" + nv.getSDT() + "',";
		sql += "'" + nv.getTrangThai() + "')";
		System.out.println(sql);
		cn.executeUpdate(sql);
	}

	public void delete(NhanVien MaNV) {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "DELETE nhan_vien WHERE MA_NV='" + MaNV + "'";
		cn.executeUpdate(sql);
		System.out.println(sql);
	}

	public void delete2(String MaNV) {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "UPDATE nhan_vien SET TRANG_THAI=0 WHERE MA_NV='" + MaNV + "'";
		cn.executeUpdate(sql);
		System.out.println(sql);
	}

	public void Insert_NhieuNV(ArrayList<NhanVien> nv) throws SQLException {
		// Tạo thể hiện
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "INSERT INTO `quanlithuvien`.`nhan_vien` (`MA_NV`, `HO_TEN`, `EMAIL`, `SDT`, `TRANG_THAI`) VALUES (?,?, ?, ?, ?);";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (NhanVien obj : nv) {
			statement.setString(1, obj.getMaNV());
			statement.setString(2, obj.getHoTen());
			statement.setString(3, obj.getEmail());
			statement.setString(4, obj.getSDT());
			statement.setInt(5, obj.getTrangThai());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}
		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);

		System.out.println("Thêm dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

	}

	public void Edit_NhieuNV(ArrayList<NhanVien> nv) throws SQLException {
		// Tạo thể hiện
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE nhan_vien  SET HO_TEN=? ,  EMAIL=?, SDT=?  ,TRANG_THAI=? WHERE MA_NV=?;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (NhanVien obj : nv) {
			statement.setString(1, obj.getHoTen());
			statement.setString(2, obj.getEmail());
			statement.setString(3, obj.getSDT());
			statement.setInt(4, obj.getTrangThai());
			statement.setString(5, obj.getMaNV());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}

		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);

		System.out.println("Sửa dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

	}

}
