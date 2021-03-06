package com.matchandtrade.rest.v1.transformer;

import java.util.ArrayList;
import java.util.List;

import com.matchandtrade.persistence.common.SearchResult;
import com.matchandtrade.persistence.entity.TradeEntity;
import com.matchandtrade.persistence.entity.TradeEntity.State;
import com.matchandtrade.rest.v1.json.TradeJson;

public class TradeTransformer {

	private TradeTransformer() {}

	private static State buildState(TradeJson.State state) {
		if (state == null) {
			return null;
		}
		TradeEntity.State result = null;
		switch (state) {
		case SUBMITTING_ITEMS:
			result = TradeEntity.State.SUBMITTING_ITEMS;
			break;
		case MATCHING_ITEMS:
			result = TradeEntity.State.MATCHING_ITEMS;
			break;
		case MATCHING_ITEMS_ENDED:
			result = TradeEntity.State.MATCHING_ITEMS_ENDED;
			break;
		case GENERATING_TRADES:
			result = TradeEntity.State.GENERATING_TRADES;
			break;
		case GENERATING_TRADES_ENDED:
			result = TradeEntity.State.GENERATING_TRADES_ENDED;
			break;
		case CANCELED:
			result = TradeEntity.State.CANCELED;
			break;
		default:
			break;
		}
		return result;
	}

	private static TradeJson.State buildState(State state) {
		if (state == null) {
			return null;
		}
		TradeJson.State result = null;
		switch (state) {
		case SUBMITTING_ITEMS:
			result = TradeJson.State.SUBMITTING_ITEMS;
			break;
		case MATCHING_ITEMS:
			result = TradeJson.State.MATCHING_ITEMS;
			break;
		case MATCHING_ITEMS_ENDED:
			result = TradeJson.State.MATCHING_ITEMS_ENDED;
			break;
		case GENERATING_TRADES:
			result = TradeJson.State.GENERATING_TRADES;
			break;
		case GENERATING_TRADES_ENDED:
			result = TradeJson.State.GENERATING_TRADES_ENDED;
			break;
		case CANCELED:
			result = TradeJson.State.CANCELED;
			break;
		default:
			break;
		}
		return result;
	}
	
	public static TradeEntity transform(TradeJson json) {
		TradeEntity result;
		result = new TradeEntity();
		result.setName(json.getName());
		result.setTradeId(json.getTradeId());
		result.setState(buildState(json.getState()));
		return result;
	}

	public static TradeJson transform(TradeEntity entity) {
		if (entity == null) {
			return null;
		}
		TradeJson result = new TradeJson();
		result.setName(entity.getName());
		result.setTradeId(entity.getTradeId());
		result.setState(buildState(entity.getState()));
		return result;
	}
	

	public static SearchResult<TradeJson> transform(SearchResult<TradeEntity> searchResult) {
		List<TradeJson> resultList = new ArrayList<>();
		for(TradeEntity e : searchResult.getResultList()) {
			resultList.add(transform(e));
		}
		return new SearchResult<>(resultList, searchResult.getPagination());
	}

}
