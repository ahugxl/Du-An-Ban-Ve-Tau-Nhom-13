package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.GaTau;
import entity.GheNgoi;
import entity.KhachHang;
import entity.LoaiHanhTrinh;
import entity.LoaiVe;
import entity.Thue;
import entity.Ve;

public class Ve_DAO {
	public ArrayList<Ve> getalltbVe(){
		ArrayList<Ve> dsVe = new ArrayList<Ve>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			 String sql = "Select * from Ve";
			 Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql);
			 while(rs.next()) {
				 String maVe = rs.getString(1);
				 String tenVe = rs.getString(2);
				 ChuyenTau ct = new ChuyenTau(rs.getString(3));
				 GheNgoi gn = new GheNgoi(rs.getString(4));
				 GaTau gaDi = new GaTau(rs.getString(5));
				 GaTau gaDen = new GaTau(rs.getString(6));
				 LocalDateTime ngayInVe = rs.getTimestamp(7).toLocalDateTime();
				 LoaiHanhTrinh loaiHanhTrinh = null;
				 String lht = rs.getString(8);
				 if(lht.equals("Thuong")) {
					 loaiHanhTrinh=LoaiHanhTrinh.Thuong;
				 }
				 else if(lht.equals("KhuHoiLuotDi")) {
					 loaiHanhTrinh= LoaiHanhTrinh.KhuHoiLuotDi;
				 }
				 else {
					 loaiHanhTrinh=LoaiHanhTrinh.KhuHoiLuotVe;
				 }
				 LoaiVe loaiVe = null;
				 String lve = rs.getString(9);
				 if(lve.equals("ToanVe")) {
					 loaiVe=LoaiVe.ToanVe;
				 }
				 else if(lve.equals("TreEm")) {
					 loaiVe= LoaiVe.ToanVe;
				 }
				 else if(lve.equals("SinhVien")) {
					 loaiVe= LoaiVe.SinhVien;
				 }
				 else if(lve.equals("MeVNAH")) {
					 loaiVe= LoaiVe.MeVNAH;
				 }
				 else {
					 loaiVe=LoaiVe.NguoiNuocNgoai;
				 }
				 String trangThaiVe = rs.getString(10);
				 boolean coPhongChopVip = rs.getBoolean(11);
				 Thue thueApDung = new Thue(rs.getString(12));
				 KhachHang kh = new KhachHang(rs.getString(13));
				 Ve ve= new Ve(maVe, tenVe, ct, gn, gaDi, gaDen, ngayInVe, loaiHanhTrinh, loaiVe, trangThaiVe, coPhongChopVip, thueApDung, kh);
				 dsVe.add(ve);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsVe;
	}
}
