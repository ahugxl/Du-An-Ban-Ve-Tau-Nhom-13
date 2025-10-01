package entity;


import entity.ChuyenTau;
import entity.ToaTau;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    // Giả lập tìm kiếm chuyến tàu
    public List<ChuyenTau> findAvailableTrips(String gaDi, String gaDen, LocalDate ngayDi) {
        List<ChuyenTau> results = new ArrayList<>();
        // Trong thực tế, đây là câu lệnh SQL SELECT...
        // Bây giờ, chúng ta chỉ trả về dữ liệu giả
        System.out.println("Đang tìm chuyến từ " + gaDi + " đến " + gaDen + " vào ngày " + ngayDi);
        
        results.add(new ChuyenTau("CT01", "SE1", "TAU01", ngayDi.atTime(8, 0), ngayDi.atTime(18, 30), 120, 80));
        results.add(new ChuyenTau("CT02", "SE3", "TAU02", ngayDi.atTime(10, 30), ngayDi.atTime(21, 0), 150, 50));
        
        return results;
    }

    // Giả lập lấy danh sách toa theo mã tàu
    public List<ToaTau> getCoachesForTrain(String maTau) {
        List<ToaTau> results = new ArrayList<>();
        System.out.println("Đang lấy danh sách toa cho tàu " + maTau);

        if (maTau.equals("TAU01")) {
            results.add(new ToaTau("T01-1", "Toa 1"));
            results.add(new ToaTau("T01-2", "Toa 2"));
            results.add(new ToaTau("T01-3", "Toa 3"));
        } else {
             results.add(new ToaTau("T02-1", "Toa 1"));
             results.add(new ToaTau("T02-2", "Toa 2"));
        }
        return results;
    }

    // Giả lập lấy trạng thái ghế theo mã toa
    public boolean[] getSeatStatusForCoach(String maToaTau) {
        System.out.println("Đang lấy trạng thái ghế cho toa " + maToaTau);
        boolean[] seatStatus = new boolean[56];
        // Giả lập vài ghế đã bán
        if (maToaTau.contains("1")) {
            seatStatus[4] = true;  // Ghế 5
            seatStatus[9] = true;  // Ghế 10
        } else if (maToaTau.contains("2")) {
            seatStatus[0] = true; // Ghế 1
            seatStatus[20] = true; // Ghế 21
        }
        return seatStatus;
    }
}