package control;

import java.sql.SQLException;
import java.util.ArrayList;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerTimVe {
	@FXML
	private TextField txtMaVe;
	@FXML
    private TextField txtSDT;
	@FXML
    private TextField txtCCCD;
    @FXML
    private Button btnInLaiVe;

    @FXML
    private Button btnTimTheoMaVe;

    @FXML
    private Button btnTimTheoThongTinKH;

    @FXML
    private TableColumn<Ve, String> clGaDen;

    @FXML
    private TableColumn<Ve, String> clGaDi;

    @FXML
    private TableColumn<Ve, String> clLoaiHT;

    @FXML
    private TableColumn<Ve, String> clLoaiVe;

    @FXML
    private TableColumn<Ve, String> clMaVe;

    @FXML
    private TableColumn<Ve, String> clNgayIn;

    @FXML
    private TableColumn<Ve, String> clPhongCho;

    @FXML
    private TableColumn<Ve, String> clTenChuyen;

    @FXML
    private TableColumn<Ve, String> clTenGhe;

    @FXML
    private TableColumn<Ve, String> clTenVe;

    @FXML
    private TableColumn<Ve, String> clTinhTrang;
    
    @FXML
    private TableColumn<Ve, String> clTenKH;

    @FXML
    private Label lblCCCDKH;

    @FXML
    private Label lblChucNang;

    @FXML
    private Label lblMaVe;

    @FXML
    private Label lblSDTKH;

    

    @FXML
    private Label lblTimTheoMaVe;

    @FXML
    private Label lblTimTheoTTKH;

    @FXML
    private TableView<Ve> tblTimVe;
	private Ve_DAO ve_dao;
	private KhachHang_DAO kh_dao;

	@FXML
    private void initialize() {
        // 1) Cấu hình cellValueFactory KHÔNG dùng lambda — bám theo getter dẫn xuất trong Ve
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
    }

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
	private void timTheoMaVe() {
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
    private void timVeTheoCCCDHoacSDT() {
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
    @FXML
    private void inLaiVe() {
        Ve veDangChon = tblTimVe.getSelectionModel().getSelectedItem();
        if (veDangChon == null) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("In vé");
            a.setHeaderText("Chưa chọn vé");
            a.setContentText("Vui lòng chọn một vé trong bảng trước khi in.");
            a.showAndWait();
            return;
        }

        try {
            PdfExporter.exportVeToPdf(veDangChon, btnInLaiVe.getScene().getWindow());

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("In vé");
            a.setHeaderText("Xuất PDF thành công");
            a.setContentText("Vé đã được xuất ra PDF.");
            a.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("In vé");
            a.setHeaderText("Xuất PDF thất bại");
            a.setContentText("Lý do: " + ex.getMessage());
            a.showAndWait();
        }
    }

    public static void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Lỗi");
        a.setHeaderText("Đã xảy ra lỗi");
        a.setContentText(message);
        a.showAndWait(); // chặn cho tới khi người dùng đóng
    }
}
