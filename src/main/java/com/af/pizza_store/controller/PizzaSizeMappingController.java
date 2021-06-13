package com.af.pizza_store.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.af.pizza_store.model.PizzaSizeMapping;
import com.af.pizza_store.resource.PizzaSizeMappingUpdateResource;
import com.af.pizza_store.resource.SuccessAndErrorDetailsResource;
import com.af.pizza_store.service.PizzaSizeMappingService;

@RestController
@RequestMapping(value = "/pizza-size")
@CrossOrigin(origins = "*")
public class PizzaSizeMappingController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private PizzaSizeMappingService pizzaSizeMappingService;
	
	
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAllPizzaSizeMappings() {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<PizzaSizeMapping> pizzaSizeMapping = pizzaSizeMappingService.findAll();
		if (!pizzaSizeMapping.isEmpty()) {
			return new ResponseEntity<>((Collection<PizzaSizeMapping>) pizzaSizeMapping, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getPizzaSizeMappingById(@PathVariable(value = "id", required = true) int id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PizzaSizeMapping> isPresentPizzaSizeMapping = pizzaSizeMappingService.findById(id);
		if (isPresentPizzaSizeMapping.isPresent()) {
			return new ResponseEntity<>(isPresentPizzaSizeMapping, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/pizza/{pizzaId}")
	public ResponseEntity<Object> getPizzaSizeMappingByPizzaId(@PathVariable(value = "pizzaId", required = true) int pizzaId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<PizzaSizeMapping> pizzaSizeMapping = pizzaSizeMappingService.findByPizzaId(pizzaId);
		if (!pizzaSizeMapping.isEmpty()) {
			return new ResponseEntity<>((Collection<PizzaSizeMapping>) pizzaSizeMapping, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/size/{pizzaSizeId}")
	public ResponseEntity<Object> getPizzaSizeMappingBySizeId(@PathVariable(value = "pizzaSizeId", required = true) int pizzaSizeId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<PizzaSizeMapping> pizzaSizeMapping = pizzaSizeMappingService.findByPizzaSizeId(pizzaSizeId);
		if (!pizzaSizeMapping.isEmpty()) {
			return new ResponseEntity<>((Collection<PizzaSizeMapping>) pizzaSizeMapping, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/pizza/{pizzaId}/size/{pizzaSizeId}")
	public ResponseEntity<Object> getPizzaSizeMappingByPizzaIdAndPizzaSizeId(@PathVariable(value = "pizzaId", required = true) int pizzaId,
			@PathVariable(value = "pizzaSizeId", required = true) int pizzaSizeId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<PizzaSizeMapping> isPresentPizzaSizeMapping = pizzaSizeMappingService.findByPizzaIdAndPizzaSizeId(pizzaId, pizzaSizeId);
		if (isPresentPizzaSizeMapping.isPresent()) {
			return new ResponseEntity<>(isPresentPizzaSizeMapping, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updatePizzaSizeMapping(@PathVariable(value = "id", required = true) int id,
			@RequestBody PizzaSizeMappingUpdateResource pizzaSizeMappingUpdateResource) {
		PizzaSizeMapping pizzaSizeMapping = pizzaSizeMappingService.updatePizzaSizeMapping(id, pizzaSizeMappingUpdateResource);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource("Successfully Updated.", pizzaSizeMapping.getId().toString());
		return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deletePizzaSizeMapping(@PathVariable(value = "id", required = true) int id) {
		String message = pizzaSizeMappingService.deletePizzaSizeMapping(id);
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(message);
		return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
	}
}
