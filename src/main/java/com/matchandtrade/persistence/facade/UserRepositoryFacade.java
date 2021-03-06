package com.matchandtrade.persistence.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.matchandtrade.persistence.entity.UserEntity;
import com.matchandtrade.persistence.repository.UserRepository;

@Repository
public class UserRepositoryFacade {
	
	@Autowired
	private UserRepository userRepository;

	public UserEntity get(Integer userId) {
		return userRepository.findOne(userId);
	}

	public UserEntity findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void save(UserEntity entity) {
		userRepository.save(entity);
	}

}
