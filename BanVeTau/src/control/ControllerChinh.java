package control;

import java.io.IOException;

import entity.NhanVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControllerChinh {

    @FXML
    private Label lblTenNhanVien;

    // THÊM DÒNG NÀY: Khai báo Label cho chức vụ từ FXML
    @FXML
    private Label lblChucVu; 

    private NhanVien nhanVien;
    private boolean quyen; // Biến để lưu quyền (true = quản lý, false = nhân viên)

    /**
     * Phương thức này nhận dữ liệu từ LoginController
     */
    public void setNhanVien(NhanVien nv) {
        this.nhanVien = nv;
        
        // 1. Lấy quyền quản lý từ đối tượng NhanVien
        boolean isManager = nv.isQuanLy();
        
        // 2. Lưu quyền vào biến của Controller
        this.quyen = isManager;
        
        // 3. Xác định chuỗi chức vụ dựa trên quyền
        String chucVu = "";
        if (isManager) {
            chucVu = "Quản lý";
        } else {
            chucVu = "Nhân viên";
        }
        
        // 4. Cập nhật các Label trên giao diện
        lblTenNhanVien.setText("Xin chào, " + nhanVien.getTenNV());
        lblChucVu.setText(chucVu);
    }

    @FXML
    void chuyenSangGDDangNhap(ActionEvent event) {
        // Tại đây, bạn sẽ viết code để chuyển màn hình quay trở lại giao diện đăng nhập
        System.out.println("Nút chuyển về màn hình đăng nhập đã được nhấn!");
    }
    @FXML
    private BorderPane mainBorderPane;
	public void chuyenSangGDTimKiemThuong(ActionEvent event) {
	    try {
	        // 1. Tạo FXMLLoader. Đây là cách làm chuẩn để có thể lấy được controller
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_TimVeThuong.fxml"));
	        Parent root = loader.load();

	        // 2. Lấy controller của cửa sổ tìm kiếm sau khi đã load FXML
	        ControllerTimVeThuong searchController = loader.getController();

	        // 3. TRUYỀN THAM CHIẾU: Gọi phương thức setMainController và truyền "this"
	        //    "this" ở đây chính là đối tượng SceneController hiện tại
	        searchController.setMainController(this);

	        // 4. Tạo và hiển thị Stage như bình thường
	        Stage newStage = new Stage();
	        newStage.setTitle("Tra tìm vé");
	        newStage.setScene(new Scene(root));
	        newStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void showTicketResultsView() {
	     try {
	         // Tải FXML chứa kết quả (file SellTicketView.fxml mà bạn đã tạo)
	         Parent ticketResultsView = FXMLLoader.load(getClass().getResource("/gui/GD_DanhSachToaNgoi.fxml"));
	         
	         // Đặt nó vào trung tâm của BorderPane chính
	         mainBorderPane.setCenter(ticketResultsView);
	         
	     } catch (IOException e) {
	         System.err.println("Không thể tải file SellTicketView.fxml");
	         e.printStackTrace();
	     }
	 }
}