package com.af.pizza_store.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PizzaAddResource {
	
	@NotBlank(message = "{common.not-null}")
	private String name;
	
	private String description;
	
	private String image;
	
	@Valid
	private List<PizzaSizeMappingAddResource> sizes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<PizzaSizeMappingAddResource> getSizes() {
		return sizes;
	}

	public void setSizes(List<PizzaSizeMappingAddResource> sizes) {
		this.sizes = sizes;
	}
	
}