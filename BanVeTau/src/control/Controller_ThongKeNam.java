package control;

import java.net.URL;
import java.text.NumberFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import dao.ThongKeNam_DAO; // Dùng DAO của năm
import entity.HoaDon;
import entity.NhanVien;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller_ThongKeNam implements Initializable {

    @FXML private ComboBox<Integer> cmbNam;
    @FXML private TextField txtTongSoHDTheoNam;
    @FXML private TextField txtTongTienHDTheoNam;
    @FXML private TableView<NhanVien> tblNhanVien;
    @FXML private TableColumn<NhanVien, String> colMaNhanVien_Nho;
    @FXML private TableColumn<NhanVien, String> colTenNhanVien_Nho;
    @FXML private BarChart<String, Number> bieuDoDoanhSo;
    @FXML private Label lblBieuDo;

    private ThongKeNam_DAO thongKeDAO = new ThongKeNam_DAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns(); 
        populateYearComboBox(); // Gọi hàm này để nạp dữ liệu năm
        // Đặt giá trị mặc định là một số Integer
   
        // Chỉ cần lắng nghe sự kiện thay đổi NĂM
        cmbNam.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadDataForYear(newVal);
            }
        });

        // Tải dữ liệu lần đầu cho năm hiện tại
        cmbNam.setValue(Year.now().getValue());
    }

    /**
     * Tải và hiển thị tất cả dữ liệu cho năm được chọn
     */
    private void loadDataForYear(int year) {
        // Cập nhật tiêu đề biểu đồ
        lblBieuDo.setText("BIỂU ĐỒ DOANH THU NĂM " + year);

        // --- Cập nhật Biểu đồ ---
        Map<Integer, Double> monthlyRevenue = thongKeDAO.getDoanhThuTungThang(year);
        bieuDoDoanhSo.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu năm " + year);
        for (int i = 1; i <= 12; i++) {
            double revenue = monthlyRevenue.getOrDefault(i, 0.0);
            series.getData().add(new XYChart.Data<>("Tháng " + i, revenue));
        }
        bieuDoDoanhSo.getData().add(series);

        // --- Cập nhật Khung bên trái ---
        List<HoaDon> dsHoaDonCuaNam = thongKeDAO.getHoaDonTheoNam(year);

        // Cập nhật các ô tổng hợp
        txtTongSoHDTheoNam.setText(String.valueOf(dsHoaDonCuaNam.size()));
        double tongTien = dsHoaDonCuaNam.stream().mapToDouble(HoaDon::getTongTien).sum();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtTongTienHDTheoNam.setText(currencyFormatter.format(tongTien));

        // Cập nhật bảng nhân viên
        Set<NhanVien> dsNhanVien = dsHoaDonCuaNam.stream()
                                                .map(HoaDon::getNhanVienLapHoaDon)
                                                .collect(Collectors.toSet());
        tblNhanVien.setItems(FXCollections.observableArrayList(new ArrayList<>(dsNhanVien)));
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