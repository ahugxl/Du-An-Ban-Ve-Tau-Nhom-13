package control;

import entity.Ve;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class ControllerInLaiVe extends ControllerTimVe{
	@FXML @Override
    public void initialize() {
        super.initialize();
        // thêm bindSelection riêng nếu muốn
    }
    @FXML
    protected  void inLaiVe() {
        Ve veDangChon = tblTimVe.getSelectionModel().getSelectedItem();
        if (veDangChon == null) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("In vé");
            a.setHeaderText("Chưa chọn vé");
            a.setContentText("Vui lòng chọn một vé trong bảng trước khi in.");
            a.showAndWait();
            return;
        }

        try {
            PdfExporter.exportVeToPdf(veDangChon, btnInLaiVe.getScene().getWindow());

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("In vé");
            a.setHeaderText("Xuất PDF thành công");
            a.setContentText("Vé đã được xuất ra PDF.");
            a.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("In vé");
            a.setHeaderText("Xuất PDF thất bại");
            a.setContentText("Lý do: " + ex.getMessage());
            a.showAndWait();
        }
    }
}
