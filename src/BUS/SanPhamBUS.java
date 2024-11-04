package BUS;

import DAO.SanPhamDAO;
import DAO.ThuongHieuDAO;
import DAO.HeDieuHanhDAO;
import DAO.XuatXuDAO;
import DAO.KhuVucKhoDAO;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
import java.util.ArrayList;

public class SanPhamBUS {

    public final SanPhamDAO spDAO = new SanPhamDAO();
    public PhienBanSanPhamBUS cauhinhBus = new PhienBanSanPhamBUS();
    private ArrayList<SanPhamDTO> listSP = new ArrayList<>();

    public SanPhamBUS() {
        listSP = spDAO.selectAll();
    }

    public ArrayList<SanPhamDTO> getAll() {

        return this.listSP;
    }

    public SanPhamDTO getByIndex(int index) {
        return this.listSP.get(index);
    }

    public SanPhamDTO getByMaSP(int masp) {
        int vitri = -1;
        int i = 0;
        while (i <= this.listSP.size() && vitri == -1) {
            if (this.listSP.get(i).getMasp() == masp) {
                vitri = i;
            } else {
                i++;
            }
        }
        return this.listSP.get(vitri);
    }

    public int getIndexByMaSP(int masanpham) {
        int i = 0;
        int vitri = -1;
        while (i < this.listSP.size() && vitri == -1) {
            if (listSP.get(i).getMasp() == masanpham) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(SanPhamDTO lh, ArrayList<PhienBanSanPhamDTO> listch) {
        boolean check = spDAO.insert(lh) != 0;
        if (check) {
            cauhinhBus.add(listch);
            this.listSP.add(lh);
        }
        return check;
    }

    public Boolean delete(SanPhamDTO lh) {
        boolean check = spDAO.delete(Integer.toString(lh.getMasp())) != 0;
        if (check) {
            this.listSP.remove(lh);
        }
        return check;
    }

    public Boolean update(SanPhamDTO lh) {
        boolean check = spDAO.update(lh) != 0;
        if (check) {
            this.listSP.set(getIndexByMaSP(lh.getMasp()), lh);
        }
        return check;
    }

    public ArrayList<SanPhamDTO> getByMakhuvuc(int makv) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (SanPhamDTO i : this.listSP) {
            if (i.getKhuvuckho() == makv) {
                result.add(i);
            }
        }
        return result;
    }

    public ArrayList<SanPhamDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (SanPhamDTO i : this.listSP) {
            if (Integer.toString(i.getMasp()).toLowerCase().contains(text)
                    || i.getTensp().toLowerCase().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchByType(String type, String text) {
        text = text.toLowerCase();
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        switch (type) {
            case "Mã SP":
                for (SanPhamDTO i : this.listSP) {
                    if (Integer.toString(i.getMasp()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Tên sản phẩm":
                for (SanPhamDTO i : this.listSP) {
                    if (i.getTensp().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Số lượng tồn":
                for (SanPhamDTO i : this.listSP) {
                    if (Integer.toString(i.getSoluongton()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Thương hiệu":
                for (SanPhamDTO i : this.listSP) {
                    if (ThuongHieuDAO.getInstance().selectById(i.getThuonghieu() + "").getTenthuonghieu().toLowerCase()
                            .contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Hệ điều hành":
                for (SanPhamDTO i : this.listSP) {
                    if (HeDieuHanhDAO.getInstance().selectById(i.getHedieuhanh() + "").getTenhdh().toLowerCase()
                            .contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Kích thước màn":
                for (SanPhamDTO i : this.listSP) {
                    if (String.valueOf(i.getKichthuocman()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Chip xử lý":
                for (SanPhamDTO i : this.listSP) {
                    if (i.getChipxuly().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Dung lượng pin":
                for (SanPhamDTO i : this.listSP) {
                    if (Integer.toString(i.getDungluongpin()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Xuất xứ":
                for (SanPhamDTO i : this.listSP) {
                    if (XuatXuDAO.getInstance().selectById(i.getXuatxu() + "").getTenxuatxu().toLowerCase()
                            .contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Khu vực kho":
                for (SanPhamDTO i : this.listSP) {
                    if (KhuVucKhoDAO.getInstance().selectById(i.getKhuvuckho() + "").getTenkhuvuc().toLowerCase()
                            .contains(text)) {
                        result.add(i);
                    }
                }
                break;
            default:
                for (SanPhamDTO i : this.listSP) {
                    if (Integer.toString(i.getMasp()).toLowerCase().contains(text)
                            || i.getTensp().toLowerCase().contains(text)
                            || Integer.toString(i.getSoluongton()).toLowerCase().contains(text)
                            || ThuongHieuDAO.getInstance().selectById(i.getThuonghieu() + "").getTenthuonghieu()
                                    .toLowerCase().contains(text)
                            || HeDieuHanhDAO.getInstance().selectById(i.getHedieuhanh() + "").getTenhdh().toLowerCase()
                                    .contains(text)
                            || String.valueOf(i.getKichthuocman()).toLowerCase().contains(text)
                            || i.getChipxuly().toLowerCase().contains(text)
                            || Integer.toString(i.getDungluongpin()).toLowerCase().contains(text)
                            || XuatXuDAO.getInstance().selectById(i.getXuatxu() + "").getTenxuatxu().toLowerCase()
                                    .contains(text)
                            || KhuVucKhoDAO.getInstance().selectById(i.getKhuvuckho() + "").getTenkhuvuc().toLowerCase()
                                    .contains(text)) {
                        result.add(i);
                    }
                }
                break;
        }
        return result;
    }

    public SanPhamDTO getSp(int mapb) {
        return spDAO.selectByPhienBan(mapb + "");
    }

    public int getQuantity() {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        int n = 0;
        for (SanPhamDTO i : this.listSP) {
            if (i.getSoluongton() != 0) {
                n += i.getSoluongton();
            }
        }
        return n;
    }
}
