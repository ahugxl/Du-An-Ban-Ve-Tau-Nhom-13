package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerThongKe {

    // Tham chiếu đến ControllerChinh
    private ControllerChinh mainController;

    // Dùng để nhận tham chiếu từ ControllerChinh
    public void setMainController(ControllerChinh sceneChinh) {
        this.mainController = sceneChinh;
    }

    // Xử lý sự kiện khi bấm nút "Thống kê theo ngày"
    @FXML
    void thongKeNgay(ActionEvent event) {
        if (mainController != null) {
            System.out.println("Thực hiện thống kê theo ngày...");
            // Ở đây bạn có thể gọi phương thức trong ControllerChinh
            // mainController.hienThiThongKeNgay();
        } else {
            System.err.println("Lỗi: Không có tham chiếu đến Main Controller.");
        }
    }

    // Xử lý sự kiện khi bấm nút "Thống kê theo tháng"
    @FXML
    void thongKeThang(ActionEvent event) {
        if (mainController != null) {
            System.out.println("Thực hiện thống kê theo tháng...");
            // mainController.hienThiThongKeThang();
        }
    }

    // Xử lý sự kiện khi bấm nút "Thống kê theo năm"
    @FXML
    void thongKeNam(ActionEvent event) {
        if (mainController != null) {
            System.out.println("Thực hiện thống kê theo năm...");
            // mainController.hienThiThongKeNam();
        }
    }

    // Thoát cửa sổ thống kê (nếu cần đóng riêng)
    @FXML
    void thoat(ActionEvent event) {
        dongCuaSo(event);
    }

    private void dongCuaSo(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
