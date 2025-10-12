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
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerTimVe {
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
        clTenChuyen.setCellValueFactory(new PropertyValueFactory<>("chuyen"));            // Ve.getTenChuyen()
        clTenGhe.setCellValueFactory(new PropertyValueFactory<>("ghe"));                  // Ve.getTenGhe()
        clGaDi.setCellValueFactory(new PropertyValueFactory<>("tenGaDi"));                   // Ve.getTenGaDi()
        clGaDen.setCellValueFactory(new PropertyValueFactory<>("tenGaDen"));                 // Ve.getTenGaDen()
        clNgayIn.setCellValueFactory(new PropertyValueFactory<>("ngayInVeStr"));             // Ve.getNgayInVeStr()
        clLoaiHT.setCellValueFactory(new PropertyValueFactory<>("loaiHanhTrinhStr"));        // Ve.getLoaiHanhTrinhStr()
        clLoaiVe.setCellValueFactory(new PropertyValueFactory<>("loaiVeStr"));               // Ve.getLoaiVeStr()
        clTinhTrang.setCellValueFactory(new PropertyValueFactory<>("trangThaiVeStr"));       // Ve.getTrangThaiVeStr()
        clPhongCho.setCellValueFactory(new PropertyValueFactory<>("coPhongChopVipStr"));     // Ve.getCoPhongChopVipStr()

        

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
}
