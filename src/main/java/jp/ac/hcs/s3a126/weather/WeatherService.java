package jp.ac.hcs.s3a126.weather;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 天気予報情報を操作する
 * 天気予報処理APIを活用する
 * -https://weather.tsukumijima.net/api/forecast?city={cityCode}
 */

@Transactional
@Service
public class WeatherService {
	
	@Autowired
	RestTemplate restTemplate;
	
	/**天気予報処理API リクエストURL*/
	private static final String URL = "https://weather.tsukumijima.net/api/forecast?city={cityCode}";
	
	/**
	 * 指定した地域名に紐づく天気予報情報を取得する
	 * @param cityCode 都市コード
	 * @return WeatherEntity
	 */
	public WeatherEntity getWeather(String cityCode) {
		//APIへアクセスして、結果を取得
		String json = restTemplate.getForObject(URL, String.class, cityCode);
		
		//エンティティクラスを生成
		WeatherEntity weatherEntity = new WeatherEntity();
		
		//jsonクラスへ変換失敗のため、例外処理
		try {
			//変換クラスを生成し、文字列からjsonクラスへ変換する（例外の可能性あり）
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);
			
			//titleパラメータの抽出
			String title = node.get("title").asText();
			weatherEntity.setTitle(title);
			
			//descriptionパラメータの抽出
			JsonNode description = node.get("description");
			weatherEntity.setDescription(description.get("headlineText").asText() + description.get("bodyText").asText());
			//forecast（配列）をForEachで配列分繰り返す
			for(JsonNode forecast : node.get("forecasts")) {
				WeatherData data = new WeatherData();
				//dateLabelをDataクラスへ設定
				data.setDateLabel(forecast.get("dateLabel").asText());
				//telopをDataクラスへ設定
				data.setTelop(forecast.get("telop").asText());
				//DataクラスをEntityの配列に追加
				weatherEntity.getForecasts().add(data);
				
			}
			
		} catch (IOException e) {
			//例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();
		}
		return weatherEntity;
	}
}
