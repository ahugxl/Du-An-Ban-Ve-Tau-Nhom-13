package control;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import entity.KhachHang;
import dao.KhachHang_DAO;

public class Controller_DanhMuc_KhachHang implements Initializable {

    // Form Fields
    @FXML private TextField txtTim;
    @FXML private TextField maKhachHangField;
    @FXML private TextField hoTenField;
    @FXML private TextField soGiayToField;
    @FXML private DatePicker ngaySinhPicker;
    @FXML private TextField soDienThoaiField;
    @FXML private RadioButton namRadio;
    @FXML private RadioButton nuRadio;
    @FXML private ToggleGroup gioiTinhGroup;
    
    // Buttons
    @FXML private Button btnThem;
    @FXML private Button btnSua;
    @FXML private Button btnXoaThongTin;
    
    // Table and Columns
    @FXML private TableView<KhachHang> tbKhachHang;
    @FXML private TableColumn<KhachHang, String> colMaKhachHang;
    @FXML private TableColumn<KhachHang, String> colHoTen;
    @FXML private TableColumn<KhachHang, String> colSoGiayTo;
    @FXML private TableColumn<KhachHang, LocalDate> colNgaySinh;
    @FXML private TableColumn<KhachHang, String> colSoDienThoai;
    @FXML private TableColumn<KhachHang, String> colGioiTinh;
    @FXML private TableColumn<KhachHang, Void> colThaoTac;
    
    private ObservableList<KhachHang> ds = FXCollections.observableArrayList();
    private ObservableList<KhachHang> dsLoc = FXCollections.observableArrayList();
    private KhachHang_DAO khachHang_DAO= new KhachHang_DAO();
    
    private KhachHang selectedCustomer = null;
    private int customerCounter = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load sample data
        loadSampleData();
        
        // Setup search functionality
        setupSearch();
        
        // Tự động tạo mã khách hàng
        maKhachHangField.setEditable(false);
        maKhachHangField.setStyle("-fx-background-color: #f0f0f0;");
    }
    public void loadDataTable() {
    	ds=FXCollections.observableArrayList(khachHang_DAO.getAllKhachHang());
    	dsLoc.setAll(ds);
    	colMaKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaKhachHang()));
    	colHoTen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoTenKhachHang()));
    	col
    }
    
    private void setupActionsColumn() {
        Callback<TableColumn<KhachHang, Void>, TableCell<KhachHang, Void>> cellFactory = 
            new Callback<>() {
                @Override
                public TableCell<KhachHang, Void> call(final TableColumn<KhachHang, Void> param) {
                    final TableCell<KhachHang, Void> cell = new TableCell<>() {
                        
                        private final Button editBtn = new Button("Sửa");
                        private final Button deleteBtn = new Button("Xóa");
                        
                        {
                            editBtn.getStyleClass().add("edit-button");
                            deleteBtn.getStyleClass().add("delete-button");
                            
                            editBtn.setOnAction(event -> {
                                KhachHang customer = getTableView().getItems().get(getIndex());
                                handleEditCustomer(customer);
                            });
                            
                            deleteBtn.setOnAction(event -> {
                                KhachHang customer = getTableView().getItems().get(getIndex());
                                handleDeleteCustomer(customer);
                            });
                        }
                        
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                HBox hbox = new HBox(10, editBtn, deleteBtn);
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
    
    private void loadSampleData() {
        ds.addAll(
            new KhachHang("KH001", "Nguyễn Văn A", "001234567890", LocalDate.of(1990, 5, 15), "0901234567", true),
            new KhachHang("KH002", "Trần Thị B", "001234567891", LocalDate.of(1992, 8, 20), "0912345678", false),
            new KhachHang("KH003", "Lê Hoàng C", "001234567892", LocalDate.of(1988, 3, 10), "0923456789", true),
            new KhachHang("KH004", "Phạm Thị D", "001234567893", LocalDate.of(1995, 11, 25), "0934567890", false),
            new KhachHang("KH005", "Hoàng Văn E", "001234567894", LocalDate.of(1993, 7, 8), "0945678901", true)
        );
        
        customerCounter = ds.size() + 1;
        dsLoc.addAll(ds);
    }
    
    private void setupSearch() {
        txtTim.textProperty().addListener((observable, oldValue, newValue) -> {
            filterCustomers(newValue);
        });
    }
    
    @FXML
    private void tim() {
        filterCustomers(txtTim.getText());
    }
    
    private void filterCustomers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            dsLoc.setAll(ds);
        } else {
            String lowerKeyword = keyword.toLowerCase().trim();
            dsLoc.setAll(
                ds.stream()
                    .filter(kh -> 
                        kh.getMaKhachHang().toLowerCase().contains(lowerKeyword) ||
                        kh.getHoTenKhachHang().toLowerCase().contains(lowerKeyword) ||
                        kh.getSoGiayTo().toLowerCase().contains(lowerKeyword) ||
                        kh.getSoDienThoai().contains(lowerKeyword)
                    )
                    .toList()
            );
        }
    }
    
    /**
     * TỰ ĐỘNG TẠO MÃ KHÁCH HÀNG
     */
    private String generateCustomerId() {
        return String.format("KH%03d", customerCounter++);
    }
    
    @FXML
    private void them() {
        if (!validateInput()) {
            return;
        }
        
        String maKH = generateCustomerId();
        
        KhachHang newCustomer = new KhachHang(
            maKH,
            hoTenField.getText().trim(),
            soGiayToField.getText().trim(),
            ngaySinhPicker.getValue(),
            soDienThoaiField.getText().trim(),
            namRadio.isSelected()
        );
        
        try {
            // TODO: Gọi DAO để INSERT vào database
            // khachHangDAO.insert(newCustomer);
            
            ds.add(newCustomer);
            dsLoc.add(newCustomer);
            
            showAlert(Alert.AlertType.INFORMATION, "Thành công", 
                     "Thêm khách hàng thành công!\nMã KH: " + maKH);
            xoaThongTin();
            
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", 
                     "Không thể thêm khách hàng: " + e.getMessage());
        }
    }
    
    @FXML
    private void sua() {
        if (selectedCustomer == null || !validateInput()) {
            return;
        }
        
        selectedCustomer.setHoTenKhachHang(hoTenField.getText().trim());
        selectedCustomer.setSoGiayTo(soGiayToField.getText().trim());
        selectedCustomer.setNgaySinh(ngaySinhPicker.getValue());
        selectedCustomer.setSoDienThoai(soDienThoaiField.getText().trim());
        selectedCustomer.setGioiTinh(namRadio.isSelected());
        
        try {
            // TODO: Gọi DAO để UPDATE database
            // khachHangDAO.update(selectedCustomer);
            
            tbKhachHang.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật khách hàng thành công!");
            xoaThongTin();
            
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", 
                     "Không thể cập nhật: " + e.getMessage());
        }
    }
    
    private void handleEditCustomer(KhachHang customer) {
        selectedCustomer = customer;
        
        maKhachHangField.setText(customer.getMaKhachHang());
        hoTenField.setText(customer.getHoTenKhachHang());
        soGiayToField.setText(customer.getSoGiayTo());
        ngaySinhPicker.setValue(customer.getNgaySinh());
        soDienThoaiField.setText(customer.getSoDienThoai());
        
        if (customer.isGioiTinh()) {
            namRadio.setSelected(true);
        } else {
            nuRadio.setSelected(true);
        }
        
        btnThem.setVisible(false);
        btnThem.setManaged(false);
        btnSua.setVisible(true);
        btnSua.setManaged(true);
    }
    
    private void handleDeleteCustomer(KhachHang customer) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận xóa");
        confirmAlert.setHeaderText("Bạn có chắc chắn muốn xóa khách hàng này?");
        confirmAlert.setContentText(
            "Mã KH: " + customer.getMaKhachHang() + "\n" +
            "Họ tên: " + customer.getHoTenKhachHang()
        );
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // TODO: Gọi DAO để DELETE
                // khachHangDAO.delete(customer.getMaKhachHang());
                
                ds.remove(customer);
                dsLoc.remove(customer);
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa khách hàng thành công!");
                
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", 
                         "Không thể xóa: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void xoaThongTin() {
        maKhachHangField.clear();
        hoTenField.clear();
        soGiayToField.clear();
        ngaySinhPicker.setValue(null);
        soDienThoaiField.clear();
        namRadio.setSelected(true);
        
        selectedCustomer = null;
        btnThem.setVisible(true);
        btnThem.setManaged(true);
        btnSua.setVisible(false);
        btnSua.setManaged(false);
    }
    
    private boolean validateInput() {
        // Validate họ tên
        if (hoTenField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập họ tên khách hàng!");
            hoTenField.requestFocus();
            return false;
        }
        
        // Validate họ tên (chỉ chữ cái và khoảng trắng)
        if (!Pattern.matches("^[a-zA-ZÀ-ỹ\\s]+$", hoTenField.getText().trim())) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Họ tên chỉ được chứa chữ cái!");
            hoTenField.requestFocus();
            return false;
        }
        
        // Validate số giấy tờ
        if (soGiayToField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập số giấy tờ!");
            soGiayToField.requestFocus();
            return false;
        }
        
        // Validate số giấy tờ (9 hoặc 12 số)
        String soGiayTo = soGiayToField.getText().trim();
        if (!Pattern.matches("^[0-9]{9}$|^[0-9]{12}$", soGiayTo)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Số giấy tờ phải là 9 hoặc 12 chữ số!");
            soGiayToField.requestFocus();
            return false;
        }
        
        // Kiểm tra trùng số giấy tờ
        if (selectedCustomer == null || !soGiayTo.equals(selectedCustomer.getSoGiayTo())) {
            boolean isDuplicate = ds.stream()
                .anyMatch(kh -> kh.getSoGiayTo().equals(soGiayTo));
            
            if (isDuplicate) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Số giấy tờ đã tồn tại!");
                soGiayToField.requestFocus();
                return false;
            }
        }
        
        // Validate ngày sinh
        if (ngaySinhPicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn ngày sinh!");
            ngaySinhPicker.requestFocus();
            return false;
        }
        
        // Kiểm tra tuổi (phải >= 1 tuổi)
        LocalDate ngaySinh = ngaySinhPicker.getValue();
        if (ngaySinh.isAfter(LocalDate.now().minusYears(1))) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Khách hàng phải từ 1 tuổi trở lên!");
            ngaySinhPicker.requestFocus();
            return false;
        }
        
        // Validate số điện thoại
        if (soDienThoaiField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập số điện thoại!");
            soDienThoaiField.requestFocus();
            return false;
        }
        
        // Validate số điện thoại (10 số, bắt đầu bằng 0)
        String sdt = soDienThoaiField.getText().trim();
        if (!Pattern.matches("^0[0-9]{9}$", sdt)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Số điện thoại phải có 10 số và bắt đầu bằng 0!");
            soDienThoaiField.requestFocus();
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