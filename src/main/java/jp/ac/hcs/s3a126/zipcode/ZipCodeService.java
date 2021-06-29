package jp.ac.hcs.s3a126.zipcode;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 郵便番号情報を操作する
 * zipcloud社の郵便番号検索APIを活用する
 * -http://zipcloud.ibsnet.co.jp/doc/api
 */

@Transactional
@Service
public class ZipCodeService {
	
	@Autowired
	RestTemplate restTemplate;
	
	/**郵便番号検索API　リクエストURL*/
	private static final String URL = "https://zipcloud.ibsnet.co.jp/api/search?zipcode={zipcode}";
	
	/**
	 * 指定した郵便番号に紐づく郵便番号情報を取得する
	 * @param zipcode 郵便番号(７桁、ハイフンなし)
	 * @return ZipCodeEntity
	 */
	public ZipCodeEntity getZip(String zipcode) {
		//APIへアクセスして、結果を取得
		String json = restTemplate.getForObject(URL, String.class, zipcode);
		
		//エンティティクラスを生成
		ZipCodeEntity zipCodeEntity = new ZipCodeEntity();
		
		//jsonクラスへ変換失敗のため、例外処理
		try {
			//変換クラスを生成し、文字列からjsonクラスへ変換する（例外の可能性あり）
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);
			
			//statusパラメータの抽出
			String status = node.get("status").asText();
			zipCodeEntity.setStatus(status);
			
			//messageパラメータの抽出
			String message = node.get("message").asText();
			zipCodeEntity.setMessage(message);
			
			//resultパラメータの抽出（配列分取得する）
			for(JsonNode result : node.get("results")) {
				//データクラスの生成（result1件分）
				ZipCodeData zipCodeData = new ZipCodeData();
				
				zipCodeData.setZipcode(result.get("zipcode").asText());
				zipCodeData.setPrefcode(result.get("prefcode").asText());
				zipCodeData.setAddress1(result.get("address1").asText());
				zipCodeData.setAddress2(result.get("address2").asText());
				zipCodeData.setAddress3(result.get("address3").asText());
				zipCodeData.setKana1(result.get("kana1").asText());
				zipCodeData.setKana2(result.get("kana2").asText());
				zipCodeData.setKana3(result.get("kana3").asText());
				
				//可変長配列の末尾に追加
				zipCodeEntity.getResults().add(zipCodeData);
			}
			
		} catch (IOException e) {
			//例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();
		}
		return zipCodeEntity;
	}
}
