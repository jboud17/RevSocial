package com.revature;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.post.Post;
import com.revature.post.postService;
import com.revature.postLike.PostLikeService;
import com.revature.user.UserNew;
import com.revature.user.userService;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DataJpaTest
@SuppressWarnings("deprecation")
public class SocialWebsiteTesting extends AbstractTest{

	@Autowired
	private postService post_service;
	
	@Autowired
	private PostLikeService post_likes_service;
	
	@Autowired
	private userService user_service;
	
	/*
	 * UserController method testing
	 */
	
	@Test
	public void testGetAllUsers() {
		
		List<UserNew> list = user_service.getAllUser();
		Assert.assertNotNull("Failure. List of users is null.", list);
		// Assert.assertEquals("Failure. There should have been atleast 3 users found", 3, list.size());
	}
	
	@Test
	public void testAddUser() {
		
		UserNew temp = new UserNew("Joey", "Tribiani", "HowuDoin", "YaBaby", "Ha", "outtaworkactor@soapOperaStar.com", null);
		user_service.addUser(temp);
		UserNew checkIt = user_service.getUser("HowuDoin");
		
		Assert.assertEquals(temp, checkIt);
	}
	
	@Test
	public void testGetUser() {
		
		UserNew user = user_service.getUser("YaBaby");
		UserNew temp = new UserNew("Joey", "Tribiani", "HowuDoin", "YaBaby", "Ha", "outtaworkactor@soapOperaStar.com", null);
		
		Assert.assertEquals(temp, user);
	}
	
	@Test
	public void testUpdateUser() {
		
		UserNew user = user_service.getUser(2);
		user.setFirst_name("WhyNot");
		
		user_service.updateUser(2, user);
		Assert.assertEquals("WhyNot", user_service.getUser(2).getFirst_name());
	}
	
	@Test
	public void testDeleteUser() {
		
		int lengthBe4 = user_service.getAllUser().size();
		user_service.deleteUser(2);
		int lengthAfter = user_service.getAllUser().size();
		
		Assert.assertNotSame(lengthBe4, lengthAfter);
	}
	
	@Test
	public void testLogin() {
		
		boolean result = user_service.Login("HowuDoin", "YaBaby");
		Assert.assertEquals(true, result);
	}

	/*
	 * PostController method testing
	 */
		
	@Test
	public void testGetPostByUser() {
		
		List<Post> posts = post_service.getPostsByUser(1);
		Assert.assertNotNull(posts);	
	}
	
	@Test
	public void testGetAllPosts() {
		
		List<Post> posts = post_service.getAllPosts();
		Assert.assertNotNull(posts);
	}

	@Test
	public void testGetPost() {
		
		Post post = post_service.getPost(1);
		Assert.assertNotNull(post);
	}
	
	@Test
	public void testAddPost() {
		
		Post post = new Post("title", "hashIt", "This is just for testing", user_service.getUser("HowuDoin"));
		post_service.addPost(post);
		
		Assert.assertEquals(post, post_service.getPost(user_service.getUser("HowuDoin").getUser_id()));
	}

	@Test
	public void testUpdatePost() {
		
		Post post = post_service.getPost(1);
		post.setPost_text("Ha! changed your post:)");
		
		Assert.assertEquals("Ha! changed your post:)", post_service.getPost(1).getPost_text());
	}
	
	@Test
	public void testDeletePost() {
		
		int lengthBe4 = post_service.getAllPosts().size();
		post_service.deletePost(user_service.getUser("HowuDoin").getUser_id());
		int lengthAfter = post_service.getAllPosts().size();
		
		Assert.assertNotSame(lengthBe4, lengthAfter);
	}
	
	@Test
	public void testCheckHash() {
		
		boolean post = post_service.checkHash("Ha");
		Assert.assertEquals(true, post);
	}
		
	/*
	 * PostLike method testing
	 */
	
	@Test
	public void testInsertRecord() {
		
		boolean result = post_likes_service.InsertRecord(1, user_service.getUser("HowuDoin").getUser_id());
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void testGetPostLikes() {
		
		List<Object> list = post_likes_service.getPostLikes();
		Assert.assertNotNull(list);
	}
}
