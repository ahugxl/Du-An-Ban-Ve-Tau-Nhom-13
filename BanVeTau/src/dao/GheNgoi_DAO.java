package dao;

import entity.*;
import connectDB.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GheNgoi_DAO {
	private final ToaTau_DAO toaTauDAO = new ToaTau_DAO();
    public List<GheNgoi> getGheByToa(String maToaTau) {
        List<GheNgoi> list = new ArrayList<>();
        String sql = "SELECT maGheNgoi, viTriGhe FROM GheNgoi WHERE maToaTau = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maToaTau);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GheNgoi g = new GheNgoi();
                g.setMaGheNgoi(rs.getString("maGheNgoi"));
                g.setViTriGhe(rs.getInt("viTriGhe"));

                ToaTau t = new ToaTau();
                t.setMaToaTau(maToaTau);
                g.setToaTau(t);

                list.add(g);
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }
 // Lấy toàn bộ ghế ngồi
    public ArrayList<GheNgoi> getAllGheNgoi() throws SQLException {
    	ArrayList<GheNgoi> ds = new ArrayList<>();

        String sql = "SELECT maGheNgoi, viTriGhe, maToaTau, daDat FROM GheNgoi";

        ConnectDB.getInstance();
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maGhe = rs.getString("maGheNgoi");

                // viTriGhe đang NVARCHAR -> ép int
                int viTri = rs.getInt("viTriGhe");

                ToaTau toa = toaTauDAO.getToaTauTheoMa(rs.getString("maToaTau"), con);
                boolean daDat = rs.getBoolean("daDat");

                ds.add(new GheNgoi(maGhe, viTri, toa, daDat));
            }
        }
        return ds;
    }

    // Lấy 1 ghế theo mã
    public GheNgoi getGheNgoiTheoMa(String maGheNgoi, Connection con) throws SQLException {
        String sql = "SELECT maGheNgoi, viTriGhe, maToaTau, daDat " +
                     "FROM GheNgoi WHERE maGheNgoi = ?";

        
        	try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maGheNgoi);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maGhe = rs.getString("maGheNgoi");
                    int viTri = rs.getInt("viTriGhe");
                    ToaTau toa = toaTauDAO.getToaTauTheoMa(rs.getString("maToaTau"), con);
                    boolean daDat = rs.getBoolean("daDat");

                    return new GheNgoi(maGhe, viTri, toa, daDat);
                }
            }
        }
        return null; // không tìm thấy
    }
}
