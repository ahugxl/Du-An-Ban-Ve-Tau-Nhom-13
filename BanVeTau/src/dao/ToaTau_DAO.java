package dao;

import entity.*;
import connectDB.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToaTau_DAO {
    private final Tau_DAO tauDAO = new Tau_DAO();
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
 // Lấy toàn bộ toa tàu
//    public ArrayList<ToaTau> getAllToaTau() throws SQLException {
//    	ArrayList<ToaTau> ds = new ArrayList<>();
//
//        String sql = "SELECT maToaTau, tenToaTau, thuTuToa, maLoaiToa, soLuongGhe, heSoHangToa, maTau " +
//                     "FROM ToaTau";
//
//        ConnectDB.getInstance();
//        try (Connection con = ConnectDB.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                String maToa   = rs.getString("maToaTau");
//                String tenToa  = rs.getNString("tenToaTau");       // NVARCHAR
//                int thuTu      = rs.getInt("thuTuToa");
//                LoaiToaTau loaiToa;
//                String codeLT  = rs.getString("maLoaiToa");
//                if (codeLT.equals("GNK6DH")) {
//					loaiToa = LoaiToaTau.GiuongNamSauCho;
//				} else if (codeLT.equals("GNK4DH")) {
//					loaiToa = LoaiToaTau.GiuongNamBonCho;
//				} else if (codeLT.equals("NMDH")) {
//					loaiToa = LoaiToaTau.NgoiMem;
//				} else {
//					loaiToa = LoaiToaTau.NgoiCung;
//				}
//
//                int soGhe      = rs.getInt("soLuongGhe");
//                double heSo    = rs.getDouble("heSoHangToa");       // SQL float -> Java double
//                String maTau   = rs.getString("maTau");
//                Tau tau        = tauDAO.getTauTheoMa(maTau);
//
//                ds.add(new ToaTau(maToa, tenToa, thuTu, loaiToa, soGhe, heSo, tau));
//            }
//        }
//        return ds;
//    }

    // Lấy 1 toa tàu theo mã
    public ToaTau getToaTauTheoMa(String maToaTau, Connection con) throws SQLException {
        String sql = "SELECT maToaTau, tenToaTau, thuTuToa, maLoaiToa, soLuongGhe, heSoHangToa, maTau " +
                     "FROM ToaTau WHERE maToaTau = ?";

        
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maToaTau);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maToa   = rs.getString("maToaTau");
                    String tenToa  = rs.getNString("tenToaTau");
                    int thuTu      = rs.getInt("thuTuToa");
                    LoaiToaTau loaiToa;
                    String codeLT  = rs.getString("maLoaiToa");
                    if (codeLT.equals("GNK6DH")) {
    					loaiToa = LoaiToaTau.GiuongNamSauCho;
    				} else if (codeLT.equals("GNK4DH")) {
    					loaiToa = LoaiToaTau.GiuongNamBonCho;
    				} else if (codeLT.equals("NMDH")) {
    					loaiToa = LoaiToaTau.NgoiMem;
    				} else {
    					loaiToa = LoaiToaTau.NgoiCung;
    				}

                    int soGhe      = rs.getInt("soLuongGhe");
                    double heSo    = rs.getDouble("heSoHangToa");
                    String maTau   = rs.getString("maTau");
                    Tau tau        = tauDAO.getTauTheoMa(maTau, con);

                    return new ToaTau(maToa, tenToa, thuTu, loaiToa, soGhe, heSo, tau);
                }
            }
        }
        return null; // không tìm thấy
    }
}
