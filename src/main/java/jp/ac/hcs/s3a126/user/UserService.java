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
	
}
