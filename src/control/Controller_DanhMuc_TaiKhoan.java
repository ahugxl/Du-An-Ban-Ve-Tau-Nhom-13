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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

public class Controller_DanhMuc_TaiKhoan implements Initializable {

	@FXML
	private TextField txtTim;
	@FXML
	private TextField txtTenTaiKhoan;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtRole;

	@FXML
	private Button btnSua;
	@FXML
	private Button btnXoaThongTin;

	@FXML
	private TableView<TaiKhoan> tbTaiKhoan;
	@FXML
	private TableColumn<TaiKhoan, String> colTenTaiKhoan;
	@FXML
	private TableColumn<TaiKhoan, String> colEmail;
	@FXML
	private TableColumn<TaiKhoan, String> colChucVu;
	@FXML
	private TableColumn<TaiKhoan, Void> colThaoTac;

	private ObservableList<TaiKhoan> ds = FXCollections.observableArrayList();
	private ObservableList<TaiKhoan> dsLoc = FXCollections.observableArrayList();
	private TaiKhoan tk = null;

	private final TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();
	private final NhanVien_DAO nhanVienDAO = new NhanVien_DAO();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setupSearch();
		loadDataToTable();
		tbTaiKhoan.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        capNhatTaiKhoan(newSelection);
		    } else {
		        xoaThongTin();
		    }
		});
	}

	public void loadDataToTable() {
		ds = FXCollections.observableArrayList(taiKhoanDAO.getAllTaiKhoan());
		dsLoc.setAll(ds);
		colTenTaiKhoan.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenTaiKhoan()));
		colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		colChucVu.setCellValueFactory(cellData -> {
			try {
				NhanVien nv = nhanVienDAO.getNhanVienTheoTaiKhoan(cellData.getValue().getTenTaiKhoan());
				if (nv != null && nv.getCv() != null) {
					return new SimpleStringProperty(nv.getCv().getTenChucVu());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return new SimpleStringProperty("N/A");
		});
		setupActionsColumn();
		tbTaiKhoan.setItems(dsLoc);
	}

	private void setupActionsColumn() {
		Callback<TableColumn<TaiKhoan, Void>, TableCell<TaiKhoan, Void>> cellFactory = new Callback<>() {
			@Override
			public TableCell<TaiKhoan, Void> call(final TableColumn<TaiKhoan, Void> param) {
				final TableCell<TaiKhoan, Void> cell = new TableCell<>() {

					private final Button resetBtn = new Button("Reset MK");
					{
						resetBtn.getStyleClass().add("reset-button");
						resetBtn.setOnAction(event -> {
							TaiKhoan account = getTableView().getItems().get(getIndex());
							resetPassword(account);
						});
					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							HBox hbox = new HBox(8, resetBtn);
							hbox.setAlignment(Pos.CENTER);
							setGraphic(hbox);
						}
					}
				};
				return cell;
			}
		};
		colThaoTac.setCellFactory(cellFactory);
	}

	private void setupSearch() {
		txtTim.textProperty().addListener((observable, oldValue, newValue) -> {
			filterAccounts(newValue);
		});
	}

	@FXML
	private void tim() {
		filterAccounts(txtTim.getText());
	}

	private void filterAccounts(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			dsLoc.setAll(ds);
		} else {
			String lowerKeyword = keyword.toLowerCase().trim();
			dsLoc.setAll(
					ds.stream()
							.filter(tk -> tk.getTenTaiKhoan().toLowerCase().contains(lowerKeyword)
									|| (tk.getEmail() != null && tk.getEmail().toLowerCase().contains(lowerKeyword)))
							.toList());
		}
	}
	
	@FXML
	private void sua() {
		if (!validateEmail()) {
			return;
		}
		try {
			String newEmail = txtEmail.getText().trim();
			boolean success = taiKhoanDAO.capNhatEmail(tk.getTenTaiKhoan(), newEmail);
			if (success) {
				tk.setEmail(newEmail);
				tbTaiKhoan.refresh();
				showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật email thành công!");
				xoaThongTin();
			} else {
				showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật email!");
			}

		} catch (Exception e) {
			showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void resetPassword(TaiKhoan account) {
//		Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
//		confirmAlert.setTitle("Xác nhận reset mật khẩu");
//		confirmAlert.setHeaderText("Bạn có chắc chắn muốn reset mật khẩu?");
//		confirmAlert.setContentText("Tài khoản: " + account.getTenTaiKhoan() + "\n\n"
//				+ "⚠️ Mật khẩu sẽ được đặt lại về: 1\n" + "Người dùng cần đổi mật khẩu sau khi đăng nhập!");
//
//		Optional<ButtonType> result = confirmAlert.showAndWait();
//		if (result.isPresent() && result.get() == ButtonType.OK) {
//			try {
//				boolean success = taiKhoanDAO.resetMatKhau(account.getTenTaiKhoan());
//				if (success) {
//					account.setMatKhau("1");
//					tbTaiKhoan.refresh();
//
//					showAlert(Alert.AlertType.INFORMATION, "Thành công", "✅ Reset mật khẩu thành công!\n\n"
//							+ "Tài khoản: " + account.getTenTaiKhoan() + "\n" + "Mật khẩu mới: 1");
//				} else {
//					showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể reset mật khẩu!");
//				}
//
//			} catch (Exception e) {
//				showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi: " + e.getMessage());
//				e.printStackTrace();
//			}
//		}
	}

	private void capNhatTaiKhoan(TaiKhoan taikhoan) {
		tk = taikhoan;
		txtTenTaiKhoan.setText(tk.getTenTaiKhoan());
		txtEmail.setText(tk.getEmail());
		try {
	        NhanVien nv = nhanVienDAO.getNhanVienTheoTaiKhoan(tk.getTenTaiKhoan());
	        if (nv != null && nv.getCv() != null) {
	            txtRole.setText(nv.getCv().getTenChucVu());
	        } else {
	            txtRole.setText("N/A");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        txtRole.setText("N/A");
	    }
		btnSua.setVisible(true);
		btnSua.setManaged(true);
	}

	@FXML
	private void xoaThongTin() {
		txtTenTaiKhoan.clear();
		txtEmail.clear();
		txtRole.clear();;
		tbTaiKhoan.getSelectionModel().clearSelection();
		tk = null;
		btnSua.setVisible(false);
		btnSua.setManaged(false);
	}

	private boolean validateEmail() {
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