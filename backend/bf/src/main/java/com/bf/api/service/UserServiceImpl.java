package com.bf.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bf.api.request.UserInfoFetchReq;
import com.bf.api.request.UserRegisterPostReq;
import com.bf.db.entity.User;
import com.bf.db.repository.UserRepository;
import com.bf.db.repository.UserRepositorySupport;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getUserId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		
		/** 바꾼부분 start **/
		user.setDepartment(userRegisterInfo.getDepartment());
		user.setName(userRegisterInfo.getName());
		user.setPosition(userRegisterInfo.getPosition());
		/** 바꾼부분 **/
		
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		
		System.out.println("!!@!!!!");
		System.out.println(user);
		return user;
	}
	
	/** start **/ 
	@Override
	public boolean DuplicateUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		if(userRepositorySupport.findUserByUserId(userId).isPresent()) // 유저 정보가 존재하면
			return true;
		else return false;
		
			
	}

	@Override
	public void updateUserInfo(String userId,UserInfoFetchReq userInfo) {
		User updateUser = userRepositorySupport.findUserByUserId(userId).get();
		
		// if(updateUser == null) throw시겨주기 ! 공부필요
		
		updateUser.setDepartment(userInfo.getDepartment());
		updateUser.setPosition(userInfo.getPosition());
		updateUser.setName(userInfo.getName());
		
		userRepository.save(updateUser);
		
	}

	/**
	 * user정보를 삭제한다
	 * @param userId 
	 */
	@Override
	public void deleteUserInfo(String userId) {
		// TODO Auto-generated method stub
		User deleteUser = userRepositorySupport.findUserByUserId(userId).get();

		userRepository.deleteById(deleteUser.getId());
	}


	
}
