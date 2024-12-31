package com.siva.edairy.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public String displayEntries(HttpSession session,@RequestParam(defaultValue="0") int pageNo, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("username", user.getFullname());
			Page<Entry> pages = entryService.getAllEntries(user, pageNo);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalElements", pages.getTotalElements());
			model.addAttribute("totalPages", pages.getTotalPages());
			model.addAttribute("entries", pages.getContent());
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
	
	@GetMapping("/user/viewprofile")
	public String viewUserProfile(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("user", user);
			return "ViewProfile";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/user/editprofile/{id}")
	public String updateUserProfile(HttpSession session, Model model, @PathVariable("id") int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("user", user);
			return "EditProfile";
		}
		else {
			return "redirect:/login";
		}
		
	}
	
	@PostMapping("/user/updateInformation")
	public String updateUserInformation(@ModelAttribute User user, HttpSession session, Model model, @RequestParam("date") String date) {
		user.setDateOfBirth(Date.valueOf(date));
		User update = userService.addUser(user);
		if (update!=null) {
			model.addAttribute("user", update);
			session.setAttribute("successMsg", "user information updated successfully");
			return "redirect:/user/viewprofile";
		}
		else {
			session.setAttribute("failMsg", "sorry something went wrong on the server");
			return "redirect:/user/viewprofile";
		}
	}
	
	@GetMapping("/user/deleteprofile/{id}")
	public String deleteAccount(HttpSession session, Model model, @PathVariable("id") int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			boolean entryIsDelete = entryService.removeEntriesByUser(user);
			boolean userIsDelete = userService.removeUserById(id);
			if (entryIsDelete && userIsDelete) {
				session.setAttribute("successMsg", "user account successfully deleted");
				return "redirect:/login";
			}
			else {
				session.setAttribute("failMsg", "sorry, something went wrong on the server");
				return "redirect:/user/viewprofile";
			}
		}
		else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/forgotpassword")
	public String forgotPassword() {
		return "ForgotPassword";
	}
	
	@PostMapping("/next")
	public String emailAndMobileNumber(HttpSession session, @RequestParam("email") String email, @RequestParam("mobile") String mobile) {
		User user = userService.getUserByEmailAndMobile(email, mobile);
		if (user!=null) {
			System.out.println(email);
			session.setAttribute("email", email);
			return "ForgotPassword1";
		}
		else {
			session.setAttribute("failMsg", "Invalid Email And Mobile");
			return "redirect:/forgotpassword";
		}
	}
	
	@PostMapping("/changepassword")
	public String changePassword(HttpSession session, @RequestParam("password") String password, @RequestParam("cpassword") String cpassword) {
		String email = (String) session.getAttribute("email");
		if (email!=null) {
			if (password.equals(cpassword)) {
				User user = userService.getUserByEmail(email);
				if (user!=null) {
					user.setPassword(password);
					User update = userService.addUser(user);
					if (update!=null) {
						session.setAttribute("successMsg", "password changed successfully");
						return "redirect:/login";
					}
					else {
						session.setAttribute("failMsg", "something went wrong");
						return "ForgotPassword1";
					}
				}
				else {
					session.setAttribute("failMsg", "something went wrong");
					return "ForgotPassword1";
				}
			}
			else {
				session.setAttribute("failMsg", "Both passwords are not same");
				return "ForgotPassword1";
			}
		}
		else {
			session.setAttribute("failMsg", "something went wrong");
			return "redirect:/login";
		}
	}

	@GetMapping("/user/logout")
	public String logoutPage(HttpSession session) {
		session.removeAttribute("user");
		session.setAttribute("logout", "you have successfully logout");
		return "redirect:/login";
	}
	
}
