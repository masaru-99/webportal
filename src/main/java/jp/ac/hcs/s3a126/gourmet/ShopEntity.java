package jp.ac.hcs.s3a126.gourmet;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 店舗情報
 * 各項目のデータ仕様も合わせて管理する
 *
 */
@Data
public class ShopEntity {
	
	/**店舗情報のリスト*/
	private List<ShopData> shoplist = new ArrayList<ShopData>();
}
