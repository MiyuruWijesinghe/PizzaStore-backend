package com.af.pizza_store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.af.pizza_store.enums.CommonStatus;
import com.af.pizza_store.exception.NoRecordFoundException;
import com.af.pizza_store.model.PizzaSize;
import com.af.pizza_store.repository.PizzaSizeRepository;
import com.af.pizza_store.resource.PizzaSizeAddResource;
import com.af.pizza_store.resource.PizzaSizeUpdateResource;
import com.af.pizza_store.service.PizzaSizeService;
import com.af.pizza_store.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class PizzaSizeServiceImpl implements PizzaSizeService {

	
	@Autowired
	private Environment environment;
	
	@Autowired
	private PizzaSizeRepository pizzaSizeRepository;
	
	
	private int generateId() {
		List<PizzaSize> pizzaSizeList = pizzaSizeRepository.findAll();
		List<Integer> pizzaSizeIdList = new ArrayList<>();
		
		for(PizzaSize pizzaSizeObject : pizzaSizeList) {
			pizzaSizeIdList.add(pizzaSizeObject.getId());
		}
		
		return IdGenerator.generateIDs(pizzaSizeIdList);	
	}
	
	@Override
	public List<PizzaSize> findAll() {
		return pizzaSizeRepository.findAll();
	}

	@Override
	public Optional<PizzaSize> findById(int id) {
		Optional<PizzaSize> pizzaSize = pizzaSizeRepository.findById(id);
		if (pizzaSize.isPresent()) {
			return Optional.ofNullable(pizzaSize.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Integer savePizzaSize(PizzaSizeAddResource pizzaSizeAddResource) {
		PizzaSize pizzaSize = new PizzaSize();
		pizzaSize.setId(generateId());
		pizzaSize.setName(pizzaSizeAddResource.getName());
		pizzaSize.setStatus(CommonStatus.ACTIVE.toString());
		pizzaSizeRepository.save(pizzaSize);
		return pizzaSize.getId();
	}

	@Override
	public PizzaSize updatePizzaSize(int id, PizzaSizeUpdateResource pizzaSizeUpdateResource) {
		Optional<PizzaSize> isPresentPizzaSize = pizzaSizeRepository.findById(id);
		if (!isPresentPizzaSize.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		PizzaSize pizzaSize = isPresentPizzaSize.get();
		pizzaSize.setStatus(pizzaSizeUpdateResource.getStatus());
		pizzaSizeRepository.save(pizzaSize);
		return pizzaSize;
	}

	@Override
	public String deletePizzaSize(int id) {
		Optional<PizzaSize> isPresentPizzaSize = pizzaSizeRepository.findById(id);
		if (!isPresentPizzaSize.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		pizzaSizeRepository.deleteById(id);
		return environment.getProperty("pizza-size.deleted");
	}

}
