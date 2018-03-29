package com.revature.user;

import org.springframework.data.repository.CrudRepository;

public interface userRepo extends CrudRepository<UserNew,Integer>{
	public UserNew findByUsername(String username);
}
