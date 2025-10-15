package control;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import dao.Ve_DAO;
import entity.KhachHang;
import entity.Ve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerTimVe extends ControllerTimVeBase {


    @FXML
    private DatePicker dateNgayBanVe;
	@FXML @Override
    public void initialize() {
        super.initialize();   // BẮT BUỘC gọi super để chạy logic chung
        // thêm xử lý riêng nếu có (ví dụ set tooltip, format thêm...)
    }


    @FXML
    protected  void timVeTheoNgay() {
    	try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	kh_dao = new KhachHang_DAO();
    	ArrayList<Ve> dsVe = ve_dao.getalltbVe();
		LocalDate ngay = dateNgayBanVe.getValue();
		ObservableList<Ve> data = FXCollections.observableArrayList();
		if(ngay==null) {
			hienTatCaVe();
			showError("Vui lòng chọn ngày để tìm kiếm!");
			return;
		}
		else{
			ArrayList<KhachHang> dsKH = null;
			
			dsKH = kh_dao.getAllKhachHang();
			
			for(Ve ve : dsVe) {
				if(ve.getNgayInVe().toLocalDate().equals(ngay)) {
					data.add(ve);
					
				}
			}
			if(data.isEmpty()) {
				hienTatCaVe();
				showError("Không tìm thấy vé với ngày đã chọn: " + ngay);
				return;
			}
			else {
				tblTimVe.setItems(data);
				return;
			}
			
		}
		
	}


}
