package jp.ac.hcs.s3a126.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * タスク情報のデータを管理する
 * -Taskテーブル
 */

@Repository
public class TaskRepository {
	/**SQL 全件取得（期限日昇順）*/
	private static final String SQL_SELECT_ALL = "SELECT * FROM task WHERE user_id = ? order by limitday";	
	
	/**SQL１件追加*/
	private static final String SQL_INSERT_ONE = "INSERT INTO task(id, user_id, comment, limitday) VALUES((SELECT MAX(id) + 1 FROM task), ?, ?, ?)";
	
	/**SQL１件削除*/
	private static final String SQL_DELETE_ONE = "DELETE FROM task WHERE id = ?";
	
	@Autowired
	JdbcTemplate jdbc;
	
	/**
	 * TaskテーブルからユーザIDを主キーに全データを取得
	 * @param user_id 検索するユーザID
	 * @return TaskEntity
	 * @throws DataAccessException
	 */
	
	public TaskEntity selectAll(String user_id) throws DataAccessException{
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL, user_id);
		TaskEntity taskEntity = mappingSelectResult(resultList);
		return taskEntity;
	}
	
	/**
	 * Taskテーブルから取得したデータをTaskEntity形式のマッピングする
	 * @param resultList Taskテーブルから取得したデータ
	 * @return TaskEntity
	 */
	private TaskEntity mappingSelectResult(List<Map<String, Object>> resultList) throws DataAccessException {
		TaskEntity entity = new TaskEntity();
		
		for(Map<String, Object> map : resultList) {
			TaskData data = new TaskData();
			data.setId((Integer) map.get("id"));
			data.setUser_id((String) map.get("user_id"));
			data.setComment((String) map.get("comment"));
			data.setLimitday((Date) map.get("limitday"));
			
			entity.getTasklist().add(data);
		}
		return entity;
		
	}
	
	/**
	 * Taskテーブルにデータを１件追加する
	 * @param data 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int insertOne(TaskData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE,
									data.getUser_id(),
									data.getComment(),
									data.getLimitday());
		return rowNumber;
	}
	
	/**
	 * Taskテーブルのデータを１件削除する
	 * @param id 削除するタスクID
	 * @return 削除データ数
	 * @throws DataAccessException
	 */
	public int deleteOne(int id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_DELETE_ONE, id);
		return rowNumber;
	}
}
