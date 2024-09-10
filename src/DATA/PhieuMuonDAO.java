/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import DTO.CTPhieuMuon;
import DTO.CTPhieuNhap;
import DTO.ConnectDataBase;
import DTO.CTPhieuMuon;
import DTO.DocGia;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.NhanVien;
import DTO.PhieuMuon;
import DTO.PhieuNhap;
import DTO.PhieuMuon;
import DTO.SachCT;
import DTO.TacGia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
/*
 *
 * @author Admin
 */
public class PhieuMuonDAO {

	/**
	 *
	 * @param maPMUON
	 * @return
	 */

	// Lấy dữ liệu của chi tiết phiếu nhập dựa theo một mã phiếu nhập có sẵn
	@SuppressWarnings("empty-statement")
	public ArrayList<CTPhieuMuon> getListCTPhieuMuon(String maPMUON) {
		ArrayList<CTPhieuMuon> list = new ArrayList<>();
		try {

			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT ctpm.MA_PMUON AS MAPMUON,  ctpm.MA_SACH AS MASACH,  ctpm.SO_LUONG AS SOLUONG,  "
					+ "s.TEN_SACH AS TENSACH " + "FROM ct_pmuon AS ctpm,  sach AS s "
					+ "WHERE ctpm.MA_SACH=s.MA_SACH AND ctpm.MA_PMUON=? AND ctpm.TRANG_THAI=1";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				preparedStatement.setString(1, maPMUON);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						// Xử lý kết quả trả về từ truy vấn
						CTPhieuMuon obj = new CTPhieuMuon();
						SachCT objsach = new SachCT();
						obj.setPhieuMuon(resultSet.getString("MAPMUON"));
						objsach.set_MaSach(resultSet.getString("MASACH"));
						objsach.set_TenSach(resultSet.getString("TENSACH"));
						obj.setSoLuong(resultSet.getInt("SOLUONG"));
						obj.setSach(objsach);
						list.add(obj);
					}
				}
			}

			// Đóng connect
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			e.getMessage();
		}
		return list;
	}

	public ArrayList<CTPhieuMuon> getListCTPhieuMuon1() {
		ArrayList<CTPhieuMuon> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();
			String Query = "SELECT sach.MA_SACH, sach.TEN_SACH, " + "COUNT(ct_pmuon.MA_PMUON) AS SoLanMuon, "
					+ "SUM(ct_pmuon.SO_LUONG) AS SoLuongMuon, " + "TotalMuon.TotalSoLanMuon AS TongSoLanMuon "
					+ "FROM sach " + "JOIN ct_pmuon ON sach.MA_SACH = ct_pmuon.MA_SACH "
					+ "JOIN phieu_muon ON ct_pmuon.MA_PMUON = phieu_muon.MA_PMUON " + "JOIN (SELECT ct_pmuon.MA_SACH, "
					+ "SUM(ct_pmuon.SO_LUONG) AS TotalSoLuongMuon, " + "COUNT(ct_pmuon.MA_PMUON) AS TotalSoLanMuon "
					+ "FROM ct_pmuon " + "GROUP BY ct_pmuon.MA_SACH) AS TotalMuon ON sach.MA_SACH = TotalMuon.MA_SACH "
					+ "GROUP BY sach.MA_SACH, sach.TEN_SACH, TotalMuon.TotalSoLanMuon";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						// Xử lý kết quả trả về từ truy vấn
						CTPhieuMuon obj = new CTPhieuMuon();
						SachCT objsach = new SachCT();
						objsach.set_MaSach(resultSet.getString("MA_SACH"));
						objsach.set_TenSach(resultSet.getString("TEN_SACH"));
						obj.setSach(objsach);
						obj.setSoLanMuon(resultSet.getInt("SoLanMuon"));
						obj.setTongSL(resultSet.getInt("SoLuongMuon"));
						list.add(obj);
					}
				}
			}

			// Đóng connect
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Lấy dữ liệu từ Phiếu Nhập
	@SuppressWarnings("empty-statement")
	public ArrayList<PhieuMuon> getListPhieuMuon() {
		ArrayList<PhieuMuon> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT pm.MA_PMUON AS MAPMUON, pm.NG_MUON AS NGMUON, pm.MA_NV AS MANV, pm.MA_DG AS MADG, pm.NG_TRA AS NGTRA, pm.SO_LUONG AS SOLUONG, pm.TRANG_THAI AS TRANGTHAI, "
					+ "dg.HO_TEN AS HOTEN, nv.HO_TEN AS TENNV " + "FROM PHIEU_MUON AS pm "
					+ "JOIN NHAN_VIEN AS nv ON pm.MA_NV = nv.MA_NV " + "JOIN DOC_GIA AS dg ON pm.MA_DG = dg.MA_DG "
					+ "WHERE pm.TRANG_THAI = 1";

			// Tạo đối tượng Statement
			java.sql.Statement statement = connectdata.createStatement();

			try (
					// Thực thi truy vấn SQL
					ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				while (resultSet.next()) {
					PhieuMuon obj = new PhieuMuon();
					DocGia obj_dg = new DocGia();
					NhanVien obj_nv = new NhanVien();
					obj.setMaPhieuMuon(resultSet.getString("MAPMUON"));
					obj.setSoLuong(resultSet.getInt("SOLUONG"));
					obj.setNgayMuon(resultSet.getDate("NGMUON"));
					obj.setNgayTra(resultSet.getDate("NGTRA"));
					obj_dg.setMaDG(resultSet.getString("MADG"));
					obj_dg.setHoTen(resultSet.getString("HOTEN"));
					obj_nv.setMaNV(resultSet.getString("MANV"));
					obj_nv.setHoTen(resultSet.getString("TENNV"));
					obj.setDocGia(obj_dg);
					obj.setMaNhanVien(obj_nv);
					list.add(obj);
				}
			}
			// Đóng connect
			conn.closeConnection(connectdata);

			for (int i = 0; i < list.size(); i++) {
				ArrayList<CTPhieuMuon> obj = new ArrayList<>();
				obj = getListCTPhieuMuon(list.get(i).getMaPhieuMuon());
				list.get(i).setListCTiet(obj);
			}

			//

		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng PHIEU_MUON ");
		}
		return list;
	}

	// Lấy dữ liệu từ Sách
	public ArrayList<SachCT> getListSACH() {
		ArrayList<SachCT> list = new ArrayList<>();
		try {
			// Tạo kết nối
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT  s.MA_SACH AS MASACH, s.TEN_SACH AS TENSACH, s.SO_LUONG AS SOLUONG, s.DON_GIA AS DONGIA, s.MA_NXB AS MANXB, nxb.TEN_NXB AS TENNXB "
					+ "FROM  SACH AS s, NHA_XUAT_BAN AS nxb " + "WHERE s.TRANG_THAI=1 AND s.MA_NXB=nxb.MA_NXB";

			// Tạo đối tượng Statement
			java.sql.Statement statement = connectdata.createStatement();
			// Thực thi truy vấn SQL
			try (ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				while (resultSet.next()) {
					SachCT obj = new SachCT();
					NhaXuatBan objnxb = new NhaXuatBan();
					obj.set_MaSach(resultSet.getString("MASACH"));
					obj.set_TenSach(resultSet.getString("TENSACH"));
					obj.set_SoLuong(resultSet.getInt("SOLUONG"));
					obj.set_DonGia(resultSet.getInt("DONGIA"));
					objnxb.setMaNXB(resultSet.getString("MANXB"));
					objnxb.setTenNXB(resultSet.getString("TENNXB"));
					obj.set_nhaXuatBan(objnxb);
					// Thêm vào danh sách
					list.add(obj);
				}
			}
			// Đóng kết nối
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			// Xử lý ngoại lệ, ví dụ: in ra lỗi
			e.printStackTrace();
		}
		return list;
	}

	// Lấy dữ liệu từ Tác Giả
	public ArrayList<DocGia> getListDocGia() {
		ArrayList<DocGia> List_Docgianew = new ArrayList<DocGia>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();

			String Query = " SELECT MA_DG, HO_TEN " + " FROM DOC_GIA " + " WHERE TRANG_TAI = 1";

			Statement statement = connectdata.createStatement();

			// Xử lý kết quả trả về từ truy vấn
			try (
					// Thực thi truy vấn SQL
					ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				// Tạo một ArrayList rỗng
				while (resultSet.next()) {
					DocGia docgia = new DocGia();
					docgia.setMaDG(resultSet.getString("MA_DG"));
					docgia.setHoTen(resultSet.getString("HO_TEN"));
					List_Docgianew.add(docgia);
				}

			}
			// Đóng connect
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng DOC_GIA ");
		}
		return List_Docgianew;
	}

	// Thêm phiếu nhập mới
	public int ThemMotPhieuMuon(PhieuMuon obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "INSERT INTO phieu_muon SET MA_PMUON=?, MG_MUON=?, MA_NV=?, MA_DG=?, NG_TRA=?, SO_LUONG=?, TRANG_THAI=1";
			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, obj.getMaPhieuMuon());
				preparedStatement.setDate(2, (Date) obj.getNgayMuon());
				preparedStatement.setString(3, obj.getMaNhanVien().getMaNV());
				preparedStatement.setString(4, obj.getDocGia().getMaDG());
				preparedStatement.setDate(5, (Date) obj.getNgayTra());
				preparedStatement.setInt(6, obj.getSoLuong());

				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Thêm thông tin phiếu mượn mới");
				} else {
					System.out.println("Không thể thêm thông tin phiếu mượn mới");
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

	// Thêm chi tiết phiếu nhập
	public void ThemMotChiTietPhieuMuon(ArrayList<CTPhieuMuon> obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "INSERT INTO ct_pmuon (MA_PMUON, MA_SACH, SO_LUONG,TRANG_THAI) VALUES (?, ?, ?, 1)";
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				for (int i = 0; i < obj.size(); i++) {
					preparedStatement.setString(1, obj.get(i).getPhieuMuon());
					preparedStatement.setString(2, obj.get(i).getSach().get_MaSach());
					preparedStatement.setInt(3, obj.get(i).getSoLuong());
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
			}
			// Đóng PreparedStatement
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			e.printStackTrace(); // Xử lý lỗi một cách cẩn thận hơn
			System.out.printf("\n Lỗi Không Thể Thêm Vào CTPhieuMuon");
		}
	}

	// Xóa Chi Tiết Phiếu Nhập
	public void XoaCTietPhieuMuon(ArrayList<CTPhieuMuon> list) throws SQLException {
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE ct_pmuon SET TRANG_THAI=0 WHERE MA_PMUON=?;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (int i = 0; i < list.size(); i++) {
			statement.setString(1, list.get(i).getPhieuMuon());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}
		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);
	}

	// Xóa Phiếu Nhập
	public int XoaPhieuMuon(PhieuMuon obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "UPDATE phieu_muon  SET TRANG_THAI=0  WHERE MA_PMUON=?";
			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, obj.getMaPhieuMuon());
				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("\nĐã xóa phiếu nhập thành công");
				} else {
					System.out.println("\nKhông thể xóa phiếu nhập");
				}
			}
			// Đóng PreparedStatement
			conn.closeConnection(connectdata);
			XoaCTietPhieuMuon(obj.getListCTiet());
			return rowsAffected;
		} catch (SQLException e) {
			System.out.printf("\n Lỗi  ");
		}
		return -1;
	}

	public void delete2(String userID) throws SQLException {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "UPDATE phieu_muon SET TRANG_THAI=0 WHERE MA_PMUON='" + userID + "'";
		cn.executeUpdate(sql);
		deletectpm(userID);
		System.out.println(sql);
	}

	public void deletectpm(String userID) throws SQLException {
		ConnectDataBase cn = new ConnectDataBase();
		String sql = "UPDATE ct_pmuon SET TRANG_THAI=0 WHERE MA_PMUON='" + userID + "'";
		cn.executeUpdate(sql);
		System.out.println(sql);
	}

	public ArrayList<PhieuMuon> ThongKeTheoNgay(Date chosenDate) {
		ArrayList<PhieuMuon> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String query = "SELECT pm.MA_PMUON AS MAPMUON, pm.MG_MUON AS NGMUON, pm.NG_TRA AS NGTRA, pm.MA_DG AS MADG, pm.MA_NV AS MANV, pm.SO_LUONG AS SOLUONG, "
					+ "dg.HO_TEN AS TENDG, nv.HO_TEN AS TENNV "
					+ "FROM PHIEU_MUON AS pm, NHAN_VIEN AS nv, DOC_GIA AS dg "
					+ "WHERE MG_MUON = ? AND pm.TRANG_THAI = 1 AND pm.MA_DG = dg.MA_DG AND pm.MA_NV = nv.MA_NV";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setDate(1, chosenDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					// Xử lý kết quả trả về từ truy vấn
					while (resultSet.next()) {
						PhieuMuon obj = new PhieuMuon();
						NhanVien obj_nv = new NhanVien();
						DocGia obj_dg = new DocGia();
						obj.setMaPhieuMuon(resultSet.getString("MAPMUON"));
						obj.setSoLuong(resultSet.getInt("SOLUONG"));
						obj.setNgayMuon(resultSet.getDate("NGMUON"));
						obj.setNgayTra(resultSet.getDate("NGTRA"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						obj_dg.setMaDG(resultSet.getString("MADG"));
						obj_dg.setHoTen(resultSet.getString("TENDG"));
						obj.setMaNhanVien(obj_nv);
						obj.setDocGia(obj_dg);
						list.add(obj);
					}
				}
			}

			// Lấy danh sách chi tiết phiếu mượn cho từng phiếu mượn trong danh sách
			for (int i = 0; i < list.size(); i++) {
				ArrayList<CTPhieuMuon> obj = new ArrayList<>();
				obj = getListCTPhieuMuon(list.get(i).getMaPhieuMuon());
				list.get(i).setListCTiet(obj);
			}

			// Đóng connect
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			System.out.println("\n Lỗi không thể thống kê theo ngày ");
		}
		return list;
	}

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

	public ArrayList<NhanVien> getListNhanVien() {
		ArrayList<NhanVien> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT  MA_NV, HO_TEN " + "FROM  NHAN_VIEN " + "WHERE TRANG_THAI = 1";

			// Tạo đối tượng Statement
			try (java.sql.Statement statement = connectdata.createStatement();
					ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				while (resultSet.next()) {
					NhanVien obj = new NhanVien();
					obj.setMaNV(resultSet.getString("MA_NV"));
					obj.setHoTen(resultSet.getString("HO_TEN"));
					list.add(obj); // Thêm đối tượng vào danh sách
				}
			}
			// Đóng connect
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng NHA_XUAT_BAN ");
		}
		return list;
	}

	public ArrayList<PhieuMuon> ThongKeTheoKhoangThoiGian(Date startDate, Date endDate) {
		ArrayList<PhieuMuon> list = new ArrayList<>();
		try {
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT pm.MA_PMUON AS MAPMUON, pm.MG_MUON AS NGMUON,pm.	NG_TRA AS NGTRA, pm.MA_DG AS MADG, pm.MA_NV AS MANV, pm.SO_LUONG AS SOLUONG, "
					+ "dg.HO_TEN AS TENDG, nv.HO_TEN AS TENNV "
					+ "FROM PHIEU_MUON AS pm,NHAN_VIEN AS nv , DOC_GIA AS dg "
					+ "WHERE pm.TRANG_THAI = 1 AND pm.MA_NV = nv.MA_NV AND pm.MA_DG = dg.MA_DG AND pm.MG_MUON BETWEEN ? AND ?";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				preparedStatement.setDate(1, startDate);
				preparedStatement.setDate(2, endDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						PhieuMuon obj = new PhieuMuon();
						DocGia obj_dg = new DocGia();
						NhanVien obj_nv = new NhanVien();
						obj.setMaPhieuMuon(resultSet.getString("MAPMUON"));
						obj.setSoLuong(resultSet.getInt("SOLUONG"));
						obj.setNgayMuon(resultSet.getDate("NGMUON"));
						obj.setNgayTra(resultSet.getDate("NGTRA"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						obj_dg.setMaDG(resultSet.getString("MADG"));
						obj_dg.setHoTen(resultSet.getString("TENDG"));
						obj.setMaNhanVien(obj_nv);
						obj.setDocGia(obj_dg);
						list.add(obj);
					}
				}
			}

			for (PhieuMuon phieuMuon : list) {
				ArrayList<CTPhieuMuon> obj = getListCTPhieuMuon(phieuMuon.getMaPhieuMuon());
				phieuMuon.setListCTiet(obj);
			}

			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể thống kê theo khoảng thời gian ");
			e.printStackTrace();
		}
		return list;
	}

	public void Edit_NhieuSach(ArrayList<SachCT> objsach) throws SQLException {
		// Tạo thể hiện
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE sach  SET SO_LUONG=? WHERE MA_SACH=?;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (SachCT obj : objsach) {
			statement.setInt(1, obj.get_SoLuong());
			statement.setString(2, obj.get_MaSach());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}

		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);

		System.out.println("Sửa dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

	}

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

	// UPDATE SÁCH MỚI CHƯA CÓ TRONG THƯ VIỆN

	public void Insert_NhieuSach(ArrayList<SachCT> sach) throws SQLException {
		// Tạo thể hiện
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE sach  SET MA_SACH=? ,TEN_SACH=?, MO_TA=?, NAM_XB=?, MA_TGIA=? ,MA_NXB=? ,DON_GIA=?, SO_LUONG=?, TRANG_TAI=1, MA_LOAI_SACH=? ;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (SachCT a : sach) {
			statement.setString(1, a.get_MaSach());
			statement.setString(2, a.get_TenSach());
			statement.setString(3, a.get_MoTa());
			statement.setString(4, a.get_NamXB());
			statement.setString(5, a.get_tacGia().getMaTG());
			statement.setString(6, a.get_nhaXuatBan().getMaNXB());

			statement.setInt(7, a.get_DonGia());
			statement.setInt(8, a.get_SoLuong());
			statement.setString(9, a.get_LoaiSach().getMaLoai());

			statement.addBatch(); // Thêm câu lệnh vào batch
		}

		// Thực thi tất cả các câu lệnh trong batch
		statement.executeBatch();
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);
		System.out.println("Thêm dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

	}

	public ArrayList<PhieuMuon> ThongKeTheoNam_Thang(int year, int month) {
		ArrayList<PhieuMuon> list = new ArrayList<>();
		try {
			// Kết nối CSDL
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			// Tạo câu truy vấn SQL
			String query = "SELECT pm.MA_PMUON AS MAPMUON, pm.MG_MUON AS NGMUON,pm.	NG_TRA AS NGTRA, pm.MA_DG AS MADG, pm.MA_NV AS MANV, pm.SO_LUONG AS SOLUONG, "
					+ "dg.HO_TEN AS TENDG, nv.HO_TEN AS TENNV " + "FROM PHIEU_MUON AS pm "
					+ "INNER JOIN NHAN_VIEN AS nv ON pm.MA_NV = nv.MA_NV "
					+ "INNER JOIN DOC_GIA AS dg  ON pm.MA_DG = dg.MA_DG "
					+ "WHERE YEAR( pm.MG_MUON) = ? AND MONTH( pm.MG_MUON) = ?";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setInt(1, year);
				preparedStatement.setInt(2, month);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						PhieuMuon obj = new PhieuMuon();
						DocGia obj_dg = new DocGia();
						NhanVien obj_nv = new NhanVien();
						obj.setMaPhieuMuon(resultSet.getString("MAPMUON"));
						obj.setSoLuong(resultSet.getInt("SOLUONG"));
						obj.setNgayMuon(resultSet.getDate("NGMUON"));
						obj.setNgayTra(resultSet.getDate("NGTRA"));
						obj_dg.setMaDG(resultSet.getString("MADG"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						obj.setDocGia(obj_dg);
						obj.setMaNhanVien(obj_nv);
						list.add(obj);
					}
				}
			}

			// Lấy thông tin chi tiết của từng phiếu nhập
			for (PhieuMuon pm : list) {
				ArrayList<CTPhieuMuon> chiTietList = getListCTPhieuMuon(pm.getMaPhieuMuon());
				pm.setListCTiet(chiTietList);
			}

			// Đóng kết nối
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			// In ra thông báo lỗi kèm thông tin cụ thể
			e.printStackTrace();
			System.out.println("\nLỗi không thể thống kê theo tháng và năm");
		}

		return list;
	}

	public ArrayList<CTPhieuMuon> ThongKeTheoNgay1(Date chosenDate) {
		ArrayList<CTPhieuMuon> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String query = "SELECT sach.MA_SACH, sach.TEN_SACH, " + "COUNT(ct_pmuon.MA_PMUON) AS SoLanMuon, "
					+ "SUM(ct_pmuon.SO_LUONG) AS SoLuongMuon, " + "TotalMuon.TotalSoLanMuon AS TongSoLanMuon, "
					+ "phieu_muon.MG_MUON " + "FROM sach " + "JOIN ct_pmuon ON sach.MA_SACH = ct_pmuon.MA_SACH "
					+ "JOIN phieu_muon ON ct_pmuon.MA_PMUON = phieu_muon.MA_PMUON " + "JOIN (SELECT ct_pmuon.MA_SACH, "
					+ "SUM(ct_pmuon.SO_LUONG) AS TotalSoLuongMuon, " + "COUNT(ct_pmuon.MA_PMUON) AS TotalSoLanMuon "
					+ "FROM ct_pmuon " + "GROUP BY ct_pmuon.MA_SACH) AS TotalMuon ON sach.MA_SACH = TotalMuon.MA_SACH "
					+ "WHERE phieu_muon.MG_MUON = ?"
					+ "GROUP BY sach.MA_SACH, sach.TEN_SACH, TotalMuon.TotalSoLanMuon,phieu_muon.MG_MUON";
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setDate(1, chosenDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					// Xử lý kết quả trả về từ truy vấn
					while (resultSet.next()) {
						CTPhieuMuon obj = new CTPhieuMuon();
						SachCT objsach = new SachCT();
						objsach.set_MaSach(resultSet.getString("MA_SACH"));
						objsach.set_TenSach(resultSet.getString("TEN_SACH"));
						obj.setSach(objsach);
						obj.setSoLanMuon(resultSet.getInt("SoLanMuon"));
						obj.setTongSL(resultSet.getInt("SoLuongMuon"));
						list.add(obj);
					}
				}
			}

			// Đóng connect
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			System.out.println("\n Lỗi không thể thống kê theo ngày ");
		}
		return list;
	}

	public ArrayList<CTPhieuMuon> ThongKeTheoKhoangThoiGian1(Date startDate, Date endDate) {
		ArrayList<CTPhieuMuon> list = new ArrayList<>();
		try {
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT sach.MA_SACH, sach.TEN_SACH, " + "COUNT(ct_pmuon.MA_PMUON) AS SoLanMuon, "
					+ "SUM(ct_pmuon.SO_LUONG) AS SoLuongMuon, " + "ANY_VALUE(phieu_muon.MG_MUON) AS MG_MUON, "
					+ "TotalMuon.TotalSoLanMuon AS TongSoLanMuon " + "FROM sach "
					+ "JOIN ct_pmuon ON sach.MA_SACH = ct_pmuon.MA_SACH "
					+ "JOIN phieu_muon ON ct_pmuon.MA_PMUON = phieu_muon.MA_PMUON " + "JOIN (SELECT ct_pmuon.MA_SACH, "
					+ "SUM(ct_pmuon.SO_LUONG) AS TotalSoLuongMuon, " + "COUNT(ct_pmuon.MA_PMUON) AS TotalSoLanMuon "
					+ "FROM ct_pmuon " + "GROUP BY ct_pmuon.MA_SACH) AS TotalMuon ON sach.MA_SACH = TotalMuon.MA_SACH "
					+ "WHERE phieu_muon.MG_MUON BETWEEN ? AND ? "
					+ "GROUP BY sach.MA_SACH, sach.TEN_SACH, TotalMuon.TotalSoLanMuon";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				preparedStatement.setDate(1, startDate);
				preparedStatement.setDate(2, endDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						CTPhieuMuon obj = new CTPhieuMuon();
						SachCT objsach = new SachCT();
						objsach.set_MaSach(resultSet.getString("MA_SACH"));
						objsach.set_TenSach(resultSet.getString("TEN_SACH"));
						obj.setSach(objsach);
						obj.setSoLanMuon(resultSet.getInt("SoLanMuon"));
						obj.setTongSL(resultSet.getInt("SoLuongMuon"));
						list.add(obj);
					}
				}
			}

			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể thống kê theo khoảng thời gian ");
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<CTPhieuMuon> ThongKeTheoNam_Thang1(int year, int month) {
		ArrayList<CTPhieuMuon> list = new ArrayList<>();
		try {
			// Kết nối CSDL
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			// Tạo câu truy vấn SQL

			String query = "SELECT sach.MA_SACH, sach.TEN_SACH, " + "COUNT(ct_pmuon.MA_PMUON) AS SoLanMuon, "
					+ "SUM(ct_pmuon.SO_LUONG) AS SoLuongMuon, " + "ANY_VALUE(phieu_muon.MG_MUON) AS MG_MUON, "
					+ "TotalMuon.TotalSoLanMuon AS TongSoLanMuon " + "FROM sach "
					+ "JOIN ct_pmuon ON sach.MA_SACH = ct_pmuon.MA_SACH "
					+ "JOIN phieu_muon ON ct_pmuon.MA_PMUON = phieu_muon.MA_PMUON " + "JOIN (SELECT ct_pmuon.MA_SACH, "
					+ "SUM(ct_pmuon.SO_LUONG) AS TotalSoLuongMuon, " + "COUNT(ct_pmuon.MA_PMUON) AS TotalSoLanMuon "
					+ "FROM ct_pmuon " + "GROUP BY ct_pmuon.MA_SACH) AS TotalMuon ON sach.MA_SACH = TotalMuon.MA_SACH "
					+ "WHERE YEAR(phieu_muon.MG_MUON) = ? AND MONTH(phieu_muon.MG_MUON) = ?  "
					+ "GROUP BY sach.MA_SACH, sach.TEN_SACH, TotalMuon.TotalSoLanMuon";
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setInt(1, year);
				preparedStatement.setInt(2, month);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						CTPhieuMuon obj = new CTPhieuMuon();
						SachCT objsach = new SachCT();
						objsach.set_MaSach(resultSet.getString("MA_SACH"));
						objsach.set_TenSach(resultSet.getString("TEN_SACH"));
						obj.setSach(objsach);
						obj.setSoLanMuon(resultSet.getInt("SoLanMuon"));
						obj.setTongSL(resultSet.getInt("SoLuongMuon"));
						list.add(obj);
					}
				}
			}

			// Đóng kết nối
			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			// In ra thông báo lỗi kèm thông tin cụ thể
			e.printStackTrace();
			System.out.println("\nLỗi không thể thống kê theo tháng và năm");
		}

		return list;
	}

}
