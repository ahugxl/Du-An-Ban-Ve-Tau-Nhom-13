package control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.ChucVu;
import entity.ChuyenTau;
import entity.NhanVien;
import entity.TaiKhoan;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ControllerChinh {

    @FXML
    private Label lblTenNhanVien;
    @FXML
    private Pane centerPane;
    // THÊM DÒNG NÀY: Khai báo Label cho chức vụ từ FXML
    @FXML
    private Label lblChucVu; 

    private NhanVien nhanVien;
    private TaiKhoan taiKhoan;
    private boolean laQuanLy; // Biến để lưu quyền (true = quản lý, false = nhân viên)
    private NhanVien_DAO nv_dao;
    private Node centerBanDau;  
    /**
     * Phương thức này nhận dữ liệu từ LoginController
     * @throws SQLException 
     */
    @FXML
    private void initialize() {
        centerBanDau = mainBorderPane.getCenter();
    }
    public void setTaiKhoan(TaiKhoan tk) throws SQLException {
    	this.taiKhoan = tk;
    	try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		nv_dao= new NhanVien_DAO();
		NhanVien nv =nv_dao.getNhanVienTheoTaiKhoan(taiKhoan.getTenTaiKhoan());
		if(nv!=null) {
			this.nhanVien=nv;
		}
		else {
			System.out.println("Loi khong tim thay nhan vien phu hop voi ten tai khoan");
		}
        
		String chucVu = "";
		if(nv.getCv()==ChucVu.NhanVienQuanLy) {
			laQuanLy=true;
			 chucVu = "Quản lý";
		}
		else{
			laQuanLy=false;
			chucVu = "Nhân viên ban ve";
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
	        Scene scene = new Scene(root);
	        String css=this.getClass().getResource("/gui/GD_TimVeThuong.css").toExternalForm();
			scene.getStylesheets().add(css);
	        newStage.setScene(scene);
	        newStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showTicketResultsView(List<ChuyenTau> tripResults) { // Thêm tham số List<ChuyenTau>
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_DanhSachToaNgoi.fxml"));
	        Parent view = loader.load();
	        
	        // Lấy controller của view kết quả
	        ControllerDanhSachToaNgoi controller = loader.getController();
	        
	        // TRUYỀN DỮ LIỆU: Gọi phương thức trong controller đó để hiển thị kết quả
	        controller.displayTrips(tripResults); 
	        
	        // (Tùy chọn) Truyền tham chiếu của ControllerChinh qua
	        controller.setMainController(this);

	        String css = getClass().getResource("/gui/GD_DanhSachToaNgoi.css").toExternalForm();
	        view.getStylesheets().add(css);
	        
	        mainBorderPane.setCenter(view);

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
	public void hienGiaoDienTimVe() {
		try {


	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_TimVe.fxml"));
	        Parent timVeUI = loader.load();
	        
	        String css=this.getClass().getResource("/gui/GD_TimVe.css").toExternalForm();
	        timVeUI.getStylesheets().add(css);
	        mainBorderPane.setCenter(timVeUI);
	    } catch (IOException e) {
	    	System.err.println("Không thể tải file GD_TimVe.fxml");
	        e.printStackTrace();
	    }
	}
	public void hienGiaoDienHuyVe() {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_HuyVe.fxml"));
	        Parent timVeUI = loader.load();
	        
	        String css=this.getClass().getResource("/gui/GD_TimVe.css").toExternalForm();
	        timVeUI.getStylesheets().add(css);
	        mainBorderPane.setCenter(timVeUI);
	    } catch (IOException e) {
	    	System.err.println("Không thể tải file GD_HuyVe.fxml");
	        e.printStackTrace();
	    }
	}
	public void hienGiaoDienInLaiVe() {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_InLaiVe.fxml"));
	        Parent timVeUI = loader.load();
	        
	        String css=this.getClass().getResource("/gui/GD_TimVe.css").toExternalForm();
	        timVeUI.getStylesheets().add(css);
	        mainBorderPane.setCenter(timVeUI);
	    } catch (IOException e) {
	    	System.err.println("Không thể tải file GD_HuyVe.fxml");
	        e.printStackTrace();
	    }
	}
	@FXML
	public void chuyenSangGDThongKe(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_ThongKe.fxml"));
	        Parent thongKeUI = loader.load();

	        ControllerThongKe thongKeController = loader.getController();
	        thongKeController.setMainController(this);

	        mainBorderPane.setCenter(thongKeUI);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	@FXML
    private void doiNenTuFile() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ảnh", "*.png","*.jpg","*.jpeg"));
		File f = fc.showOpenDialog(centerPane.getScene().getWindow());
		if (f != null) {
		    String uri = f.toURI().toString(); // thành "file:/D:/.../hinh.png"
		    centerPane.setStyle(
		        "-fx-background-image: url('" + uri + "');" +
		        "-fx-background-size: cover;" +
		        "-fx-background-position: center;" +
		        "-fx-background-repeat: no-repeat;"
		    );
		}

    }
	@FXML
	 public void chuyenSangGDNhanVien(ActionEvent event) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_DanhMuc_NhanVien.fxml"));
           Parent nv = loader.load();
           mainBorderPane.setCenter(nv);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
	public void chuyenSangGDKhachHang(ActionEvent event) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_DanhMuc_KhachHang.fxml"));
           Parent nv = loader.load();
           String css = this.getClass().getResource("/gui/GD_DanhMuc_NhanVien.css").toExternalForm();
           nv.getStylesheets().add(css);
           mainBorderPane.setCenter(nv);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
	public void chuyenSangGDTaiKhoan(ActionEvent event) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_DanhMuc_TaiKhoan.fxml"));
           Parent nv = loader.load();
           String css = this.getClass().getResource("/gui/GD_DanhMuc_NhanVien.css").toExternalForm();
           nv.getStylesheets().add(css);
           mainBorderPane.setCenter(nv);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
	public void chuyenSangGDDoiMatKhau(ActionEvent event) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_HeThong_DoiMatKhau.fxml"));
           Parent nv = loader.load();
//           String css = this.getClass().getResource("/gui/GD_DanhMuc_NhanVien.css").toExternalForm();
//           nv.getStylesheets().add(css);
           mainBorderPane.setCenter(nv);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
	@FXML
	private void dangXuat(ActionEvent event) {
	    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmAlert.setTitle("Xác nhận đăng xuất");
	    confirmAlert.setHeaderText("Bạn có chắc muốn đăng xuất?");
	    confirmAlert.setContentText("Bạn sẽ cần đăng nhập lại để sử dụng hệ thống.");
	    
	    Optional<ButtonType> result = confirmAlert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        try {
	            // 1. Lấy Stage hiện tại để đóng
	            Stage currentStage = (Stage) mainBorderPane.getScene().getWindow();
	            
	            // 2. Load màn hình đăng nhập
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GD_DangNhap.fxml"));
	            Parent loginRoot = loader.load();
	            
	            // 3. Tạo Scene mới cho đăng nhập
	            Scene loginScene = new Scene(loginRoot);
	            
	            String css = getClass().getResource("/gui/GD_DangNhap.css").toExternalForm();
	            loginScene.getStylesheets().add(css);
	            
	            // 4. Tạo Stage mới cho màn hình đăng nhập
	            Stage loginStage = new Stage();
	            loginStage.setTitle("Đăng nhập - Hệ thống bán vé tàu");
	            loginStage.setScene(loginScene);
	            loginStage.setResizable(false);
	            
	            // 5. Đóng màn hình hiện tại
	            currentStage.close();
	            
	            // 6. Hiển thị màn hình đăng nhập
	            loginStage.show();
	            
	            // 7. Xóa thông tin session (nếu có)
	            this.nhanVien = null;
	            this.taiKhoan = null;
	            
//	            System.out.println("Đăng xuất thành công!");
	            
	        } catch (IOException e) {
	            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	            errorAlert.setTitle("Lỗi");
	            errorAlert.setHeaderText("Không thể đăng xuất");
	            errorAlert.setContentText("Lỗi: " + e.getMessage());
	            errorAlert.showAndWait();
	            e.printStackTrace();
	        }
	    }
	}

	/**
	 * YÊU CẦU 4: CHỨC NĂNG ĐÓNG ỨNG DỤNG
	 * - Xác nhận trước khi đóng
	 * - Thoát hoàn toàn ứng dụng
	 */
	@FXML
	private void dong(ActionEvent event) {
	    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmAlert.setTitle("Xác nhận thoát");
	    confirmAlert.setHeaderText("Bạn có chắc muốn thoát ứng dụng?");
	    confirmAlert.setContentText("Tất cả dữ liệu chưa lưu sẽ bị mất.");
	    
	    // Tùy chỉnh các nút
	    ButtonType btnThoat = new ButtonType("Thoát", ButtonBar.ButtonData.OK_DONE);
	    ButtonType btnHuy = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
	    confirmAlert.getButtonTypes().setAll(btnThoat, btnHuy);
	    
	    Optional<ButtonType> result = confirmAlert.showAndWait();
	    if (result.isPresent() && result.get() == btnThoat) {
	        try {
	            // Đóng kết nối database (nếu cần)
	            ConnectDB.getInstance().disconnect();
	            
	            System.out.println("Đang đóng ứng dụng...");
	            
	            // Thoát ứng dụng
	            Platform.exit();
	            System.exit(0);
	            
	        } catch (Exception e) {
	            // Nếu có lỗi vẫn thoát
//	            System.err.println("Lỗi khi đóng ứng dụng: " + e.getMessage());
	            Platform.exit();
	            System.exit(0);
	        }
	    }
	}
	@FXML
    private void hoanTac() {
		 mainBorderPane.setCenter(centerBanDau);
	}
}