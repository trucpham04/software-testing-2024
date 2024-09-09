/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import DTO.CTPhieuMuon;
import DTO.CTPhieuTra;
import DTO.DocGia;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.NhanVien;
import DTO.PhieuMuon;
import DTO.PhieuTra;
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
public class PhieuTraDAO {

	/**
	 *
	 * @param maPMUON
	 * @return
	 */

	// Lấy dữ liệu của chi tiết phiếu nhập dựa theo một mã phiếu nhập có sẵn
	@SuppressWarnings("empty-statement")
	public ArrayList<CTPhieuTra> getListCTPhieuTra(String maPTRA) {
		ArrayList<CTPhieuTra> list = new ArrayList<>();
		try {
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT ctpt.MA_PTRA AS MAPTRA,  ctpt.MA_SACH AS MASACH,  ctpt.SO_LUONG AS SOLUONG,  "
					+ "s.TEN_SACH AS TENSACH " + "FROM ct_ptra AS ctpt,  sach AS s "
					+ "WHERE ctpt.MA_SACH=s.MA_SACH AND ctpt.MA_PTRA=? AND ctpt.TRANG_THAI=1";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				preparedStatement.setString(1, maPTRA);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						// Xử lý kết quả trả về từ truy vấn
						CTPhieuTra obj = new CTPhieuTra();
						SachCT objsach = new SachCT();
						obj.setPhieuTra(resultSet.getString("MAPTRA")); // Chỉnh sửa tên cột
						obj.setSoLuong(resultSet.getInt("SOLUONG")); // Chỉnh sửa tên cột
						objsach.set_MaSach(resultSet.getString("MASACH"));
						objsach.set_TenSach(resultSet.getString("TENSACH"));
						obj.setSach(objsach);
						list.add(obj);
					}
				}
			}
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			// Xử lý lỗi, ví dụ in ra thông báo
			System.out.println("Đã xảy ra lỗi khi thực thi truy vấn: " + e.getMessage());
		}
		return list;
	}

	// Lấy dữ liệu từ Phiếu Nhập
	@SuppressWarnings("empty-statement")
	public ArrayList<PhieuTra> getListPhieuTra() {
		ArrayList<PhieuTra> list = new ArrayList<>();
		try {
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();
			String Query = "SELECT pt.MA_PTRA AS MAPTRA, pt.MA_PMUON AS MAPMUON, pt.MA_DG AS MADG, pt.MA_NV AS MANV, pt.NG_TRA AS NGTRA, pt.TINH_TRANG AS TINHTRANG, pt.SO_LUONG AS SOLUONG, pt.TRANG_THAI AS TRANGTHAI, "
					+ "dg.HO_TEN AS HOTEN, nv.HO_TEN AS TENNV, pm.MA_PMUON AS MAPM " + "FROM PHIEU_TRA AS pt "
					+ "JOIN NHAN_VIEN AS nv ON pt.MA_NV = nv.MA_NV " + "JOIN DOC_GIA AS dg ON pt.MA_DG = dg.MA_DG "
					+ "JOIN PHIEU_MUON AS pm ON pt.MA_PMUON = pm.MA_PMUON " + "WHERE pt.TRANG_THAI = 1";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query);
					ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					PhieuTra obj = new PhieuTra();
					PhieuMuon obj_pm = new PhieuMuon();
					DocGia obj_dg = new DocGia();
					NhanVien obj_nv = new NhanVien();
					obj.setPhieuTra(resultSet.getString("MAPTRA"));
					obj_pm.setMaPhieuMuon(resultSet.getString("MAPM"));
					obj.setSoLuong(resultSet.getInt("SOLUONG"));
					obj.setNgayTra(resultSet.getDate("NGTRA"));
					obj.setTinhTrang(resultSet.getString("TINHTRANG"));
					obj_dg.setMaDG(resultSet.getString("MADG"));
					obj_dg.setHoTen(resultSet.getString("HOTEN"));
					obj_nv.setMaNV(resultSet.getString("MANV"));
					obj_nv.setHoTen(resultSet.getString("TENNV"));
					obj.setPhieuMuon(obj_pm);
					obj.setDocGia(obj_dg);
					obj.setNhanVien(obj_nv);
					list.add(obj);
				}
			}

			for (PhieuTra phieuTra : list) {
				ArrayList<CTPhieuTra> obj = getListCTPhieuTra(phieuTra.getPhieuTra());
				phieuTra.setListCTiet(obj);
			}

			conn.closeConnection(connectdata);

		} catch (SQLException e) {
			System.out.println("\n Lỗi không thể lấy dữ liệu từ bảng PHIEU_TRA: " + e.getMessage());
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

	// Thêm phiếu nhập mới
	public int ThemMotPhieuTra(PhieuTra obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "INSERT INTO phieu_tra SET MA_PTRA=?, MA_PMUON=?, MA_DG=?, MA_NV=?, NG_TRA=?, TINH_TRANG=?,SO_LUONG=?,  TRANG_THAI=1";
			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, obj.getPhieuTra());
				preparedStatement.setString(2, obj.getPhieuMuon().getMaPhieuMuon());
				preparedStatement.setString(6, obj.getTinhTrang());
				preparedStatement.setString(4, obj.getNhanVien().getMaNV());
				preparedStatement.setString(3, obj.getDocGia().getMaDG());
				preparedStatement.setDate(5, (Date) obj.getNgayTra());
				preparedStatement.setInt(7, obj.getSoLuong());

				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Thêm thông tin phiếu trả mới");
				} else {
					System.out.println("Không thể thêm thông tin phiếu trả mới");
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
	public void ThemMotChiTietPhieuTra(ArrayList<CTPhieuTra> obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "INSERT INTO ct_ptra (MA_PTRA, MA_SACH, SO_LUONG,TRANG_THAI) VALUES (?, ?, ?,1)";
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				for (int i = 0; i < obj.size(); i++) {
					preparedStatement.setString(1, obj.get(i).getPhieuTra());
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
			System.out.printf("\n Lỗi Không Thể Thêm Vào CTPhieuTra");
		}
	}

	// Xóa Chi Tiết Phiếu Nhập
	public void XoaCTietPhieuTra(ArrayList<CTPhieuTra> list) throws SQLException {
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE ct_ptra SET TRANG_THAI=0 WHERE MA_PTRA=?;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (int i = 0; i < list.size(); i++) {
			statement.setString(1, list.get(i).getPhieuTra());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}
		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);
	}

	// Xóa Phiếu Nhập
	public int XoaPhieuTra(PhieuTra obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "UPDATE phieu_tra  SET TRANG_THAI=0  WHERE MA_PTRA=?";
			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, obj.getPhieuTra());
				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("\nĐã xóa phiếu nhập thành công");
				} else {
					System.out.println("\nKhông thể xóa phiếu nhập");
				}
			}
			// Đóng PreparedStatement
			conn.closeConnection(connectdata);
			XoaCTietPhieuTra(obj.getListCTiet());
			return rowsAffected;
		} catch (SQLException e) {
			System.out.printf("\n Lỗi  ");
		}
		return -1;
	}

	public int XoaPhieuMuon1(String obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "UPDATE phieu_muon  SET TRANG_THAI=0  WHERE MA_PMUON=?";
			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, obj);
				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("\nĐã xóa phiếu nhập thành công");
				} else {
					System.out.println("\nKhông thể xóa phiếu nhập");
				}
			}
			// Đóng PreparedStatement
			conn.closeConnection(connectdata);
			XoaCTietPhieuMuon1(obj);
			return rowsAffected;
		} catch (SQLException e) {
			System.out.printf("\n Lỗi  ");
		}
		return -1;
	}

	public void XoaCTietPhieuMuon1(String objctpm) throws SQLException {
		ArrayList<CTPhieuMuon> list = new ArrayList<CTPhieuMuon>();
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE ct_pmuon SET TRANG_THAI=0 WHERE MA_PMUON=?;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (int i = 0; i < list.size(); i++) {
			statement.setString(1, objctpm);
			statement.addBatch(); // Thêm câu lệnh vào batch
		}
		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);
	}

	public ArrayList<PhieuTra> ThongKeTheoNgay(Date chosenDate) {
		ArrayList<PhieuTra> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String query = "SELECT pt.MA_PTRA AS MAPTRA, pt.MA_PMUON AS MAPMUON, pt.MA_NV AS MANV, pt.MA_DG AS MADG, "
					+ "pt.NG_TRA AS NGTRA, pt.TINH_TRANG AS TINHTRANG, pt.SO_LUONG AS SOLUONG, "
					+ "dg.HO_TEN AS TENDG, nv.HO_TEN AS TENNV, pm.MA_PMUON AS MAPM "
					+ "FROM PHIEU_TRA AS pt "
					+ "JOIN NHAN_VIEN AS nv ON pt.MA_NV = nv.MA_NV "
					+ "JOIN DOC_GIA AS dg ON pt.MA_DG = dg.MA_DG "
					+ "JOIN PHIEU_MUON AS pm ON pt.MA_PMUON = pm.MA_PMUON "
					+ "WHERE pt.TRANG_THAI = 1 "
					+ "AND pt.NG_TRA = ?";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setDate(1, chosenDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					// Xử lý kết quả trả về từ truy vấn
					while (resultSet.next()) {
						PhieuTra obj = new PhieuTra();
						DocGia obj_dg = new DocGia();
						NhanVien obj_nv = new NhanVien();
						PhieuMuon objPM = new PhieuMuon();
						obj.setPhieuTra(resultSet.getString("MAPTRA"));
						obj.setSoLuong(resultSet.getInt("SOLUONG"));
						obj.setNgayTra(resultSet.getDate("NGTRA"));
						obj.setTinhTrang(resultSet.getString("TINHTRANG"));
						obj_dg.setMaDG(resultSet.getString("MADG"));
						obj_dg.setHoTen(resultSet.getString("TENDG"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						objPM.setMaPhieuMuon(resultSet.getString("MAPM"));
						obj.setPhieuMuon(objPM);
						obj.setDocGia(obj_dg);
						obj.setNhanVien(obj_nv);
						list.add(obj);
					}
				}
			}

			// Lấy danh sách chi tiết phiếu mượn cho từng phiếu mượn trong danh sách
			for (int i = 0; i < list.size(); i++) {
				ArrayList<CTPhieuTra> obj = new ArrayList<>();
				obj = getListCTPhieuTra(list.get(i).getPhieuTra());
				list.get(i).setListCTiet(obj);
			}

			// Đóng connect
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			System.out.println("\n Lỗi không thể thống kê theo ngày ");
		}
		return list;
	}

	public ArrayList<PhieuTra> ThongKeTheoNam_Thang(int year, int month) {
		ArrayList<PhieuTra> list = new ArrayList<>();
		try {
			// Kết nối CSDL
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			// Tạo câu truy vấn SQL
			String query = "SELECT pt.MA_PTRA AS MAPTRA, pt.MA_PMUON AS MAPMUON, pt.MA_NV AS MANV, pt.MA_DG AS MADG, "
					+ "pt.NG_TRA AS NGTRA, pt.TINH_TRANG AS TINHTRANG, pt.SO_LUONG AS SOLUONG, "
					+ "dg.HO_TEN AS TENDG, nv.HO_TEN AS TENNV, pm.MA_PMUON AS MAPM "
					+ "FROM PHIEU_TRA AS pt "
					+ "JOIN NHAN_VIEN AS nv ON pt.MA_NV = nv.MA_NV "
					+ "JOIN DOC_GIA AS dg ON pt.MA_DG = dg.MA_DG "
					+ "JOIN PHIEU_MUON AS pm ON pt.MA_PMUON = pm.MA_PMUON "
					+ "WHERE pt.TRANG_THAI = 1 "
					+ "AND YEAR(pt.NG_TRA) = ? AND MONTH(pt.NG_TRA) = ?";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setInt(1, year);
				preparedStatement.setInt(2, month);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						PhieuTra obj = new PhieuTra();
						DocGia obj_dg = new DocGia();
						NhanVien obj_nv = new NhanVien();
						PhieuMuon objPM = new PhieuMuon();
						obj.setPhieuTra(resultSet.getString("MAPTRA"));
						obj.setSoLuong(resultSet.getInt("SOLUONG"));
						obj.setNgayTra(resultSet.getDate("NGTRA"));
						obj.setTinhTrang(resultSet.getString("TINHTRANG"));
						obj_dg.setMaDG(resultSet.getString("MADG"));
						obj_dg.setHoTen(resultSet.getString("TENDG"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						objPM.setMaPhieuMuon(resultSet.getString("MAPM"));
						obj.setPhieuMuon(objPM);
						obj.setDocGia(obj_dg);
						obj.setNhanVien(obj_nv);
						list.add(obj);
					}
				}
			}

			// Lấy thông tin chi tiết của từng phiếu nhập
			for (PhieuTra pm : list) {
				ArrayList<CTPhieuTra> chiTietList = getListCTPhieuTra(pm.getPhieuTra());
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

	public ArrayList<PhieuTra> ThongKeTheoKhoangThoiGian(Date startDate, Date endDate) {
		ArrayList<PhieuTra> list = new ArrayList<>();
		try {
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT pt.MA_PTRA AS MAPTRA, pt.MA_PMUON AS MAPMUON, pt.MA_NV AS MANV, pt.MA_DG AS MADG, "
					+ "pt.NG_TRA AS NGTRA, pt.TINH_TRANG AS TINHTRANG, pt.SO_LUONG AS SOLUONG, "
					+ "dg.HO_TEN AS TENDG, nv.HO_TEN AS TENNV, pm.MA_PMUON AS MAPM "
					+ "FROM PHIEU_TRA AS pt "
					+ "JOIN NHAN_VIEN AS nv ON pt.MA_NV = nv.MA_NV "
					+ "JOIN DOC_GIA AS dg ON pt.MA_DG = dg.MA_DG "
					+ "JOIN PHIEU_MUON AS pm ON pt.MA_PMUON = pm.MA_PMUON "
					+ "WHERE pt.TRANG_THAI = 1 "
					+ "AND pt.NG_TRA BETWEEN ? AND ?";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				preparedStatement.setDate(1, startDate);
				preparedStatement.setDate(2, endDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						PhieuTra obj = new PhieuTra();
						DocGia obj_dg = new DocGia();
						NhanVien obj_nv = new NhanVien();
						PhieuMuon objPM = new PhieuMuon();
						obj.setPhieuTra(resultSet.getString("MAPTRA"));
						obj.setSoLuong(resultSet.getInt("SOLUONG"));
						obj.setNgayTra(resultSet.getDate("NGTRA"));
						obj.setTinhTrang(resultSet.getString("TINHTRANG"));
						obj_dg.setMaDG(resultSet.getString("MADG"));
						obj_dg.setHoTen(resultSet.getString("TENDG"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						objPM.setMaPhieuMuon(resultSet.getString("MAPM"));
						obj.setPhieuMuon(objPM);
						obj.setDocGia(obj_dg);
						obj.setNhanVien(obj_nv);
						list.add(obj);
					}
				}
			}

			for (PhieuTra phieutra : list) {
				ArrayList<CTPhieuTra> obj = getListCTPhieuTra(phieutra.getPhieuTra());
				phieutra.setListCTiet(obj);
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
		String sqlquery = "UPDATE sach  SET MA_SACH=? ,TEN_SACH=?, MO_TA=?, NAM_XB=?, MA_TGIA=? ,MA_NXB=? ,DON_GIA=?, TINH_TRANG=?, TRANG_TAI=1, MA_LOAI_SACH=? ;";
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

	// Lấy dữ liệu từ Phiếu Nhập
	@SuppressWarnings("empty-statement")
	public ArrayList<PhieuMuon> getListPhieuMuon() {
		ArrayList<PhieuMuon> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT pm.MA_PMUON AS MAPMUON, pm.MG_MUON AS NGMUON, pm.MA_NV AS MANV, pm.MA_DG AS MADG, pm.NG_TRA AS NGTRA, pm.SO_LUONG AS SOLUONG, pm.TRANG_THAI AS TRANGTHAI, "
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

	public ArrayList<CTPhieuMuon> getListCTPhieuMuon() {
		ArrayList<CTPhieuMuon> list = new ArrayList<>();
		String query = "SELECT ctpm.MA_PMUON AS MAPMUON, ctpm.MA_SACH AS MASACH, ctpm.SO_LUONG AS SOLUONG, "
				+ "s.TEN_SACH AS TENSACH "
				+ "FROM CT_PMUON ctpm "
				+ "JOIN SACH s ON ctpm.MA_SACH = s.MA_SACH "
				+ "JOIN PHIEU_MUON pm ON ctpm.MA_PMUON = pm.MA_PMUON "
				+ "WHERE ctpm.TRANG_THAI = 1";

		try (java.sql.Connection connectdata = new DataConnection().getConnection();
				PreparedStatement preparedStatement = connectdata.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				CTPhieuMuon obj = new CTPhieuMuon();
				SachCT objsach = new SachCT();
				obj.setPhieuMuon(resultSet.getString("MAPMUON"));
				objsach.set_MaSach(resultSet.getString("MASACH"));
				objsach.set_TenSach(resultSet.getString("TENSACH"));
				obj.setSoLuong(resultSet.getInt("SOLUONG"));
				obj.setSach(objsach);
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
