package BUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import DATA.NhanVienDAO;
import DATA.UserDAO;
import DTO.NhanVien;
import DTO.User;

public class UserBUS {
    private ArrayList<User> dsUS;

    public UserBUS() {
        list();
    }

    public void list() {
        UserDAO usDAO = new UserDAO();
        dsUS = new ArrayList<>();
        dsUS = usDAO.list();
    }

    public void add(User hd) {
        dsUS.add(hd);
        UserDAO usDAO = new UserDAO();
        usDAO.add(hd);
    }

    public void add(User hd, int i) {
        dsUS.add(hd);

    }

    public void add_all(ArrayList<User> us) {
        for (User i : us) {
            dsUS.add(i);
        }
    }

    public void delete(User userID) {
        for (User hd : dsUS) {
            if (hd.getManv().equals(userID)) {
                dsUS.remove(hd);
                UserDAO usDAO = new UserDAO();
                usDAO.delete(userID);
                return;
            }
        }
    }

    public void delete2(String userID) {
        for (User hd : dsUS) {
            if (hd.getManv().equals(userID)) {
                dsUS.remove(hd);
                UserDAO usDAO = new UserDAO();
                usDAO.delete2(userID);
                return;
            }
        }
    }

    public void set(User s) {
        for (int i = 0; i < dsUS.size(); i++) {
            if (dsUS.get(i).getManv().equals(s.getManv())) {
                dsUS.set(i, s);
                UserDAO usDAO = new UserDAO();
                usDAO.set(s);
                return;
            }
        }
    }

    public User check(String username, char[] pass, String maquyen) {

        for (User us : dsUS) {
            char[] correctPass = us.getPassword().toCharArray();
            if (us.getUsername().equals(username) && Arrays.equals(pass, correctPass) && us.getMacv().equals(maquyen)
                    && us.getTrangthai() == 1) {
                return us;
            }
        }
        return null;
    }

    public ArrayList<User> getList() {
        return dsUS;
    }

    public boolean checkid(String manv) {
        for (User us : dsUS) {
            if (us.getManv().equals(manv)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> search_manv(String manv) {
        ArrayList<User> search = new ArrayList<>();

        manv = manv.isEmpty() ? manv = "" : manv;
        // hoten = hoten.isEmpty() ? hoten = "" : hoten;
        // email=email.isEmpty() ? email ="": email;
        // sdt=sdt.isEmpty()? sdt ="": sdt;
        for (User us : dsUS) {
            if (us.getManv().contains(manv))

            {
                search.add(us);
            }
        }
        return search;
    }

    public ArrayList<User> search_usname(String usn) {
        ArrayList<User> search = new ArrayList<>();

        usn = usn.isEmpty() ? usn = "" : usn;
        // hoten = hoten.isEmpty() ? hoten = "" : hoten;
        // email=email.isEmpty() ? email ="": email;
        // sdt=sdt.isEmpty()? sdt ="": sdt;
        for (User us : dsUS) {
            if (us.getUsername().contains(usn))

            {
                search.add(us);
            }
        }
        return search;
    }

    public String TaoMaNV() {
        int CODE_LENGTH = 4;
        String DIGITS = "0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return codeBuilder.toString();
    }

    // Tạo mã độc giả mới không trùng với bất kì mã nào trong hệ thống
    public String TaoMaNV_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMaNV();
            for (int i = 0; i < dsUS.size(); i++) {
                if (this.dsUS.get(i).getManv().equals(ma))
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

}
