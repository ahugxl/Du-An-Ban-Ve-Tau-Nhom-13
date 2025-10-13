package control;

import dao.ChuyenTau_DAO; // THÊM DAO MỚI
import dao.GheNgoi_DAO;
import dao.ToaTau_DAO;
import entity.ChuyenTau; // THÊM ENTITY MỚI
import entity.GheNgoi;
import entity.ToaTau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerChonVe {

    @FXML private HBox boxTau;
    @FXML private HBox boxToaIcons;
    @FXML private GridPane gridGhe;
    @FXML private Label lblChieuDi;
    @FXML private Label lblTenToa;
    @FXML private Label lblSelectedTrainInfo;

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

    @FXML
    public void initialize() throws SQLException {
        lblChieuDi.setText(String.format("CHIỀU ĐI: NGÀY %s TỪ SÀI GÒN ĐẾN HÀ NỘI", 
                                          LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        // THAY ĐỔI LỚN: Tải ChuyenTau thay vì Tau
        loadChuyenTau();
    }

    // THAY ĐỔI LỚN: Phương thức này giờ làm việc với ChuyenTau
    private void loadChuyenTau() throws SQLException {
        // Giả sử bạn có phương thức getChuyenTauByCriteria() trong DAO
        List<ChuyenTau> listChuyenTau = chuyenTauDAO.getAllChuyenTau(); // Hoặc phương thức phù hợp
        boxTau.getChildren().clear();

        for (ChuyenTau chuyenTau : listChuyenTau) {
            StackPane trainCard = createTrainCard(chuyenTau);
            trainCard.setOnMouseClicked(e -> {
                if (selectedTrainCard != null) {
                    selectedTrainCard.getStyleClass().remove("train-card-selected");
                }
                trainCard.getStyleClass().add("train-card-selected");
                selectedTrainCard = trainCard;

                maChuyenTauDangChon = chuyenTau.getMaChuyenTau();
                lblSelectedTrainInfo.setText(chuyenTau.getTau().getMaTau());
                loadToaIcons(chuyenTau.getTau().getMaTau());
            });
            boxTau.getChildren().add(trainCard);
        }
        
        if (!listChuyenTau.isEmpty()) {
            boxTau.getChildren().get(0).fireEvent(new javafx.scene.input.MouseEvent(javafx.scene.input.MouseEvent.MOUSE_CLICKED, 
                0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1, 
                true, true, true, true, true, true, true, true, true, true, null));
        }
    }

    // VIẾT LẠI HOÀN TOÀN: Phương thức này tạo card tàu giống hệt mẫu thiết kế
    private StackPane createTrainCard(ChuyenTau chuyenTau) throws SQLException {
        // 1. Tạo phần thân tàu (ảnh nền)
        ImageView trainBody = new ImageView(new Image(getClass().getResourceAsStream("/image/tau.png")));
        trainBody.setFitHeight(130);
        trainBody.setFitWidth(180);

        // 2. Tạo phần bánh xe
        ImageView trainWheels = new ImageView(new Image(getClass().getResourceAsStream("/image/tau.png")));
        trainWheels.setFitHeight(30);
        trainWheels.setFitWidth(180);

        // 3. Tạo VBox chứa thân tàu và bánh xe
        VBox trainImageContainer = new VBox(trainBody, trainWheels);
        trainImageContainer.setAlignment(Pos.CENTER);

        // 4. Tạo VBox chứa nội dung chữ
        VBox textContent = new VBox(5);
        textContent.setAlignment(Pos.CENTER);
        textContent.setPadding(new Insets(10, 0, 0, 0));

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");

        Label lblMaTau = new Label(chuyenTau.getTau().getMaTau());
        lblMaTau.getStyleClass().add("card-train-code");

     // Tạo một formatter duy nhất cho cả ngày và giờ
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM HH:mm");

        // Lấy đối tượng LocalDateTime và gọi phương thức .format() của nó
        Label lblThoiGianDi = new Label("TG đi   " + chuyenTau.getNgayGioKhoiHanh().format(formatter));
        Label lblThoiGianDen = new Label("TG đến " + chuyenTau.getNgayGioDen().format(formatter));

        // Logic lấy số ghế (bạn cần viết các phương thức này trong DAO)
        int soGheDat = chuyenTauDAO.getSoLuongGheDaDat(chuyenTau.getMaChuyenTau());
        int tongSoGhe = chuyenTauDAO.getTongSoGhe(chuyenTau.getMaChuyenTau());
        int soGheTrong = tongSoGhe - soGheDat;

        Label lblChoDat = new Label(String.valueOf(soGheDat));
        lblChoDat.getStyleClass().add("card-train-seats-booked");
        Label lblChoTrong = new Label(String.valueOf(soGheTrong));
        lblChoTrong.getStyleClass().add("card-train-seats-available");
        
        HBox seatInfoBox = new HBox(30, lblChoDat, lblChoTrong);
        seatInfoBox.setAlignment(Pos.CENTER);

        textContent.getChildren().addAll(lblMaTau, lblThoiGianDi, lblThoiGianDen, seatInfoBox);

        // 5. Dùng StackPane để chồng hình ảnh và chữ lên nhau
        StackPane finalCard = new StackPane(trainImageContainer, textContent);
        finalCard.getStyleClass().add("train-card");
        finalCard.setUserData(chuyenTau); // Lưu lại đối tượng ChuyenTau
        
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
                // Bỏ chọn style của toa cũ (nếu có)
                if(selectedToaNode != null) {
                    selectedToaNode.getStyleClass().remove("toa-icon-selected");
                }
                // Thêm style cho toa vừa được click
                toaContainer.getStyleClass().add("toa-icon-selected");
                selectedToaNode = toaContainer;

                // Lấy thông tin toa và cập nhật giao diện
                ToaTau selectedToa = (ToaTau) toaContainer.getUserData();
                currentToaIndex = danhSachToaCuaTau.indexOf(selectedToa);
                lblTenToa.setText(String.format("Toa số %d: %s", selectedToa.getThuTuToa(), selectedToa.getTenToaTau()));
                
                // Tải sơ đồ ghế của toa vừa chọn
                loadGhe(selectedToa.getMaToaTau());
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
            // Kích hoạt sự kiện click để cập nhật toàn bộ giao diện
            toaNode.fireEvent(new javafx.scene.input.MouseEvent(javafx.scene.input.MouseEvent.MOUSE_CLICKED, 
                0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1, 
                true, true, true, true, true, true, true, true, true, true, null));
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
    // ...
}