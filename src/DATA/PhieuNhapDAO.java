/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import DTO.CTPhieuMuon;
import DTO.CTPhieuNhap;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.NhanVien;
import DTO.PhieuNhap;
import DTO.SachCT;
import DTO.TacGia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class PhieuNhapDAO {

	/**
	 *
	 * @param maPNHAP
	 * @return
	 */

	// Lấy dữ liệu của chi tiết phiếu nhập dựa theo một mã phiếu nhập có sẵn
	@SuppressWarnings("empty-statement")
	public ArrayList<CTPhieuNhap> getListCTPhieuNhap(String maPNHAP) {
		ArrayList<CTPhieuNhap> list = new ArrayList<>();
		try {

			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT ctpn.MA_PNHAP AS MAPNHAP,  ctpn.MA_SACH AS MASACH,  ctpn.SO_LUONG AS SOLG_NHAPVAO,  ctpn.THANH_TIEN AS THANHTIEN,  "
					+ "s.TEN_SACH AS TENSACH " + "FROM ct_pnhap AS ctpn,  sach AS s "
					+ "WHERE ctpn.MA_SACH=s.MA_SACH AND ctpn.MA_PNHAP=? AND ctpn.TRANG_THAI=1";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				preparedStatement.setString(1, maPNHAP);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						// Xử lý kết quả trả về từ truy vấn
						CTPhieuNhap obj = new CTPhieuNhap();
						SachCT objsach = new SachCT();
						obj.set_MaPhieuNhap(resultSet.getString("MAPNHAP"));
						obj.set_soluong(resultSet.getInt("SOLG_NHAPVAO"));
						obj.set_thanhtien(resultSet.getInt("THANHTIEN"));
						objsach.set_MaSach(resultSet.getString("MASACH"));
						objsach.set_TenSach(resultSet.getString("TENSACH"));
						obj.set_Sach(objsach);
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

	// Lấy dữ liệu từ Phiếu Nhập
	@SuppressWarnings("empty-statement")
	public ArrayList<PhieuNhap> getListPhieuNhap() {
		ArrayList<PhieuNhap> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT pn.MA_PNHAP AS MAPNHAP, pn.NG_NHAP AS NGNHAP, pn.MA_NXB AS MANXB, pn.MA_NV AS MANV, pn.TONG_TIEN AS TONGTIEN, "
					+ "nxb.TEN_NXB AS TENNXB, nv.HO_TEN AS TENNV "
					+ "FROM PHIEU_NHAP AS pn JOIN NHAN_VIEN AS nv JOIN NHA_XUAT_BAN AS nxb "
					+ "WHERE pn.TRANG_THAI=1 AND pn.MA_NXB=nxb.MA_NXB AND pn.MA_NV=nv.MA_NV";

			// Tạo đối tượng Statement
			java.sql.Statement statement = connectdata.createStatement();

			try (
					// Thực thi truy vấn SQL
					ResultSet resultSet = statement.executeQuery(Query)) {
				// Xử lý kết quả trả về từ truy vấn
				while (resultSet.next()) {
					PhieuNhap obj = new PhieuNhap();
					NhaXuatBan obj_nxb = new NhaXuatBan();
					NhanVien obj_nv = new NhanVien();
					obj.set_maPNhap(resultSet.getString("MAPNHAP"));
					obj.setTongTien(resultSet.getFloat("TONGTIEN"));
					obj.setNgayNhap(resultSet.getDate("NGNHAP"));
					obj_nxb.setMaNXB(resultSet.getString("MANXB"));
					obj_nxb.setTenNXB(resultSet.getString("TENNXB"));
					obj_nv.setMaNV(resultSet.getString("MANV"));
					obj_nv.setHoTen(resultSet.getString("TENNV"));
					obj.set_NhaXuatBan(obj_nxb);
					obj.set_NhanVien(obj_nv);
					list.add(obj);
				}
			}
			// Đóng connect
			conn.closeConnection(connectdata);

			for (int i = 0; i < list.size(); i++) {
				ArrayList<CTPhieuNhap> obj = new ArrayList<>();
				obj = getListCTPhieuNhap(list.get(i).get_maPNhap());
				list.get(i).setListCTiet(obj);
			}

			//

		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng PHIEU_NHAP ");
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

	// Lấy dữ liệu từ Nhà Xuất Bản
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

	// Thêm phiếu nhập mới
	public int ThemMotPhieuNhap(PhieuNhap obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "INSERT INTO phieu_nhap  SET MA_PNHAP=?, NG_NHAP=?, MA_NXB=?, MA_NV=?, TONG_TIEN=?, TRANG_THAI=1";
			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, obj.get_maPNhap());
				preparedStatement.setDate(2, (Date) obj.getNgayNhap());
				preparedStatement.setString(3, obj.get_NhaXuatBan().getMaNXB());
				preparedStatement.setString(4, obj.get_NhanVien().getMaNV());
				preparedStatement.setFloat(5, obj.getTongTien());

				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Thêm thông tin phiếu nhập mới");
				} else {
					System.out.println("Không thể thêm thông tin phiếu nhập mới");
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
	public void ThemMotChiTietPhieuNhap(ArrayList<CTPhieuNhap> obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "INSERT INTO ct_pnhap (MA_PNHAP, MA_SACH, SO_LUONG, THANH_TIEN, TRANG_THAI) VALUES (?, ?, ?, ?, 1)";
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				for (int i = 0; i < obj.size(); i++) {
					preparedStatement.setString(1, obj.get(i).get_MaPhieuNhap());
					preparedStatement.setString(2, obj.get(i).get_Sach().get_MaSach());
					preparedStatement.setInt(3, obj.get(i).get_soluong());
					preparedStatement.setInt(4, obj.get(i).get_thanhtien());
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
			}
			// Đóng PreparedStatement
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			e.printStackTrace(); // Xử lý lỗi một cách cẩn thận hơn
			System.out.printf("\n Lỗi Không Thể Thêm Vào CTPhieuNhap");
		}
	}

	// Xóa Chi Tiết Phiếu Nhập
	public void XoaCTietPhieuNhap(ArrayList<CTPhieuNhap> list) throws SQLException {
		DataConnection conn = new DataConnection();
		Connection connectdata = conn.getConnection();
		String sqlquery = "UPDATE ct_pnhap SET TRANG_THAI=0 WHERE MA_PNHAP=?;";
		PreparedStatement statement = connectdata.prepareStatement(sqlquery);
		for (int i = 0; i < list.size(); i++) {
			statement.setString(1, list.get(i).get_MaPhieuNhap());
			statement.addBatch(); // Thêm câu lệnh vào batch
		}
		statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
		// Đóng PreparedStatement
		conn.closeConnection(connectdata);
	}

	// Xóa Phiếu Nhập
	public int XoaPhieuNhap(PhieuNhap obj) {
		try {
			DataConnection conn = new DataConnection();
			Connection connectdata = conn.getConnection();
			String sqlquery = "UPDATE phieu_nhap  SET TRANG_THAI=0  WHERE MA_PNHAP=?";
			int rowsAffected;
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
				preparedStatement.setString(1, obj.get_maPNhap());
				rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("\nĐã xóa phiếu nhập thành công");
				} else {
					System.out.println("\nKhông thể xóa phiếu nhập");
				}
			}
			// Đóng PreparedStatement
			conn.closeConnection(connectdata);
			XoaCTietPhieuNhap(obj.getListCTiet());
			return rowsAffected;
		} catch (SQLException e) {
			System.out.printf("\n Lỗi  ");
		}
		return -1;
	}

	public ArrayList<PhieuNhap> ThongKeTheoNgay(Date chosenDate) {
		ArrayList<PhieuNhap> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String query = "SELECT pn.MA_PNHAP AS MAPNHAP, pn.NG_NHAP AS NGNHAP, pn.MA_NXB AS MANXB, pn.MA_NV AS MANV, pn.TONG_TIEN AS TONGTIEN, "
					+ "nxb.TEN_NXB AS TENNXB, nv.HO_TEN AS TENNV "
					+ "FROM PHIEU_NHAP AS pn,NHAN_VIEN AS nv , NHA_XUAT_BAN AS nxb "
					+ "WHERE NG_NHAP = ? AND pn.TRANG_THAI=1 AND pn.MA_NXB=nxb.MA_NXB AND pn.MA_NV=nv.MA_NV";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setDate(1, chosenDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					// Xử lý kết quả trả về từ truy vấn
					while (resultSet.next()) {
						PhieuNhap obj = new PhieuNhap();
						NhaXuatBan obj_nxb = new NhaXuatBan();
						NhanVien obj_nv = new NhanVien();
						obj.set_maPNhap(resultSet.getString("MAPNHAP"));
						obj.setTongTien(resultSet.getFloat("TONGTIEN"));
						obj.setNgayNhap(resultSet.getDate("NGNHAP"));
						obj_nxb.setMaNXB(resultSet.getString("MANXB"));
						obj_nxb.setTenNXB(resultSet.getString("TENNXB"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						obj.set_NhaXuatBan(obj_nxb);
						obj.set_NhanVien(obj_nv);
						list.add(obj);
					}
				}
			}

			for (int i = 0; i < list.size(); i++) {
				ArrayList<CTPhieuNhap> obj = new ArrayList<>();
				obj = getListCTPhieuNhap(list.get(i).get_maPNhap());
				list.get(i).setListCTiet(obj);
			}

			// Đóng connect
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			System.out.println("\n Lỗi không thể thống kê theo ngày ");
		}
		return list;
	}

	public ArrayList<PhieuNhap> ThongKeTheoKhoangThoiGian(Date startDate, Date endDate) {
		ArrayList<PhieuNhap> list = new ArrayList<>();
		try {
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT pn.MA_PNHAP AS MAPNHAP, pn.NG_NHAP AS NGNHAP, pn.MA_NXB AS MANXB, pn.MA_NV AS MANV, pn.TONG_TIEN AS TONGTIEN, "
					+ "nxb.TEN_NXB AS TENNXB, nv.HO_TEN AS TENNV "
					+ "FROM PHIEU_NHAP AS pn , NHAN_VIEN AS nv , NHA_XUAT_BAN AS nxb "
					+ "WHERE pn.TRANG_THAI=1 AND pn.MA_NXB=nxb.MA_NXB AND pn.MA_NV=nv.MA_NV AND pn.NG_NHAP BETWEEN ? AND ?";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				preparedStatement.setDate(1, startDate);
				preparedStatement.setDate(2, endDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						PhieuNhap obj = new PhieuNhap();
						NhaXuatBan obj_nxb = new NhaXuatBan();
						NhanVien obj_nv = new NhanVien();
						obj.set_maPNhap(resultSet.getString("MAPNHAP"));
						obj.setTongTien(resultSet.getFloat("TONGTIEN"));
						obj.setNgayNhap(resultSet.getDate("NGNHAP"));
						obj_nxb.setMaNXB(resultSet.getString("MANXB"));
						obj_nxb.setTenNXB(resultSet.getString("TENNXB"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						obj.set_NhaXuatBan(obj_nxb);
						obj.set_NhanVien(obj_nv);
						list.add(obj);
					}
				}
			}

			for (PhieuNhap phieuNhap : list) {
				ArrayList<CTPhieuNhap> obj = getListCTPhieuNhap(phieuNhap.get_maPNhap());
				phieuNhap.setListCTiet(obj);
			}

			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			System.out.printf("\n Lỗi không thể thống kê theo khoảng thời gian ");
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<PhieuNhap> ThongKeTheoNam_Thang(int year, int month) {
		ArrayList<PhieuNhap> list = new ArrayList<>();
		try {
			// Kết nối CSDL
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			// Tạo câu truy vấn SQL
			String query = "SELECT pn.MA_PNHAP AS MAPNHAP, pn.NG_NHAP AS NGNHAP, pn.MA_NXB AS MANXB, pn.MA_NV AS MANV, pn.TONG_TIEN AS TONGTIEN, "
					+ "nxb.TEN_NXB AS TENNXB, nv.HO_TEN AS TENNV " + "FROM PHIEU_NHAP AS pn "
					+ "INNER JOIN NHAN_VIEN AS nv ON pn.MA_NV = nv.MA_NV "
					+ "INNER JOIN NHA_XUAT_BAN AS nxb ON pn.MA_NXB = nxb.MA_NXB "
					+ "WHERE YEAR(pn.NG_NHAP) = ? AND MONTH(pn.NG_NHAP) = ?";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setInt(1, year);
				preparedStatement.setInt(2, month);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						PhieuNhap obj = new PhieuNhap();
						NhaXuatBan obj_nxb = new NhaXuatBan();
						NhanVien obj_nv = new NhanVien();
						obj.set_maPNhap(resultSet.getString("MAPNHAP"));
						obj.setTongTien(resultSet.getFloat("TONGTIEN"));
						obj.setNgayNhap(resultSet.getDate("NGNHAP"));
						obj_nxb.setMaNXB(resultSet.getString("MANXB"));
						obj_nxb.setTenNXB(resultSet.getString("TENNXB"));
						obj_nv.setMaNV(resultSet.getString("MANV"));
						obj_nv.setHoTen(resultSet.getString("TENNV"));
						obj.set_NhaXuatBan(obj_nxb);
						obj.set_NhanVien(obj_nv);
						list.add(obj);
					}
				}
			}

			// Lấy thông tin chi tiết của từng phiếu nhập
			for (PhieuNhap phieuNhap : list) {
				ArrayList<CTPhieuNhap> chiTietList = getListCTPhieuNhap(phieuNhap.get_maPNhap());
				phieuNhap.setListCTiet(chiTietList);
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

	// UPDATE ARRAYLIST ĐÃ CÓ TRONG THƯ VIỆN

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

	public ArrayList<CTPhieuNhap> getListCTPhieuNhap1() {
		ArrayList<CTPhieuNhap> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String query = "SELECT sach.MA_SACH, sach.TEN_SACH, " + "SUM(ct_pnhap.THANH_TIEN) AS TongTien, "
					+ "SUM(ct_pnhap.SO_LUONG) AS SoLuongnhap, " + "Totalnhap.TotalSoLannhap AS TongSoLannhap "
					+ "FROM sach " + "JOIN ct_pnhap ON sach.MA_SACH = ct_pnhap.MA_SACH "
					+ "JOIN phieu_nhap ON ct_pnhap.MA_PNHAP = phieu_nhap.MA_PNHAP " + "JOIN (SELECT ct_pnhap.MA_SACH, "
					+ "SUM(ct_pnhap.SO_LUONG) AS TotalSoLuongnhap, " + "COUNT(ct_pnhap.MA_PNHAP) AS TotalSoLannhap "
					+ "FROM ct_pnhap " + "GROUP BY ct_pnhap.MA_SACH) AS Totalnhap ON sach.MA_SACH = Totalnhap.MA_SACH "
					+ "GROUP BY sach.MA_SACH, sach.TEN_SACH, Totalnhap.TotalSoLannhap";

			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query);
					ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					// Xử lý kết quả trả về từ truy vấn
					CTPhieuNhap obj = new CTPhieuNhap();
					SachCT objsach = new SachCT();
					objsach.set_MaSach(resultSet.getString("MA_SACH"));
					objsach.set_TenSach(resultSet.getString("TEN_SACH"));
					obj.set_Sach(objsach);
					obj.setTongTien(resultSet.getInt("TongTien"));
					obj.setTongSL(resultSet.getInt("SoLuongnhap")); // Corrected column name
					list.add(obj);
				}
			}

			// Đóng connect
			conn.closeConnection(connectdata);
		} catch (SQLException e) {
			e.printStackTrace(); // Use e.printStackTrace() to log the exception
		}
		return list;
	}

	public ArrayList<CTPhieuNhap> ThongKeTheoNgay1(Date chosenDate) {
		ArrayList<CTPhieuNhap> list = new ArrayList<>();
		try {
			// Tạo thể hiện
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String query = "SELECT sach.MA_SACH, sach.TEN_SACH, " + "SUM(ct_pnhap.THANH_TIEN) AS TongTien, "
					+ "SUM(ct_pnhap.SO_LUONG) AS SoLuongnhap, " + "Totalnhap.TotalSoLannhap AS TongSoLannhap "
					+ "FROM sach " + "JOIN ct_pnhap ON sach.MA_SACH = ct_pnhap.MA_SACH "
					+ "JOIN phieu_nhap ON ct_pnhap.MA_PNHAP = phieu_nhap.MA_PNHAP " + "JOIN (SELECT ct_pnhap.MA_SACH, "
					+ "SUM(ct_pnhap.SO_LUONG) AS TotalSoLuongnhap, " + "COUNT(ct_pnhap.MA_PNHAP) AS TotalSoLannhap "
					+ "FROM ct_pnhap " + "GROUP BY ct_pnhap.MA_SACH) AS Totalnhap ON sach.MA_SACH = Totalnhap.MA_SACH "
					+ "WHERE phieu_nhap.NG_NHAP = ?" + "GROUP BY sach.MA_SACH, sach.TEN_SACH, Totalnhap.TotalSoLannhap";
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setDate(1, chosenDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					// Xử lý kết quả trả về từ truy vấn
					while (resultSet.next()) {
						CTPhieuNhap obj = new CTPhieuNhap();
						SachCT objsach = new SachCT();
						objsach.set_MaSach(resultSet.getString("MA_SACH"));
						objsach.set_TenSach(resultSet.getString("TEN_SACH"));
						obj.set_Sach(objsach);
						obj.setTongTien(resultSet.getInt("TongTien"));
						obj.setTongSL(resultSet.getInt("SoLuongnhap")); // Corrected column name
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

	public ArrayList<CTPhieuNhap> ThongKeTheoKhoangThoiGian1(Date startDate, Date endDate) {
		ArrayList<CTPhieuNhap> list = new ArrayList<>();
		try {
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			String Query = "SELECT sach.MA_SACH, sach.TEN_SACH, " + "SUM(ct_pnhap.THANH_TIEN) AS TongTien, "
					+ "SUM(ct_pnhap.SO_LUONG) AS SoLuongnhap, " + "Totalnhap.TotalSoLannhap AS TongSoLannhap "
					+ "FROM sach " + "JOIN ct_pnhap ON sach.MA_SACH = ct_pnhap.MA_SACH "
					+ "JOIN phieu_nhap ON ct_pnhap.MA_PNHAP = phieu_nhap.MA_PNHAP " + "JOIN (SELECT ct_pnhap.MA_SACH, "
					+ "SUM(ct_pnhap.SO_LUONG) AS TotalSoLuongnhap, " + "COUNT(ct_pnhap.MA_PNHAP) AS TotalSoLannhap "
					+ "FROM ct_pnhap "

					+ "GROUP BY ct_pnhap.MA_SACH) AS Totalnhap ON sach.MA_SACH = Totalnhap.MA_SACH "
					+ "WHERE phieu_nhap.NG_NHAP BETWEEN ? AND ? "
					+ "GROUP BY sach.MA_SACH, sach.TEN_SACH, Totalnhap.TotalSoLannhap";
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(Query)) {
				preparedStatement.setDate(1, startDate);
				preparedStatement.setDate(2, endDate);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						CTPhieuNhap obj = new CTPhieuNhap();
						SachCT objsach = new SachCT();
						objsach.set_MaSach(resultSet.getString("MA_SACH"));
						objsach.set_TenSach(resultSet.getString("TEN_SACH"));
						obj.set_Sach(objsach);
						obj.setTongTien(resultSet.getInt("TongTien"));
						obj.setTongSL(resultSet.getInt("SoLuongnhap")); // Corrected column name
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

	public ArrayList<CTPhieuNhap> ThongKeTheoNam_Thang1(int year, int month) {
		ArrayList<CTPhieuNhap> list = new ArrayList<>();
		try {
			// Kết nối CSDL
			DataConnection conn = new DataConnection();
			java.sql.Connection connectdata = conn.getConnection();

			// Tạo câu truy vấn SQL

			String query = "SELECT sach.MA_SACH, sach.TEN_SACH, " + "SUM(ct_pnhap.THANH_TIEN) AS TongTien, "
					+ "SUM(ct_pnhap.SO_LUONG) AS SoLuongnhap, " + "Totalnhap.TotalSoLannhap AS TongSoLannhap "
					+ "FROM sach " + "JOIN ct_pnhap ON sach.MA_SACH = ct_pnhap.MA_SACH "
					+ "JOIN phieu_nhap ON ct_pnhap.MA_PNHAP = phieu_nhap.MA_PNHAP " + "JOIN (SELECT ct_pnhap.MA_SACH, "
					+ "SUM(ct_pnhap.SO_LUONG) AS TotalSoLuongnhap, " + "COUNT(ct_pnhap.MA_PNHAP) AS TotalSoLannhap "
					+ "FROM ct_pnhap " + "GROUP BY ct_pnhap.MA_SACH) AS Totalnhap ON sach.MA_SACH = Totalnhap.MA_SACH "
					+ "WHERE YEAR(phieu_nhap.NG_NHAP) = ? AND MONTH(phieu_nhap.NG_NHAP) = ?  "
					+ "GROUP BY sach.MA_SACH, sach.TEN_SACH, Totalnhap.TotalSoLannhap";
			try (PreparedStatement preparedStatement = connectdata.prepareStatement(query)) {
				preparedStatement.setInt(1, year);
				preparedStatement.setInt(2, month);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						CTPhieuNhap obj = new CTPhieuNhap();
						SachCT objsach = new SachCT();
						objsach.set_MaSach(resultSet.getString("MA_SACH"));
						objsach.set_TenSach(resultSet.getString("TEN_SACH"));
						obj.set_Sach(objsach);
						obj.setTongTien(resultSet.getInt("TongTien"));
						obj.setTongSL(resultSet.getInt("SoLuongnhap")); // Corrected column name
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
