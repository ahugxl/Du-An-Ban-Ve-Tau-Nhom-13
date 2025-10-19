package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Email;
public class ControllerQuenMatKhau {
	private Stage stage;
	private Scene scene;
	private Parent root;
	private NhanVien_DAO nv_dao;
	private TaiKhoan_DAO tk_dao;
    @FXML
    private Button btnQuayLaiDangNhap;

    @FXML
    private Button btnXacNhan;

    @FXML
    private TextField txtEmail;
    @FXML
    public void quayLaiDangNhap(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_DangNhap.fxml"));
            root = loader.load();
//            String css=this.getClass().getResource("/gui/GD_Chinh.css").toExternalForm();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
//            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
    public void datLaiMatKhau() {
    	nv_dao = new NhanVien_DAO();
    	tk_dao = new TaiKhoan_DAO();
    	Email email = new Email();
    	try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	String txtemail = txtEmail.getText().trim();
    	if(txtemail.isEmpty()) {
			showError("Vui lòng nhập email!");
			return;
		}
    	ArrayList<NhanVien> dsNhanVien = nv_dao.getalltbNhanVien();
    	for (NhanVien nv : dsNhanVien) {
    		System.out.println("So sánh: "+nv.getTaiKhoan().getEmail()+" với "+txtemail);
    		if(nv.getTaiKhoan().getEmail().equals(txtemail)) {
    			System.out.println("Tìm thấy email trong hệ thống");
    			if (showConfirm("Xác nhận đặt lại mật khẩu cho tài khoản: " + nv.getTaiKhoan().getTenTaiKhoan() + " .Của nhân viên"+ nv.getTenNV() +" ?")) {
    				String pwd = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    				if(tk_dao.capNhatMatKhauTheoTen(nv.getTaiKhoan().getTenTaiKhoan(),pwd)) {
    					System.out.println("Đặt lại mật khẩu thành công");
    					
    					if(email.guiEmail(nv, pwd)) {
    						System.out.println("Gửi email thành công");
    						showInfo("Đặt lại mật khẩu thành công. Vui lòng kiểm tra email để nhận mật khẩu mới.");
    						return;
    					}
    					else {
    						System.out.println("Gửi email thất bại");
    						return;
    					}
    				}
    				else {
    					System.out.println("Đặt lại mật khẩu thất bại");
    					return;
    				}
    			}
    			else {
					System.out.println("Người dùng hủy đặt lại mật khẩu");
					return;
				}
				
			}
    	}
    	showError("Email không tồn tại trong hệ thống. Vui lòng kiểm tra lại!");
    	return;
	}
    public static void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Lỗi");
        a.setHeaderText("Đã xảy ra lỗi");
        a.setContentText(message);
        a.showAndWait(); // chặn cho tới khi người dùng đóng
    }
    public static void showInfo(String message) {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Thông báo");
		a.setHeaderText(null);
		a.setContentText(message);
		a.showAndWait(); // chặn cho tới khi người dùng đóng
	}
    public static boolean showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Nút rõ ràng: Có / Không
        ButtonType yes = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType no  = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yes;
    }
}
