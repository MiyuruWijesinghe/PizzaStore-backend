package com.af.pizza_store.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.af.pizza_store.model.PizzaSizeMapping;
import com.af.pizza_store.repository.PizzaSizeMappingRepository;
import com.af.pizza_store.resource.PizzaSizeMappingUpdateResource;
import com.af.pizza_store.service.PizzaSizeMappingService;

@Component
@Transactional(rollbackFor=Exception.class)
public class PizzaSizeMappingServiceImpl implements PizzaSizeMappingService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private PizzaSizeMappingRepository pizzaSizeMappingRepository;
	
	@Override
	public List<PizzaSizeMapping> findAll() {
		return pizzaSizeMappingRepository.findAll();
	}

	@Override
	public Optional<PizzaSizeMapping> findById(int id) {
		Optional<PizzaSizeMapping> pizzaSizeMapping = pizzaSizeMappingRepository.findById(id);
		if (pizzaSizeMapping.isPresent()) {
			return Optional.ofNullable(pizzaSizeMapping.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<PizzaSizeMapping> findByPizzaIdAndPizzaSizeId(int pizzaId, int pizzaSizeId) {
		Optional<PizzaSizeMapping> pizzaSizeMapping = pizzaSizeMappingRepository.findByPizzasIdAndPizzaSizesId(pizzaId, pizzaSizeId);
		if (pizzaSizeMapping.isPresent()) {
			return Optional.ofNullable(pizzaSizeMapping.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<PizzaSizeMapping> findByPizzaId(int pizzaId) {
		return pizzaSizeMappingRepository.findByPizzasId(pizzaId);
	}

	@Override
	public List<PizzaSizeMapping> findByPizzaSizeId(int pizzaSizeId) {
		return pizzaSizeMappingRepository.findByPizzaSizesId(pizzaSizeId);
	}

	@Override
	public PizzaSizeMapping updatePizzaSizeMapping(int id, PizzaSizeMappingUpdateResource pizzaSizeMappingUpdateResource) {
		Optional<PizzaSizeMapping> isPresentPizzaSizeMapping = pizzaSizeMappingRepository.findById(id);
		if(isPresentPizzaSizeMapping.isPresent()) {
			PizzaSizeMapping pizzaSizeMapping = isPresentPizzaSizeMapping.get();
			pizzaSizeMapping.setPrice(new BigDecimal(pizzaSizeMappingUpdateResource.getPrice()));
			pizzaSizeMappingRepository.save(pizzaSizeMapping);
			return pizzaSizeMapping;
		}
		
		return null;
	}

}
