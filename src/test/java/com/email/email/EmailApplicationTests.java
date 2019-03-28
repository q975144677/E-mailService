package com.email.email;

import com.email.email.pojo.Email;
import com.email.email.pojo.User;
import com.email.email.service.EmailService;
import com.email.email.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {
@Autowired
	EmailService emailService ;
@Autowired
	UserService userService ;
@Autowired
	JavaMailSender mailSender ;

@Value("${spring.mail.username}")
	String username ;

	public static String recipientAddress = "q975144677@163.com";
	//收件人账户名
	public static String recipientAccount = "q975144677@163.com";
	//收件人账户密码
	public static String recipientPassword = "163sqm";
@Autowired
	RedisTemplate<String,Object> r;
	@Test
	public void test6(){

		r.opsForValue().set("s","s");
		System.out.println(r.opsForValue().get("s"));
	}

@Test
public void test5() throws Exception {
	 //收件人地址



		//1、连接邮件服务器的参数配置
		Properties props = new Properties();
		//设置传输协议
		props.setProperty("mail.store.protocol", "pop3");
		//设置收件人的POP3服务器
		props.setProperty("mail.pop3.host", "pop3.163.com");
		//2、创建定义整个应用程序所需的环境信息的 Session 对象
		Session session = Session.getInstance(props);
		//设置调试信息在控制台打印出来
		//session.setDebug(true);

		Store store =
				session.getStore("pop3");
		//连接收件人POP3服务器
		store.connect("pop3.163.com", recipientAccount, recipientPassword);
		//获得用户的邮件账户，注意通过pop3协议获取某个邮件夹的名称只能为inbox
		Folder folder = store.getFolder("inbox");
		//设置对邮件账户的访问权限
		folder.open(Folder.READ_WRITE);

		//得到邮件账户的所有邮件信息
		Message[] messages = folder.getMessages();
	for (int i = 0; i < messages.length; i++) {
		String subject = messages[i].getSubject();
		String from = (messages[i].getFrom()[0]).toString();
		System.out.println("第 " + (i + 1) + "封邮件的主题：" + subject);
		System.out.println("第 " + (i + 1) + "封邮件的发件人地址：" + from);
	}
			//关闭邮件夹对象
		folder.close();
		//关闭连接对象
		store.close();

}
	private static String getAllMultipart(Part part) throws Exception{
		String contentType = part.getContentType();
		int index = contentType.indexOf("name");
		boolean conName = false;
		if(index!=-1){
			conName=true;
		}
		//判断part类型
		if(part.isMimeType("text/plain") && ! conName) {
			return (String) part.getContent();
		}else if (part.isMimeType("text/html") && ! conName) {
			return (String) part.getContent();
		}else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++) {
				//递归获取数据
				getAllMultipart(multipart.getBodyPart(i));
				//附件可能是截图或上传的(图片或其他数据)
				if (multipart.getBodyPart(i).getDisposition() != null) {
					//附件为截图
					if (multipart.getBodyPart(i).isMimeType("image/*")) {
						InputStream is = multipart.getBodyPart(i)
								.getInputStream();
						String name = multipart.getBodyPart(i).getFileName();
						String fileName;
						//截图图片
						if(name.startsWith("=?")){
							fileName = name.substring(name.lastIndexOf(".") - 1,name.lastIndexOf("?="));
						}else{
							//上传图片
							fileName = name;
						}

						FileOutputStream fos = new FileOutputStream("D:\\"
								+ fileName);
						int len = 0;
						byte[] bys = new byte[1024];
						while ((len = is.read(bys)) != -1) {
							fos.write(bys,0,len);
						}
						fos.close();
					} else {
						//其他附件
						InputStream is = multipart.getBodyPart(i)
								.getInputStream();
						String name = multipart.getBodyPart(i).getFileName();
						FileOutputStream fos = new FileOutputStream("D:\\"
								+ name);
						int len = 0;
						byte[] bys = new byte[1024];
						while ((len = is.read(bys)) != -1) {
							fos.write(bys,0,len);
						}
						fos.close();
					}
				}
			}
		}else if (part.isMimeType("message/rfc822")) {
			getAllMultipart((Part) part.getContent());
		}
		return "-";
	}



@Test
public void test4(){
User user = new User();
 user.setPostbox("q975144677@163.com");
user.setPostPassword("163sqm");
	Email email = new Email();
	email.setContent("2");
	email.setReceiveemail("q975144677@163.com,2456935718@qq.com");
	email.setSubject("test");
	email.setTemplate("emailTemplate");
emailService.sendEmailByUser(email,user);

}
@Test
public void test3(){
	User user = new User() ;
	user.setPostbox("adsdasasddasdas");
	user.setUsername("adsdasdasads");
	user.setPassword("321");
	userService.insert(user);
	System.out.println(userService.get("adsdasdasads"));

}

@Test
public void test() throws MessagingException {
	Email email = new Email();
	email.setContent("2");
	email.setReceiveemail("q975144677@163.com,2456935718@qq.com");
	email.setSubject("test");
	email.setTemplate("emailTemplate");
	emailService.sendEmail(email);

}

@Test
public void test2(){
	MimeMessage mimeMessage = mailSender.createMimeMessage();
	try {

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage , true);
		helper.setText("2DHSAOIJIO你好 ");
helper.setSubject("2");
System.out.println(username);
helper.setFrom(username);
helper.setTo("q975144677@163.com");
System.out.println("1111111111111111111111111");
mailSender.send(mimeMessage);
	} catch (MessagingException e) {
		e.printStackTrace();
	}

/*
	Email email = new Email();
	email.setReceiveemail("q975144677@163.com");
	email.setSubject("test");
	email.setTemplate("emailTemplate");
	email.setUid(1);
	email.setContent("test");

	try {
		emailService.sendEmail(email);
	} catch (MessagingException e) {
		e.printStackTrace();
	}
*/
}

	@Test
	public void contextLoads() throws ParseException {
		User user = userService.get(1);
		List<User> users = userService.list();
		Email email = emailService.get(1);
		List<Email> emails = emailService.list();
		System.out.println(user);
		for (User user1 : users){
			System.out.println(user1);
		}
		System.out.println(email);
		for(Email email1  : emails){
			System.out.println(emails);
		}
		//2019-03-22 22:01:48
		String s = "2019-03-22 22:01:48";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date =  sdf.parse(s);
		emails = emailService.listBySendTime(date);
		for(Email email1  : emails){
			System.out.println(emails);
		}
	}

}
