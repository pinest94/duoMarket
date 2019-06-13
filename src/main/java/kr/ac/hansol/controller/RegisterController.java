package kr.ac.hansol.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansol.model.Cart;
import kr.ac.hansol.model.ShippingAddress;
import kr.ac.hansol.model.User;
import kr.ac.hansol.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping("/register")
	public String registerUser(Model model) {
		User user = new User();
		ShippingAddress shippingAddress = new ShippingAddress();
		
		user.setShippingAddress(shippingAddress);
		
		model.addAttribute("user", user);
		
		return "registerUser";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPost(@Valid User user, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			return "registerUser";
		}
		
		List<User> userList = userService.getAllUsers();
		
		for(int i=0; i<userList.size(); i++) {
			if(user.getUsername().equals(userList.get(i).getUsername())) {
				model.addAttribute("usernameMsg", "Username already exist");
				return "registerUser"; 
			}
		}
		
		user.setEnabled(true);
		
		if(user.getUsername().equals("admin")) {
			user.setAuthority("ROLE_ADMIN");
		} else {
			user.setAuthority("ROLE_USER");
		}

		Cart cart = new Cart();
		user.setCart(cart);
		userService.addUser(user);
		
		return "registerUserSuccess";
	}
}
