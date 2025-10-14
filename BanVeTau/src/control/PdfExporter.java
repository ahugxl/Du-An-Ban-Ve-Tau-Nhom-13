package control;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import entity.Ve;

/**
 * Xuất PDF cho 1 vé tàu.
 * Yêu cầu: PDFBox 3.x (pdfbox, fontbox, pdfbox-io) có trên Modulepath/ClassPath.
 * Font Unicode: đặt NotoSans-Regular.ttf trong resources/fonts/.
 */
public class PdfExporter {

    // Font Unicode trên classpath
    private static final String FONT_PATH = "/fonts/NotoSans-Regular.ttf";

    public static void exportVeToPdf(Ve ve, Window owner) {
        if (ve == null) {
            new Alert(Alert.AlertType.WARNING, "Vui lòng chọn một vé trước khi in.").showAndWait();
            return;
        }

        FileChooser fc = new FileChooser();
        fc.setTitle("Lưu vé tàu dạng PDF");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fc.setInitialFileName("Ve_" + safe(ve.getMaVe()) + ".pdf");
        File file = fc.showSaveDialog(owner);
        if (file == null) return;

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            // Font Unicode (để hiển thị TV)
            PDType0Font font;
            try (InputStream is = Objects.requireNonNull(
                    PdfExporter.class.getResourceAsStream(FONT_PATH),
                    "Không tìm thấy font: " + FONT_PATH)) {
                font = PDType0Font.load(doc, is, true);
            }

            float margin = 50f;
            float top = page.getMediaBox().getHeight() - margin;

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {

                // ===== Tiêu đề =====
                cs.beginText();
                cs.setFont(font, 20);
                cs.newLineAtOffset(margin, top);
                cs.showText("VÉ TÀU");
                cs.endText();

                // ===== Gạch ngang =====
                cs.setLineWidth(1f);
                cs.moveTo(margin, top - 15);
                cs.lineTo(page.getMediaBox().getWidth() - margin, top - 15);
                cs.stroke();

                // ===== Khối thông tin (dùng leading + newLine) =====
                cs.beginText();
                cs.setFont(font, 12);
                cs.setLeading(18);                 // khoảng cách dòng
                cs.newLineAtOffset(margin, top - 40); // vị trí dòng đầu

                cs.showText("Mã vé: " + safe(ve.getMaVe()));                    cs.newLine();
                cs.showText("Tên vé: " + safe(ve.getTenVe()));                   cs.newLine();
                cs.showText("Mã chuyến: " + safe(ve.getChuyen()));               cs.newLine();
                cs.showText("Ghế: " + safe(ve.getGhe()));                        cs.newLine();
                cs.showText("Ngày in: " + safe(ve.getNgayInVeStr()));            cs.newLine();
                cs.showText("Ga đi: " + safe(ve.getTenGaDi()));                  cs.newLine();
                cs.showText("Ga đến: " + safe(ve.getTenGaDen()));                cs.newLine();
                cs.showText("Loại hành trình: " + safe(ve.getLoaiHanhTrinhStr())); cs.newLine();
                cs.showText("Loại vé: " + safe(ve.getLoaiVeStr()));              cs.newLine();
                cs.showText("Tình trạng vé: " + safe(ve.getTrangThaiVeStr()));   cs.newLine();
                cs.showText("Phòng chờ VIP: " + safe(ve.getCoPhongChoVipStr())); cs.newLine();
                cs.showText("Khách hàng: " + safe(ve.getTenKhachHang()));        cs.newLine();

                cs.endText();
            }

            doc.save(file);
            new Alert(Alert.AlertType.INFORMATION, "Xuất PDF thành công:\n" + file.getAbsolutePath()).showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Xuất PDF thất bại: " + ex.getMessage()).showAndWait();
        }
    }

    private static String safe(String s) {
        return (s == null) ? "" : s;
    }

}
