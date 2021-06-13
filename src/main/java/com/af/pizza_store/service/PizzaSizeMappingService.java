package com.af.pizza_store.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.af.pizza_store.model.PizzaSizeMapping;
import com.af.pizza_store.resource.PizzaSizeMappingUpdateResource;

@Service
public interface PizzaSizeMappingService {

	public List<PizzaSizeMapping> findAll();

	public Optional<PizzaSizeMapping> findById(int id);
	
	public Optional<PizzaSizeMapping> findByPizzaIdAndPizzaSizeId(int pizzaId, int pizzaSizeId);
	
	public List<PizzaSizeMapping> findByPizzaId(int pizzaId);
	
	public List<PizzaSizeMapping> findByPizzaSizeId(int pizzaSizeId);

	public PizzaSizeMapping updatePizzaSizeMapping(int id, PizzaSizeMappingUpdateResource pizzaSizeMappingUpdateResource);
}
