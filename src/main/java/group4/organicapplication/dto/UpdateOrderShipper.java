package group4.organicapplication.dto;

import java.util.List;

public class UpdateOrderShipper {

    private long idDonHang;
    private String ghiChuShipper;
    private List<UpdatePurchaseOrder> danhSachCapNhatChiTietDon;

    public static class UpdatePurchaseOrder {
        private long idChiTiet;
//        private int soLuongNhanHang;

        public long getIdChiTiet() {
            return idChiTiet;
        }

        public void setIdChiTiet(long idChiTiet) {
            this.idChiTiet = idChiTiet;
        }
    }

    public long getIdDonHang() {
        return idDonHang;
    }

    public String getGhiChuShipper() {
        return ghiChuShipper;
    }

    public List<UpdatePurchaseOrder> getDanhSachCapNhatChiTietDon() {
        return danhSachCapNhatChiTietDon;
    }

    public void setIdDonHang(long idDonHang) {
        this.idDonHang = idDonHang;
    }

    public void setGhiChuShipper(String ghiChuShipper) {
        this.ghiChuShipper = ghiChuShipper;
    }

    public void setDanhSachCapNhatChiTietDon(List<UpdatePurchaseOrder> danhSachCapNhatChiTietDon) {
        this.danhSachCapNhatChiTietDon = danhSachCapNhatChiTietDon;
    }
}
