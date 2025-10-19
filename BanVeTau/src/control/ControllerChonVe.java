package control;

import dao.ChuyenTau_DAO; // THÊM DAO MỚI
import dao.GheNgoi_DAO_mthanh;
import dao.ToaTau_DAO;
import dao.GaTau_DAO_mthanh;
import entity.ChuyenTau; // THÊM ENTITY MỚI
import entity.GaTau;
import entity.GheNgoi_mthanh;
import entity.KhachHang;
import entity.LoaiVe;
import entity.ToaTau;
import entity.Ve;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
    @FXML private RadioButton rbTapThe;
    @FXML private ToggleGroup radioBookingType;
    @FXML private HBox groupBookingBox;
    @FXML private Spinner<Integer> spinnerSoLuongVe;
    @FXML private Label lblNgayVe;
    @FXML private DatePicker dpNgayDi;
    @FXML private DatePicker dpNgayVe;
    @FXML private Button btnTimKiem;
    @FXML private CheckBox cbVipLounge;
 // Thay HBox boxTau bằng GridPane gridTau
    @FXML private GridPane gridTau;
    @FXML private VBox leftContentPanel;
    @FXML private VBox boxGioVe;
    @FXML private Label lblTongGioVe;
    // THÊM DAO MỚI
    private final ChuyenTau_DAO chuyenTauDAO = new ChuyenTau_DAO(); 
    private final ToaTau_DAO toaDAO = new ToaTau_DAO();
    private final GheNgoi_DAO_mthanh gheDAO = new GheNgoi_DAO_mthanh();
    private final List<Ve> danhSachVeTrongGio = new ArrayList<>();
    private final HashMap<GheNgoi_mthanh, Ve> veTrongGioMap = new HashMap<>();
    private final List<GheNgoi_mthanh> danhSachGheDaChon = new ArrayList<>();
    private StackPane selectedTrainCard = null; // Card tàu giờ là StackPane
    private Node selectedToaNode = null;
    
    private String maChuyenTauDangChon = null;
    private List<ToaTau> danhSachToaCuaTau = new ArrayList<>();
    private int currentToaIndex = -1;
    
    private final GaTau_DAO_mthanh gaTauDAO = new GaTau_DAO_mthanh();
    private final DecimalFormat df = new DecimalFormat("#,### VNĐ");
    public void initData(GaTau gaDi, GaTau gaDen, LocalDate ngayDi) {
        // Cập nhật lại các control trên giao diện nếu cần
        cmbGaDi.setValue(gaDi);
        cmbGaDen.setValue(gaDen);
        dpNgayDi.setValue(ngayDi);
        
        // Cập nhật lại tiêu đề
        lblChieuDi.setText(String.format("CHIỀU ĐI: NGÀY %s TỪ %s ĐẾN %s", 
                                          ngayDi.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                          gaDi.getTenGaTau().toUpperCase(), 
                                          gaDen.getTenGaTau().toUpperCase()));
        
        // TODO: Gọi lại phương thức loadChuyenTau với các tham số tìm kiếm mới
        // loadChuyenTau(gaDi, gaDen, ngayDi);
    }
    
    @FXML
    public void initialize() throws SQLException {
        // 1. Ẩn phần nội dung bên trái khi khởi động
        leftContentPanel.setVisible(false);
        leftContentPanel.setManaged(true); // SỬA LỖI: Phải là 'false' để không chiếm không gian

        // 2. Cấu hình form tìm kiếm bên phải
        // Load dữ liệu cho ComboBox
        List<GaTau> dsGa = gaTauDAO.getAllGaTau();
        cmbGaDi.getItems().addAll(dsGa);
        cmbGaDen.getItems().addAll(dsGa);
        cmbGaDi.getSelectionModel().selectFirst();
        cmbGaDen.getSelectionModel().selectLast();
        dpNgayDi.setValue(LocalDate.now());

        // 3. CẬP NHẬT: Logic ẩn/hiện cho 3 lựa chọn
        // Mặc định ẩn các phần không cần thiết cho "Một chiều"
        lblNgayVe.setVisible(false);
        dpNgayVe.setVisible(false);
        groupBookingBox.setVisible(false);
        groupBookingBox.setManaged(false);

        // Thêm listener mới cho ToggleGroup
        radioBookingType.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            // Luôn ẩn tất cả các control đặc biệt trước
            lblNgayVe.setVisible(false);
            dpNgayVe.setVisible(false);
            groupBookingBox.setVisible(false);
            groupBookingBox.setManaged(false);

            if (newToggle == rbKhuHoi) {
                // Nếu chọn "Khứ hồi", hiện "Ngày về"
                lblNgayVe.setVisible(true);
                dpNgayVe.setVisible(true);
            } 
            else if (newToggle == rbTapThe) {
                // Nếu chọn "Tập thể", hiện "Số lượng vé"
                groupBookingBox.setVisible(true);
                groupBookingBox.setManaged(true);
            }
            // Nếu là "Một chiều" (mặc định), không cần làm gì cả vì đã ẩn hết
        });
        
        // 4. Binding chiều rộng cho sidebar (giữ nguyên)
        rightSidebar.prefWidthProperty().bind(rootHBox.widthProperty().divide(4)); // Giữ 1/3
    }

    // THAY ĐỔI LỚN: Phương thức này giờ làm việc với ChuyenTau
 // Trong file ControllerChonVe.java

    private void loadChuyenTau(GaTau gaDi, GaTau gaDen, LocalDate ngayDi) throws SQLException {
        List<ChuyenTau> listChuyenTau = chuyenTauDAO.findChuyenTau(gaDi, gaDen, ngayDi); 
        
        lblChieuDi.setText(String.format("CHIỀU ĐI: NGÀY %s TỪ %s ĐẾN %s", 
                                          ngayDi.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                          gaDi.getTenGaTau().toUpperCase(), 
                                          gaDen.getTenGaTau().toUpperCase()));

        // SỬA Ở ĐÂY: Dọn dẹp GridPane thay vì HBox
        gridTau.getChildren().clear(); 

        int columnIndex = 0;
        for (ChuyenTau chuyenTau : listChuyenTau) {
            StackPane trainCard = createTrainCard(chuyenTau);
            
            // SỬA Ở ĐÂY: Thêm card vào GridPane
            gridTau.add(trainCard, columnIndex, 0); 
            
            columnIndex++;
        }
        
        if (!listChuyenTau.isEmpty() && gridTau.getChildren().size() > 0) {
            // Tự động click vào card đầu tiên
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
            try {
				loadToaIcons(chuyenTau.getTau().getMaTau());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        
        return finalCard;
    }
    
    private void loadToaIcons(String maTau) throws SQLException {
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
                try {
					updateToaSelection(toaContainer);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
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
 // Bên trong lớp ControllerChonVe.java

    /**
     * Tải và vẽ sơ đồ ghế ngồi cho một toa tàu cụ thể, đồng thời gán sự kiện
     * để cập nhật giỏ vé khi người dùng chọn hoặc bỏ chọn ghế.
     * @param maToa Mã của toa tàu cần hiển thị ghế.
     * @throws SQLException 
     */
    private void loadGhe(String maToa) throws SQLException {
        // 1. Dọn dẹp sơ đồ ghế cũ
        gridGhe.getChildren().clear();
        
        // 2. Lấy danh sách ghế của toa tàu từ database
        List<GheNgoi_mthanh> listGhe = gheDAO.getGheByToa(maToa);

        // 3. Vẽ các header cho sơ đồ (Khoang 1, T1, T2...)
        // Header cho các khoang
        for (int i = 1; i <= 7; i++) { // Giả định có 7 khoang
            Label lblKhoang = new Label("Khoang " + i);
            lblKhoang.getStyleClass().add("grid-header");
            gridGhe.add(lblKhoang, i, 0);
        }
        // Header cho các tầng
        gridGhe.add(new Label("T3"), 0, 1); 
        gridGhe.add(new Label("T2"), 0, 2);
        gridGhe.add(new Label("T1"), 0, 3);
        for (Node node : gridGhe.getChildren()) {
            if (GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) > 0) {
                node.getStyleClass().add("grid-header");
            }
        }
        
        // 4. Duyệt qua danh sách ghế để tạo nút và gán sự kiện
        for (GheNgoi_mthanh ghe : listGhe) {
            Button btnGhe = new Button(String.valueOf(ghe.getViTriGhe()));
            btnGhe.setUserData(ghe); // Lưu đối tượng GheNgoi vào nút để dùng sau

            // TODO: Thêm logic kiểm tra trạng thái ghế (đã bán) từ database
            // Ví dụ: if (ghe.getTrangThai().equals("DaBan")) { ... }

            // Kiểm tra xem ghế này đã được chọn từ trước (ở toa khác) hay chưa
            if (danhSachGheDaChon.contains(ghe)) {
                btnGhe.getStyleClass().add("seat-selected");
            } else {
                btnGhe.getStyleClass().add("seat-available");
            }

            // Gán sự kiện chính: Chọn/Bỏ chọn ghế
            btnGhe.setOnAction(e -> {
                GheNgoi_mthanh clickedGhe = (GheNgoi_mthanh) ((Button) e.getSource()).getUserData();
                
                if (danhSachGheDaChon.contains(clickedGhe)) {
                    // --- LOGIC BỎ CHỌN GHẾ ---
                    danhSachGheDaChon.remove(clickedGhe);
                    btnGhe.getStyleClass().setAll("seat-available"); // Reset style
                    xoaVeKhoiGio(clickedGhe); // Gọi hàm xóa vé khỏi giỏ hàng
                } else {
                    // --- LOGIC CHỌN GHẾ ---
                    danhSachGheDaChon.add(clickedGhe);
                    btnGhe.getStyleClass().setAll("seat-selected"); // Reset style
                    themVeVaoGio(clickedGhe); // Gọi hàm thêm vé vào giỏ hàng
                }
            });

            // 5. Logic sắp xếp ghế vào GridPane (bạn cần tùy chỉnh cho phù hợp)
            int viTriGhe = ghe.getViTriGhe();
            int khoang = ((viTriGhe - 1) / 6) + 1;
            int viTriTrongKhoang = (viTriGhe - 1) % 6;
            
            int row = 0;
            if (viTriTrongKhoang < 2) row = 3;      // Tầng 1
            else if (viTriTrongKhoang < 4) row = 2; // Tầng 2
            else row = 1;                          // Tầng 3
            
            gridGhe.add(btnGhe, khoang, row);
        }
    }
    
    /**
     * Xử lý sự kiện cho nút chuyển sang toa kế trước.
     * @throws SQLException 
     */
    @FXML
    void previousToa(ActionEvent event) throws SQLException {
        if (currentToaIndex > 0) {
            currentToaIndex--;
            selectToaByIndex(currentToaIndex);
        }
    }

    /**
     * Xử lý sự kiện cho nút chuyển sang toa kế sau.
     * @throws SQLException 
     */
    @FXML
    void nextToa(ActionEvent event) throws SQLException {
        if (currentToaIndex < danhSachToaCuaTau.size() - 1) {
            currentToaIndex++;
            selectToaByIndex(currentToaIndex);
        }
    }

    /**
     * Tự động chọn một toa trong danh sách dựa vào chỉ số (index).
     * @param index Chỉ số của toa trong danh sách `danhSachToaCuaTau`
     * @throws SQLException 
     */
    private void selectToaByIndex(int index) throws SQLException {
        if (index >= 0 && index < danhSachToaCuaTau.size()) {
            Node toaNode = boxToaIcons.getChildren().get(index);
            // Gọi trực tiếp phương thức chung thay vì mô phỏng click
            updateToaSelection(toaNode); 
        }
    }


    /**
     * Xử lý sự kiện cho nút "Hủy / Chọn lại".
     * @throws SQLException 
     */
    private void updateToaSelection(Node toaNode) throws SQLException {
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
    void timKiemChuyenTau(ActionEvent event) throws SQLException {
        // 1. Lấy thông tin tìm kiếm từ form
        GaTau gaDi = cmbGaDi.getValue();
        GaTau gaDen = cmbGaDen.getValue();
        LocalDate ngayDi = dpNgayDi.getValue();
        
        if (gaDi == null || gaDen == null || ngayDi == null) {
            // (Thêm cảnh báo cho người dùng)
            return;
        }

        // 2. Hiển thị lại phần nội dung bên trái
        leftContentPanel.setVisible(true);
        leftContentPanel.setManaged(true);

        // 3. Gọi hàm tải dữ liệu với các tham số tìm kiếm
        loadChuyenTau(gaDi, gaDen, ngayDi);
    }
 // Thêm import này ở đầu file Controller
    // ...

    /**
     * Tạo giao diện cho một vé trong giỏ và thêm vào VBox.
     * PHIÊN BẢN MỚI: Tự động tính giá vé thực tế.
     */
    private void themVeVaoGio(GheNgoi_mthanh ghe) {
        Ve ve = new Ve();
        ve.setChuyenTau((ChuyenTau) selectedTrainCard.getUserData());
        ve.setGheNgoi(ghe);
        ve.setGaDi(cmbGaDi.getValue());
        ve.setGaDen(cmbGaDen.getValue());
        
        danhSachVeTrongGio.add(ve);
        veTrongGioMap.put(ghe, ve);
        
        VBox cartItem = new VBox(5);
        cartItem.getStyleClass().add("cart-item");
        cartItem.setUserData(ve);

        HBox topContent = new HBox();
        topContent.setAlignment(Pos.CENTER_LEFT);
        
        VBox itemDetails = new VBox();
        itemDetails.getStyleClass().add("cart-item-details");
        
        Label line1 = new Label(ve.getChuyenTau().getTau().getMaTau() + " " + ve.getChuyenTau().getTuyenDuong().getTenTuyenDuong());
        Label line2 = new Label(ve.getChuyenTau().getNgayGioKhoiHanh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        Label line3 = new Label(String.format("%s toa %d chỗ %d", ghe.getToaTau().getTenToaTau(), ghe.getToaTau().getThuTuToa(), ghe.getViTriGhe()));
        itemDetails.getChildren().addAll(line1, line2, line3);
        
        Label priceLabel = new Label();
        priceLabel.getStyleClass().add("cart-item-price");
        
        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/image/iconThungRac.png")));
        deleteIcon.setFitWidth(24);
        deleteIcon.setFitHeight(24);
        deleteIcon.getStyleClass().add("delete-icon");
        deleteIcon.setOnMouseClicked(e -> {
            for (Node node : gridGhe.getChildren()) {
                if (node instanceof Button && ghe.equals(node.getUserData())) {
                    ((Button) node).fire();
                    break;
                }
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topContent.getChildren().addAll(itemDetails, spacer, priceLabel, deleteIcon);

        CheckBox cbVip = new CheckBox("Phòng chờ VIP (+20,000 VNĐ)");
        cbVip.getStyleClass().add("vip-checkbox");
        cbVip.selectedProperty().addListener((obs, oldVal, newVal) -> {
            ve.setCoPhongChoVip(newVal);
            updateTongGioVe();
        });
        
        cartItem.getChildren().addAll(topContent, cbVip);
        boxGioVe.getChildren().add(cartItem);
        
        updateTongGioVe();
    }

    /**
     * Xóa một vé khỏi giỏ vé.
     */
    private void xoaVeKhoiGio(GheNgoi_mthanh ghe) {
        Ve veToRemove = veTrongGioMap.get(ghe);
        if (veToRemove != null) {
            danhSachVeTrongGio.remove(veToRemove);
            
            Node nodeToRemove = null;
            for (Node node : boxGioVe.getChildren()) {
                if (veToRemove.equals(node.getUserData())) {
                    nodeToRemove = node;
                    break;
                }
            }
            if (nodeToRemove != null) {
                boxGioVe.getChildren().remove(nodeToRemove);
            }
            
            veTrongGioMap.remove(ghe);
            updateTongGioVe();
        }
    }

    /**
     * Tính toán và cập nhật lại tổng tiền của toàn bộ giỏ vé.
     */
    private void updateTongGioVe() {
        double total = 0;
        
        for (Node node : boxGioVe.getChildren()) {
            Ve ve = (Ve) node.getUserData();
            double giaVe = ve.getGiaVeThucTe();
            total += giaVe;
            
            HBox topContent = (HBox) ((VBox) node).getChildren().get(0);
            Label priceLabel = (Label) topContent.getChildren().get(2);
            priceLabel.setText(df.format(giaVe));
        }
        
        lblTongGioVe.setText(df.format(total));
    }

    /**
     * Cập nhật lại nút Hủy: xóa cả ghế đã chọn và giỏ vé.
     */
    @FXML
    void huyChonVe(ActionEvent event) {
        // Tạo một bản sao của danh sách để tránh lỗi ConcurrentModificationException
        List<GheNgoi_mthanh> gheCanBoChon = new ArrayList<>(danhSachGheDaChon);

        for (GheNgoi_mthanh ghe : gheCanBoChon) {
             for (Node node : gridGhe.getChildren()) {
                if (node instanceof Button && ghe.equals(node.getUserData())) {
                    ((Button) node).fire(); // Kích hoạt sự kiện bỏ chọn
                    break;
                }
            }
        }
    }

    @FXML
    void xacNhanChonVe(ActionEvent event) throws SQLException {
        if (danhSachGheDaChon.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn ít nhất một ghế để tiếp tục.");
            alert.showAndWait();
            return;
        }
        
        try {
            // 1. Lấy scene hiện tại từ một control bất kỳ, ví dụ nút "Xác nhận" đã được nhấn
            Node source = (Node) event.getSource();
            Scene scene = source.getScene();

            // 2. Tải file FXML của giao diện thanh toán
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GiaoDienThanhToan.fxml"));
            Parent thanhToanRoot = loader.load();

            // 3. Lấy controller của màn hình ThanhToan và truyền dữ liệu qua
            ControllerThanhToan thanhToanController = loader.getController();
            ChuyenTau ct = (ChuyenTau) selectedTrainCard.getUserData();
            thanhToanController.initData(danhSachGheDaChon, ct, cmbGaDi.getValue(), cmbGaDen.getValue());

            // 4. THAY THẾ nội dung của scene hiện tại bằng giao diện mới
            scene.setRoot(thanhToanRoot);

        } catch (IOException e) {
            System.err.println("Lỗi khi chuyển sang giao diện thanh toán:");
            e.printStackTrace();
        }
    }
}