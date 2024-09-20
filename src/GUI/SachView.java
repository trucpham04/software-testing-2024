/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.LoaiSachBUS;
import BUS.NhaXuatBanBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;
import BUS.TacGiaBUS;
import DATA.SachDAO;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.NhanVien;
import DTO.Sach;
import DTO.TacGia;

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
import javax.swing.JTextArea;
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
public class SachView extends JPanel {
	private final SachBUS sachbus = new SachBUS();
	private final SachDAO sachdao = new SachDAO();
	private final PhieuNhapBUS pnbus = new PhieuNhapBUS();
	private JLabel label_Title;
	private JPanel panel_Title;

	private JComboBox combobox, txt_Tgia, txt_NXB, txt_mals;
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
	private JLabel label_Mota;
	private JTextArea txt_mota;
	private JLabel label_namXB;
	private JTextField txt_namXB;
	private JLabel label_Tgia;
	private JLabel label_NXB;
	private JLabel label_dongia;
	private JTextField txt_dongia;
	private JLabel label_soluong;
	private JTextField txt_soluong;
	private JLabel label_mals;
	private JButton reset;
	private JButton peform;

	private boolean isButtonInsertSelected = false;
	private boolean isButtonEditSelected = false;
	private boolean isButtonDeleteSelected = false;
	private Sach objsach = new Sach();
	private TacGiaBUS tgbus = new TacGiaBUS();
	private NhaXuatBanBUS nxbbus = new NhaXuatBanBUS();
	private LoaiSachBUS lsbus = new LoaiSachBUS();

	public void TimTheoIDSach() {
		// Tìm kiếm theo id
		String userInput = txt_Search.getText();

		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập mã sách mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<Sach> s = sachbus.Tim_Theo_ID_Sach(userInput);
			// Danh sách rỗng
			if (s.isEmpty() == true)
				JOptionPane.showMessageDialog(null, "Không tìm thấy sách nào có mã giống với mã mà bạn đang tìm kiếm.");
			else {
				ThemDataVaoJTable(model, s);
				table.setModel(model);
			}
			txt_Search.setText("");

		}
	}

	public void TimTheoIDTG() {
		// Tìm kiếm theo id
		String userInput = txt_Search.getText();

		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập tên tác giả mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<Sach> s = sachbus.Tim_Theo_Ten_TG(userInput);
			// Danh sách rỗng
			if (s.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy tác giả nào có mã giống với tên mà bạn đang tìm kiếm.");
			else {
				ThemDataVaoJTable(model, s);
				table.setModel(model);
			}
			txt_Search.setText("");

		}
	}

	public void TimTheoIDNXB() {
		// Tìm kiếm theo id
		String userInput = txt_Search.getText();

		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập tên nhà xuất bản mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<Sach> s = sachbus.Tim_Theo_Ten_NXB(userInput);

			if (s.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy nhà xuất bản nào có tên giống với mã mà bạn đang tìm kiếm.");
			else {
				ThemDataVaoJTable(model, s);
				table.setModel(model);
			}
			txt_Search.setText("");

		}
	}

	public void TimTheoIDLS() {
		// Tìm kiếm theo id
		String userInput = txt_Search.getText();

		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập tên thể loại sách mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<Sach> s = sachbus.Tim_Theo_Ten_LS(userInput);

			if (s.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy thể loại sách nào có tên giống với mã mà bạn đang tìm kiếm.");
			else {
				ThemDataVaoJTable(model, s);
				table.setModel(model);
			}
			txt_Search.setText("");

		}
	}

	public void TimTheoTen() {
		String userInput = txt_Search.getText();
		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập tên sách mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<Sach> s = sachbus.Tim_Theo_Ten(userInput);
			// Danh sách rỗng
			if (s.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy sách nào có tên giống với tên sách mà bạn đang tìm kiếm.");
			else {
				ThemDataVaoJTable(model, s);
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

	public void TimTheoMoTa() {
		String userInput = txt_Search.getText();

		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập mô tả của sách mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {

			ArrayList<Sach> s = sachbus.Tim_Theo_MoTa(userInput);
			// Danh sách rỗng
			if (s.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy sách nào có mô tả giống với mô tả mà bạn đang tìm kiếm.");
			else {
				ThemDataVaoJTable(model, s);
				table.setModel(model);
			}
			txt_Search.setText("");

		}

	}

	public void TimTheoDG() {
		String userInput = txt_Search.getText();
		int userip = Integer.parseInt(userInput);
		if (userInput.isEmpty())
			JOptionPane.showMessageDialog(null, "Hãy nhập giá của sách mà bạn muốn tìm kiếm !", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		else {
			ArrayList<Sach> tacgiatrung = sachbus.Tim_Theo_Gia(userip);
			// Danh sách rỗng
			if (tacgiatrung.isEmpty() == true)
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy sách nào có đơn giá giống với đơn giá mà bạn đang tìm kiếm.");
			else {
				ThemDataVaoJTable(model, tacgiatrung);
				table.setModel(model);
			}
			txt_Search.setText("");

		}
	}

	public void clear() {
		MA.setText("");
		txt_mota.setText("");
		txt_namXB.setText("");
		txt_Ten.setText("");
		getTxt_Tgia().getSelectedItem();
		txt_dongia.setText("");
		txt_NXB.getSelectedItem();
		txt_soluong.setText("");
		txt_mals.getSelectedItem();
		txt_mota.setFocusable(false);
		txt_namXB.setFocusable(false);
		txt_Ten.setFocusable(false);
		getTxt_Tgia().setEditable(false);
		txt_dongia.setFocusable(false);
		txt_NXB.setEditable(false);
		txt_soluong.setFocusable(false);
		txt_mals.setEditable(false);
		reset.setVisible(false);
		peform.setVisible(false);
	}

	public void Xoa() {
		label_form.setText("Xóa Thông Tin Sách");
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa thông sách này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String masach = MA.getText();
			String ten = txt_Ten.getText();
			String mota = txt_mota.getText();
			String namxb = txt_namXB.getText();
			String matg = (String) getTxt_Tgia().getSelectedItem();
			String nxb = (String) txt_NXB.getSelectedItem();
			String dongia = txt_dongia.getText();
			String soluong = txt_soluong.getText();
			String mals = (String) txt_mals.getSelectedItem();
			int selectedIndex = txt_NXB.getSelectedIndex();
			NhaXuatBan nxbthem = sachbus.get_ListNhaXuatBan().get(selectedIndex);
			objsach.setMaNXB(nxbthem);
			int selectedIndex1 = txt_Tgia.getSelectedIndex();
			TacGia nxbthem1 = sachbus.get_ListTacGia().get(selectedIndex1);
			objsach.setTacGia(nxbthem1);
			int selectedIndex2 = txt_mals.getSelectedIndex();
			LoaiSach nxbthem2 = sachbus.get_ListLoaiSach().get(selectedIndex2);
			objsach.setMaLS(nxbthem2);
			int dg = Integer.parseInt(dongia);
			int sl = Integer.parseInt(soluong);
			objsach.setMaSach(masach);
			objsach.setTenSach(ten);
			objsach.setMoTa(mota);
			objsach.setNamXB(namxb);
			objsach.setDonGia(dg);
			objsach.setSoLuong(sl);
			objsach.setTrangThai(1);
			if (ten.isEmpty() || mota.isEmpty() || namxb.isEmpty() || dongia.isEmpty() || soluong.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền chính xác và đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				int row = sachdao.XoaSach_Data(objsach);
				if (row > 0) {
					sachbus.XoaSach_Array(objsach);
					ThemDataVaoJTable(model, sachbus.getList_Sach());
					clear();
					JOptionPane.showMessageDialog(null, "Đã xóa thành công !");

				} else {
					JOptionPane.showMessageDialog(null, "Đã có lỗi xảy ra.");

				}
			}
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
		int option = JOptionPane.showConfirmDialog(null, "Bạn có sửa thông tin sách này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String masach = MA.getText();
			String ten = txt_Ten.getText();
			String mota = txt_mota.getText();
			String namxb = txt_namXB.getText();
			String matg = (String) getTxt_Tgia().getSelectedItem();
			String nxb = (String) txt_NXB.getSelectedItem();
			String dongia = txt_dongia.getText();
			String soluong = txt_soluong.getText();
			String mals = (String) txt_mals.getSelectedItem();
			int selectedIndex = txt_NXB.getSelectedIndex();
			NhaXuatBan nxbthem = sachbus.get_ListNhaXuatBan().get(selectedIndex);
			objsach.setMaNXB(nxbthem);
			int selectedIndex1 = txt_Tgia.getSelectedIndex();
			TacGia nxbthem1 = sachbus.get_ListTacGia().get(selectedIndex1);
			objsach.setTacGia(nxbthem1);
			int selectedIndex2 = txt_mals.getSelectedIndex();
			LoaiSach nxbthem2 = sachbus.get_ListLoaiSach().get(selectedIndex2);
			objsach.setMaLS(nxbthem2);
			int dg = Integer.parseInt(dongia);
			int sl = Integer.parseInt(soluong);
			objsach.setMaSach(masach);
			objsach.setTenSach(ten);
			objsach.setMoTa(mota);
			objsach.setNamXB(namxb);
			objsach.setDonGia(dg);
			objsach.setSoLuong(sl);
			objsach.setTrangThai(1);
			if (ten.isEmpty() || mota.isEmpty() || namxb.isEmpty() || dongia.isEmpty() || soluong.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền chính xác và đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {

				int row = sachdao.EditSach_Data(objsach);
				if (row > 0) {
					sachbus.EditSach_Array(objsach);
					ThemDataVaoJTable(model, sachbus.getList_Sach());
					table.setModel(model);
					clear();
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
			clear();

			reset.setVisible(false);
			peform.setVisible(false);

		}
		isButtonEditSelected = false;
		System.out.printf("%s", isButtonEditSelected);
		table.clearSelection();

	}

	public void Them() {
		int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm sách này không?", "Xác nhận",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			// Thực hiện thao tác khi người dùng nhấn OK
			// Đặt các đoạn mã của bạn ở đây

			String masach = MA.getText();
			String ten = txt_Ten.getText();
			String mota = txt_mota.getText();
			String namxb = txt_namXB.getText();
			String matg = (String) getTxt_Tgia().getSelectedItem();
			String nxb = (String) txt_NXB.getSelectedItem();
			String dongia = txt_dongia.getText();
			String soluong = txt_soluong.getText();
			String mals = (String) txt_mals.getSelectedItem();
			int selectedIndex = txt_NXB.getSelectedIndex();
			NhaXuatBan nxbthem = sachbus.get_ListNhaXuatBan().get(selectedIndex);
			objsach.setMaNXB(nxbthem);
			int selectedIndex1 = txt_Tgia.getSelectedIndex();
			TacGia nxbthem1 = sachbus.get_ListTacGia().get(selectedIndex1);
			objsach.setTacGia(nxbthem1);
			int selectedIndex2 = txt_mals.getSelectedIndex();
			LoaiSach nxbthem2 = sachbus.get_ListLoaiSach().get(selectedIndex2);
			objsach.setMaLS(nxbthem2);
			int dg = Integer.parseInt(dongia);
			int sl = Integer.parseInt(soluong);
			objsach.setMaSach(masach);
			objsach.setTenSach(ten);
			objsach.setMoTa(mota);
			objsach.setNamXB(namxb);
			objsach.setDonGia(dg);
			objsach.setSoLuong(sl);
			objsach.setTrangThai(1);
			if (ten.isEmpty() || mota.isEmpty() || namxb.isEmpty() || dongia.isEmpty() || soluong.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền chính xác và đầy đủ thông tin!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {

				int a = sachdao.insertSach_Data(objsach);
				if (a > 0) {
					sachbus.ThemSach_Arr(objsach);
					ThemDataVaoJTable(model, sachbus.getList_Sach());
					table.setModel(model);
					clear();
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
			clear();
			reset.setVisible(false);
			peform.setVisible(false);
		}
		isButtonInsertSelected = false;

	}

	public void ThemDataVaoJTable(DefaultTableModel model, ArrayList<Sach> data) {
		model.setRowCount(0); // Xóa tất cả dữ liệu cũ trong JTable
		for (Sach s : data) {
			// Đảm bảo dữ liệu được thêm vào theo đúng thứ tự của các cột trong model
			model.addRow(new Object[] { s.getMaSach(), s.getTenSach(), s.getMoTa(), s.getNamXB(),
					s.getTacGia().getTenTG(), s.getMaNXB().getTenNXB(), s.getDonGia(), s.getSoLuong(),
					s.getMaLS().getTenLoai() });
		}
	}

	public SachView() {
		DocGiaGUI();
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

	public void DocGiaGUI() {
		// Lấy dữ liệu đổ xuống arraylist
		sachbus.DanhSachlist();
		;

		// Title
		label_Title = new JLabel("Sách");
		label_Title.setFont(new Font("Arial", BOLD, 50));
		label_Title.setForeground(Color.WHITE);
		panel_Title = new JPanel(new FlowLayout());
		panel_Title.setBackground(Color.PINK);
		panel_Title.setPreferredSize(new Dimension(1160, 60));
		panel_Title.add(label_Title);

		String[] combobox_item = { "Tìm Kiếm Theo ID Sách", "Tìm Kiếm Theo Tên Sách", "Tìm Kiếm Theo Tên Tác Giả",
				"Tìm Kiếm Theo Tên NXB", "Tìm Kiếm Theo Thể Loại Sách", "Tìm Kiếm Theo Mô Tả",
				"Tìm Kiếm Theo Đơn Giá" };
		combobox = new JComboBox(combobox_item);
		combobox.setPreferredSize(new Dimension(250, 40));
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

		// String[] columnNames = { "Mã Sách", "Tên Sách", "Mô Tả", "Năm XB", "Tác Giả",
		// "NXB", "Đơn Giá", "Số Lượng",
		// "Trạng Thái", "Loại Sách" };
		String[] columnNames = { "Mã Sách", "Tên Sách", "Mô Tả", "Năm XB", "Tác Giả", "NXB", "Đơn Giá", "Số Lượng",
				"Loại Sách" };
		model = new DefaultTableModel(columnNames, 0);
		ThemDataVaoJTable(model, sachbus.getList_Sach());
		table = new JTable(model);
		table.setDefaultEditor(Object.class, null);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(90);
		columnModel.getColumn(1).setPreferredWidth(100);
		columnModel.getColumn(2).setPreferredWidth(150);
		columnModel.getColumn(3).setPreferredWidth(90);
		columnModel.getColumn(4).setPreferredWidth(110);
		columnModel.getColumn(5).setPreferredWidth(100);
		columnModel.getColumn(6).setPreferredWidth(100);
		columnModel.getColumn(7).setPreferredWidth(90);
		columnModel.getColumn(8).setPreferredWidth(100);
		// columnModel.getColumn(9).setPreferredWidth(130);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
		// table.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);

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
		panel_Table.setPreferredSize(new Dimension(1160, 155));
		panel_Table.setBackground(Color.WHITE);
		panel_Table.add(new JScrollPane(table));

		label_form = new JLabel("Hiển Thị Thông Tin Sách");
		label_form.setFont(new Font("Arial", BOLD, 20));
		JPanel panel_label_form = new JPanel();
		panel_label_form.add(label_form);
		panel_label_form.setBackground(Color.WHITE);
		label_MA = new JLabel("Mã Sách :");
		label_MA.setFont(new Font("Arial", BOLD, 19));
		label_MA.setPreferredSize(new Dimension(150, 40));
		MA = new JLabel("");
		MA.setPreferredSize(new Dimension(150, 40));
		MA.setFont(new Font("Arial", Font.ITALIC, 19));
		label_Ten = new JLabel("Tên Sách :");
		label_Ten.setPreferredSize(new Dimension(150, 40));
		label_Ten.setFont(new Font("Arial", BOLD, 19));
		label_Mota = new JLabel("Mô Tả :");
		label_Mota.setPreferredSize(new Dimension(150, 40));
		label_Mota.setFont(new Font("Arial", BOLD, 19));
		label_namXB = new JLabel("Năm Xuất Bản :");
		label_namXB.setPreferredSize(new Dimension(150, 40));
		label_namXB.setFont(new Font("Arial", BOLD, 19));
		label_Tgia = new JLabel("Tác Giả :");
		label_Tgia.setPreferredSize(new Dimension(150, 40));
		label_Tgia.setFont(new Font("Arial", BOLD, 19));
		label_NXB = new JLabel("Nhà Xuất Bản :");
		label_NXB.setPreferredSize(new Dimension(150, 40));
		label_NXB.setFont(new Font("Arial", BOLD, 19));
		label_dongia = new JLabel("Đơn Giá :");
		label_dongia.setPreferredSize(new Dimension(150, 40));
		label_dongia.setFont(new Font("Arial", BOLD, 19));
		label_soluong = new JLabel("Số Lượng :");
		label_soluong.setPreferredSize(new Dimension(150, 40));
		label_soluong.setFont(new Font("Arial", BOLD, 19));
		label_mals = new JLabel("Thể loại :");
		label_mals.setPreferredSize(new Dimension(150, 40));
		label_mals.setFont(new Font("Arial", BOLD, 19));
		txt_Ten = new JTextField();
		txt_Ten.setPreferredSize(new Dimension(275, 40));
		txt_Ten.setFont(new Font("Arial", BOLD, 19));
		txt_Ten.setFocusable(false);
		txt_mota = new JTextArea();
		txt_mota.setLineWrap(true);
		txt_mota.setWrapStyleWord(true);
		txt_mota.setFont(new Font("Arial", BOLD, 19));
		txt_mota.setFocusable(false);
		JScrollPane scrollPane = new JScrollPane(txt_mota);
		scrollPane.setPreferredSize(new Dimension(780, 39));
		txt_namXB = new JTextField();
		txt_namXB.setPreferredSize(new Dimension(275, 40));
		txt_namXB.setFont(new Font("Arial", BOLD, 19));
		txt_namXB.setFocusable(false);
		String[] modelcoboboxnxb = TaoModelTacGia(sachbus.get_ListTacGia());
		txt_Tgia = new JComboBox<>(modelcoboboxnxb);
		getTxt_Tgia().setPreferredSize(new Dimension(275, 40));
		getTxt_Tgia().setFont(new Font("Arial", BOLD, 19));
		getTxt_Tgia().setEditable(false);
		String[] nxb = TaoModelNhaXuatBan(sachbus.get_ListNhaXuatBan());
		txt_NXB = new JComboBox<>(nxb);
		txt_NXB.setPreferredSize(new Dimension(275, 40));
		txt_NXB.setFont(new Font("Arial", BOLD, 19));
		txt_NXB.setEditable(false);
		txt_dongia = new JTextField();
		txt_dongia.setPreferredSize(new Dimension(275, 40));
		txt_dongia.setFont(new Font("Arial", BOLD, 19));
		txt_dongia.setFocusable(false);
		txt_soluong = new JTextField();
		txt_soluong.setPreferredSize(new Dimension(275, 40));
		txt_soluong.setFont(new Font("Arial", BOLD, 19));
		txt_soluong.setFocusable(false);
		String[] ls = TaoModelLoaiSach(sachbus.get_ListLoaiSach());
		txt_mals = new JComboBox<>(ls);
		txt_mals.setPreferredSize(new Dimension(275, 40));
		txt_mals.setFont(new Font("Arial", BOLD, 19));
		txt_mals.setEditable(false);

		panel_form = new JPanel(new GridLayout(2, 2));

		JPanel panel_MA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_MA.add(label_MA);
		panel_MA.add(MA);
		panel_MA.setPreferredSize(new Dimension(500, 50));
		panel_MA.setBackground(Color.WHITE);

		JPanel panel_TEN = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_TEN.add(label_Ten);
		panel_TEN.add(txt_Ten);
		panel_TEN.setPreferredSize(new Dimension(500, 50));
		panel_TEN.setBackground(Color.WHITE);

		JPanel panel_NAMXB = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_NAMXB.add(label_namXB);
		panel_NAMXB.add(txt_namXB);
		panel_NAMXB.setPreferredSize(new Dimension(500, 50));
		panel_NAMXB.setBackground(Color.WHITE);

		JPanel panel_MOTA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_MOTA.add(label_Mota);
		panel_MOTA.add(scrollPane);
		panel_MOTA.setPreferredSize(new Dimension(1000, 50));
		panel_MOTA.setBackground(Color.WHITE);

		JPanel panel_TACGIA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_TACGIA.add(label_Tgia);
		panel_TACGIA.add(getTxt_Tgia());
		panel_TACGIA.setPreferredSize(new Dimension(500, 50));
		panel_TACGIA.setBackground(Color.WHITE);

		JPanel panel_NXB = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_NXB.add(label_NXB);
		panel_NXB.add(txt_NXB);
		panel_NXB.setPreferredSize(new Dimension(500, 50));
		panel_NXB.setBackground(Color.WHITE);

		JPanel panel_DONGIA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_DONGIA.add(label_dongia);
		panel_DONGIA.add(txt_dongia);
		panel_DONGIA.setPreferredSize(new Dimension(500, 50));
		panel_DONGIA.setBackground(Color.WHITE);

		JPanel panel_SOLUONG = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_SOLUONG.add(label_soluong);
		panel_SOLUONG.add(txt_soluong);
		panel_SOLUONG.setPreferredSize(new Dimension(500, 50));
		panel_SOLUONG.setBackground(Color.WHITE);

		JPanel panel_MALS = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_MALS.add(label_mals);
		panel_MALS.add(txt_mals);
		panel_MALS.setPreferredSize(new Dimension(500, 50));
		panel_MALS.setBackground(Color.WHITE);

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
		panel_form.add(panel_NAMXB);
		panel_form.add(panel_TACGIA);
		panel_form.add(panel_NXB);
		panel_form.add(panel_DONGIA);
		panel_form.add(panel_SOLUONG);
		panel_form.add(panel_MALS);
		panel_form.add(panel_MOTA);
		panel_form.setPreferredSize(new Dimension(1160, 300));
		panel_form.setBackground(Color.WHITE);
		panel_form.setLayout(new FlowLayout());

		panel_main_form = new JPanel(new BorderLayout());
		panel_main_form.setBackground(Color.WHITE);
		panel_main_form.setPreferredSize(new Dimension(1160, 360));
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
						TimTheoIDSach();
						break;
					case 1:
						TimTheoTen();
						break;
					case 2:
						TimTheoIDTG();
						break;
					case 3:
						TimTheoIDNXB();
						break;
					case 4:

						TimTheoIDLS();
						break;
					case 5:
						TimTheoMoTa();

						break;
					case 6:

						TimTheoDG();

						break;
				}

			}

		});

		// Tạo sự kiện home
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				reset.setVisible(false);
				peform.setVisible(false);
				ThemDataVaoJTable(model, sachbus.getList_Sach());
				table.setModel(model);
			}
		});

		// Thêm tác giả
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label_form.setText("Thêm Độc Giả");
				txt_mota.setText("");
				txt_namXB.setText("");
				txt_Ten.setText("");
				getTxt_Tgia().setSelectedItem("");
				txt_dongia.setText("");
				txt_NXB.setSelectedItem("");
				txt_soluong.setText("");
				txt_mals.setSelectedItem("");
				txt_mota.setFocusable(true);
				txt_namXB.setFocusable(true);
				txt_Ten.setFocusable(true);
				getTxt_Tgia().setEditable(true);
				txt_dongia.setFocusable(true);
				txt_NXB.setEditable(true);
				txt_soluong.setFocusable(true);
				txt_mals.setEditable(true);
				reset.setVisible(true);
				peform.setVisible(true);
				// Tạo mã tác giả duy nhất
				String matgia = sachbus.TaoMaSach_DuyNhat();
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
					label_form.setText("Hiển thị thông tin sách");
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
						Object masach = table.getValueAt(selectedRow, 0);
						Object ten = table.getValueAt(selectedRow, 1);
						Object mota = table.getValueAt(selectedRow, 2);
						Object namxb = table.getValueAt(selectedRow, 3);
						Object tacgia = table.getValueAt(selectedRow, 4);
						Object nxb = table.getValueAt(selectedRow, 5);
						Object dongia = table.getValueAt(selectedRow, 6);
						Object soluong = table.getValueAt(selectedRow, 7);
						Object mals = table.getValueAt(selectedRow, 9);
						String dg = String.valueOf(dongia);
						String sl = String.valueOf(soluong);
						label_form = new JLabel("Hiển Thị Thông Tin Sách");
						MA.setText((String) masach);
						txt_mota.setText((String) mota);
						txt_namXB.setText((String) namxb);
						txt_Ten.setText((String) ten);
						getTxt_Tgia().setSelectedItem(tacgia);
						txt_dongia.setText(dg);
						txt_NXB.setSelectedItem(nxb);
						txt_soluong.setText((sl));
						txt_mals.setSelectedItem(mals);
						txt_mota.setFocusable(false);
						txt_namXB.setFocusable(false);
						txt_Ten.setFocusable(false);
						getTxt_Tgia().setEditable(false);
						txt_dongia.setFocusable(false);
						txt_NXB.setEditable(false);
						txt_soluong.setFocusable(false);
						txt_mals.setEditable(false);
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một sách!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				} else {
					label_form.setText("Sửa Thông Tin Sách");
					peform.setVisible(true);
					txt_mota.setFocusable(true);
					txt_namXB.setFocusable(true);
					txt_Ten.setFocusable(true);
					getTxt_Tgia().setEditable(true);
					txt_dongia.setFocusable(true);
					txt_NXB.setEditable(true);
					txt_soluong.setFocusable(true);
					txt_mals.setEditable(true);
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
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một sách!", "Cảnh báo",
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
					ArrayList<Sach> obj = readExcelFile();
					ArrayList<Sach> objtrung = new ArrayList<Sach>();
					ArrayList<Sach> objkotrung = new ArrayList<Sach>();
					int a = splitArrayList(obj, sachbus.getList_Sach(), objtrung, objkotrung);
					if (!objkotrung.isEmpty()) {
						sachdao.Insert_NhieuSach(objkotrung);
						sachbus.ThemNhieuSach_Arr(objkotrung);
					}
					if (!objtrung.isEmpty()) {
						sachdao.Edit_NhieuSach(objtrung);
						for (int i = 0; i < objtrung.size(); i++) {
							if (objtrung.get(i).getTrangThai() == 0) {
								sachbus.XoaSach_Array(objtrung.get(i));
							} else {
								sachbus.EditSach_Array(objtrung.get(i));
							}

						}
					}

					ThemDataVaoJTable(model, sachbus.getList_Sach());
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

	public ArrayList<Sach> readExcelFile() throws InvalidFormatException {
		ArrayList<Sach> obj = new ArrayList<>();
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

					String masach = dataFormatter.formatCellValue(row.getCell(0));
					String ten = dataFormatter.formatCellValue(row.getCell(1));
					String mota = dataFormatter.formatCellValue(row.getCell(2));
					String namxb = dataFormatter.formatCellValue(row.getCell(3));
					Object matg = dataFormatter.formatCellValue(row.getCell(4));
					Object nxb = dataFormatter.formatCellValue(row.getCell(5));
					String dongia = dataFormatter.formatCellValue(row.getCell(6));
					String soluong = dataFormatter.formatCellValue(row.getCell(7));
					String trangthai = dataFormatter.formatCellValue(row.getCell(8));
					Object mals = dataFormatter.formatCellValue(row.getCell(9));

					// Add more fields as per your Excel structure
					try {
						int trangThaiInt = Integer.parseInt(trangthai);
						int dg = Integer.parseInt(dongia);
						int sl = Integer.parseInt(soluong);
						int selectedIndex = txt_NXB.getSelectedIndex();
						NhaXuatBan nxbthem = sachbus.get_ListNhaXuatBan().get(selectedIndex);
						objsach.setMaNXB(nxbthem);
						int selectedIndex1 = txt_Tgia.getSelectedIndex();
						TacGia nxbthem1 = sachbus.get_ListTacGia().get(selectedIndex1);
						objsach.setTacGia(nxbthem1);
						int selectedIndex2 = txt_mals.getSelectedIndex();
						LoaiSach nxbthem2 = sachbus.get_ListLoaiSach().get(selectedIndex2);
						objsach.setMaLS(nxbthem2);
						objsach.setMaSach(masach);
						objsach.setTenSach(ten);
						objsach.setMoTa(mota);
						objsach.setNamXB(namxb);
						objsach.setDonGia(dg);
						objsach.setSoLuong(sl);
						objsach.setTrangThai(trangThaiInt);
						obj.add(objsach);
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

	public int splitArrayList(ArrayList<Sach> B, ArrayList<Sach> A, ArrayList<Sach> C, ArrayList<Sach> D) {
		int data_loi = 0;
		int data_loi1 = 0;
		// Tạo một bản đồ lưu mã nhân viên của A
		Map<String, Sach> mapA = new HashMap<>();
		for (Sach emp : A) {
			mapA.put(emp.getMaSach(), emp);
		}

		// Duyệt qua các phần tử trong B để tách thành C và D
		for (Sach emp : B) {
			if (!emp.getMaSach().isEmpty() && !emp.getTenSach().isEmpty() && !emp.getMoTa().isEmpty()
					&& !emp.getNamXB().isEmpty() && !emp.getTacGia().getMaTG().isEmpty()
					&& !emp.getMaNXB().getMaNXB().isEmpty() && (emp.getSoLuong() != 0) && (emp.getDonGia() != 0)
					&& !emp.getMaLS().getMaLoai().isEmpty() && (emp.getTrangThai() == 0 || emp.getTrangThai() == 1)
					&& KiemTraMa(emp.getMaSach()) == true) {
				if (mapA.containsKey(emp.getMaSach())) {
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

	public JComboBox getTxt_Tgia() {
		return txt_Tgia;
	}

	public void setTxt_Tgia(JComboBox txt_Tgia) {
		this.txt_Tgia = txt_Tgia;
	}

}
