package com.af.pizza_store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.af.pizza_store.model.PizzaSize;

@Repository
public interface PizzaSizeRepository extends MongoRepository<PizzaSize, Integer> {

}