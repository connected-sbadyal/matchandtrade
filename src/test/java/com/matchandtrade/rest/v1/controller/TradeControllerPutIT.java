package com.matchandtrade.rest.v1.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.matchandtrade.persistence.entity.TradeEntity;
import com.matchandtrade.rest.RestException;
import com.matchandtrade.rest.v1.json.TradeJson;
import com.matchandtrade.rest.v1.transformer.TradeTransformer;
import com.matchandtrade.test.TestingDefaultAnnotations;
import com.matchandtrade.test.random.TradeRandom;

@RunWith(SpringRunner.class)
@TestingDefaultAnnotations
public class TradeControllerPutIT {
	
	@Autowired
	private MockControllerFactory mockControllerFactory;
	private TradeController fixture;
	@Autowired
	private TradeRandom tradeRandom;

	@Before
	public void before() {
		if (fixture == null) {
			fixture = mockControllerFactory.getTradeController(false);
		}
	}
	
	@Test
	public void put() {
		TradeEntity existingTrade = tradeRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		TradeJson tradeRequest = TradeTransformer.transform(existingTrade);
		tradeRequest.setName(tradeRequest.getName() + " - Trade.name after PUT");
		TradeJson tradeResponse = fixture.put(tradeRequest.getTradeId(), tradeRequest);
		assertEquals(tradeRequest.getName(), tradeResponse.getName());
	}

	@Test(expected=RestException.class)
	public void putNotFound() {
		// Try to PUT a trade that does not exist
		TradeJson tradePutRequest = TradeRandom.nextJson();
		fixture.put(-1, tradePutRequest);
	}
	
	@Test(expected=RestException.class)
	public void putNotTradeOwner() {
		TradeEntity existingTrade = tradeRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		TradeJson tradeRequest = TradeTransformer.transform(existingTrade);
		mockControllerFactory.getTradeController(false);
		fixture.put(tradeRequest.getTradeId(), tradeRequest);
	}

	@Test(expected=RestException.class)
	public void putDuplicatedName() {
		TradeEntity existingTrade = tradeRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		TradeEntity anotherExistingTrade = tradeRandom.nextPersistedEntity(fixture.authenticationProvider.getAuthentication().getUser());
		TradeJson request = new TradeJson();
		request.setName(existingTrade.getName());
		request.setState(TradeJson.State.SUBMITTING_ITEMS);
		fixture.put(anotherExistingTrade.getTradeId(), request);
	}

}
