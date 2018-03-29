package com.revature.post;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.revature.user.UserNew;

public interface postRepo extends CrudRepository<Post,Integer> {
	public List<Post> findAll(Sort sort);
	public List<Post> findByUser(UserNew user);
    public List<Post> findByHash(String hash);
}
