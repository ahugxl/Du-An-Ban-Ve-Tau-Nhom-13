package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerTimVeThuong {

    // Biến này sẽ giữ tham chiếu đến Controller của cửa sổ chính
    private ControllerChinh mainController;

    // Phương thức này được SceneController gọi để truyền chính nó qua
    public void setMainController(ControllerChinh sceneChinh) {
        this.mainController = sceneChinh;
    }

    @FXML
    void xacNhanTimKiem(ActionEvent event) {
        // 2. Kiểm tra xem tham chiếu có tồn tại không
        if (mainController != null) {
            // 3. Gọi một phương thức CÔNG KHAI (public) của SceneController
            mainController.showTicketResultsView(); // Chúng ta sẽ tạo phương thức này ở Bước 3

            // 4. (Tùy chọn) Đóng cửa sổ tìm kiếm sau khi đã tìm
            thoat(event);
        } else {
            System.err.println("Lỗi: Không có tham chiếu đến Main Controller.");
        }
    }

    @FXML
    void thoat(ActionEvent event) {
    	dongCuaSo(event);
    }

    private void dongCuaSo(ActionEvent event) {
        // Lấy Stage (cửa sổ) hiện tại và đóng nó
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}