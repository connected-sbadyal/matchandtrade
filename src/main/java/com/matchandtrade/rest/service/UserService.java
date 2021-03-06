package com.matchandtrade.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.matchandtrade.persistence.entity.UserEntity;
import com.matchandtrade.persistence.facade.UserRepositoryFacade;

@Component
public class UserService {

	@Autowired
	private UserRepositoryFacade userRepository;

	@Transactional
	public void update(UserEntity user) {
		userRepository.save(user);
	}

	public UserEntity get(Integer userId) {
		return userRepository.get(userId);
	}

}
