package control;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import entity.ChuyenTau;
import entity.ToaTau;
import entity.DatabaseService;

public class ControllerDanhSachToaNgoi implements Initializable {

    //<editor-fold desc="Khai báo các thành phần FXML">
    @FXML private Button btChuyen1, btChuyen2, btChuyen3, btChuyen4, btChuyen5;
    @FXML private Label bt1_lbTenTau, bt1_lbThoiGianDi, bt1_lbThoiGianDen, bt1_lbSoLuongDatCho, bt1_lbSoLuongChoTrong;
    @FXML private Label bt2_lbTenTau, bt2_lbThoiGianDi, bt2_lbThoiGianDen, bt2_lbSoLuongDatCho, bt2_lbSoLuongChoTrong;
    @FXML private Label bt3_lbTenTau, bt3_lbThoiGianDi, bt3_lbThoiGianDen, bt3_lbSoLuongDatCho, bt3_lbSoLuongChoTrong;
    @FXML private Label bt4_lbTenTau, bt4_lbThoiGianDi, bt4_lbThoiGianDen, bt4_lbSoLuongDatCho, bt4_lbSoLuongChoTrong;
    @FXML private Label bt5_lbTenTau, bt5_lbThoiGianDi, bt5_lbThoiGianDen, bt5_lbSoLuongDatCho, bt5_lbSoLuongChoTrong;
    
    @FXML private TableView<?> tbGioVe;
    
    @FXML private Button btToaVT1, btToaVT2, btToaVT3, btToaVT4, btToaVT5, btToaVT6, btToaVT7, btToaVTDau;
    @FXML private Label lbTenToa;
    
    @FXML private Button btNgoiVT1, btNgoiVT2, btNgoiVT3, btNgoiVT4, btNgoiVT5, btNgoiVT6, btNgoiVT7, btNgoiVT8,
            btNgoiVT9, btNgoiVT10, btNgoiVT11, btNgoiVT12, btNgoiVT13, btNgoiVT14, btNgoiVT15, btNgoiVT16,
            btNgoiVT17, btNgoiVT18, btNgoiVT19, btNgoiVT20, btNgoiVT21, btNgoiVT22, btNgoiVT23, btNgoiVT24,
            btNgoiVT25, btNgoiVT26, btNgoiVT27, btNgoiVT28, btNgoiVT29, btNgoiVT30, btNgoiVT31, btNgoiVT32,
            btNgoiVT33, btNgoiVT34, btNgoiVT35, btNgoiVT36, btNgoiVT37, btNgoiVT38, btNgoiVT39, btNgoiVT40,
            btNgoiVT41, btNgoiVT42, btNgoiVT43, btNgoiVT44, btNgoiVT45, btNgoiVT46, btNgoiVT47, btNgoiVT48,
            btNgoiVT49, btNgoiVT50, btNgoiVT51, btNgoiVT52, btNgoiVT53, btNgoiVT54, btNgoiVT55, btNgoiVT56;
            
    @FXML private Button btTiepTuc;
    //</editor-fold>

    // --- Biến thành viên ---
    private ControllerChinh mainController;
    private final DatabaseService dbService = new DatabaseService();

    private List<Button> dsNutChuyen;
    private List<Button> dsNutToa;
    private List<Button> dsNutGhe;

    private List<ChuyenTau> danhSachChuyenTau; // Lưu danh sách chuyến tàu từ màn hình tìm kiếm
    private ChuyenTau chuyenTauDaChon;
    private List<ToaTau> danhSachToa;
    
    /**
     * Phương thức này được ControllerChinh gọi để truyền tham chiếu của nó qua.
     */
    public void setMainController(ControllerChinh mainController) {
        this.mainController = mainController;
    }

    /**
     * Phương thức khởi tạo, được FXMLLoader gọi sau khi tất cả các thành phần FXML đã được "tiêm".
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTripCards();
        setupCoachButtons();
        setupSeatMap();
    }

    /**
     * Nhận danh sách các chuyến tàu từ ControllerChinh và hiển thị chúng lên giao diện.
     * @param trips Danh sách các chuyến tàu tìm được.
     */
    public void displayTrips(List<ChuyenTau> trips) {
        this.danhSachChuyenTau = trips;
        
        // Tạo một mảng 2 chiều chứa các Label tương ứng với mỗi thẻ
        Label[][] tripLabels = {
            {bt1_lbTenTau, bt1_lbThoiGianDi, bt1_lbThoiGianDen, bt1_lbSoLuongDatCho, bt1_lbSoLuongChoTrong},
            {bt2_lbTenTau, bt2_lbThoiGianDi, bt2_lbThoiGianDen, bt2_lbSoLuongDatCho, bt2_lbSoLuongChoTrong},
            {bt3_lbTenTau, bt3_lbThoiGianDi, bt3_lbThoiGianDen, bt3_lbSoLuongDatCho, bt3_lbSoLuongChoTrong},
            {bt4_lbTenTau, bt4_lbThoiGianDi, bt4_lbThoiGianDen, bt4_lbSoLuongDatCho, bt4_lbSoLuongChoTrong},
            {bt5_lbTenTau, bt5_lbThoiGianDi, bt5_lbThoiGianDen, bt5_lbSoLuongDatCho, bt5_lbSoLuongChoTrong}
        };

        // Ẩn tất cả các thẻ trước khi hiển thị
        dsNutChuyen.forEach(btn -> btn.setVisible(false));
        
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Hiển thị và điền thông tin cho các thẻ tương ứng với kết quả
        for (int i = 0; i < trips.size(); i++) {
            if (i < dsNutChuyen.size()) {
                Button card = dsNutChuyen.get(i);
                ChuyenTau trip = trips.get(i);
                Label[] labels = tripLabels[i];

                labels[0].setText(trip.getTenTau());
                labels[1].setText(trip.getThoiGianDi().format(timeFormatter));
                labels[2].setText(trip.getThoiGianDen().format(timeFormatter));
                labels[3].setText(String.valueOf(trip.getSoLuongDatCho()));
                labels[4].setText(String.valueOf(trip.getSoLuongChoTrong()));
                
                card.setVisible(true);
            }
        }
        
        // Tự động chọn chuyến đầu tiên nếu có kết quả
        if (!trips.isEmpty()) {
            chonNutChuyen(dsNutChuyen.get(0));
        }
    }

    // --- Các phương thức thiết lập giao diện (Setup Methods) ---

    private void setupTripCards() {
        dsNutChuyen = Arrays.asList(btChuyen1, btChuyen2, btChuyen3, btChuyen4, btChuyen5);
        dsNutChuyen.forEach(btn -> btn.setOnAction(event -> chonNutChuyen(btn)));
    }

    private void setupCoachButtons() {
        dsNutToa = Arrays.asList(btToaVTDau, btToaVT1, btToaVT2, btToaVT3, btToaVT4, btToaVT5, btToaVT6, btToaVT7);
        dsNutToa.forEach(btn -> btn.setOnAction(event -> chonNutToa(btn)));
    }
    
    private void setupSeatMap() {
        dsNutGhe = Arrays.asList(
            btNgoiVT1, btNgoiVT2, btNgoiVT3, btNgoiVT4, btNgoiVT5, btNgoiVT6, btNgoiVT7, btNgoiVT8,
            btNgoiVT9, btNgoiVT10, btNgoiVT11, btNgoiVT12, btNgoiVT13, btNgoiVT14, btNgoiVT15, btNgoiVT16,
            btNgoiVT17, btNgoiVT18, btNgoiVT19, btNgoiVT20, btNgoiVT21, btNgoiVT22, btNgoiVT23, btNgoiVT24,
            btNgoiVT25, btNgoiVT26, btNgoiVT27, btNgoiVT28, btNgoiVT29, btNgoiVT30, btNgoiVT31, btNgoiVT32,
            btNgoiVT33, btNgoiVT34, btNgoiVT35, btNgoiVT36, btNgoiVT37, btNgoiVT38, btNgoiVT39, btNgoiVT40,
            btNgoiVT41, btNgoiVT42, btNgoiVT43, btNgoiVT44, btNgoiVT45, btNgoiVT46, btNgoiVT47, btNgoiVT48,
            btNgoiVT49, btNgoiVT50, btNgoiVT51, btNgoiVT52, btNgoiVT53, btNgoiVT54, btNgoiVT55, btNgoiVT56
        );
        dsNutGhe.forEach(btn -> btn.setOnAction(this::handleSeatClick));
    }

    // --- Các phương thức xử lý sự kiện (Event Handlers) ---

    private void chonNutChuyen(Button selectedButton) {
        // Cập nhật giao diện cho các thẻ chuyến
        dsNutChuyen.forEach(btn -> btn.getStyleClass().remove("trip-card-selected"));
        selectedButton.getStyleClass().add("trip-card-selected");

        // Lấy đối tượng ChuyenTau tương ứng
        int index = dsNutChuyen.indexOf(selectedButton);
        if (index != -1 && index < danhSachChuyenTau.size()) {
            this.chuyenTauDaChon = danhSachChuyenTau.get(index);
            
            // Tải danh sách toa cho chuyến tàu này
            this.danhSachToa = dbService.getCoachesForTrain(chuyenTauDaChon.getMaTau());
            
            // Cập nhật giao diện các nút chọn toa
            updateCoachButtonsView();
        }
    }

    private void updateCoachButtonsView() {
        dsNutToa.forEach(btn -> btn.setVisible(false)); // Ẩn tất cả
        for(int i = 0; i < danhSachToa.size(); i++) {
            if (i < dsNutToa.size()) { // Đảm bảo không vượt quá số nút có sẵn
                ToaTau toa = danhSachToa.get(i);
                Button nutToa = dsNutToa.get(i+1); // Bắt đầu từ btToaVT1 (index 1), bỏ qua nút Đầu
                nutToa.setText(toa.getTenToaTau().replace("Toa ", "")); // Chỉ hiện số
                nutToa.setVisible(true);
            }
        }
        btToaVTDau.setVisible(true); // Luôn hiện nút "Đầu"
        
        // Mặc định chọn toa đầu tiên
        if (!danhSachToa.isEmpty()) {
            chonNutToa(btToaVTDau);
        }
    }

    private void chonNutToa(Button selectedButton) {
        // Cập nhật giao diện cho các nút toa
        dsNutToa.forEach(btn -> btn.getStyleClass().remove("coach-button-selected"));
        selectedButton.getStyleClass().add("coach-button-selected");
        
        // Tải lại dữ liệu ghế cho toa tàu mới được chọn
        String tenToa = "Toa " + selectedButton.getText();
        if (selectedButton == btToaVTDau) {
            tenToa = danhSachToa.get(0).getTenToaTau();
        }
        lbTenToa.setText(tenToa.toUpperCase());
        
        String maToa = "";
        for (ToaTau toa : danhSachToa) {
            if (toa.getTenToaTau().equals(tenToa)) {
                maToa = toa.getMaToaTau();
                break;
            }
        }
        
        if (!maToa.isEmpty()) {
            boolean[] seatStatus = dbService.getSeatStatusForCoach(maToa);
            updateSeatView(seatStatus);
        }
    }

    private void handleSeatClick(ActionEvent event) {
        Button selectedSeat = (Button) event.getSource();
        if (selectedSeat.getStyleClass().contains("seat-available")) {
            selectedSeat.getStyleClass().setAll("seat-button", "seat-selected");
        } else if (selectedSeat.getStyleClass().contains("seat-selected")) {
            selectedSeat.getStyleClass().setAll("seat-button", "seat-available");
        }
    }
    
    private void updateSeatView(boolean[] seatStatus) {
        for (int i = 0; i < dsNutGhe.size(); i++) {
            Button seat = dsNutGhe.get(i);
            // Đảm bảo nút được kích hoạt và class được reset
            seat.setDisable(false);
            seat.getStyleClass().setAll("seat-button"); // Reset về class cơ bản
            
            if (i < seatStatus.length && seatStatus[i]) {
                seat.getStyleClass().add("seat-occupied");
                seat.setDisable(true);
            } else {
                seat.getStyleClass().add("seat-available");
            }
        }
    }
}