package com.matchandtrade.rest;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON class which extends <i>RestResponseInterceptor</i> and provide support to Spring HATEOAS. 
 * 
 * @author rafael.santos.bra@gmail.com
 * @see com.matchandtrade.config.RestResponseInterceptor;
 */
public abstract class JsonLinkSupport extends ResourceSupport implements Json {
	
	public abstract void buildLinks();
	
	@JsonProperty("_links")
	@Override
	public List<Link> getLinks() {
		return super.getLinks();
	}
	
}