package com.af.pizza_store.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.af.pizza_store.enums.CommonStatus;
import com.af.pizza_store.exception.NoRecordFoundException;
import com.af.pizza_store.model.Pizza;
import com.af.pizza_store.model.PizzaSize;
import com.af.pizza_store.model.PizzaSizeMapping;
import com.af.pizza_store.repository.PizzaRepository;
import com.af.pizza_store.repository.PizzaSizeMappingRepository;
import com.af.pizza_store.repository.PizzaSizeRepository;
import com.af.pizza_store.resource.PizzaAddResource;
import com.af.pizza_store.resource.PizzaSizeMappingAddResource;
import com.af.pizza_store.resource.PizzaUpdateResource;
import com.af.pizza_store.service.PizzaService;
import com.af.pizza_store.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class PizzaServiceImpl implements PizzaService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private PizzaRepository pizzaRepository;
	
	@Autowired
	private PizzaSizeRepository pizzaSizeRepository;
	
	@Autowired
	private PizzaSizeMappingRepository pizzaSizeMappingRepository;
	
	private int generatePizzaId() {
		List<Pizza> pizzaList = pizzaRepository.findAll();
		List<Integer> pizzaIdList = new ArrayList<>();
		
		for(Pizza pizzaObject : pizzaList) {
			pizzaIdList.add(pizzaObject.getId());
		}
		
		return IdGenerator.generateIDs(pizzaIdList);	
	}
	
	private int generatePizzaSizeMappingId() {
		List<PizzaSizeMapping> pizzaSizeList = pizzaSizeMappingRepository.findAll();
		List<Integer> pizzaSizeIdList = new ArrayList<>();
		
		for(PizzaSizeMapping pizzaSizeObject : pizzaSizeList) {
			pizzaSizeIdList.add(pizzaSizeObject.getId());
		}
		
		return IdGenerator.generateIDs(pizzaSizeIdList);	
	}
	
	@Override
	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}

	@Override
	public Optional<Pizza> findById(int id) {
		Optional<Pizza> pizza = pizzaRepository.findById(id);
		if (pizza.isPresent()) {
			return Optional.ofNullable(pizza.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Integer savePizza(PizzaAddResource pizzaAddResource) {
		Pizza pizza = new Pizza();
		pizza.setId(generatePizzaId());
		pizza.setName(pizzaAddResource.getName());
		pizza.setDescription(pizzaAddResource.getDescription());
		pizza.setImage(pizzaAddResource.getImage());
		pizza.setStatus(CommonStatus.ACTIVE.toString());
		pizzaRepository.save(pizza);
		
		if(pizzaAddResource.getSizes() !=null && !pizzaAddResource.getSizes().isEmpty()) {
			
			for (PizzaSizeMappingAddResource pizzaSizeMappingAddResource : pizzaAddResource.getSizes()) {
				
				PizzaSizeMapping pizzaSizeMapping = new PizzaSizeMapping();
				pizzaSizeMapping.setId(generatePizzaSizeMappingId());
				pizzaSizeMapping.setPizzas(pizza);
				
				Optional<PizzaSize> isPresentPizzaSize = pizzaSizeRepository.findById(Integer.parseInt(pizzaSizeMappingAddResource.getSizeId()));
				if (isPresentPizzaSize.isPresent()) {
					pizzaSizeMapping.setPizzaSizes(isPresentPizzaSize.get());
				}
				pizzaSizeMapping.setPrice(BigDecimal.ZERO);
				pizzaSizeMappingRepository.save(pizzaSizeMapping);
			}
		}
		
		return pizza.getId();
	}

	@Override
	public Pizza updatePizza(int id, PizzaUpdateResource pizzaUpdateResource) {
		Optional<Pizza> isPresentPizza = pizzaRepository.findById(id);
		if (!isPresentPizza.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Pizza pizza = isPresentPizza.get();
		pizza.setDescription(pizzaUpdateResource.getDescription());
		pizza.setImage(pizzaUpdateResource.getImage());
		pizza.setStatus(pizzaUpdateResource.getStatus());
		pizzaRepository.save(pizza);
		return pizza;
	}

	@Override
	public String deletePizza(int id) {
		Optional<Pizza> isPresentPizza = pizzaRepository.findById(id);
		if (!isPresentPizza.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		pizzaRepository.deleteById(id);
		return environment.getProperty("pizza.deleted");
	}

}
