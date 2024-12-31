package com.siva.edairy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.siva.edairy.model.User;
import com.siva.edairy.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class IUserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean emailIsPresent(String email) {
		try {
			Boolean existsByEmail = userRepository.existsByEmail(email);
			if (existsByEmail) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean mobileIsPresent(String mobile) {
		try {
			Boolean mobileNumberExist = userRepository.existsByMobile(mobile);
			if (mobileNumberExist) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public User addUser(User user) {
		try {
			User save = userRepository.save(user);
			if (save!=null) {
				return save;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		try {
			User user = userRepository.findByEmailAndPassword(email, password);
			if (user!=null) {
				return user;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean removeUserById(int id) {
		try {
			userRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public User getUserByEmailAndMobile(String email, String mobile) {
		try {
			User user = userRepository.findByEmailAndMobile(email, mobile);
			if (user!=null) {
				return user;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public User getUserByEmail(String email) {
		try {
			User user = userRepository.findByEmail(email);
			if (user!=null) {
				return user;
			}
			else {
				return null;
			} 
		} catch (Exception e) {
			return null;
		}
	}
	
	public void removeSessionMessage() {
		HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("successMsg");
		session.removeAttribute("failMsg");
		session.removeAttribute("logout");
	}

}
