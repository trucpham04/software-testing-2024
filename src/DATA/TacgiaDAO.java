/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import java.util.ArrayList;
import DTO.TacGia;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class TacgiaDAO {

    // Lấy tất cả tác giả từ Database xuống
    public ArrayList<TacGia> getTacgia_Data() {
        ArrayList<TacGia> List_Tacgianew = new ArrayList<>();
        try {
            // Tạo thể hiện
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();

            String Query = "SELECT * FROM TAC_GIA WHERE TRANG_THAI=1";
            // Tạo đối tượng Statement
            Statement statement = connectdata.createStatement();

            try (
                    // Thực thi truy vấn SQL
                    ResultSet resultSet = statement.executeQuery(Query)) {
                // Xử lý kết quả trả về từ truy vấn
                while (resultSet.next()) {
                    TacGia tacgia = new TacGia();
                    tacgia.setMaTG(resultSet.getString("MA_TGIA"));
                    tacgia.setTenTG(resultSet.getString("TEN_TGIA"));
                    tacgia.setGioiTinh(resultSet.getString("GIOI_TINH"));
                    tacgia.setNamSinh(resultSet.getString("NAM_SINH"));
                    tacgia.setTrangThai(resultSet.getInt("TRANG_THAI"));
                    List_Tacgianew.add(tacgia);
                }
            }
            // Đóng connect
            conn.closeConnection(connectdata);

        } catch (SQLException e) {
            System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng TAC_GIA ");
        }
        return List_Tacgianew;

    }

    // Update một tác giả mới lên cở sở dữ liệu
    public int insertTacgia_Data(TacGia newtacgia) {
        try {
            // Tạo thể hiện
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "INSERT INTO `quanlithuvien`.`tac_gia` (`MA_TGIA`, `TEN_TGIA`, `GIOI_TINH`, `NAM_SINH`, `TRANG_THAI`) VALUES (?,?, ?, ?, ?);";
            int rowsAffected;
            // Thiết lập giá trị cho các tham số
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                // Thiết lập giá trị cho các tham số
                preparedStatement.setString(1, newtacgia.getMaTG());
                preparedStatement.setString(2, newtacgia.getTenTG());
                preparedStatement.setString(3, newtacgia.getGioiTinh());
                preparedStatement.setString(4, newtacgia.getNamSinh());
                preparedStatement.setInt(5, newtacgia.getTrangThai());
                // Thực thi truy vấn INSERT
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã thêm tác giả thành công");
                } else {
                    System.out.println("Không thể thêm thông tin tác giả mới!");
                }
            }
            // Đóng PreparedStatement
            conn.closeConnection(connectdata);
            return rowsAffected;

        } catch (SQLException e) {
            System.out.printf("\n Lỗi");
        }
        return -1;
    }

    // Sủa thông tin của một tác giả (cho sửa tên ,giới tính, năm sinh) update lên
    // databse

    public int EditTacgia_Data(TacGia objedit) {
        try {
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "UPDATE tac_gia SET TEN_TGIA=?, GIOI_TINH=?, NAM_SINH=? WHERE MA_TGIA=?";
            int rowsAffected;
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                // Thiết lập các giá trị mới từ objedit
                preparedStatement.setString(1, objedit.getTenTG());
                preparedStatement.setString(2, objedit.getGioiTinh());
                preparedStatement.setString(3, objedit.getNamSinh());
                preparedStatement.setString(4, objedit.getMaTG());

                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã thay đổi thông tin tác giả thành công");
                } else {
                    System.out.println("Không thể thay đổi thông tin tác giả");
                }
            } // Đóng PreparedStatement tự động ở đây

            // Đóng kết nối
            conn.closeConnection(connectdata);
            return rowsAffected;
        } catch (SQLException e) {
            System.out.println("\n Lỗi: " + e.getMessage());
        }
        return -1;
    }

    // Xóa thông tin tác giả (set trạng thái về 0)
    public int XoaTacGia_Data(TacGia objedit) {
        try {
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "UPDATE tac_gia  SET TRANG_THAI=0  WHERE MA_TGIA=?";
            int rowsAffected;
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                preparedStatement.setString(1, objedit.getMaTG());
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã xóa tác giả  thành công");
                } else {
                    System.out.println("Không thể xóa tác giả");
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

    public void Insert_NhieuTacGia(ArrayList<TacGia> docgia) throws SQLException {
        // Tạo thể hiện
        DataConnection conn = new DataConnection();
        Connection connectdata = conn.getConnection();
        String sqlquery = "INSERT INTO `quanlithuvien`.`tac_gia` (`MA_TGIA`, `TEN_TGIA`, `GIOI_TINH`, `NAM_SINH`, `TRANG_THAI`) VALUES (?,?, ?, ?, ?);";
        PreparedStatement statement = connectdata.prepareStatement(sqlquery);
        for (TacGia obj : docgia) {
            statement.setString(1, obj.getMaTG());
            statement.setString(2, obj.getTenTG());
            statement.setString(3, obj.getGioiTinh());
            statement.setString(4, obj.getNamSinh());
            statement.setInt(5, obj.getTrangThai());
            statement.addBatch(); // Thêm câu lệnh vào batch
        }

        statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
        // Đóng PreparedStatement
        conn.closeConnection(connectdata);

        System.out.println("Thêm dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

    }

    public void Edit_NhieuTacGia(ArrayList<TacGia> docgia) throws SQLException {
        // Tạo thể hiện
        DataConnection conn = new DataConnection();
        Connection connectdata = conn.getConnection();
        String sqlquery = "UPDATE tac_gia SET TEN_TGIA=?, GIOI_TINH=?, NAM_SINH=?  TRANG_THAI= ? WHERE MA_TGIA=?";
        PreparedStatement statement = connectdata.prepareStatement(sqlquery);
        for (TacGia obj : docgia) {
            statement.setString(1, obj.getTenTG());
            statement.setString(2, obj.getGioiTinh());
            statement.setString(3, obj.getNamSinh());
            statement.setInt(4, obj.getTrangThai());
            statement.setString(5, obj.getMaTG());
            statement.addBatch(); // Thêm câu lệnh vào batch
        }

        statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
        // Đóng PreparedStatement
        conn.closeConnection(connectdata);

        System.out.println("Sửa dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

    }

}