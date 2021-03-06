package com.matchandtrade.rest.v1.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.matchandtrade.persistence.common.SearchResult;
import com.matchandtrade.persistence.entity.ItemEntity;
import com.matchandtrade.persistence.entity.TradeMembershipEntity;
import com.matchandtrade.rest.RestException;
import com.matchandtrade.rest.v1.json.ItemJson;
import com.matchandtrade.test.TestingDefaultAnnotations;
import com.matchandtrade.test.random.ItemRandom;
import com.matchandtrade.test.random.TradeMembershipRandom;

@RunWith(SpringRunner.class)
@TestingDefaultAnnotations
public class ItemControllerGetIT {

	private ItemController fixture;
	@Autowired
	private ItemRandom itemRandom;
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
	public void getAllFromTradeMembership() {
		TradeMembershipEntity existingTradeMembership = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		itemRandom.nextPersistedEntity(existingTradeMembership);
		itemRandom.nextPersistedEntity(existingTradeMembership);
		itemRandom.nextPersistedEntity(existingTradeMembership);
		SearchResult<ItemJson> response = fixture.get(existingTradeMembership.getTradeMembershipId(), null, null, null);
		assertEquals(3, response.getResultList().size());
	}

	@Test
	public void getById() {
		TradeMembershipEntity existingTradeMembership = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemEntity existingItem = itemRandom.nextPersistedEntity(existingTradeMembership);
		ItemJson response = fixture.get(existingTradeMembership.getTradeMembershipId(), existingItem.getItemId());
		assertNotNull(response);
	}

	@Test
	public void getByName() {
		TradeMembershipEntity existingTradeMembership = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemEntity existingItem = itemRandom.nextPersistedEntity(existingTradeMembership);
		SearchResult<ItemJson> response = fixture.get(existingTradeMembership.getTradeMembershipId(), existingItem.getName(), null, null);
		assertEquals(existingItem.getName(), response.getResultList().get(0).getName());
	}
	
	@Test(expected = RestException.class)
 	public void getTradeMembershipNotFound() {
		TradeMembershipEntity existingTradeMembership = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemEntity existingItem = itemRandom.nextPersistedEntity(existingTradeMembership);
		fixture.get(-1, existingItem.getItemId());
	}

	@Test(expected = RestException.class)
 	public void getUserNotAssociatedWithTradeMembership() {
		TradeMembershipEntity existingTradeMembership = tradeMembershipRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		ItemEntity existingItem = itemRandom.nextPersistedEntity(existingTradeMembership);
		fixture = mockControllerFactory.getItemController(false);
		fixture.get(existingTradeMembership.getTradeMembershipId(), existingItem.getItemId());
	}

}
