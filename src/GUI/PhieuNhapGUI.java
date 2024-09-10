/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.PhieuNhapBUS;
import DATA.PhieuNhapDAO;
import DTO.CTPhieuNhap;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.NhanVien;
import DTO.PhieuNhap;
import DTO.SachCT;
import DTO.TacGia;
import javax.swing.*;
import java.awt.*;
import static java.awt.Font.BOLD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Admin
 */
public class PhieuNhapGUI extends JPanel {
    private JLabel label_title;
    private JPanel panel_title;

    private JComboBox combobox_search;
    String[] searchs = { "Tìm Theo Mã Phiếu Nhập", "Tìm Theo Tên Nhân Viên", "Tìm Theo Mã Nhân Viên",
            "Tìm TheoTên Nhà Xuất Bản", "Tìm Theo Mã Nhà Xuất Bản" };
    private JTextField txt_Search;
    private JButton btn_Searchsubmit;
    private JPanel panel_Search;

    private JPanel panel_nav;
    private JButton insert;
    private JButton delete;
    private JButton home;
    String[] thongke = { "Chọn Yêu Cầu Thống Kê", "Thống Kê Theo Ngày ", "Thống Kê Theo Khoảng Thời Gian ",
            "Thống Kê Theo Tháng Năm" };
    private JComboBox combobox;
    private JButton detail;

    private JPanel panel_thongke;
    private JPanel panel_date;
    private JPanel panel_date1;
    private JPanel panel_date2;
    private JPanel panel_date3;
    private JButton btn_thongke;
    private JButton btn_close_thongke;
    private JDatePickerImpl date;
    private JDatePickerImpl start_date;
    private JDatePickerImpl end_date;
    private JComboBox Month;
    String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
    private JSpinner year;
    private SpinnerNumberModel yearModel;
    private JLabel label_date;
    private JLabel label_start_date;
    private JLabel label_end_date;

    // Table
    private JTable table_pnhap;
    private JPanel panel_table_pnhap;
    private DefaultTableModel model;

    private final PhieuNhapBUS phieunhapbus = new PhieuNhapBUS();
    private final PhieuNhapDAO phieunhapdao = new PhieuNhapDAO();

    // Kiểm tra xem chuỗi nhập vào có phải là mã loại sách hay không
    public boolean KiemTraMa5(String input) {
        // Kiểm tra xem chuỗi có chứa 4 chữ số và không chứa ký tự không phải số không
        return input.matches("\\d{5}");
    }

    // Kiểm tra xem chuỗi nhập vào có phải là mã loại sách hay không
    public boolean KiemTraMa4(String input) {
        // Kiểm tra xem chuỗi có chứa 4 chữ số và không chứa ký tự không phải số không
        return input.matches("\\d{4}");
    }

    public void ThemDataVaoJTablePhieuNhap(DefaultTableModel model, ArrayList<PhieuNhap> data) {
        model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
        for (PhieuNhap pn : data) {
            // Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
            model.addRow(new Object[] {
                    pn.get_maPNhap(),
                    pn.getNgayNhap(),
                    pn.get_NhaXuatBan().getMaNXB(),
                    pn.get_NhaXuatBan().getTenNXB(),
                    pn.get_NhanVien().getMaNV(),
                    pn.get_NhanVien().getHoTen(),
                    pn.getTongTien()

            });

        }
    }

    public void ThemDataVaoJTableCTPhieuNhap(DefaultTableModel model, ArrayList<CTPhieuNhap> data) {
        model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
        for (CTPhieuNhap ctpn : data) {
            // Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
            model.addRow(new Object[] {
                    ctpn.get_MaPhieuNhap(),
                    ctpn.get_Sach().get_MaSach(),
                    ctpn.get_Sach().get_TenSach(),
                    ctpn.get_soluong(),
                    ctpn.get_thanhtien()

            });

        }
    }

    private Integer select_thongke;

    public PhieuNhapGUI() {
        PhieuNhapGUI();
    }

    public void PhieuNhapGUI() {
        phieunhapbus.getDanhSachList();
        JPanel main = new JPanel();
        main.setLayout(new FlowLayout());
        main.setPreferredSize(new Dimension(1160, 670));

        // Title
        label_title = new JLabel("Phiếu Nhập");
        label_title.setFont(new Font("Arial", BOLD, 50));
        label_title.setForeground(Color.WHITE);
        panel_title = new JPanel(new FlowLayout());
        panel_title.setBackground(Color.PINK);
        panel_title.setPreferredSize(new Dimension(1160, 60));
        panel_title.add(label_title);

        // TÌM KIẾM
        combobox_search = new JComboBox(searchs);
        combobox_search.setPreferredSize(new Dimension(260, 40));
        combobox_search.setFont(new Font("Arial", BOLD, 15));
        combobox_search.setForeground(Color.WHITE);
        combobox_search.setBackground(Color.PINK);
        txt_Search = new JTextField();
        txt_Search.setPreferredSize(new Dimension(600, 40));
        txt_Search.setFont(new Font("Arial", BOLD, 15));
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/search-icon.png"));
        // Lấy hình ảnh từ ImageIcon
        Image originalImage = icon.getImage();
        // Thay đổi kích thước của hình ảnh
        Image scaledImage = originalImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        // Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        // Tạo một JButton với biểu tượng đã thay đổi kích thước
        btn_Searchsubmit = new JButton("Tìm KIếm", scaledIcon);
        btn_Searchsubmit.setPreferredSize(new Dimension(200, 40));
        btn_Searchsubmit.setForeground(Color.WHITE);
        btn_Searchsubmit.setBackground(Color.PINK);
        btn_Searchsubmit.setFont(new Font("Arial", BOLD, 15));
        panel_Search = new JPanel(new FlowLayout());
        panel_Search.setPreferredSize(new Dimension(1160, 50));
        panel_Search.setBackground(Color.WHITE);
        panel_Search.add(combobox_search);
        panel_Search.add(txt_Search);
        panel_Search.add(btn_Searchsubmit);
        // String [] searchs={"Tìm Theo Mã Phiếu Nhập", "Tìm Theo Tên Nhân Viên", "Tìm
        // Theo Mã Nhân Viên","Tìm TheoTên Nhà Xuất Bản", "Tìm Theo Mã Nhà Xuất Bản"};
        btn_Searchsubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = combobox_search.getSelectedIndex();
                switch (selectedIndex) {
                    case 0: {
                        // Tìm theo mã Phiếu Nhập
                        String userInput = txt_Search.getText();
                        if (userInput.isEmpty())
                            JOptionPane.showMessageDialog(null, "Hãy nhập mã phiếu nhập mà bạn muốn tìm kiếm !",
                                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        else {
                            if (KiemTraMa5(userInput) == true) {
                                ArrayList<PhieuNhap> objtrung = phieunhapbus.TimKiemTheoMaPhieuNhap(userInput);
                                // Danh sách rỗng
                                if (objtrung.isEmpty() == true)
                                    JOptionPane.showMessageDialog(null,
                                            "Không tìm thấy phiếu nhập nào có mã giống với mã mà bạn đang tìm kiếm.");
                                else {
                                    ThemDataVaoJTablePhieuNhap(model, objtrung);
                                }
                                txt_Search.setText("");

                            } else {
                                JOptionPane.showMessageDialog(null, "Mã bạn nhập không đúng !", "Cảnh báo",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        break;
                    }

                    case 1: {
                        // TÌM THEO TÊN NHÂN VIÊN
                        String userInput = txt_Search.getText();
                        if (userInput.isEmpty())
                            JOptionPane.showMessageDialog(null, "Hãy nhập tên nhân viên mà bạn muốn tìm kiếm !",
                                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        else {
                            ArrayList<PhieuNhap> objtrung = phieunhapbus.TimKiemTheoTenNhanVien(userInput);
                            // Danh sách rỗng
                            if (objtrung.isEmpty() == true)
                                JOptionPane.showMessageDialog(null,
                                        "Không tìm thấy phiếu nhập nào có tên nhân viên giống với tên nhân viên mà bạn đang tìm kiếm.");
                            else {
                                ThemDataVaoJTablePhieuNhap(model, objtrung);
                            }
                            txt_Search.setText("");
                        }
                        break;
                    }

                    case 2: {
                        // TÌM THEO MÃ NHÂN VIÊN
                        String userInput = txt_Search.getText();
                        if (userInput.isEmpty())
                            JOptionPane.showMessageDialog(null, "Hãy nhập mã nhân viên mà bạn muốn tìm kiếm !",
                                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        else {
                            if (KiemTraMa4(userInput) == true) {
                                ArrayList<PhieuNhap> objtrung = phieunhapbus.TimKiemTheoMaNhanVien(userInput);
                                // Danh sách rỗng
                                if (objtrung.isEmpty() == true)
                                    JOptionPane.showMessageDialog(null,
                                            "Không tìm thấy phiếu nhập nào có mã giống với mã mà bạn đang tìm kiếm.");
                                else {
                                    ThemDataVaoJTablePhieuNhap(model, objtrung);
                                }
                                txt_Search.setText("");

                            } else {
                                JOptionPane.showMessageDialog(null, "Mã bạn nhập không đúng !", "Cảnh báo",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        break;

                    }

                    case 3: {
                        // TÌM THEO TÊN NHÀ XUẤT BẢN
                        String userInput = txt_Search.getText();
                        if (userInput.isEmpty())
                            JOptionPane.showMessageDialog(null, "Hãy nhập tên nhà xuất bản  mà bạn muốn tìm kiếm !",
                                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        else {
                            ArrayList<PhieuNhap> objtrung = phieunhapbus.TimKiemTheoTenNhaXuatBan(userInput);
                            // Danh sách rỗng
                            if (objtrung.isEmpty() == true)
                                JOptionPane.showMessageDialog(null,
                                        "Không tìm thấy phiếu nhập nào có tên nhà xuất bản giống với tên nhà xuất bản mà bạn đang tìm kiếm.");
                            else {
                                ThemDataVaoJTablePhieuNhap(model, objtrung);
                            }
                            txt_Search.setText("");
                        }
                        break;
                    }

                    case 4: {
                        String userInput = txt_Search.getText();
                        if (userInput.isEmpty())
                            JOptionPane.showMessageDialog(null, "Hãy nhập mã nhà xuất bản mà bạn muốn tìm kiếm !",
                                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        else {
                            if (KiemTraMa4(userInput) == true) {
                                ArrayList<PhieuNhap> objtrung = phieunhapbus.TimKiemTheoMaNhaXuatBan(userInput);
                                // Danh sách rỗng
                                if (objtrung.isEmpty() == true)
                                    JOptionPane.showMessageDialog(null,
                                            "Không tìm thấy phiếu nhập nào có mã giống với mã mà bạn đang tìm kiếm.");
                                else {
                                    ThemDataVaoJTablePhieuNhap(model, objtrung);
                                }
                                txt_Search.setText("");

                            } else {
                                JOptionPane.showMessageDialog(null, "Mã bạn nhập không đúng !", "Cảnh báo",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        break;

                    }

                }
            }
        });

        // NAVIGATOR
        panel_nav = new JPanel(new FlowLayout());
        panel_nav.setBackground(Color.WHITE);
        panel_nav.setPreferredSize(new Dimension(1160, 70));

        ImageIcon iconhome = new ImageIcon(getClass().getResource("/img/home-icon.png"));
        // Lấy hình ảnh từ ImageIcon
        Image originalImagehome = iconhome.getImage();
        // Thay đổi kích thước của hình ảnh
        Image scaledImagehome = originalImagehome.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        // Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIconhome = new ImageIcon(scaledImagehome);
        home = new JButton("Home", scaledIconhome);
        home.setBackground(Color.PINK);
        home.setFont(new Font("Arial", BOLD, 15));
        home.setPreferredSize(new Dimension(150, 60));

        ImageIcon iconinsert = new ImageIcon(getClass().getResource("/img/blue-plus-icon.png"));
        // Lấy hình ảnh từ ImageIcon
        Image originalImageinsert = iconinsert.getImage();
        // Thay đổi kích thước của hình ảnh
        Image scaledImageinsert = originalImageinsert.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        // Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIconinsert = new ImageIcon(scaledImageinsert);
        insert = new JButton("Thêm", scaledIconinsert);
        insert.setBackground(Color.PINK);
        insert.setFont(new Font("Arial", BOLD, 15));
        insert.setPreferredSize(new Dimension(150, 60));

        ImageIcon icondelete = new ImageIcon(getClass().getResource("/img/blue-cross-icon.png"));
        // Lấy hình ảnh từ ImageIcon
        Image originalImagedelete = icondelete.getImage();
        // Thay đổi kích thước của hình ảnh
        Image scaledImagedelete = originalImagedelete.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        // Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIcondelete = new ImageIcon(scaledImagedelete);
        delete = new JButton("Xóa", scaledIcondelete);
        delete.setBackground(Color.PINK);
        delete.setFont(new Font("Arial", BOLD, 15));
        delete.setPreferredSize(new Dimension(150, 60));

        ImageIcon icondetail = new ImageIcon("src/img/Eye-icon.png");
        // Lấy hình ảnh từ ImageIcon
        Image originalImagedetail = icondetail.getImage();
        // Thay đổi kích thước của hình ảnh
        Image scaledImagedetail = originalImagedetail.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        // Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIcondetail = new ImageIcon(scaledImagedetail);
        detail = new JButton("Hiển Thị", scaledIcondetail);
        detail.setBackground(Color.PINK);
        detail.setFont(new Font("Arial", BOLD, 15));
        detail.setPreferredSize(new Dimension(150, 60));

        combobox = new JComboBox(thongke);
        combobox.setPreferredSize(new Dimension(260, 60));
        combobox.setFont(new Font("Arial", BOLD, 15));
        combobox.setForeground(Color.WHITE);
        combobox.setBackground(Color.PINK);

        panel_nav.add(home);
        panel_nav.add(insert);
        panel_nav.add(delete);
        panel_nav.add(detail);
        panel_nav.add(combobox);

        // CÁC SỰ KIỆN
        // Tạo một MouseListener để lắng nghe sự kiện di chuột
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                button.setBackground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                button.setBackground(Color.PINK);
            }
        };
        home.addMouseListener(mouseListener);
        insert.addMouseListener(mouseListener);
        delete.addMouseListener(mouseListener);
        detail.addMouseListener(mouseListener);

        // Table
        String[] columnNames = { "Mã PN", "Ng_Nhập", "Mã NXB", "Tên NXB", "Mã NV", "Tên NV", "Tổng Tiền" };
        model = new DefaultTableModel(columnNames, 0);
        ThemDataVaoJTablePhieuNhap(model, phieunhapbus.get_ListPhieuNhap());
        table_pnhap = new JTable(model);
        TableColumnModel columnModel = table_pnhap.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // Mã
        columnModel.getColumn(1).setPreferredWidth(100); // Tên
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(200);
        columnModel.getColumn(6).setPreferredWidth(100);// Trạng Thái
        table_pnhap.setDefaultEditor(Object.class, null);

        table_pnhap.setFocusable(false);
        table_pnhap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_pnhap.setIntercellSpacing(new Dimension(0, 0));
        table_pnhap.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table_pnhap.setRowHeight(30);
        table_pnhap.setShowVerticalLines(true);
        table_pnhap.getTableHeader().setOpaque(false);
        table_pnhap.setFillsViewportHeight(true);
        table_pnhap.getTableHeader().setBackground(Color.PINK);
        table_pnhap.getTableHeader().setForeground(Color.WHITE);
        table_pnhap.setSelectionBackground(new Color(52, 152, 219));

        panel_table_pnhap = new JPanel(new CardLayout());
        panel_table_pnhap.setPreferredSize(new Dimension(1160, 300));
        panel_table_pnhap.setBackground(Color.WHITE);

        // Thêm JTable vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(table_pnhap);
        panel_table_pnhap.add(scrollPane);

        // Nếu người dùng chọn nút HIỂN THỊ Chi Tiết Phiếu Nhập

        detail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table_pnhap.getSelectedRow();
                if (selectedRow == -1) {
                    // Không có dòng nào được chọn
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu nhập!", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    Object Ma = table_pnhap.getValueAt(selectedRow, 0);
                    var MaString = Ma.toString();
                    ArrayList<PhieuNhap> obj = phieunhapbus.TimKiemTheoMaPhieuNhap(MaString);
                    JPanel hthi = PhieuNhapHienThiGUI(obj.get(0));
                    JFrame frame = new JFrame();
                    frame.setSize(1170, 683);
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                    frame.add(hthi);
                }
            }
        });

        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel hthi = ThemPhieuNhapGUI();
                frame_pnhap = new JFrame();
                frame_pnhap.setSize(1170, 683);
                frame_pnhap.setVisible(true);
                frame_pnhap.setLocationRelativeTo(null);
                frame_pnhap.add(hthi);

            }
        });

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_thongke.setVisible(false);
                ThemDataVaoJTablePhieuNhap(model, phieunhapbus.get_ListPhieuNhap());
            }
        });

        // Nếu người dùng chọn nút XOÁ Phiếu Nhập
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table_pnhap.getSelectedRow();
                if (selectedRow == -1) {
                    // Không có dòng nào được chọn
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu nhập!", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    Object Ma = table_pnhap.getValueAt(selectedRow, 0);
                    var MaString = Ma.toString();
                    ArrayList<PhieuNhap> obj = phieunhapbus.TimKiemTheoMaPhieuNhap(MaString);
                    phieunhapdao.XoaPhieuNhap(obj.get(0));
                    try {
                        phieunhapdao.XoaCTietPhieuNhap(obj.get(0).getListCTiet());
                    } catch (SQLException ex) {
                        Logger.getLogger(PhieuNhapGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    phieunhapbus.XoaPhieuNhap(obj.get(0));
                    ThemDataVaoJTablePhieuNhap(model, phieunhapbus.get_ListPhieuNhap());
                    JOptionPane.showMessageDialog(null, "Đã xóa thành công phiếu nhập !");

                }

            }

        });

        // Nếu Người dùng chọn jcombobox thống kê
        // String [] thongke={"Chọn Yêu Cầu Thống Kê","Thống Kê Theo Ngày ", "Thống Kê
        // Theo Khoảng Thời Gian ","Thống Kê Theo Tháng Năm"};
        combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedOption = combobox.getSelectedIndex();
                switch (selectedOption) {
                    case 0: {
                        panel_thongke.setVisible(false);
                        break;
                    }
                    case 1: {
                        panel_thongke.setVisible(true);
                        panel_date.setVisible(true);
                        panel_date1.setVisible(false);
                        panel_date2.setVisible(false);
                        panel_date3.setVisible(true);
                        select_thongke = 1;
                        break;

                    }

                    case 2: {
                        panel_thongke.setVisible(true);
                        panel_date.setVisible(false);
                        panel_date1.setVisible(true);
                        panel_date2.setVisible(false);
                        panel_date3.setVisible(true);
                        select_thongke = 2;
                        break;

                    }

                    case 3: {
                        panel_thongke.setVisible(true);
                        panel_date.setVisible(false);
                        panel_date1.setVisible(false);
                        panel_date2.setVisible(true);
                        panel_date3.setVisible(true);
                        select_thongke = 3;
                        break;
                    }

                }

            }

        });

        // THỐNG KÊ
        UtilDateModel datemodel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(datemodel, p);

        panel_thongke = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_thongke.setPreferredSize(new Dimension(1000, 150));
        panel_thongke.setBackground(Color.WHITE);

        panel_date = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_date.setLayout(new FlowLayout());
        panel_date.setBackground(Color.WHITE);
        panel_date.setPreferredSize(new Dimension(250, 60));
        label_date = new JLabel("Nhập Ngày :");
        label_date.setFont(new Font("Arial", BOLD, 20));
        date = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        panel_date.add(label_date);
        panel_date.add(date);

        panel_date1 = new JPanel(new FlowLayout());
        panel_date1.setLayout(new FlowLayout());
        panel_date1.setBackground(Color.WHITE);
        panel_date1.setPreferredSize(new Dimension(250, 150));

        label_start_date = new JLabel("Nhập Ngày Bắt Đầu:");
        label_start_date.setFont(new Font("Arial", Font.BOLD, 20));

        // Tạo một UtilDateModel cho start_date và end_date
        UtilDateModel startDateModel = new UtilDateModel();
        JDatePanelImpl datePanel1 = new JDatePanelImpl(startDateModel, p);
        UtilDateModel endDateModel = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(endDateModel, p);

        // Tạo JDatePickerImpl cho start_date và end_date, sử dụng các UtilDateModel
        // riêng biệt
        start_date = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        end_date = new JDatePickerImpl(datePanel2, new DateLabelFormatter());

        label_end_date = new JLabel("Nhập Ngày Kết Thúc:");
        label_end_date.setFont(new Font("Arial", Font.BOLD, 20));

        panel_date1.add(label_start_date);
        panel_date1.add(start_date);
        panel_date1.add(label_end_date);
        panel_date1.add(end_date);

        panel_date2 = new JPanel(new FlowLayout());
        panel_date2.setLayout(new FlowLayout());
        panel_date2.setBackground(Color.WHITE);
        panel_date2.setPreferredSize(new Dimension(250, 150));
        JLabel label_months = new JLabel("Nhập Tháng :");
        label_months.setFont(new Font("Arial", BOLD, 20));
        Month = new JComboBox(months);
        Month.setPreferredSize(new Dimension(200, 29));
        Month.setFont(new Font("Arial", BOLD, 18));
        JLabel label_year = new JLabel("Nhập Năm :");
        label_year.setFont(new Font("Arial", BOLD, 20));
        yearModel = new SpinnerNumberModel(2024, 2022, 2050, 1);
        // Tạo một đối tượng JSpinner mới với SpinnerNumberModel
        year = new JSpinner(yearModel);
        year.setPreferredSize(new Dimension(200, 29));
        year.setFont(new Font("Arial", BOLD, 18));
        panel_date2.add(label_months);
        panel_date2.add(Month);
        panel_date2.add(label_year);
        panel_date2.add(year);

        ImageIcon icontk = new ImageIcon("src/img/Chart-Business-icon.png");
        // Lấy hình ảnh từ ImageIcon
        Image originalImagetk = icontk.getImage();
        // Thay đổi kích thước của hình ảnh
        Image scaledImagetk = originalImagetk.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        // Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIcontk = new ImageIcon(scaledImagetk);
        btn_thongke = new JButton("Thống kê", scaledIcontk);
        btn_thongke.setPreferredSize(new Dimension(200, 60));
        btn_thongke.setForeground(Color.WHITE);
        btn_thongke.setBackground(Color.PINK);
        btn_thongke.setFont(new Font("Arial", BOLD, 15));
        btn_close_thongke = new JButton("x");
        btn_close_thongke.setFont(new Font("Arial", BOLD, 25));
        btn_close_thongke.setPreferredSize(new Dimension(50, 50));
        btn_close_thongke.setBackground(Color.WHITE);
        btn_close_thongke.setBorder(BorderFactory.createEmptyBorder());
        panel_date3 = new JPanel();
        panel_date3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_date3.setBackground(Color.WHITE);
        panel_date3.setPreferredSize(new Dimension(200, 150));
        panel_date3.add(btn_close_thongke);
        panel_date3.add(btn_thongke);
        btn_close_thongke.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_close_thongke.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_close_thongke.setForeground(Color.BLACK);
            }
        });
        // Nhấn Nút Close Thống Kê
        btn_close_thongke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn tiếp tục hành động này không?",
                        "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.CANCEL_OPTION) {
                    panel_thongke.setVisible(false);
                }
            }

        });

        // Nhấn Nút Thống Kê
        btn_thongke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (select_thongke) {
                    case 1: {
                        // Lấy ngày được chọn từ JDatePickerImpl
                        java.util.Date selectedDate = (java.util.Date) date.getModel().getValue();
                        // Kiểm tra xem người dùng đã chọn ngày chưa
                        if (selectedDate != null) {
                            // Chuyển đổi từ java.util.Date sang java.sql.Date
                            java.sql.Date selectedDateSql = new java.sql.Date(selectedDate.getTime());

                            ArrayList<PhieuNhap> obj = new ArrayList<>();
                            obj = phieunhapdao.ThongKeTheoNgay(selectedDateSql);
                            ThemDataVaoJTablePhieuNhap(model, obj);
                            select_thongke = 0;
                            panel_thongke.setVisible(false);
                            break;
                        } else {
                            // Nếu người dùng chưa chọn ngày, hiển thị thông báo nhắc nhở
                            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bạn muốn thống kê!", "Cảnh báo",
                                    JOptionPane.WARNING_MESSAGE);
                        }

                    }
                    case 2: {
                        // Kiểm tra xem người dùng đã nhập đủ start_date và end_date chưa
                        if (start_date.getModel().getValue() == null || end_date.getModel().getValue() == null) {
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc.",
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Lấy giá trị ngày bắt đầu và ngày kết thúc từ JDatePickerImpl
                        java.util.Date startDate = (java.util.Date) start_date.getModel().getValue();
                        java.util.Date endDate = (java.util.Date) end_date.getModel().getValue();

                        // Kiểm tra xem start_date có lớn hơn hoặc bằng end_date không
                        if (startDate.compareTo(endDate) >= 0) {
                            JOptionPane.showMessageDialog(null,
                                    "Ngày bắt đầu phải nhỏ hơn ngày kết thúc. Vui lòng nhập lại.", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());
                        java.sql.Date endDateSql = new java.sql.Date(endDate.getTime());
                        ArrayList<PhieuNhap> obj = new ArrayList<>();
                        obj = phieunhapdao.ThongKeTheoKhoangThoiGian(startDateSql, endDateSql);
                        ThemDataVaoJTablePhieuNhap(model, obj);
                        select_thongke = 0;
                        panel_thongke.setVisible(false);
                        break;
                    }
                    case 3: {
                        int select_month = Month.getSelectedIndex();
                        int year_selected = (int) year.getValue();
                        int month = select_month + 1;
                        ArrayList<PhieuNhap> obj = new ArrayList<>();
                        obj = phieunhapdao.ThongKeTheoNam_Thang(year_selected, month);
                        ThemDataVaoJTablePhieuNhap(model, obj);
                        select_thongke = 0;
                        panel_thongke.setVisible(false);

                        break;
                    }

                }
            }
        });

        panel_thongke.add(panel_date);
        panel_thongke.add(panel_date1);
        panel_thongke.add(panel_date2);
        panel_thongke.add(panel_date3);
        this.setLayout(new FlowLayout(0, 0, 0));
        this.setBackground(Color.WHITE);
        this.add(panel_title);
        this.add(panel_Search);
        this.add(panel_nav);
        this.add(panel_thongke);
        panel_thongke.setVisible(false);
        this.add(panel_table_pnhap);

    }

    // Hiển Thị
    private JLabel label_title_ht;
    private JPanel panel_title_ht;
    private JPanel panel_pnhap_ht;

    private JPanel panel_pnhap_ht1;
    private JLabel label_mapnhap_ht;
    private JLabel mapnhap_ht;

    private JPanel panel_pnhap_ht2;
    private JLabel label_ngnhap_ht;
    private JLabel ngnhap_ht;

    private JPanel panel_pnhap_ht3;
    private JLabel label_manxb_ht;
    private JLabel manxb_ht;

    private JPanel panel_pnhap_ht4;
    private JLabel label_tennxb_ht;
    private JLabel tennxb_ht;

    private JPanel panel_pnhap_ht5;
    private JLabel label_manv_ht;
    private JLabel manv_ht;

    private JPanel panel_pnhap_ht6;
    private JLabel label_tennv_ht;
    private JLabel tennv_ht;

    private JPanel panel_pnhap_ht7;
    private JLabel label_tongtien_ht;
    private JLabel tongtien_ht;

    private JPanel panel_table_ctpn_ht;
    private JTable table_ctpn_ht;
    private DefaultTableModel modelctpn;

    // Dùng hiển thị phiếu nhập một cách chi tiết nha
    public JPanel PhieuNhapHienThiGUI(PhieuNhap obj) {
        JPanel main = new JPanel();
        label_title_ht = new JLabel("Chi Tiết Phiếu Nhập");
        label_title_ht.setFont(new Font("Arial", BOLD, 30));
        label_title_ht.setForeground(Color.WHITE);
        panel_title_ht = new JPanel(new FlowLayout());
        panel_title_ht.setBackground(Color.PINK);
        panel_title_ht.setPreferredSize(new Dimension(1160, 50));
        panel_title_ht.add(label_title_ht);

        // 1
        label_mapnhap_ht = new JLabel("- Mã Phiếu Nhập :");
        label_mapnhap_ht.setFont(new Font("Arial", BOLD, 18));
        label_mapnhap_ht.setForeground(Color.MAGENTA);
        mapnhap_ht = new JLabel();
        mapnhap_ht.setFont(new Font("Arial", BOLD, 18));
        // SET MÃ PHIẾU NHẬP
        mapnhap_ht.setText(obj.get_maPNhap());
        panel_pnhap_ht1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_ht1.add(label_mapnhap_ht);
        panel_pnhap_ht1.add(mapnhap_ht);
        panel_pnhap_ht1.setBackground(Color.WHITE);

        // 2
        label_ngnhap_ht = new JLabel("- Ngày Nhập Hàng :");
        label_ngnhap_ht.setFont(new Font("Arial", BOLD, 18));
        label_ngnhap_ht.setForeground(Color.MAGENTA);
        ngnhap_ht = new JLabel();
        ngnhap_ht.setFont(new Font("Arial", BOLD, 18));
        // SET NGÀY NHẬP
        ngnhap_ht.setText(obj.getNgayNhap().toString());
        panel_pnhap_ht2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_ht2.add(label_ngnhap_ht);
        panel_pnhap_ht2.add(ngnhap_ht);
        panel_pnhap_ht2.setBackground(Color.WHITE);

        // 3
        label_manxb_ht = new JLabel("- Mã Nhà Xuất Bản :");
        label_manxb_ht.setFont(new Font("Arial", BOLD, 18));
        label_manxb_ht.setForeground(Color.MAGENTA);
        manxb_ht = new JLabel();
        manxb_ht.setFont(new Font("Arial", BOLD, 18));
        // SET MÃ NHÀ XUẤT BẢN
        manxb_ht.setText(obj.get_NhaXuatBan().getMaNXB());
        panel_pnhap_ht3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_ht3.add(label_manxb_ht);
        panel_pnhap_ht3.add(manxb_ht);
        panel_pnhap_ht3.setBackground(Color.WHITE);

        // 4
        label_tennxb_ht = new JLabel("- Tên Nhà Xuất Bản :");
        label_tennxb_ht.setFont(new Font("Arial", BOLD, 18));
        label_tennxb_ht.setForeground(Color.MAGENTA);
        tennxb_ht = new JLabel();
        tennxb_ht.setFont(new Font("Arial", BOLD, 18));
        // SET TÊN NHÀ XUẤT BẢN
        tennxb_ht.setText(obj.get_NhaXuatBan().getTenNXB());
        panel_pnhap_ht4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_ht4.add(label_tennxb_ht);
        panel_pnhap_ht4.add(tennxb_ht);
        panel_pnhap_ht4.setBackground(Color.WHITE);

        // 5
        label_manv_ht = new JLabel("- Mã Nhân Viên :");
        label_manv_ht.setFont(new Font("Arial", BOLD, 18));
        label_manv_ht.setForeground(Color.MAGENTA);
        manv_ht = new JLabel();
        manv_ht.setFont(new Font("Arial", BOLD, 18));
        // SET MÃ NHÂN VIÊN
        manv_ht.setText(obj.get_NhanVien().getMaNV());
        panel_pnhap_ht5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_ht5.add(label_manv_ht);
        panel_pnhap_ht5.add(manv_ht);
        panel_pnhap_ht5.setBackground(Color.WHITE);

        // 6
        label_tennv_ht = new JLabel("- Tên Nhân Viên :");
        label_tennv_ht.setFont(new Font("Arial", BOLD, 18));
        label_tennv_ht.setForeground(Color.MAGENTA);
        tennv_ht = new JLabel();
        tennv_ht.setFont(new Font("Arial", BOLD, 18));
        // SET TÊN NHÂN VIÊN
        tennv_ht.setText(obj.get_NhanVien().getHoTen());
        panel_pnhap_ht6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_ht6.add(label_tennv_ht);
        panel_pnhap_ht6.add(tennv_ht);
        panel_pnhap_ht6.setBackground(Color.WHITE);

        // 7
        label_tongtien_ht = new JLabel("- Tổng TIền :");
        label_tongtien_ht.setFont(new Font("Arial", BOLD, 18));
        label_tongtien_ht.setForeground(Color.MAGENTA);
        tongtien_ht = new JLabel();
        tongtien_ht.setFont(new Font("Arial", BOLD, 18));
        // SET TỔNG TIỀN
        tongtien_ht.setText(obj.getTongTien().toString());
        panel_pnhap_ht7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_ht7.add(label_tongtien_ht);
        panel_pnhap_ht7.add(tongtien_ht);
        panel_pnhap_ht7.setBackground(Color.WHITE);

        // Tổng
        panel_pnhap_ht = new JPanel(new GridLayout(4, 2));
        panel_pnhap_ht.setBackground(Color.WHITE);
        panel_pnhap_ht.setPreferredSize(new Dimension(1160, 150));
        panel_pnhap_ht.add(panel_pnhap_ht1);
        panel_pnhap_ht.add(panel_pnhap_ht2);
        panel_pnhap_ht.add(panel_pnhap_ht3);
        panel_pnhap_ht.add(panel_pnhap_ht4);
        panel_pnhap_ht.add(panel_pnhap_ht5);
        panel_pnhap_ht.add(panel_pnhap_ht6);
        panel_pnhap_ht.add(panel_pnhap_ht7);

        String columnNames[] = { "Mã Phiếu", "Mã Sách", "Tên Sách", "Số Lượng", "Thành Tiền" };
        modelctpn = new DefaultTableModel(columnNames, 0);
        ThemDataVaoJTableCTPhieuNhap(modelctpn, obj.getListCTiet());
        table_ctpn_ht = new JTable(modelctpn);
        TableColumnModel columnModel = table_ctpn_ht.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // Mã
        columnModel.getColumn(1).setPreferredWidth(100); // Tên
        columnModel.getColumn(2).setPreferredWidth(400);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(200);
        table_ctpn_ht.setDefaultEditor(Object.class, null);

        table_ctpn_ht.setFocusable(false);
        table_ctpn_ht.setIntercellSpacing(new Dimension(0, 0));
        table_ctpn_ht.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table_ctpn_ht.setRowHeight(30);
        table_ctpn_ht.setShowVerticalLines(true);
        table_ctpn_ht.getTableHeader().setOpaque(false);
        table_ctpn_ht.setFillsViewportHeight(true);
        table_ctpn_ht.getTableHeader().setBackground(Color.PINK);
        table_ctpn_ht.getTableHeader().setForeground(Color.WHITE);
        table_ctpn_ht.setSelectionBackground(new Color(52, 152, 219));

        panel_table_ctpn_ht = new JPanel(new CardLayout());
        panel_table_ctpn_ht.setPreferredSize(new Dimension(1160, 500));
        panel_table_ctpn_ht.setBackground(Color.WHITE);

        // Thêm JTable vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(table_ctpn_ht);
        panel_table_ctpn_ht.add(scrollPane);

        main.setLayout(new FlowLayout());
        main.setPreferredSize(new Dimension(1160, 670));
        main.add(panel_title_ht);
        main.add(panel_pnhap_ht);
        main.add(panel_table_ctpn_ht);

        return main;

    }

    private JFrame frame_pnhap;
    // THÊM
    private JLabel label_title_t;
    private JPanel panel_title_t;

    private JPanel panel_pnhap_t1;
    private JLabel label_mapnhap_t;
    private JLabel mapnhap_t;

    private JPanel panel_pnhap_t2;
    private JLabel label_ngnhap_t;
    private JLabel ngnhap_t;

    private JPanel panel_pnhap_t3;
    private JLabel label_nxb_t;
    private JComboBox nxb_t;

    private JPanel panel_pnhap_manv;
    private JLabel label_manv;
    private JComboBox manv;

    private JPanel panel_pnhap_tennv;
    private JLabel label_tennv;
    private JLabel ten_nv;

    private JPanel panel_ctpn_t;
    private JTable table_ctpn_t;
    private DefaultTableModel modelthemctpn;
    private JButton btn_thempnhap;

    private JButton btn_themsachmoi;
    private JButton btn_themsachcu;
    private JPanel panel_ctpn_nav;

    private JPanel panel_ctpn_detail_t;
    private JLabel label_title_ctpndetail_t;

    private JLabel label_sach_t;
    private JComboBox sach_t;
    private JPanel panel_ctpn_detail_t1;

    private JLabel label_solg_t;
    private JTextField solg_t;
    private JPanel panel_ctpn_detail_t2;
    private JButton btn_closesach_t;
    private JButton btn_themsach_t;
    // THÊM PHIẾU NHẬP NHA
    private PhieuNhap objpnhapmoi;
    // List CTPN
    private ArrayList<CTPhieuNhap> list_obj;
    // Nếu sách cũ thì chỉ update số lượng
    private ArrayList<SachCT> list_sachold;
    // Nếu Sách Mới thì update thông tin của sách luôn
    private ArrayList<SachCT> list_sachnew;
    // Thêm Phiếu Nhập
    private ArrayList<SachCT> list_sachincombobox;
    private SachCT objsachold;
    private SachCT objsachnew;
    private CTPhieuNhap objctpnnew;

    public JPanel ThemPhieuNhapGUI() {
        objpnhapmoi = new PhieuNhap();// Khởi tạo một đối tượng Phiếu Nhập
        String maPN = phieunhapbus.TaoMaPhieuNhap_DuyNhat();// Tạo một mã phiếu nhập
        objpnhapmoi.set_maPNhap(maPN);
        JPanel main = new JPanel();
        // Khởi tạo
        list_obj = new ArrayList<>();
        list_sachold = new ArrayList<>();
        list_sachnew = new ArrayList<>();
        list_sachincombobox = new ArrayList<>();

        label_title_t = new JLabel("Thêm Phiếu Nhập");
        label_title_t.setFont(new Font("Arial", BOLD, 30));
        label_title_t.setForeground(Color.WHITE);
        panel_title_t = new JPanel(new FlowLayout());
        panel_title_t.setBackground(Color.PINK);
        panel_title_t.setPreferredSize(new Dimension(1160, 40));
        panel_title_t.add(label_title_t);

        label_mapnhap_t = new JLabel("- Mã Phiếu Nhập :");
        label_mapnhap_t.setFont(new Font("Arial", BOLD, 18));
        mapnhap_t = new JLabel();
        mapnhap_t.setText(maPN);
        mapnhap_t.setFont(new Font("Arial", BOLD, 18));
        panel_pnhap_t1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_t1.add(label_mapnhap_t);
        panel_pnhap_t1.add(mapnhap_t);
        panel_pnhap_t1.setBackground(Color.WHITE);
        panel_pnhap_t1.setPreferredSize(new Dimension(540, 30));

        // Lấy thời gian hiện tại
        LocalDate currentDate = LocalDate.now();
        // Đổi từ local date sang date
        Date date = java.sql.Date.valueOf(currentDate);
        // Set Ngày Nhập
        objpnhapmoi.setNgayNhap(date);
        // Format thời gian theo định dạng cụ thể, ở đây mình chọn định dạng
        // 'yyyy-MM-dd'
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);

        // Cập nhật cơ sở dữ liệu với ngày hiện tại
        // updateDatabase(dateString);
        label_ngnhap_t = new JLabel("- Ngày Nhập :");
        label_ngnhap_t.setFont(new Font("Arial", BOLD, 18));
        ngnhap_t = new JLabel();
        ngnhap_t.setText(dateString);
        ngnhap_t.setFont(new Font("Arial", BOLD, 18));
        panel_pnhap_t2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_t2.add(label_ngnhap_t);
        panel_pnhap_t2.add(ngnhap_t);
        panel_pnhap_t2.setBackground(Color.WHITE);
        panel_pnhap_t2.setPreferredSize(new Dimension(540, 30));

        label_manv = new JLabel("- Mã Nhân Viên :");
        label_manv.setFont(new Font("Arial", BOLD, 18));
        manv = new JComboBox(TaoModelNhanVien(phieunhapbus.get_ListNhanVien()));
        manv.setPreferredSize(new Dimension(100, 30));
        manv.setFont(new Font("Arial", BOLD, 18));
        panel_pnhap_manv = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_manv.add(label_manv);
        panel_pnhap_manv.add(manv);
        panel_pnhap_manv.setBackground(Color.WHITE);
        panel_pnhap_manv.setPreferredSize(new Dimension(540, 50));

        label_tennv = new JLabel("- Tên Nhân Viên :");
        label_tennv.setFont(new Font("Arial", BOLD, 18));
        ten_nv = new JLabel();
        ten_nv.setFont(new Font("Arial", BOLD, 18));
        panel_pnhap_tennv = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_tennv.add(label_tennv);
        panel_pnhap_tennv.add(ten_nv);
        panel_pnhap_tennv.setBackground(Color.WHITE);
        panel_pnhap_tennv.setPreferredSize(new Dimension(540, 50));

        label_nxb_t = new JLabel("- Nhà Xuất Bản :");
        label_nxb_t.setFont(new Font("Arial", BOLD, 18));
        // Tạo nhà xuất bản
        String[] modelcoboboxnxb = TaoModelNhaXuatBan(phieunhapbus.get_ListNhaXuatBan());
        nxb_t = new JComboBox<>(modelcoboboxnxb);
        nxb_t.setPreferredSize(new Dimension(300, 30));
        nxb_t.setFont(new Font("Arial", BOLD, 18));
        panel_pnhap_t3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_pnhap_t3.add(label_nxb_t);
        panel_pnhap_t3.add(nxb_t);
        panel_pnhap_t3.setBackground(Color.WHITE);
        panel_pnhap_t3.setPreferredSize(new Dimension(1160, 50));

        // SỰ KIÊN KHI CHỌN NHÀ XUẤT BẢN
        nxb_t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list_sachincombobox.isEmpty() != true) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "Nếu bạn chọn nhà xuất bản khác thì toàn bộ những gì bạn nhập sẽ bị xóa hết !Bạn có chắc chắn muốn chọn nhà xuất bản khác không?",
                            "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                list_sachincombobox.clear();
                list_obj.clear();
                list_sachold.clear();
                list_sachnew.clear();
                modelthemctpn.setRowCount(0);

                int selectedIndex = nxb_t.getSelectedIndex();
                String MaNXB = phieunhapbus.get_ListNhaXuatBan().get(selectedIndex).getMaNXB();
                NhaXuatBan nxbthem = phieunhapbus.get_ListNhaXuatBan().get(selectedIndex);
                // SET NHÀ XUÁT BẢN PHIẾU NHẬP
                objpnhapmoi.set_NhaXuatBan(nxbthem);
                String[] modelll = new String[phieunhapbus.get_ListSach().size()];
                int a = 0;
                for (int i = 0; i < phieunhapbus.get_ListSach().size(); i++) {
                    SachCT objsach = phieunhapbus.get_ListSach().get(i);
                    if (objsach.get_nhaXuatBan().getMaNXB().equals(MaNXB) == true) {
                        list_sachincombobox.add(objsach);
                        modelll[a] = objsach.get_TenSach();
                        a++;
                    }
                }
                sach_t.removeAllItems();

                for (int i = 0; i < a; i++) {
                    String item = modelll[i];
                    sach_t.addItem(item);
                }
            }
        });

        ImageIcon iconinsert = new ImageIcon(getClass().getResource("/img/blue-plus-icon.png"));
        // Lấy hình ảnh từ ImageIcon
        Image originalImageinsert = iconinsert.getImage();
        // Thay đổi kích thước của hình ảnh
        Image scaledImageinsert = originalImageinsert.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        // Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIconinsert = new ImageIcon(scaledImageinsert);

        // Nav
        // NAVIGATOR
        panel_ctpn_nav = new JPanel(new FlowLayout());
        panel_ctpn_nav.setBackground(Color.WHITE);
        panel_ctpn_nav.setPreferredSize(new Dimension(1160, 70));

        btn_themsachmoi = new JButton("Thêm Sách Mới", scaledIconinsert);
        btn_themsachmoi.setBackground(Color.PINK);
        btn_themsachmoi.setFont(new Font("Arial", BOLD, 15));
        btn_themsachmoi.setPreferredSize(new Dimension(200, 60));
        // SỰ KIỆN XẢY RA KHI THÊM SÁCH MỚI
        btn_themsachmoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel hthi = ThemSachGUI();
                frame_themsach = new JFrame();
                frame_themsach.setSize(1170, 683);
                frame_themsach.setVisible(true);
                frame_themsach.setLocationRelativeTo(null);
                frame_themsach.add(hthi);
            }

        });

        // Khi ta muốn đóng JFRAME THÊM SÁCH
        btn_themsachcu = new JButton("Thêm Sách", scaledIconinsert);
        btn_themsachcu.setBackground(Color.PINK);
        btn_themsachcu.setFont(new Font("Arial", BOLD, 15));
        btn_themsachcu.setPreferredSize(new Dimension(200, 60));

        // SỰ KIÊN KHI TA THÊM SÁCH CŨ
        btn_themsachcu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list_sachincombobox.isEmpty() == true) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà xuất bản !", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                panel_ctpn_detail_t.setVisible(true);
                btn_thempnhap.setVisible(false);
                btn_themsachmoi.setVisible(false);
            }
        });
        panel_ctpn_nav.add(btn_themsachmoi);
        panel_ctpn_nav.add(btn_themsachcu);

        // Thêm Chi tiết Tiết Phiếu Nhập
        panel_ctpn_detail_t = new JPanel();
        panel_ctpn_detail_t.setLayout(new FlowLayout());
        panel_ctpn_detail_t.setPreferredSize(new Dimension(1160, 120));/////

        label_sach_t = new JLabel("- Sách :");
        label_sach_t.setFont(new Font("Arial", BOLD, 18));
        sach_t = new JComboBox();
        sach_t.setPreferredSize(new Dimension(300, 30));
        sach_t.setFont(new Font("Arial", BOLD, 15));
        panel_ctpn_detail_t1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_ctpn_detail_t1.add(label_sach_t);
        panel_ctpn_detail_t1.add(sach_t);
        panel_ctpn_detail_t1.setBackground(Color.WHITE);
        panel_ctpn_detail_t1.setPreferredSize(new Dimension(900, 30));/////

        label_solg_t = new JLabel("- Số Lượng Nhập Vào :");
        label_solg_t.setFont(new Font("Arial", BOLD, 18));
        solg_t = new JTextField();
        solg_t.setPreferredSize(new Dimension(300, 30));
        panel_ctpn_detail_t2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_ctpn_detail_t2.add(label_solg_t);
        panel_ctpn_detail_t2.add(solg_t);
        panel_ctpn_detail_t2.setBackground(Color.WHITE);
        panel_ctpn_detail_t2.setPreferredSize(new Dimension(900, 40));/////

        label_title_ctpndetail_t = new JLabel("Nhập Thêm Sách ");
        label_title_ctpndetail_t.setFont(new Font("Arial", BOLD, 20));
        label_title_ctpndetail_t.setForeground(Color.red);
        label_title_ctpndetail_t.setPreferredSize(new Dimension(1020, 30));
        label_title_ctpndetail_t.setHorizontalAlignment(SwingConstants.CENTER);
        btn_closesach_t = new JButton("X");
        btn_closesach_t.setFont(new Font("Arial", BOLD, 15));
        btn_closesach_t.setPreferredSize(new Dimension(100, 30));
        btn_closesach_t.setBackground(Color.ORANGE);/////
        // SỰ KIỆN KHI TA MUỐN ĐÓNG KHI NHẬP SÁCH CŨ
        btn_closesach_t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn tiếp tục hành động này không?",
                        "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.CANCEL_OPTION) {
                    panel_ctpn_detail_t.setVisible(false);
                    btn_thempnhap.setVisible(true);
                    btn_themsachmoi.setVisible(true);
                }

            }
        });

        JPanel panel_ctpndetail_t3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_ctpndetail_t3.add(label_title_ctpndetail_t);
        panel_ctpndetail_t3.add(btn_closesach_t);
        panel_ctpndetail_t3.setBackground(Color.WHITE);////

        JPanel panel_ctpndetail_t4 = new JPanel();
        panel_ctpndetail_t4 = new JPanel();
        panel_ctpndetail_t4.add(panel_ctpn_detail_t1);
        panel_ctpndetail_t4.add(panel_ctpn_detail_t2);
        panel_ctpndetail_t4.setBackground(Color.WHITE);
        panel_ctpndetail_t4.setPreferredSize(new Dimension(990, 100));/////

        btn_themsach_t = new JButton("Thêm", scaledIconinsert);
        btn_themsach_t.setBackground(Color.PINK);
        btn_themsach_t.setFont(new Font("Arial", BOLD, 15));
        btn_themsach_t.setPreferredSize(new Dimension(150, 50));///

        // SỰ KIỆN XẢY RA KHI TA THÊM SỐ LƯỢNG SÁCH ĐÃ CÓ TRONG CỬA HÀNG
        btn_themsach_t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                objsachold = new SachCT();
                objctpnnew = new CTPhieuNhap();
                int selectedIndex = sach_t.getSelectedIndex();
                if (selectedIndex != -1) {
                    String txt_solg = solg_t.getText().trim();
                    String regex = "^[1-9]\\d*$";
                    if (txt_solg.isEmpty() || !Pattern.matches(regex, txt_solg)) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng đầy đủ và chính xác !", "Cảnh báo",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    int solgnew = list_sachincombobox.get(selectedIndex).get_SoLuong() + Integer.parseInt(txt_solg);
                    int thanhtien = solgnew * list_sachincombobox.get(selectedIndex).get_DonGia();
                    objsachold = list_sachincombobox.get(selectedIndex);
                    objsachold.set_SoLuong(solgnew);

                    objctpnnew.set_MaPhieuNhap(objpnhapmoi.get_maPNhap());
                    objctpnnew.set_soluong(Integer.parseInt(txt_solg));
                    objctpnnew.set_thanhtien(thanhtien);
                    objctpnnew.set_Sach(objsachold);
                    // Thêm chi tiêt phiếu nhập
                    list_obj.add(objctpnnew);
                    // Thêm vào danh sách sửa những sách có trong cửa hàng
                    list_sachold.add(objsachold);
                    // Thêm dòng mới jtable
                    ThemMotDongVoJTableCTPNhap(objctpnnew);
                    panel_ctpn_detail_t.setVisible(false);
                    btn_thempnhap.setVisible(true);
                    btn_themsachmoi.setVisible(true);
                    // Sách đã chọn rồi sẽ không đc chọn nữa
                    list_sachincombobox.remove(selectedIndex);
                    // Xóa danh sách chọn
                    solg_t.setText("");
                    sach_t.removeAllItems();
                    // Tạo mới danh sách chọn mới
                    String[] objmodelsach = TaoModelSach(list_sachincombobox);
                    for (String item : objmodelsach) {
                        sach_t.addItem(item);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn tên sách mà bạn muốn thêm số lượng mới !",
                            "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        JPanel panel_ctpndetail_t5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_ctpndetail_t5.add(panel_ctpndetail_t4);
        panel_ctpndetail_t5.add(btn_themsach_t);
        panel_ctpndetail_t5.setBackground(Color.WHITE);

        panel_ctpn_detail_t = new JPanel(new BorderLayout());
        panel_ctpn_detail_t.setPreferredSize(new Dimension(1160, 120));
        panel_ctpn_detail_t.setBackground(Color.WHITE);
        panel_ctpn_detail_t.add(panel_ctpndetail_t3, BorderLayout.NORTH);
        panel_ctpn_detail_t.add(panel_ctpndetail_t5, BorderLayout.CENTER);
        panel_ctpn_detail_t.setVisible(false);

        // Table
        String columnNames[] = { "Mã Phiếu", "Mã Sách", "Tên Sách", "Số Lượng", "Thành Tiền" };
        modelthemctpn = new DefaultTableModel(columnNames, 0);
        table_ctpn_t = new JTable(modelthemctpn);
        TableColumnModel columnModel = table_ctpn_t.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // Mã
        columnModel.getColumn(1).setPreferredWidth(100); // Tên
        columnModel.getColumn(2).setPreferredWidth(400);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(200);
        table_ctpn_t.setDefaultEditor(Object.class, null);

        table_ctpn_t.setFocusable(false);
        table_ctpn_t.setIntercellSpacing(new Dimension(0, 0));
        table_ctpn_t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table_ctpn_t.setRowHeight(30);
        table_ctpn_t.setShowVerticalLines(true);
        table_ctpn_t.getTableHeader().setOpaque(false);
        table_ctpn_t.setFillsViewportHeight(true);
        table_ctpn_t.getTableHeader().setBackground(Color.PINK);
        table_ctpn_t.getTableHeader().setForeground(Color.WHITE);
        table_ctpn_t.setSelectionBackground(new Color(52, 152, 219));

        panel_ctpn_t = new JPanel(new CardLayout());
        panel_ctpn_t.setPreferredSize(new Dimension(1160, 250));
        panel_ctpn_t.setBackground(Color.WHITE);

        // Thêm JTable vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(table_ctpn_t);
        panel_ctpn_t.add(scrollPane);

        // Thêm Phiếu Nhập
        JPanel panel_thempnhap = new JPanel();
        panel_thempnhap.setBackground(Color.WHITE);
        panel_thempnhap.setPreferredSize(new Dimension(1160, 60));

        btn_thempnhap = new JButton("Thêm Phiếu Nhập", scaledIconinsert);
        btn_thempnhap.setBackground(Color.PINK);
        btn_thempnhap.setFont(new Font("Arial", BOLD, 15));
        btn_thempnhap.setPreferredSize(new Dimension(300, 50));
        panel_thempnhap.add(btn_thempnhap);

        manv.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = manv.getSelectedItem().toString();
                if (ma.isEmpty() || KiemTraMa4(ma) == false) {
                    JOptionPane.showMessageDialog(null, "Vui lòng không để trống ô này !", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int ktra = 0;
                for (NhanVien nv : phieunhapbus.get_ListNhanVien()) {
                    if (nv.getMaNV().equals(ma)) {
                        ten_nv.setText(nv.getHoTen());
                        objpnhapmoi.set_NhanVien(nv);
                        ktra = 1;
                        break;
                    }
                }

                if (ktra == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã nhân viên của bạn !", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

            }
        });
        btn_thempnhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = manv.getSelectedItem().toString();
                if (ma.isEmpty() || KiemTraMa4(ma) == false) {
                    JOptionPane.showMessageDialog(null, "Vui lòng không để trống ô này !", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int ktra = 0;
                for (NhanVien nv : phieunhapbus.get_ListNhanVien()) {
                    if (nv.getMaNV().equals(ma)) {
                        ten_nv.setText(nv.getHoTen());
                        objpnhapmoi.set_NhanVien(nv);
                        ktra = 1;
                        break;
                    }
                }

                if (ktra == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã nhân viên của bạn !", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double Tong = 0.0;
                for (CTPhieuNhap obj : list_obj) {
                    Tong += obj.get_thanhtien();
                }
                objpnhapmoi.setTongTien((float) Tong);

                objpnhapmoi.setListCTiet(list_obj);
                int ThemMotPhieuNhap = phieunhapdao.ThemMotPhieuNhap(objpnhapmoi);

                phieunhapbus.ThemPhieuNhapMoi(objpnhapmoi);
                // Nhớ thay đổi bảng

                phieunhapdao.ThemMotChiTietPhieuNhap(list_obj);
                try {
                    phieunhapdao.Edit_NhieuSach(list_sachold);
                } catch (SQLException ex) {
                    Logger.getLogger(PhieuNhapGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    phieunhapdao.Insert_NhieuSach(list_sachnew);
                } catch (SQLException ex) {
                    Logger.getLogger(PhieuNhapGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                phieunhapbus.set_ListSach(phieunhapdao.getListSACH());
                ThemDataVaoJTablePhieuNhap(model, phieunhapbus.get_ListPhieuNhap());
                frame_pnhap.setVisible(false);

            }

        });

        main.setLayout(new FlowLayout());
        main.setBackground(Color.WHITE);
        main.setPreferredSize(new Dimension(1160, 670));
        main.add(panel_title_t);
        main.add(panel_pnhap_t1);
        main.add(panel_pnhap_t2);
        main.add(panel_pnhap_manv);
        main.add(panel_pnhap_tennv);
        main.add(panel_pnhap_t3);
        main.add(panel_ctpn_nav);

        main.add(panel_ctpn_detail_t);

        main.add(panel_ctpn_t);

        main.add(panel_thempnhap);
        return main;

    }

    public String[] TaoModelNhaXuatBan(ArrayList<NhaXuatBan> obj) {
        String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
        for (int i = 0; i < obj.size(); i++) {
            modelll[i] = obj.get(i).getTenNXB(); // Gán tên nhà xuất bản vào mảng
        }
        return modelll;
    }

    public String[] TaoModelLoaiSach(ArrayList<LoaiSach> obj) {
        String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
        for (int i = 0; i < obj.size(); i++) {
            modelll[i] = obj.get(i).getTenLoai(); // Gán tên nhà xuất bản vào mảng
        }
        return modelll;
    }

    public String[] TaoModelTacGia(ArrayList<TacGia> obj) {
        String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
        for (int i = 0; i < obj.size(); i++) {
            modelll[i] = obj.get(i).getTenTG(); // Gán tên nhà xuất bản vào mảng
        }
        return modelll;
    }

    public String[] TaoModelNhanVien(ArrayList<NhanVien> obj) {
        String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
        for (int i = 0; i < obj.size(); i++) {
            modelll[i] = obj.get(i).getMaNV(); // Gán tên nhà xuất bản vào mảng
        }
        return modelll;
    }

    public String[] TaoModelSach(ArrayList<SachCT> obj) {
        String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
        for (int i = 0; i < obj.size(); i++) {
            modelll[i] = obj.get(i).get_TenSach(); // Gán tên nhà xuất bản vào mảng
        }
        return modelll;
    }

    // Thêm dữ liệu vô jtable ctpn
    public void ThemMotDongVoJTableCTPNhap(CTPhieuNhap obj) {
        // String columnNames[]={"Mã Phiếu","Mã Sách","Tên Sách","Số Lượng","Thành
        // Tiền"};
        modelthemctpn.addRow(new Object[] {
                obj.get_MaPhieuNhap(),
                obj.get_Sach().get_MaSach(),
                obj.get_Sach().get_TenSach(),
                obj.get_soluong(),
                obj.get_thanhtien()

        });

    }

    private JFrame frame_themsach;

    // Thêm sách
    private JLabel label_title_sach;
    private JPanel panel_label_title_sach;

    private JPanel panel_themsach_temp;
    private JLabel label_masach_t;
    private JLabel masach_t;

    private JLabel label_tensach_t;
    private JTextField tensach_t;
    private JPanel panel_themsach_temp1;

    private JLabel label_namxb_t;
    private JTextField namxb_t;
    private JPanel panel_themsach_temp2;

    private JLabel label_dongia_t;
    private JTextField dongia_t;
    private JPanel panel_themsach_temp3;

    private JLabel label_soluong_t;
    private JTextField soluong_t;
    private JPanel panel_themsach_temp4;

    private JLabel label_mota_t;
    private TextArea mota_t;
    private JPanel panel_themsach_temp5;

    // tác giả
    private JLabel label_tacgia_t2;
    private JComboBox combobox_tacgia;
    private JPanel panel_themsach_temp6;

    // loại sách
    private JLabel label_loaisach_t2;
    private JComboBox combo_lsach;
    private JPanel panel_themsach_temp7;
    private JPanel panel_themsach_temp8;
    private JButton btn_themsachmoi1;

    public JPanel ThemSachGUI() {
        JPanel main = new JPanel();
        objsachnew = new SachCT();
        objctpnnew = new CTPhieuNhap();
        label_title_sach = new JLabel("Thêm Sách Mới");
        label_title_sach.setFont(new Font("Arial", BOLD, 30));
        label_title_sach.setForeground(Color.WHITE);
        panel_label_title_sach = new JPanel(new FlowLayout());
        panel_label_title_sach.setBackground(Color.PINK);
        panel_label_title_sach.setPreferredSize(new Dimension(1160, 40));
        panel_label_title_sach.add(label_title_sach);

        label_masach_t = new JLabel("- Mã Sách Mới : ");
        label_masach_t.setFont(new Font("Arial", BOLD, 18));
        masach_t = new JLabel();
        masach_t.setFont(new Font("Arial", BOLD, 18));
        String masachnew = phieunhapbus.TaoMaSach_DuyNhat();
        // SET MÃ SÁCH MỚI
        objsachnew.set_MaSach(masachnew);
        masach_t.setText(masachnew);
        panel_themsach_temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_themsach_temp.setPreferredSize(new Dimension(540, 40));
        panel_themsach_temp.add(label_masach_t);
        panel_themsach_temp.add(masach_t);/////

        label_tensach_t = new JLabel("- Tên Sách Mới : ");
        label_tensach_t.setFont(new Font("Arial", BOLD, 18));
        tensach_t = new JTextField();
        tensach_t.setPreferredSize(new Dimension(300, 30));
        tensach_t.setFont(new Font("Arial", BOLD, 18));
        panel_themsach_temp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_themsach_temp1.setPreferredSize(new Dimension(540, 40));
        panel_themsach_temp1.add(label_tensach_t);
        panel_themsach_temp1.add(tensach_t);

        label_namxb_t = new JLabel("- Năm Xuất Bản : ");
        label_namxb_t.setFont(new Font("Arial", BOLD, 18));
        namxb_t = new JTextField();
        namxb_t.setPreferredSize(new Dimension(300, 30));
        namxb_t.setFont(new Font("Arial", BOLD, 18));
        panel_themsach_temp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_themsach_temp2.setPreferredSize(new Dimension(540, 40));
        panel_themsach_temp2.add(label_namxb_t);
        panel_themsach_temp2.add(namxb_t);

        label_dongia_t = new JLabel("- Đơn Giá : ");
        label_dongia_t.setFont(new Font("Arial", BOLD, 18));
        dongia_t = new JTextField();
        dongia_t.setPreferredSize(new Dimension(300, 30));
        panel_themsach_temp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_themsach_temp3.setPreferredSize(new Dimension(540, 40));
        panel_themsach_temp3.add(label_dongia_t);
        panel_themsach_temp3.add(dongia_t);

        label_soluong_t = new JLabel("- Số Lượng : ");
        label_soluong_t.setFont(new Font("Arial", BOLD, 18));
        soluong_t = new JTextField();
        soluong_t.setPreferredSize(new Dimension(300, 30));
        panel_themsach_temp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_themsach_temp4.setPreferredSize(new Dimension(540, 40));
        panel_themsach_temp4.add(label_soluong_t);
        panel_themsach_temp4.add(soluong_t);

        String[] modeltacgia = TaoModelTacGia(phieunhapbus.get_ListTacGia());
        label_tacgia_t2 = new JLabel("- Tác Giả :");
        label_tacgia_t2.setFont(new Font("Arial", BOLD, 18));
        combobox_tacgia = new JComboBox(modeltacgia);
        combobox_tacgia.setPreferredSize(new Dimension(300, 30));
        panel_themsach_temp6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_themsach_temp6.setPreferredSize(new Dimension(540, 40));
        panel_themsach_temp6.add(label_tacgia_t2);
        panel_themsach_temp6.add(combobox_tacgia);

        String[] modelloaisach = TaoModelLoaiSach(phieunhapbus.get_ListLoaiSach());
        label_loaisach_t2 = new JLabel("- Loại Sách :");
        label_loaisach_t2.setFont(new Font("Arial", BOLD, 18));
        combo_lsach = new JComboBox(modelloaisach);
        combo_lsach.setPreferredSize(new Dimension(300, 30));
        panel_themsach_temp7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_themsach_temp7.setPreferredSize(new Dimension(540, 70));
        panel_themsach_temp7.add(label_loaisach_t2);
        panel_themsach_temp7.add(combo_lsach);

        label_mota_t = new JLabel("- Mô Tả : ");
        label_mota_t.setFont(new Font("Arial", BOLD, 18));
        mota_t = new TextArea();
        mota_t.setPreferredSize(new Dimension(900, 250));

        panel_themsach_temp5 = new JPanel(new FlowLayout());
        panel_themsach_temp5.setPreferredSize(new Dimension(1080, 300));
        panel_themsach_temp5.add(label_mota_t);
        panel_themsach_temp5.add(mota_t);

        panel_themsach_temp.setBackground(Color.WHITE);
        panel_themsach_temp1.setBackground(Color.WHITE);
        panel_themsach_temp2.setBackground(Color.WHITE);
        panel_themsach_temp3.setBackground(Color.WHITE);
        panel_themsach_temp4.setBackground(Color.WHITE);
        panel_themsach_temp5.setBackground(Color.WHITE);
        panel_themsach_temp6.setBackground(Color.WHITE);
        panel_themsach_temp7.setBackground(Color.WHITE);

        ImageIcon iconinsert = new ImageIcon(getClass().getResource("/img/blue-plus-icon.png"));
        // Lấy hình ảnh từ ImageIcon
        Image originalImageinsert = iconinsert.getImage();
        // Thay đổi kích thước của hình ảnh
        Image scaledImageinsert = originalImageinsert.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        // Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIconinsert = new ImageIcon(scaledImageinsert);
        btn_themsachmoi1 = new JButton("Thêm", scaledIconinsert);
        btn_themsachmoi1.setBackground(Color.PINK);
        btn_themsachmoi1.setFont(new Font("Arial", BOLD, 15));
        btn_themsachmoi1.setPreferredSize(new Dimension(150, 60));
        panel_themsach_temp8 = new JPanel(new FlowLayout());
        panel_themsach_temp8.setPreferredSize(new Dimension(1060, 70));
        panel_themsach_temp8.add(btn_themsachmoi1);
        panel_themsach_temp8.setBackground(Color.WHITE);

        btn_themsachmoi1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String namxb = namxb_t.getText();
                String regex = "^[1-9]\\d*$";
                String currencyPattern = "^\\d+$";
                String regexyear = "^(?!0)\\d{4}$";
                String ten_sach = tensach_t.getText();
                int tacgia_option = combobox_tacgia.getSelectedIndex();
                int lsach_option = combo_lsach.getSelectedIndex();
                String solg = soluong_t.getText();
                String dongia = dongia_t.getText();
                String mota = mota_t.getText();
                if (!Pattern.matches(regexyear, namxb) || namxb.isEmpty() || ten_sach.isEmpty() || mota.isEmpty()
                        || dongia.isEmpty() || solg.isEmpty() || !Pattern.matches(regex, solg)
                        || !Pattern.matches(currencyPattern, dongia) || tacgia_option == -1 || lsach_option == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng và đầy đủ !", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                objsachnew.set_TenSach(ten_sach);
                objsachnew.set_MaSach(masachnew);
                objsachnew.set_NamXB(namxb);
                objsachnew.set_DonGia(Integer.parseInt(dongia));
                objsachnew.set_MoTa(mota);
                objsachnew.set_SoLuong(Integer.parseInt(solg));
                objsachnew.set_LoaiSach(phieunhapbus.get_ListLoaiSach().get(lsach_option));
                objsachnew.set_nhaXuatBan(objpnhapmoi.get_NhaXuatBan());
                objsachnew.set_tacGia(phieunhapbus.get_ListTacGia().get(tacgia_option));

                objctpnnew.set_MaPhieuNhap(objpnhapmoi.get_maPNhap());
                objctpnnew.set_Sach(objsachnew);
                objctpnnew.set_soluong(Integer.parseInt(solg));
                objctpnnew.set_thanhtien(Integer.parseInt(solg) * Integer.parseInt(dongia));

                list_obj.add(objctpnnew);// Thêm CTPNhập
                list_sachnew.add(objsachnew);// Thêm sách mới

                ThemMotDongVoJTableCTPNhap(objctpnnew);
                btn_thempnhap.setVisible(true);
                btn_themsach_t.setVisible(true);
                frame_themsach.setVisible(false);

            }
        });
        main.setLayout(new FlowLayout(FlowLayout.LEFT));
        main.setBackground(Color.WHITE);
        main.setPreferredSize(new Dimension(1160, 670));
        main.add(panel_label_title_sach);
        main.add(panel_themsach_temp);
        main.add(panel_themsach_temp1);
        main.add(panel_themsach_temp2);
        main.add(panel_themsach_temp3);
        main.add(panel_themsach_temp4);
        main.add(panel_themsach_temp6);
        main.add(panel_themsach_temp7);
        main.add(panel_themsach_temp5);
        main.add(panel_themsach_temp8);
        return main;

    }

}
