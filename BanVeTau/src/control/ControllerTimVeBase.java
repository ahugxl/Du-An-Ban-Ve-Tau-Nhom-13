package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import dao.Ve_DAO;
import entity.KhachHang;
import entity.Ve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class ControllerTimVeBase {
	@FXML
	protected  TextField txtMaVe;
	@FXML
    protected  TextField txtSDT;
	@FXML
    protected  TextField txtCCCD;
    @FXML
    protected  Button btnInLaiVe;

    @FXML
    protected  Button btnTimTheoMaVe;

    @FXML
    protected  Button btnTimTheoThongTinKH;

    @FXML
    protected  TableColumn<Ve, String> clGaDen;

    @FXML
    protected  TableColumn<Ve, String> clGaDi;

    @FXML
    protected  TableColumn<Ve, String> clLoaiHT;

    @FXML
    protected  TableColumn<Ve, String> clLoaiVe;

    @FXML
    protected  TableColumn<Ve, String> clMaVe;

    @FXML
    protected  TableColumn<Ve, String> clNgayIn;

    @FXML
    protected  TableColumn<Ve, String> clPhongCho;

    @FXML
    protected  TableColumn<Ve, String> clTenChuyen;

    @FXML
    protected  TableColumn<Ve, String> clTenGhe;

    @FXML
    protected  TableColumn<Ve, String> clTenVe;

    @FXML
    protected  TableColumn<Ve, String> clTinhTrang;
    
    @FXML
    protected  TableColumn<Ve, String> clTenKH;

    @FXML
    protected  Label lblCCCDKH;

    @FXML
    protected  Label lblChucNang;

    @FXML
    protected  Label lblMaVe;

    @FXML
    protected  Label lblSDTKH;

    

    @FXML
    protected  Label lblTimTheoMaVe;

    @FXML
    protected  Label lblTimTheoTTKH;

    @FXML
    protected  TableView<Ve> tblTimVe;
	protected  Ve_DAO ve_dao = new Ve_DAO();
	protected  KhachHang_DAO kh_dao = new KhachHang_DAO();
	@FXML
    protected  void initialize() {
        clMaVe.setCellValueFactory(new PropertyValueFactory<>("maVe"));
        clTenVe.setCellValueFactory(new PropertyValueFactory<>("tenVe"));
        clTenChuyen.setCellValueFactory(new PropertyValueFactory<>("chuyen"));            
        clTenGhe.setCellValueFactory(new PropertyValueFactory<>("ghe"));               
        clGaDi.setCellValueFactory(new PropertyValueFactory<>("tenGaDi"));                 
        clGaDen.setCellValueFactory(new PropertyValueFactory<>("tenGaDen"));               
        clNgayIn.setCellValueFactory(new PropertyValueFactory<>("ngayInVeStr"));            
        clLoaiHT.setCellValueFactory(new PropertyValueFactory<>("loaiHanhTrinhStr"));        
        clLoaiVe.setCellValueFactory(new PropertyValueFactory<>("loaiVeStr"));               
        clTinhTrang.setCellValueFactory(new PropertyValueFactory<>("trangThaiVeStr"));      
        clPhongCho.setCellValueFactory(new PropertyValueFactory<>("coPhongChoVipStr"));     
        clTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));

        

        // 2) Kết nối DB + nạp dữ liệu ban đầu
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ve_dao = new Ve_DAO();
        hienTatCaVe();
        bindSelection();
    }
	protected void bindSelection() { /* mặc định không làm gì */ }
    public void hienTatCaVe() {
        try {
            ArrayList<Ve> dsVe = ve_dao.getalltbVe();
            if (dsVe != null && !dsVe.isEmpty()) {
                ObservableList<Ve> data = FXCollections.observableArrayList(dsVe);
                tblTimVe.setItems(data);
            } else {
                // không có dữ liệu → clear bảng
                tblTimVe.getItems().clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
	protected  void timTheoMaVe() {
	    String ma = txtMaVe.getText();
	    if (ma == null || ma.isBlank()) {
	        hienTatCaVe();
	        showError("Vui lòng nhập mã vé để tìm kiếm!");
	        return;
	    }
	    Ve ve = ve_dao.getVeTheoMa(ma.trim());
	    javafx.collections.ObservableList<Ve> data =
	            javafx.collections.FXCollections.observableArrayList();
	    if (ve != null) {
	    	data.add(ve);
	    	tblTimVe.setItems(data);
	    }
	    else { 
	    	//hienTatCaVe();
	        showError("Không tìm thấy vé với mã: " + ma);
	        return;
	    }
	   
	}
    @FXML
    protected  void timVeTheoCCCDHoacSDT() {
    	try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	kh_dao = new KhachHang_DAO();
    	ArrayList<Ve> dsVe = ve_dao.getalltbVe();
		String sdt = txtSDT.getText().trim();
		String cccd = txtCCCD.getText().trim();
		ObservableList<Ve> data = FXCollections.observableArrayList();
		if(sdt.isBlank() && cccd.isBlank()) {
			hienTatCaVe();
			showError("Vui lòng nhập SĐT hoặc CCCD để tìm kiếm!");
			return;
		}
		else if(!sdt.isBlank() && !cccd.isBlank()) {
			hienTatCaVe();
			showError("Vui lòng chỉ nhập SĐT hoặc CCCD để tìm kiếm!");
			return;
		}
		else if(!sdt.isBlank() && cccd.isBlank()) {
			ArrayList<KhachHang> dsKH = null;
			try {
				dsKH = kh_dao.getDsKhachHangTheoSoDienThoai(sdt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Ve ve : dsVe) {
				for(KhachHang kh : dsKH) {
					System.out.println(ve.getKhachHang().getMaKhachHang());
					if(ve.getKhachHang().getMaKhachHang().equals(kh.getMaKhachHang())) {
						data.add(ve);
						
					}
					
				}
			}
			if(data.isEmpty()) {
				hienTatCaVe();
				showError("Không tìm thấy vé với SĐT: " + sdt);
				return;
			}
			else {
				tblTimVe.setItems(data);
				return;
			}
			
		}
		else if(!cccd.isBlank() && sdt.isBlank()) {
			ArrayList<KhachHang> dsKH = null;
			try {
				dsKH = kh_dao.getDsKhachHangTheoCCCD(cccd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Ve ve : dsVe) {
				for(KhachHang kh : dsKH) {
					System.out.println(ve.getKhachHang().getMaKhachHang());
					if(ve.getKhachHang().getMaKhachHang().equals(kh.getMaKhachHang())) {
						data.add(ve);
						
					}
					
				}
			}
			if(data.isEmpty()) {
				hienTatCaVe();
				showError("Không tìm thấy vé với CCCD: " + cccd);
				return;
			}
			else {
				tblTimVe.setItems(data);
				return;
			}
			
		}
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
