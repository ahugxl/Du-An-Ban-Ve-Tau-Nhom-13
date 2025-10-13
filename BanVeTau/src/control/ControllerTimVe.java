package control;

import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import dao.Ve_DAO;
import entity.Ve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private Label lblTenKH;

    @FXML
    private Label lblTimTheoMaVe;

    @FXML
    private Label lblTimTheoTTKH;

    @FXML
    private TableView<Ve> tblTimVe;
	private Ve_DAO ve_dao;
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
//    @FXML
//	private void timTheoMaVe() {
//	    String ma = txtMaVe.getText();
//	    if (ma == null || ma.isBlank()) {
//	        // load lại toàn bộ/hoặc thông báo
//	        hienTatCaVe();
//	        return;
//	    }
//	    Ve ve = ve_dao.timVeTheoMaVe(ma.trim());
//	    javafx.collections.ObservableList<Ve> data =
//	            javafx.collections.FXCollections.observableArrayList();
//	    if (ve != null) data.add(ve);
//	    tblTimVe.setItems(data);
//	}
}
