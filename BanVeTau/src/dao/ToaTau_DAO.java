package dao;

import entity.*;
import connectDB.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToaTau_DAO {
    public List<ToaTau> getToaByTau(String maTau) {
        List<ToaTau> list = new ArrayList<>();
        String sql = "SELECT maToaTau, tenToaTau, thuTuToa, maLoaiToa, soLuongGhe, heSoHangToa " +
                     "FROM ToaTau WHERE maTau = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTau);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	String maLoaiToaFromDB = rs.getString("maLoaiToa");
            	LoaiToaTau loaiToaEnum;
                    switch (maLoaiToaFromDB) {
                        case "GNK6DH":
                            loaiToaEnum = LoaiToaTau.GiuongNamSauCho;
                            break;
                        case "GNK4DH":
                            loaiToaEnum = LoaiToaTau.GiuongNamBonCho;
                            break;
                        case "NMDH":
                            loaiToaEnum = LoaiToaTau.NgoiMem;
                            break;
                        case "NCDH":
                            loaiToaEnum = LoaiToaTau.NgoiCung;
                            break;
                        default:
                            // Xử lý trường hợp ngoại lệ
                            throw new IllegalArgumentException("Loại toa không hợp lệ: " + maLoaiToaFromDB);
                    }
                ToaTau t = new ToaTau();
                t.setMaToaTau(rs.getString("maToaTau"));
                t.setTenToaTau(rs.getString("tenToaTau"));
                t.setThuTuToa(rs.getInt("thuTuToa"));
                t.setLoaiToaTau(loaiToaEnum); // enum
                t.setSoLuongGhe(rs.getInt("soLuongGhe"));
                t.setHeSoHangToa(rs.getDouble("heSoHangToa"));

                Tau tau = new Tau();
                tau.setMaTau(maTau);
                t.setTau(tau);

                list.add(t);
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }
}
