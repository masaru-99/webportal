package jp.ac.hcs.s3a126.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * タスク情報
 * 各項目のデータ仕様も合わせて管理する
 *
 */
@Data
public class TaskEntity {
	
	/**タスク情報のリスト*/
	private List<TaskData> tasklist = new ArrayList<TaskData>();
}
