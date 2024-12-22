package com.siva.edairy.service;

import com.siva.edairy.model.User;

public interface IUserService {
	
	boolean emailIsPresent(String email);
	boolean mobileIsPresent(String mobile);
	User addUser(User user);
	User getUserByEmailAndPassword(String email, String password);

}
