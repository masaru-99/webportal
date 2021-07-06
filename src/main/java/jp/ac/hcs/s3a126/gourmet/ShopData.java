package jp.ac.hcs.s3a126.gourmet;

import lombok.Data;

/** １件分の店舗情報*/
@Data
public class ShopData {
	
	/** id */
	private String id;
	
	/** 名前 */
	private String name;
	
	/** ロゴURL */
	private String logo_image;
	
	/** 名前（カナ） */
	private String name_kana;
	
	/** 住所 */
	private String address;
	
	/** 最寄り駅 */
	private String access;
	
	/** URL */
	private String url;
	
	/** 携帯用のイメージ画像 */
	private String image;
}
