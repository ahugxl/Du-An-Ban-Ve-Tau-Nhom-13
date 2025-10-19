package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ControllerThongKe implements Initializable {

    // Tham chiếu đến BorderPane chính từ file FXML
    @FXML
    private BorderPane mainBorderPane;
    private ControllerChinh mainController;

    // Các @FXML và các biến khác của bạn ở đây...
    // @FXML
    // private BorderPane mainBorderPane;
    
    // 2. ĐÂY LÀ PHƯƠNG THỨC BẠN ĐANG THIẾU
    // Phương thức này nhận ControllerChinh từ bên ngoài và lưu lại
    public void setMainController(ControllerChinh mainController) {
        this.mainController = mainController;
    }
    /**
     * Phương thức này được tự động gọi sau khi file FXML đã được tải.
     * Dùng để thiết lập trạng thái ban đầu cho giao diện.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ví dụ: Tải giao diện "Theo tháng" làm mặc định khi mở lên
        loadView("/gui/ThongKeNgay.fxml"); // Thay đổi đường dẫn cho đúng với dự án của bạn
    }

    @FXML
    void thongKeNgay(ActionEvent event) {
        System.out.println("Nút 'Theo ngày' được bấm.");
        loadView("/gui/ThongKeNgay.fxml"); // Thay đổi đường dẫn cho đúng
    }

    @FXML
    void thongKeThang(ActionEvent event) {
        System.out.println("Nút 'Theo tháng' được bấm.");
        loadView("/gui/ThongKeThang.fxml"); // Thay đổi đường dẫn cho đúng
    }

    @FXML
    void thongKeNam(ActionEvent event) {
        System.out.println("Nút 'Theo năm' được bấm.");
        loadView("/gui/ThongKeNam.fxml"); // Thay đổi đường dẫn cho đúng
    }

    /**
     * Hàm tiện ích để tải một file FXML và đặt nó vào vùng center của BorderPane.
     * @param fxmlPath Đường dẫn tới file FXML cần tải (ví dụ: "/view/ThangView.fxml")
     */
    private void loadView(String fxmlPath) {
        try {
            // Tải file FXML
            Pane view = FXMLLoader.load(getClass().getResource(fxmlPath));
            
            // Đặt giao diện vừa tải vào vùng center
            mainBorderPane.setCenter(view);

        } catch (IOException e) {
            System.err.println("Không thể tải file FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}