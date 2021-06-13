package com.af.pizza_store.core;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.af.pizza_store.exception.NoRecordFoundException;
import com.af.pizza_store.exception.ValidateRecordException;
import com.af.pizza_store.resource.PizzaAddResource;
import com.af.pizza_store.resource.PizzaSizeAddResource;
import com.af.pizza_store.resource.PizzaSizeMappingUpdateResource;
import com.af.pizza_store.resource.PizzaSizeUpdateResource;
import com.af.pizza_store.resource.PizzaUpdateResource;
import com.af.pizza_store.resource.SuccessAndErrorDetailsResource;
import com.af.pizza_store.resource.ValidateResource;


@RestControllerAdvice
public class RESTServiceExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private Environment environment;
	
	@ExceptionHandler({NoRecordFoundException.class})
	public ResponseEntity<Object> noRecordFoundException(NoRecordFoundException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.record-not-found"));
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({ValidateRecordException.class})
	public ResponseEntity<Object> validateRecordException(ValidateRecordException ex, WebRequest request) {
		try {
			ValidateResource typeValidation = new ValidateResource();		
			Class validationDetailClass = typeValidation.getClass();
			Field sField = validationDetailClass.getDeclaredField(ex.getField());
			sField.setAccessible(true);
			sField.set(typeValidation, ex.getMessage());		
			return new ResponseEntity<>(typeValidation, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			SuccessAndErrorDetailsResource successAndErrorDetailsDto = new SuccessAndErrorDetailsResource();
			successAndErrorDetailsDto.setMessages(environment.getProperty("common.internal-server-error"));
			successAndErrorDetailsDto.setDetails(e.getMessage());
			return new ResponseEntity<>(successAndErrorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		try {
			Field sField = null;
			String fieldName = null;
			Integer index = null;
			Integer index1 = null;
			Integer atPoint = null;
			String className = ex.getBindingResult().getObjectName();
			
			switch (className) {

			case "pizzaAddResource":
				PizzaAddResource pizzaAddResource = new PizzaAddResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = pizzaAddResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(pizzaAddResource.getClass().cast(pizzaAddResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(pizzaAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "pizzaUpdateResource":
				PizzaUpdateResource pizzaUpdateResource = new PizzaUpdateResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = pizzaUpdateResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(pizzaUpdateResource.getClass().cast(pizzaUpdateResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(pizzaUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "pizzaSizeAddResource":
				PizzaSizeAddResource pizzaSizeAddResource = new PizzaSizeAddResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = pizzaSizeAddResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(pizzaSizeAddResource.getClass().cast(pizzaSizeAddResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(pizzaSizeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "pizzaSizeUpdateResource":
				PizzaSizeUpdateResource pizzaSizeUpdateResource = new PizzaSizeUpdateResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = pizzaSizeUpdateResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(pizzaSizeUpdateResource.getClass().cast(pizzaSizeUpdateResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(pizzaSizeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "pizzaSizeMappingUpdateResource":
				PizzaSizeMappingUpdateResource pizzaSizeMappingUpdateResource = new PizzaSizeMappingUpdateResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = pizzaSizeMappingUpdateResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(pizzaSizeMappingUpdateResource.getClass().cast(pizzaSizeMappingUpdateResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(pizzaSizeMappingUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);	

			default:
				return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (Exception e) {
			SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
			successAndErrorDetailsResource.setMessages(environment.getProperty("common.internal-server-error"));
			successAndErrorDetailsResource.setDetails(e.getMessage());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
