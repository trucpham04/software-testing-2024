/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.LoaiSachBUS;
import DATA.LoaiSachDAO;
import DTO.LoaiSach;
import javax.swing.*;
import java.awt.*;
import static java.awt.Font.BOLD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.asn1.eac.Flags;

/**
 *
 * @author Admin
 */
public class LoaiSachView extends JPanel {
	private final LoaiSachBUS loaisachbus = new LoaiSachBUS();
	private final LoaiSachDAO loaisachdao = new LoaiSachDAO();
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
	private JButton reset;
	private JButton peform;

	private boolean isButtonInsertSelected = false;
	private boolean isButtonEditSelected = false;
	private boolean isButtonDeleteSelected = false;

	public void Xoa() {
		label_form.setText("Xóa Thông Tin Loại Sách");
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa thông tin về loại sách này không?",
				"Xác nhận", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String ma = MA.getText();
			String ten = txt_Ten.getText();

			LoaiSach obj = new LoaiSach(ma, ten, 1);
			int row = loaisachdao.XoaLoaiSach_Data(obj);
			if (row > 0) {
				loaisachbus.XoaLoaiSach_Array(obj);
				ThemDataVaoJTable(model, loaisachbus.getList_LoaiSach());
				table.setModel(model);
				MA.setText("");
				txt_Ten.setText("");
				txt_Ten.setFocusable(false);
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
			txt_Ten.setFocusable(false);
			reset.setVisible(false);
			peform.setVisible(false);
		}
		table.clearSelection();
		isButtonDeleteSelected = false;
		System.out.printf("%s", isButtonDeleteSelected);

	}

	public void Sua() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có sửa thông tin loại sách  này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String matgia = MA.getText();
			String tentg = txt_Ten.getText();
			if (tentg.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền chính xác và đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {

				LoaiSach obj = new LoaiSach(matgia, tentg, 1);
				int row = loaisachdao.EditLoaiSach_Data(obj);
				if (row > 0) {
					loaisachbus.EditLoaiSach_Array(obj);
					ThemDataVaoJTable(model, loaisachbus.getList_LoaiSach());
					table.setModel(model);
					MA.setText("");
					txt_Ten.setText("");
					txt_Ten.setFocusable(false);
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
			txt_Ten.setFocusable(false);
			reset.setVisible(false);
			peform.setVisible(false);

		}
		isButtonEditSelected = false;
		System.out.printf("%s", isButtonEditSelected);
		table.clearSelection();

	}

	public void Them() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm loại sách này không này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			// Thực hiện thao tác khi người dùng nhấn OK
			// Đặt các đoạn mã của bạn ở đây
			String matgia = MA.getText();
			String tentg = txt_Ten.getText();
			if (tentg.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ và chính xác thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {

				LoaiSach obj = new LoaiSach(matgia, tentg, 1);
				int a = loaisachdao.insertLoaiSach_Data(obj);
				if (a > 0) {
					loaisachbus.ThemLoaiSach_Arr(obj);
					ThemDataVaoJTable(model, loaisachbus.getList_LoaiSach());
					table.setModel(model);
					MA.setText("");
					txt_Ten.setText("");
					txt_Ten.setFocusable(false);
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
			txt_Ten.setFocusable(false);
			reset.setVisible(false);
			peform.setVisible(false);
		}
		isButtonInsertSelected = false;
		System.out.printf("%s", isButtonInsertSelected);

	}

	// Kiểm tra xem chuỗi nhập vào có phải là mã loại sách hay không
	public boolean KiemTraMa(String input) {
		// Kiểm tra xem chuỗi có chứa 4 chữ số và không chứa ký tự không phải số không
		return input.matches("\\d{4}");
	}

	public void ThemDataVaoJTable(DefaultTableModel model, ArrayList<LoaiSach> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (LoaiSach loaiSach : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { loaiSach.getMaLoai(), loaiSach.getTenLoai(), loaiSach.getTrangThai() });
		}
	}

	public LoaiSachView() {
		LoaiSachGUI();
	}

	public void LoaiSachGUI() {
		// Lấy dữ liệu đổ vào arraylist
		loaisachbus.DanhSachlist();

		// Title
		label_Title = new JLabel("Loại Sách");
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

		String[] columnNames = { "Mã", "Tên", "Trạng Thái" };
		model = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTable(model, loaisachbus.getList_LoaiSach());
		table = new JTable(model);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(200); // Mã
		columnModel.getColumn(1).setPreferredWidth(500); // Tên
		columnModel.getColumn(2).setPreferredWidth(200); // Phái
		table.setDefaultEditor(Object.class, null);

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
		panel_Table.setPreferredSize(new Dimension(1160, 600));
		panel_Table.setBackground(Color.WHITE);
		panel_Table.add(new JScrollPane(table));

		label_form = new JLabel("Hiển Thị Thông Tin Loại Sách");
		label_form.setFont(new Font("Arial", BOLD, 20));
		JPanel panel_label_form = new JPanel();
		panel_label_form.add(label_form);
		panel_label_form.setBackground(Color.WHITE);
		label_MA = new JLabel("Mã Loại Sách :");
		label_MA.setFont(new Font("Arial", BOLD, 19));
		MA = new JLabel("");
		MA.setFont(new Font("Arial", Font.ITALIC, 19));
		label_Ten = new JLabel("Tên Loại Sách :");
		label_Ten.setFont(new Font("Arial", BOLD, 19));
		txt_Ten = new JTextField();
		txt_Ten.setPreferredSize(new Dimension(250, 40));
		txt_Ten.setFont(new Font("Arial", BOLD, 19));
		txt_Ten.setFocusable(false);
		panel_form = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
		panel_form.add(panel_button);
		panel_form.setPreferredSize(new Dimension(1160, 150));
		panel_form.setBackground(Color.WHITE);

		panel_main_form = new JPanel(new BorderLayout());
		panel_main_form.setBackground(Color.WHITE);
		panel_main_form.setPreferredSize(new Dimension(1160, 220));
		panel_main_form.add(panel_label_form, BorderLayout.NORTH);
		panel_main_form.add(panel_form, BorderLayout.CENTER);
		panel_main_form.setBorder(new LineBorder(Color.PINK, 4, true));

		this.setLayout(new FlowLayout(0, 0, 0));
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
		import_excel.addMouseListener(mouseListener);
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
						JOptionPane.showMessageDialog(null, "Hãy nhập mã loại sách mà bạn muốn tìm kiếm !", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					else {
						if (KiemTraMa(userInput) == true) {
							ArrayList<LoaiSach> loaisachtrung = loaisachbus.Tim_Theo_ID(userInput);
							// Danh sách rỗng
							if (loaisachtrung.isEmpty() == true)
								JOptionPane.showMessageDialog(null,
										"Không tìm thấy loại sách nào có mã giống với mã mà bạn đang tìm kiếm.");
							else {
								ThemDataVaoJTable(model, loaisachtrung);
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
						ArrayList<LoaiSach> tacgiatrung = loaisachbus.Tim_Theo_Ten(userInput);
						// Danh sách rỗng
						if (tacgiatrung.isEmpty() == true)
							JOptionPane.showMessageDialog(null,
									"Không tìm thấy loại sách nào có tên giống với tên mà bạn đang tìm kiếm.");
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
				MA.setText("");
				txt_Ten.setText("");
				txt_Ten.setFocusable(false);
				reset.setVisible(false);
				peform.setVisible(false);
				ThemDataVaoJTable(model, loaisachbus.getList_LoaiSach());
				table.setModel(model);
			}
		});

		// Thêm tác giả
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label_form.setText("Thêm Loại Sách");
				txt_Ten.setText("");
				txt_Ten.setFocusable(true);
				reset.setVisible(true);
				peform.setVisible(true);
				// Tạo mã tác giả duy nhất
				String matgia = loaisachbus.TaoMaLoaiSach_DuyNhat();
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
					label_form = new JLabel("Hiển Thị Thông Tin Loại Sach");
					MA.setText("");
					txt_Ten.setText("");
					txt_Ten.setFocusable(false);
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
						// isRowSelected = true;
						// Lấy dữ liệu của dòng được chọn
						Object Ma = table.getValueAt(selectedRow, 0);
						Object Ten = table.getValueAt(selectedRow, 1);
						label_form = new JLabel("Hiển Thị Thông Tin Loại Sách :");
						MA.setText((String) Ma);
						txt_Ten.setText((String) Ten);
						txt_Ten.setFocusable(false);
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một loại sách!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					label_form.setText("Sửa Thông Tin Loại Sách :");
					peform.setVisible(true);
					txt_Ten.setFocusable(true);
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một loại sách!", "Cảnh báo",
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
					ArrayList<LoaiSach> obj = readExcelFile();
					ArrayList<LoaiSach> objtrung = new ArrayList<LoaiSach>();
					ArrayList<LoaiSach> objkotrung = new ArrayList<LoaiSach>();
					int a = splitArrayList(obj, loaisachbus.getList_LoaiSach(), objtrung, objkotrung);
					if (!objkotrung.isEmpty()) {
						loaisachdao.Insert_NhieuLoaiSach(objkotrung);
						loaisachbus.ThemNhieuLoaiSach_Arr(objkotrung);
					}
					if (!objtrung.isEmpty()) {
						loaisachdao.Edit_NhieuLoaiSach(objtrung);
						for (int i = 0; i < objtrung.size(); i++) {
							if (objtrung.get(i).getTrangThai() == 0) {
								loaisachbus.XoaLoaiSach_Array(objtrung.get(i));
							} else {
								loaisachbus.EditLoaiSach_Array(objtrung.get(i));
							}

						}
					}

					ThemDataVaoJTable(model, loaisachbus.getList_LoaiSach());
					table = new JTable(model);
					if (a > 0)
						JOptionPane.showMessageDialog(null, "Có tất cả " + obj.size() + " nhưng chỉ có "
								+ (obj.size() - a) + " được thực hiện vì " + a + " còn lại không hợp lệ");
					else
						JOptionPane.showMessageDialog(null, "Đã import dữ liệu từ excel vào data");

				} catch (InvalidFormatException ex) {
					Logger.getLogger(DocGiaView.class.getName()).log(Level.SEVERE, null, ex);
				} catch (java.sql.SQLException ex) {
					Logger.getLogger(LoaiSachView.class.getName()).log(Level.SEVERE, null, ex);
				}

			}

		});

	}

	public ArrayList<LoaiSach> readExcelFile() throws InvalidFormatException {
		ArrayList<LoaiSach> obj = new ArrayList<>();
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
					String trangthai = dataFormatter.formatCellValue(row.getCell(2));
					// Add more fields as per your Excel structure
					try {
						int trangThaiInt = Integer.parseInt(trangthai);
						LoaiSach newobj = new LoaiSach(ma, ten, trangThaiInt);
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

	public int splitArrayList(ArrayList<LoaiSach> B, ArrayList<LoaiSach> A, ArrayList<LoaiSach> C,
			ArrayList<LoaiSach> D) {
		int data_loi = 0;
		int data_loi1 = 0;
		// Tạo một bản đồ lưu mã nhân viên của A
		Map<String, LoaiSach> mapA = new HashMap<>();
		for (LoaiSach emp : A) {
			mapA.put(emp.getMaLoai(), emp);
		}

		// Duyệt qua các phần tử trong B để tách thành C và D
		for (LoaiSach emp : B) {
			if (!emp.getMaLoai().isEmpty() && !emp.getTenLoai().isEmpty()
					&& (emp.getTrangThai() == 0 || emp.getTrangThai() == 1) && KiemTraMa(emp.getMaLoai()) == true) {
				if (mapA.containsKey(emp.getMaLoai())) {
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
