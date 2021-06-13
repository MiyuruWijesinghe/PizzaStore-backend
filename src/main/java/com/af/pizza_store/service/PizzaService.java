package com.af.pizza_store.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.af.pizza_store.model.Pizza;
import com.af.pizza_store.resource.PizzaAddResource;
import com.af.pizza_store.resource.PizzaUpdateResource;

@Service
public interface PizzaService {

	public List<Pizza> findAll();
	
	public Optional<Pizza> findById(int id);
	
	public Integer savePizza(PizzaAddResource pizzaAddResource);
	
	public Pizza updatePizza(int id, PizzaUpdateResource pizzaUpdateResource);
	
	public String deletePizza(int id);
}
