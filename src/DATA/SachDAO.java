/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;

import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.Sach;
import DTO.TacGia;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class SachDAO {

	// Lấy tất cả độc giả từ Database xuống
	public ArrayList<Sach> getSach_Data() {
		ArrayList<Sach> List_Docgianew = new ArrayList<Sach>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();

			String Query = "SELECT s.MA_SACH AS MS, s.TEN_SACH AS TS, s.MO_TA AS MT, s.NAM_XB AS NAMXB, s.MA_TGIA AS MTG, s.MA_NXB AS MANXB, s.DON_GIA AS DG, s.SO_LUONG AS SL, s.TRANG_THAI AS TT, s.MA_LOAI_SACH AS MLS, "
					+ "tg.TEN_TGIA AS TTG, nxb.TEN_NXB AS TNXB, ls.TEN_LOAI AS TL, tg.MA_TGIA, nxb.MA_NXB, ls.MA_LOAI "
					+ "FROM SACH AS s " + "JOIN TAC_GIA AS tg ON s.MA_TGIA = tg.MA_TGIA "
					+ "JOIN NHA_XUAT_BAN AS nxb ON s.MA_NXB = nxb.MA_NXB "
					+ "JOIN LOAI_SACH AS ls ON s.MA_LOAI_SACH = ls.MA_LOAI " + "WHERE s.TRANG_THAI = 1";

			// Tạo đối tượng Statement
			Statement statement = connectdata.createStatement();

			// Xử lý kết quả trả về từ truy vấn
			try (
					// Thực thi truy vấn SQL
					ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				// Tạo một ArrayList rỗng
				while (resultSet.next()) {
					Sach sach = new Sach();
					NhaXuatBan nxb = new NhaXuatBan();
					TacGia tg = new TacGia();
					LoaiSach ls = new LoaiSach();
					sach.setMaSach(resultSet.getString("MS"));
					sach.setTenSach(resultSet.getString("TS"));
					sach.setMoTa(resultSet.getString("MT"));
					sach.setNamXB(resultSet.getString("NAMXB"));
					tg.setMaTG(resultSet.getString("MT"));
					tg.setTenTG(resultSet.getString("TTG"));
					nxb.setMaNXB(resultSet.getString("MANXB"));
					nxb.setTenNXB(resultSet.getString("TNXB"));
					sach.setDonGia(resultSet.getInt("DG"));
					sach.setSoLuong(resultSet.getInt("SL"));
					sach.setTrangThai(resultSet.getInt("TT"));
					ls.setMaLoai(resultSet.getString("MLS"));
					ls.setTenLoai(resultSet.getString("TL"));
					sach.setTacGia(tg);
					sach.setMaNXB(nxb);
					sach.setMaLS(ls);
					List_Docgianew.add(sach);
				}

			}
			// Đóng connect
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng Sách ");
		}
		return List_Docgianew;
	}

	// Update một độc giả mới lên cở sở dữ liệu
	public int insertSach_Data(Sach sach) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "INSERT INTO sach SET MA_SACH=?, TEN_SACH=?, MO_TA=?, NAM_XB=?, MA_TGIA=?, MA_NXB=?, DON_GIA=?, SO_LUONG=?, TRANG_THAI=?, MA_LOAI_SACH=?";
			int rowsAffected;

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, sach.getMaSach());
				preparedStatement.setString(2, sach.getTenSach());
				preparedStatement.setString(3, sach.getMoTa());
				preparedStatement.setString(4, sach.getNamXB());
				preparedStatement.setString(5, sach.getTacGia().getMaTG());
				preparedStatement.setString(6, sach.getMaNXB().getMaNXB());
				preparedStatement.setInt(7, sach.getDonGia());
				preparedStatement.setInt(8, sach.getSoLuong());
				preparedStatement.setInt(9, sach.getTrangThai());

				preparedStatement.setString(10, sach.getMaLS().getMaLoai());

				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Đã thêm sách thành công");
				} else {
					System.out.println("Không thể thêm thông tin sách mới!");
				}
			}

			conn.closeConnection(connectdata);
			return rowsAffected;
		} catch (SQLException e) {
			System.out.println("Lỗi: " + e.getMessage());
		}
		return -1;
	}

	// Sủa thông tin của một độc giả (cho sửa tên ,sdt, email) update lên databse
	public int EditSach_Data(Sach sach) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "UPDATE sach  SET TEN_SACH=?, MO_TA=? ,NAM_XB=? ,MA_TGIA=? ,MA_NXB=?, DON_GIA=?, SO_LUONG=?, TRANG_THAI=? ,MA_LOAI_SACH=? WHERE MA_SACH=?;";

			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {

				preparedStatement.setString(1, sach.getTenSach());
				preparedStatement.setString(2, sach.getMoTa());
				preparedStatement.setString(3, sach.getNamXB());
				preparedStatement.setString(4, sach.getTacGia().getMaTG());
				preparedStatement.setString(5, sach.getMaNXB().getMaNXB());
				preparedStatement.setInt(6, sach.getDonGia());
				preparedStatement.setInt(7, sach.getSoLuong());
				preparedStatement.setInt(8, sach.getTrangThai());
				preparedStatement.setString(9, sach.getMaLS().getMaLoai());
				preparedStatement.setString(10, sach.getMaSach());
				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Đã thay đổi thông tin sách thành công");
				} else {
					System.out.println("Không thể thay đổi thông tin sách");
				}
			}
			// Đóng PreparedStatement
			conn.closeConnection(connectdata);
			return rowsAffected;
		} catch (SQLException e) {
			System.out.printf("\n Lỗi ");
		}
		return -1;
	}

	// Xóa thông tin độc giả (set trạng thái về 0)
	public int XoaSach_Data(Sach sach) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "UPDATE sach  SET TRANG_THAI=0  WHERE MA_SACH=?";
			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, sach.getMaSach());
				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Đã xóa sách thành công");
				} else {
					System.out.println("Không thể xóa sách");
				}
			}
			// Đóng PreparedStatement
			conn.closeConnection(connectdata);
			return rowsAffected;
		} catch (SQLException e) {
			System.out.printf("\n Lỗi ");
		}
		return -1;
	}

	public void Insert_NhieuSach(ArrayList<Sach> s) throws SQLException {
		// Tạo thể hiện
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "INSERT INTO `quanlithuvien`.`sach` (`MA_SACH`, `TEN_SACH`, `MO_TA`, `NAM_XB`, `MA_TGIA`,`MA_NXB`,`DON_GIA`,`SO_LUONG`,`TRANG_THAI`,`MA_LOAI_SACH`) VALUES (?,?, ?, ?, ?,?,?,?,?,?);";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (Sach sach : s) {
			statement.setString(1, sach.getMaSach());
			statement.setString(2, sach.getTenSach());
			statement.setString(3, sach.getMoTa());
			statement.setString(4, sach.getNamXB());
			statement.setString(5, sach.getTacGia().getMaTG());
			statement.setString(6, sach.getMaNXB().getMaNXB());
			statement.setInt(7, sach.getDonGia());
			statement.setInt(8, sach.getSoLuong());
			statement.setInt(9, sach.getTrangThai());
			statement.setString(10, sach.getMaLS().getMaLoai());

			statement.addBatch(); // Thêm câu lệnh vào batch
		}

		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);

		System.out.println("Thêm dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

	}

	public void Edit_NhieuSach(ArrayList<Sach> s) throws SQLException {
		// Tạo thể hiện
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE sach SET TEN_SACH=? , MO_TA=? , NAM_XB=? , MA_TGIA=? , MA_NXB=? , DON_GIA=? , SO_LUONG=? , TRANG_THAI=? , MA_LOAI_SACH=? WHERE MA_SACH=?;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (Sach sach : s) {
			statement.setString(1, sach.getTenSach());
			statement.setString(2, sach.getMoTa());
			statement.setString(3, sach.getNamXB());
			statement.setString(4, sach.getTacGia().getMaTG());
			statement.setString(5, sach.getMaNXB().getMaNXB());
			statement.setInt(6, sach.getDonGia());
			statement.setInt(7, sach.getSoLuong());
			statement.setInt(8, sach.getTrangThai());
			statement.setString(9, sach.getMaLS().getMaLoai());
			statement.setString(10, sach.getMaSach());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}
		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);

		System.out.println("Sửa dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

	}

	public ArrayList<NhaXuatBan> getListNhaXuatBan() {
		ArrayList<NhaXuatBan> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT  MA_NXB, TEN_NXB " + "FROM  NHA_XUAT_BAN " + " WHERE TRANG_THAI=1";
			// Tạo đối tượng Statement
			java.sql.Statement statement = connectdata.createStatement();
			try (
					// Thực thi truy vấn SQL
					ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				while (resultSet.next()) {
					NhaXuatBan obj = new NhaXuatBan();
					obj.setMaNXB(resultSet.getString("MA_NXB"));
					obj.setTenNXB(resultSet.getString("TEN_NXB"));
					list.add(obj);
				}
			}
			// Đóng connect
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng NHA_XUAT_BAN ");
		}
		return list;
	}

	// Lấy dữ liệu từ Tác Giả
	public ArrayList<TacGia> getListTacGia() {
		ArrayList<TacGia> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT  MA_TGIA, TEN_TGIA " + "FROM  TAC_GIA " + " WHERE TRANG_THAI=1";
			// Tạo đối tượng Statement
			java.sql.Statement statement = connectdata.createStatement();
			try (
					// Thực thi truy vấn SQL
					ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				while (resultSet.next()) {
					TacGia obj = new TacGia();
					obj.setMaTG(resultSet.getString("MA_TGIA"));
					obj.setTenTG(resultSet.getString("TEN_TGIA"));
					list.add(obj);
				}
			}
			// Đóng connect
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng TAC_GIA ");
		}
		return list;
	}

	// Lấy dữ liệu từ Loaị Sách
	public ArrayList<LoaiSach> getListLoaiSach() {
		ArrayList<LoaiSach> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT  MA_LOAI, TEN_LOAI " + "FROM  LOAI_SACH" + " WHERE TRANG_THAI=1";
			// Tạo đối tượng Statement
			java.sql.Statement statement = connectdata.createStatement();
			try (
					// Thực thi truy vấn SQL
					ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				while (resultSet.next()) {
					LoaiSach obj = new LoaiSach();
					obj.setMaLoai(resultSet.getString("MA_LOAI"));
					obj.setTenLoai(resultSet.getString("TEN_LOAI"));
					list.add(obj);
				}
			}
			// Đóng connect
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng LOAI_SACH ");
		}
		return list;
	}
}
