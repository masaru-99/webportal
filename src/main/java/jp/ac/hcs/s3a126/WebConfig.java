package jp.ac.hcs.s3a126;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
	
	/** 出力パス */
	public static final String OUTPUT_PATH = "target/";

	/** タスク情報のCSVファイル名 */
	public static final String FILENAME_TASK_CSV = "tasklist.csv";
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
