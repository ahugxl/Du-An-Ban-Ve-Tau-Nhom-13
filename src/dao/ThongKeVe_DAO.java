package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connectDB.ConnectDB;

public class ThongKeVe_DAO {

    /**
     * 1. Thống kê doanh thu theo từng tuyến đường
     */
	public record TyLeLapDayDTO(String maChuyenTau, String tenChuyenTau, int soVeDaBan, int tongSoGhe) {};
	public List<TyLeLapDayDTO> getTyLeLapDay(int nam) {
		List<TyLeLapDayDTO> data = new ArrayList<>();
        String sql = "WITH VeDaBan AS ( " +
                     "  SELECT v.maChuyenTau, COUNT(v.maVe) AS SoVeBan " +
                     "  FROM Ve v " +
                     "  JOIN ChiTietHoaDon cthd ON v.maVe = cthd.maVe " +
                     "  JOIN HoaDon hd ON cthd.maHoaDon = hd.maHoaDon " +
                     "  WHERE YEAR(hd.ngayLapHoaDon) = ? AND v.trangThaiVe = N'Đã thanh toán' "  +
                     "  GROUP BY v.maChuyenTau " +
                     "), TongGhe AS ( " +
                     "  SELECT t.maTau, SUM(tt.soLuongGhe) AS TongSoGhe " +
                     "  FROM Tau t JOIN ToaTau tt ON t.maTau = tt.maTau GROUP BY t.maTau " +
                     ") " +
                     "SELECT ct.maChuyenTau, t.tenTau, ISNULL(vdb.SoVeBan, 0) AS SoVeBan, tg.TongSoGhe " +
                     "FROM ChuyenTau ct " +
                     "JOIN Tau t ON ct.maTau = t.maTau " +
                     "JOIN TongGhe tg ON ct.maTau = tg.maTau " +
                     "LEFT JOIN VeDaBan vdb ON ct.maChuyenTau = vdb.maChuyenTau " +
                     "WHERE YEAR(ct.ngayGioKhoiHanh) = ?";
        
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, nam);
            stmt.setInt(2, nam);
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    // Tạo đối tượng record trực tiếp
                    data.add(new TyLeLapDayDTO(
                        rs.getString("maChuyenTau"),
                        rs.getString("tenTau"),
                        rs.getInt("SoVeBan"),
                        rs.getInt("TongSoGhe")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

	public Map<String, Double> getDoanhThuTheoTuyenDuong(int nam) {
        Map<String, Double> data = new HashMap<>();
        String sql = "SELECT td.tenTuyenDuong, SUM(hd.tongTien) AS TongDoanhThu " +
                     "FROM HoaDon hd " +
                     "JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon " +
                     "JOIN Ve v ON cthd.maVe = v.maVe " +
                     "JOIN ChuyenTau ct ON v.maChuyenTau = ct.maChuyenTau " +
                     "JOIN TuyenDuong td ON ct.maTuyenDuong = td.maTuyenDuong " +
                     "WHERE YEAR(hd.ngayLapHoaDon) = ? " +
                     "GROUP BY td.tenTuyenDuong";
        
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            // Gán giá trị năm vào tham số '?'
            stmt.setInt(1, nam);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Duyệt qua từng dòng kết quả
                while (rs.next()) {
                    String tenTuyenDuong = rs.getString("tenTuyenDuong");
                    double tongDoanhThu = rs.getDouble("TongDoanhThu");
                    // Thêm vào Map
                    data.put(tenTuyenDuong, tongDoanhThu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 2. Thống kê tổng số lượng vé bán ra theo từng tuyến đường trong một năm.
     */
    public Map<String, Integer> getSoLuongVeTheoTuyenDuong(int nam) {
        Map<String, Integer> data = new HashMap<>();
        String sql = "SELECT td.tenTuyenDuong, COUNT(v.maVe) AS TongSoVe " +
                     "FROM HoaDon hd " +
                     "JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon " +
                     "JOIN Ve v ON cthd.maVe = v.maVe " +
                     "JOIN ChuyenTau ct ON v.maChuyenTau = ct.maChuyenTau " +
                     "JOIN TuyenDuong td ON ct.maTuyenDuong = td.maTuyenDuong " +
                     "WHERE YEAR(hd.ngayLapHoaDon) = ? " +
                     "GROUP BY td.tenTuyenDuong";
        
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            // Gán giá trị năm vào tham số '?'
            stmt.setInt(1, nam);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Duyệt qua từng dòng kết quả
                while (rs.next()) {
                    String tenTuyenDuong = rs.getString("tenTuyenDuong");
                    int tongSoVe = rs.getInt("TongSoVe");
                    // Thêm vào Map
                    data.put(tenTuyenDuong, tongSoVe);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 3. Thống kê số lượng vé theo từng loại toa
     */
    public Map<String, Integer> getSoLuongVeTheoLoaiToa(int nam) {
        Map<String, Integer> data = new HashMap<>();
        String sql = "SELECT ltt.tenLoai, COUNT(v.maVe) AS TongSoVe " +
                     "FROM HoaDon hd " +
                     "JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon " +
                     "JOIN Ve v ON cthd.maVe = v.maVe " +
                     "JOIN GheNgoi gn ON v.maGheNgoi = gn.maGheNgoi " +
                     "JOIN ToaTau tt ON gn.maToaTau = tt.maToaTau " +
                     "JOIN LoaiToaTau ltt ON tt.maLoaiToa = ltt.maLoaiToa " +
                     "WHERE YEAR(hd.ngayLapHoaDon) = ? " +
                     "GROUP BY ltt.tenLoai";
        
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, nam);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    data.put(rs.getString("tenLoai"), rs.getInt("TongSoVe"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
//    // Lớp nội bộ để chứa dữ liệu tỷ lệ lấp đầy
//    public static class TyLeLapDayDTO {
//        // Tạo các thuộc tính và getter/setter cho:
//        // maChuyenTau, tenChuyenTau, soVeDaBan, tongSoGhe
//    }
//
//    /**
//     * 4. Thống kê tỷ lệ lấp đầy
//     */
//    public List<TyLeLapDayDTO> getTyLeLapDay(int nam) {
//        List<TyLeLapDayDTO> data = new ArrayList<>();
//        // Đây là câu SQL phức tạp nhất, cần JOIN nhiều bảng và tính toán
//        // ...
//        return data;
//    }
}