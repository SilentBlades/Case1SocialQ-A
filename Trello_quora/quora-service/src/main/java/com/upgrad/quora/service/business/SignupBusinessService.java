package com.upgrad.quora.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrad.quora.service.dao.UserDAO;
import com.upgrad.quora.service.entity.UserEntity;

@Service
public class SignupBusinessService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private PasswordCryptographyProvider cryptographyProvider;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UserEntity signup(UserEntity userEntity) {
		String[] encryptedText =cryptographyProvider.encrypt(userEntity.getPassword());
		
		userEntity.setSalt(encryptedText[0]);
		userEntity.setPassword(encryptedText[1]);
		
		return userDao.createUser(userEntity);
	}
}
