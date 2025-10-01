package control;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import entity.ChuyenTau;
import entity.DatabaseService;

public class ControllerTimVeThuong implements Initializable {

    // 1. Khai báo các thành phần FXML
    @FXML private DatePicker dtmNgayDiTimVeThuong;
    @FXML private TextField txtGaDi;
    @FXML private TextField txtGaDen;
    @FXML private Button btnTraTim;
    @FXML private Button btnThoat;

    private ControllerChinh mainController;
    private DatabaseService dbService; // Dùng để truy vấn dữ liệu

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbService = new DatabaseService(); // Khởi tạo service
        // Đặt giá trị mặc định cho ngày đi là hôm nay cho tiện
        dtmNgayDiTimVeThuong.setValue(LocalDate.now()); 
    }

    // Phương thức này được ControllerChinh gọi để truyền chính nó qua
    public void setMainController(ControllerChinh mainController) {
        this.mainController = mainController;
    }

    @FXML
    void xacNhanTimKiem(ActionEvent event) {
        // 2. Lấy dữ liệu từ người dùng
        String gaDi = txtGaDi.getText();
        String gaDen = txtGaDen.getText();
        LocalDate ngayDi = dtmNgayDiTimVeThuong.getValue();

        // Kiểm tra xem người dùng đã nhập đủ thông tin chưa
        if (gaDi != null && !gaDi.trim().isEmpty() && 
            gaDen != null && !gaDen.trim().isEmpty() && 
            ngayDi != null) 
        {
            // 3. Gọi service để tìm kiếm
            List<ChuyenTau> ketQuaTimKiem = dbService.findAvailableTrips(gaDi, gaDen, ngayDi);

            // 4. Kiểm tra xem tham chiếu có tồn tại không và gửi kết quả đi
            if (mainController != null) {
                // Gọi phương thức của ControllerChinh và truyền danh sách kết quả qua
                mainController.showTicketResultsView(ketQuaTimKiem);
            } else {
                System.err.println("Lỗi: Không có tham chiếu đến Main Controller.");
            }
        } else {
            // Có thể hiển thị một cửa sổ cảnh báo ở đây
            System.out.println("Vui lòng nhập đầy đủ Ga đi, Ga đến và Ngày đi.");
        }
    }

    @FXML
    void thoat(ActionEvent event) {
        // Logic để quay lại giao diện chính hoặc đóng cửa sổ nếu cần
        // Ví dụ: Yêu cầu main controller hiển thị lại một giao diện mặc định
        if (mainController != null) {
            // mainController.showDefaultView();
        }
    }
}