package com.af.pizza_store.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.af.pizza_store.model.PizzaSizeMapping;

@Repository
public interface PizzaSizeMappingRepository extends MongoRepository<PizzaSizeMapping, Integer> {

	public Optional<PizzaSizeMapping> findByPizzasIdAndPizzaSizesId(int pizzaId, int pizzaSizeId);

	public List<PizzaSizeMapping> findByPizzasId(int pizzaId);

	public List<PizzaSizeMapping> findByPizzaSizesId(int pizzaSizeId);
	
}