package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller_DanhMuc_TaiKhoan implements Initializable {

    // =========================
    // 🔹 Khai báo các thành phần FXML
    // =========================

    @FXML private TextField txtTim;
    @FXML private TextField tenTaiKhoanField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> trangThaiComboBox;
    @FXML private Button btnThem;
    @FXML private Button btnSua;
    @FXML private Button btnXoaThongTin;

    @FXML private TableView<TaiKhoan> tbTaiKhoan;
    @FXML private TableColumn<TaiKhoan, String> colTenTaiKhoan;
    @FXML private TableColumn<TaiKhoan, String> colEmail;
    @FXML private TableColumn<TaiKhoan, String> colTrangThai;
    @FXML private TableColumn<TaiKhoan, String> colNgayTao;
    @FXML private TableColumn<TaiKhoan, Integer> colSoNVSuDung;
    @FXML private TableColumn<TaiKhoan, Void> colThaoTac;

    private ObservableList<TaiKhoan> danhSachTaiKhoan = FXCollections.observableArrayList();
    private ObservableList<TaiKhoan> danhSachGoc = FXCollections.observableArrayList();

    private TaiKhoan taiKhoanDangChon = null;

    // =========================
    // 🔹 Khởi tạo Controller
    // =========================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadSampleData();
        setupSearch();
        setupComboBox();

        btnSua.setVisible(false);
        btnSua.setManaged(false);
    }

    // =========================
    // 🔹 Thiết lập ComboBox trạng thái
    // =========================
    private void setupComboBox() {
        trangThaiComboBox.setItems(FXCollections.observableArrayList(
                "Hoạt động", "Khóa", "Chưa đổi mật khẩu"
        ));
    }

    // =========================
    // 🔹 Thiết lập cột cho TableView
    // =========================
    private void setupTableColumns() {
        colTenTaiKhoan.setCellValueFactory(data -> data.getValue().tenTaiKhoanProperty());
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());
        colTrangThai.setCellValueFactory(data -> data.getValue().trangThaiProperty());
        colNgayTao.setCellValueFactory(data -> data.getValue().ngayTaoProperty());
        colSoNVSuDung.setCellValueFactory(data -> data.getValue().soNVSuDungProperty().asObject());

        setupThaoTacColumn();
    }

    // =========================
    // 🔹 Cột "Thao tác" (Sửa / Xóa)
    // =========================
    private void setupThaoTacColumn() {
        colThaoTac.setCellFactory(param -> new TableCell<>() {
            private final Button btnSua = new Button("Sửa");
            private final Button btnXoa = new Button("Xóa");
            private final HBox box = new HBox(10, btnSua, btnXoa);

            {
                btnSua.getStyleClass().add("btnSuaBang");
                btnXoa.getStyleClass().add("btnXoaBang");

                btnSua.setOnAction(e -> {
                    TaiKhoan tk = getTableView().getItems().get(getIndex());
                    hienThongTin(tk);
                });

                btnXoa.setOnAction(e -> {
                    TaiKhoan tk = getTableView().getItems().get(getIndex());
                    xoa(tk);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(box);
                }
            }
        });
    }

    // =========================
    // 🔹 Nạp dữ liệu mẫu
    // =========================
    private void loadSampleData() {
        danhSachTaiKhoan.addAll(
                new TaiKhoan("admin", "admin@gmail.com", "Hoạt động", "2024-03-12", 5),
                new TaiKhoan("minhquang", "minhquang@gmail.com", "Khóa", "2024-02-01", 2),
                new TaiKhoan("hoanganh", "hoanganh@gmail.com", "Chưa đổi mật khẩu", "2024-04-21", 1)
        );
        danhSachGoc.setAll(danhSachTaiKhoan);
        tbTaiKhoan.setItems(danhSachTaiKhoan);
    }

    // =========================
    // 🔹 Tìm kiếm tài khoản
    // =========================
    @FXML
    private void tim() {
        String keyword = txtTim.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            danhSachTaiKhoan.setAll(danhSachGoc);
            return;
        }

        ObservableList<TaiKhoan> ketQua = FXCollections.observableArrayList();
        for (TaiKhoan tk : danhSachGoc) {
            if (tk.getTenTaiKhoan().toLowerCase().contains(keyword) ||
                tk.getEmail().toLowerCase().contains(keyword)) {
                ketQua.add(tk);
            }
        }
        danhSachTaiKhoan.setAll(ketQua);
    }

    private void setupSearch() {
        txtTim.textProperty().addListener((obs, oldVal, newVal) -> tim());
    }

    // =========================
    // 🔹 Thêm tài khoản mới
    // =========================
    @FXML
    private void them() {
        if (!validateInput()) return;

        TaiKhoan tk = new TaiKhoan(
                tenTaiKhoanField.getText().trim(),
                emailField.getText().trim(),
                trangThaiComboBox.getValue(),
                LocalDate.now().toString(),
                0
        );

        danhSachTaiKhoan.add(tk);
        danhSachGoc.add(tk);

        showAlert(Alert.AlertType.INFORMATION, "Thêm tài khoản thành công!");
        xoaThongTin();
    }

    // =========================
    // 🔹 Sửa tài khoản
    // =========================
    @FXML
    private void sua() {
        if (taiKhoanDangChon == null) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng chọn tài khoản để cập nhật!");
            return;
        }

        if (!validateInput()) return;

        taiKhoanDangChon.setTenTaiKhoan(tenTaiKhoanField.getText().trim());
        taiKhoanDangChon.setEmail(emailField.getText().trim());
        taiKhoanDangChon.setTrangThai(trangThaiComboBox.getValue());

        tbTaiKhoan.refresh();
        showAlert(Alert.AlertType.INFORMATION, "Cập nhật tài khoản thành công!");

        xoaThongTin();
    }

    // =========================
    // 🔹 Hiển thị thông tin khi nhấn "Sửa"
    // =========================
    private void hienThongTin(TaiKhoan tk) {
        taiKhoanDangChon = tk;

        tenTaiKhoanField.setText(tk.getTenTaiKhoan());
        emailField.setText(tk.getEmail());
        trangThaiComboBox.setValue(tk.getTrangThai());

        btnThem.setVisible(false);
        btnThem.setManaged(false);
        btnSua.setVisible(true);
        btnSua.setManaged(true);
    }

    // =========================
    // 🔹 Xóa tài khoản
    // =========================
    private void xoa(TaiKhoan tk) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn có chắc muốn xóa tài khoản này?");
        alert.setContentText("Tên tài khoản: " + tk.getTenTaiKhoan());

        if (alert.showAndWait().get() == ButtonType.OK) {
            danhSachTaiKhoan.remove(tk);
            danhSachGoc.remove(tk);
            showAlert(Alert.AlertType.INFORMATION, "Xóa tài khoản thành công!");
        }
    }

    // =========================
    // 🔹 Xóa form
    // =========================
    @FXML
    private void xoaThongTin() {
        tenTaiKhoanField.clear();
        emailField.clear();
        trangThaiComboBox.setValue(null);

        btnSua.setVisible(false);
        btnSua.setManaged(false);
        btnThem.setVisible(true);
        btnThem.setManaged(true);

        taiKhoanDangChon = null;
    }

    // =========================
    // 🔹 Kiểm tra hợp lệ dữ liệu nhập
    // =========================
    private boolean validateInput() {
        if (tenTaiKhoanField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập tên tài khoản!");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập email!");
            return false;
        }
        if (trangThaiComboBox.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng chọn trạng thái!");
            return false;
        }
        return true;
    }

    // =========================
    // 🔹 Hiển thị thông báo
    // =========================
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // =========================
    // 🔹 Lớp nội bộ đại diện cho Tài khoản
    // =========================
    public static class TaiKhoan {
        private final javafx.beans.property.SimpleStringProperty tenTaiKhoan;
        private final javafx.beans.property.SimpleStringProperty email;
        private final javafx.beans.property.SimpleStringProperty trangThai;
        private final javafx.beans.property.SimpleStringProperty ngayTao;
        private final javafx.beans.property.SimpleIntegerProperty soNVSuDung;

        public TaiKhoan(String tenTaiKhoan, String email, String trangThai, String ngayTao, int soNVSuDung) {
            this.tenTaiKhoan = new javafx.beans.property.SimpleStringProperty(tenTaiKhoan);
            this.email = new javafx.beans.property.SimpleStringProperty(email);
            this.trangThai = new javafx.beans.property.SimpleStringProperty(trangThai);
            this.ngayTao = new javafx.beans.property.SimpleStringProperty(ngayTao);
            this.soNVSuDung = new javafx.beans.property.SimpleIntegerProperty(soNVSuDung);
        }

        public String getTenTaiKhoan() { return tenTaiKhoan.get(); }
        public void setTenTaiKhoan(String value) { tenTaiKhoan.set(value); }
        public javafx.beans.property.StringProperty tenTaiKhoanProperty() { return tenTaiKhoan; }

        public String getEmail() { return email.get(); }
        public void setEmail(String value) { email.set(value); }
        public javafx.beans.property.StringProperty emailProperty() { return email; }

        public String getTrangThai() { return trangThai.get(); }
        public void setTrangThai(String value) { trangThai.set(value); }
        public javafx.beans.property.StringProperty trangThaiProperty() { return trangThai; }

        public String getNgayTao() { return ngayTao.get(); }
        public javafx.beans.property.StringProperty ngayTaoProperty() { return ngayTao; }

        public int getSoNVSuDung() { return soNVSuDung.get(); }
        public javafx.beans.property.IntegerProperty soNVSuDungProperty() { return soNVSuDung; }
    }
}
