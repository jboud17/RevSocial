package com.revature.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {

	@Autowired
	private userRepo UserRepo;
	
	public List<UserNew> getAllUser(){
		List<UserNew> users = new ArrayList<>();
		UserRepo.findAll()
		.forEach(users::add);
		return users;
	}
	
	
	public UserNew getUser(Integer id) {
		return UserRepo.findById(id).get();
	}
	
	public UserNew getUser(String Username) {
		return UserRepo.findByUsername(Username);
	}

	public void addUser(UserNew user) {
		UserRepo.save(user);
	}

	public void updateUser(Integer id, UserNew user) {
		UserRepo.save(user);
	}

	public void deleteUser(Integer id) {
		UserRepo.deleteById(id);
	}
	
	public Boolean Login(String username, String password) {
		String tempUsr = "";
		String tempPsw = "";
		if(UserRepo.findByUsername(username) != null) {
			tempUsr = UserRepo.findByUsername(username).getUsername();
			tempPsw = UserRepo.findByUsername(username).getPassword();
		}
		if (username.equals(tempUsr) && password.equals(tempPsw)) {
			return true;
		} else {
			return false;		
		}
	}

}
