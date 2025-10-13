package control;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import entity.NhanVien;
import dao.NhanVien_DAO;

public class Controller_DanhMuc_NhanVien implements Initializable {

	// Form Fields
	@FXML
	private TextField txtTim;
	@FXML
	private TextField maNhanVienField;
	@FXML
	private TextField tenNhanVienField;
	@FXML
	private DatePicker ngaySinhPicker;
	@FXML
	private RadioButton namRadio;
	@FXML
	private RadioButton nuRadio;
	@FXML
	private ToggleGroup gioiTinhGroup;
	@FXML
	private TextField soDienThoaiField;
	@FXML
	private ComboBox<String> cbbTrangThai;
	@FXML
	private ComboBox<String> cbbChucVu;
	@FXML
	private ComboBox<String> cbbTenTaiKhoan;

	// Buttons
	@FXML
	private Button addButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button clearButton;

	// Table and Columns
	@FXML
	private TableView<NhanVien> tbNhanVien;
	@FXML
	private TableColumn<NhanVien, String> colMaNhanVien;
	@FXML
	private TableColumn<NhanVien, String> colTenNhanVien;
	@FXML
	private TableColumn<NhanVien, LocalDate> colNgaySinh;
	@FXML
	private TableColumn<NhanVien, String> colGioiTinh;
	@FXML
	private TableColumn<NhanVien, String> colSoDienThoai;
	@FXML
	private TableColumn<NhanVien, String> colTrangThai;
	@FXML
	private TableColumn<NhanVien, String> colChucVu;
	@FXML
	private TableColumn<NhanVien, String> colTaiKhoan;
	@FXML
	private TableColumn<NhanVien, Void> colActions;

	private ObservableList<NhanVien> ds = FXCollections.observableArrayList();
	private ObservableList<NhanVien> dsLoc = FXCollections.observableArrayList();
	private NhanVien selectedEmployee = null;
	private final NhanVien_DAO nhanVienDAO = new NhanVien_DAO();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
//        loadComboBoxData();
		setupSearch();
		loadDataToTable();
	}

	private void loadComboBoxData() {
		// Load Chức vụ
		cbbChucVu.setItems(FXCollections.observableArrayList("CV001 - Giám đốc", "CV002 - Phó giám đốc",
				"CV003 - Trưởng phòng", "CV004 - Nhân viên", "CV005 - Thực tập sinh"));

		// Load Tài khoản
		cbbTenTaiKhoan.setItems(FXCollections.observableArrayList("admin01", "user01", "user02", "user03", "user04"));
	}

	public void loadDataToTable() {
//		ObservableList<NhanVien> ds = FXCollections.observableArrayList(nhanVienDAO.getalltbNhanVien());
    	ds=FXCollections.observableArrayList(nhanVienDAO.getalltbNhanVien());
    	dsLoc.setAll(ds);
		colMaNhanVien.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
		colTenNhanVien.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNV()));
		colNgaySinh.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNgaySinh()));
		colGioiTinh.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().isGioiTinh() ? "Nam" : "Nữ"));
		colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSdt()));
		colTrangThai.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().isTrangThaiLamViec() ? "Đang làm việc" : "Đã nghỉ"));
		colChucVu.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCv().getDisplayName()));
		colTaiKhoan.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getTaiKhoan().getTenTaiKhoan()));
		loadColThaoTac();
		tbNhanVien.setItems(dsLoc);
	}

	private void loadColThaoTac() {
		Callback<TableColumn<NhanVien, Void>, TableCell<NhanVien, Void>> cellFactory = new Callback<>() {
			@Override
			public TableCell<NhanVien, Void> call(final TableColumn<NhanVien, Void> param) {
				final TableCell<NhanVien, Void> cell = new TableCell<>() {

					private final Button btnSuaBang = new Button("Sửa");
					private final Button btnXoaBang = new Button("Xóa");

					{
						btnSuaBang.getStyleClass().add("btnSuaBang");
						btnXoaBang.getStyleClass().add("btnXoaBang");

						btnSuaBang.setOnAction(event -> {
							NhanVien employee = getTableView().getItems().get(getIndex());
							handleEditEmployee(employee);
						});

						btnXoaBang.setOnAction(event -> {
							NhanVien employee = getTableView().getItems().get(getIndex());
							handleDeleteEmployee(employee);
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							HBox hbox = new HBox(10, btnSuaBang, btnXoaBang);
							hbox.setAlignment(Pos.CENTER);
							setGraphic(hbox);
						}
					}
				};
				return cell;
			}
		};
		colActions.setCellFactory(cellFactory);
	}

	private void setupSearch() {
		txtTim.textProperty().addListener((observable, oldValue, newValue) -> {
			loc(newValue);
		});
	}

	@FXML
	private void tim() {
		loc(txtTim.getText());
	}

	private void loc(String tuKhoa) {
		if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
			dsLoc.setAll(ds);
		} else {
			var ketQua = nhanVienDAO.tim(tuKhoa);
			System.out.println("Kết quả tìm: " + ketQua.size());
			dsLoc.setAll(nhanVienDAO.tim(tuKhoa));
		}
	}

	@FXML
	private void xoaThongTin() {
		maNhanVienField.clear();
		tenNhanVienField.clear();
		ngaySinhPicker.setValue(null);
		namRadio.setSelected(true);
		soDienThoaiField.clear();
		cbbTrangThai.setValue(null);
		cbbChucVu.setValue(null);
		cbbTenTaiKhoan.setValue(null);

		selectedEmployee = null;
		addButton.setVisible(true);
		addButton.setManaged(true);
		updateButton.setVisible(false);
		updateButton.setManaged(false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@FXML
	private void them() {
		if (!validateInput()) {
			return;
		}

		NhanVien newEmployee = new NhanVien(maNhanVienField.getText(), tenNhanVienField.getText(),
				ngaySinhPicker.getValue(), namRadio.isSelected(), soDienThoaiField.getText(), cbbTrangThai.getValue(),
				cbbChucVu.getValue(), cbbTenTaiKhoan.getValue());

		ds.add(newEmployee);
		dsLoc.add(newEmployee);

		showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm nhân viên thành công!");
		xoaThongTin();
	}

	@FXML
	private void sua() {
		if (selectedEmployee == null || !validateInput()) {
			return;
		}

		selectedEmployee.setMaNhanVien(maNhanVienField.getText());
		selectedEmployee.setTenNhanVien(tenNhanVienField.getText());
		selectedEmployee.setNgaySinh(ngaySinhPicker.getValue());
		selectedEmployee.setGioiTinh(namRadio.isSelected());
		selectedEmployee.setSoDienThoai(soDienThoaiField.getText());
		selectedEmployee.setTrangThaiLamViec(cbbTrangThai.getValue());
		selectedEmployee.setMaChucVu(cbbChucVu.getValue());
		selectedEmployee.setTenTaiKhoan(cbbTenTaiKhoan.getValue());

		tbNhanVien.refresh();
		showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật nhân viên thành công!");
		xoaThongTin();
	}

	private void handleEditEmployee(NhanVien employee) {
		selectedEmployee = employee;

		maNhanVienField.setText(employee.getMaNhanVien());
		tenNhanVienField.setText(employee.getTenNV());
		ngaySinhPicker.setValue(employee.getNgaySinh());

		if (employee.isGioiTinh()) {
			namRadio.setSelected(true);
		} else {
			nuRadio.setSelected(true);
		}

		soDienThoaiField.setText(employee.getSoDienThoai());
		cbbTrangThai.setValue(employee.getTrangThaiLamViec());
		cbbChucVu.setValue(employee.getMaChucVu());
		cbbTenTaiKhoan.setValue(employee.getTenTaiKhoan());

		addButton.setVisible(false);
		addButton.setManaged(false);
		updateButton.setVisible(true);
		updateButton.setManaged(true);
	}

	private void handleDeleteEmployee(NhanVien employee) {
		Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmAlert.setTitle("Xác nhận xóa");
		confirmAlert.setHeaderText("Bạn có chắc chắn muốn xóa nhân viên này?");
		confirmAlert.setContentText("Mã NV: " + employee.getMaNhanVien() + "\nTên: " + employee.getTenNV());

		Optional<ButtonType> result = confirmAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			ds.remove(employee);
			dsLoc.remove(employee);
			showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa nhân viên thành công!");
		}
	}

	

	private boolean validateInput() {
		if (maNhanVienField.getText().trim().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập mã nhân viên!");
			return false;
		}
		if (tenNhanVienField.getText().trim().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập tên nhân viên!");
			return false;
		}
		if (ngaySinhPicker.getValue() == null) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn ngày sinh!");
			return false;
		}
		if (soDienThoaiField.getText().trim().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập số điện thoại!");
			return false;
		}
		if (cbbTrangThai.getValue() == null) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn trạng thái làm việc!");
			return false;
		}
		if (cbbChucVu.getValue() == null) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn chức vụ!");
			return false;
		}
		return true;
	}

	private void showAlert(Alert.AlertType type, String title, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
