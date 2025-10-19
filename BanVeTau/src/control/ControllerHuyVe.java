package control;

import java.time.Duration;
import java.time.LocalDateTime;

import entity.Ve;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerHuyVe extends ControllerTimVe{
    @FXML
    private Button btnKT;

    @FXML
    private Button btnXacNhanHuy;


    @FXML
    private TextField txtMVDangChon;

    @FXML
    private TextField txtNgayInDC;

    @FXML
    private TextField txtPhanTram;

    @FXML
    private TextField txtSDT11;

    @FXML
    private TextField txtSoTienHoan;

    @FXML
    private TextField txtTTVeDC;
    @FXML @Override
    public void initialize() {
        super.initialize();
    }
    @Override
    protected void bindSelection() {
        tblTimVe.getSelectionModel().selectedItemProperty().addListener((obs, o, v) -> {
            if (v == null) {
            	txtMVDangChon.clear(); txtTTVeDC.clear(); txtNgayInDC.clear();
            } else {
            	txtMVDangChon.setText(v.getMaVe());
            	txtTTVeDC.setText(v.getTrangThaiVeStr());
            	txtNgayInDC.setText(v.getChuyenTau().getNgayGioKhoiHanh().toString());
            }
        });
    }
    @FXML
    private void huyVe() {
				// Lấy vé đã chọn từ bảng
		Ve veChon = tblTimVe.getSelectionModel().getSelectedItem();
		if (veChon == null) {
			showError("Vui lòng chọn vé để hủy.");
			return;
		}
		if (kiemTraLePhi()) {
			if (showConfirm("Xác nhận hủy vé: " + veChon.getMaVe() + " ?")) {
				// Thực hiện hủy vé
				
				if(ve_dao.huyVe(txtMVDangChon.getText().trim())) {
					showInfo("Hủy vé thành công.");
					hienTatCaVe(); // Cập nhật lại bảng sau khi hủy vé
					return;
				} else {
					showError("Hủy vé thất bại. Vui lòng thử lại.");
					return;
				}
			}
			
			
			
		}
		else {
			showError("Không thể hủy vé do không thỏa điều kiện lệ phí.");
			return;
		}
		
	
		
		
	}
    @FXML
    private boolean kiemTraLePhi() {
        // Lấy vé đang chọn trên bảng
        Ve ve = tblTimVe.getSelectionModel().getSelectedItem();
        if (ve == null) {
            showError("Vui lòng chọn một vé trong bảng.");
            return false;
        }

        // Lấy mốc thời gian để so sánh.
        // ĐÚNG NGHIỆP VỤ: nên dùng giờ khởi hành của chuyến tàu.
        // Nếu chưa có, fallback sang ngayInVe để không bị null.
        LocalDateTime mocThoiGian;
        if (ve.getChuyenTau() != null && ve.getChuyenTau().getNgayGioKhoiHanh() != null) {
            mocThoiGian = ve.getChuyenTau().getNgayGioKhoiHanh();
        }
        else {
            showError("Vé không có thông tin thời gian để kiểm tra.");
            return false;
        }

        // Tính số giờ từ hiện tại tới mốc thời gian
        LocalDateTime now = LocalDateTime.now();
        long hoursUntil = Duration.between(now, mocThoiGian).toHours();

        // Nếu đã quá giờ (âm) hoặc còn < 4 giờ → không cho trả
        if (hoursUntil < 4) {
            txtPhanTram.clear();
            showError("Còn dưới 4 giờ so với giờ tàu chạy: không được phép trả vé.");
            return false;
        }

        // Quy tắc lệ phí
        int percent;
        if (hoursUntil >= 24) {
            percent = 10;  // từ 24h trở lên
        } else { 
            percent = 20;  // từ 4h đến dưới 24h
        }

        // Hiển thị
        txtPhanTram.setText(percent + "%");
        return true;
    }

}
