/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.DocGiaBUS;
import DATA.DocGiaDAO;
import DTO.DocGia;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class DocGiaView extends JPanel {
	private final DocGiaBUS docgiabus = new DocGiaBUS();
	private final DocGiaDAO docgiadao = new DocGiaDAO();
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
			JOptionPane.showMessageDialog(null, "Hãy nhập mã độc giả mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			if (KiemTraMa(userInput) == true) {
				ArrayList<DocGia> tacgiatrung = docgiabus.Tim_Theo_ID(userInput);
				// Danh sách rỗng
				if (tacgiatrung.isEmpty() == true)
					JOptionPane.showMessageDialog(null,
							"Không tìm thấy độc giả nào có mã giống với mã mà bạn đang tìm kiếm.");
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
	}

	public void TimTheoTen() {
		String userInput = txt_Search.getText();
		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập tên độc giả mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<DocGia> tacgiatrung = docgiabus.Tim_Theo_Ten(userInput);
			// Danh sách rỗng
			if (tacgiatrung.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy độc giả nào có tên giống với tên mà bạn đang tìm kiếm.");
			else {
				ThemDataVaoJTable(model, tacgiatrung);
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

	public int Ktra_EmailHopLe(String chuoi) {
		// Biểu thức kiểm tra
		String pattern = "^[\\w.-]+@gmail\\.com$";

		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(chuoi);
		// Đúng trả về 1
		if (match.matches())
			return 1;
		return 0;
	}

	public int Ktr_SDTHopLe(String phoneNumber) {
		String phoneRegex = "^0[0-9]{9}$";
		Pattern pattern = Pattern.compile(phoneRegex);
		Matcher matcher = pattern.matcher(phoneNumber);
		if (matcher.matches())
			return 1;
		return 0;
	}

	public void TimTheoSDT() {
		String userInput = txt_Search.getText();
		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập số điện thoại độc giả mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {

			if (Ktr_SDTHopLe(userInput) == 1) {
				ArrayList<DocGia> tacgiatrung = docgiabus.Tim_Theo_SDT(userInput);
				// Danh sách rỗng
				if (tacgiatrung.isEmpty() == true)
					JOptionPane.showMessageDialog(null,
							"Không tìm thấy độc giả nào có số điện thoại giống với số điện thoại mà bạn đang tìm kiếm.");
				else {
					ThemDataVaoJTable(model, tacgiatrung);
					table.setModel(model);
				}
				txt_Search.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "Số điện thoại của bạn nhập không hợp lệ !", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);

			}
		}

	}

	public void TimTheoEmail() {
		String userInput = txt_Search.getText();
		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập email độc giả mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			if (Ktra_EmailHopLe(userInput) == 1) {
				ArrayList<DocGia> tacgiatrung = docgiabus.Tim_Theo_Email(userInput);
				// Danh sách rỗng
				if (tacgiatrung.isEmpty() == true)
					JOptionPane.showMessageDialog(null,
							"Không tìm thấy độc giả nào có email giống với email mà bạn đang tìm kiếm.");
				else {
					ThemDataVaoJTable(model, tacgiatrung);
					table.setModel(model);
				}
				txt_Search.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "Email của bạn không hợp lệ !", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public void Xoa() {
		label_form.setText("Xóa Thông Tin Độc Giả");
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa thông tin  độc giả này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String matgia = MA.getText();
			String tentg = txt_Ten.getText();
			String sdt = txt_SDT.getText();
			String email = txt_Email.getText();
			DocGia obj = new DocGia(matgia, tentg, sdt, email, 1);
			int row = docgiadao.XoaDocGia_Data(obj);
			if (row > 0) {
				docgiabus.XoaDocGia_Array(obj);
				ThemDataVaoJTable(model, docgiabus.getList_DocGia());
				table.setModel(model);
				MA.setText("");
				txt_Ten.setText("");
				txt_SDT.setText("");
				txt_Email.setText("");
				txt_Ten.setFocusable(false);
				txt_SDT.setFocusable(false);
				txt_Email.setFocusable(false);
				reset.setVisible(false);
				peform.setVisible(false);
				JOptionPane.showMessageDialog(null, "Đã xóa thành công !");

			} else {
				JOptionPane.showMessageDialog(null, "Đã có lỗi xảy ra.");

			}

		} else {
			// Thực hiện thao tác khi người dùng nhấn Cancel hoặc đóng hộp thoại
			// Đặt các đoạn mã của bạn ở đây
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
		table.clearSelection();
		isButtonDeleteSelected = false;
		System.out.printf("%s", isButtonDeleteSelected);

	}

	public void Sua() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có sửa thông tin độc giả này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String matgia = MA.getText();
			String tentg = txt_Ten.getText();
			String sdt = txt_SDT.getText();
			String email = txt_Email.getText();
			if (tentg.isEmpty() || sdt.isEmpty() || Ktr_SDTHopLe(sdt) == 0 || email.isEmpty()
					|| Ktra_EmailHopLe(email) == 0) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền chính xác và đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {

				DocGia obj = new DocGia(matgia, tentg, sdt, email, 1);
				int row = docgiadao.EditDocGia_Data(obj);
				if (row > 0) {
					docgiabus.EditDocGia_Array(obj);
					ThemDataVaoJTable(model, docgiabus.getList_DocGia());
					table.setModel(model);
					MA.setText("");
					txt_Ten.setText("");
					txt_SDT.setText("");
					txt_Email.setText("");
					txt_Ten.setFocusable(false);
					txt_SDT.setFocusable(false);
					txt_Email.setFocusable(false);
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
		isButtonEditSelected = false;
		System.out.printf("%s", isButtonEditSelected);
		table.clearSelection();

	}

	public void Them() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm độc giả này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			// Thực hiện thao tác khi người dùng nhấn OK
			// Đặt các đoạn mã của bạn ở đây
			String matgia = MA.getText();
			String tentg = txt_Ten.getText();
			String sdt = txt_SDT.getText();
			String email = txt_Email.getText();
			if (tentg.isEmpty() || sdt.isEmpty() || Ktr_SDTHopLe(sdt) == 0 || email.isEmpty()
					|| Ktra_EmailHopLe(email) == 0) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ và chính xác thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				DocGia obj = new DocGia(matgia, tentg, sdt, email, 1);
				int a = docgiadao.insertDocgia_Data(obj);
				if (a > 0) {
					docgiabus.ThemDocGia_Arr(obj);
					ThemDataVaoJTable(model, docgiabus.getList_DocGia());
					table.setModel(model);
					MA.setText("");
					txt_Ten.setText("");
					txt_SDT.setText("");
					txt_Email.setText("");
					txt_Ten.setFocusable(false);
					txt_SDT.setFocusable(false);
					txt_Email.setFocusable(false);
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
		isButtonInsertSelected = false;
		System.out.printf("%s", isButtonInsertSelected);

	}

	public void ThemDataVaoJTable(DefaultTableModel model, ArrayList<DocGia> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (DocGia tacGia : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { tacGia.getMaDG(), tacGia.getHoTen(), tacGia.getSDT(), tacGia.getEmail(),
					tacGia.getTrangThai() });
		}
	}

	public DocGiaView() {
		DocGiaGUI();
	}

	public void DocGiaGUI() {
		// Lấy dữ liệu đổ xuống arraylist
		docgiabus.DanhSachlist();

		// Title
		label_Title = new JLabel("Độc Giả");
		label_Title.setFont(new Font("Arial", BOLD, 50));
		label_Title.setForeground(Color.WHITE);
		panel_Title = new JPanel(new FlowLayout());
		panel_Title.setBackground(Color.PINK);
		panel_Title.setPreferredSize(new Dimension(1160, 60));
		panel_Title.add(label_Title);

		String[] combobox_item = { "Tìm Kiếm Theo ID", "Tìm Kiếm Theo Tên", "Tìm Kiếm Theo SĐT",
				"Tìm Kiếm Theo Email" };
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
		panel_nav.add(import_excel);
		panel_nav.add(excel);

		String[] columnNames = { "Mã", "Tên", "SĐT", "Email", "Trạng Thái" };
		model = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTable(model, docgiabus.getList_DocGia());
		table = new JTable(model);
		table.setDefaultEditor(Object.class, null);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); // Mã
		columnModel.getColumn(1).setPreferredWidth(200); // Tên
		columnModel.getColumn(2).setPreferredWidth(200); // Phái
		columnModel.getColumn(3).setPreferredWidth(200);
		columnModel.getColumn(4).setPreferredWidth(60);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
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

		label_form = new JLabel("Hiển Thị Thông Tin Độc Giả");
		label_form.setFont(new Font("Arial", BOLD, 20));
		JPanel panel_label_form = new JPanel();
		panel_label_form.add(label_form);
		panel_label_form.setBackground(Color.WHITE);
		label_MA = new JLabel("Mã Độc Giả :");
		label_MA.setFont(new Font("Arial", BOLD, 19));
		MA = new JLabel("");
		MA.setFont(new Font("Arial", Font.ITALIC, 19));
		label_Ten = new JLabel("Tên Độc Giả :");
		label_Ten.setFont(new Font("Arial", BOLD, 19));
		label_SDT = new JLabel("Số Điện Thoại :");
		label_SDT.setFont(new Font("Arial", BOLD, 19));
		label_Email = new JLabel("Email  :");
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
		this.setLayout(new FlowLayout(0,0,0));
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
				case 2:
					TimTheoSDT();
					break;
				case 3:
					TimTheoEmail();
					break;

				}

			}

		});

		// Tạo sự kiện home
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MA.setText("");
				txt_Ten.setText("");
				txt_SDT.setText("");
				txt_Email.setText("");
				txt_Ten.setFocusable(false);
				txt_SDT.setFocusable(false);
				txt_Email.setFocusable(false);
				reset.setVisible(false);
				peform.setVisible(false);
				ThemDataVaoJTable(model, docgiabus.getList_DocGia());
				table.setModel(model);
			}
		});

		// Thêm tác giả
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label_form.setText("Thêm Độc Giả");
				txt_Ten.setText("");
				txt_SDT.setText("");
				txt_Email.setText("");
				txt_Ten.setFocusable(true);
				txt_SDT.setFocusable(true);
				txt_Email.setFocusable(true);

				reset.setVisible(true);
				peform.setVisible(true);
				// Tạo mã tác giả duy nhất
				String matgia = docgiabus.TaoMaDGia_DuyNhat();
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
					label_form.setText("Hiển thị thông tin độc giả");
					MA.setText("");
					txt_Ten.setText("");
					txt_SDT.setText("");
					txt_Email.setText("");
					txt_Ten.setFocusable(false);
					txt_SDT.setFocusable(false);
					txt_Email.setFocusable(false);
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
						// Lấy dữ liệu của dòng được chọn
						Object Ma = table.getValueAt(selectedRow, 0);
						Object Ten = table.getValueAt(selectedRow, 1);
						Object SDT = table.getValueAt(selectedRow, 2);
						Object EMAIL = table.getValueAt(selectedRow, 3);

						label_form = new JLabel("Hiển Thị Thông Tin Độc Giả");
						MA.setText((String) Ma);
						txt_Ten.setText((String) Ten);
						txt_SDT.setText((String) SDT);
						txt_Email.setText((String) EMAIL);
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một độc giả!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					label_form.setText("Sửa Thông Tin Độc Giả");
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một độc giả!", "Cảnh báo",
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
					ArrayList<DocGia> obj = readExcelFile();
					ArrayList<DocGia> objtrung = new ArrayList<DocGia>();
					ArrayList<DocGia> objkotrung = new ArrayList<DocGia>();
					int a = splitArrayList(obj, docgiabus.getList_DocGia(), objtrung, objkotrung);
					if (!objkotrung.isEmpty()) {
						docgiadao.Insert_NhieuDocGia(objkotrung);
						docgiabus.ThemNhieuDocGia_Arr(objkotrung);
					}
					if (!objtrung.isEmpty()) {
						docgiadao.Edit_NhieuDocGia(objtrung);
						for (int i = 0; i < objtrung.size(); i++) {
							if (objtrung.get(i).getTrangThai() == 0) {
								docgiabus.XoaDocGia_Array(objtrung.get(i));
							} else {
								docgiabus.EditDocGia_Array(objtrung.get(i));
							}

						}
					}

					ThemDataVaoJTable(model, docgiabus.getList_DocGia());
					table = new JTable(model);
					if (a > 0)
						JOptionPane.showMessageDialog(null, "Có tất cả " + obj.size() + " nhưng chỉ có "
								+ (obj.size() - a) + " được thực hiện vì " + a + " còn lại không hợp lệ");
					else
						JOptionPane.showMessageDialog(null, "Đã import dữ liệu từ excel vào data");

				} catch (InvalidFormatException ex) {
					Logger.getLogger(DocGiaView.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SQLException ex) {
					Logger.getLogger(DocGiaView.class.getName()).log(Level.SEVERE, null, ex);
				}

			}

		});

	}

	public ArrayList<DocGia> readExcelFile() throws InvalidFormatException {
		ArrayList<DocGia> obj = new ArrayList<>();
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
					String sdt = dataFormatter.formatCellValue(row.getCell(2));
					String email = dataFormatter.formatCellValue(row.getCell(3));
					String trangthai = dataFormatter.formatCellValue(row.getCell(4));
					// Add more fields as per your Excel structure
					try {
						int trangThaiInt = Integer.parseInt(trangthai);
						DocGia newobj = new DocGia(ma, ten, sdt, email, trangThaiInt);
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

	public int splitArrayList(ArrayList<DocGia> B, ArrayList<DocGia> A, ArrayList<DocGia> C, ArrayList<DocGia> D) {
		int data_loi = 0;
		int data_loi1 = 0;
		// Tạo một bản đồ lưu mã nhân viên của A
		Map<String, DocGia> mapA = new HashMap<>();
		for (DocGia emp : A) {
			mapA.put(emp.getMaDG(), emp);
		}

		// Duyệt qua các phần tử trong B để tách thành C và D
		for (DocGia emp : B) {
			if (!emp.getMaDG().isEmpty() && !emp.getHoTen().isEmpty() && !emp.getSDT().isEmpty()
					&& !emp.getEmail().isEmpty() && (emp.getTrangThai() == 0 || emp.getTrangThai() == 1)
					&& KiemTraMa(emp.getMaDG()) == true
					|| Ktra_EmailHopLe(emp.getEmail()) == 1 && Ktr_SDTHopLe(emp.getSDT()) == 1) {
				if (mapA.containsKey(emp.getMaDG())) {
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

}
