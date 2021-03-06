package com.matchandtrade.rest.v1.transformer;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

import com.matchandtrade.persistence.common.Criterion;
import com.matchandtrade.persistence.common.Criterion.LogicalOperator;
import com.matchandtrade.persistence.common.Field;
import com.matchandtrade.persistence.common.Pagination;
import com.matchandtrade.persistence.common.SearchCriteria;
import com.matchandtrade.persistence.common.SearchResult;
import com.matchandtrade.persistence.criteria.ItemRecipeQueryBuilder;
import com.matchandtrade.persistence.dto.ItemAndTradeMembershipIdDto;
import com.matchandtrade.persistence.entity.Entity;
import com.matchandtrade.rest.Json;
import com.matchandtrade.rest.v1.json.search.Matcher;
import com.matchandtrade.rest.v1.json.search.Operator;
import com.matchandtrade.rest.v1.json.search.Recipe;
import com.matchandtrade.rest.v1.json.search.SearchCriteriaJson;

public class SearchTransformer {

	public static SearchResult<Json> transform(SearchResult<Entity> searchResult, Recipe recipe) {
		List<Json> resultList = searchResult.getResultList().stream()
			.map(entity -> {
				if (Recipe.ITEMS == recipe) {
					ItemAndTradeMembershipIdDto tradeMembershipAndItemDto = (ItemAndTradeMembershipIdDto) entity;
					return ItemTransformer.transform(tradeMembershipAndItemDto.getItem());
				} else {
					throw new InvalidParameterException("Unsupported recipe: " + recipe);
				}
			})
			.collect(Collectors.toList());
		return new SearchResult<>(resultList, searchResult.getPagination());
	}

	public static SearchCriteria transform(SearchCriteriaJson request, Pagination pagination) {
		if (Recipe.ITEMS == request.getRecipe()) {
			return transformItemsRecipe(request, pagination);
		} else {
			throw new InvalidParameterException("Unable to transform SearchCriteria with recipe: " + request.getRecipe());
		}
	}
	
	private static Criterion transformCriterion(Field field, Object value, Operator operator, Matcher matcher) {
		// Transform operator
		LogicalOperator persistenceOperator = LogicalOperator.AND;
		if (operator == Operator.OR) {
			persistenceOperator = LogicalOperator.OR;
		}
		
		// Transform matcher
		Criterion.Restriction persistanceRestriction; 
		switch (matcher) {
		case NOT_EQUALS:
			persistanceRestriction = Criterion.Restriction.NOT_EQUALS;
			break;
		case EQUALS_IGNORE_CASE:
			persistanceRestriction = Criterion.Restriction.EQUALS_IGNORE_CASE;
			break;
		case LIKE_IGNORE_CASE:
			persistanceRestriction = Criterion.Restriction.LIKE_IGNORE_CASE;
			break;
		default:
			persistanceRestriction = Criterion.Restriction.EQUALS;
			break;
		}
		
		return new Criterion(field, value, persistenceOperator, persistanceRestriction);
	}
	
	private static SearchCriteria transformItemsRecipe(SearchCriteriaJson request, Pagination pagination) {
		SearchCriteria result = new SearchCriteria(pagination);
		request.getCriteria().forEach(entry -> {
			if ("Trade.tradeId".equals(entry.getKey())) {
				result.getCriteria().add(transformCriterion(ItemRecipeQueryBuilder.Field.TRADE_ID, entry.getValue(), entry.getOperator(), entry.getMatcher()));
			}
			if ("TradeMembership.tradeMembershipId".equals(entry.getKey())) {
				result.getCriteria().add(transformCriterion(ItemRecipeQueryBuilder.Field.TRADE_MEMBERSHIP_ID, entry.getValue(), entry.getOperator(), entry.getMatcher()));
			}
		});
		return result;
	}

	private SearchTransformer() {}
	
}
