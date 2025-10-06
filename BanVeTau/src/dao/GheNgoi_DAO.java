package dao;

import entity.*;
import connectDB.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GheNgoi_DAO {
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
}
