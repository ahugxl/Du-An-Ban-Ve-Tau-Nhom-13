package control;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import dao.ThongKeNgay_DAO;
import entity.HoaDon;
import entity.NhanVien;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerThongKeNgay implements Initializable {
    
    // Khai báo các thành phần giao diện
    @FXML private TableView<HoaDon> tblHoaDon;
    @FXML private TableColumn<HoaDon, String> colMaHoaDon;
    @FXML private TableColumn<HoaDon, String> colSoDienThoaiKH;
    @FXML private TableColumn<HoaDon, String> colTenKhachHang;
    @FXML private TableColumn<HoaDon, String> colMaNhanVien;
    @FXML private TableColumn<HoaDon, String> colTenNhanVien;
    @FXML private TableColumn<HoaDon, Double> colTongTien;

    @FXML private TableView<NhanVien> tblNhanVien;
    @FXML private TableColumn<NhanVien, String> colMaNhanVien_Nho;
    @FXML private TableColumn<NhanVien, String> colTenNhanVien_Nho;
    
    @FXML private DatePicker datePickerNgay;
    @FXML private TextField txtTongSoHDTheoNgay;
    @FXML private TextField txtTongTienHDTheoNgay;
    @FXML private ComboBox<String> cmbThongKeTheo;

    private ThongKeNgay_DAO thongKeDAO = new ThongKeNgay_DAO();
    
    // Danh sách gốc để lưu trữ tất cả hóa đơn trong ngày
    private List<HoaDon> masterHoaDonList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        
        cmbThongKeTheo.getItems().addAll("Tất cả", "Theo nhân viên");
        cmbThongKeTheo.getSelectionModel().selectFirst();

        // Tải dữ liệu ban đầu
        datePickerNgay.setValue(LocalDate.now());
        loadDataFromDBForDate(datePickerNgay.getValue());

        // --- CÁC SỰ KIỆN LẮNG NGHE ---
        
        // 1. Khi thay đổi ngày, tải lại toàn bộ dữ liệu gốc từ CSDL
        datePickerNgay.setOnAction(event -> {
            LocalDate selectedDate = datePickerNgay.getValue();
            if (selectedDate != null) {
                loadDataFromDBForDate(selectedDate);
            }
        });
        
        // 2. Khi thay đổi chế độ (Tất cả / Theo nhân viên), cập nhật lại hiển thị
        cmbThongKeTheo.valueProperty().addListener((obs, oldVal, newVal) -> updateDisplay());
        
        // 3. Khi chọn một nhân viên trong bảng bên trái, cập nhật lại hiển thị
        tblNhanVien.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateDisplay());
    }

    /**
     * CHỈ LÀM MỘT VIỆC: Lấy dữ liệu gốc từ CSDL cho một ngày và gọi hàm hiển thị
     */
    private void loadDataFromDBForDate(LocalDate date) {
        masterHoaDonList = thongKeDAO.getHoaDonTheoNgay(date);
        updateDisplay(); // Cập nhật giao diện dựa trên dữ liệu mới và chế độ hiện tại
    }
    
    /**
     * PHƯƠNG THỨC TRUNG TÂM: Quyết định hiển thị cái gì lên giao diện
     * dựa trên chế độ đang được chọn mà không cần gọi lại CSDL.
     */
    private void updateDisplay() {
        String mode = cmbThongKeTheo.getValue();
        if (mode == null) return;

        if (mode.equals("Tất cả")) {
            // Chế độ "Tất cả": Bảng lớn đầy, bảng nhỏ rỗng
            tblHoaDon.setItems(FXCollections.observableArrayList(masterHoaDonList));
            updateSummaryFields(masterHoaDonList);
            tblNhanVien.getItems().clear(); // Xóa sạch bảng nhân viên
        } 
        else if (mode.equals("Theo nhân viên")) {
            // Chế độ "Theo nhân viên": Bảng nhỏ đầy, bảng lớn chờ lựa chọn
            
            // 1. Nạp dữ liệu cho bảng nhân viên
            Set<NhanVien> dsNhanVien = masterHoaDonList.stream()
                                                      .map(HoaDon::getNhanVienLapHoaDon)
                                                      .collect(Collectors.toSet());
            tblNhanVien.setItems(FXCollections.observableArrayList(new ArrayList<>(dsNhanVien)));
            
            // 2. Lọc dữ liệu cho bảng hóa đơn
            NhanVien selectedNhanVien = tblNhanVien.getSelectionModel().getSelectedItem();
            if (selectedNhanVien != null) {
                // Nếu có 1 nhân viên đang được chọn, lọc và hiển thị hóa đơn của họ
                List<HoaDon> filteredList = masterHoaDonList.stream()
                    .filter(hd -> hd.getNhanVienLapHoaDon().equals(selectedNhanVien))
                    .collect(Collectors.toList());
                tblHoaDon.setItems(FXCollections.observableArrayList(filteredList));
                updateSummaryFields(filteredList);
            } else {
                // Nếu chưa có nhân viên nào được chọn, làm trống bảng lớn
                tblHoaDon.getItems().clear();
                updateSummaryFields(new ArrayList<>()); // Cập nhật ô tổng hợp về 0
            }
        }
    }

    /**
     * Cập nhật các ô tổng hợp (Tổng số HĐ, Tổng tiền)
     */
    private void updateSummaryFields(List<HoaDon> dsHoaDon) {
        txtTongSoHDTheoNgay.setText(String.valueOf(dsHoaDon.size()));
        double tongTien = dsHoaDon.stream().mapToDouble(HoaDon::getTongTien).sum();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtTongTienHDTheoNgay.setText(currencyFormatter.format(tongTien));
    }
    
    /**
     * Cài đặt CellValueFactory cho các cột (giữ nguyên)
     */
    private void setupTableColumns() {
        colMaNhanVien_Nho.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colTenNhanVien_Nho.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        colMaHoaDon.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien")); 
        colTenKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getHoTenKhachHang()));
        colSoDienThoaiKH.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getSoDienThoai()));
        colMaNhanVien.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNhanVienLapHoaDon().getMaNhanVien()));
        colTenNhanVien.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNhanVienLapHoaDon().getTenNV()));
    }
}