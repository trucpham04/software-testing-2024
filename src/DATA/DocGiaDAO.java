/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DATA;

import java.util.ArrayList;
import DTO.DocGia;
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
public class DocGiaDAO {

    // Lấy tất cả độc giả từ Database xuống
    public ArrayList<DocGia> getDocgia_Data() {
        ArrayList<DocGia> List_Docgianew = new ArrayList<DocGia>();
        try {
            // Tạo thể hiện
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();

            String Query = "SELECT * FROM DOC_GIA WHERE TRANG_TAI=1";
            // Tạo đối tượng Statement
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
                    docgia.setSDT(resultSet.getString("SDT"));
                    docgia.setEmail(resultSet.getString("EMAIL"));
                    docgia.setTrangThai(resultSet.getInt("TRANG_TAI"));
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

    // Update một độc giả mới lên cở sở dữ liệu
    public int insertDocgia_Data(DocGia newdocgia) {
        try {
            // Tạo thể hiện
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "INSERT INTO `quanlithuvien`.`doc_gia` (`MA_DG`, `HO_TEN`, `SDT`, `EMAIL`, `TRANG_TAI`) VALUES (?,?, ?, ?, ?);";
            int rowsAffected;
            // Thiết lập giá trị cho các tham số
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                // Thiết lập giá trị cho các tham số
                preparedStatement.setString(1, newdocgia.getMaDG());
                preparedStatement.setString(2, newdocgia.getHoTen());
                preparedStatement.setString(3, newdocgia.getSDT());
                preparedStatement.setString(4, newdocgia.getEmail());
                preparedStatement.setInt(5, newdocgia.getTrangThai());
                // Thực thi truy vấn INSERT
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã thêm độc giả thành công");
                } else {
                    System.out.println("Không thể thêm thông tin độc giả mới!");
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

    // Sủa thông tin của một độc giả (cho sửa tên ,sdt, email) update lên databse
    public int EditDocGia_Data(DocGia objedit) {
        try {
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "UPDATE doc_gia  SET HO_TEN=? , SDT=?  , EMAIL=? WHERE MA_DG=?";

            int rowsAffected;
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                preparedStatement.setString(1, objedit.getHoTen());
                preparedStatement.setString(2, objedit.getSDT());
                preparedStatement.setString(3, objedit.getEmail());
                preparedStatement.setString(4, objedit.getMaDG());
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã thay đổi thông tin  độc giả thành công");
                } else {
                    System.out.println("Không thể thay đổi thông tin độc giả");
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
    public int XoaDocGia_Data(DocGia objedit) {
        try {
            DataConnection conn = new DataConnection();
            Connection connectdata = conn.getConnection();
            String sqlquery = "UPDATE doc_gia  SET TRANG_TAI=0  WHERE MA_DG=?";
            int rowsAffected;
            try (PreparedStatement preparedStatement = connectdata.prepareStatement(sqlquery)) {
                preparedStatement.setString(1, objedit.getMaDG());
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã xóa độc giả  thành công");
                } else {
                    System.out.println("Không thể xóa độc giả");
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

    public void Insert_NhieuDocGia(ArrayList<DocGia> docgia) throws SQLException {
        // Tạo thể hiện
        DataConnection conn = new DataConnection();
        Connection connectdata = conn.getConnection();
        String sqlquery = "INSERT INTO `quanlithuvien`.`doc_gia` (`MA_DG`, `HO_TEN`, `SDT`, `EMAIL`, `TRANG_TAI`) VALUES (?,?, ?, ?, ?);";
        PreparedStatement statement = connectdata.prepareStatement(sqlquery);
        for (DocGia obj : docgia) {
            statement.setString(1, obj.getMaDG());
            statement.setString(2, obj.getHoTen());
            statement.setString(3, obj.getSDT());
            statement.setString(4, obj.getEmail());
            statement.setInt(5, obj.getTrangThai());
            statement.addBatch(); // Thêm câu lệnh vào batch
        }

        statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
        // Đóng PreparedStatement
        conn.closeConnection(connectdata);

        System.out.println("Thêm dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

    }

    public void Edit_NhieuDocGia(ArrayList<DocGia> docgia) throws SQLException {
        // Tạo thể hiện
        DataConnection conn = new DataConnection();
        Connection connectdata = conn.getConnection();
        String sqlquery = "UPDATE doc_gia  SET HO_TEN=? , SDT=?  , EMAIL=?, TRANG_TAI=? WHERE MA_DG=?;";
        PreparedStatement statement = connectdata.prepareStatement(sqlquery);
        for (DocGia obj : docgia) {
            statement.setString(1, obj.getHoTen());
            statement.setString(2, obj.getSDT());
            statement.setString(3, obj.getEmail());
            statement.setInt(4, obj.getTrangThai());
            statement.setString(5, obj.getMaDG());
            statement.addBatch(); // Thêm câu lệnh vào batch
        }

        statement.executeBatch(); // Thực thi tất cả các câu lệnh trong batch
        // Đóng PreparedStatement
        conn.closeConnection(connectdata);

        System.out.println("Sửa dữ liệu từ ArrayList  vào cơ sở dữ liệu thành công!");

    }

}
