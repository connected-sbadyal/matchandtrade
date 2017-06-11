package com.matchandtrade.persistence.common;

/**
 * A criterion, typically used with {@code SearchCriteria}
 * 
 * @author rafael.santos.bra@gmail.com
 *
 */
public class Criterion {

	public enum Restriction {EQUALS, NOT_EQUALS, EQUALS_IGNORE_CASE, LIKE_IGNORE_CASE}
	public enum LogicalOperator {AND, OR}
	
	private Object field;
	private LogicalOperator logicalOperator;
	private Restriction restriction;
	private Object value;
	
	public Criterion(Object field, Object value) {
		this.field = field;
		this.value = value;
		this.logicalOperator = LogicalOperator.AND;
		this.restriction = Restriction.EQUALS;
	}

	public Criterion(Object field, Object value, Restriction restriction) {
		this.field = field;
		this.value = value;
		this.logicalOperator = LogicalOperator.AND;
		this.restriction = restriction;
	}

	public Object getField() {
		return field;
	}
	
	public LogicalOperator getLogicalOperator() {
		return logicalOperator;
	}
	
	public Restriction getRestriction() {
		return restriction;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((logicalOperator == null) ? 0 : logicalOperator.hashCode());
		result = prime * result + ((restriction == null) ? 0 : restriction.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Criterion other = (Criterion) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (logicalOperator != other.logicalOperator)
			return false;
		if (restriction != other.restriction)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
