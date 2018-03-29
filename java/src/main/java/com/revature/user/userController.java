package com.revature.user;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.config.FrontController;
//import com.revature.config.S3Bucket;
import com.revature.config.S3Bucket;

@RestController
public class userController {
	
	private static final Logger log = Logger.getLogger(userController.class);
			
	@Autowired
	private userService UserService;
		
	// AllUsersServlet
	@RequestMapping("/SocialMedia/allUsers")
	public String getAllUsers(HttpServletRequest req, HttpServletResponse resp) {
		FrontController.addHeader(resp);
		List<UserNew> allUsers = UserService.getAllUser();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//everything but password has @Expose, this makes it so it won't write the passwords
		String json = gson.toJson(allUsers);
		return json;
	}
	
	@RequestMapping("/users/{id}")
	public UserNew getUser(@PathVariable Integer id) {
		return UserService.getUser(id);
	}
	
	//RegisterUserServlet
	@RequestMapping(method=RequestMethod.POST, value="/SocialMedia/registerUser")
	public void addUser(HttpServletRequest req, HttpServletResponse resp, 
			@RequestParam("firstName") String firstname, @RequestParam("lastName") String lastname,
			@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("birthdate") String date) throws IOException {
		//split the date
		String[] split = date.split("-");
		int[] splitInNums = new int[3];
		for(int i=0; i<3; i++) {
			splitInNums[i] = Integer.parseInt(split[i]);
		}
		Timestamp birthdate = null;
		
		//Parse the date
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
			Calendar parsedDate = new GregorianCalendar(splitInNums[0], splitInNums[1], splitInNums[2]);
		    birthdate = new Timestamp(parsedDate.getTimeInMillis());
		    System.out.println(birthdate);
		} catch(Exception e) {
			System.out.println("date format failed");
			System.out.println("Attempted date: " + date);
			e.printStackTrace();
		}
		
		UserNew user = new UserNew();
		
		user.setFirst_name(firstname);
		user.setLast_name(lastname);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setBirthdate(birthdate);
		
		UserService.addUser(user);
		resp.sendRedirect("http://localhost:4200/login");
	}
	
	//UpdateUserDetailsServlet
	@RequestMapping(method=RequestMethod.POST, value="/SocialMedia/updateInfo")
	public void updateUser(HttpServletRequest req, HttpServletResponse resp,@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("email") String email) {
		HttpSession session = req.getSession();
		Integer id = (Integer) session.getAttribute("uid");
		UserNew tmpUser = UserService.getUser(id);
		tmpUser.setFirst_name(firstname);
		tmpUser.setLast_name(lastname);
		tmpUser.setEmail(email);
		session.setAttribute("firstname", firstname);
		session.setAttribute("lastname", lastname);
		session.setAttribute("email", email);
		UserService.updateUser(id, tmpUser);
		try {
			resp.sendRedirect("http://localhost:4200/home");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		UserService.deleteUser(id);
	}
	
	//LoginServlet
	@RequestMapping(method=RequestMethod.POST, value = "/SocialMedia/login")
	public void Login(HttpServletRequest req, HttpServletResponse resp,@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
		HttpSession session = req.getSession();
		String page = "http://localhost:4200/login";
		if(UserService.Login(username, password)) {
			UserNew infoHolder = UserService.getUser(username);
			session.setAttribute("uid",infoHolder.getUser_id());
			session.setAttribute("imgHash",infoHolder.getHash());
			session.setAttribute("firstname", infoHolder.getFirst_name());
			session.setAttribute("lastname",infoHolder.getLast_name());
			session.setAttribute("username", username);
			session.setAttribute("email", infoHolder.getEmail());
			session.setAttribute("birthdate",infoHolder.getBirthdate());
			page = "http://localhost:4200/home";
			log.info("Sucessfully logged in as "+username);
		} else {
			log.info("Wrong Login credentials. U:"+username+" P:"+password);
		}
		resp.sendRedirect(page);
	}
	
	//LogoutServlet
	@RequestMapping("/SocialMedia/logout")
	public void Logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		FrontController.addHeader(resp);
		HttpSession session = req.getSession();
		String user = (String) session.getAttribute("username");
		if (session != null){
		session.invalidate();
		log.info(user+" logged out");
		}
		resp.sendRedirect("http://localhost:4200/login");
		
	}
	
//	@RequestMapping("/sessiontest")
//	public String Tester(HttpServletRequest req, HttpServletResponse resp) {
//		HttpSession session = req.getSession();
//		String name = (String) session.getAttribute("username");
//		return name;
//	}
	
	//ResetUserPasswordServlet
	@RequestMapping(method=RequestMethod.POST, value="/SocialMedia/resetPassword")
	public void updatePassword(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam("newPassword") String password) throws IOException {
		HttpSession session = req.getSession();
		Integer id = (Integer) session.getAttribute("uid");
		UserNew tmpUser = UserService.getUser(id);
		tmpUser.setPassword(password);
		
		String email = tmpUser.getEmail();
		emailUser(email, password);
		UserService.updateUser(id, tmpUser);
		
		resp.sendRedirect("http://localhost:4200/home");
	}
	
	public void emailUser(final String email, String newPassword) {
		 //test1 works with SSL encryption!
		final String username = "mineminemineminemine73@gmail.com";
		final String password = "FindMyFriends";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject("Password has been reset.");
			message.setText("Hi User!" +
					"\n\n Your password has been reset to "+newPassword+". "
							+ "Let us know if you did not make this change!");
			Transport.send(message);

			System.out.println("User has been emailed.");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	//ProfilePictureServlet
	@RequestMapping(method=RequestMethod.POST, value="/SocialMedia/profilePic")
	public void changePic(HttpServletRequest req, HttpServletResponse resp, @RequestParam("profilepic") MultipartFile file) throws IOException {
		FrontController.addHeader(resp); // had 2 of these. removed one
		HttpSession session = req.getSession();
		Integer id = (Integer) session.getAttribute("uid");
		
//		FileItemFactory fileFact = new DiskFileItemFactory();
//		ServletFileUpload servfileup = new ServletFileUpload(fileFact);
//		List<FileItem> formresults = null;
//		try {
//			formresults = servfileup.parseRequest(req);
//		} catch (FileUploadException e1) {
//			e1.printStackTrace();
//		}
		String imgHash = "";
//		
//		for(FileItem result : formresults) {
//			//byte[] fileByteArray = result.get();
//			S3Bucket s3 = new S3Bucket();
////			imgHash = s3.uploadToS3(fileByteArray);
//			System.out.println("imgHash");
//		}
		
		byte[] fileByteArray = file.getBytes();
		S3Bucket s3 = new S3Bucket();
		imgHash = s3.uploadToS3(fileByteArray);
		System.out.println("imgHash = " + imgHash);
	
		UserNew tmpUser = UserService.getUser(id);
		tmpUser.setHash(imgHash);
		UserService.updateUser(id, tmpUser);
		session.setAttribute("imgHash", imgHash);
		log.info("User "+id+" updated their profile picture");
		resp.sendRedirect("http://localhost:4200/home");	
	}
	
}
	    	

