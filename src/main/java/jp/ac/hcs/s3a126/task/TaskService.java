package jp.ac.hcs.s3a126.task;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
			data.setLimitday(new SimpleDateFormat("yyyy-MM-dd").parse(limitday));
			rowNumber = taskRepository.insertOne(data);
			
		}catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber;
	}
	
	/**
	 * タスク情報を削除する
	 * @param data 削除するユーザ情報
	 * @return rowNumber
	 * @throws ParseException 
	 */
	public boolean deleteOne(int id) throws ParseException {
		int count;
		try {
			count = taskRepository.deleteOne(id);
		}catch (DataAccessException e) {
			e.printStackTrace();
			count = 0;
		}
		return count > 0;
	}
	
	/**
	 * タスク情報をCSVファイルとしてサーバに保存する.
	 * @param user_id ユーザID
	 * @throws DataAccessException
	 */
	public void taskListCsvOut(String user_id) throws DataAccessException {
		taskRepository.tasklistCsvOut(user_id);
	}

	/**
	 * サーバーに保存されているファイルを取得して、byte配列に変換する.
	 * @param fileName ファイル名
	 * @return ファイルのbyte配列
	 * @throws IOException ファイル取得エラー
	 */
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}
}
