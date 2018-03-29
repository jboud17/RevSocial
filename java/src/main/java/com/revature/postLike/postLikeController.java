package com.revature.postLike;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.config.FrontController;

@RestController
public class postLikeController {
	private static final Logger log = Logger.getLogger(postLikeController.class);
	
	@Autowired
	private PostLikeService postLikeService;
	
	//LikePostServlet
	@RequestMapping("/SocialMedia/likePost")
	public void LikePost(HttpServletRequest req, HttpServletResponse resp,
		@RequestParam("userId") String userId, @RequestParam("postId") String postId) throws IOException {
		FrontController.addHeader(resp);
		int PostId = Integer.parseInt(postId);
		int UserId = Integer.parseInt(userId);
		if(postLikeService.InsertRecord(PostId, UserId)) {
			resp.getWriter().write("[{\"status\" : \"green\" }]");
			log.info("New record inserted to post_like table");
		} else {
			resp.getWriter().write("[{\"status\" : \"red\" }]");
			log.info("Record already exists in post_like table");
		}
		
	}

}
