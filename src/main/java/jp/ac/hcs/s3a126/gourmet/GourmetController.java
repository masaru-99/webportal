package jp.ac.hcs.s3a126.gourmet;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GourmetController {
	@Autowired
	private GourmetService gourmetService;
	
	/**
	 * キーワードからグルメに関する店舗情報を検索する
	 * 地域は北海道に限定する
	 * @param keyword 検索するキーワード
	 * @param principal ログイン情報
	 * @param model
	 */
	@PostMapping("/gourmet")
	public String search(@RequestParam(name = "shopname", required = false) String keyword, Principal principal, Model model) {
		
		ShopEntity shopEntity = gourmetService.searchGourmet(keyword);
		model.addAttribute("shopEntity", shopEntity);
		model.addAttribute("keyword", "「" + keyword + "」の検索結果");
		log.info("[" + principal.getName() + "]グルメ検索:" + keyword);
		
		return "gourmet/gourmet";
		
	}
	
};
