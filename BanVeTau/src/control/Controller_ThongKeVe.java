package control;

import java.net.URL;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import dao.ThongKeVe_DAO;
import dao.ThongKeVe_DAO.TyLeLapDayDTO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller_ThongKeVe implements Initializable {

    @FXML private ComboBox<Integer> cmbNam;
    @FXML private ComboBox<String> cmbTuyenDuongFilter;
    @FXML private BarChart<String, Number> chartTuyenDuong;
    @FXML private NumberAxis yAxisTuyenDuong;
    @FXML private PieChart chartLoaiToa;
    
    @FXML private TableView<TyLeLapDayDTO> tblTyLeLapDay;
    @FXML private TableColumn<TyLeLapDayDTO, String> colMaChuyenTau;
    @FXML private TableColumn<TyLeLapDayDTO, String> colTenChuyenTau;
    @FXML private TableColumn<TyLeLapDayDTO, Integer> colSoVeBan;
    @FXML private TableColumn<TyLeLapDayDTO, Integer> colTongSoGhe;
    @FXML private TableColumn<TyLeLapDayDTO, Double> colTyLe;
    
    private ThongKeVe_DAO thongKeDAO;
    private ControllerChinh mainController;
    
    public void setMainController(ControllerChinh mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thongKeDAO = new ThongKeVe_DAO();
        
        populateYearComboBox();
        setupOccupancyTableColumns();
        
        cmbTuyenDuongFilter.getItems().addAll("Theo Doanh thu", "Theo Số lượng vé");
        cmbTuyenDuongFilter.getSelectionModel().selectFirst();
        
        cmbNam.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadAllTabsData(newVal);
            }
        });
        
        cmbTuyenDuongFilter.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (cmbNam.getValue() != null) {
                loadDataForTuyenDuong(cmbNam.getValue());
            }
        });

        cmbNam.setValue(Year.now().getValue());
    }
    
    private void loadAllTabsData(int nam) {
        loadDataForTuyenDuong(nam);
        loadDataForLoaiToa(nam);
        loadDataForTyLeLapDay(nam);
    }

    private void loadDataForTuyenDuong(int nam) {
        chartTuyenDuong.getData().clear();
        String filter = cmbTuyenDuongFilter.getValue();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        
        if (filter.equals("Theo Doanh thu")) {
            yAxisTuyenDuong.setLabel("Doanh thu (VND)");
            series.setName("Doanh thu");
            Map<String, Double> data = thongKeDAO.getDoanhThuTheoTuyenDuong(nam);
            data.forEach((tuyen, doanhThu) -> series.getData().add(new XYChart.Data<>(tuyen, doanhThu)));
        } else { // Theo Số lượng vé
            yAxisTuyenDuong.setLabel("Số lượng vé");
            series.setName("Số lượng vé");
            Map<String, Integer> data = thongKeDAO.getSoLuongVeTheoTuyenDuong(nam);
            data.forEach((tuyen, soLuong) -> series.getData().add(new XYChart.Data<>(tuyen, soLuong)));
        }
        chartTuyenDuong.getData().add(series);
    }

    /**
     * ✅ VIẾT LẠI PHƯƠNG THỨC NÀY CHO ĐÚNG
     * Tải dữ liệu cho biểu đồ tròn (Tab 2)
     */
    private void loadDataForLoaiToa(int nam) {
        // 1. Gọi DAO để lấy dữ liệu
        Map<String, Integer> data = thongKeDAO.getSoLuongVeTheoLoaiToa(nam);
        
        // 2. Tạo danh sách dữ liệu cho biểu đồ tròn
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        data.forEach((loaiToa, soLuong) -> {
            pieChartData.add(new PieChart.Data(loaiToa + " (" + soLuong + ")", soLuong));
        });
        
        // 3. Gán dữ liệu cho biểu đồ
        chartLoaiToa.setData(pieChartData);
    }
   
    	private void loadDataForTyLeLapDay(int nam) {
    	    System.out.println("--- Đang tải dữ liệu cho Tỷ Lệ Lấp Đầy, năm: " + nam);
    	    List<TyLeLapDayDTO> data = thongKeDAO.getTyLeLapDay(nam);
    	    System.out.println(">>> DAO getTyLeLapDay đã trả về: " + data.size() + " dòng.");
    	    
    	    // Kiểm tra xem TableView có bị null không
    	    if (tblTyLeLapDay == null) {
    	        System.err.println("!!! LỖI: tblTyLeLapDay là NULL! Hãy kiểm tra fx:id trong FXML!");
    	    } else {
    	        tblTyLeLapDay.setItems(FXCollections.observableArrayList(data));
    	        System.out.println(">>> Đã cập nhật dữ liệu cho bảng Tỷ Lệ Lấp Đầy.");
    	    }
    	}
    

    	private void setupOccupancyTableColumns() {
    	    // Sử dụng biểu thức lambda để chỉ định chính xác cách lấy dữ liệu
    	    // Cách này hoàn toàn tương thích với record
    	    colMaChuyenTau.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().maChuyenTau()));
    	    colTenChuyenTau.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tenChuyenTau()));
    	    
    	    // Đối với kiểu số Integer, cần thêm .asObject()
    	    colSoVeBan.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().soVeDaBan()).asObject());
    	    colTongSoGhe.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().tongSoGhe()).asObject());
    	    
    	    // Cột ProgressBar cần tính toán nên vẫn dùng lambda
    	    colTyLe.setCellValueFactory(cellData -> {
    	        // Lấy đối tượng record từ dòng hiện tại
    	        var dto = cellData.getValue();
    	        
    	        // Tính toán tỷ lệ
    	        double tyLe = (dto.tongSoGhe() > 0) ? (double) dto.soVeDaBan() / dto.tongSoGhe() : 0;
    	        
    	        // Trả về một đối tượng Property mà TableView có thể "quan sát"
    	        return new SimpleDoubleProperty(tyLe).asObject();
    	    });
    	    
    	    // Phần setCellFactory của ProgressBar không thay đổi, giữ nguyên như cũ
    	    colTyLe.setCellFactory(column -> {
    	        return new TableCell<TyLeLapDayDTO, Double>() {
    	            private final ProgressBar progressBar = new ProgressBar();
    	            {
    	                progressBar.setMaxWidth(Double.MAX_VALUE);
    	            }
    	            @Override
    	            protected void updateItem(Double item, boolean empty) {
    	                super.updateItem(item, empty);
    	                if (empty || item == null) {
    	                    setGraphic(null);
    	                    setText(null);
    	                } else {
    	                    progressBar.setProgress(item);
    	                    if (item < 0.3) { // Dưới 30%
    	                        progressBar.setStyle("-fx-accent: #E53935;"); // Màu đỏ
    	                    } else if (item < 0.7) { // Từ 30% đến 70%
    	                        progressBar.setStyle("-fx-accent: #FDD835;"); // Màu vàng
    	                    } else { // Trên 70%
    	                        progressBar.setStyle("-fx-accent: #4CAF50;"); // Màu xanh lá
    	                    }

    	                    setGraphic(progressBar);
    	                    setText(String.format(" %.1f%%", item * 100));
    	                }
    	            }
    	        };
    	    });
    	}
    
    
    private void populateYearComboBox() {
        int currentYear = Year.now().getValue();
        for (int i = currentYear; i >= currentYear - 5; i--) {
            cmbNam.getItems().add(i);
        }
    }
}