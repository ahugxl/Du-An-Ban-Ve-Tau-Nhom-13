package control;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.Thue_DAO;
import entity.Thue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class Controller_QuanLyThue implements Initializable {

    @FXML private TextField txtMaThue;
    @FXML private TextField txtTenThue;
    @FXML private TextField txtMucThue;
    @FXML private ComboBox<String> cmbTrangThai;
    @FXML private DatePicker dateNgayBatDau;
    @FXML private TableView<Thue> tblThue;
    @FXML private TableColumn<Thue, String> colMaThue;
    @FXML private TableColumn<Thue, String> colTenThue;
    @FXML private TableColumn<Thue, Double> colMucThue;
    @FXML private TableColumn<Thue, String> colTrangThai;
    @FXML private TableColumn<Thue, LocalDate> colNgayBatDau;

    private Thue_DAO thueDAO = new Thue_DAO();
   
	private ControllerChinh mainController;
    
    public void setMainController(ControllerChinh mainController) {
        this.mainController = mainController;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cài đặt các cột cho TableView
        colMaThue.setCellValueFactory(new PropertyValueFactory<>("maSoThue"));
        colTenThue.setCellValueFactory(new PropertyValueFactory<>("tenThue"));
        colMucThue.setCellValueFactory(new PropertyValueFactory<>("mucThue"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colNgayBatDau.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));

        // Thêm các lựa chọn cho ComboBox
        cmbTrangThai.getItems().addAll("Đang áp dụng", "Hết hiệu lực");

        // Thêm listener để khi nhấp vào một dòng trong bảng, dữ liệu sẽ hiện lên form
        tblThue.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtMaThue.setText(newSelection.getMaSoThue());
                txtTenThue.setText(newSelection.getTenThue());
                txtMucThue.setText(String.valueOf(newSelection.getMucThue()));
                cmbTrangThai.setValue(newSelection.getTrangThai());
                dateNgayBatDau.setValue(newSelection.getNgayBatDau());
            }
        });

        // Tải dữ liệu ban đầu
        try {
			loadThueData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void loadThueData() throws SQLException {
        List<Thue> dsThue = thueDAO.getAllThue();
        tblThue.setItems(FXCollections.observableArrayList(dsThue));
        tblThue.refresh();
    }

    @FXML
    void handleThem(ActionEvent event) throws SQLException {
        // Lấy dữ liệu từ form
        String maThue = txtMaThue.getText();
        String tenThue = txtTenThue.getText();
        double mucThue = Double.parseDouble(txtMucThue.getText());
        String trangThai = cmbTrangThai.getValue();
        LocalDate ngayBatDau = dateNgayBatDau.getValue();

        // Tạo đối tượng và gọi DAO
        Thue thue = new Thue(maThue, tenThue, mucThue, trangThai, ngayBatDau);
        if (thueDAO.addThue(thue)) {
            showAlert(AlertType.INFORMATION, "Thành công", "Thêm thuế mới thành công!");
            loadThueData(); // Tải lại bảng
            handleXoaRong(null); // Xóa rỗng form
        } else {
            showAlert(AlertType.ERROR, "Thất bại", "Thêm thuế mới thất bại.");
        }
    }

    @FXML
    void handleSua(ActionEvent event) throws SQLException {
        // 1. Lấy đối tượng Thue đang được chọn trong bảng
        Thue selectedThue = tblThue.getSelectionModel().getSelectedItem();
        
        if (selectedThue == null) {
            showAlert(AlertType.WARNING, "Chưa chọn dữ liệu", "Vui lòng chọn một dòng trong bảng để sửa.");
            return;
        }
        
        // 2. Lấy thông tin mới từ các ô nhập liệu
        String tenThue = txtTenThue.getText();
        double mucThue = Double.parseDouble(txtMucThue.getText());
        String trangThai = cmbTrangThai.getValue();
        LocalDate ngayBatDau = dateNgayBatDau.getValue();
        
        // 3. Cập nhật thông tin cho đối tượng đã chọn
        selectedThue.setTenThue(tenThue);
        selectedThue.setMucThue(mucThue);
        selectedThue.setTrangThai(trangThai);
        selectedThue.setNgayBatDau(ngayBatDau);
        
        // 4. Gọi phương thức DAO để cập nhật xuống CSDL
        if (thueDAO.updateThue(selectedThue)) {
            showAlert(AlertType.INFORMATION, "Thành công", "Cập nhật thông tin thuế thành công!");
            
           
            loadThueData();
        } else {
            showAlert(AlertType.ERROR, "Thất bại", "Cập nhật thông tin thuế thất bại.");
        }
    }
    @FXML
    void handleXoaRong(ActionEvent event) {
        txtMaThue.clear();
        txtTenThue.clear();
        txtMucThue.clear();
        cmbTrangThai.getSelectionModel().clearSelection();
        dateNgayBatDau.setValue(null);
        tblThue.getSelectionModel().clearSelection();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
   
	
}