package control;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import dao.*;
import entity.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControllerThanhToan {

    // --- FXML Components ---
    @FXML private ListView<Ve> listViewVe;
    @FXML private VBox ticketDetailPanel;
    @FXML private Label lblDetailTitle;
    @FXML private TextField txtHoTen;
    @FXML private TextField txtSoGiayTo;
    @FXML private ComboBox<LoaiVe> cmbLoaiVe;
    @FXML private ComboBox<KhuyenMai> cmbKhuyenMai;
    @FXML private Label lblTongTienVe;
    @FXML private Label lblGiamGia;
    @FXML private Label lblTienThue;
    @FXML private Label lblTongCong;
    @FXML private Button btnThanhToan;
    @FXML private Button btnQuayLai;
    @FXML private TextField txtTienKhachDua;
    @FXML private Label lblTienTraLai;

    // --- DAOs ---
    private final LoaiVe_DAO loaiVeDAO = new LoaiVe_DAO();
    private final KhuyenMai_DAO khuyenMaiDAO = new KhuyenMai_DAO();
    private final Thue_DAO thueDAO = new Thue_DAO();
    private final KhachHang_DAO_mthanh khachHangDAO = new KhachHang_DAO_mthanh();

    // --- State Management ---
    private List<Ve> danhSachVe = new ArrayList<>();
    private Ve veDangChon = null;
    private double tongThanhToanFinal = 0;
    private final DecimalFormat df = new DecimalFormat("#,### VNĐ");

    @FXML
    public void initialize() throws SQLException {
        ticketDetailPanel.setDisable(true); // Vô hiệu hóa panel chi tiết ban đầu

        // Nạp dữ liệu cho các ComboBox
        cmbLoaiVe.setItems(FXCollections.observableArrayList(loaiVeDAO.getAllLoaiVe()));
        cmbKhuyenMai.setItems(FXCollections.observableArrayList(khuyenMaiDAO.getKhuyenMaiHopLe()));
        
        // Listener để theo dõi vé nào đang được chọn trong ListView
        listViewVe.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Trước khi chuyển, lưu lại thông tin của vé cũ
            if (oldSelection != null) {
                saveCurrentVeDetails();
            }
            // Hiển thị thông tin của vé mới
            if (newSelection != null) {
                veDangChon = newSelection;
                displayVeDetails(veDangChon);
            }
        });
        
        // Listener cho các control trong form chi tiết để tự động cập nhật
        cmbLoaiVe.setOnAction(e -> saveCurrentVeDetailsAndUpdateTotal());
        cmbKhuyenMai.setOnAction(e -> saveCurrentVeDetailsAndUpdateTotal());
        
        txtTienKhachDua.textProperty().addListener((obs, oldText, newText) -> {
            try {
                double tienKhachDua = newText.isEmpty() ? 0 : Double.parseDouble(newText.replace(",", ""));
                double tienTraLai = tienKhachDua - tongThanhToanFinal;
                lblTienTraLai.setText(df.format(tienTraLai));
            } catch (NumberFormatException e) {
                lblTienTraLai.setText("Số không hợp lệ");
            }
        });
    }

    public void initData(List<GheNgoi_mthanh> gheDaChon, ChuyenTau chuyenTau, GaTau gaDi, GaTau gaDen) {
        try {
            Thue thueVAT10 = thueDAO.getThueTheoMa("VAT10");
            LoaiVe loaiVeMacDinh = loaiVeDAO.getLoaiVeTheoMa("TV");

            for (GheNgoi_mthanh ghe : gheDaChon) {
                Ve ve = new Ve();
                ve.setChuyenTau(chuyenTau);
                ve.setGheNgoi(ghe);
                ve.setGaDi(gaDi);
                ve.setGaDen(gaDen);
                ve.setThueApDung(thueVAT10);
                ve.setLoaiVe(loaiVeMacDinh);
                danhSachVe.add(ve);
            }
            listViewVe.setItems(FXCollections.observableArrayList(danhSachVe));
            listViewVe.getSelectionModel().selectFirst();
            updateTongTien();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Hiển thị thông tin của vé được chọn lên form
    private void displayVeDetails(Ve ve) {
        ticketDetailPanel.setDisable(false);
        lblDetailTitle.setText(String.format("Chi tiết cho vé: Ghế %d, Toa %d", ve.getGheNgoi().getViTriGhe(), ve.getGheNgoi().getToaTau().getThuTuToa()));
        
        KhachHang kh = ve.getKhachHang();
        txtHoTen.setText(kh != null ? kh.getHoTenKhachHang() : "");
        txtSoGiayTo.setText(kh != null ? kh.getSoGiayTo() : "");

        cmbLoaiVe.setValue(ve.getLoaiVe());
        
        // Giả sử mỗi vé chỉ áp dụng 1 khuyến mãi
        if (ve.getDanhSachChiTietKhuyenMai() != null && !ve.getDanhSachChiTietKhuyenMai().isEmpty()) {
            cmbKhuyenMai.setValue(ve.getDanhSachChiTietKhuyenMai().get(0).getKhuyenMai());
        } else {
            cmbKhuyenMai.setValue(null);
        }
    }
    
    // Lưu các thay đổi từ form vào đối tượng `veDangChon`
    private void saveCurrentVeDetails() {
        if (veDangChon == null) return;
        
        // Tạo hoặc cập nhật đối tượng KhachHang cho vé
        KhachHang kh = veDangChon.getKhachHang();
        if (kh == null) {
            kh = new KhachHang();
        }
        kh.setHoTenKhachHang(txtHoTen.getText());
        kh.setSoGiayTo(txtSoGiayTo.getText());
        veDangChon.setKhachHang(kh);
        
        // Cập nhật Loại vé
        veDangChon.setLoaiVe(cmbLoaiVe.getValue());

        // Cập nhật Khuyến mãi
        KhuyenMai km = cmbKhuyenMai.getValue();
        veDangChon.getDanhSachChiTietKhuyenMai().clear();
        if (km != null) {
            veDangChon.getDanhSachChiTietKhuyenMai().add(new ChiTietKhuyenMai(km, LocalDateTime.now()));
        }
    }
    
    private void saveCurrentVeDetailsAndUpdateTotal() {
        saveCurrentVeDetails();
        updateTongTien();
        listViewVe.refresh(); // Cập nhật lại ListView để hiển thị thông tin mới (nếu cần)
    }
    
    private void updateTongTien() {
        double tongTienGoc = 0, tongGiamGia = 0, tongTienThue = 0;
        LocalDate ngayDatVe = LocalDate.now();

        for (Ve ve : danhSachVe) {
            tongTienGoc += ve.getGiaVeThucTe();
            tongGiamGia += ve.getGiamGia(ngayDatVe);
            tongTienThue += ve.getSoTienThue(ngayDatVe);
        }

        tongThanhToanFinal = (tongTienGoc - tongGiamGia) + tongTienThue;
        lblTongTienVe.setText(df.format(tongTienGoc));
        lblGiamGia.setText("- " + df.format(tongGiamGia));
        lblTienThue.setText(df.format(tongTienThue));
        lblTongCong.setText(df.format(tongThanhToanFinal));
    }

    @FXML void handleApplyLoaiVeToAll(ActionEvent event) {
        LoaiVe selectedLoaiVe = cmbLoaiVe.getValue();
        if (selectedLoaiVe != null) {
            for (Ve ve : danhSachVe) {
                ve.setLoaiVe(selectedLoaiVe);
            }
            listViewVe.refresh();
            updateTongTien();
            showAlert(Alert.AlertType.INFORMATION, "Đã áp dụng loại vé cho tất cả các vé.");
        }
    }

    @FXML void handleApplyKhuyenMaiToAll(ActionEvent event) {
        KhuyenMai selectedKhuyenMai = cmbKhuyenMai.getValue();
        for (Ve ve : danhSachVe) {
            ve.getDanhSachChiTietKhuyenMai().clear();
            if (selectedKhuyenMai != null) {
                ve.getDanhSachChiTietKhuyenMai().add(new ChiTietKhuyenMai(selectedKhuyenMai, LocalDateTime.now()));
            }
        }
        listViewVe.refresh();
        updateTongTien();
        showAlert(Alert.AlertType.INFORMATION, "Đã áp dụng khuyến mãi cho tất cả các vé.");
    }

    @FXML void handleThanhToan(ActionEvent event) {
        saveCurrentVeDetails();
        // TODO: Logic lưu Hóa đơn và tất cả các vé trong 'danhSachVe' vào CSDL
        showAlert(Alert.AlertType.INFORMATION, "Thanh toán thành công!");
    }

    @FXML void handleQuayLai(ActionEvent event) {
        Stage currentStage = (Stage) btnQuayLai.getScene().getWindow();
        currentStage.close();
    }
    
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}