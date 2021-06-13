package com.af.pizza_store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.af.pizza_store.model.PizzaSize;
import com.af.pizza_store.resource.PizzaSizeAddResource;
import com.af.pizza_store.resource.PizzaSizeUpdateResource;

@Service
public interface PizzaSizeService {

	public List<PizzaSize> findAll();
	
	public Optional<PizzaSize> findById(int id);
	
	public Integer savePizzaSize(PizzaSizeAddResource pizzaSizeAddResource);
	
	public PizzaSize updatePizzaSize(int id, PizzaSizeUpdateResource pizzaSizeUpdateResource);
	
	public String deletePizzaSize(int id);
}