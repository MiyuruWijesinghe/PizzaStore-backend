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

import com.af.pizza_store.model.Pizza;
import com.af.pizza_store.resource.PizzaAddResource;
import com.af.pizza_store.resource.PizzaUpdateResource;
import com.af.pizza_store.resource.SuccessAndErrorDetailsResource;
import com.af.pizza_store.service.PizzaService;

@RestController
@RequestMapping(value = "/pizza")
@CrossOrigin(origins = "*")
public class PizzaController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private PizzaService pizzaService;
	
	
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllPizzas() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Pizza> pizza = pizzaService.findAll();
		if (!pizza.isEmpty()) {
			return new ResponseEntity<>((Collection<Pizza>) pizza, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getPizzaById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Pizza> isPresentPizza = pizzaService.findById(id);
		if (isPresentPizza.isPresent()) {
			return new ResponseEntity<>(isPresentPizza, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Object> addPizza(@Valid @RequestBody PizzaAddResource pizzaAddResource) {
		Integer pizzaId = pizzaService.savePizza(pizzaAddResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.saved"), pizzaId.toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updatePizza(@PathVariable(value = "id", required = true) int id,
			@Valid @RequestBody PizzaUpdateResource pizzaUpdateResource) {
		Pizza pizza = pizzaService.updatePizza(id, pizzaUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("common.updated"), pizza);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deletePizza(@PathVariable(value = "id", required = true) int id) {
		String message = pizzaService.deletePizza(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
}
