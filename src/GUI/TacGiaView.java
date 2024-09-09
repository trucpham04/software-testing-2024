/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.TacGiaBUS;
import DTO.TacGia;
import DATA.TacgiaDAO;
import com.mysql.cj.result.Row;

import javax.swing.*;

//import org.apache.poi.ss.usermodel.*;
import java.awt.*;
import static java.awt.Font.BOLD;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */

public class TacGiaView extends JPanel {
	private final TacGiaBUS tacgiabus = new TacGiaBUS();
	private final TacgiaDAO tacgiadao = new TacgiaDAO();
	private JLabel label_Title;
	private JPanel panel_Title;

	private JComboBox combobox;
	private JTextField txt_Search;
	private JButton btn_Searchsubmit;
	private JPanel panel_Search;

	private JPanel panel_main_form;
	private JPanel panel_form;
	private JLabel label_form;
	private JLabel label_MATG;
	private JLabel MA_TG;
	private JLabel label_TenTG;
	private JTextField txt_TenTG;
	private JLabel label_NamSinh;
	private JTextField txt_NamSinh;
	private JLabel label_Phai;
	private ButtonGroup gender;
	private JRadioButton male;
	private JRadioButton female;
	private JPanel panel_gender;
	private JButton reset;
	private JButton peform;

	private JPanel panel_Table;
	private JTable table;
	private DefaultTableModel model;

	private JPanel panel_nav;
	private JButton insert;
	private JButton delete;
	private JButton edit;
	private JButton excel;
	private JButton import_excel;
	private JButton home;

	private boolean isButtonInsertSelected = false;
	private boolean isButtonEditSelected = false;
	private boolean isButtonDeleteSelected = false;
	String[] columnNames = { "Mã", "Tên", "Phái", "Năm Sinh", "Trạng Thái" };

	public void Xoa() {
		label_form.setText("Xóa Thông Tin Tác Gỉả");
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa thông tin  tác giả này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String matgia = MA_TG.getText();
			String tentg = txt_TenTG.getText();
			String namsinh = txt_NamSinh.getText();

			String gioitinh;
			if (male.isSelected() == true)
				gioitinh = "Nam";
			else
				gioitinh = "Nữ";

			TacGia newtacgia = new TacGia(matgia, tentg, gioitinh, namsinh, 1);
			int row = tacgiadao.XoaTacGia_Data(newtacgia);
			if (row > 0) {
				tacgiabus.XoaTGia_Array(newtacgia);
				ThemDataVaoJTable(model, tacgiabus.getList_TacGia());
				table.setModel(model);
				MA_TG.setText("");
				txt_TenTG.setText("");
				txt_NamSinh.setText("");
				txt_TenTG.setFocusable(false);
				txt_NamSinh.setFocusable(false);
				male.setEnabled(false);
				female.setEnabled(false);
				reset.setVisible(false);
				peform.setVisible(false);
				JOptionPane.showMessageDialog(null, "Đã xóa thành công !");

			} else {
				JOptionPane.showMessageDialog(null, "Đã có lỗi xảy ra.");

			}

		} else {
			// Thực hiện thao tác khi người dùng nhấn Cancel hoặc đóng hộp thoại
			// Đặt các đoạn mã của bạn ở đây
			MA_TG.setText("");
			txt_TenTG.setText("");
			txt_NamSinh.setText("");
			txt_TenTG.setFocusable(false);
			txt_NamSinh.setFocusable(false);
			male.setEnabled(false);
			female.setEnabled(false);
			reset.setVisible(false);
			peform.setVisible(false);

		}
		table.clearSelection();
		isButtonDeleteSelected = false;
		System.out.printf("%s", isButtonDeleteSelected);

	}

	public void Sua() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có sửa thông tin  tác giả này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String matgia = MA_TG.getText();
			String tentg = txt_TenTG.getText();
			String namsinh = txt_NamSinh.getText();
			if (tentg.isEmpty() || namsinh.isEmpty()
					|| (male.isSelected() == false && female.isSelected() == false || KtrNamSinh(namsinh) == false)) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền chính xác và đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				String gioitinh;
				if (male.isSelected() == true)
					gioitinh = "Nam";
				else
					gioitinh = "Nữ";

				TacGia newtacgia = new TacGia(matgia, tentg, gioitinh, namsinh, 1);
				int row = tacgiadao.EditTacgia_Data(newtacgia);
				if (row > 0) {
					tacgiabus.EditTGia_Array(newtacgia);
					ThemDataVaoJTable(model, tacgiabus.getList_TacGia());
					table.setModel(model);
					MA_TG.setText("");
					txt_TenTG.setText("");
					txt_NamSinh.setText("");
					txt_TenTG.setFocusable(false);
					txt_NamSinh.setFocusable(false);
					male.setEnabled(false);
					female.setEnabled(false);
					reset.setVisible(false);
					peform.setVisible(false);
					JOptionPane.showMessageDialog(null, "Đã sửa thành công !");

				} else {
					JOptionPane.showMessageDialog(null, "Đã có lỗi xảy ra.");

				}
			}

		} else {
			// Thực hiện thao tác khi người dùng nhấn Cancel hoặc đóng hộp thoại
			// Đặt các đoạn mã của bạn ở đây
			MA_TG.setText("");
			txt_TenTG.setText("");
			txt_NamSinh.setText("");
			txt_TenTG.setFocusable(false);
			txt_NamSinh.setFocusable(false);
			male.setEnabled(false);
			female.setEnabled(false);
			reset.setVisible(false);
			peform.setVisible(false);

		}
		isButtonEditSelected = false;
		System.out.printf("%s", isButtonEditSelected);
		table.clearSelection();

	}

	public void Them() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm tác giả này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			// Thực hiện thao tác khi người dùng nhấn OK
			// Đặt các đoạn mã của bạn ở đây
			String matgia = MA_TG.getText();
			String tentg = txt_TenTG.getText();
			String namsinh = txt_NamSinh.getText();
			if (tentg.isEmpty() || namsinh.isEmpty()
					|| (male.isSelected() == false && female.isSelected() == false || KtrNamSinh(namsinh) == false)) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ và chính xác thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				String gioitinh;
				if (male.isSelected() == true)
					gioitinh = "Nam";
				else
					gioitinh = "Nữ";

				TacGia newtacgia = new TacGia(matgia, tentg, gioitinh, namsinh, 1);
				int a = tacgiadao.insertTacgia_Data(newtacgia);
				if (a > 0) {
					tacgiabus.ThemTacGia_Arr(newtacgia);
					ThemDataVaoJTable(model, tacgiabus.getList_TacGia());
					table.setModel(model);
					MA_TG.setText("");
					txt_TenTG.setText("");
					txt_NamSinh.setText("");
					txt_TenTG.setFocusable(false);
					txt_NamSinh.setFocusable(false);
					male.setEnabled(false);
					female.setEnabled(false);
					reset.setVisible(false);
					peform.setVisible(false);
					JOptionPane.showMessageDialog(null, "Đã thêm thành công !");

				} else {
					JOptionPane.showMessageDialog(null, "Đã có lỗi xảy ra.");
				}
			}
		} else {
			// Thực hiện thao tác khi người dùng nhấn Cancel hoặc đóng hộp thoại
			// Đặt các đoạn mã của bạn ở đây
			MA_TG.setText("");
			txt_TenTG.setText("");
			txt_NamSinh.setText("");
			txt_TenTG.setFocusable(false);
			txt_NamSinh.setFocusable(false);
			male.setEnabled(false);
			female.setEnabled(false);
			reset.setVisible(false);
			peform.setVisible(false);
		}
		isButtonInsertSelected = false;
		System.out.printf("%s", isButtonInsertSelected);

	}

//Kiểm tra xem chuỗi nhập vào có phải là mã tác giả hay không
	public boolean KiemTraMa(String input) {
		// Kiểm tra xem chuỗi có chứa 4 chữ số và không chứa ký tự không phải số không
		return input.matches("\\d{4}");
	}

	public void ThemDataVaoJTable(DefaultTableModel model, ArrayList<TacGia> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (TacGia tacGia : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { tacGia.getMaTG(), tacGia.getTenTG(), tacGia.getGioiTinh(), tacGia.getNamSinh(),
					tacGia.getTrangThai() });
		}
	}

	public boolean KtrNamSinh(String nam) {
		Pattern pattern = Pattern.compile("^[1-9]\\d{3}$");
		Matcher matcher = pattern.matcher(nam);
		return matcher.matches();
	}

	public TacGiaView() {
		TacGiaGUI();
	}

	public void TacGiaGUI() {
		// Lấy dữ liệu đổ xuống arraylist
		tacgiabus.DanhSachlist();

		// Title
		label_Title = new JLabel("Tác Giả");
		label_Title.setFont(new Font("Arial", BOLD, 50));
		label_Title.setForeground(Color.WHITE);
		panel_Title = new JPanel(new FlowLayout());
		panel_Title.setBackground(Color.PINK);
		panel_Title.setPreferredSize(new Dimension(1160, 60));
		panel_Title.add(label_Title);

		String[] combobox_item = { "Tìm Kiếm Theo ID", "Tìm Kiếm Theo Tên" };
		combobox = new JComboBox(combobox_item);
		combobox.setPreferredSize(new Dimension(260, 40));
		combobox.setFont(new Font("Arial", BOLD, 15));
		combobox.setForeground(Color.WHITE);
		combobox.setBackground(Color.PINK);
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
		btn_Searchsubmit = new JButton("Tìm KIếm", scaledIcon);
		btn_Searchsubmit.setPreferredSize(new Dimension(200, 40));
		btn_Searchsubmit.setForeground(Color.WHITE);
		btn_Searchsubmit.setBackground(Color.PINK);
		btn_Searchsubmit.setFont(new Font("Arial", BOLD, 15));

		panel_Search = new JPanel(new FlowLayout());
		panel_Search.setPreferredSize(new Dimension(1160, 50));
		panel_Search.setBackground(Color.WHITE);
		panel_Search.add(combobox);
		panel_Search.add(txt_Search);
		panel_Search.add(btn_Searchsubmit);

		// Nav
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

		ImageIcon iconprint = new ImageIcon("src/img/Dakirby309-Simply-Styled-Microsoft-Excel-2013.256.png");
		// Lấy hình ảnh từ ImageIcon
		Image originalImageprint = iconprint.getImage();
		// Thay đổi kích thước của hình ảnh
		Image scaledImageprint = originalImageprint.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		// Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
		ImageIcon scaledIconprint = new ImageIcon(scaledImageprint);
		import_excel = new JButton("Import Excel", scaledIconprint);
		import_excel.setBackground(Color.PINK);
		import_excel.setFont(new Font("Arial", BOLD, 15));
		import_excel.setPreferredSize(new Dimension(200, 60));

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

		ImageIcon iconedit = new ImageIcon("src/img/Pencil-4-icon.png");
		// Lấy hình ảnh từ ImageIcon
		Image originalImageedit = iconedit.getImage();
		// Thay đổi kích thước của hình ảnh
		Image scaledImageedit = originalImageedit.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		// Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
		ImageIcon scaledIconedit = new ImageIcon(scaledImageedit);
		edit = new JButton("Sửa", scaledIconedit);
		edit.setBackground(Color.PINK);
		edit.setFont(new Font("Arial", BOLD, 15));
		edit.setPreferredSize(new Dimension(150, 60));

		ImageIcon iconexcel = new ImageIcon("src/img/Microsoft-Excel-icon.png");
		// Lấy hình ảnh từ ImageIcon
		Image originalImageexcel = iconexcel.getImage();
		// Thay đổi kích thước của hình ảnh
		Image scaledImageexcel = originalImageexcel.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		// Tạo một ImageIcon từ hình ảnh đã thay đổi kích thước
		ImageIcon scaledIconexcel = new ImageIcon(scaledImageexcel);
		excel = new JButton("Export Excel", scaledIconexcel);
		excel.setBackground(Color.PINK);
		excel.setFont(new Font("Arial", BOLD, 15));
		excel.setPreferredSize(new Dimension(200, 60));

		panel_nav.add(home);
		panel_nav.add(insert);
		panel_nav.add(delete);
		panel_nav.add(edit);
		panel_nav.add(excel);
		panel_nav.add(import_excel);

		model = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTable(model, tacgiabus.getList_TacGia());
		table = new JTable(model);
		// Set TableCellEditor mặc định không cho phép chỉnh sửa
		table.setDefaultEditor(Object.class, null);

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); // Mã
		columnModel.getColumn(1).setPreferredWidth(500); // Tên
		columnModel.getColumn(2).setPreferredWidth(100); // Phái
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(3).setPreferredWidth(60);

		table.setFocusable(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.getTableHeader().setFont(new Font("Arial", BOLD, 20));
		table.setRowHeight(30);
		table.setShowVerticalLines(true);
		table.getTableHeader().setOpaque(false);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setBackground(Color.pink);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setSelectionBackground(new Color(52, 152, 219));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);

		panel_Table = new JPanel(new CardLayout());
		panel_Table.setPreferredSize(new Dimension(1160, 240));
		panel_Table.setBackground(Color.WHITE);
		panel_Table.add(new JScrollPane(table));

		label_form = new JLabel("Hiển Thị Thông Tin Tác Giả");
		label_form.setFont(new Font("Arial", BOLD, 20));
		JPanel panel_label_form = new JPanel();
		panel_label_form.add(label_form);
		panel_label_form.setBackground(Color.WHITE);
		label_MATG = new JLabel("Mã Tác Giả :");
		label_MATG.setFont(new Font("Arial", BOLD, 19));
		MA_TG = new JLabel("");
		MA_TG.setFont(new Font("Arial", Font.ITALIC, 19));
		label_TenTG = new JLabel("Tên Tác Giả :");
		label_TenTG.setFont(new Font("Arial", BOLD, 19));
		label_NamSinh = new JLabel("Năm Sinh :");
		label_NamSinh.setFont(new Font("Arial", BOLD, 19));
		label_Phai = new JLabel("Giới Tính :");
		label_Phai.setFont(new Font("Arial", BOLD, 19));
		txt_TenTG = new JTextField();
		txt_TenTG.setPreferredSize(new Dimension(250, 40));
		txt_TenTG.setFont(new Font("Arial", BOLD, 19));
		txt_TenTG.setFocusable(false);
		txt_NamSinh = new JTextField();
		txt_NamSinh.setPreferredSize(new Dimension(275, 40));
		txt_NamSinh.setFont(new Font("Arial", BOLD, 19));
		txt_NamSinh.setFocusable(false);
		male = new JRadioButton("Nam");
		male.setBackground(Color.WHITE);
		male.setFont(new Font("Arial", BOLD, 19));
		male.setEnabled(false);
		female = new JRadioButton("Nữ");
		female.setFont(new Font("Arial", BOLD, 19));
		female.setBackground(Color.WHITE);
		female.setEnabled(false);
		gender = new ButtonGroup();
		gender.add(male);
		gender.add(female);
		panel_form = new JPanel(new GridLayout(2, 2));

		JPanel panel_MA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_MA.add(label_MATG);
		panel_MA.add(MA_TG);
		panel_MA.setPreferredSize(new Dimension(1160, 50));
		panel_MA.setBackground(Color.WHITE);

		JPanel panel_TEN = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_TEN.add(label_TenTG);
		panel_TEN.add(txt_TenTG);
		panel_TEN.setPreferredSize(new Dimension(1160, 50));
		panel_TEN.setBackground(Color.WHITE);

		JPanel panel_NSinh = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_NSinh.add(label_NamSinh);
		panel_NSinh.add(txt_NamSinh);
		panel_NSinh.setPreferredSize(new Dimension(1160, 50));
		panel_NSinh.setBackground(Color.WHITE);

		reset = new JButton("Clear");
		reset.setPreferredSize(new Dimension(150, 40));
		reset.setFont(new Font("Arial", BOLD, 19));
		reset.setBackground(Color.PINK);
		reset.setVisible(false);
		peform = new JButton("Thực Hiện");
		peform.setPreferredSize(new Dimension(150, 40));
		peform.setBackground(Color.PINK);
		peform.setFont(new Font("Arial", BOLD, 19));
		peform.setVisible(false);
		JPanel panel_button = new JPanel(new FlowLayout());
		panel_button.setBackground(Color.WHITE);
		panel_button.setPreferredSize(new Dimension(1160, 50));
		panel_button.add(reset);
		panel_button.add(peform);

		panel_gender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_gender.add(label_Phai);
		panel_gender.add(male);
		panel_gender.add(female);
		panel_gender.setPreferredSize(new Dimension(1160, 50));
		panel_gender.setBackground(Color.WHITE);

		panel_form.add(panel_MA);
		panel_form.add(panel_TEN);
		panel_form.add(panel_gender);
		panel_form.add(panel_NSinh);
		panel_form.setPreferredSize(new Dimension(1160, 120));
		panel_form.setBackground(Color.WHITE);

		panel_main_form = new JPanel(new BorderLayout());
		panel_main_form.setBackground(Color.WHITE);
		panel_main_form.setPreferredSize(new Dimension(1160, 190));
		panel_main_form.add(panel_label_form, BorderLayout.NORTH);
		panel_main_form.add(panel_form, BorderLayout.CENTER);
		panel_main_form.add(panel_button, BorderLayout.SOUTH);
		panel_main_form.setBorder(new LineBorder(Color.PINK, 4, true));

		this.setLayout(new FlowLayout(0,0,0));
		this.setPreferredSize(new Dimension(1180, 730));
		this.setBackground(Color.WHITE);
		this.add(panel_Title);
		this.add(panel_main_form);
		this.add(panel_Search);
		this.add(panel_nav);
		this.add(panel_Table);

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
		edit.addMouseListener(mouseListener);
		excel.addMouseListener(mouseListener);
		reset.addMouseListener(mouseListener);
		peform.addMouseListener(mouseListener);
		btn_Searchsubmit.addMouseListener(mouseListener);

		// Tạo một Action Event để lắng nghe sụ kiện tìm kiếm
		btn_Searchsubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = combobox.getSelectedIndex();
				if (selectedIndex == 0) {
					// Tìm kiếm theo id
					String userInput = txt_Search.getText();
					if (userInput.isEmpty())
						JOptionPane.showMessageDialog(null, "Hãy nhập mã tác giả mà bạn muốn tìm kiếm !", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					else {
						if (KiemTraMa(userInput) == true) {
							ArrayList<TacGia> tacgiatrung = tacgiabus.Tim_Theo_ID(userInput);
							// Danh sách rỗng
							if (tacgiatrung.isEmpty() == true)
								JOptionPane.showMessageDialog(null,
										"Không tìm thấy tác giả nào có mã giống với mã mà bạn đang tìm kiếm.");
							else {
								ThemDataVaoJTable(model, tacgiatrung);
								table.setModel(model);
							}
							txt_Search.setText("");

						} else {
							JOptionPane.showMessageDialog(null, "Mã bạn nhập không đúng !", "Cảnh báo",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				} else {
					// Tìm kiếm theo tên sản phẩm
					// Tìm kiếm theo id
					String userInput = txt_Search.getText();
					if (userInput.isEmpty())
						JOptionPane.showMessageDialog(null, "Hãy nhập tên  tác giả mà bạn muốn tìm kiếm !", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					else {
						ArrayList<TacGia> tacgiatrung = tacgiabus.Tim_Theo_Ten(userInput);
						// Danh sách rỗng
						if (tacgiatrung.isEmpty() == true)
							JOptionPane.showMessageDialog(null,
									"Không tìm thấy tác giả nào có tên giống với tên mà bạn đang tìm kiếm.");
						else {
							ThemDataVaoJTable(model, tacgiatrung);
							table.setModel(model);
						}
						txt_Search.setText("");
					}
				}
			}

		});

		// Tạo sự kiện home
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MA_TG.setText("");
				txt_TenTG.setText("");
				txt_NamSinh.setText("");
				txt_TenTG.setFocusable(false);
				txt_NamSinh.setFocusable(false);
				male.setEnabled(false);
				female.setEnabled(false);
				reset.setVisible(false);
				peform.setVisible(false);
				ThemDataVaoJTable(model, tacgiabus.getList_TacGia());
				table.setModel(model);
			}
		});

		// Thêm tác giả
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label_form.setText("Thêm Tác Gỉả");
				txt_TenTG.setText("");
				txt_NamSinh.setText("");
				txt_TenTG.setFocusable(true);
				txt_NamSinh.setFocusable(true);
				male.setEnabled(true);
				female.setEnabled(true);
				reset.setVisible(true);
				peform.setVisible(true);
				// Tạo mã tác giả duy nhất
				String matgia = tacgiabus.TaoMaTGia_DuyNhat();
				MA_TG.setText(matgia);
				isButtonInsertSelected = true;
			}
		});

		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn tiếp tục hành động này không?",
						"Xác nhận", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.CANCEL_OPTION) {
					label_form = new JLabel("Hiển Thị Thông Tin Tác Giả");
					MA_TG.setText("");
					txt_TenTG.setText("");
					txt_NamSinh.setText("");
					txt_TenTG.setFocusable(false);
					txt_NamSinh.setFocusable(false);
					male.setEnabled(false);
					female.setEnabled(false);
					reset.setVisible(false);
					peform.setVisible(false);
					table.clearSelection();
				}
			}
		});

		// Hiển thị thông tin sản phẩm
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// getValueIsAdjusting() trả về true nếu sự kiện được kích hoạt là kết quả
				if (e.getValueIsAdjusting()) {
					reset.setVisible(true);
					peform.setVisible(false);

					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
//                            isRowSelected = true;
						// Lấy dữ liệu của dòng được chọn
						Object Ma = table.getValueAt(selectedRow, 0);
						Object Ten = table.getValueAt(selectedRow, 1);
						Object Phai = table.getValueAt(selectedRow, 2);
						Object NamSinh = table.getValueAt(selectedRow, 3);
						label_form = new JLabel("Hiển Thị Thông Tin Tác Giả");
						MA_TG.setText((String) Ma);
						txt_TenTG.setText((String) Ten);
						txt_NamSinh.setText((String) NamSinh);
						txt_TenTG.setFocusable(false);
						txt_NamSinh.setFocusable(false);
						if (Phai.equals("Nữ") == true) {
							female.setSelected(true);
							female.setEnabled(false);
							male.setEnabled(false);
						} else {
							male.setSelected(true);
							female.setEnabled(false);
							male.setEnabled(false);
						}
					} else {

					}
				}
			}
		});

		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					// Không có dòng nào được chọn
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một tác giả!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					label_form.setText("Sửa Thông Tin Tác Gỉả");
					peform.setVisible(true);
					txt_TenTG.setFocusable(true);
					txt_NamSinh.setFocusable(true);
					female.setEnabled(true);
					male.setEnabled(true);
					isButtonEditSelected = true;
				}
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					// Không có dòng nào được chọn
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một tác giả!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					peform.setVisible(true);
					isButtonDeleteSelected = true;
				}
			}
		});

		peform.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isButtonInsertSelected == true) {
					Them();
				}
				if (isButtonEditSelected == true) {
					Sua();
				}
				if (isButtonDeleteSelected == true) {
					Xoa();
				}

			}

		});

		excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Tạo một workbook mới (tập tin Excel)
				XSSFWorkbook workbook = new XSSFWorkbook();
				// Tạo một trang trong workbook
				Sheet sheet = workbook.createSheet("Sheet1");
				// Tạo một hộp thoại lưu file
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Chọn vị trí lưu cho tệp Excel của bạn");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
				fileChooser.setFileFilter(filter);
				int userSelection = fileChooser.showSaveDialog(null);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					String filePath = fileToSave.getAbsolutePath();
					// Đảm bảo rằng phần mở rộng là .xlsx
					if (!filePath.toLowerCase().endsWith(".xlsx")) {
						filePath += ".xlsx";
					}
					// Thêm nội dung

					for (int i = 0; i < table.getRowCount(); i++) {
						// Tạo một hàng
						org.apache.poi.ss.usermodel.Row row = sheet.createRow(i + 1);
						for (int j = 0; j < table.getColumnCount(); j++) {
							Object value = table.getValueAt(i, j);
							if (value != null) {
								Cell cell = row.createCell(j); // Tạo một ô mới trong dòng
								cell.setCellValue(value.toString()); // Đặt giá trị cho ô
							}

						}

					}

					try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
						workbook.write(outputStream);
						System.out.println("Tạo tệp Excel thành công!");
						outputStream.close();
					} catch (IOException ex) {
						Logger.getLogger(TacGiaView.class.getName()).log(Level.SEVERE, null, ex);
					}

				}

			}

		});

		import_excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<TacGia> obj = readExcelFile();
					for (int i = 0; i < obj.size(); i++) {
						System.out.println(obj.get(i).toString());
					}

				} catch (InvalidFormatException ex) {
					Logger.getLogger(TacGiaView.class.getName()).log(Level.SEVERE, null, ex);
				}

			}

		});

		import_excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<TacGia> obj = readExcelFile();
					ArrayList<TacGia> objtrung = new ArrayList<TacGia>();
					ArrayList<TacGia> objkotrung = new ArrayList<TacGia>();
					int a = splitArrayList(obj, tacgiabus.getList_TacGia(), objtrung, objkotrung);
					if (!objkotrung.isEmpty()) {
						tacgiadao.Insert_NhieuTacGia(objkotrung);
						tacgiabus.ThemNhieuTacGia_Arr(objkotrung);
					}
					if (!objtrung.isEmpty()) {
						tacgiadao.Edit_NhieuTacGia(objtrung);
						for (int i = 0; i < objtrung.size(); i++) {
							if (objtrung.get(i).getTrangThai() == 0) {
								tacgiabus.XoaTGia_Array(objtrung.get(i));
							} else {
								tacgiabus.EditTGia_Array(objtrung.get(i));
							}

						}
					}

					ThemDataVaoJTable(model, tacgiabus.getList_TacGia());
					table = new JTable(model);
					if (a > 0)
						JOptionPane.showMessageDialog(null, "Có tất cả " + obj.size() + " nhưng chỉ có "
								+ (obj.size() - a) + " được thực hiện vì " + a + " còn lại không hợp lệ");
					else
						JOptionPane.showMessageDialog(null, "Đã import dữ liệu từ excel vào data");

				} catch (InvalidFormatException ex) {
					Logger.getLogger(DocGiaView.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SQLException ex) {
					Logger.getLogger(TacGiaView.class.getName()).log(Level.SEVERE, null, ex);
				}

			}

		});

	}

	public ArrayList<TacGia> readExcelFile() throws InvalidFormatException {
		ArrayList<TacGia> obj = new ArrayList<>();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn tập tin Excel");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files", "xlsx"));

		int userSelection = fileChooser.showOpenDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (FileInputStream excelFile = new FileInputStream(selectedFile)) {
				Workbook workbook = WorkbookFactory.create(excelFile);
				Sheet sheet = workbook.getSheetAt(0);
				DataFormatter dataFormatter = new DataFormatter();
				for (org.apache.poi.ss.usermodel.Row row : sheet) {
					if (row.getRowNum() == 0)
						continue; // Skip header row
					String ma = dataFormatter.formatCellValue(row.getCell(0));
					String ten = dataFormatter.formatCellValue(row.getCell(1));
					String gtinh = dataFormatter.formatCellValue(row.getCell(2));
					String nsinh = dataFormatter.formatCellValue(row.getCell(3));
					String trangthai = dataFormatter.formatCellValue(row.getCell(4));
					// Add more fields as per your Excel structure
					try {
						int trangThaiInt = Integer.parseInt(trangthai);
						TacGia newobj = new TacGia(ma, ten, gtinh, nsinh, trangThaiInt);
						obj.add(newobj);
					} catch (NumberFormatException e) {
						// Handle invalid integer format for 'trangthai' here
					}
				}
			} catch (IOException e) {
				// Handle IO exception here
				e.printStackTrace();
			}
		}

		return obj;
	}

	public int splitArrayList(ArrayList<TacGia> B, ArrayList<TacGia> A, ArrayList<TacGia> C, ArrayList<TacGia> D) {
		int data_loi = 0;
		int data_loi1 = 0;
		// Tạo một bản đồ lưu mã nhân viên của A
		Map<String, TacGia> mapA = new HashMap<>();
		for (TacGia emp : A) {
			mapA.put(emp.getMaTG(), emp);
		}

		// Duyệt qua các phần tử trong B để tách thành C và D
		for (TacGia emp : B) {
			if (!emp.getMaTG().isEmpty() && !emp.getTenTG().isEmpty() && !emp.getGioiTinh().isEmpty()
					&& !emp.getNamSinh().isEmpty() && (emp.getTrangThai() == 0 || emp.getTrangThai() == 1)
					&& KiemTraMa(emp.getMaTG()) == true || kiemTraGioiTinh(emp.getGioiTinh()) == true) {
				if (mapA.containsKey(emp.getMaTG())) {
					C.add(emp);
				} else {
					if (emp.getTrangThai() == 1)
						D.add(emp);
					else
						data_loi1 = data_loi1 + 1;
				}
			} else {
				data_loi = data_loi + 1;
			}

		}
		return data_loi + data_loi1;
	}

	public boolean kiemTraGioiTinh(String chuoi) {
		// Biểu thức chính quy để kiểm tra chuỗi
		String regex = "^Nam$|^Nữ$";

		// Tạo pattern từ biểu thức chính quy
		Pattern pattern = Pattern.compile(regex);

		// Tạo matcher từ chuỗi cần kiểm tra và pattern
		Matcher matcher = pattern.matcher(chuoi);

		// Kiểm tra xem chuỗi có khớp với biểu thức chính quy hay không
		return matcher.matches();
	}

}
