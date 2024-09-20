
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
public class ThongKeView extends JPanel implements ActionListener {
	private JLabel label_title;
	private JPanel panel_title;

	private JPanel panel_nav;
	private JButton insert;
	private JButton delete;
	private JButton home;
	String[] thongke = { "Chọn Yêu Cầu Thống Kê", "Thống Kê Theo Ngày ", "Thống Kê Theo Khoảng Thời Gian ",
			"Thống Kê Theo Tháng Năm" };
	private JComboBox combobox;
	private JButton detail;
	private JComboBox combobox1;
	String[] thongke1 = { "Thống Kê Mượn Sách", "Thống Kê Nhập Sách" };
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

	private JPanel ms;
	private JLabel ms_t, ms_tt;

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

	public void ThemDataVaoJTablePhieuMuon(DefaultTableModel model, ArrayList<CTPhieuMuon> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (CTPhieuMuon pm : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { pm.getSach().get_MaSach(), pm.getSach().get_TenSach(), pm.getTongSL(), // Thêm
																												// tổng
																												// số
																												// lượng
																												// mượn
					pm.getSoLanMuon()// Thêm số lần mượn
			});
		}
	}

	private Integer select_thongke;

	public ThongKeView() {
		PhieuMuonGUI();
	}

	public void PhieuMuonGUI() {
		phieumuonbus.getDanhSachList();
		JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.setPreferredSize(new Dimension(1160, 670));

		// Title
		label_title = new JLabel("Thống Kê Mượn Sách");
		label_title.setFont(new Font("Arial", BOLD, 50));
		label_title.setForeground(Color.WHITE);
		panel_title = new JPanel(new FlowLayout());
		panel_title.setBackground(Color.PINK);
		panel_title.setPreferredSize(new Dimension(1160, 60));
		panel_title.add(label_title);

		// TÌM KIẾM
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/search-icon.png"));
		// Lấy hình ảnh từ ImageIcon
		Image originalImage = icon.getImage();
		// Thay đổi kích thước của hình ảnh
		Image scaledImage = originalImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		// Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		// Tạo một JButton với biểu tượng đã thay đổi kích thước

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
		combobox1 = new JComboBox(thongke1);
		combobox1.setPreferredSize(new Dimension(260, 60));
		combobox1.setFont(new Font("Arial", BOLD, 15));
		combobox1.setForeground(Color.WHITE);
		combobox1.setBackground(Color.PINK);
		combobox1.addActionListener(this);
		panel_nav.add(combobox1);
		panel_nav.add(home);
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
		String[] columnNames = { "Mã Sách", "Tên Sách", "Số lượng", "Số lần mượn" };
		model = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTablePhieuMuon(model, phieumuonbus.getList_ctphieumuon());
		table_pmhap = new JTable(model);
		TableColumnModel columnModel = table_pmhap.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(200); // Mã
		columnModel.getColumn(1).setPreferredWidth(200); // Tên
		columnModel.getColumn(2).setPreferredWidth(200);
		columnModel.getColumn(3).setPreferredWidth(200);
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
		ms = new JPanel();
		ms.setPreferredSize(new Dimension(1080, 200));
		ms.setBackground(Color.white);
		ms.setLayout(new FlowLayout(0, 0, 20));
		ms_t = new JLabel("Sách Được Mượn Nhiều Nhất: ");
		ms_t.setFont(new Font("Arial", Font.BOLD, 20));
		ms_t.setPreferredSize(new Dimension(295, 50));
		ms.add(ms_t);
		ms_tt = new JLabel();
		ms_tt.setFont(new Font("Arial", Font.BOLD, 20));
		ms_tt.setPreferredSize(new Dimension(300, 50));
		ms.add(ms_tt);
		int max = 0;
		for (CTPhieuMuon ct : phieumuonbus.getList_ctphieumuon()) {
			if (ct.getSoLanMuon() > max) {
				max = ct.getSoLanMuon();
				ms_tt.setText(ct.getSach().get_TenSach());
			}
		}

		// Thêm JTable vào JScrollPane
		JScrollPane scrollPane = new JScrollPane(table_pmhap);
		panel_table_pmhap.add(scrollPane);

		// Nếu người dùng chọn nút HIỂN THỊ Chi Tiết Phiếu Mượn

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

							ArrayList<CTPhieuMuon> obj = new ArrayList<>();
							obj = phieumuondao.ThongKeTheoNgay1(selectedDateSql);
							ThemDataVaoJTablePhieuMuon(model, obj);
							int max = 0;
							for (CTPhieuMuon ct : obj) {
								if (ct.getSoLanMuon() > max) {
									max = ct.getSoLanMuon();
									ms_tt.setText(ct.getSach().get_TenSach());
								}
							}
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
						ArrayList<CTPhieuMuon> obj = new ArrayList<>();
						obj = phieumuondao.ThongKeTheoKhoangThoiGian1(startDateSql, endDateSql);
						ThemDataVaoJTablePhieuMuon(model, obj);
						int max = 0;
						for (CTPhieuMuon ct : obj) {
							if (ct.getSoLanMuon() > max) {
								max = ct.getSoLanMuon();
								ms_tt.setText(ct.getSach().get_TenSach());
							}
						}
						select_thongke = 0;
						panel_thongke.setVisible(false);
						break;
					}
					case 3: {
						int select_month = Month.getSelectedIndex();
						int year_selected = (int) year.getValue();
						int month = select_month + 1;
						ArrayList<CTPhieuMuon> obj = new ArrayList<>();
						obj = phieumuondao.ThongKeTheoNam_Thang1(year_selected, month);
						ThemDataVaoJTablePhieuMuon(model, obj);
						int max = 0;
						for (CTPhieuMuon ct : obj) {
							if (ct.getSoLanMuon() > max) {
								max = ct.getSoLanMuon();
								ms_tt.setText(ct.getSach().get_TenSach());
							}
						}
						select_thongke = 0;
						panel_thongke.setVisible(false);

						break;
					}

				}
			}
		});
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_thongke.setVisible(false);
				ThemDataVaoJTablePhieuMuon(model, phieumuonbus.getList_ctphieumuon());
			}
		});

		panel_thongke.add(panel_date);
		panel_thongke.add(panel_date1);
		panel_thongke.add(panel_date2);
		panel_thongke.add(panel_date3);
		this.setLayout(new FlowLayout(0, 0, 0));
		this.setBackground(Color.WHITE);
		this.add(panel_title);
		this.add(panel_nav);
		this.add(panel_thongke);
		panel_thongke.setVisible(false);
		this.add(panel_table_pmhap);
		this.add(ms);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedOption = combobox1.getSelectedIndex();
		switch (selectedOption) {
			case 0:
				ThongKeView p = new ThongKeView();
				p.setPreferredSize(new Dimension(1160, 730));
				this.removeAll();
				this.add(p);
				this.repaint();
				this.validate();
				break;

			case 1:
				ThongKeView1 p1 = new ThongKeView1();
				p1.setPreferredSize(new Dimension(1160, 730));
				this.removeAll();
				this.add(p1);
				this.repaint();
				this.validate();

				break;
		}
	}

}
