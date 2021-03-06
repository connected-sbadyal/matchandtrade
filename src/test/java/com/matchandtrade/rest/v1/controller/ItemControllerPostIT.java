package com.matchandtrade.rest.v1.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.matchandtrade.persistence.entity.TradeMembershipEntity;
import com.matchandtrade.rest.RestException;
import com.matchandtrade.rest.v1.json.ItemJson;
import com.matchandtrade.test.TestingDefaultAnnotations;
import com.matchandtrade.test.random.ItemRandom;
import com.matchandtrade.test.random.TradeMembershipRandom;

@RunWith(SpringRunner.class)
@TestingDefaultAnnotations
public class ItemControllerPostIT {

	private ItemController fixture;
	@Autowired
	private MockControllerFactory mockControllerFactory;
	@Autowired
	private TradeMembershipRandom tradeMembershipRandom;

	@Before
	public void before() {
		if (fixture == null) {
			fixture = mockControllerFactory.getItemController(true);
		}
	}

	@Test
	public void post() {
		TradeMembershipEntity existingTradeMemberhip = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemJson item = ItemRandom.nextJson();
		fixture.post(existingTradeMemberhip.getTradeMembershipId(), item);
	}
	
	@Test(expected = RestException.class)
	public void postCannotHaveDuplicatedName() {
		TradeMembershipEntity existingTradeMemberhip = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemJson item = ItemRandom.nextJson();
		fixture.post(existingTradeMemberhip.getTradeMembershipId(), item);
		fixture.post(existingTradeMemberhip.getTradeMembershipId(), item);
	}
	
	@Test(expected=RestException.class)
	public void postNameIsNull() {
		TradeMembershipEntity existingTradeMemberhip = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemJson item = ItemRandom.nextJson();
		item.setName(null);
		fixture.post(existingTradeMemberhip.getTradeMembershipId(), item);
	}

	@Test(expected = RestException.class)
	public void postNameTooLong() {
		TradeMembershipEntity existingTradeMemberhip = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemJson item = ItemRandom.nextJson();
		item.setName("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
		fixture.post(existingTradeMemberhip.getTradeMembershipId(), item);
	}
	
	@Test(expected = RestException.class)
	public void postNameTooShort() {
		TradeMembershipEntity existingTradeMemberhip = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemJson item = ItemRandom.nextJson();
		item.setName("ab");
		fixture.post(existingTradeMemberhip.getTradeMembershipId(), item);
	}

	@Test(expected = RestException.class)
	public void postTradeMembershipNotFound() {
		ItemJson item = ItemRandom.nextJson();
		fixture.post(-1, item);
	}

	@Test(expected=RestException.class)
	public void postUserNotAssociatedWithTradeMembership() {
		TradeMembershipEntity existingTradeMemberhip = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemJson item = ItemRandom.nextJson();
		fixture = mockControllerFactory.getItemController(false);
		fixture.post(existingTradeMemberhip.getTradeMembershipId(), item);
	}
	
}
