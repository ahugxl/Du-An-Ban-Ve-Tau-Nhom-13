package control;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth; // Import để lấy số ngày trong tháng
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import dao.ThongKeThang_DAO;
import entity.HoaDon;
import entity.NhanVien;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label; // Import Label
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller_ThongKeThang implements Initializable {

    @FXML private ComboBox<String> cmbThang;
    @FXML private ComboBox<Integer> cmbNam;
    @FXML private TextField txtTongSoHDTheoThang;
    @FXML private TextField txtTongTienHDTheoThang;
    @FXML private TableView<NhanVien> tblNhanVien;
    @FXML private TableColumn<NhanVien, String> colMaNhanVien_Nho;
    @FXML private TableColumn<NhanVien, String> colTenNhanVien_Nho;
    @FXML private BarChart<String, Number> bieuDoDoanhSo;
    @FXML private Label lblBieuDo; // Thêm biến cho tiêu đề biểu đồ

    private ThongKeThang_DAO thongKeDAO = new ThongKeThang_DAO();
    @FXML private CategoryAxis xAxis ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        populateYearComboBox();

        // Lắng nghe sự kiện thay đổi NĂM hoặc THÁNG, gọi cùng một hàm loadData()
        cmbNam.valueProperty().addListener((obs, oldVal, newVal) -> loadData());
        cmbThang.valueProperty().addListener((obs, oldVal, newVal) -> loadData());
        
        // Tải dữ liệu lần đầu khi giao diện được mở
        LocalDate today = LocalDate.now();
        cmbNam.setValue(today.getYear());
        // Để mặc định là "Tất cả các tháng" ban đầu
        cmbThang.getSelectionModel().selectFirst();
       
        loadData(); // Tải dữ liệu ban đầu
        xAxis.setTickLabelGap(3);
    }

    /**
     * Phương thức trung tâm, tải lại toàn bộ dữ liệu cho giao diện
     */
    private void loadData() {
        String selectedMonthStr = cmbThang.getValue();
        Integer selectedYear = cmbNam.getValue();

        // Nếu chưa chọn năm hoặc chọn "Tất cả các tháng", chỉ xóa dữ liệu và dừng lại
        if (selectedYear == null || selectedMonthStr == null || selectedMonthStr.equals("Tất cả các tháng")) {
            clearUI();
            lblBieuDo.setText("BIỂU ĐỒ THỐNG KÊ DOANH SỐ"); // Reset tiêu đề
            return;
        }

        int selectedMonth = Integer.parseInt(selectedMonthStr.split(" ")[1]);
        
        // Gọi DAO một lần duy nhất để lấy tất cả hóa đơn trong tháng
        List<HoaDon> dsHoaDon = thongKeDAO.getHoaDonTheoThang(selectedMonth, selectedYear);

        // Cập nhật tất cả các thành phần trên giao diện
        updateLeftPane(dsHoaDon);
        updateBarChart(dsHoaDon, selectedMonth, selectedYear);
    }
    
    /**
     * Cập nhật khung thông tin bên trái
     */
    private void updateLeftPane(List<HoaDon> dsHoaDon) {
        // Cập nhật các ô tổng hợp
        txtTongSoHDTheoThang.setText(String.valueOf(dsHoaDon.size()));
        double tongTien = dsHoaDon.stream().mapToDouble(HoaDon::getTongTien).sum();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtTongTienHDTheoThang.setText(currencyFormatter.format(tongTien));

        // Cập nhật bảng nhân viên
        Set<NhanVien> dsNhanVien = dsHoaDon.stream()
                                          .map(HoaDon::getNhanVienLapHoaDon)
                                          .collect(Collectors.toSet());
        tblNhanVien.setItems(FXCollections.observableArrayList(new ArrayList<>(dsNhanVien)));
    }
    
    /**
     * Cập nhật biểu đồ theo từng ngày trong tháng
     */
    private void updateBarChart(List<HoaDon> dsHoaDon, int month, int year) {
        // Cập nhật tiêu đề biểu đồ
        lblBieuDo.setText(String.format("BIỂU ĐỒ DOANH THU THÁNG %d NĂM %d", month, year));
        
        bieuDoDoanhSo.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu ngày");

        // Nhóm các hóa đơn theo ngày và tính tổng doanh thu cho mỗi ngày
        Map<Integer, Double> dailyRevenue = dsHoaDon.stream()
            .collect(Collectors.groupingBy(
                hd -> hd.getNgayLapHoaDon().getDayOfMonth(),
                Collectors.summingDouble(HoaDon::getTongTien)
            ));

        // Lấy số ngày của tháng được chọn (ví dụ: tháng 2 năm 2024 có 29 ngày)
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

        // Lặp qua tất cả các ngày trong tháng để đảm bảo ngày nào cũng có cột
        for(int i = 1; i <= daysInMonth; i++) {
            double revenue = dailyRevenue.getOrDefault(i, 0.0);
            series.getData().add(new XYChart.Data<>(String.valueOf(i), revenue));
        }
        
        bieuDoDoanhSo.getData().add(series);
    }
    
    private void clearUI() {
        txtTongSoHDTheoThang.clear();
        txtTongTienHDTheoThang.clear();
        tblNhanVien.getItems().clear();
        bieuDoDoanhSo.getData().clear();
    }
    
    private void setupTableColumns() {
        colMaNhanVien_Nho.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colTenNhanVien_Nho.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
    }

    private void populateYearComboBox() {
        int currentYear = Year.now().getValue();
        for (int i = currentYear; i >= currentYear - 5; i--) {
            cmbNam.getItems().add(i);
        }
    }
}