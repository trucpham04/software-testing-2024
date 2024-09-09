/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.PhieuMuonBUS;
import BUS.PhieuTraBUS;
import DATA.PhieuTraDAO;
import DTO.CTPhieuMuon;
import DTO.CTPhieuTra;
import DTO.DocGia;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.NhanVien;
import DTO.PhieuMuon;
import DTO.PhieuTra;
import DTO.SachCT;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Admin
 */
public class PhieuTraGUI extends JPanel {
	private JLabel label_title;
	private JPanel panel_title;

	private JComboBox combobox_search;
	String[] searchs = { "Tìm Theo Mã Phiếu Trả", "Tìm Theo Tên Nhân Viên", "Tìm Theo Tên Độc Giả" };
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
	private JTable table_pthap;
	private JPanel panel_table_pthap;
	private DefaultTableModel model;

	private final PhieuTraBUS phieutrabus = new PhieuTraBUS();
	private final PhieuTraDAO phieutradao = new PhieuTraDAO();
	private final PhieuMuonBUS phieumuonbus = new PhieuMuonBUS();
	private final PhieuTraDAO phieumuondao = new PhieuTraDAO();
	private JPanel panel_pthap_masach;
	private JLabel label_maptuon_t;
	private JLabel maptuon_t;
	private JPanel panel_ptuon_t;
	private JLabel label_tennv;
	private JLabel ten_nv;

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

	public void ThemDataVaoJTablePhieuTra(DefaultTableModel model, ArrayList<PhieuTra> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (PhieuTra pt : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { pt.getPhieuTra(), pt.getPhieuMuon().getMaPhieuMuon(), pt.getDocGia().getHoTen(),
					pt.getNhanVien().getHoTen(), pt.getNgayTra(), pt.getTinhTrang(), pt.getSoLuong(), pt.getTrangThai()

			});

		}
	}

	public void ThemDataVaoJTableCTPhieuTra(DefaultTableModel model, ArrayList<CTPhieuTra> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (CTPhieuTra ctpt : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { ctpt.getPhieuTra(), ctpt.getSach().get_MaSach(), ctpt.getSach().get_TenSach(),
					ctpt.getSoLuong(),
					// tpt.getThanhTien()

			});

		}
	}

	private Integer select_thongke;

	public PhieuTraGUI() {
		PhieuTraGUI();
	}

	public void PhieuTraGUI() {
		phieutrabus.getDanhSachList();
		JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.setPreferredSize(new Dimension(1160, 670));

		// Title
		label_title = new JLabel("Phiếu Trả");
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
		ImageIcon icon = new ImageIcon("src/img/search-icon.png");
		// Lấy hình ảnh từ ImageIcon
		Image originalImage = icon.getImage();
		// Thay đổi kích thước của hình ảnh
		Image scaledImage = originalImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		// Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		// Tạo một JButton với biểu tượng đã thay đổi kích thước
		btn_Searchsubmit = new JButton("Tìm Kiếm", scaledIcon);
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
		// String [] searchs={"Tìm Theo Mã Phiếu Trả", "Tìm Theo Tên Nhân Viên", "Tìm
		// Theo Mã Nhân Viên","Tìm TheoTên Nhà Xuất Bản", "Tìm Theo Mã Nhà Xuất Bản"};
		btn_Searchsubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = combobox_search.getSelectedIndex();
				switch (selectedIndex) {
				case 0: {
					// Tìm theo mã Phiếu Mượn
					String userInput = txt_Search.getText();
					if (userInput.isEmpty())
						JOptionPane.showMessageDialog(null, "Hãy nhập mã phiếu trả mà bạn muốn tìm kiếm !", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					else {
						if (KiemTraMa5(userInput) == true) {
							ArrayList<PhieuTra> objtrung = phieutrabus.TimKiemTheoMaPhieuTra(userInput);
							// Danh sách rỗng
							if (objtrung.isEmpty() == true)
								JOptionPane.showMessageDialog(null,
										"Không tìm thấy phiếu trả nào có mã giống với mã mà bạn đang tìm kiếm.");
							else {
								ThemDataVaoJTablePhieuTra(model, objtrung);
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
					// TÌM THEO MÃ NHÂN VIÊN
					String userInput = txt_Search.getText();
					if (userInput.isEmpty())
						JOptionPane.showMessageDialog(null, "Hãy nhập tên nhân viên mà bạn muốn tìm kiếm !", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					else {
						ArrayList<PhieuTra> objtrung = phieutrabus.TimKiemTheoMaNhanVien(userInput);
						// Danh sách rỗng
						if (objtrung.isEmpty() == true)
							JOptionPane.showMessageDialog(null,
									"Không tìm thấy nhân viên nào có tên giống với tên nhân viên mà bạn đang tìm kiếm.");
						else {
							ThemDataVaoJTablePhieuTra(model, objtrung);
						}
						txt_Search.setText("");
					}
					break;
				}

				case 2: {
					// TÌM THEO MÃ ĐỘC GIẢ
					String userInput = txt_Search.getText();
					if (userInput.isEmpty())
						JOptionPane.showMessageDialog(null, "Hãy nhập tên độc giả mà bạn muốn tìm kiếm !", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					else {

						ArrayList<PhieuTra> objtrung = phieutrabus.TimKiemTheoMaDocGia(userInput);
						// Danh sách rỗng
						if (objtrung.isEmpty() == true)
							JOptionPane.showMessageDialog(null,
									"Không tìm thấy độc giả nào có tên giống với tên mà bạn đang tìm kiếm.");
						else {
							ThemDataVaoJTablePhieuTra(model, objtrung);
						}
						txt_Search.setText("");

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

		ImageIcon iconhome = new ImageIcon("src/img/home-icon.png");
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

		ImageIcon iconinsert = new ImageIcon("src/img/blue-plus-icon.png");
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

		ImageIcon icondelete = new ImageIcon("src/img/blue-cross-icon.png");
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
		String[] columnNames = { "Mã Phiếu Trả", "Mã Phiếu Mượn", "Tên Độc Giả", "Tên Nhân Viên", "Ngày Trả",
				"Tình Trạng", "Số Lượng" };
		model = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTablePhieuTra(model, phieutrabus.get_ListPhieuTra());
		table_pthap = new JTable(model);
		TableColumnModel columnModel = table_pthap.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); // Mã
		columnModel.getColumn(1).setPreferredWidth(100); // Tên
		columnModel.getColumn(2).setPreferredWidth(100);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(5).setPreferredWidth(150);
		columnModel.getColumn(6).setPreferredWidth(100);
		table_pthap.setDefaultEditor(Object.class, null);

		table_pthap.setFocusable(false);
		table_pthap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_pthap.setIntercellSpacing(new Dimension(0, 0));
		table_pthap.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table_pthap.setRowHeight(30);
		table_pthap.setShowVerticalLines(true);
		table_pthap.getTableHeader().setOpaque(false);
		table_pthap.setFillsViewportHeight(true);
		table_pthap.getTableHeader().setBackground(Color.PINK);
		table_pthap.getTableHeader().setForeground(Color.WHITE);
		table_pthap.setSelectionBackground(new Color(52, 152, 219));

		panel_table_pthap = new JPanel(new CardLayout());
		panel_table_pthap.setPreferredSize(new Dimension(1160, 300));
		panel_table_pthap.setBackground(Color.WHITE);

		// Thêm JTable vào JScrollPane
		JScrollPane scrollPane = new JScrollPane(table_pthap);
		panel_table_pthap.add(scrollPane);

		// Nếu người dùng chọn nút HIỂN THỊ Chi Tiết Phiếu Trả

		detail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_pthap.getSelectedRow();
				if (selectedRow == -1) {
					// Không có dòng nào được chọn
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu trả!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Object Ma = table_pthap.getValueAt(selectedRow, 0);
					var MaString = Ma.toString();
					ArrayList<PhieuTra> obj = phieutrabus.TimKiemTheoMaPhieuTra(MaString);
					JPanel hthi = PhieuTraHienThiGUI(obj.get(0));
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
				JPanel hthi = ThemPhieuTraGUI();
				frame_pthap = new JFrame();
				frame_pthap.setSize(1170, 683);
				frame_pthap.setVisible(true);
				frame_pthap.setLocationRelativeTo(null);
				frame_pthap.add(hthi);

			}
		});

		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_thongke.setVisible(false);
				ThemDataVaoJTablePhieuTra(model, phieutrabus.get_ListPhieuTra());
			}
		});

		// Nếu người dùng chọn nút XOÁ Phiếu Trả
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_pthap.getSelectedRow();
				if (selectedRow == -1) {
					// Không có dòng nào được chọn
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu trả!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Object Ma = table_pthap.getValueAt(selectedRow, 0);
					var MaString = Ma.toString();
					ArrayList<PhieuTra> obj = phieutrabus.TimKiemTheoMaPhieuTra(MaString);
					phieumuondao.XoaPhieuTra(obj.get(0));
					try {
						phieumuondao.XoaCTietPhieuTra(obj.get(0).getListCTiet());
					} catch (SQLException ex) {
						Logger.getLogger(PhieuTraGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
					phieutrabus.XoaPhieuTra(obj.get(0));
					ThemDataVaoJTablePhieuTra(model, phieutrabus.get_ListPhieuTra());
					JOptionPane.showMessageDialog(null, "Đã xóa thành công phiếu trả !");

				}

			}

		});

		// Nếu Người dùng chọn jcombobox thống kê
		String[] thongke = { "Chọn Yêu Cầu Thống Kê", "Thống Kê Theo Ngày ", "Thống Kê Theo Khoảng Thời Gian ",
				"Thống Kê Theo Tháng Năm" };
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

// Tạo JDatePickerImpl cho start_date và end_date, sử dụng các UtilDateModel riêng biệt
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

						ArrayList<PhieuTra> obj = new ArrayList<>();
						obj = phieutradao.ThongKeTheoNgay(selectedDateSql);
						ThemDataVaoJTablePhieuTra(model, obj);
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
					ArrayList<PhieuTra> obj = new ArrayList<>();
					obj = phieutradao.ThongKeTheoKhoangThoiGian(startDateSql, endDateSql);
					ThemDataVaoJTablePhieuTra(model, obj);
					select_thongke = 0;
					panel_thongke.setVisible(false);
					break;
				}
				case 3: {
					int select_month = Month.getSelectedIndex();
					int year_selected = (int) year.getValue();
					int month = select_month + 1;
					ArrayList<PhieuTra> obj = new ArrayList<>();
					obj = phieutradao.ThongKeTheoNam_Thang(year_selected, month);
					ThemDataVaoJTablePhieuTra(model, obj);
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
		this.setLayout(new FlowLayout(0,0,0));
		this.setBackground(Color.WHITE);
		this.add(panel_title);
		this.add(panel_Search);
		this.add(panel_nav);
		this.add(panel_thongke);
		panel_thongke.setVisible(false);
		this.add(panel_table_pthap);

	}

	// Hiển Thị
	private JLabel label_title_ht;
	private JPanel panel_title_ht;
	private JPanel panel_pthap_ht;

	private JPanel panel_pthap_ht1;
	private JLabel label_mapthap_ht;
	private JLabel mapthap_ht;

	private JPanel panel_pthap_ht2;
	private JLabel label_ngaymuon_ht;
	private JLabel ngaymuon_ht;

	private JPanel panel_pthap_ht3;
	private JLabel label_ngaytra_ht;
	private JLabel ngaytra_ht;

	private JPanel panel_pthap_ht4;
	private JLabel label_madg_ht;
	private JLabel madg_ht;

	private JPanel panel_pthap_ht5;
	private JLabel label_tendg_ht;
	private JLabel tendg_ht;

	private JPanel panel_pthap_ht6;
	private JLabel label_manv_ht;
	private JLabel manv_ht;

	private JPanel panel_pthap_ht7;
	private JLabel label_tennv_ht;
	private JLabel tennv_ht;

	private JPanel panel_pthap_ht8;
	private JLabel label_tt_ht;
	private JLabel tt_ht;

	private JPanel panel_table_ctpt_ht;
	private JTable table_ctpt_ht;
	private DefaultTableModel modelctpt;

	// Dùng hiển thị phiếu trả một cách chi tiết nha
	public JPanel PhieuTraHienThiGUI(PhieuTra obj) {
		JPanel main = new JPanel();
		label_title_ht = new JLabel("Chi Tiết Phiếu Mượn");
		label_title_ht.setFont(new Font("Arial", BOLD, 30));
		label_title_ht.setForeground(Color.WHITE);
		panel_title_ht = new JPanel(new FlowLayout());
		panel_title_ht.setBackground(Color.PINK);
		panel_title_ht.setPreferredSize(new Dimension(1160, 50));
		panel_title_ht.add(label_title_ht);

		// 1
		label_mapthap_ht = new JLabel("- Mã Phiếu Mượn :");
		label_mapthap_ht.setFont(new Font("Arial", BOLD, 18));
		label_mapthap_ht.setForeground(Color.MAGENTA);
		mapthap_ht = new JLabel();
		mapthap_ht.setFont(new Font("Arial", BOLD, 18));
		// SET MÃ PHIẾU MƯỢN
		mapthap_ht.setText(obj.getPhieuMuon().getMaPhieuMuon());
		panel_pthap_ht1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pthap_ht1.add(label_mapthap_ht);
		panel_pthap_ht1.add(mapthap_ht);
		panel_pthap_ht1.setBackground(Color.WHITE);

		label_ngaymuon_ht = new JLabel("- Mã Phiếu Trả :");
		label_ngaymuon_ht.setFont(new Font("Arial", BOLD, 18));
		label_ngaymuon_ht.setForeground(Color.MAGENTA);
		ngaymuon_ht = new JLabel();
		ngaymuon_ht.setFont(new Font("Arial", BOLD, 18));
		// SET NGÀY Mượn
		ngaymuon_ht.setText(obj.getPhieuTra().toString());

		panel_pthap_ht2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pthap_ht2.add(label_ngaymuon_ht);
		panel_pthap_ht2.add(ngaymuon_ht);
		panel_pthap_ht2.setBackground(Color.WHITE);

		// 3
		label_ngaytra_ht = new JLabel("- Ngày Trả :");
		label_ngaytra_ht.setFont(new Font("Arial", BOLD, 18));
		label_ngaytra_ht.setForeground(Color.MAGENTA);
		ngaytra_ht = new JLabel();
		ngaytra_ht.setFont(new Font("Arial", BOLD, 18));
		// SET NGÀY Mượn
		ngaytra_ht.setText(obj.getNgayTra().toString());

		panel_pthap_ht3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pthap_ht3.add(label_ngaytra_ht);
		panel_pthap_ht3.add(ngaytra_ht);
		panel_pthap_ht3.setBackground(Color.WHITE);

		// 4
		label_tennv_ht = new JLabel("- Tên Nhân Viên :");
		label_tennv_ht.setFont(new Font("Arial", BOLD, 18));
		label_tennv_ht.setForeground(Color.MAGENTA);
		tennv_ht = new JLabel();
		tennv_ht.setFont(new Font("Arial", BOLD, 18));
		tennv_ht.setText(obj.getNhanVien().getHoTen());
		panel_pthap_ht7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pthap_ht7.add(label_tennv_ht);
		panel_pthap_ht7.add(tennv_ht);
		panel_pthap_ht7.setBackground(Color.WHITE);

		label_madg_ht = new JLabel("- Mã Độc Giả :");
		label_madg_ht.setFont(new Font("Arial", BOLD, 18));
		label_madg_ht.setForeground(Color.MAGENTA);
		madg_ht = new JLabel();
		madg_ht.setFont(new Font("Arial", BOLD, 18));
		madg_ht.setText(obj.getDocGia().getMaDG());
		panel_pthap_ht4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pthap_ht4.add(label_madg_ht);
		panel_pthap_ht4.add(madg_ht);
		panel_pthap_ht4.setBackground(Color.WHITE);

		label_tt_ht = new JLabel("- Tình Trạng :");
		label_tt_ht.setFont(new Font("Arial", BOLD, 18));
		label_tt_ht.setForeground(Color.MAGENTA);
		tt_ht = new JLabel();
		tt_ht.setFont(new Font("Arial", BOLD, 18));
		tt_ht.setText(obj.getTinhTrang().toString());
		panel_pthap_ht8 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pthap_ht8.add(label_tt_ht);
		panel_pthap_ht8.add(tt_ht);
		panel_pthap_ht8.setBackground(Color.WHITE);

		label_tendg_ht = new JLabel("- Tên Độc Giả :");
		label_tendg_ht.setFont(new Font("Arial", BOLD, 18));
		label_tendg_ht.setForeground(Color.MAGENTA);
		tendg_ht = new JLabel();
		tendg_ht.setFont(new Font("Arial", BOLD, 18));
		tendg_ht.setText(obj.getDocGia().getHoTen());
		panel_pthap_ht5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pthap_ht5.add(label_tendg_ht);
		panel_pthap_ht5.add(tendg_ht);
		panel_pthap_ht5.setBackground(Color.WHITE);

		// 6
		label_manv_ht = new JLabel("- Mã Nhân Viên :");
		label_manv_ht.setFont(new Font("Arial", BOLD, 18));
		label_manv_ht.setForeground(Color.MAGENTA);
		manv_ht = new JLabel();
		manv_ht.setFont(new Font("Arial", BOLD, 18));
		// SET TÊN NHÀ XUẤT BẢN
		manv_ht.setText(obj.getNhanVien().getMaNV());
		panel_pthap_ht6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pthap_ht6.add(label_manv_ht);
		panel_pthap_ht6.add(manv_ht);
		panel_pthap_ht6.setBackground(Color.WHITE);

		// Tổng
		panel_pthap_ht = new JPanel(new GridLayout(4, 2));
		panel_pthap_ht.setBackground(Color.WHITE);
		panel_pthap_ht.setPreferredSize(new Dimension(1160, 150));

		panel_pthap_ht.add(panel_pthap_ht2);
		panel_pthap_ht.add(panel_pthap_ht1);
		panel_pthap_ht.add(panel_pthap_ht3);
		panel_pthap_ht.add(panel_pthap_ht8);
		panel_pthap_ht.add(panel_pthap_ht4);
		panel_pthap_ht.add(panel_pthap_ht5);
		panel_pthap_ht.add(panel_pthap_ht6);
		panel_pthap_ht.add(panel_pthap_ht7);

		String columnNames[] = { "Mã Phiếu", "Mã Sách", "Tên Sách", "Số Lượng" };
		modelctpt = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTableCTPhieuTra(modelctpt, obj.getListCTiet());
		table_ctpt_ht = new JTable(modelctpt);
		TableColumnModel columnModel = table_ctpt_ht.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); // Mã
		columnModel.getColumn(1).setPreferredWidth(100); // Tên
		columnModel.getColumn(2).setPreferredWidth(400);
		columnModel.getColumn(3).setPreferredWidth(200);
		table_ctpt_ht.setDefaultEditor(Object.class, null);

		table_ctpt_ht.setFocusable(false);
		table_ctpt_ht.setIntercellSpacing(new Dimension(0, 0));
		table_ctpt_ht.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table_ctpt_ht.setRowHeight(30);
		table_ctpt_ht.setShowVerticalLines(true);
		table_ctpt_ht.getTableHeader().setOpaque(false);
		table_ctpt_ht.setFillsViewportHeight(true);
		table_ctpt_ht.getTableHeader().setBackground(Color.PINK);
		table_ctpt_ht.getTableHeader().setForeground(Color.WHITE);
		table_ctpt_ht.setSelectionBackground(new Color(52, 152, 219));

		panel_table_ctpt_ht = new JPanel(new CardLayout());
		panel_table_ctpt_ht.setPreferredSize(new Dimension(1160, 500));
		panel_table_ctpt_ht.setBackground(Color.WHITE);

		// Thêm JTable vào JScrollPane
		JScrollPane scrollPane = new JScrollPane(table_ctpt_ht);
		panel_table_ctpt_ht.add(scrollPane);

		main.setLayout(new FlowLayout());
		main.setPreferredSize(new Dimension(1160, 670));
		main.add(panel_title_ht);
		main.add(panel_pthap_ht);
		main.add(panel_table_ctpt_ht);

		return main;

	}

	private JFrame frame_pthap;
	// THÊM
	private JLabel label_title_t;
	private JPanel panel_title_t;

	private JPanel panel_pmhap_t1;
	private JLabel label_mapmhap_t;
	private JComboBox mapmhap_t;

	private JPanel panel_pt_t1;
	private JLabel label_mapt_t;
	private JLabel mapt_t;

	private JPanel panel_pmhap_t2;
	private JLabel label_ngmuon_t;
	private JLabel ngnhap_t;
	private JLabel ngtra_t;

	private JPanel panel_pmhap_t3;
	private JLabel label_nxb_t;
	private JComboBox nxb_t;

	private JPanel panel_pmhap_manv;
	private JLabel label_manv;
	private JComboBox manv;

	private JPanel panel_pmhap_madg;
	private JLabel label_madg;
	private JLabel madg;

	private JPanel panel_pmhap_tendg;
	private JLabel label_tendg;
	private JLabel ten_dg;

	private JPanel panel_pmhap_tennv;
	private JLabel label_tennv1;
	private JLabel ten_nv1;

	private JPanel panel_ctpt_t;
	private JTable table_ctpt_t;
	private DefaultTableModel modelthemctpt;
	private JButton btn_thempthap;

	private JButton btn_themsachmoi;
	private JButton btn_themsachcu;
	private JPanel panel_ctpt_nav;

	private JPanel panel_ctpt_detail_t;
	private JLabel label_title_ctptdetail_t;

	private JLabel label_sach_t;
	private JComboBox sach_t;
	private JPanel panel_ctpt_detail_t1;
	private PhieuMuon objpm;
	private JLabel label_solg_t;
	private JLabel solg_t;
	private JPanel panel_ctpt_detail_t2;
	private JButton btn_closesach_t;
	private JButton btn_themsach_t;
	// THÊM PHIẾU NHẬP NHA
	private PhieuTra objpthaptoi;
	// List CTPN
	private ArrayList<CTPhieuTra> list_obj;
	// Nếu sách cũ thì chỉ update số lượng
	private ArrayList<SachCT> list_sachold;
	// Nếu Sách Mới thì update thông tin của sách luôn
	private ArrayList<SachCT> list_sachnew;
	// Thêm Phiếu Trả
	private ArrayList<SachCT> list_sachincombobox;
	private SachCT objsachold;
	private SachCT objsachnew;
	private CTPhieuTra objctptnew;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JPanel ThemPhieuTraGUI() {
		objpthaptoi = new PhieuTra();// Khởi tạo một đối tượng Phiếu Trả

		String mapt = phieutrabus.TaoMaPhieuMuon_DuyNhat();// Tạo một mã phiếu trả
		objpthaptoi.setPhieuTra(mapt);
		JPanel main = new JPanel();
		// Khởi tạo
		list_obj = new ArrayList<>();
		list_sachold = new ArrayList<>();
		list_sachnew = new ArrayList<>();
		list_sachincombobox = new ArrayList<>();

		label_title_t = new JLabel("Thêm Phiếu Trả");
		label_title_t.setFont(new Font("Arial", BOLD, 30));
		label_title_t.setForeground(Color.WHITE);
		panel_title_t = new JPanel(new FlowLayout());
		panel_title_t.setBackground(Color.PINK);
		panel_title_t.setPreferredSize(new Dimension(1160, 40));
		panel_title_t.add(label_title_t);

		label_mapt_t = new JLabel("- Mã Phiếu Trả :");
		label_mapt_t.setFont(new Font("Arial", BOLD, 18));
		mapt_t = new JLabel();
		mapt_t.setText(mapt);
		mapt_t.setFont(new Font("Arial", BOLD, 18));
		panel_pt_t1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pt_t1.add(label_mapt_t);
		panel_pt_t1.add(mapt_t);
		panel_pt_t1.setBackground(Color.WHITE);
		panel_pt_t1.setPreferredSize(new Dimension(540, 50));

		label_mapmhap_t = new JLabel("- Mã Phiếu Mượn :");
		label_mapmhap_t.setFont(new Font("Arial", BOLD, 18));
		mapmhap_t = new JComboBox(TaoModelPM(phieutrabus.getList_phieumuon()));
		mapmhap_t.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_t1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_t1.add(label_mapmhap_t);
		panel_pmhap_t1.add(mapmhap_t);
		panel_pmhap_t1.setBackground(Color.WHITE);
		panel_pmhap_t1.setPreferredSize(new Dimension(540, 50));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 60); // Thêm 7 ngày vào ngày hiện tại

		String ngayTraFormatted = dateFormat.format(calendar.getTime());
		// Lấy thời gian hiện tại
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(ngayTraFormatted, formatter);
		// Đổi từ local date sang date
		Date date1 = java.sql.Date.valueOf(currentDate);
		Date date2 = Date.valueOf(date);
		String dateString = dateFormat.format(date1);
		// Set Ngày Nhập
		objpthaptoi.setNgayTra(date1);

		// Format thời gian theo định dạng cụ thể, ở đây mình chọn định dạng
		// 'yyyy-MM-dd'

		// Cập nhật cơ sở dữ liệu với ngày hiện tại
		// updateDatabase(dateString);

		label_ngmuon_t = new JLabel("- Tình Trạng :");
		label_ngmuon_t.setFont(new Font("Arial", BOLD, 18));
		ngnhap_t = new JLabel();
		ngnhap_t.setText("");
		ngnhap_t.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_t2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_t2.add(label_ngmuon_t);
		panel_pmhap_t2.add(ngnhap_t);
		panel_pmhap_t2.setBackground(Color.WHITE);
		panel_pmhap_t2.setPreferredSize(new Dimension(540, 50));

		label_manv = new JLabel("- Mã Nhân Viên :");
		label_manv.setFont(new Font("Arial", BOLD, 18));
		manv = new JComboBox(TaoModelNhanVien(phieutrabus.get_ListNhanVien()));
		manv.setPreferredSize(new Dimension(100, 30));
		manv.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_manv = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_manv.add(label_manv);
		panel_pmhap_manv.add(manv);
		panel_pmhap_manv.setBackground(Color.WHITE);
		panel_pmhap_manv.setPreferredSize(new Dimension(540, 50));

		label_tennv = new JLabel("- Tên Nhân Viên:");
		label_tennv.setFont(new Font("Arial", BOLD, 18));
		ten_nv = new JLabel();
		ten_nv.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_tennv = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_tennv.add(label_tennv);
		panel_pmhap_tennv.add(ten_nv);
		panel_pmhap_tennv.setBackground(Color.WHITE);
		panel_pmhap_tennv.setPreferredSize(new Dimension(540, 50));

		label_madg = new JLabel("- Mã Độc Giả:");
		label_madg.setFont(new Font("Arial", BOLD, 18));
		madg = new JLabel();
		madg.setPreferredSize(new Dimension(100, 30));
		madg.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_madg = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_madg.add(label_madg);
		panel_pmhap_madg.add(madg);
		panel_pmhap_madg.setBackground(Color.WHITE);
		panel_pmhap_madg.setPreferredSize(new Dimension(540, 50));

		label_tendg = new JLabel("- Tên Độc Giả:");
		label_tendg.setFont(new Font("Arial", BOLD, 18));
		ten_dg = new JLabel();
		ten_dg.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_tendg = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_tendg.add(label_tendg);
		panel_pmhap_tendg.add(ten_dg);
		panel_pmhap_tendg.setBackground(Color.WHITE);
		panel_pmhap_tendg.setPreferredSize(new Dimension(540, 50));

		// Tạo label và hiển thị ngày trả
		label_nxb_t = new JLabel("- Ngày Trả :");
		label_nxb_t.setFont(new Font("Arial", BOLD, 18));
		ngtra_t = new JLabel();
		ngtra_t.setText(dateString);
		ngtra_t.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_t3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_t3.add(label_nxb_t);
		panel_pmhap_t3.add(ngtra_t);
		panel_pmhap_t3.setBackground(Color.WHITE);
		panel_pmhap_t3.setPreferredSize(new Dimension(540, 50));

		ImageIcon iconinsert = new ImageIcon("src/img/blue-plus-icon.png");
		// Lấy hình ảnh từ ImageIcon
		Image originalImageinsert = iconinsert.getImage();
		// Thay đổi kích thước của hình ảnh
		Image scaledImageinsert = originalImageinsert.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		// Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
		ImageIcon scaledIconinsert = new ImageIcon(scaledImageinsert);

		// Nav
		// NAVIGATOR
		panel_ctpt_nav = new JPanel(new FlowLayout());
		panel_ctpt_nav.setBackground(Color.WHITE);
		panel_ctpt_nav.setPreferredSize(new Dimension(1160, 70));

		btn_themsachmoi = new JButton("Thêm Sách Mới", scaledIconinsert);
		btn_themsachmoi.setBackground(Color.PINK);
		btn_themsachmoi.setFont(new Font("Arial", BOLD, 15));
		btn_themsachmoi.setPreferredSize(new Dimension(200, 60));
		// SỰ KIỆN XẢY RA KHI THÊM SÁCH MỚI

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
					JOptionPane.showMessageDialog(null, "Số sách trả đã đủ !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			
				panel_ctpt_detail_t.setVisible(true);
				btn_thempthap.setVisible(false);
				btn_themsachmoi.setVisible(false);
				sach_t.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
	
					    Object selectedItem = sach_t.getSelectedItem();
					    
					    if (selectedItem != null) {
					        String selectedItemString = selectedItem.toString();
					        
					        for (CTPhieuMuon ctpm : phieutrabus.getList_ctpm()) {
					            if (ctpm.getSach().get_TenSach().equals(selectedItemString)) {
					                String str = String.valueOf(ctpm.getSoLuong());
					                solg_t.setText(str);
					                break;
					            }
					        }
					    }

					  
					}
				});
			}
		});
		panel_ctpt_nav.add(btn_themsachcu);

		// Thêm Chi tiết Tiết Phiếu Trả
		panel_ctpt_detail_t = new JPanel();
		panel_ctpt_detail_t.setLayout(new FlowLayout());
		panel_ctpt_detail_t.setPreferredSize(new Dimension(1160, 120));/////

		label_sach_t = new JLabel("- Sách :");
		label_sach_t.setFont(new Font("Arial", BOLD, 18));
		sach_t = new JComboBox<>(TaoModelctpm(phieutrabus.getList_ctpm()));
		sach_t.setPreferredSize(new Dimension(300, 30));
		sach_t.setFont(new Font("Arial", BOLD, 15));
		panel_ctpt_detail_t1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_ctpt_detail_t1.add(label_sach_t);
		panel_ctpt_detail_t1.add(sach_t);
		panel_ctpt_detail_t1.setBackground(Color.WHITE);
		panel_ctpt_detail_t1.setPreferredSize(new Dimension(900, 30));/////

		label_solg_t = new JLabel("- Số Lượng Trả :");
		label_solg_t.setFont(new Font("Arial", BOLD, 18));
		solg_t = new JLabel();
		solg_t.setFont(new Font("Arial", BOLD, 18));
		solg_t.setPreferredSize(new Dimension(300, 30));
		panel_ctpt_detail_t2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_ctpt_detail_t2.add(label_solg_t);
		panel_ctpt_detail_t2.add(solg_t);
		panel_ctpt_detail_t2.setBackground(Color.WHITE);
		panel_ctpt_detail_t2.setPreferredSize(new Dimension(900, 40));/////

		label_title_ctptdetail_t = new JLabel("Nhập Thêm Sách ");
		label_title_ctptdetail_t.setFont(new Font("Arial", BOLD, 20));
		label_title_ctptdetail_t.setForeground(Color.red);
		label_title_ctptdetail_t.setPreferredSize(new Dimension(1020, 30));
		label_title_ctptdetail_t.setHorizontalAlignment(SwingConstants.CENTER);
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
					panel_ctpt_detail_t.setVisible(false);
					btn_thempthap.setVisible(true);
					btn_themsachmoi.setVisible(true);
				}

			}
		});

		JPanel panel_ctptdetail_t3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_ctptdetail_t3.add(label_title_ctptdetail_t);
		panel_ctptdetail_t3.add(btn_closesach_t);
		panel_ctptdetail_t3.setBackground(Color.WHITE);////

		JPanel panel_ctptdetail_t4 = new JPanel();
		panel_ctptdetail_t4 = new JPanel();
		panel_ctptdetail_t4.add(panel_ctpt_detail_t1);
		panel_ctptdetail_t4.add(panel_ctpt_detail_t2);
		panel_ctptdetail_t4.setBackground(Color.WHITE);
		panel_ctptdetail_t4.setPreferredSize(new Dimension(990, 100));/////

		btn_themsach_t = new JButton("Thêm");
		btn_themsach_t.setBackground(Color.PINK);
		btn_themsach_t.setFont(new Font("Arial", BOLD, 15));
		btn_themsach_t.setPreferredSize(new Dimension(150, 50));///

		// SỰ KIỆN XẢY RA KHI TA THÊM SỐ LƯỢNG SÁCH ĐÃ CÓ TRONG CỬA HÀNG
		btn_themsach_t.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				objsachold = new SachCT();
				objctptnew = new CTPhieuTra();
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
					objsachold = list_sachincombobox.get(selectedIndex);
					objsachold.set_SoLuong(solgnew);

					objctptnew.setPhieuTra(objpthaptoi.getPhieuTra());
					objctptnew.setSoLuong(Integer.parseInt(txt_solg));
					objctptnew.setSach(objsachold);
					// Thêm chi tiêt phiếu trả
					list_obj.add(objctptnew);
					// Thêm vào danh sách sửa những sách có trong cửa hàng
					list_sachold.add(objsachold);
					// Thêm dòng mới jtable
					ThemMotDongVoJTableCTptuon(objctptnew);
					panel_ctpt_detail_t.setVisible(false);
					btn_thempthap.setVisible(true);
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

		JPanel panel_ctptdetail_t5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_ctptdetail_t5.add(panel_ctptdetail_t4);
		panel_ctptdetail_t5.add(btn_themsach_t);
		panel_ctptdetail_t5.setBackground(Color.WHITE);

		panel_ctpt_detail_t = new JPanel(new BorderLayout());
		panel_ctpt_detail_t.setPreferredSize(new Dimension(1160, 120));
		panel_ctpt_detail_t.setBackground(Color.WHITE);
		panel_ctpt_detail_t.add(panel_ctptdetail_t3, BorderLayout.NORTH);
		panel_ctpt_detail_t.add(panel_ctptdetail_t5, BorderLayout.CENTER);
		panel_ctpt_detail_t.setVisible(false);

		// Table
		String columnNames[] = { "Mã Phiếu", "Mã Sách", "Tên Sách", "Số Lượng" };
		modelthemctpt = new DefaultTableModel(columnNames, 0);
		table_ctpt_t = new JTable(modelthemctpt);
		TableColumnModel columnModel = table_ctpt_t.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(200); // Mã
		columnModel.getColumn(1).setPreferredWidth(200); // Tên
		columnModel.getColumn(2).setPreferredWidth(200);
		columnModel.getColumn(3).setPreferredWidth(200);
		table_ctpt_t.setDefaultEditor(Object.class, null);

		table_ctpt_t.setFocusable(false);
		table_ctpt_t.setIntercellSpacing(new Dimension(0, 0));
		table_ctpt_t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table_ctpt_t.setRowHeight(30);
		table_ctpt_t.setShowVerticalLines(true);
		table_ctpt_t.getTableHeader().setOpaque(false);
		table_ctpt_t.setFillsViewportHeight(true);
		table_ctpt_t.getTableHeader().setBackground(Color.PINK);
		table_ctpt_t.getTableHeader().setForeground(Color.WHITE);
		table_ctpt_t.setSelectionBackground(new Color(52, 152, 219));

		panel_ctpt_t = new JPanel(new CardLayout());
		panel_ctpt_t.setPreferredSize(new Dimension(1160, 200));
		panel_ctpt_t.setBackground(Color.WHITE);

		// Thêm JTable vào JScrollPane
		JScrollPane scrollPane = new JScrollPane(table_ctpt_t);
		panel_ctpt_t.add(scrollPane);

		// Thêm Phiếu Trả
		JPanel panel_thempthap = new JPanel();
		panel_thempthap.setBackground(Color.WHITE);
		panel_thempthap.setPreferredSize(new Dimension(1160, 60));

		btn_thempthap = new JButton("Thêm Phiếu Trả");
		btn_thempthap.setBackground(Color.PINK);
		btn_thempthap.setFont(new Font("Arial", BOLD, 15));
		btn_thempthap.setPreferredSize(new Dimension(300, 50));
		panel_thempthap.add(btn_thempthap);
		
		mapmhap_t.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (list_sachincombobox.isEmpty() != true) {
					int option = JOptionPane.showConfirmDialog(null,
							"Nếu bạn chọn phiếu trả khác thì toàn bộ những gì bạn nhập sẽ bị xóa hết !Bạn có chắc chắn muốn chọn nhà xuất bản khác không?",
							"Xác nhận", JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				list_sachincombobox.clear();
				list_obj.clear();
				list_sachold.clear();
				list_sachnew.clear();
				  modelthemctpt.setRowCount(0); 
				String[] modelll = new String[phieutrabus.getList_ctpm().size()];
				int selectedIndex1 = mapmhap_t.getSelectedIndex();
				int a = 0;
				String mapm = phieutrabus.getList_phieumuon().get(selectedIndex1).getMaPhieuMuon();
				for (CTPhieuMuon ct : phieutrabus.getList_ctpm()) {
					for (int i = 0; i < phieutrabus.get_ListSach().size(); i++) {
						SachCT objsach = phieutrabus.get_ListSach().get(i);
						if (ct.getPhieuMuon().equals(mapm) && objsach.get_MaSach().equals(ct.getSach().get_MaSach())) {
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
				String ma1 = mapmhap_t.getSelectedItem().toString();
				if (ma1.isEmpty() || KiemTraMa5(ma1) == false) {
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống ô này !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				int ktra = 0;
				for (PhieuMuon pm : phieutrabus.getList_phieumuon()) {
					if (pm.getMaPhieuMuon().equals(ma1)) {
						objpthaptoi.setPhieuMuon(pm);
						madg.setText(pm.getDocGia().getMaDG());
						ten_dg.setText(pm.getDocGia().getHoTen());
						if (date1.compareTo(pm.getNgayTra()) > 0) {
							ngnhap_t.setText("Quá Hạn");
						} else
							ngnhap_t.setText("Đúng Hạn");
						ktra = 1;
						break;
					}
				}

				if (ktra == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã phiếu mượn của bạn !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String selectedIndex = madg.getText();
				int number = Integer.parseInt(selectedIndex);

				DocGia dg = phieutrabus.getList_docgia().get(number);
				// SET NHÀ XUÁT BẢN PHIẾU NHẬP
				objpthaptoi.setDocGia(dg);
				objpthaptoi.setTinhTrang(ngnhap_t.getText());

			}
		});
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
				for (NhanVien nv : phieutrabus.get_ListNhanVien()) {
					if (nv.getMaNV().equals(ma)) {
						ten_nv.setText(nv.getHoTen());
						objpthaptoi.setNhanVien(nv);
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

		btn_thempthap.addActionListener(new ActionListener() {
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void actionPerformed(ActionEvent e) {
				String ma1 = mapmhap_t.getSelectedItem().toString();
				if (ma1.isEmpty() || KiemTraMa5(ma1) == false) {
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống ô này !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				int ktra3 = 0;
				for (PhieuMuon pm : phieutrabus.getList_phieumuon()) {
					if (pm.getMaPhieuMuon().equals(ma1)) {
						objpthaptoi.setPhieuMuon(pm);
						madg.setText(pm.getDocGia().getMaDG());
						ten_dg.setText(pm.getDocGia().getHoTen());
						if (date1.compareTo(pm.getNgayTra()) > 0) {
							ngnhap_t.setText("Quá Hạn");
						} else
							ngnhap_t.setText("Đúng Hạn");
						ktra3 = 1;
						break;
					}
				}

				if (ktra3 == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã phiếu mượn của bạn !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String selectedIndex = madg.getText();
				int number = Integer.parseInt(selectedIndex);

				DocGia dg = phieutrabus.getList_docgia().get(number);
				// SET NHÀ XUÁT BẢN PHIẾU NHẬP
				objpthaptoi.setDocGia(dg);
				objpthaptoi.setTinhTrang(ngnhap_t.getText());

				String ma = manv.getSelectedItem().toString();
				if (ma.isEmpty() || KiemTraMa4(ma) == false) {
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống ô này !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				int ktra = 0;
				for (NhanVien nv : phieutrabus.get_ListNhanVien()) {
					if (nv.getMaNV().equals(ma)) {
						ten_nv.setText(nv.getHoTen());
						objpthaptoi.setNhanVien(nv);
						ktra = 1;
						break;
					}
				}

				if (ktra == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã nhân viên của bạn !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String mapmm = mapmhap_t.getSelectedItem().toString();
				phieumuonbus.delete2(mapmm);
				double Tong = 0.0;
				for (CTPhieuTra obj : list_obj) {
					Tong += obj.getSoLuong();
				}
				objpthaptoi.setSoLuong((int) Tong);

				objpthaptoi.setListCTiet(list_obj);
				int ThemMotPhieuTra = phieutradao.ThemMotPhieuTra(objpthaptoi);

				phieutrabus.ThemPhieuTraMoi(objpthaptoi);
				// Nhớ thay đổi bảng

				phieutradao.ThemMotChiTietPhieuTra(list_obj);
				try {
					phieutradao.Edit_NhieuSach(list_sachold);
				} catch (SQLException ex) {
					Logger.getLogger(PhieuTraGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
				try {
					phieutradao.Insert_NhieuSach(list_sachnew);
				} catch (SQLException ex) {
					Logger.getLogger(PhieuTraGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
				phieutrabus.set_ListSach(phieutradao.getListSACH());

				ThemDataVaoJTablePhieuTra(model, phieutrabus.get_ListPhieuTra());
				frame_pthap.setVisible(false);

			}

		});
		
		main.setLayout(new FlowLayout());
		main.setBackground(Color.WHITE);
		main.setPreferredSize(new Dimension(1160, 670));
		main.add(panel_title_t);
		main.add(panel_pt_t1);
		main.add(panel_pmhap_t1);
		main.add(panel_pmhap_t3);
		main.add(panel_pmhap_t2);

		main.add(panel_pmhap_manv);
		main.add(panel_pmhap_tennv);
		main.add(panel_pmhap_madg);
		main.add(panel_pmhap_tendg);
		main.add(panel_ctpt_nav);

		main.add(panel_ctpt_detail_t);

		main.add(panel_ctpt_t);

		main.add(panel_thempthap);
		return main;

	}

	public String[] TaoModelLoaiSach(ArrayList<LoaiSach> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			modelll[i] = obj.get(i).getTenLoai(); // Gán tên nhà xuất bản vào mảng
		}
		return modelll;
	}

	public String[] TaoModelTacGia(ArrayList<PhieuMuon> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			modelll[i] = obj.get(i).getMaPhieuMuon(); // Gán tên nhà xuất bản vào mảng
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

	public String[] TaoModelNhanVien(ArrayList<NhanVien> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			modelll[i] = obj.get(i).getMaNV(); // Gán tên nhà xuất bản vào mảng
		}
		return modelll;
	}

	public String[] TaoModelDocGia(ArrayList<DocGia> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			modelll[i] = obj.get(i).getMaDG(); // Gán tên nhà xuất bản vào mảng
		}
		return modelll;
	}

	public String[] TaoModelPM(ArrayList<PhieuMuon> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			modelll[i] = obj.get(i).getMaPhieuMuon(); // Gán tên nhà xuất bản vào mảng
		}
		return modelll;
	}

	public String[] TaoModelctpm(ArrayList<CTPhieuMuon> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			if (obj.get(i).getPhieuMuon().equals(mapmhap_t.getSelectedItem().toString())) {
				modelll[i] = obj.get(i).getSach().get_TenSach(); // Gán tên nhà xuất bản vào mảng
			}
		}
		return modelll;
	}

//Thêm dữ liệu vô jtable ctpt
	public void ThemMotDongVoJTableCTptuon(CTPhieuTra obj) {
		// String columnNames[]={"Mã Phiếu","Mã Sách","Tên Sách","Số Lượng","Thành
		// Tiền"};
		modelthemctpt.addRow(new Object[] { obj.getPhieuTra(), obj.getSach().get_MaSach(), obj.getSach().get_TenSach(),
				obj.getSoLuong(),

		});

	}

}
