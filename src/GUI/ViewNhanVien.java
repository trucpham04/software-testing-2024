package GUI;

import static java.awt.Font.BOLD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ViewNhanVien extends JFrame implements MouseListener {
	JPanel menu, main, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9, lb10, lb11,
			lb12;
	final int DEFAULT_WIDTH = 1160, DEFAULT_HEIGHT = 730;

	public ViewNhanVien() {
		init();
	}

	public void init() {
		setTitle("Quản lý thư viện");
		getContentPane().setBackground(Color.white);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1452, 730);
		this.setLayout(new BorderLayout(0, 0));
		menu = new JPanel();
		menu.setBackground(Color.white);
		main = new SachView();
		main.setBackground(Color.white);
		menu.setPreferredSize(new Dimension(280, 0));
		main.setPreferredSize(new Dimension(1160, 0));
		menu.setLayout(new FlowLayout(0, 0, 0));
		main.setLayout(new FlowLayout(0, 0, 0));
		this.add(menu, BorderLayout.WEST);
		this.add(main, BorderLayout.CENTER);
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(Color.WHITE);
		p1.setPreferredSize(new Dimension(280, 58));
		p1.setBorder(new LineBorder(Color.PINK, 4, true));
		p2 = new JPanel();
		p2.setLayout(null);
		p2.setBackground(Color.WHITE);
		p2.setPreferredSize(new Dimension(280, 58));
		p2.setBorder(new LineBorder(Color.PINK, 4, true));
		p3 = new JPanel();
		p3.setLayout(null);
		p3.setBackground(Color.WHITE);
		p3.setPreferredSize(new Dimension(280, 58));
		p3.setBorder(new LineBorder(Color.PINK, 4, true));
		p4 = new JPanel();
		p4.setLayout(null);
		p4.setBackground(Color.WHITE);
		p4.setPreferredSize(new Dimension(280, 58));
		p4.setBorder(new LineBorder(Color.PINK, 4, true));
		p5 = new JPanel();
		p5.setLayout(null);
		p5.setBackground(Color.WHITE);
		p5.setPreferredSize(new Dimension(280, 58));
		p5.setBorder(new LineBorder(Color.PINK, 4, true));
		p6 = new JPanel();
		p6.setLayout(null);
		p6.setBackground(Color.WHITE);
		p6.setPreferredSize(new Dimension(280, 58));
		p6.setBorder(new LineBorder(Color.PINK, 4, true));
		p7 = new JPanel();
		p7.setLayout(null);
		p7.setBackground(Color.WHITE);
		p7.setPreferredSize(new Dimension(280, 58));
		p7.setBorder(new LineBorder(Color.PINK, 4, true));
		p8 = new JPanel();
		p8.setLayout(null);
		p8.setBackground(Color.WHITE);
		p8.setPreferredSize(new Dimension(280, 58));
		p8.setBorder(new LineBorder(Color.PINK, 4, true));
		p9 = new JPanel();
		p9.setLayout(null);
		p9.setBackground(Color.WHITE);
		p9.setPreferredSize(new Dimension(280, 58));
		p9.setBorder(new LineBorder(Color.PINK, 4, true));
		p10 = new JPanel();
		p10.setLayout(null);
		p10.setBackground(Color.WHITE);
		p10.setPreferredSize(new Dimension(280, 58));
		p10.setBorder(new LineBorder(Color.PINK, 4, true));
		p11 = new JPanel();
		p11.setLayout(null);
		p11.setBackground(Color.WHITE);
		p11.setPreferredSize(new Dimension(280, 58));
		p11.setBorder(new LineBorder(Color.PINK, 4, true));
		p12 = new JPanel();
		p12.setLayout(null);
		p12.setBackground(Color.WHITE);
		p12.setPreferredSize(new Dimension(280, 56));
		p12.setBorder(new LineBorder(Color.PINK, 4, true));
		ImageIcon a1 = new ImageIcon("src/img/Book.png");
		Image b1 = a1.getImage();
		Image c1 = b1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d1 = new ImageIcon(c1);
		ImageIcon a2 = new ImageIcon("src/img/Book.png");
		Image b2 = a2.getImage();
		Image c2 = b2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d2 = new ImageIcon(c2);
		ImageIcon a3 = new ImageIcon("src/img/Docgia.png");
		Image b3 = a3.getImage();
		Image c3 = b3.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d3 = new ImageIcon(c3);
		ImageIcon a4 = new ImageIcon("src/img/Tacgia.png");
		Image b4 = a4.getImage();
		Image c4 = b4.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d4 = new ImageIcon(c4);
		ImageIcon a5 = new ImageIcon("src/img/Nhaxuatban.png");
		Image b5 = a5.getImage();
		Image c5 = b5.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d5 = new ImageIcon(c5);
		ImageIcon a6 = new ImageIcon("src/img/Nhap.png");
		Image b6 = a6.getImage();
		Image c6 = b6.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d6 = new ImageIcon(c6);
		ImageIcon a7 = new ImageIcon("src/img/Phieumuon.png");
		Image b7 = a7.getImage();
		Image c7 = b7.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d7 = new ImageIcon(c7);
		ImageIcon a8 = new ImageIcon("src/img/Phieutra.png");
		Image b8 = a8.getImage();
		Image c8 = b8.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d8 = new ImageIcon(c8);
		ImageIcon a9 = new ImageIcon("src/img/Nhanvien.png");
		Image b9 = a9.getImage();
		Image c9 = b9.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d9 = new ImageIcon(c9);
		ImageIcon a10 = new ImageIcon("src/img/Taikhoan.png");
		Image b10 = a10.getImage();
		Image c10 = b10.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d10 = new ImageIcon(c10);
		ImageIcon a11 = new ImageIcon("src/img/ThongKe.png");
		Image b11 = a11.getImage();
		Image c11 = b11.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d11 = new ImageIcon(c11);
		ImageIcon a12 = new ImageIcon("src/img/Logout.png");
		Image b12 = a12.getImage();
		Image c12 = b12.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon d12 = new ImageIcon(c12);
		l1 = new JLabel(d1);
		l1.setBounds(20, 14, 30, 30);
		lb1 = new JLabel("Sách");
		lb1.setBounds(55, 14, 200, 30);
		lb1.setFont(new Font("Arial", BOLD, 25));
		lb1.setForeground(Color.pink);
		l2 = new JLabel(d2);
		l2.setBounds(20, 14, 30, 30);
		lb2 = new JLabel("Loại Sách");
		lb2.setBounds(55, 14, 200, 30);
		lb2.setFont(new Font("Arial", BOLD, 25));
		lb2.setForeground(Color.pink);
		l3 = new JLabel(d3);
		l3.setBounds(20, 14, 30, 30);
		lb3 = new JLabel("Độc Giả");
		lb3.setBounds(55, 14, 200, 30);
		lb3.setFont(new Font("Arial", BOLD, 25));
		lb3.setForeground(Color.pink);
		l4 = new JLabel(d4);
		l4.setBounds(20, 14, 30, 30);
		lb4 = new JLabel("Tác Giả");
		lb4.setBounds(55, 14, 200, 30);
		lb4.setFont(new Font("Arial", BOLD, 25));
		lb4.setForeground(Color.pink);
		l5 = new JLabel(d5);
		l5.setBounds(20, 14, 30, 30);
		lb5 = new JLabel("Nhà Xuất Bản");
		lb5.setBounds(55, 14, 200, 30);
		lb5.setFont(new Font("Arial", BOLD, 25));
		lb5.setForeground(Color.pink);
		l6 = new JLabel(d6);
		l6.setBounds(20, 14, 30, 30);
		lb6 = new JLabel("Phiếu Nhập");
		lb6.setBounds(55, 14, 200, 30);
		lb6.setFont(new Font("Arial", BOLD, 25));
		lb6.setForeground(Color.pink);
		l7 = new JLabel(d7);
		l7.setBounds(20, 14, 30, 30);
		lb7 = new JLabel("Phiếu Mượn");
		lb7.setBounds(55, 14, 200, 30);
		lb7.setFont(new Font("Arial", BOLD, 25));
		lb7.setForeground(Color.pink);
		l8 = new JLabel(d8);
		l8.setBounds(20, 14, 30, 30);
		lb8 = new JLabel("Phiếu Trả");
		lb8.setBounds(55, 14, 200, 30);
		lb8.setFont(new Font("Arial", BOLD, 25));
		lb8.setForeground(Color.pink);
		l9 = new JLabel(d9);
		l9.setBounds(20, 14, 30, 30);
		lb9 = new JLabel("Nhân Viên");
		lb9.setBounds(55, 14, 200, 30);
		lb9.setFont(new Font("Arial", BOLD, 25));
		lb9.setForeground(Color.pink);
		l10 = new JLabel(d10);
		l10.setBounds(20, 14, 30, 30);
		lb10 = new JLabel("Tài Khoản");
		lb10.setBounds(55, 14, 200, 30);
		lb10.setFont(new Font("Arial", BOLD, 25));
		lb10.setForeground(Color.pink);
		l11 = new JLabel(d11);
		l11.setBounds(20, 14, 30, 30);
		lb11 = new JLabel("Thống Kê");
		lb11.setBounds(55, 14, 200, 30);
		lb11.setFont(new Font("Arial", BOLD, 25));
		lb11.setForeground(Color.pink);
		l12 = new JLabel(d12);
		l12.setBounds(20, 13, 30, 30);
		lb12 = new JLabel("Đăng Xuất");
		lb12.setBounds(55, 13, 200, 30);
		lb12.setFont(new Font("Arial", BOLD, 25));
		lb12.setForeground(Color.pink);
		menu.add(p1);
		menu.add(p2);
		menu.add(p3);
		menu.add(p4);
		menu.add(p5);
		menu.add(p6);
		menu.add(p7);
		menu.add(p8);
//		menu.add(p9);
//		menu.add(p10);
		menu.add(p11);
		menu.add(p12);
		p1.add(l1);
		p1.add(lb1);
		p2.add(l2);
		p2.add(lb2);
		p3.add(l3);
		p3.add(lb3);
		p4.add(l4);
		p4.add(lb4);
		p5.add(l5);
		p5.add(lb5);
		p6.add(l6);
		p6.add(lb6);
		p7.add(l7);
		p7.add(lb7);
		p8.add(l8);
		p8.add(lb8);
		p9.add(l9);
		p9.add(lb9);
		p10.add(l10);
		p10.add(lb10);
		p11.add(l11);
		p11.add(lb11);
		p12.add(l12);
		p12.add(lb12);
		l1.addMouseListener(this);
		l2.addMouseListener(this);
		l3.addMouseListener(this);
		l4.addMouseListener(this);
		l5.addMouseListener(this);
		l6.addMouseListener(this);
		l7.addMouseListener(this);
		l8.addMouseListener(this);
		l9.addMouseListener(this);
		l10.addMouseListener(this);
		l11.addMouseListener(this);
		l12.addMouseListener(this);
		lb1.addMouseListener(this);
		lb2.addMouseListener(this);
		lb3.addMouseListener(this);
		lb4.addMouseListener(this);
		lb5.addMouseListener(this);
		lb6.addMouseListener(this);
		lb7.addMouseListener(this);
		lb8.addMouseListener(this);
		lb9.addMouseListener(this);
		lb10.addMouseListener(this);
		lb11.addMouseListener(this);
		lb12.addMouseListener(this);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public static void main(String args[]) {
		new ViewNhanVien();
	}

	public void changeInfo(MouseEvent e) {
		if (e.getSource() == l1 || e.getSource() == lb1) {
			SachView p = new SachView();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l2 || e.getSource() == lb2) {
			LoaiSachView p = new LoaiSachView();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l3 || e.getSource() == lb3) {
			DocGiaView p = new DocGiaView();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l4 || e.getSource() == lb4) {
			TacGiaView p = new TacGiaView();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l5 || e.getSource() == lb5) {
			NhaXuatBanView p = new NhaXuatBanView();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l6 || e.getSource() == lb6) {
			PhieuNhapGUI p = new PhieuNhapGUI();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l7 || e.getSource() == lb7) {
			PhieuMuonGUI p = new PhieuMuonGUI();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l8 || e.getSource() == lb8) {
			PhieuTraGUI p =new PhieuTraGUI();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l9 || e.getSource() == lb9) {
			NhanVienView p = new NhanVienView();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l10 || e.getSource() == lb10) {
			UsersView p = new UsersView();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l11 || e.getSource() == lb11) {
			ThongKeView p = new ThongKeView();
			p.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
			main.removeAll();
			main.add(p);
			main.repaint();
			main.validate();
		}
		if (e.getSource() == l12 || e.getSource() == lb12) {
			int option = JOptionPane.showConfirmDialog(null, "Bạn có đồng ý đăng xuất không?", "Xác nhận",
					JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				Login lg = new Login();
				this.dispose();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		changeInfo(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == lb1 || e.getSource() == l1) {
			p1.setBackground(Color.pink);
			lb1.setForeground(Color.white);
		}
		if (e.getSource() == lb2 || e.getSource() == l2) {
			p2.setBackground(Color.pink);
			lb2.setForeground(Color.white);
		}
		if (e.getSource() == lb3 || e.getSource() == l3) {
			p3.setBackground(Color.pink);
			lb3.setForeground(Color.white);
		}
		if (e.getSource() == lb4 || e.getSource() == l4) {
			p4.setBackground(Color.pink);
			lb4.setForeground(Color.white);
		}
		if (e.getSource() == lb5 || e.getSource() == l5) {
			p5.setBackground(Color.pink);
			lb5.setForeground(Color.white);
		}
		if (e.getSource() == lb6 || e.getSource() == l6) {
			p6.setBackground(Color.pink);
			lb6.setForeground(Color.white);
		}
		if (e.getSource() == lb7 || e.getSource() == l7) {
			p7.setBackground(Color.pink);
			lb7.setForeground(Color.white);
		}
		if (e.getSource() == lb8 || e.getSource() == l8) {
			p8.setBackground(Color.pink);
			lb8.setForeground(Color.white);
		}
		if (e.getSource() == lb9 || e.getSource() == l9) {
			p9.setBackground(Color.pink);
			lb9.setForeground(Color.white);
		}
		if (e.getSource() == lb10 || e.getSource() == l10) {
			p10.setBackground(Color.pink);
			lb10.setForeground(Color.white);
		}
		if (e.getSource() == lb11 || e.getSource() == l11) {
			p11.setBackground(Color.pink);
			lb11.setForeground(Color.white);
		}
		if (e.getSource() == lb12 || e.getSource() == l12) {
			p12.setBackground(Color.pink);
			lb12.setForeground(Color.white);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == lb1 || e.getSource() == l1) {
			p1.setBackground(Color.white);
			lb1.setForeground(Color.pink);
		}
		if (e.getSource() == lb2 || e.getSource() == l2) {
			p2.setBackground(Color.white);
			lb2.setForeground(Color.pink);
		}
		if (e.getSource() == lb3 || e.getSource() == l3) {
			p3.setBackground(Color.white);
			lb3.setForeground(Color.pink);
		}
		if (e.getSource() == lb4 || e.getSource() == l4) {
			p4.setBackground(Color.white);
			lb4.setForeground(Color.pink);
		}
		if (e.getSource() == lb5 || e.getSource() == l5) {
			p5.setBackground(Color.white);
			lb5.setForeground(Color.pink);
		}
		if (e.getSource() == lb6 || e.getSource() == l6) {
			p6.setBackground(Color.white);
			lb6.setForeground(Color.pink);
		}
		if (e.getSource() == lb7 || e.getSource() == l7) {
			p7.setBackground(Color.white);
			lb7.setForeground(Color.pink);
		}
		if (e.getSource() == lb8 || e.getSource() == l8) {
			p8.setBackground(Color.white);
			lb8.setForeground(Color.pink);
		}
		if (e.getSource() == lb9 || e.getSource() == l9) {
			p9.setBackground(Color.white);
			lb9.setForeground(Color.pink);
		}
		if (e.getSource() == lb10 || e.getSource() == l10) {
			p10.setBackground(Color.white);
			lb10.setForeground(Color.pink);
		}
		if (e.getSource() == lb11 || e.getSource() == l11) {
			p11.setBackground(Color.white);
			lb11.setForeground(Color.pink);
		}
		if (e.getSource() == lb12 || e.getSource() == l12) {
			p12.setBackground(Color.white);
			lb12.setForeground(Color.pink);
		}
	}

}