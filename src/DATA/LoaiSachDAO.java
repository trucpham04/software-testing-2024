/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import DTO.LoaiSach;
import java.util.ArrayList;
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
public class LoaiSachDAO {

    // Lấy tất cả loại sách từ Database xuống
    public ArrayList<LoaiSach> getLoaiSach_Data() {
        ArrayList<LoaiSach> List_LoaiSachnew = new ArrayList<>();
        try {
            // Tạo thể hiện
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();

            String Query = "SELECT * FROM loai_sach WHERE TRANG_THAI=1";
            // Tạo đối tượng Statement
            Statement statement = connectdata.createStatement();

            // Xử lý kết quả trả về từ truy vấn
            try (
                    // Thực thi truy vấn SQL
                    ResultSet resultSet = statement.executeQuery(Query)) {
                // Xử lý kết quả trả về từ truy vấn
                // Tạo một ArrayList rỗng

                while (resultSet.next()) {
                    LoaiSach loaisach = new LoaiSach();
                    loaisach.setMaLoai(resultSet.getString("MA_LOAI"));
                    loaisach.setTenLoai(resultSet.getString("TEN_LOAI"));
                    loaisach.setTrangThai(resultSet.getInt("TRANG_THAI"));
                    List_LoaiSachnew.add(loaisach);
                }

            }
            // Đóng connect
            conn.closeConnection(connectdata);

        } catch (SQLException e) {
            System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng LOAI_SACH ");
        }
        return List_LoaiSachnew;
    }

    // Update một loại sách mới lên cở sở dữ liệu
    public int insertLoaiSach_Data(LoaiSach newloaisach) {
        try {
            // Tạo thể hiện
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "INSERT INTO `quanlithuvien`.`loai_sach` (`MA_LOAI`,`TEN_LOAI`,  `TRANG_THAI`) VALUES (?,?, ?)";
            int rowsAffected;
            // Thiết lập giá trị cho các tham số
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                // Thiết lập giá trị cho các tham số
                preparedStatement.setString(1, newloaisach.getMaLoai());
                preparedStatement.setString(2, newloaisach.getTenLoai());
                preparedStatement.setInt(3, newloaisach.getTrangThai());

                // Thực thi truy vấn INSERT
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã thêm loại sách thành công");
                } else {
                    System.out.println("Không thể thêm thông tin loại sạch mới!");
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

    // Sủa thông tin của một loại sách (cho sửa tên ) update lên databse
    public int EditLoaiSach_Data(LoaiSach objedit) {
        try {
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "UPDATE loai_sach  SET TEN_LOAI=? WHERE MA_LOAI=?";
            int rowsAffected;
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                preparedStatement.setString(1, objedit.getTenLoai());
                preparedStatement.setString(2, objedit.getMaLoai());
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã thay đổi thông tin  loại sách thành công");
                } else {
                    System.out.println("Không thể thay đổi thông tin loại sách");
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

    // Xóa thông tin loại sách (set trạng thái về 0)
    public int XoaLoaiSach_Data(LoaiSach objedit) {
        try {
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "UPDATE loai_sach  SET TRANG_THAI=0  WHERE MA_LOAI=?";
            int rowsAffected;
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                preparedStatement.setString(1, objedit.getMaLoai());
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("\nĐã xóa loại sách  thành công");
                } else {
                    System.out.println("\nKhông thể xóa loại sách");
                }
            }
            // Đóng PreparedStatement
            conn.closeConnection(connectdata);
            return rowsAffected;
        } catch (SQLException e) {
            System.out.printf("\n Lỗi  ");
        }
        return -1;
    }

    public void Insert_NhieuLoaiSach(ArrayList<LoaiSach> docgia) throws SQLException {
        // Tạo thể hiện
        DataConnection conn = new DataConnection();
        Connection connectdata = conn.getConnection();
        String sqlquery = "INSERT INTO `quanlithuvien`.`loai_sach` (`MA_LOAI`,`TEN_LOAI`,  `TRANG_THAI`) VALUES (?,?, ?);";
        PreparedStatement statement = connectdata.prepareStatement(sqlquery);
        for (LoaiSach obj : docgia) {
            statement.setString(1, obj.getMaLoai());
            statement.setString(2, obj.getTenLoai());
            statement.setInt(3, obj.getTrangThai());
            statement.addBatch(); // Thêm câu lệnh vào batch
        }
        statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
        // Đóng PreparedStatement
        conn.closeConnection(connectdata);

        System.out.println("Thêm dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

    }

    public void Edit_NhieuLoaiSach(ArrayList<LoaiSach> docgia) throws SQLException {
        // Tạo thể hiện
        DataConnection conn = new DataConnection();
        Connection connectdata = conn.getConnection();
        String sqlquery = "UPDATE loai_sach  SET TEN_LOAI=? TRANG_THAI=? WHERE MA_LOAI=?;";
        PreparedStatement statement = connectdata.prepareStatement(sqlquery);
        for (LoaiSach obj : docgia) {
            statement.setString(1, obj.getTenLoai());
            statement.setInt(2, obj.getTrangThai());
            statement.setString(3, obj.getMaLoai());

            statement.addBatch(); // Thêm câu lệnh vào batch
        }

        statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
        // Đóng PreparedStatement
        conn.closeConnection(connectdata);

        System.out.println("Sửa dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

    }

}
