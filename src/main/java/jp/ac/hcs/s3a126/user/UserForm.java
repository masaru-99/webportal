package jp.ac.hcs.s3a126.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * フロント-バックでユーザ情報をやり取りする
 * 各項目のデータ仕様はUserEntityを参照する
 */

@Data
public class UserForm {
	/** ユーザID（メールアドレス）*/
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;
	
	/** パスワード */
	@NotBlank(message = "{require_check}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{pattern_check}")
	private String password;
	
	/** ユーザ名 */
	@NotBlank(message = "{require_check}")
	private String user_name;
	
	/** ダークモードフラグ */
//	@Pattern(regexp = "^(true|false)$", message = "{pattern_check}")
	private boolean darkmode;
	
	/** 権限 */
	@NotBlank(message = "{require_check}")
	private String role;
}
