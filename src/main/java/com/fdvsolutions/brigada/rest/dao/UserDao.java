package com.fdvsolutions.brigada.rest.dao;

import java.util.HashMap;

import com.fdvsolutions.brigada.rest.model.User;

public class UserDao {

	public static HashMap<Integer, User> users = new HashMap<Integer, User>();
	
	static {
		
		User user = new User();
		user.setId(1);
		user.setName("user1");
		users.put(1, user);
		
		User user2 = new User();
		user2.setId(2);
		user2.setName("user2");
		users.put(2, user2);		
	}

	public static User getUserById(Integer id) {
		return users.get(id);
	}

	public static HashMap<Integer, User> getAllUsers() {
		return users;
	}

}
