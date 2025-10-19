package control;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import connectDB.ConnectDB;
<<<<<<< HEAD
import dao.Ve_DAO_mthanh;
=======
import dao.KhachHang_DAO;
import dao.Ve_DAO;
import entity.KhachHang;
>>>>>>> 31c4a36eedea504558e94040528102682768f8b5
import entity.Ve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerTimVe extends ControllerTimVeBase {


    @FXML
    private DatePicker dateNgayBanVe;
	@FXML @Override
    public void initialize() {
        super.initialize();   // BẮT BUỘC gọi super để chạy logic chung
        // thêm xử lý riêng nếu có (ví dụ set tooltip, format thêm...)
    }


    @FXML
<<<<<<< HEAD
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
    private Label lblTenKH;

    @FXML
    private Label lblTimTheoMaVe;

    @FXML
    private Label lblTimTheoTTKH;

    @FXML
    private TableView<Ve> tblTimVe;
	private Ve_DAO_mthanh ve_dao;
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
=======
    protected  void timVeTheoNgay() {
    	try {
>>>>>>> 31c4a36eedea504558e94040528102682768f8b5
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	kh_dao = new KhachHang_DAO();
    	ArrayList<Ve> dsVe = ve_dao.getalltbVe();
		LocalDate ngay = dateNgayBanVe.getValue();
		ObservableList<Ve> data = FXCollections.observableArrayList();
		if(ngay==null) {
			hienTatCaVe();
			showError("Vui lòng chọn ngày để tìm kiếm!");
			return;
		}
		else{
			ArrayList<KhachHang> dsKH = null;
			
			dsKH = kh_dao.getAllKhachHang();
			
			for(Ve ve : dsVe) {
				if(ve.getNgayInVe().toLocalDate().equals(ngay)) {
					data.add(ve);
					
				}
			}
			if(data.isEmpty()) {
				hienTatCaVe();
				showError("Không tìm thấy vé với ngày đã chọn: " + ngay);
				return;
			}
			else {
				tblTimVe.setItems(data);
				return;
			}
			
		}
		
	}

<<<<<<< HEAD
        ve_dao = new Ve_DAO_mthanh();
        hienTatCaVe();
    }
=======
>>>>>>> 31c4a36eedea504558e94040528102682768f8b5

}
