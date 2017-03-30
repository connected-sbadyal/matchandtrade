package com.matchandtrade.rest.v1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.matchandtrade.authorization.Authorization;
import com.matchandtrade.model.UserModel;
import com.matchandtrade.persistence.entity.UserEntity;
import com.matchandtrade.rest.Controller;
import com.matchandtrade.rest.v1.json.UserJson;
import com.matchandtrade.rest.v1.transformer.UserTransformer;
import com.matchandtrade.rest.v1.validator.UserValidator;

@RestController
@RequestMapping(path="/rest/v1/users/")
public class UserController extends Controller {

	@Autowired
	Authorization authorization;
	@Autowired
	UserModel userModel;
	@Autowired
	UserValidator userValidador;
	@Autowired
	UserTransformer userTransformer;

	@RequestMapping(path="{userId}", method=RequestMethod.GET)
	public UserJson get(@PathVariable("userId") Integer userId) {
		// Validate request identity
		authorization.validateIdentity(getAuthentication());
		// Validate the request
		userValidador.validateGetById(getAuthentication(), userId);
		// Delegate to model layer
		UserEntity userEntity = userModel.get(userId);
		// Transform the response
		UserJson result = UserTransformer.transform(userEntity);
		return result;
	}
	
	@RequestMapping(path="{userId}", method=RequestMethod.PUT)
	public UserJson put(@PathVariable("userId") Integer userId, @RequestBody UserJson requestJson) {
		// Validate request identity
		authorization.validateIdentity(getAuthentication());
		// Validate the request
		requestJson.setUserId(userId);
		userValidador.validatePut(requestJson);
		// Transform the request
		UserEntity userEntity = userTransformer.transform(requestJson, true);
		// Delegate to model layer
		userModel.save(userEntity);
		// Transform the response
		UserJson result = UserTransformer.transform(userEntity);
		return result;
	}
}