package com.revature.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.user.UserNew;
import com.revature.user.userRepo;

@Service
public class postService {
	
	@Autowired	
	private postRepo PostRepo;
	
	@Autowired
	private userRepo UserRepo;
	
	public List<Post> getPostsByUser(Integer userId) {
		List<Post> posts = new ArrayList<>();
		UserNew user = UserRepo.findById(userId).get();
		PostRepo.findByUser(user)
		.forEach(posts::add);
		return posts;
	}
	
	public List<Post> getAllPosts() {
		List<Post> posts = new ArrayList<>();
		PostRepo.findAll()
		.forEach(posts::add);
		return posts;
	}
	
	public Post getPost(Integer id) {
		return PostRepo.findById(id).get();
	}

	public void addPost(Post post) {
		PostRepo.save(post);
	}

	public void updatePost(Post post) {
		PostRepo.save(post);
	}

	public void deletePost(Integer id) {
		PostRepo.deleteById(id);
	}
	
	// unsure if it will return a list or just one post
	public boolean checkHash(String hash) {
        if(PostRepo.findByHash(hash).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
