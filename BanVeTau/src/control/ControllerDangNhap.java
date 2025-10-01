package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.NhanVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerDangNhap{
	

	private Stage stage;
	private Scene scene;
	private Parent root;
    // Khai báo các thành phần giao diện từ FXML
    @FXML
    private TextField txtTaiKhoan; // Ô nhập tài khoản

    @FXML
    private PasswordField txtMatKhau; // Ô nhập mật khẩu (đã đổi thành PasswordField)

    @FXML
    private Button btnDangNhap; // Nút đăng nhập

    /**
     * Phương thức này được gọi khi người dùng nhấn nút "Đăng nhập".
     * Tên phương thức "loginAction" phải khớp với onAction trong FXML.
     */
 // control/LoginController.java
    @FXML
    void kiemTraDangNhap(ActionEvent event) {
        // B1: Lấy thông tin người dùng nhập từ giao diện
        String username = txtTaiKhoan.getText();
        String password = txtMatKhau.getText();

        // B2: Tạo danh sách nhân viên mẫu (thực tế phần này sẽ được lấy từ database)
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        dsNhanVien.add(new NhanVien("Nguyễn Văn A", "user", "123", false));
        dsNhanVien.add(new NhanVien("Trần Thị B (Quản lý)", "admin", "admin123", true));

        // B3: Tìm kiếm nhân viên trong danh sách
        NhanVien nhanVienTimThay = null;
        for (NhanVien nv : dsNhanVien) {
            if (nv.getTaiKhoan().equals(username) && 
                nv.getMatKhau().equals(password)) {
                nhanVienTimThay = nv; // Đã tìm thấy nhân viên phù hợp
                break; // Thoát khỏi vòng lặp
            }
        }

        // B4: Xử lý kết quả đăng nhập
        if (nhanVienTimThay != null) {
            // ✅ Đăng nhập thành công, chuyển màn hình và truyền dữ liệu
            try {
                // Tạo một đối tượng FXMLLoader để tải file FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_Chinh.fxml"));
                root = loader.load();

                // Lấy controller của màn hình chính
                ControllerChinh chinhController = loader.getController();

                // Gọi phương thức để truyền đối tượng NhanVien qua
                chinhController.setNhanVien(nhanVienTimThay);

                // Hiển thị màn hình chính
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // ❌ Đăng nhập thất bại
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi Đăng nhập");
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản, mật khẩu hoặc vai trò không chính xác!");
            alert.showAndWait();
        }
    }
    
}