package com.revature.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebServlet("/SessionServlet")
@RestController
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    @RequestMapping("/SocialMedia/session")
    public void blah(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FrontController.addHeader(resp);
    	HttpSession session = req.getSession(false);
		if(session!=null){
			resp.setContentType("application/json");
			resp.getWriter().write("{\"uid\":\""+session.getAttribute("uid")+"\",");
			resp.getWriter().write("\"username\":\""+session.getAttribute("username")+"\",");
			resp.getWriter().write("\"firstname\":\""+session.getAttribute("firstname")+"\",");
			resp.getWriter().write("\"lastname\":\""+session.getAttribute("lastname")+"\",");
			resp.getWriter().write("\"email\":\""+session.getAttribute("email")+"\",");
			if(session.getAttribute("imgHash") != null) {
				resp.getWriter().write("\"imgHash\":\""+session.getAttribute("imgHash")+"\",");
			}
			else if(session.getAttribute("imgHash") == null) {
				resp.getWriter().write("\"imgHash\":null,");
			}
			if(session.getAttribute("birthdate") != null) {
				resp.getWriter().write("\"birthdate\":\""+session.getAttribute("birthdate")+"\"}");
			}
			else if(session.getAttribute("birthdate") == null) {
				resp.getWriter().write("\"birthdate\":null}");
			}
		} else {
			resp.setContentType("application/json");
			resp.getWriter().write("{\"uid\":null}");
		}
	}

}

