package jp.ac.hcs.s3a126.gourmet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 北海道内のグルメ検索を行う
 * 地域は北海道のみに限定する
 */

@Transactional
@Service
public class GourmetService {
	
	@Autowired
	RestTemplate restTemplate;
	
	//北海道に固定
	private static final String large_service_area = "SS40";
			
	//APIキー
	private static final String API_KEY = "e013ede5635860dd";
			
	//グルメサーチAPI リクエストURL
	private static final String URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key={API_KEY}&large_service_area={large_service_area}&keyword={keyword}&format=json";
	
	/**
	 * 指定したキーワードに紐づく店舗情報を取得する
	 * @param keyword キーワード
	 * @return shopEntity
	 */
	public ShopEntity searchGourmet(String keyword) {
		//APIへアクセスして、結果を取得
		String json = restTemplate.getForObject(URL, String.class, API_KEY, large_service_area, keyword);
		
		//エンティティクラスを生成
		ShopEntity shopEntity = new ShopEntity();
		
		//jsonクラスへ変換失敗のため、例外処理
		try {
			//変換クラスを生成し、文字列からjsonクラスへ変換する（例外の可能性あり）
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);
			
			//resultパラメータの抽出（配列分取得する）
			for(JsonNode result : node.get("results").get("shop")) {
				//データクラスの生成（result1件分）
				ShopData shopData = new ShopData();
				
				shopData.setName(result.get("name").asText());
				shopData.setLogo_image(result.get("logo_image").asText());
				shopData.setUrl(result.get("urls").get("pc").asText());
				shopData.setAddress(result.get("address").asText());
				shopData.setAccess(result.get("access").asText());
				
				//可変長配列の末尾に追加
				shopEntity.getShoplist().add(shopData);
			}
			
		} catch (IOException e) {
			//例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();
		}
		return shopEntity;
	}

}
