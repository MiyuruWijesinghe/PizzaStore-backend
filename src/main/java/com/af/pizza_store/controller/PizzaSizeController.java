package com.af.pizza_store.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.af.pizza_store.model.PizzaSize;
import com.af.pizza_store.resource.PizzaSizeAddResource;
import com.af.pizza_store.resource.PizzaSizeUpdateResource;
import com.af.pizza_store.resource.SuccessAndErrorDetailsResource;
import com.af.pizza_store.service.PizzaSizeService;

@RestController
@RequestMapping(value = "/size")
@CrossOrigin(origins = "*")
public class PizzaSizeController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private PizzaSizeService pizzaSizeService;
	
	
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllPizzaSizes() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<PizzaSize> pizzaSize = pizzaSizeService.findAll();
		if (!pizzaSize.isEmpty()) {
			return new ResponseEntity<>((Collection<PizzaSize>) pizzaSize, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getPizzaSizeById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PizzaSize> isPresentPizzaSize = pizzaSizeService.findById(id);
		if (isPresentPizzaSize.isPresent()) {
			return new ResponseEntity<>(isPresentPizzaSize, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Object> addPizzaSize(@Valid @RequestBody PizzaSizeAddResource pizzaSizeAddResource) {
		Integer pizzaSizeId = pizzaSizeService.savePizzaSize(pizzaSizeAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), pizzaSizeId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updatePizzaSize(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody PizzaSizeUpdateResource pizzaSizeUpdateResource) {
		PizzaSize pizzaSize = pizzaSizeService.updatePizzaSize(id, pizzaSizeUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), pizzaSize);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deletePizzaSize(@PathVariable(value = "id", required = true) int id) {
		String message = pizzaSizeService.deletePizzaSize(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
}