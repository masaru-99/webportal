package jp.ac.hcs.s3a126.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * ユーザ一覧の表示を行う
	 */
	
	@GetMapping("/user")
	public String getUserList(Principal principal, Model model) {
		
		if(principal.getName().isEmpty()) {
			return "index";
		}
		
		UserEntity userEntity = userService.selectUser();
		model.addAttribute("userEntity", userEntity);
		
		log.info("[" + principal.getName() + "]ユーザ一覧");
		
		return "user/userList";
		
	}
	/**
	 * ユーザ登録画面（管理者用）を表示する
	 * @param form 登録時の入力チェック用UserForm
	 * @param model
	 * @return ユーザ登録画面（管理者用）
	 */
	@GetMapping("/user/insert")
	public String getUserInsert(UserForm form, Model model) {
		return "user/insert";
	}
	
	/**
	 * １件分のユーザ情報をデータベースに登録する
	 * @param form 登録するユーザ情報（パスワードは平文）
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model	
	 * @return ユーザ一覧画面 
	 */
	@PostMapping("/user/insert")
	public String getUserInsert(@ModelAttribute @Validated UserForm form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {
		
		//入力チェックに引っかかった場合、前の画面に戻る
		if(bindingResult.hasErrors()) {
			return getUserInsert(form, model);
		}
		UserData userData = userService.refillToData(form);
		boolean result = userService.insertOne(userData);
		
		return getUserList(principal, model);
	}
	
	/**
	 * ユーザ詳細情報画面を表示する
	 * @param user_id 検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ詳細情報画面
	 */
	@GetMapping("/user/detail/{id}")
	public String getUserDetail(@PathVariable("id") String user_id,
			Principal principal,
			Model model) {
		
		if(user_id == null) {
			return "user/user";
		}
		
		UserData data = userService.selectUserOne(user_id);
		
		log.info("[" + principal.getName() + "]ユーザ詳細");
		
		model.addAttribute("userData", data);
		return "user/detail";
		
	}
	
	/**
	 * ユーザ情報を一件削除する
	 * @param user_id 削除するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return getUserList 
	 */
	@PostMapping("/user/delete")
	public String userDelete(@RequestParam(name = "user_id", required = false) String user_id,
			Principal principal,
			Model model) {
		
		userService.deleteUserOne(user_id);
		
		log.info("[" + principal.getName() + "]ユーザ削除:[" + user_id + "]");
		
		return getUserList(principal, model);
		
	}
};
