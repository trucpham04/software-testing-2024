/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.PhieuMuonBUS;
import DATA.PhieuMuonDAO;
import DTO.CTPhieuMuon;
import DTO.DocGia;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.NhanVien;
import DTO.PhieuMuon;
import DTO.PhieuMuon;
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
public class PhieuMuonGUI extends JPanel {
	private JLabel label_title;
	private JPanel panel_title;

	private JComboBox combobox_search;
	String[] searchs = { "Tìm Theo Mã Phiếu Mượn", "Tìm Theo Tên Nhân Viên", "Tìm Theo Tên Độc Giả" };
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
	private JTable table_pmhap;
	private JPanel panel_table_pmhap;
	private DefaultTableModel model;

	private final PhieuMuonBUS phieumuonbus = new PhieuMuonBUS();
	private final PhieuMuonDAO phieumuondao = new PhieuMuonDAO();

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

	public void ThemDataVaoJTablePhieuMuon(DefaultTableModel model, ArrayList<PhieuMuon> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (PhieuMuon pm : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { pm.getMaPhieuMuon(), pm.getNgayMuon(), pm.getMaNhanVien().getHoTen(),
					pm.getDocGia().getHoTen(),
					pm.getNgayTra(), pm.getSoLuong(), pm.getTrangThai()

			});

		}
	}

	public void ThemDataVaoJTableCTPhieuMuon(DefaultTableModel model, ArrayList<CTPhieuMuon> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (CTPhieuMuon ctpm : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { ctpm.getPhieuMuon(), ctpm.getSach().get_MaSach(), ctpm.getSach().get_TenSach(),
					ctpm.getSoLuong(),
					// tpm.getThanhTien()

			});

		}
	}

	private Integer select_thongke;

	public PhieuMuonGUI() {
		PhieuMuonGUI();
	}

	public void PhieuMuonGUI() {
		phieumuonbus.getDanhSachList();
		JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.setPreferredSize(new Dimension(1160, 670));

		// Title
		label_title = new JLabel("Phiếu Mượn");
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
		// String [] searchs={"Tìm Theo Mã Phiếu Mượn", "Tìm Theo Tên Nhân Viên", "Tìm
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
							JOptionPane.showMessageDialog(null, "Hãy nhập mã phiếu mượn mà bạn muốn tìm kiếm !",
									"Cảnh báo",
									JOptionPane.WARNING_MESSAGE);
						else {
							if (KiemTraMa5(userInput) == true) {
								ArrayList<PhieuMuon> objtrung = phieumuonbus.TimKiemTheoMaPhieuMuon(userInput);
								// Danh sách rỗng
								if (objtrung.isEmpty() == true)
									JOptionPane.showMessageDialog(null,
											"Không tìm thấy phiếu mượn nào có mã giống với mã mà bạn đang tìm kiếm.");
								else {
									ThemDataVaoJTablePhieuMuon(model, objtrung);
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
							JOptionPane.showMessageDialog(null, "Hãy nhập tên nhân viên mà bạn muốn tìm kiếm !",
									"Cảnh báo",
									JOptionPane.WARNING_MESSAGE);
						else {
							ArrayList<PhieuMuon> objtrung = phieumuonbus.TimKiemTheoMaNhanVien(userInput);
							// Danh sách rỗng
							if (objtrung.isEmpty() == true)
								JOptionPane.showMessageDialog(null,
										"Không tìm thấy nhân viên nào có mã giống với tên nhân viên mà bạn đang tìm kiếm.");
							else {
								ThemDataVaoJTablePhieuMuon(model, objtrung);
							}
							txt_Search.setText("");
						}
						break;
					}

					case 2: {
						// TÌM THEO MÃ ĐỘC GIẢ
						String userInput = txt_Search.getText();
						if (userInput.isEmpty())
							JOptionPane.showMessageDialog(null, "Hãy nhập tên độc giả mà bạn muốn tìm kiếm !",
									"Cảnh báo",
									JOptionPane.WARNING_MESSAGE);
						else {

							ArrayList<PhieuMuon> objtrung = phieumuonbus.TimKiemTheoMaDocGia(userInput);
							// Danh sách rỗng
							if (objtrung.isEmpty() == true)
								JOptionPane.showMessageDialog(null,
										"Không tìm thấy độc giả nào có tên giống với tên độc giả mà bạn đang tìm kiếm.");
							else {
								ThemDataVaoJTablePhieuMuon(model, objtrung);
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
		String[] columnNames = { "Mã Phiếu Mượn", "Ngày Mượn", "Tên Nhân Viên", "Tên Độc Giả", "Ngày Trả", "Số Lượng" };
		model = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTablePhieuMuon(model, phieumuonbus.get_ListPhieuMuon());
		table_pmhap = new JTable(model);
		TableColumnModel columnModel = table_pmhap.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); // Mã
		columnModel.getColumn(1).setPreferredWidth(200); // Tên
		columnModel.getColumn(2).setPreferredWidth(100);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(200);
		columnModel.getColumn(5).setPreferredWidth(30);
		table_pmhap.setDefaultEditor(Object.class, null);

		table_pmhap.setFocusable(false);
		table_pmhap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_pmhap.setIntercellSpacing(new Dimension(0, 0));
		table_pmhap.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table_pmhap.setRowHeight(30);
		table_pmhap.setShowVerticalLines(true);
		table_pmhap.getTableHeader().setOpaque(false);
		table_pmhap.setFillsViewportHeight(true);
		table_pmhap.getTableHeader().setBackground(Color.PINK);
		table_pmhap.getTableHeader().setForeground(Color.WHITE);
		table_pmhap.setSelectionBackground(new Color(52, 152, 219));

		panel_table_pmhap = new JPanel(new CardLayout());
		panel_table_pmhap.setPreferredSize(new Dimension(1160, 300));
		panel_table_pmhap.setBackground(Color.WHITE);

		// Thêm JTable vào JScrollPane
		JScrollPane scrollPane = new JScrollPane(table_pmhap);
		panel_table_pmhap.add(scrollPane);

		// Nếu người dùng chọn nút HIỂN THỊ Chi Tiết Phiếu Mượn

		detail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_pmhap.getSelectedRow();
				if (selectedRow == -1) {
					// Không có dòng nào được chọn
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu mượn!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Object Ma = table_pmhap.getValueAt(selectedRow, 0);
					var MaString = Ma.toString();
					ArrayList<PhieuMuon> obj = phieumuonbus.TimKiemTheoMaPhieuMuon(MaString);
					JPanel hthi = PhieuMuonHienThiGUI(obj.get(0));
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
				JPanel hthi = ThemPhieuMuonGUI();
				frame_pmhap = new JFrame();
				frame_pmhap.setSize(1170, 683);
				frame_pmhap.setVisible(true);
				frame_pmhap.setLocationRelativeTo(null);
				frame_pmhap.add(hthi);

			}
		});

		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_thongke.setVisible(false);
				ThemDataVaoJTablePhieuMuon(model, phieumuonbus.get_ListPhieuMuon());
			}
		});

		// Nếu người dùng chọn nút XOÁ Phiếu Mượn
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_pmhap.getSelectedRow();
				if (selectedRow == -1) {
					// Không có dòng nào được chọn
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu mượn!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Object Ma = table_pmhap.getValueAt(selectedRow, 0);
					var MaString = Ma.toString();
					ArrayList<PhieuMuon> obj = phieumuonbus.TimKiemTheoMaPhieuMuon(MaString);
					phieumuondao.XoaPhieuMuon(obj.get(0));
					try {
						phieumuondao.XoaCTietPhieuMuon(obj.get(0).getListCTiet());
					} catch (SQLException ex) {
						Logger.getLogger(PhieuMuonGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
					phieumuonbus.XoaPhieuMuon(obj.get(0));
					ThemDataVaoJTablePhieuMuon(model, phieumuonbus.get_ListPhieuMuon());
					JOptionPane.showMessageDialog(null, "Đã xóa thành công phiếu mượn !");

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

		ImageIcon icontk = new ImageIcon("/img/Chart-Business-icon.png");
		// Lấy hình ảnh từ ImageIcon
		Image originalImagetk = icontk.getImage();
		// Thay đổi kích thước của hình ảnh
		Image scaledImagetk = originalImagetk.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		// Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
		ImageIcon scaledIcontk = new ImageIcon(scaledImagetk);
		btn_themsachmoi = new JButton("Thêm Sách Mới", scaledIconinsert);
		btn_themsachmoi.setBackground(Color.PINK);
		btn_themsachmoi.setFont(new Font("Arial", BOLD, 15));
		btn_themsachmoi.setPreferredSize(new Dimension(200, 60));
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

							ArrayList<PhieuMuon> obj = new ArrayList<>();
							obj = phieumuondao.ThongKeTheoNgay(selectedDateSql);
							ThemDataVaoJTablePhieuMuon(model, obj);
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
						ArrayList<PhieuMuon> obj = new ArrayList<>();
						obj = phieumuondao.ThongKeTheoKhoangThoiGian(startDateSql, endDateSql);
						ThemDataVaoJTablePhieuMuon(model, obj);
						select_thongke = 0;
						panel_thongke.setVisible(false);
						break;
					}
					case 3: {
						int select_month = Month.getSelectedIndex();
						int year_selected = (int) year.getValue();
						int month = select_month + 1;
						ArrayList<PhieuMuon> obj = new ArrayList<>();
						obj = phieumuondao.ThongKeTheoNam_Thang(year_selected, month);
						ThemDataVaoJTablePhieuMuon(model, obj);
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
		this.add(panel_table_pmhap);

	}

	// Hiển Thị
	private JLabel label_title_ht;
	private JPanel panel_title_ht;
	private JPanel panel_pmhap_ht;

	private JPanel panel_pmhap_ht1;
	private JLabel label_mapmhap_ht;
	private JLabel mapmhap_ht;

	private JPanel panel_pmhap_ht2;
	private JLabel label_ngaymuon_ht;
	private JLabel ngaymuon_ht;

	private JPanel panel_pmhap_ht3;
	private JLabel label_ngaytra_ht;
	private JLabel ngaytra_ht;

	private JPanel panel_pmhap_ht4;
	private JLabel label_madg_ht;
	private JLabel madg_ht;

	private JPanel panel_pmhap_ht5;
	private JLabel label_tendg_ht;
	private JLabel tendg_ht;

	private JPanel panel_pmhap_ht6;
	private JLabel label_manv_ht;
	private JLabel manv_ht;

	private JPanel panel_pmhap_ht7;
	private JLabel label_tennv_ht;
	private JLabel tennv_ht;

	private JPanel panel_table_ctpm_ht;
	private JTable table_ctpm_ht;
	private DefaultTableModel modelctpm;

	// Dùng hiển thị phiếu mượn một cách chi tiết nha
	public JPanel PhieuMuonHienThiGUI(PhieuMuon obj) {
		JPanel main = new JPanel();
		label_title_ht = new JLabel("Chi Tiết Phiếu Mượn");
		label_title_ht.setFont(new Font("Arial", BOLD, 30));
		label_title_ht.setForeground(Color.WHITE);
		panel_title_ht = new JPanel(new FlowLayout());
		panel_title_ht.setBackground(Color.PINK);
		panel_title_ht.setPreferredSize(new Dimension(1160, 50));
		panel_title_ht.add(label_title_ht);

		// 1
		label_mapmhap_ht = new JLabel("- Mã Phiếu Mượn :");
		label_mapmhap_ht.setFont(new Font("Arial", BOLD, 18));
		label_mapmhap_ht.setForeground(Color.MAGENTA);
		mapmhap_ht = new JLabel();
		mapmhap_ht.setFont(new Font("Arial", BOLD, 18));
		// SET MÃ PHIẾU MƯỢN
		mapmhap_ht.setText(obj.getMaPhieuMuon());
		panel_pmhap_ht1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_ht1.add(label_mapmhap_ht);
		panel_pmhap_ht1.add(mapmhap_ht);
		panel_pmhap_ht1.setBackground(Color.WHITE);

		label_ngaymuon_ht = new JLabel("- Ngày Mượn :");
		label_ngaymuon_ht.setFont(new Font("Arial", BOLD, 18));
		label_ngaymuon_ht.setForeground(Color.MAGENTA);
		ngaymuon_ht = new JLabel();
		ngaymuon_ht.setFont(new Font("Arial", BOLD, 18));
		// SET NGÀY Mượn
		ngaymuon_ht.setText(obj.getNgayMuon().toString());

		panel_pmhap_ht2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_ht2.add(label_ngaymuon_ht);
		panel_pmhap_ht2.add(ngaymuon_ht);
		panel_pmhap_ht2.setBackground(Color.WHITE);

		// 3
		label_ngaytra_ht = new JLabel("- Ngày Trả :");
		label_ngaytra_ht.setFont(new Font("Arial", BOLD, 18));
		label_ngaytra_ht.setForeground(Color.MAGENTA);
		ngaytra_ht = new JLabel();
		ngaytra_ht.setFont(new Font("Arial", BOLD, 18));
		// SET NGÀY Mượn
		ngaytra_ht.setText(obj.getNgayTra().toString());

		panel_pmhap_ht3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_ht3.add(label_ngaytra_ht);
		panel_pmhap_ht3.add(ngaytra_ht);
		panel_pmhap_ht3.setBackground(Color.WHITE);

		// 4
		label_tennv_ht = new JLabel("- Tên Nhân Viên :");
		label_tennv_ht.setFont(new Font("Arial", BOLD, 18));
		label_tennv_ht.setForeground(Color.MAGENTA);
		tennv_ht = new JLabel();
		tennv_ht.setFont(new Font("Arial", BOLD, 18));
		tennv_ht.setText(obj.getMaNhanVien().getHoTen());
		panel_pmhap_ht7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_ht7.add(label_tennv_ht);
		panel_pmhap_ht7.add(tennv_ht);
		panel_pmhap_ht7.setBackground(Color.WHITE);

		label_madg_ht = new JLabel("- Mã Độc Giả :");
		label_madg_ht.setFont(new Font("Arial", BOLD, 18));
		label_madg_ht.setForeground(Color.MAGENTA);
		madg_ht = new JLabel();
		madg_ht.setFont(new Font("Arial", BOLD, 18));
		madg_ht.setText(obj.getDocGia().getMaDG());
		panel_pmhap_ht4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_ht4.add(label_madg_ht);
		panel_pmhap_ht4.add(madg_ht);
		panel_pmhap_ht4.setBackground(Color.WHITE);

		label_tendg_ht = new JLabel("- Tên Độc Giả :");
		label_tendg_ht.setFont(new Font("Arial", BOLD, 18));
		label_tendg_ht.setForeground(Color.MAGENTA);
		tendg_ht = new JLabel();
		tendg_ht.setFont(new Font("Arial", BOLD, 18));
		tendg_ht.setText(obj.getDocGia().getHoTen());
		panel_pmhap_ht5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_ht5.add(label_tendg_ht);
		panel_pmhap_ht5.add(tendg_ht);
		panel_pmhap_ht5.setBackground(Color.WHITE);

		// 6
		label_manv_ht = new JLabel("- Mã Nhân Viên :");
		label_manv_ht.setFont(new Font("Arial", BOLD, 18));
		label_manv_ht.setForeground(Color.MAGENTA);
		manv_ht = new JLabel();
		manv_ht.setFont(new Font("Arial", BOLD, 18));
		// SET TÊN NHÀ XUẤT BẢN
		manv_ht.setText(obj.getMaNhanVien().getMaNV());
		panel_pmhap_ht6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_ht6.add(label_manv_ht);
		panel_pmhap_ht6.add(manv_ht);
		panel_pmhap_ht6.setBackground(Color.WHITE);

		// Tổng
		panel_pmhap_ht = new JPanel(new GridLayout(4, 2));
		panel_pmhap_ht.setBackground(Color.WHITE);
		panel_pmhap_ht.setPreferredSize(new Dimension(1160, 150));
		panel_pmhap_ht.add(panel_pmhap_ht1);
		panel_pmhap_ht.add(panel_pmhap_ht2);
		panel_pmhap_ht.add(panel_pmhap_ht3);
		panel_pmhap_ht.add(panel_pmhap_ht4);
		panel_pmhap_ht.add(panel_pmhap_ht5);
		panel_pmhap_ht.add(panel_pmhap_ht6);
		panel_pmhap_ht.add(panel_pmhap_ht7);

		String columnNames[] = { "Mã Phiếu", "Mã Sách", "Tên Sách", "Số Lượng" };
		modelctpm = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTableCTPhieuMuon(modelctpm, obj.getListCTiet());
		table_ctpm_ht = new JTable(modelctpm);
		TableColumnModel columnModel = table_ctpm_ht.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); // Mã
		columnModel.getColumn(1).setPreferredWidth(100); // Tên
		columnModel.getColumn(2).setPreferredWidth(400);
		columnModel.getColumn(3).setPreferredWidth(200);
		table_ctpm_ht.setDefaultEditor(Object.class, null);

		table_ctpm_ht.setFocusable(false);
		table_ctpm_ht.setIntercellSpacing(new Dimension(0, 0));
		table_ctpm_ht.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table_ctpm_ht.setRowHeight(30);
		table_ctpm_ht.setShowVerticalLines(true);
		table_ctpm_ht.getTableHeader().setOpaque(false);
		table_ctpm_ht.setFillsViewportHeight(true);
		table_ctpm_ht.getTableHeader().setBackground(Color.PINK);
		table_ctpm_ht.getTableHeader().setForeground(Color.WHITE);
		table_ctpm_ht.setSelectionBackground(new Color(52, 152, 219));

		panel_table_ctpm_ht = new JPanel(new CardLayout());
		panel_table_ctpm_ht.setPreferredSize(new Dimension(1160, 500));
		panel_table_ctpm_ht.setBackground(Color.WHITE);

		// Thêm JTable vào JScrollPane
		JScrollPane scrollPane = new JScrollPane(table_ctpm_ht);
		panel_table_ctpm_ht.add(scrollPane);

		main.setLayout(new FlowLayout());
		main.setPreferredSize(new Dimension(1160, 670));
		main.add(panel_title_ht);
		main.add(panel_pmhap_ht);
		main.add(panel_table_ctpm_ht);

		return main;

	}

	private JFrame frame_pmhap;
	// THÊM
	private JLabel label_title_t;
	private JPanel panel_title_t;

	private JPanel panel_pmhap_t1;
	private JLabel label_mapmhap_t;
	private JLabel mapmhap_t;

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
	private JComboBox madg;

	private JPanel panel_pmhap_tendg;
	private JLabel label_tendg;
	private JLabel ten_dg;

	private JPanel panel_pmhap_tennv;
	private JLabel label_tennv;
	private JLabel ten_nv;

	private JPanel panel_ctpm_t;
	private JTable table_ctpm_t;
	private DefaultTableModel modelthemctpm;
	private JButton btn_thempmhap;

	private JButton btn_themsachmoi;
	private JButton btn_themsachcu;
	private JPanel panel_ctpm_nav;

	private JPanel panel_ctpm_detail_t;
	private JLabel label_title_ctpmdetail_t;

	private JLabel label_sach_t;
	private JComboBox sach_t;
	private JPanel panel_ctpm_detail_t1;

	private JLabel label_solg_t;
	private JTextField solg_t;
	private JPanel panel_ctpm_detail_t2;
	private JButton btn_closesach_t;
	private JButton btn_themsach_t;
	// THÊM PHIẾU NHẬP NHA
	private PhieuMuon objpmhapmoi;
	// List CTPN
	private ArrayList<CTPhieuMuon> list_obj;
	// Nếu sách cũ thì chỉ update số lượng
	private ArrayList<SachCT> list_sachold;
	// Nếu Sách Mới thì update thông tin của sách luôn
	private ArrayList<SachCT> list_sachnew;
	// Thêm Phiếu Mượn
	private ArrayList<SachCT> list_sachincombobox;
	private SachCT objsachold;
	private SachCT objsachnew;
	private CTPhieuMuon objctpmnew;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JPanel ThemPhieuMuonGUI() {
		objpmhapmoi = new PhieuMuon();// Khởi tạo một đối tượng Phiếu Mượn
		String maPN = phieumuonbus.TaoMaPhieuMuon_DuyNhat();// Tạo một mã phiếu mượn
		objpmhapmoi.setMaPhieuMuon(maPN);
		JPanel main = new JPanel();
		// Khởi tạo
		list_obj = new ArrayList<>();
		list_sachold = new ArrayList<>();
		list_sachnew = new ArrayList<>();
		list_sachincombobox = new ArrayList<>();

		label_title_t = new JLabel("Thêm Phiếu Mượn");
		label_title_t.setFont(new Font("Arial", BOLD, 30));
		label_title_t.setForeground(Color.WHITE);
		panel_title_t = new JPanel(new FlowLayout());
		panel_title_t.setBackground(Color.PINK);
		panel_title_t.setPreferredSize(new Dimension(1160, 40));
		panel_title_t.add(label_title_t);

		label_mapmhap_t = new JLabel("- Mã Phiếu Mượn :");
		label_mapmhap_t.setFont(new Font("Arial", BOLD, 18));
		mapmhap_t = new JLabel();
		mapmhap_t.setText(maPN);
		mapmhap_t.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_t1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_t1.add(label_mapmhap_t);
		panel_pmhap_t1.add(mapmhap_t);
		panel_pmhap_t1.setBackground(Color.WHITE);
		panel_pmhap_t1.setPreferredSize(new Dimension(1080, 50));
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
		String dateString2 = dateFormat.format(date2);
		// Set Ngày Nhập
		objpmhapmoi.setNgayMuon(date1);
		objpmhapmoi.setNgayTra(date2);
		// Format thời gian theo định dạng cụ thể, ở đây mình chọn định dạng
		// 'yyyy-MM-dd'

		// Cập nhật cơ sở dữ liệu với ngày hiện tại
		// updateDatabase(dateString);
		label_ngmuon_t = new JLabel("- Ngày Mượn :");
		label_ngmuon_t.setFont(new Font("Arial", BOLD, 18));
		ngnhap_t = new JLabel();
		ngnhap_t.setText(dateString);
		ngnhap_t.setFont(new Font("Arial", BOLD, 18));
		panel_pmhap_t2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_t2.add(label_ngmuon_t);
		panel_pmhap_t2.add(ngnhap_t);
		panel_pmhap_t2.setBackground(Color.WHITE);
		panel_pmhap_t2.setPreferredSize(new Dimension(540, 50));

		label_manv = new JLabel("- Mã Nhân Viên :");
		label_manv.setFont(new Font("Arial", BOLD, 18));
		manv = new JComboBox(TaoModelNhanVien(phieumuonbus.get_ListNhanVien()));
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
		madg = new JComboBox(TaoModelDocGia(phieumuonbus.getList_docgia()));
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
		// Tính ngày trả là 7 ngày sau khi nhập

		// Định dạng ngày trả theo định dạng mong muốn

		// Tạo label và hiển thị ngày trả
		label_nxb_t = new JLabel("- Ngày Trả :");
		label_nxb_t.setFont(new Font("Arial", BOLD, 18));
		ngtra_t = new JLabel();
		ngtra_t.setText(dateString2);
		ngtra_t.setFont(new Font("Arial", BOLD, 18));
		// Tạo panel để chứa label và ngày trả
		panel_pmhap_t3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_pmhap_t3.add(label_nxb_t);
		panel_pmhap_t3.add(ngtra_t);
		panel_pmhap_t3.setBackground(Color.WHITE);
		panel_pmhap_t3.setPreferredSize(new Dimension(540, 50));

		ImageIcon iconinsert = new ImageIcon(getClass().getResource("/img/blue-plus-icon.png"));
		// Lấy hình ảnh từ ImageIcon
		Image originalImageinsert = iconinsert.getImage();
		// Thay đổi kích thước của hình ảnh
		Image scaledImageinsert = originalImageinsert.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		// Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
		ImageIcon scaledIconinsert = new ImageIcon(scaledImageinsert);

		// Nav
		// NAVIGATOR
		panel_ctpm_nav = new JPanel(new FlowLayout());
		panel_ctpm_nav.setBackground(Color.WHITE);
		panel_ctpm_nav.setPreferredSize(new Dimension(1160, 70));

		// Khi ta muốn đóng JFRAME THÊM SÁCH
		btn_themsachcu = new JButton("Thêm Sách", scaledIconinsert);
		btn_themsachcu.setBackground(Color.PINK);
		btn_themsachcu.setFont(new Font("Arial", BOLD, 15));
		btn_themsachcu.setPreferredSize(new Dimension(200, 60));

		// SỰ KIÊN KHI TA THÊM SÁCH CŨ
		btn_themsachcu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] modelll = new String[phieumuonbus.get_ListSach().size()];
				int a = 0;
				for (SachCT s : phieumuonbus.get_ListSach()) {
					list_sachincombobox.add(s);
					modelll[a] = s.get_TenSach();
					a++;
				}

				for (int i = 0; i < a; i++) {
					String item = modelll[i];
					sach_t.addItem(item);
				}
				panel_ctpm_detail_t.setVisible(true);
				btn_thempmhap.setVisible(false);
				if (btn_themsachmoi != null) {
					btn_themsachmoi.setVisible(false);
				}
			}
		});
		// panel_ctpm_nav.add(btn_themsachmoi);
		panel_ctpm_nav.add(btn_themsachcu);

		// Thêm Chi tiết Tiết Phiếu Mượn
		panel_ctpm_detail_t = new JPanel();
		panel_ctpm_detail_t.setLayout(new FlowLayout());
		panel_ctpm_detail_t.setPreferredSize(new Dimension(1160, 120));/////
		sach_t = new JComboBox(TaoModelSach(phieumuonbus.get_ListSach()));
		label_sach_t = new JLabel("- Sách :");
		label_sach_t.setFont(new Font("Arial", BOLD, 18));
		sach_t.setPreferredSize(new Dimension(300, 30));
		sach_t.setFont(new Font("Arial", BOLD, 15));
		panel_ctpm_detail_t1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_ctpm_detail_t1.add(label_sach_t);
		panel_ctpm_detail_t1.add(sach_t);
		panel_ctpm_detail_t1.setBackground(Color.WHITE);
		panel_ctpm_detail_t1.setPreferredSize(new Dimension(900, 30));/////

		label_solg_t = new JLabel("- Số Lượng Mượn :");
		label_solg_t.setFont(new Font("Arial", BOLD, 18));
		solg_t = new JTextField();
		solg_t.setPreferredSize(new Dimension(300, 30));
		panel_ctpm_detail_t2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_ctpm_detail_t2.add(label_solg_t);
		panel_ctpm_detail_t2.add(solg_t);
		panel_ctpm_detail_t2.setBackground(Color.WHITE);
		panel_ctpm_detail_t2.setPreferredSize(new Dimension(900, 40));/////

		label_title_ctpmdetail_t = new JLabel("Nhập Thêm Sách ");
		label_title_ctpmdetail_t.setFont(new Font("Arial", BOLD, 20));
		label_title_ctpmdetail_t.setForeground(Color.red);
		label_title_ctpmdetail_t.setPreferredSize(new Dimension(1020, 30));
		label_title_ctpmdetail_t.setHorizontalAlignment(SwingConstants.CENTER);
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
					panel_ctpm_detail_t.setVisible(false);
					btn_thempmhap.setVisible(true);
					btn_themsachmoi.setVisible(true);
				}

			}
		});

		JPanel panel_ctpmdetail_t3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_ctpmdetail_t3.add(label_title_ctpmdetail_t);
		panel_ctpmdetail_t3.add(btn_closesach_t);
		panel_ctpmdetail_t3.setBackground(Color.WHITE);////

		JPanel panel_ctpmdetail_t4 = new JPanel();
		panel_ctpmdetail_t4 = new JPanel();
		panel_ctpmdetail_t4.add(panel_ctpm_detail_t1);
		panel_ctpmdetail_t4.add(panel_ctpm_detail_t2);
		panel_ctpmdetail_t4.setBackground(Color.WHITE);
		panel_ctpmdetail_t4.setPreferredSize(new Dimension(990, 100));/////

		btn_themsach_t = new JButton("Thêm");
		btn_themsach_t.setBackground(Color.PINK);
		btn_themsach_t.setFont(new Font("Arial", BOLD, 15));
		btn_themsach_t.setPreferredSize(new Dimension(150, 50));///

		// SỰ KIỆN XẢY RA KHI TA THÊM SỐ LƯỢNG SÁCH ĐÃ CÓ TRONG CỬA HÀNG
		btn_themsach_t.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				objsachold = new SachCT();
				objctpmnew = new CTPhieuMuon();
				int selectedIndex = sach_t.getSelectedIndex();
				if (selectedIndex != -1) {
					String txt_solg = solg_t.getText().trim();
					String regex = "^[1-9]\\d*$";
					if (txt_solg.isEmpty() || !Pattern.matches(regex, txt_solg)) {
						JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng đầy đủ và chính xác !", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					int solgnew = list_sachincombobox.get(selectedIndex).get_SoLuong() - Integer.parseInt(txt_solg);
					if (solgnew <= 0) {
						JOptionPane.showMessageDialog(null, "Số lượng sách không đủ để mượn !",
								"Vui lòng giảm số lượng sách mượn hoặc chọn sách khác",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					objsachold = list_sachincombobox.get(selectedIndex);
					objsachold.set_SoLuong(solgnew);

					objctpmnew.setPhieuMuon(objpmhapmoi.getMaPhieuMuon());
					objctpmnew.setSoLuong(Integer.parseInt(txt_solg));
					objctpmnew.setSach(objsachold);
					// Thêm chi tiêt phiếu mượn
					list_obj.add(objctpmnew);
					// Thêm vào danh sách sửa những sách có trong cửa hàng
					list_sachold.add(objsachold);
					// Thêm dòng mới jtable
					ThemMotDongVoJTableCTPMuon(objctpmnew);
					panel_ctpm_detail_t.setVisible(false);
					btn_thempmhap.setVisible(true);
					btn_themsachmoi.setVisible(true);
					// Sách đã chọn rồi sẽ không đc chọn nữa
					list_sachincombobox.remove(selectedIndex);
					// Xóa danh sách chọn
					solg_t.setText("");
					sach_t.removeAllItems();
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn tên sách mà bạn muốn thêm số lượng mới !",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
				}

			}

		});

		JPanel panel_ctpmdetail_t5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_ctpmdetail_t5.add(panel_ctpmdetail_t4);
		panel_ctpmdetail_t5.add(btn_themsach_t);
		panel_ctpmdetail_t5.setBackground(Color.WHITE);

		panel_ctpm_detail_t = new JPanel(new BorderLayout());
		panel_ctpm_detail_t.setPreferredSize(new Dimension(1160, 120));
		panel_ctpm_detail_t.setBackground(Color.WHITE);
		panel_ctpm_detail_t.add(panel_ctpmdetail_t3, BorderLayout.NORTH);
		panel_ctpm_detail_t.add(panel_ctpmdetail_t5, BorderLayout.CENTER);
		panel_ctpm_detail_t.setVisible(false);

		// Table
		String columnNames[] = { "Mã Phiếu", "Mã Sách", "Tên Sách", "Số Lượng" };
		modelthemctpm = new DefaultTableModel(columnNames, 0);
		table_ctpm_t = new JTable(modelthemctpm);
		TableColumnModel columnModel = table_ctpm_t.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(200); // Mã
		columnModel.getColumn(1).setPreferredWidth(200); // Tên
		columnModel.getColumn(2).setPreferredWidth(200);
		columnModel.getColumn(3).setPreferredWidth(200);
		table_ctpm_t.setDefaultEditor(Object.class, null);

		table_ctpm_t.setFocusable(false);
		table_ctpm_t.setIntercellSpacing(new Dimension(0, 0));
		table_ctpm_t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table_ctpm_t.setRowHeight(30);
		table_ctpm_t.setShowVerticalLines(true);
		table_ctpm_t.getTableHeader().setOpaque(false);
		table_ctpm_t.setFillsViewportHeight(true);
		table_ctpm_t.getTableHeader().setBackground(Color.PINK);
		table_ctpm_t.getTableHeader().setForeground(Color.WHITE);
		table_ctpm_t.setSelectionBackground(new Color(52, 152, 219));

		panel_ctpm_t = new JPanel(new CardLayout());
		panel_ctpm_t.setPreferredSize(new Dimension(1160, 200));
		panel_ctpm_t.setBackground(Color.WHITE);

		// Thêm JTable vào JScrollPane
		JScrollPane scrollPane = new JScrollPane(table_ctpm_t);
		panel_ctpm_t.add(scrollPane);

		// Thêm Phiếu Mượn
		JPanel panel_thempmhap = new JPanel();
		panel_thempmhap.setBackground(Color.WHITE);
		panel_thempmhap.setPreferredSize(new Dimension(1160, 60));

		btn_thempmhap = new JButton("Thêm Phiếu Mượn");
		btn_thempmhap.setBackground(Color.PINK);
		btn_thempmhap.setFont(new Font("Arial", BOLD, 15));
		btn_thempmhap.setPreferredSize(new Dimension(300, 50));
		panel_thempmhap.add(btn_thempmhap);

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
				for (NhanVien nv : phieumuonbus.get_ListNhanVien()) {
					if (nv.getMaNV().equals(ma)) {
						ten_nv.setText(nv.getHoTen());
						objpmhapmoi.setMaNhanVien(nv);
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
		madg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String MA = madg.getSelectedItem().toString();
				if (MA.isEmpty() || KiemTraMa4(MA) == false) {
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống ô này !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				int ktra2 = 0;
				for (DocGia dg : phieumuonbus.getList_docgia()) {
					if (dg.getMaDG().equals(MA)) {
						ten_dg.setText(dg.getHoTen());
						objpmhapmoi.setDocGia(dg);
						ktra2 = 1;
						break;
					}
				}

				if (ktra2 == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã độc giả của người mượn !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			}

		});
		btn_thempmhap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ma = manv.getSelectedItem().toString();
				String MA = madg.getSelectedItem().toString();
				if (ma.isEmpty() || KiemTraMa4(ma) == false) {
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống ô này !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (MA.isEmpty() || KiemTraMa4(MA) == false) {
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống ô này !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				int ktra = 0;
				for (NhanVien nv : phieumuonbus.get_ListNhanVien()) {
					if (nv.getMaNV().equals(ma)) {
						ten_nv.setText(nv.getHoTen());
						objpmhapmoi.setMaNhanVien(nv);
						ktra = 1;
						break;
					}
				}

				if (ktra == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã nhân viên của bạn !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				int ktra2 = 0;
				for (DocGia dg : phieumuonbus.getList_docgia()) {
					if (dg.getMaDG().equals(MA)) {
						ten_dg.setText(dg.getHoTen());
						objpmhapmoi.setDocGia(dg);
						ktra2 = 1;
						break;
					}
				}

				if (ktra2 == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mã độc giả của người mượn !", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				double Tong = 0.0;
				for (CTPhieuMuon obj : list_obj) {
					Tong += obj.getSoLuong();
				}
				objpmhapmoi.setSoLuong((int) Tong);

				objpmhapmoi.setListCTiet(list_obj);
				int ThemMotPhieuMuon = phieumuondao.ThemMotPhieuMuon(objpmhapmoi);
				if (ThemMotPhieuMuon > 0) {
					phieumuonbus.ThemPhieuMuonMoi(objpmhapmoi);
					// Nhớ thay đổi bảng

					phieumuondao.ThemMotChiTietPhieuMuon(list_obj);
					try {
						phieumuondao.Edit_NhieuSach(list_sachold);
					} catch (SQLException ex) {
						Logger.getLogger(PhieuMuonGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
					try {
						phieumuondao.Insert_NhieuSach(list_sachnew);
					} catch (SQLException ex) {
						Logger.getLogger(PhieuMuonGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
					phieumuonbus.set_ListSach(phieumuondao.getListSACH());
					ThemDataVaoJTablePhieuMuon(model, phieumuonbus.get_ListPhieuMuon());
					frame_pmhap.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Không thể thêm phiếu mượn mới!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		});

		main.setLayout(new FlowLayout());
		main.setBackground(Color.WHITE);
		main.setPreferredSize(new Dimension(1160, 670));
		main.add(panel_title_t);
		main.add(panel_pmhap_t1);
		main.add(panel_pmhap_t2);
		main.add(panel_pmhap_t3);
		main.add(panel_pmhap_manv);
		main.add(panel_pmhap_tennv);
		main.add(panel_pmhap_madg);
		main.add(panel_pmhap_tendg);

		main.add(panel_ctpm_nav);

		main.add(panel_ctpm_detail_t);

		main.add(panel_ctpm_t);

		main.add(panel_thempmhap);
		return main;

	}

	public String[] TaoModelLoaiSach(ArrayList<LoaiSach> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			modelll[i] = obj.get(i).getTenLoai(); // Gán tên nhà xuất bản vào mảng
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

	public String[] TaoModelNhanVien(ArrayList<NhanVien> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			modelll[i] = obj.get(i).getMaNV(); // Gán tên nhà xuất bản vào mảng
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

	public String[] TaoModelSach(ArrayList<SachCT> obj) {
		String[] modelll = new String[obj.size()]; // Khởi tạo mảng với độ dài là số lượng phần tử trong danh sách
		for (int i = 0; i < obj.size(); i++) {
			modelll[i] = obj.get(i).get_TenSach(); // Gán tên nhà xuất bản vào mảng
		}
		return modelll;
	}

	// Thêm dữ liệu vô jtable ctpm
	public void ThemMotDongVoJTableCTPMuon(CTPhieuMuon obj) {
		// String columnNames[]={"Mã Phiếu","Mã Sách","Tên Sách","Số Lượng","Thành
		// Tiền"};
		modelthemctpm.addRow(new Object[] { obj.getPhieuMuon(), obj.getSach().get_MaSach(), obj.getSach().get_TenSach(),
				obj.getSoLuong(),

		});

	}

}
