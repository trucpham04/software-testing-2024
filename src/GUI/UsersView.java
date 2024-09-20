package GUI;

import static com.mysql.cj.xdevapi.Type.STRING;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import static java.awt.Font.BOLD;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import BUS.NhanVienBUS;
import BUS.UserBUS;
import DATA.NhanVienDAO;
import DTO.DocGia;
import DTO.NhanVien;
import DTO.User;
import DATA.UserDAO;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class UsersView extends JPanel {
	private UserBUS usBUS = new UserBUS();
	private UserDAO usDao = new UserDAO();
	private JLabel label_Title;
	private JPanel panel_Title;

	private JComboBox combobox;
	private JTextField txt_Search;
	private JButton btn_Searchsubmit;
	private JPanel panel_Search;

	private JPanel panel_Table;
	private JTable table;
	private DefaultTableModel model;

	private JPanel panel_nav;
	private JButton insert;
	private JButton delete;
	private JButton edit;
	private JButton import_excel;
	private JButton excel;
	private JButton home;

	private JPanel panel_main_form;
	private JPanel panel_form;
	private JLabel label_form;
	private JLabel label_MA;
	private JLabel MA;
	private JLabel label_Ten;
	private JTextField txt_Ten;
	private JLabel label_SDT;
	private JTextField txt_SDT;
	private JLabel label_Email;
	private JTextField txt_Email;
	private JButton reset;
	private JButton peform;

	private boolean isButtonInsertSelected = false;
	private boolean isButtonEditSelected = false;
	private boolean isButtonDeleteSelected = false;

	public void TimTheoID() {
		// Tìm kiếm theo id
		String userInput = txt_Search.getText();
		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập mã tài khoản mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<User> us = usBUS.search_manv(userInput);
			// Danh sách rỗng
			if (us.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy tài khoản nào có mã giống với mã mà bạn đang tìm kiếm.");
			else {
				outModel(model, us);
				table.setModel(model);
			}
			txt_Search.setText("");

		}
	}

	public void TimTheoTen() {
		// Tìm kiếm theo id
		String userInput = txt_Search.getText();
		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập tên nhân viên mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<User> nv = usBUS.search_usname(userInput);
			// Danh sách rỗng
			if (nv.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy tài khoản nào có tên đăng nhập giống với tên mà bạn đang tìm kiếm.");
			else {
				outModel(model, nv);
				table.setModel(model);
			}
			txt_Search.setText("");

		}
	}

	// Kiểm tra xem chuỗi nhập vào có phải là mã tác giả hay không
	public boolean KiemTraMa(String input) {
		// Kiểm tra xem chuỗi có chứa 4 chữ số và không chứa ký tự không phải số không
		return input.matches("\\d{4}");
	}

	public int Ktra_UserName(String chuoi) {
		// Biểu thức kiểm tra
		String pattern = "[a-z][0-9]";
		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(chuoi);
		// Đúng trả về 1
		if (match.matches())
			return 1;
		return 0;
	}

	public void clear() {
		MA.setText("");
		txt_Ten.setText("");
		txt_SDT.setText("");
		txt_Email.setText("");
		txt_Ten.setFocusable(false);
		txt_SDT.setFocusable(false);
		txt_Email.setFocusable(false);
		reset.setVisible(false);
		peform.setVisible(false);
	}

	public void Xoa() {
		label_form.setText("Xóa Thông Tin Tài Khoản");
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa thông tin tài khoản này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			usBUS.delete2(MA.getText());

			table.clearSelection();
			outModel(model, usBUS.getList());
			clear();
			JOptionPane.showMessageDialog(null, "Đã xóa thành công !");
		} else {
			// Thực hiện thao tác khi người dùng nhấn Cancel hoặc đóng hộp thoại
			// Đặt các đoạn mã của bạn ở đây
			clear();
		}
		table.clearSelection();
		isButtonDeleteSelected = false;
		System.out.printf("%s", isButtonDeleteSelected);

	}

	public void Sua() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có sửa thông tin tài khoản này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String ma = MA.getText();
			String tentg = txt_Ten.getText();
			String sdt = txt_SDT.getText();
			String email = txt_Email.getText();
			if (tentg.isEmpty() || sdt.isEmpty() || Ktra_UserName(tentg) == 0 || email.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền chính xác và đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				User obj = new User(ma, tentg, email, sdt, 1);

				usBUS.set(obj);
				outModel(model, usBUS.getList());
				table.setModel(model);
				clear();
				JOptionPane.showMessageDialog(null, "Đã sửa thành công !");

			}

		} else {
			// Thực hiện thao tác khi người dùng nhấn Cancel hoặc đóng hộp thoại
			// Đặt các đoạn mã của bạn ở đây
			clear();

		}
		isButtonEditSelected = false;
		System.out.printf("%s", isButtonEditSelected);
		table.clearSelection();

	}

	public void Them() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm tài khoản này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			// Thực hiện thao tác khi người dùng nhấn OK
			// Đặt các đoạn mã của bạn ở đây
			String matgia = MA.getText();
			String tentg = txt_Ten.getText();
			String sdt = txt_SDT.getText();
			String email = txt_Email.getText();
			if (tentg.isEmpty() || sdt.isEmpty() || Ktra_UserName(tentg) == 0 || email.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ và chính xác thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {

				User obj = new User(matgia, tentg, email, sdt, 1);
				usBUS.add(obj);
				outModel(model, usBUS.getList());
				table.setModel(model);
				clear();
				JOptionPane.showMessageDialog(null, "Đã thêm thành công !");

			}
		} else {
			// Thực hiện thao tác khi người dùng nhấn Cancel hoặc đóng hộp thoại
			// Đặt các đoạn mã của bạn ở đây
			clear();
		}
		isButtonInsertSelected = false;
		System.out.printf("%s", isButtonInsertSelected);

	}

	public String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		temp = pattern.matcher(temp).replaceAll("");
		return temp.replaceAll("đ", "d");
	}

	public void outModel(DefaultTableModel tblModel, ArrayList<User> nv) {
		Vector data;
		tblModel.setRowCount(0);
		for (User n : nv) {
			data = new Vector();
			data.add(n.getManv());
			data.add(n.getUsername());
			// data.add(n.getPassword());
			data.add(n.getMacv());
			// data.add(n.getTrangthai());
			tblModel.addRow(data);
		}
		table.setModel(tblModel);
	}

	public void listSP() {
		if (usBUS.getList() == null)
			usBUS.list();
		ArrayList<User> nv = usBUS.getList();
		outModel(model, nv);
	}

	public UsersView() {
		DocGiaGUI();
	}

	public void DocGiaGUI() {
		// Lấy dữ liệu đổ xuống arraylist

		// Title
		label_Title = new JLabel("Tài Khoản");
		label_Title.setFont(new Font("Arial", BOLD, 50));
		label_Title.setForeground(Color.WHITE);
		panel_Title = new JPanel(new FlowLayout());
		panel_Title.setBackground(Color.PINK);
		panel_Title.setPreferredSize(new Dimension(1160, 60));
		panel_Title.add(label_Title);

		String[] combobox_item = { "Tìm Kiếm Theo ID", "Tìm Kiếm Theo Tên", };
		combobox = new JComboBox(combobox_item);
		combobox.setPreferredSize(new Dimension(260, 40));
		combobox.setFont(new Font("Arial", BOLD, 15));
		combobox.setForeground(Color.WHITE);
		combobox.setBackground(Color.PINK);
		combobox.setVisible(true);
		combobox.setOpaque(true);
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
		panel_Search.add(combobox);
		panel_Search.add(txt_Search);
		panel_Search.add(btn_Searchsubmit);

		// Nav
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

		ImageIcon iconedit = new ImageIcon(getClass().getResource("/img/Pencil-4-icon.png"));
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

		ImageIcon iconprint = new ImageIcon(
				getClass().getResource("/img/Dakirby309-Simply-Styled-Microsoft-Excel-2013.256.png"));
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

		ImageIcon iconexcel = new ImageIcon(getClass().getResource("/img/Microsoft-Excel-icon.png"));
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
		panel_nav.add(import_excel);
		panel_nav.add(excel);

		// String[] columnNames = { "Mã Tài Khoản", "Tên Đăng Nhập", "Mật Khẩu", "Mã
		// Quyền", "Trạng Thái" };
		String[] columnNames = { "Mã Tài Khoản", "Tên Đăng Nhập", "Mã Quyền" };
		model = new DefaultTableModel(columnNames, 0);

		table = new JTable(model);
		listSP();
		table.setDefaultEditor(Object.class, null);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(200);
		// columnModel.getColumn(3).setPreferredWidth(200);
		// columnModel.getColumn(4).setPreferredWidth(60);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		// table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		// table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

		panel_Table = new JPanel(new CardLayout());
		panel_Table.setPreferredSize(new Dimension(1160, 300));
		panel_Table.setBackground(Color.WHITE);
		panel_Table.add(new JScrollPane(table));

		label_form = new JLabel("Hiển Thị Thông Tin Tài Khoản");
		label_form.setFont(new Font("Arial", BOLD, 20));
		JPanel panel_label_form = new JPanel();
		panel_label_form.add(label_form);
		panel_label_form.setBackground(Color.WHITE);
		label_MA = new JLabel("Mã Tài Khoản :");
		label_MA.setFont(new Font("Arial", BOLD, 19));
		MA = new JLabel("");
		MA.setFont(new Font("Arial", Font.ITALIC, 19));
		label_Ten = new JLabel("Tên Đăng Nhập :");
		label_Ten.setFont(new Font("Arial", BOLD, 19));
		label_SDT = new JLabel("Mật Khẩu :");
		label_SDT.setFont(new Font("Arial", BOLD, 19));
		label_Email = new JLabel("Mã Quyền :");
		label_Email.setFont(new Font("Arial", BOLD, 19));
		txt_Ten = new JTextField();
		txt_Ten.setPreferredSize(new Dimension(250, 40));
		txt_Ten.setFont(new Font("Arial", BOLD, 19));
		txt_Ten.setFocusable(false);
		txt_SDT = new JTextField();
		txt_SDT.setPreferredSize(new Dimension(275, 40));
		txt_SDT.setFont(new Font("Arial", BOLD, 19));
		txt_SDT.setFocusable(false);
		txt_Email = new JTextField();
		txt_Email.setPreferredSize(new Dimension(275, 40));
		txt_Email.setFont(new Font("Arial", BOLD, 19));
		txt_Email.setFocusable(false);

		panel_form = new JPanel(new GridLayout(2, 2));

		JPanel panel_MA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_MA.add(label_MA);
		panel_MA.add(MA);
		panel_MA.setPreferredSize(new Dimension(1160, 50));
		panel_MA.setBackground(Color.WHITE);

		JPanel panel_TEN = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_TEN.add(label_Ten);
		panel_TEN.add(txt_Ten);
		panel_TEN.setPreferredSize(new Dimension(1160, 50));
		panel_TEN.setBackground(Color.WHITE);

		JPanel panel_SDT = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_SDT.add(label_SDT);
		panel_SDT.add(txt_SDT);
		panel_SDT.setPreferredSize(new Dimension(1160, 50));
		panel_SDT.setBackground(Color.WHITE);

		JPanel panel_Email = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_Email.add(label_Email);
		panel_Email.add(txt_Email);
		panel_Email.setPreferredSize(new Dimension(1160, 50));
		panel_Email.setBackground(Color.WHITE);

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
		panel_form.add(panel_MA);
		panel_form.add(panel_TEN);
		panel_form.add(panel_SDT);
		panel_form.add(panel_Email);
		panel_form.setPreferredSize(new Dimension(1160, 100));
		panel_form.setBackground(Color.WHITE);

		panel_main_form = new JPanel(new BorderLayout());
		panel_main_form.setBackground(Color.WHITE);
		panel_main_form.setPreferredSize(new Dimension(1160, 200));
		panel_main_form.add(panel_label_form, BorderLayout.NORTH);
		panel_main_form.add(panel_form, BorderLayout.CENTER);
		panel_main_form.add(panel_button, BorderLayout.SOUTH);
		panel_main_form.setBorder(new LineBorder(Color.PINK, 4, true));
		this.setLayout(new FlowLayout(0, 0, 0));
		this.setPreferredSize(new Dimension(1160, 730));
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
		import_excel.addMouseListener(mouseListener);
		reset.addMouseListener(mouseListener);
		peform.addMouseListener(mouseListener);
		btn_Searchsubmit.addMouseListener(mouseListener);

		// Tạo một Action Event để lắng nghe sụ kiện tìm kiếm
		btn_Searchsubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = combobox.getSelectedIndex();
				switch (selectedIndex) {
					case 0:
						TimTheoID();
						break;
					case 1:
						TimTheoTen();
						break;
				}
			}

		});

		// Tạo sự kiện home
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				listSP();
			}
		});

		// Thêm tác giả
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label_form.setText("Thêm Tài Khoản");
				txt_Ten.setText("");
				txt_SDT.setText("");
				txt_Email.setText("");
				txt_Ten.setFocusable(true);
				txt_SDT.setFocusable(true);
				txt_Email.setFocusable(true);

				reset.setVisible(true);
				peform.setVisible(true);
				// Tạo mã tác giả duy nhất
				String matgia = usBUS.TaoMaNV_DuyNhat();
				MA.setText(matgia);
				isButtonInsertSelected = true;
			}
		});

		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn tiếp tục hành động này không?",
						"Xác nhận", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.CANCEL_OPTION) {
					label_form.setText("Hiển Thị Thông Tin Tài Khoản");
					clear();
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
						// Lấy dữ liệu của dòng được chọn
						Object Manv = table.getValueAt(selectedRow, 0);
						Object usn = table.getValueAt(selectedRow, 1);
						Object pwd = table.getValueAt(selectedRow, 2);
						Object maq = table.getValueAt(selectedRow, 3);

						label_form = new JLabel("Hiển Thị Thông Tin Tài Khoản");
						MA.setText((String) Manv);
						txt_Ten.setText((String) usn);
						txt_SDT.setText((String) pwd);
						txt_Email.setText((String) maq);
						txt_Ten.setFocusable(false);
						txt_SDT.setFocusable(false);
						txt_Email.setFocusable(false);
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một tài khoản!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					label_form.setText("Sửa Thông Tin Tài Khoản");
					peform.setVisible(true);
					txt_Ten.setFocusable(true);
					txt_SDT.setFocusable(true);
					txt_Email.setFocusable(true);
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một tài khoản!", "Cảnh báo",
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
					ArrayList<User> obj = readExcelFile();
					ArrayList<User> objtrung = new ArrayList<User>();
					ArrayList<User> objkotrung = new ArrayList<User>();
					int a = splitArrayList(obj, usBUS.getList(), objtrung, objkotrung);
					if (!objkotrung.isEmpty()) {
						usDao.Insert_NhieuDocGia(objkotrung);
						usBUS.add_all(objkotrung);
					}
					if (!objtrung.isEmpty()) {
						usDao.Edit_NhieuDocGia(objtrung);
						for (int i = 0; i < objtrung.size(); i++) {
							if (objtrung.get(i).getTrangthai() == 0) {
								usBUS.delete(objtrung.get(i));
							} else {
								usBUS.set(objtrung.get(i));
							}

						}
					}

					outModel(model, usBUS.getList());
					table = new JTable(model);
					if (a > 0)
						JOptionPane.showMessageDialog(null, "Có tất cả " + obj.size() + " nhưng chỉ có "
								+ (obj.size() - a) + " được thực hiện vì " + a + " còn lại không hợp lệ");
					else
						JOptionPane.showMessageDialog(null, "Đã import dữ liệu từ excel vào data");

				} catch (InvalidFormatException ex) {
					Logger.getLogger(NhanVienView.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SQLException ex) {
					Logger.getLogger(DocGiaView.class.getName()).log(Level.SEVERE, null, ex);
				}

			}

		});

	}

	public ArrayList<User> readExcelFile() throws InvalidFormatException {
		ArrayList<User> obj = new ArrayList<>();
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
				for (Row row : sheet) {
					if (row.getRowNum() == 0)
						continue; // Skip header row
					String ma = dataFormatter.formatCellValue(row.getCell(0));
					String ten = dataFormatter.formatCellValue(row.getCell(1));
					String email = dataFormatter.formatCellValue(row.getCell(2));
					String sdt = dataFormatter.formatCellValue(row.getCell(3));
					String trangthai = dataFormatter.formatCellValue(row.getCell(4));
					// Add more fields as per your Excel structure
					try {
						int trangThaiInt = Integer.parseInt(trangthai);
						User newobj = new User(ma, ten, email, sdt, trangThaiInt);
						obj.add(newobj);
					} catch (NumberFormatException e) {
						// Handle invalid integer format for 'trangthai' here
					}
				}
				excelFile.close();

			} catch (IOException e) {
				// Handle IO exception here
				e.printStackTrace();
			}
		}

		return obj;
	}

	public int splitArrayList(ArrayList<User> B, ArrayList<User> A, ArrayList<User> C, ArrayList<User> D) {
		int data_loi = 0;
		int data_loi1 = 0;
		// Tạo một bản đồ lưu mã nhân viên của A
		Map<String, User> mapA = new HashMap<>();
		for (User emp : A) {
			mapA.put(emp.getManv(), emp);
		}

		// Duyệt qua các phần tử trong B để tách thành C và D
		for (User emp : B) {
			if (!emp.getManv().isEmpty() && !emp.getPassword().isEmpty() && !emp.getUsername().isEmpty()
					&& !emp.getMacv().isEmpty() && (emp.getTrangthai() == 0 || emp.getTrangthai() == 1)
					&& KiemTraMa(emp.getManv()) == true || Ktra_UserName(emp.getUsername()) == 1) {
				if (mapA.containsKey(emp.getManv())) {
					C.add(emp);
				} else {
					if (emp.getTrangthai() == 1)
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

}
