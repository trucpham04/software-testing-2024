/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.NhanVienBUS;
import BUS.UserBUS;
import DTO.NhanVien;
import DTO.User;
import static java.awt.Font.BOLD;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Shadow
 */
public class Login extends JFrame implements KeyListener {
	private UserBUS usBUS = new UserBUS();
	private NhanVienBUS nvBUS = new NhanVienBUS();
	private int DEFAULT_HEIGHT = 500, DEFAULT_WIDTH = 600;
	private JButton btnLogin, btnLogout;
	private JLabel user, pwd, usericon, pwdicon, header;
	private JTextField us;
	private JPasswordField pw;

	public Login() {
		init();
	}

	public void init() {

		setLayout(null);
		setTitle("Đăng nhập");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.white);
		ImageIcon iconuser = new ImageIcon("src/img/User.png");
		Image originalImageuser = iconuser.getImage();
		Image scaledImageuser = originalImageuser.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		ImageIcon scaledIconuser = new ImageIcon(scaledImageuser);
		ImageIcon icon = new ImageIcon("src/img/Lock.png");
		Image originalImage = icon.getImage();
		Image scaledImage = originalImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImageuser);
		ImageIcon a = new ImageIcon("src/img/Login.png");
		Image b = a.getImage();
		Image c = b.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon d = new ImageIcon(c);
		JPanel header_p = new JPanel();
		header_p.setBackground(Color.decode("#9C27B0"));
		header_p.setLayout(new FlowLayout());
		header_p.setBounds(0, 0, 600, 70);
		header = new JLabel("QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
		header.setPreferredSize(new Dimension(300, 60));
		header.setFont(new Font("Arial", BOLD, 30));
		header.setForeground(Color.white);

		user = new JLabel("Tên đăng nhập");
		pwd = new JLabel("Mật khẩu");
		usericon = new JLabel(scaledIconuser);
		usericon.setBounds(100, 110, 15, 20);
		user.setBounds(120, 110, 200, 20);
		user.setFont(new Font("Arial", BOLD, 15));

		pwdicon = new JLabel(scaledIcon);
		pwdicon.setBounds(100, 200, 15, 20);
		pwd.setBounds(120, 200, 100, 20);
		pwd.setFont(new Font("Arial", BOLD, 15));

		us = new JTextField();
		us.setFont(new Font("Arial", BOLD, 18));
		pw = new JPasswordField();
		pw.setFont(new Font("Arial", BOLD, 18));
		us.setBounds(100, 130, 400, 50);
		pw.setBounds(100, 220, 400, 50);
		btnLogin = new JButton("Đăng nhập", d);
		btnLogin.setBounds(200, 330, 200, 50);
		btnLogin.setBackground(Color.decode("#9C27B0"));
		btnLogin.setFont(new Font("Arial", BOLD, 20));
		btnLogin.setForeground(Color.white);
		btnLogin.requestFocus();
		this.add(user);
		this.add(pwd);
		this.add(usericon);
		this.add(pwdicon);
		this.add(pwd);
		this.add(us);
		this.add(pw);
		this.add(header_p);
		header_p.add(header);
		this.add(btnLogin);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);

//        exit.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e)
//            {
//                System.exit(0);
//            }
//        });

		us.addMouseListener((new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				us.setText("");
			}
		}));
		us.addKeyListener(this);

		us.addMouseListener((new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				us.setText("");
			}
		}));
		us.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				us.setText("");
			}
		});
		us.addKeyListener(this);

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (usBUS.getList() == null) {
					usBUS.list();
				}
				String username = us.getText();
				char[] passwd = pw.getPassword();
				User user1 = usBUS.check(username, passwd, "0001");
				User user2 = usBUS.check(username, passwd, "0002");
				if (user1 == null && user2 == null) {
					JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu");
					return;
				} else if (user1 != null) {
					GUI.ViewQuanLy a = new ViewQuanLy();
					dispose();
					return;
				} else if (user2 != null) {
					ViewNhanVien a = new ViewNhanVien();
					dispose();
					return;
				}
			}
		});
	}

	public static void main(String[] args) {

		Login lg = new Login();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object a = e.getSource();
		if (a.equals(us) || a.equals(pw)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnLogin.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
