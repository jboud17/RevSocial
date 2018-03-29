package com.revature.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.revature.user.UserNew;

public interface postRepo extends CrudRepository<Post,Integer> {

	public List<Post> findByUser(UserNew user);
    public List<Post> findByHash(String hash);
}
