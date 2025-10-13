package control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Controller_HeThong_DoiMatKhau implements Initializable {

    // Labels - Info
    @FXML private Label lblTenTaiKhoan;
    @FXML private Label lblEmail;
    
    // Password Fields
    @FXML private PasswordField oldPasswordField;
    @FXML private TextField oldPasswordVisible;
    @FXML private PasswordField newPasswordField;
    @FXML private TextField newPasswordVisible;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField confirmPasswordVisible;
    
    // Buttons
    @FXML private Button btnShowOld;
    @FXML private Button btnShowNew;
    @FXML private Button btnShowConfirm;
    
    // Labels - Feedback
    @FXML private Label lblPasswordStrength;
    @FXML private Label lblPasswordMatch;
    @FXML private Label lblMessage;
    
    private String currentUsername;
    private String currentEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind visible và invisible password fields
        setupPasswordFieldBinding();
        
        // Setup real-time validation
        setupRealTimeValidation();
    }
    
    /**
     * Set thông tin tài khoản từ controller khác
     */
    public void setAccountInfo(String username, String email) {
        this.currentUsername = username;
        this.currentEmail = email;
        lblTenTaiKhoan.setText(username);
        lblEmail.setText(email);
    }
    
    /**
     * Bind password field với text field để show/hide
     */
    private void setupPasswordFieldBinding() {
        // Old password
        oldPasswordField.textProperty().bindBidirectional(oldPasswordVisible.textProperty());
        
        // New password
        newPasswordField.textProperty().bindBidirectional(newPasswordVisible.textProperty());
        
        // Confirm password
        confirmPasswordField.textProperty().bindBidirectional(confirmPasswordVisible.textProperty());
    }
    
    /**
     * Setup validation theo thời gian thực
     */
    private void setupRealTimeValidation() {
        // Kiểm tra độ mạnh mật khẩu
        newPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            checkPasswordStrength(newVal);
            checkPasswordMatch();
        });
        
        // Kiểm tra mật khẩu khớp
        confirmPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            checkPasswordMatch();
        });
    }
    
    /**
     * Toggle hiển thị mật khẩu cũ
     */
    @FXML
    private void toggleOldPasswordVisibility() {
        if (oldPasswordField.isVisible()) {
            oldPasswordField.setVisible(false);
            oldPasswordField.setManaged(false);
            oldPasswordVisible.setVisible(true);
            oldPasswordVisible.setManaged(true);
            btnShowOld.setText("🙈");
        } else {
            oldPasswordField.setVisible(true);
            oldPasswordField.setManaged(true);
            oldPasswordVisible.setVisible(false);
            oldPasswordVisible.setManaged(false);
            btnShowOld.setText("👁");
        }
    }
    
    /**
     * Toggle hiển thị mật khẩu mới
     */
    @FXML
    private void toggleNewPasswordVisibility() {
        if (newPasswordField.isVisible()) {
            newPasswordField.setVisible(false);
            newPasswordField.setManaged(false);
            newPasswordVisible.setVisible(true);
            newPasswordVisible.setManaged(true);
            btnShowNew.setText("🙈");
        } else {
            newPasswordField.setVisible(true);
            newPasswordField.setManaged(true);
            newPasswordVisible.setVisible(false);
            newPasswordVisible.setManaged(false);
            btnShowNew.setText("👁");
        }
    }
    
    /**
     * Toggle hiển thị xác nhận mật khẩu
     */
    @FXML
    private void toggleConfirmPasswordVisibility() {
        if (confirmPasswordField.isVisible()) {
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
            confirmPasswordVisible.setVisible(true);
            confirmPasswordVisible.setManaged(true);
            btnShowConfirm.setText("🙈");
        } else {
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            confirmPasswordVisible.setVisible(false);
            confirmPasswordVisible.setManaged(false);
            btnShowConfirm.setText("👁");
        }
    }
    
    /**
     * Kiểm tra độ mạnh mật khẩu
     */
    private void checkPasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            lblPasswordStrength.setText("");
            return;
        }
        
        int strength = 0;
        
        // Kiểm tra độ dài
        if (password.length() >= 6) strength++;
        if (password.length() >= 8) strength++;
        
        // Kiểm tra chữ hoa
        if (Pattern.compile("[A-Z]").matcher(password).find()) strength++;
        
        // Kiểm tra chữ thường
        if (Pattern.compile("[a-z]").matcher(password).find()) strength++;
        
        // Kiểm tra số
        if (Pattern.compile("[0-9]").matcher(password).find()) strength++;
        
        // Kiểm tra ký tự đặc biệt
        if (Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) strength++;
        
        // Hiển thị kết quả
        lblPasswordStrength.getStyleClass().clear();
        if (strength <= 2) {
            lblPasswordStrength.setText("Độ mạnh: Yếu ⚠️");
            lblPasswordStrength.getStyleClass().add("password-weak");
        } else if (strength <= 4) {
            lblPasswordStrength.setText("Độ mạnh: Trung bình 🔶");
            lblPasswordStrength.getStyleClass().add("password-medium");
        } else {
            lblPasswordStrength.setText("Độ mạnh: Mạnh ✓");
            lblPasswordStrength.getStyleClass().add("password-strong");
        }
    }
    
    /**
     * Kiểm tra mật khẩu khớp
     */
    private void checkPasswordMatch() {
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();
        
        if (confirmPass.isEmpty()) {
            lblPasswordMatch.setText("");
            return;
        }
        
        lblPasswordMatch.getStyleClass().clear();
        if (newPass.equals(confirmPass)) {
            lblPasswordMatch.setText("✓ Mật khẩu khớp");
            lblPasswordMatch.getStyleClass().add("match-success");
        } else {
            lblPasswordMatch.setText("✗ Mật khẩu không khớp");
            lblPasswordMatch.getStyleClass().add("match-error");
        }
    }
    
    /**
     * Validate form trước khi submit
     */
    private boolean validateForm() {
        String oldPass = oldPasswordField.getText().trim();
        String newPass = newPasswordField.getText().trim();
        String confirmPass = confirmPasswordField.getText().trim();
        
        // Kiểm tra rỗng
        if (oldPass.isEmpty()) {
            showMessage("Vui lòng nhập mật khẩu hiện tại!", false);
            return false;
        }
        
        if (newPass.isEmpty()) {
            showMessage("Vui lòng nhập mật khẩu mới!", false);
            return false;
        }
        
        if (confirmPass.isEmpty()) {
            showMessage("Vui lòng xác nhận mật khẩu mới!", false);
            return false;
        }
        
        // Kiểm tra độ dài tối thiểu
        if (newPass.length() < 6) {
            showMessage("Mật khẩu mới phải có ít nhất 6 ký tự!", false);
            return false;
        }
        
        // Kiểm tra mật khẩu khớp
        if (!newPass.equals(confirmPass)) {
            showMessage("Mật khẩu mới và xác nhận không khớp!", false);
            return false;
        }
        
        // Kiểm tra mật khẩu mới khác mật khẩu cũ
        if (oldPass.equals(newPass)) {
            showMessage("Mật khẩu mới phải khác mật khẩu hiện tại!", false);
            return false;
        }
        
        return true;
    }
    
    /**
     * Xử lý đổi mật khẩu
     */
    @FXML
    private void handleChangePassword() {
        if (!validateForm()) {
            return;
        }
        
        String oldPass = oldPasswordField.getText().trim();
        String newPass = newPasswordField.getText().trim();
        
        try {
            // TODO: Gọi DAO để kiểm tra mật khẩu cũ và cập nhật mật khẩu mới
            // TaiKhoanDAO dao = new TaiKhoanDAO();
            // boolean isOldPasswordCorrect = dao.checkPassword(currentUsername, oldPass);
            
            // SIMULATION: Giả lập kiểm tra mật khẩu cũ
            boolean isOldPasswordCorrect = true; // Thay bằng dao.checkPassword()
            
            if (!isOldPasswordCorrect) {
                showMessage("Mật khẩu hiện tại không đúng!", false);
                return;
            }
            
            // TODO: Cập nhật mật khẩu mới
            // dao.updatePassword(currentUsername, newPass);
            
            System.out.println("=== ĐỔI MẬT KHẨU THÀNH CÔNG ===");
            System.out.println("Tài khoản: " + currentUsername);
            System.out.println("Mật khẩu mới: " + newPass);
            
            showMessage("Đổi mật khẩu thành công! ✓", true);
            
            // Clear form sau 2 giây
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(this::handleCancel);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            
        } catch (Exception e) {
            showMessage("Lỗi: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }
    
    /**
     * Hiển thị thông báo
     */
    private void showMessage(String message, boolean isSuccess) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().clear();
        lblMessage.getStyleClass().add("message-label");
        
        if (isSuccess) {
            lblMessage.getStyleClass().add("message-success");
        } else {
            lblMessage.getStyleClass().add("message-error");
        }
        
        lblMessage.setVisible(true);
        lblMessage.setManaged(true);
    }
    
    /**
     * Hủy và đóng cửa sổ
     */
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) lblTenTaiKhoan.getScene().getWindow();
        stage.close();
    }
}