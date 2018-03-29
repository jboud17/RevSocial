package com.revature.post;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.config.FrontController;
import com.revature.config.S3Bucket;
import com.revature.postLike.PostLikeService;
import com.revature.user.UserNew;
import com.revature.user.userService;

@RestController
public class postController {
	
	private static final Logger log = Logger.getLogger(postController.class);
	
	@Autowired
	private postService PostService;
	
	@Autowired
	private userService UserService;
	
	@Autowired
	private PostLikeService postLikeService;
	
	
	// AllPostsServlet
	// Display all posts in homepage
	@RequestMapping("/SocialMedia/allPosts")
	public String getPost(HttpServletRequest req, HttpServletResponse resp) {

        FrontController.addHeader(resp);
        List<Post> allPosts = PostService.getAllPosts();
        List<Object> postLikes = postLikeService.getPostLikes();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();	//dont write user passwords
        String json = gson.toJson(allPosts);
        String split[] = json.split("}}");
        int noLikes = 0;
        String returnJSON = "";

        for(int i=0; i<allPosts.size(); i++) {
            Post p = allPosts.get(i);
            boolean hasLikes = false;
            for(int j=0; j<postLikes.size(); j++) {

                Object row = postLikes.get(j);
                Object[] r = (Object[]) row;

                Integer postId = ((BigDecimal) r[0]).intValue();
                Integer totalLikes = ((BigDecimal) r[1]).intValue();

                if(p.getPost_id() == postId) {
                    returnJSON += split[i] + "}," + "\"post_likes\":\"" + totalLikes + "\"}";
                    hasLikes = true;
                    break;
                } 
            }

            if(!hasLikes) {
                returnJSON += split[i] + "}," + "\"post_likes\":\"" + noLikes + "\"}";
            }

            if(allPosts.size()-i == 1) {
                returnJSON += "]";
            }
        }
        return returnJSON;
    }
	
		//Display all user posts in their profile
		@RequestMapping("/{userId}/posts")
		public List<Post> getPostByUser(@PathVariable Integer userId) {
			return PostService.getPostsByUser(userId);
		}
		
		//CurrUserPostServlet
		// Current users posts
//		@RequestMapping("/cur/posts")
//		public String getPostByUser(HttpServletRequest req, HttpServletResponse resp) {
//			HttpSession session = req.getSession();
//			int id = (Integer) session.getAttribute("uid");
//			List<Post> allPostsByUser = PostService.getPostsByUser(id);
//			Gson gson = new Gson();
//			String json = gson.toJson(allPostsByUser);
//			return json;
//		}
		
//		// NewPostServlet
//		// User creates new post
//		// Probably needs some kind of special request something in signature
		@RequestMapping(method=RequestMethod.POST, value="/SocialMedia/NewPostServlet")
		public void addPost(HttpServletRequest req, HttpServletResponse resp, 
				@RequestParam("postSummary") String posttext, @RequestParam("title") String title, @RequestParam("postImage") MultipartFile file) throws IOException {		
			FrontController.addHeader(resp);
			HttpSession session = req.getSession();
			
			FrontController.addHeader(resp);
			resp.setContentType("text/html");
			
			int id = (Integer) session.getAttribute("uid");
			UserNew user = UserService.getUser(id);
			
//			FileItemFactory fileFact = new DiskFileItemFactory();
//			ServletFileUpload servfileup = new ServletFileUpload(fileFact);
//			List<FileItem> formresults = null;
//			try {
//				formresults = servfileup.parseRequest(req);
//			} catch (FileUploadException e1) {
//				e1.printStackTrace();
//			}
			String imgHash = "";
			
//			for(FileItem result : formresults) {
//				if(result.isFormField()) {
//					if(result.getFieldName().equals("postSummary"))
//						posttext = result.getString();
//					if(result.getFieldName().equals("title"))
//						title = result.getString();
//				}
//				else {
//					byte[] fileByteArray = result.get();
//					S3Bucket s3 = new S3Bucket();
//					imgHash = s3.uploadToS3(fileByteArray);
//				}
//			}
			

			byte[] fileByteArray = file.getBytes();
			S3Bucket s3 = new S3Bucket();
			imgHash = s3.uploadToS3(fileByteArray);
			
			
			Post post1 = new Post(title, imgHash, posttext ,user);
			PostService.addPost(post1);
			
			resp.sendRedirect("http://localhost:4200/home");	
		}
		
		// Update post based on id
		@RequestMapping(method=RequestMethod.PUT, value="/{userId}/posts/{postId}")
		public void updatePost(@RequestBody Post post, @PathVariable Integer userId, @PathVariable Integer postId) {
			//post.setUser(new UserNew(userId,"","","","","","",null));
			PostService.updatePost(post);
		}
		
		// Delete post based on Id
		@RequestMapping(method=RequestMethod.DELETE, value="/posts/{postId}")
		public void deletePost(@PathVariable Integer postId) {
			PostService.deletePost(postId);
		}
}
