package com.af.pizza_store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.af.pizza_store.model.Pizza;

@Repository
public interface PizzaRepository extends MongoRepository<Pizza, Integer> {

}
