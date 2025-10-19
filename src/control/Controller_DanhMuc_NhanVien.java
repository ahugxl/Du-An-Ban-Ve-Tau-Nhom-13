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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import connectDB.ConnectDB;
import dao.ChucVu_DAO;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;

public class Controller_DanhMuc_NhanVien implements Initializable {

	// Form Fields
	@FXML
	private TextField txtTim;
	@FXML
	private TextField txtMaNhanVien;
	@FXML
	private TextField txtTenNhanVien;
	@FXML
	private DatePicker txtNgaySinh;
	@FXML
	private RadioButton rdoNam;
	@FXML
	private RadioButton rdoNu;
	@FXML
	private ToggleGroup gioiTinhGroup;
	@FXML
	private TextField txtSoDienThoai;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtTrangThaiLamViec;
	@FXML
	private ComboBox<ChucVu> cboChucVu;
	@FXML
	private TextField tenTaiKhoanField;

	// Buttons
	@FXML
	private Button btnThem;
	@FXML
	private Button btnSua;
	@FXML
	private Button btnXoaThongTin;

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
	private NhanVien nhanVienDuocChon = null;
	private final NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
	private final TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();
	private final ChucVu_DAO chucVuDAO= new ChucVu_DAO();
	private int nvCounter = 1;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		loadComboBoxData();
		setupSearch();
		loadDataToTable();
		// Tự động sinh mã NV khi khởi tạo form thêm mới
		hienMaKhiMoForm();
		tbNhanVien.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        capNhatNhanVien(newSelection);
		    } else {
		        xoaThongTin();
		    }
		});
	}

	private void loadComboBoxData() {
		ObservableList<ChucVu> dsChucVu = FXCollections.observableArrayList(chucVuDAO.getAllChucVu());
	    cboChucVu.setItems(dsChucVu);
	}

	public void loadDataToTable() {
		ds = FXCollections.observableArrayList(nhanVienDAO.getalltbNhanVien());
		dsLoc.setAll(ds);

		// Cập nhật counter dựa trên mã NV lớn nhất trong database
		if (!ds.isEmpty()) {
			int maxCounter = 0;
			for (NhanVien nv : ds) {
				String maNV = nv.getMaNhanVien();
				if (maNV.startsWith("NV")) {
					try {
						int num = Integer.parseInt(maNV.substring(2));
						if (num > maxCounter) {
							maxCounter = num;
						}
					} catch (NumberFormatException e) {
					}
				}
			}
			nvCounter = maxCounter + 1;
		}

		colMaNhanVien.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
		colTenNhanVien.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNV()));
		colNgaySinh.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNgaySinh()));
		colGioiTinh.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().isGioiTinh() ? "Nam" : "Nữ"));
		colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSdt()));
		colTrangThai
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrangThaiLamViec()));
		colChucVu.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCv().getTenChucVu()));
		
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
					private final Button btnXoaBang = new Button("Xóa");

					{
						btnXoaBang.getStyleClass().add("btnXoaBang");
						btnXoaBang.setOnAction(event -> {
							NhanVien nhanVien = getTableView().getItems().get(getIndex());
							xoaNhanVien(nhanVien);

						});
					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							HBox hbox = new HBox(10, btnXoaBang);
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

//	tạo mã nv tự động
	private String taoMaNhanVienTuDong() {
		return String.format("NV%03d", nvCounter);
	}

//	Hiển thị mã nv khi mở form
	private void hienMaKhiMoForm() {
		if (btnThem.isVisible()) {
			txtMaNhanVien.setText(taoMaNhanVienTuDong());
		}
	}

	//Tạo tk tự động
	private String taoTaiKhoanTuDong(String maNV, String tenNV) {
		String maNVLower = maNV.toLowerCase();

		// Lấy từ cuối cùng của tên
		String[] words = tenNV.trim().split("\\s+");
		String lastName = words[words.length - 1];

		// Bỏ dấu tiếng Việt
		String lastNameNoAccent = removeDiacritics(lastName.toLowerCase());

		return maNVLower + "_" + lastNameNoAccent;
	}

	//bỏ dấu
	private String removeDiacritics(String str) {
		String[] vietnameseChars = { "à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "ì|í|ị|ỉ|ĩ",
				"ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "ỳ|ý|ỵ|ỷ|ỹ", "đ" };
		String[] replacements = { "a", "e", "i", "o", "u", "y", "d" };

		for (int i = 0; i < vietnameseChars.length; i++) {
			str = str.replaceAll(vietnameseChars[i], replacements[i]);
		}
		return str;
	}

	//cập nhật txtTenTaiKhoan
	@FXML
	private void taoTenTaiKhoanTuDong() {
		String maNV = txtMaNhanVien.getText();
		String tenNV = txtTenNhanVien.getText();

		if (!maNV.isEmpty() && !tenNV.trim().isEmpty()) {
			String username = taoTaiKhoanTuDong(maNV, tenNV);
			tenTaiKhoanField.setText(username);
		}
	}

	private void setupSearch() {
		txtTim.textProperty().addListener((observable, oldValue, newValue) -> {
			loc(newValue);
		});

		// Tự động tạo tên tài khoản khi nhập tên
		txtTenNhanVien.textProperty().addListener((obs, oldVal, newVal) -> {
			taoTenTaiKhoanTuDong();
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
			dsLoc.setAll(nhanVienDAO.tim(tuKhoa));
		}
	}

	/**
	 * YÊU CẦU 1: THÊM NHÂN VIÊN VỚI TỰ ĐỘNG TẠO TÀI KHOẢN - Mã NV: tự động (NV001,
	 * NV002...) - Tên TK: tự động (manhanvien_ten) - Email: nhập từ form (lưu vào
	 * TaiKhoan) - Mật khẩu mặc định: "1"
	 */
	@FXML
	private void them() {
		if (!validateInput()) {
			return;
		}

		try {
			// 1. Lấy thông tin từ form
			String maNV = txtMaNhanVien.getText(); // Đã được tự động tạo
			String tenNV = txtTenNhanVien.getText().trim();
			String username = tenTaiKhoanField.getText(); // Đã được tự động tạo
			String email = txtEmail.getText().trim();
			String trangThaiLamViec = txtTrangThaiLamViec.getText().trim();
			TaiKhoan taiKhoan = new TaiKhoan(username, "1", email);
			if (!taiKhoanDAO.themTaiKhoan(taiKhoan)) {
				showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tạo tài khoản! Có thể tên tài khoản đã tồn tại.");
				return;
			}

			ChucVu cv = cboChucVu.getValue();
			NhanVien nv = new NhanVien(maNV, tenNV, txtNgaySinh.getValue(), rdoNam.isSelected(),
					txtSoDienThoai.getText().trim(), trangThaiLamViec, cv, taiKhoan);

			boolean nvSuccess = nhanVienDAO.themNhanVien(nv);

			if (nvSuccess) {
				// Cập nhật counter và danh sách
				nvCounter++;
				ds.add(nv);
				dsLoc.add(nv);

				// Hiển thị thông báo thành công
				String message = String.format("✅ THÊM NHÂN VIÊN THÀNH CÔNG!\n\n" + "📋 THÔNG TIN TÀI KHOẢN:\n"
						+ "━━━━━━━━━━━━━━━━━━━━━━━\n" + "Mã NV: %s\n" + "Tên đăng nhập: %s\n" + "Mật khẩu mặc định: 1\n"
						+ "Email: %s\n" + "━━━━━━━━━━━━━━━━━━━━━━━\n\n" + "⚠️ LƯU Ý:\n"
						+ "• Tài khoản được tạo tự động\n" + "• Nhân viên cần đổi mật khẩu sau lần đăng nhập đầu\n"
						+ "• Truy cập: Hệ thống > Đổi mật khẩu", maNV, username, email);

				showAlert(Alert.AlertType.INFORMATION, "Thành công", message);
				xoaThongTin();

			} else {
				// Nếu thêm nhân viên thất bại, rollback: xóa tài khoản đã tạo
				taiKhoanDAO.xoaTaiKhoan(username);
				showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm nhân viên!");
			}

		} catch (Exception e) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi hệ thống: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void sua() {
		if (nhanVienDuocChon == null || !validateInput()) {
			return;
		}

		try {
			nhanVienDuocChon.setTenNV(txtTenNhanVien.getText().trim());
			nhanVienDuocChon.setNgaySinh(txtNgaySinh.getValue());
			nhanVienDuocChon.setGioiTinh(rdoNam.isSelected());
			nhanVienDuocChon.setSdt(txtSoDienThoai.getText().trim());
			nhanVienDuocChon.setTrangThaiLamViec(txtTrangThaiLamViec.getText().trim());
			nhanVienDuocChon.setCv(cboChucVu.getValue());

			// Cập nhật email trong tài khoản
			String email = txtEmail.getText().trim();
			taiKhoanDAO.capNhatEmail(nhanVienDuocChon.getTaiKhoan().getTenTaiKhoan(), email);

			boolean success = nhanVienDAO.capNhatNhanVien(nhanVienDuocChon);

			if (success) {
				tbNhanVien.refresh();
				showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật nhân viên thành công!");
				xoaThongTin();
			} else {
				showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật!");
			}

		} catch (Exception e) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void capNhatNhanVien(NhanVien nhanVien) {
		nhanVienDuocChon = nhanVien;

		txtMaNhanVien.setText(nhanVien.getMaNhanVien());
		txtTenNhanVien.setText(nhanVien.getTenNV());
		txtNgaySinh.setValue(nhanVien.getNgaySinh());

		if (nhanVien.isGioiTinh()) {
			rdoNam.setSelected(true);
		} else {
			rdoNu.setSelected(true);
		}

		txtSoDienThoai.setText(nhanVien.getSdt());
		txtTrangThaiLamViec.setText(nhanVien.getTrangThaiLamViec());
		cboChucVu.setValue(nhanVien.getCv());
		tenTaiKhoanField.setText(nhanVien.getTaiKhoan().getTenTaiKhoan());
		txtEmail.setText(nhanVien.getTaiKhoan().getEmail());
		try {
			TaiKhoan tk = taiKhoanDAO.getTaiKhoanTheoTen(nhanVien.getTaiKhoan().getTenTaiKhoan());
			txtEmail.setText(tk.getEmail());
		} catch (Exception e) {
			txtEmail.setText("");
		}

		btnThem.setVisible(false);
		btnThem.setManaged(false);
		btnSua.setVisible(true);
		btnSua.setManaged(true);
	}

	private void xoaNhanVien(NhanVien nhanVien) {
		Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmAlert.setTitle("Xác nhận xóa");
		confirmAlert.setHeaderText("Bạn có chắc chắn muốn xóa nhân viên này?");
		confirmAlert.setContentText("Mã NV: " + nhanVien.getMaNhanVien() + "\n" + "Tên: " + nhanVien.getTenNV() + "\n\n"
				+ "⚠️ Sẽ xóa mềm (đánh dấu trangThaiXoa = true)!");

		Optional<ButtonType> result = confirmAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				if (nhanVienDAO.xoaNhanVien(nhanVien)&& taiKhoanDAO.anTaiKhoan(nhanVien.getTaiKhoan().getTenTaiKhoan())) {
					ds.remove(nhanVien);
					dsLoc.remove(nhanVien);
					showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa nhân viên thành công!");
					
				} else {
					showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa!");
				}
			} catch (Exception e) {
				showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi: " + e.getMessage());
			}
		}
	}

	@FXML
	private void xoaThongTin() {
		txtMaNhanVien.clear();
		txtTenNhanVien.clear();
		txtNgaySinh.setValue(null);
		rdoNam.setSelected(true);
		txtSoDienThoai.clear();
		txtEmail.clear();
		txtTrangThaiLamViec.clear();
		cboChucVu.setValue(null);
		tenTaiKhoanField.clear();

		nhanVienDuocChon = null;
		btnThem.setVisible(true);
		btnThem.setManaged(true);
		btnSua.setVisible(false);
		btnSua.setManaged(false);

		// Tự động tạo mã NV mới cho lần thêm tiếp theo
		hienMaKhiMoForm();
	}

	private boolean validateInput() {
		// Validate tên
		if (txtTenNhanVien.getText().trim().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập tên nhân viên!");
			txtTenNhanVien.requestFocus();
			return false;
		}

		// Validate tên (chỉ chữ cái và khoảng trắng)
		if (!Pattern.matches("^[a-zA-ZÀ-ỹ\\s]+$", txtTenNhanVien.getText().trim())) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Tên chỉ được chứa chữ cái!");
			txtTenNhanVien.requestFocus();
			return false;
		}

		// Validate email
		String email = txtEmail.getText().trim();
		if (email.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập email!");
			txtEmail.requestFocus();
			return false;
		}
		if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", email)) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Email không hợp lệ!");
			txtEmail.requestFocus();
			return false;
		}

		// Validate ngày sinh
		if (txtNgaySinh.getValue() == null) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn ngày sinh!");
			txtNgaySinh.requestFocus();
			return false;
		}

		// Validate tuổi (>= 18)
		LocalDate ngaySinh = txtNgaySinh.getValue();
		if (ngaySinh.isAfter(LocalDate.now().minusYears(18))) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Nhân viên phải từ 18 tuổi trở lên!");
			txtNgaySinh.requestFocus();
			return false;
		}

		// Validate SĐT
		String sdt = txtSoDienThoai.getText().trim();
		if (sdt.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập số điện thoại!");
			txtSoDienThoai.requestFocus();
			return false;
		}
		if (!Pattern.matches("^0[0-9]{9}$", sdt)) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Số điện thoại phải có 10 số và bắt đầu bằng 0!");
			txtSoDienThoai.requestFocus();
			return false;
		}

		// Validate trạng thái
//		if (txtTrangThaiLamViec.getValue() == null) {
//			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn trạng thái làm việc!");
//			txtTrangThaiLamViec.requestFocus();
//			return false;
//		}

		// Validate chức vụ
		if (cboChucVu.getValue() == null) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn chức vụ!");
			cboChucVu.requestFocus();
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