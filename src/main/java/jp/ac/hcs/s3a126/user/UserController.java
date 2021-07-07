package jp.ac.hcs.s3a126.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * ユーザ一覧の表示を行う
	 */
	@GetMapping("/user/list")
	public String getUser(Principal principal, Model model) {
		
		if(principal.getName().isEmpty()) {
			return "index";
		}
		
		UserEntity userEntity = userService.selectUser();
		model.addAttribute("userEntity", userEntity);
		
		log.info("[" + principal.getName() + "]ユーザ一覧");
		
		return "user/userList";
		
	}
	
};
