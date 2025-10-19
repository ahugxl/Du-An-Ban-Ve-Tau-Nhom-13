package util;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entity.NhanVien;
import entity.TaiKhoan;

import javax.activation.DataHandler;
import javax.activation.DataSource;
public class Email {
	//NguyenThinh@123
	//ktqc wpjb shfo aaho
	final String fromEmail = "thinhtestemail@gmail.com";
	final String password = "ktqcwpjbshfoaaho";
	public boolean guiEmail(NhanVien nv, String matKhauMoi) {
		//Properties
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP HOST
				props.put("mail.smtp.port", "587"); //TLS Port 587 SSL Port 465
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				
				// ctreate Authenticator
				Authenticator auth = new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						// TODO Auto-generated method stub
						return new PasswordAuthentication(fromEmail, password);
					}
					
				};
				// Phien lam viec
				Session session = Session.getInstance(props, auth);
				
				// Gui email
				String toEmail = nv.getTaiKhoan().getEmail();
				// Tao mot tin nhan moi
				MimeMessage msg = new MimeMessage(session);
				try {
					// Kieu noi dung
					msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
					// Nguoi gui
					msg.setFrom(fromEmail);
					// Nguoi nhan
					msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
					// Tieu de
					msg.setSubject("Đây là mật khẩu mới của bạn", "UTF-8");
					// Noi dung
					String html = """
							<!DOCTYPE html>
							<html lang="vi">
							<head><meta charset="UTF-8"></head>
							<body style="font-family:Arial,sans-serif;line-height:1.5">
							  <h1>Đây là mật khẩu mới của bạn</h1>
							  <p><strong>Mật khẩu tạm thời:</strong> %s</p>
							  <p>Vui lòng <strong>đổi mật khẩu ngay lập tức</strong> để đảm bảo an toàn tài khoản.</p>
							  <img src="https://13store.id.vn/uploads/hinh-anh-dong-cam-on-thank-you-dep.png" alt="Security Image" style="width:150px;height:auto;margin-top:20px;">
							</body>
							</html>
							""".formatted(matKhauMoi);

					msg.setContent(html, "text/html; charset=UTF-8");

//					msg.setText("Day la noi dung email", "UTF-8");
					// Gui email
					Transport.send(msg);
					System.out.println("Gui email thanh cong");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Loi ket noi");
					return false;
				}
		return true;
	}
}
