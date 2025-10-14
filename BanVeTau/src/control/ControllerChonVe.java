package control;

import dao.ChuyenTau_DAO; // THÊM DAO MỚI
import dao.GheNgoi_DAO;
import dao.ToaTau_DAO;
import dao.GaTau_DAO_mthanh;
import entity.ChuyenTau; // THÊM ENTITY MỚI
import entity.GaTau;
import entity.GheNgoi;
import entity.ToaTau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControllerChonVe {
	
	@FXML private HBox rootHBox;
    @FXML private HBox boxTau;
    @FXML private HBox boxToaIcons;
    @FXML private GridPane gridGhe;
    @FXML private Label lblChieuDi;
    @FXML private Label lblTenToa;
    @FXML private Label lblSelectedTrainInfo;
    @FXML private VBox rightSidebar;
    @FXML private ComboBox<GaTau> cmbGaDi;
    @FXML private ComboBox<GaTau> cmbGaDen;
    @FXML private RadioButton rbMotChieu;
    @FXML private RadioButton rbKhuHoi;
    @FXML private ToggleGroup radioChieuDi;
    @FXML private DatePicker dpNgayDi;
    @FXML private DatePicker dpNgayVe;
    @FXML private Button btnTimKiem;
    @FXML private CheckBox cbVipLounge;
 // Thay HBox boxTau bằng GridPane gridTau
    @FXML private GridPane gridTau;

    // THÊM DAO MỚI
    private final ChuyenTau_DAO chuyenTauDAO = new ChuyenTau_DAO(); 
    private final ToaTau_DAO toaDAO = new ToaTau_DAO();
    private final GheNgoi_DAO gheDAO = new GheNgoi_DAO();

    private final List<GheNgoi> danhSachGheDaChon = new ArrayList<>();
    private StackPane selectedTrainCard = null; // Card tàu giờ là StackPane
    private Node selectedToaNode = null;
    
    private String maChuyenTauDangChon = null;
    private List<ToaTau> danhSachToaCuaTau = new ArrayList<>();
    private int currentToaIndex = -1;
    
    private final GaTau_DAO_mthanh gaTauDAO = new GaTau_DAO_mthanh();

    @FXML
    public void initialize() throws SQLException {
        lblChieuDi.setText(String.format("CHIỀU ĐI: NGÀY %s TỪ SÀI GÒN ĐẾN HÀ NỘI", 
                                          LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        // THAY ĐỔI LỚN: Tải ChuyenTau thay vì Tau
        loadChuyenTau();
        List<GaTau> dsGa = gaTauDAO.getAllGaTau();
        cmbGaDi.getItems().addAll(dsGa);
        cmbGaDen.getItems().addAll(dsGa);
        
        // Thiết lập giá trị mặc định (ví dụ)
        cmbGaDi.getSelectionModel().selectFirst();
        cmbGaDen.getSelectionModel().selectLast();
        dpNgayDi.setValue(LocalDate.now());

        // Thêm listener để bật/tắt DatePicker ngày về
        radioChieuDi.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == rbKhuHoi) {
                dpNgayVe.setDisable(false);
            } else {
                dpNgayVe.setDisable(true);
                dpNgayVe.setValue(null);
            }
        });
        rightSidebar.prefWidthProperty().bind(rootHBox.widthProperty().divide(5));
    }

    // THAY ĐỔI LỚN: Phương thức này giờ làm việc với ChuyenTau
 // Trong file ControllerChonVe.java

    private void loadChuyenTau() throws SQLException {
        // 1. Lấy danh sách các chuyến tàu từ database
        List<ChuyenTau> listChuyenTau = chuyenTauDAO.getAllChuyenTau();
        
        // 2. Dọn dẹp các card tàu cũ khỏi GridPane
        gridTau.getChildren().clear();

        // 3. Dùng biến đếm để xác định vị trí cột sẽ thêm card tàu vào
        int columnIndex = 0; 
        
        // 4. Duyệt qua danh sách chuyến tàu và tạo card cho mỗi chuyến
        for (ChuyenTau chuyenTau : listChuyenTau) {
            // Gọi phương thức createTrainCard để tạo giao diện cho card
            StackPane trainCard = createTrainCard(chuyenTau);
            
            // Thêm card tàu vào GridPane tại cột `columnIndex` và hàng số 0
            gridTau.add(trainCard, columnIndex, 0); 
            
            // Tăng chỉ số cột để card tiếp theo được đặt vào cột kế bên
            columnIndex++; 
        }
        
        // 5. Tự động chọn (click) vào card tàu đầu tiên trong danh sách
        if (!listChuyenTau.isEmpty() && gridTau.getChildren().size() > 0) {
            gridTau.getChildren().get(0).fireEvent(new javafx.scene.input.MouseEvent(
                javafx.scene.input.MouseEvent.MOUSE_CLICKED, 
                0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1, 
                true, true, true, true, true, true, true, true, true, true, null));
        }
    }

    // VIẾT LẠI HOÀN TOÀN: Phương thức này tạo card tàu giống hệt mẫu thiết kế
 // Trong file ControllerChonVe.java

    private StackPane createTrainCard(ChuyenTau chuyenTau) throws SQLException {
        // 1. Tạo VBox chứa nội dung chữ (giống như cũ)
        VBox textContent = new VBox(5);
        textContent.setAlignment(Pos.CENTER);
        textContent.setPadding(new Insets(10, 0, 0, 0));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        Label lblMaTau = new Label(chuyenTau.getTau().getMaTau());
        lblMaTau.getStyleClass().add("card-train-code");
        Label lblThoiGianDi = new Label("TG đi:   " + chuyenTau.getNgayGioKhoiHanh().format(formatter));
        Label lblThoiGianDen = new Label("TG đến: " + chuyenTau.getNgayGioDen().format(formatter));
        Label lblSoLuong = new Label("SL chỗ đặt   SL chỗ trống");
        lblThoiGianDi.getStyleClass().add("card-train-time");
        lblThoiGianDen.getStyleClass().add("card-train-time");
        lblSoLuong.getStyleClass().add("card-train-time");

        int soGheDat = chuyenTauDAO.getSoLuongGheDaDat(chuyenTau.getMaChuyenTau());
        int tongSoGhe = chuyenTauDAO.getTongSoGhe(chuyenTau.getMaChuyenTau());
        int soGheTrong = tongSoGhe - soGheDat;

        Label lblChoDat = new Label(String.valueOf(soGheDat));
        lblChoDat.getStyleClass().add("card-train-seats-booked");
        Label lblChoTrong = new Label(String.valueOf(soGheTrong));
        lblChoTrong.getStyleClass().add("card-train-seats-available");
        
        HBox seatInfoBox = new HBox(30, lblChoDat, lblChoTrong);
        seatInfoBox.setAlignment(Pos.CENTER);
        textContent.getChildren().addAll(lblMaTau, lblThoiGianDi, lblThoiGianDen, lblSoLuong, seatInfoBox);

        // 2. Tạo StackPane đóng vai trò là card chính
        // KHÔNG CÒN ImageView cho thân tàu và bánh xe nữa
        StackPane finalCard = new StackPane(textContent); // Chỉ chứa phần chữ
        finalCard.setUserData(chuyenTau);
        
        // 3. Gán các style class cần thiết
        finalCard.getStyleClass().addAll("train-card", "train-card-gray"); // Mặc định là màu xám
        
        // 4. Cập nhật lại sự kiện click
        finalCard.setOnMouseClicked(e -> {
            if (selectedTrainCard != null) {
                // Xóa style của card cũ
                selectedTrainCard.getStyleClass().remove("train-card-selected");
                selectedTrainCard.getStyleClass().remove("train-card-blue");
                selectedTrainCard.getStyleClass().add("train-card-gray");
            }
            
            // Thêm style cho card mới được chọn
            finalCard.getStyleClass().add("train-card-selected");
            finalCard.getStyleClass().add("train-card-blue");
            finalCard.getStyleClass().remove("train-card-gray");
            
            selectedTrainCard = finalCard;

            // Các logic còn lại giữ nguyên
            maChuyenTauDangChon = chuyenTau.getMaChuyenTau();
            lblSelectedTrainInfo.setText(chuyenTau.getTau().getMaTau());
            loadToaIcons(chuyenTau.getTau().getMaTau());
        });
        
        return finalCard;
    }
    
    private void loadToaIcons(String maTau) {
        // Lấy danh sách các toa thuộc đoàn tàu từ database
        danhSachToaCuaTau = toaDAO.getToaByTau(maTau);
        
        // Dọn dẹp giao diện cũ
        boxToaIcons.getChildren().clear();
        gridGhe.getChildren().clear();
        danhSachGheDaChon.clear();
        lblTenToa.setText("Vui lòng chọn toa");

        // Sắp xếp danh sách toa theo thứ tự của nó trên đoàn tàu
        danhSachToaCuaTau.sort((t1, t2) -> Integer.compare(t1.getThuTuToa(), t2.getThuTuToa()));

        for (ToaTau toa : danhSachToaCuaTau) {
            // Tạo icon và label cho mỗi toa
            ImageView toaIcon = new ImageView(new Image(getClass().getResourceAsStream("/image/toa.png")));
            toaIcon.setFitWidth(30);
            toaIcon.setFitHeight(30);
            
            VBox toaContainer = new VBox(2);
            toaContainer.setUserData(toa); // Gán đối tượng ToaTau vào VBox để lấy lại khi click
            toaContainer.setAlignment(Pos.CENTER);
            toaContainer.getChildren().addAll(toaIcon, new Label(String.valueOf(toa.getThuTuToa())));
            toaContainer.getStyleClass().add("toa-icon-container");

            // Gán sự kiện click cho mỗi toa
            toaContainer.setOnMouseClicked(e -> {
                // Chỉ cần gọi phương thức chung
                updateToaSelection(toaContainer); 
            });
            boxToaIcons.getChildren().add(toaContainer);
        }
        
        // Tự động chọn toa đầu tiên trong danh sách
        if (!danhSachToaCuaTau.isEmpty()) {
            currentToaIndex = 0;
            selectToaByIndex(currentToaIndex);
        }
    }

    /**
     * Tải và vẽ sơ đồ ghế ngồi cho một toa tàu cụ thể.
     * @param maToa Mã của toa tàu cần hiển thị ghế
     */
    private void loadGhe(String maToa) {
        gridGhe.getChildren().clear();
        danhSachGheDaChon.clear();
        List<GheNgoi> listGhe = gheDAO.getGheByToa(maToa);

        // --- PHẦN VẼ HEADER CHO SƠ ĐỒ GHẾ ---
        // Header cho các khoang (ví dụ từ 1 đến 7)
        for (int i = 1; i <= 7; i++) {
            Label lblKhoang = new Label("Khoang " + i);
            lblKhoang.getStyleClass().add("grid-header");
            gridGhe.add(lblKhoang, i, 0); // Hàng 0, các cột từ 1 đến 7
        }
        // Header cho các tầng (ví dụ T3, T2, T1)
        gridGhe.add(new Label("T3"), 0, 1); 
        gridGhe.add(new Label("T2"), 0, 2);
        gridGhe.add(new Label("T1"), 0, 3);
        // Thêm style cho các header tầng
        for (Node node : gridGhe.getChildren()) {
            if (GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) > 0) {
                node.getStyleClass().add("grid-header");
            }
        }
        
        // --- PHẦN VẼ VÀ SẮP XẾP CÁC GHẾ ---
        for (GheNgoi ghe : listGhe) {
            Button btnGhe = new Button(String.valueOf(ghe.getViTriGhe()));
            btnGhe.setUserData(ghe); // Lưu đối tượng GheNgoi vào nút
            
            // TODO: Thêm logic kiểm tra trạng thái ghế (đã bán, đang bảo trì...) từ database
            // if (ghe.getTrangThai().equals("DaBan")) {
            //    btnGhe.getStyleClass().add("seat-unavailable");
            //    btnGhe.setDisable(true);
            // } else {
                btnGhe.getStyleClass().add("seat-available");
                btnGhe.setOnAction(e -> {
                    GheNgoi clickedGhe = (GheNgoi) ((Button) e.getSource()).getUserData();
                    // Logic chọn/bỏ chọn ghế
                    if (danhSachGheDaChon.contains(clickedGhe)) {
                        danhSachGheDaChon.remove(clickedGhe);
                        btnGhe.getStyleClass().remove("seat-selected");
                        btnGhe.getStyleClass().add("seat-available");
                    } else {
                        danhSachGheDaChon.add(clickedGhe);
                        btnGhe.getStyleClass().remove("seat-available");
                        btnGhe.getStyleClass().add("seat-selected");
                    }
                });
            // }

            // =======================================================================================
            // LƯU Ý QUAN TRỌNG: LOGIC SẮP XẾP GHẾ VÀO GRIDPANE
            // Logic dưới đây là một VÍ DỤ dựa trên giao diện mẫu (toa giường nằm 42 chỗ).
            // Bạn CẦN ĐIỀU CHỈNH lại cho phù hợp với cách đánh số ghế và loại toa của bạn.
            // Ví dụ: mỗi khoang có 6 ghế, được đánh số từ 1 đến 42.
            int viTriGhe = ghe.getViTriGhe();
            int khoang = ((viTriGhe - 1) / 6) + 1; // Khoang thứ mấy (1-7)
            int viTriTrongKhoang = (viTriGhe - 1) % 6; // Vị trí trong khoang (0-5)
            
            // Xác định hàng (tầng) và cột dựa trên vị trí trong khoang
            int row = 0; // Tương ứng với T3, T2, T1 (hàng 1, 2, 3 trong grid)
            int col = 0; // Tương ứng với cột trong grid
            
            // Ghế 1,2 -> T1 ; 3,4 -> T2 ; 5,6 -> T3
            // Ghế số lẻ bên trái, số chẵn bên phải
            if (viTriTrongKhoang < 2) { // Tầng 1
                row = 3; 
            } else if (viTriTrongKhoang < 4) { // Tầng 2
                row = 2;
            } else { // Tầng 3
                row = 1;
            }
            
            col = khoang; // Ghế thuộc khoang nào thì nằm ở cột đó

            gridGhe.add(btnGhe, col, row);
            // =======================================================================================
        }
    }
    
    /**
     * Xử lý sự kiện cho nút chuyển sang toa kế trước.
     */
    @FXML
    void previousToa(ActionEvent event) {
        if (currentToaIndex > 0) {
            currentToaIndex--;
            selectToaByIndex(currentToaIndex);
        }
    }

    /**
     * Xử lý sự kiện cho nút chuyển sang toa kế sau.
     */
    @FXML
    void nextToa(ActionEvent event) {
        if (currentToaIndex < danhSachToaCuaTau.size() - 1) {
            currentToaIndex++;
            selectToaByIndex(currentToaIndex);
        }
    }

    /**
     * Tự động chọn một toa trong danh sách dựa vào chỉ số (index).
     * @param index Chỉ số của toa trong danh sách `danhSachToaCuaTau`
     */
    private void selectToaByIndex(int index) {
        if (index >= 0 && index < danhSachToaCuaTau.size()) {
            Node toaNode = boxToaIcons.getChildren().get(index);
            // Gọi trực tiếp phương thức chung thay vì mô phỏng click
            updateToaSelection(toaNode); 
        }
    }

    /**
     * Xử lý sự kiện cho nút "Xác nhận chọn vé".
     */
    @FXML
    void xacNhanChonVe(ActionEvent event) {
        if (danhSachGheDaChon.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn ít nhất một ghế để tiếp tục.");
            alert.showAndWait();
        } else {
            System.out.println("Xác nhận chọn vé thành công! Các ghế đã chọn:");
            for (GheNgoi ghe : danhSachGheDaChon) {
                System.out.println(" - Mã ghế: " + ghe.getMaGheNgoi() + ", Vị trí: " + ghe.getViTriGhe());
            }
            // TODO: Thêm logic chuyển sang màn hình tiếp theo hoặc lưu dữ liệu vào hóa đơn
        }
    }

    /**
     * Xử lý sự kiện cho nút "Hủy / Chọn lại".
     */
    @FXML
    void huyChonVe(ActionEvent event) {
        danhSachGheDaChon.clear();
        // Bỏ chọn tất cả ghế trên sơ đồ
        for (Node node : gridGhe.getChildren()) {
            if (node instanceof Button && node.getStyleClass().contains("seat-selected")) {
                node.getStyleClass().remove("seat-selected");
                node.getStyleClass().add("seat-available");
            }
        }
        System.out.println("Đã hủy tất cả các ghế đã chọn.");
    }
    private void updateToaSelection(Node toaNode) {
        // Bỏ chọn style của toa cũ
        if (selectedToaNode != null) {
            selectedToaNode.getStyleClass().remove("toa-icon-selected");
        }
        // Thêm style cho toa mới được chọn
        toaNode.getStyleClass().add("toa-icon-selected");
        selectedToaNode = toaNode;

        // Lấy thông tin toa và cập nhật giao diện
        ToaTau selectedToa = (ToaTau) toaNode.getUserData();
        currentToaIndex = danhSachToaCuaTau.indexOf(selectedToa);
        lblTenToa.setText(String.format("Toa số %d: %s", selectedToa.getThuTuToa(), selectedToa.getTenToaTau()));
        
        // Tải sơ đồ ghế của toa
        loadGhe(selectedToa.getMaToaTau());
    }
    @FXML
    void timKiemChuyenTau(ActionEvent event) {
        GaTau gaDi = cmbGaDi.getValue();
        GaTau gaDen = cmbGaDen.getValue();
        LocalDate ngayDi = dpNgayDi.getValue();
        
        System.out.println("Bắt đầu tìm kiếm chuyến tàu:");
        System.out.println("Ga đi: " + gaDi);
        System.out.println("Ga đến: " + gaDen);
        System.out.println("Ngày đi: " + ngayDi);
        
        // TODO: Gọi đến phương thức loadChuyenTau với các tham số tìm kiếm
        // loadChuyenTau(gaDi.getMaGaTau(), gaDen.getMaGaTau(), ngayDi);
    }
}