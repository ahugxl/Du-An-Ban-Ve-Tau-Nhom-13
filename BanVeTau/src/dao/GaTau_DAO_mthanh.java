package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.GaTau;

public class GaTau_DAO_mthanh {

    /**
     * Lấy danh sách tất cả các ga tàu có trong cơ sở dữ liệu.
     * @return a List of GaTau objects.
     * @throws SQLException 
     */
    public List<GaTau> getAllGaTau() throws SQLException {
        List<GaTau> dsGaTau = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT * FROM GaTau ORDER BY tenGaTau"; // Sắp xếp theo tên cho dễ nhìn trong ComboBox
        
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GaTau gaTau = new GaTau(
                    rs.getString("maGaTau"),
                    rs.getString("tenGaTau"),
                    rs.getString("diaChiGa"),
                    rs.getString("soDienThoaiGa")
                );
                dsGaTau.add(gaTau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dsGaTau;
    }

    // Bạn có thể thêm các phương thức khác ở đây nếu cần, ví dụ:
    // public GaTau getGaTauByMa(String maGa) { ... }
    // public boolean addGaTau(GaTau gaTau) { ... }
}