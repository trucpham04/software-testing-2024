/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import DTO.NhaXuatBan;
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
public class NhaXuatBanDAO {

    // Lấy tất cả nhà xuất bản từ Database xuống
    public ArrayList<NhaXuatBan> getNhaXuatBan_Data() {
        ArrayList<NhaXuatBan> List_NhaXuatBannew = new ArrayList<>();
        try {
            // Tạo thể hiện
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();

            String Query = "SELECT * FROM nha_xuat_ban WHERE TRANG_THAI=1";
            // Tạo đối tượng Statement
            Statement statement = connectdata.createStatement();

            // Xử lý kết quả trả về từ truy vấn
            try (
                    // Thực thi truy vấn SQL
                    ResultSet resultSet = statement.executeQuery(Query)) {
                // Xử lý kết quả trả về từ truy vấn
                // Tạo một ArrayList rỗng

                while (resultSet.next()) {
                    NhaXuatBan nhaxuatban = new NhaXuatBan();
                    nhaxuatban.setMaNXB(resultSet.getString("MA_NXB"));
                    nhaxuatban.setTenNXB(resultSet.getString("TEN_NXB"));
                    nhaxuatban.setTrangThai(resultSet.getInt("TRANG_THAI"));
                    List_NhaXuatBannew.add(nhaxuatban);
                }

            }
            // Đóng connect
            conn.closeConnection(connectdata);

        } catch (SQLException e) {
            System.out.printf("\n Lỗi không thể lấy dữ liệu từ bảng LOAI_SACH ");
        }
        return List_NhaXuatBannew;
    }

    // Update một nhà xuất bản mới lên cở sở dữ liệu
    public int insertNhaXuatBan_Data(NhaXuatBan newnhaxuatban) {
        try {
            // Tạo thể hiện
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "INSERT INTO `quanlithuvien`.`nha_xuat_ban` (`MA_NXB`,`TEN_NXB`,  `TRANG_THAI`) VALUES (?,?, ?)";
            int rowsAffected;
            // Thiết lập giá trị cho các tham số
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                // Thiết lập giá trị cho các tham số
                preparedStatement.setString(1, newnhaxuatban.getMaNXB());
                preparedStatement.setString(2, newnhaxuatban.getTenNXB());
                preparedStatement.setInt(3, newnhaxuatban.getTrangThai());

                // Thực thi truy vấn INSERT
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã thêm nhà xuất bản thành công");
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

    // Sủa thông tin của một nhà xuất bản (cho sửa tên ) update lên databse
    public int EditNhaXuatBan_Data(NhaXuatBan objedit) {
        try {
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "UPDATE nha_xuat_ban  SET TEN_NXB=? WHERE MA_NXB=?";
            int rowsAffected;
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                preparedStatement.setString(1, objedit.getTenNXB());
                preparedStatement.setString(2, objedit.getMaNXB());
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã thay đổi thông tin  nhà xuất bản thành công");
                } else {
                    System.out.println("Không thể thay đổi thông tin nhà xuất bản");
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

    // Xóa thông tin nhà xuất bản (set trạng thái về 0)
    public int XoaNhaXuatBan_Data(NhaXuatBan objedit) {
        try {
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "UPDATE nha_xuat_ban  SET TRANG_THAI=0  WHERE MA_NXB=?";
            int rowsAffected;
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                preparedStatement.setString(1, objedit.getMaNXB());
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("\nĐã xóa nhà xuất bản  thành công");
                } else {
                    System.out.println("\nKhông thể xóa nhà xuất bản");
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

    public void Insert_NhieuNhaXuatBan(ArrayList<NhaXuatBan> docgia) throws SQLException {
        // Tạo thể hiện
        DataConnection conn = new DataConnection();
        Connection connectdata = conn.getConnection();
        String sqlquery = "INSERT INTO `quanlithuvien`.`nha_xuat_ban` (`MA_NXB`,`TEN_NXB`,  `TRANG_THAI`) VALUES (?,?, ?);";
        PreparedStatement statement = connectdata.prepareStatement(sqlquery);
        for (NhaXuatBan obj : docgia) {
            statement.setString(1, obj.getMaNXB());
            statement.setString(2, obj.getTenNXB());
            statement.setInt(3, obj.getTrangThai());
            statement.addBatch(); // Thêm câu lệnh vào batch
        }
        statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
        // Đóng PreparedStatement
        conn.closeConnection(connectdata);

        System.out.println("Thêm dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

    }

    public void Edit_NhieuNhaXuatBan(ArrayList<NhaXuatBan> docgia) throws SQLException {
        // Tạo thể hiện
        DataConnection conn = new DataConnection();
        Connection connectdata = conn.getConnection();
        String sqlquery = "UPDATE nha_xuat_ban  SET TEN_NXB=? TRANG_THAI=? WHERE MA_NXB=?;";
        PreparedStatement statement = connectdata.prepareStatement(sqlquery);
        for (NhaXuatBan obj : docgia) {
            statement.setString(1, obj.getTenNXB());
            statement.setInt(2, obj.getTrangThai());
            statement.setString(3, obj.getMaNXB());

            statement.addBatch(); // Thêm câu lệnh vào batch
        }

        statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
        // Đóng PreparedStatement
        conn.closeConnection(connectdata);

        System.out.println("Sửa dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

    }

}
