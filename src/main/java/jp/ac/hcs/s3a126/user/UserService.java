package jp.ac.hcs.s3a126.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ情報を操作する
 */

@Transactional
@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * 全ユーザ情報を取得する
	 * @return userEntity
	 */
	public UserEntity selectUser() {
		UserEntity userEntity;
		try {
			userEntity = userRepository.selectAll();
		}catch (DataAccessException e) {
			e.printStackTrace();
			userEntity = null;
		}
		return userEntity;
	}
	
	/**
	 * ユーザ情報を1件追加する
	 * @param userData 追加するユーザ情報
	 * @return 処理結果
	 */
	public boolean insertOne(UserData userData) {
		int rowNumber;
		try {
			rowNumber = userRepository.insertOne(userData);
		}catch (DataAccessException e) {
			e.printStackTrace();
			rowNumber = 0;
		}
		
		return rowNumber > 0;
	}
	
	/**
	 * 入力項目をUserDataへ変換する
	 * @param form 入力データ
	 * @return UserData
	 */
	UserData refillToData(UserForm form) {
        UserData data = new UserData();
        data.setUser_id(form.getUser_id());
        data.setPassword(form.getPassword());
        data.setUser_name(form.getUser_name());
        data.setDarkmode(form.isDarkmode());
        data.setRole(form.getRole());
        
        //初期値は有効とする
        data.setEnabled(true);
        return data;
    }
	
	/**
	 * ユーザ詳細情報を一件取得する
	 * @param user_id 取得するユーザのユーザID
	 * @return data
	 */
	public UserData selectUserOne(String user_id) {
		UserData data = new UserData();
		try {
			data = userRepository.selectOne(user_id);
		}catch (DataAccessException e) {
			e.printStackTrace();
			data = null;
		}
		return data;
	}
	
	/**
	 * ユーザ詳細情報を一件削除する
	 * @param user_id 削除するユーザID
	 * @return data
	 */
	public boolean deleteUserOne(String user_id) {
		int rowNumber;
		try {
			rowNumber = userRepository.deleteOne(user_id);
		}catch (DataAccessException e) {
			e.printStackTrace();
			rowNumber = 0;
		}
		return rowNumber > 0;
	}
}
