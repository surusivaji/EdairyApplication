package com.siva.edairy.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.siva.edairy.model.Entry;
import com.siva.edairy.model.User;
import com.siva.edairy.service.IEntryService;
import com.siva.edairy.service.IUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IEntryService entryService;
	
	@GetMapping("/")
	public String indexPage() {
		return "Index";
	}
	
	@GetMapping("/register")
	public String registerPage() {
		return "Registration";
	}
	
	@PostMapping("/saveUserInforamtion")
	public String saveUserInformation(@ModelAttribute User user, HttpSession session, String date) {
		System.out.println(user);
		boolean isMobilePresent = userService.mobileIsPresent(user.getEmail());
		boolean isEmailPresent = userService.emailIsPresent(user.getEmail());
		if (isMobilePresent && isEmailPresent) {
			session.setAttribute("failMsg", "email and mobile number is already exists");
			return "redirect:/register";
		}
		else if (isMobilePresent && !isEmailPresent) {
			session.setAttribute("failMsg", "mobile number is already exists");
			return "redirect:/register";
		}
		else if (isEmailPresent && !isMobilePresent) {
			session.setAttribute("failMsg", "email id already exists");
			return "redirect:/register";
		}
		else {
			user.setDateOfBirth(Date.valueOf(date));
			User save = userService.addUser(user);
			if (save!=null) {
				session.setAttribute("successMsg", "user registration successfully completed");
				return "redirect:/register";
			}
			else {
				session.setAttribute("failMsg", "something went wrong on the server");
				return "redirect:/register";
			}
		}
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "Login";
	}
	
	@PostMapping("/loginInformation")
	public String loginInformation(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
		User user = userService.getUserByEmailAndPassword(email, password);
		if (user!=null) {
			System.out.println(user);
			session.setAttribute("user", user);
			return "redirect:/user/home";
		} 
		else {
			session.setAttribute("failMsg", "Invalid Credientials");
			return "redirect:/login";
		}
	}
	
	@GetMapping("/user/home")
	public String homePage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("username", user.getFullname());
			return "Home";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/user/displayentries")
	public String displayEntries(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("username", user.getFullname());
			List<Entry> allEntries = entryService.getAllEntries(user);
			model.addAttribute("entries", allEntries);
			return "DisplayEntries";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/user/addentry")
	public String addEntryPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("username", user.getFullname());
			return "AddEntry";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@PostMapping("/user/saveEntry")
	public String saveEntry(@ModelAttribute Entry entry, @RequestParam("date") String date, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			entry.setEntryDate(Date.valueOf(date));
			entry.setUser(user);
			Entry save = entryService.addEntry(entry);
			if (save!=null) {
				session.setAttribute("successMsg", "entry added successfully");
				return "redirect:/user/displayentries";
			}
			else {
				session.setAttribute("failMsg", "something went wrong");
				return "redirect:/user/displayentries";
			}
		}
		else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/user/editentry/{id}")
	public String updateEntryPage(@PathVariable("id") int id, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("username", user.getFullname());
			Entry entry = entryService.getEntryById(id);
			if (entry!=null) {
				model.addAttribute("entry", entry);
				return "EditEntry";
			}
			else {
				session.setAttribute("failMsg", "id not present in the entry");
				return "redirect:/user/displayentries";
			}
		}
		else {
			return "redirect:/login";
		}
	}
	
	@PostMapping("/user/editEntryInformation")
	public String updateEntryInformation(@ModelAttribute Entry entry, @RequestParam("date") String date, HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user != null) {
	    	entry.setEntryDate(Date.valueOf(date));
	    	entry.setUser(user);
	        Entry isUpdate = entryService.addEntry(entry);
	        if (isUpdate != null) {
	            session.setAttribute("successMsg", "entry updated successfully");
	            return "redirect:/user/displayentries";
	        } else {
	            session.setAttribute("failMsg", "something went wrong on the server");
	            return "redirect:/user/displayentries";
	        }
	    } else {
	        return "redirect:/login";
	    }
	}
	
	@GetMapping("/user/readentry/{id}")
	public String readEntryPage(HttpSession session, Model model, @PathVariable("id") int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("username", user.getFullname());
			Entry entry = entryService.getEntryById(id);
			if (entry!=null) {
				model.addAttribute("entry", entry);
				return "ReadEntry";
			}
			else {
				session.setAttribute("failMsg", "something went wrong on the server");
				return "redirect:/user/displayentries";
			}
		} 
		else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/user/removeEntry/{id}")
	public String removeEntryPage(HttpSession session, Model model, @PathVariable("id") int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			boolean isDelete = entryService.removeEntryById(id);
			if (isDelete) {
				session.setAttribute("successMsg", "user removed successfully");
				return "redirect:/user/displayentries";
			}
			else {
				session.setAttribute("failMsg", "something went wrong on the server");
				return "redirect:/user/displayentries";
			}
		}
		else {
			return "redirect:/user/displayentries";
		}
	}

	@GetMapping("/user/logout")
	public String logoutPage(HttpSession session) {
		session.removeAttribute("user");
		session.setAttribute("logout", "you have successfully logout");
		return "redirect:/login";
	}
	
}
