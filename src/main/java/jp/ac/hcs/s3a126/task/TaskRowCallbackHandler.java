package jp.ac.hcs.s3a126.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

import jp.ac.hcs.s3a126.WebConfig;

/**
 * SQLで取得した結果をCSVファイルとしてサーバに保存する.
 */
public class TaskRowCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		try {

			File directory = new File(WebConfig.OUTPUT_PATH);
			if (!directory.exists()) {
				directory.mkdir();
			}

			File file = new File(WebConfig.OUTPUT_PATH + WebConfig.FILENAME_TASK_CSV);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);
			do {
				// Taskテーブルのデータ構造
				String str = rs.getInt("id") + ","
						+ rs.getString("user_id") + ","
						+ rs.getString("comment") + ","
						+ rs.getDate("limitday");

				bw.write(str);
				bw.newLine();
			} while (rs.next());
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

	}

}