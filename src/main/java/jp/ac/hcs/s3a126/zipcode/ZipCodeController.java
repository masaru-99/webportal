package jp.ac.hcs.s3a126.zipcode;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ZipCodeController {
	
	/**
	 * 郵便番号から住所を検索し、結果画面を表示する。
	 * @param zipcode 検索する郵便番号（ハイフンなし）
	 * @param principal ログイン情報
	 * @param model
	 * @return 結果画面 - 郵便番号
	 */
	@PostMapping("/zip")
	public String getZipCode(@RequestParam("zipcode") String zipcode,
			Principal principal, Model model) {
		log.info("[" + principal.getName() + "]住所検索：---");
		return "zipcode/zipcode";
	}
}