package control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import entity.NhanVien;
import entity.TaiKhoan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class ControllerDangNhap implements Initializable{
	

	private Stage stage;
	private Scene scene;
	private Parent root;
	private MediaPlayer mediaPlayer; // Khai báo ở đây
	private TaiKhoan_DAO tk_dao;
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
     * @throws SQLException 
     */
 // control/LoginController.java
    @FXML
    void kiemTraDangNhap(ActionEvent event) throws SQLException {
    	try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		tk_dao = new TaiKhoan_DAO();
        // B1: Lấy thông tin người dùng nhập từ giao diện
        String username = txtTaiKhoan.getText();
        String password = txtMatKhau.getText();

        // B2: Tạo danh sách nhân viên mẫu (thực tế phần này sẽ được lấy từ database)
        ArrayList<TaiKhoan> dsTaiKhoan = new ArrayList<>();
        dsTaiKhoan = tk_dao.getalltbPhongBan();

        // B3: Tìm kiếm nhân viên trong danh sách
        TaiKhoan taiKhoanTimThay = null;
        for (TaiKhoan tk : dsTaiKhoan) {
            if (tk.getTenTaiKhoan().equals(username) && 
                tk.getMatKhau().equals(password)) {
            	taiKhoanTimThay = tk; // Đã tìm thấy nhân viên phù hợp
                break; // Thoát khỏi vòng lặp
            }
        }

        // B4: Xử lý kết quả đăng nhập
        if (taiKhoanTimThay != null) {
            // ✅ Đăng nhập thành công, chuyển màn hình và truyền dữ liệu
            try {
                // Tạo một đối tượng FXMLLoader để tải file FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_Chinh.fxml"));
                root = loader.load();

                // Lấy controller của màn hình chính
                ControllerChinh chinhController = loader.getController();

                // Gọi phương thức để truyền đối tượng NhanVien qua
                chinhController.setTaiKhoan(taiKhoanTimThay);

                // Hiển thị màn hình chính
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose(); // giải phóng tài nguyên media
                }
                String css=this.getClass().getResource("/gui/GD_Chinh.css").toExternalForm();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi Đăng nhập");
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản, mật khẩu hoặc vai trò không chính xác!");
            alert.showAndWait();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
            String musicFile = "src/music/music.mp3";
            Media media = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Lỗi: Không tìm thấy file nhạc hoặc không thể phát.");
            e.printStackTrace();
        }
		
	}
    
}