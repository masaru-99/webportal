package jp.ac.hcs.s3a126.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * タスク情報を操作する
 */

@Transactional
@Service
public class TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	
	/**
	 * 指定したユーザIDのタスク情報を全件取得する
	 * @param user_id ユーザID
	 * @return TaskEntity
	 */
	public TaskEntity selectAll(String user_id) {
		TaskEntity taskEntity;
		try {
			taskEntity = taskRepository.selectAll(user_id);
		}catch (DataAccessException e) {
			e.printStackTrace();
			taskEntity = null;
		}
		return taskEntity;
	}
	/**
	 * タスク情報を追加する
	 * @param data 追加するユーザ情報
	 * @return rowNumber
	 * @throws ParseException 
	 */
	public int insertOne(String user_id, String comment, String limitday) throws ParseException {
		int rowNumber = 0;
		try {
			
			TaskData data = new TaskData();
			data.setUser_id(user_id);
			data.setComment(comment);
			data.setLimitday(new SimpleDateFormat("yyyy-dd-MM").parse(limitday));
			rowNumber = taskRepository.insertOne(data);
			
		}catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber;
	}
}
