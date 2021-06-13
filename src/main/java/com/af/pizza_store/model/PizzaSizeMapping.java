package com.af.pizza_store.model;

import java.math.BigDecimal;
import javax.persistence.Transient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "PizzaSizeMapping")
public class PizzaSizeMapping {

	@Id
	private Integer id;
	
	@JsonIgnore
	private Pizza pizzas;
	
	@Transient
    private Integer pizzaId;
	
	@Transient
    private String pizzaName;
	
	@Transient
    private String pizzaImage;
	
	@JsonIgnore
	private PizzaSize pizzaSizes;
	
	@Transient
    private Integer pizzaSizeId;
	
	@Transient
    private String pizzaSizeName;
	
	private BigDecimal price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pizza getPizzas() {
		return pizzas;
	}

	public void setPizzas(Pizza pizzas) {
		this.pizzas = pizzas;
	}

	public PizzaSize getPizzaSizes() {
		return pizzaSizes;
	}

	public void setPizzaSizes(PizzaSize pizzaSizes) {
		this.pizzaSizes = pizzaSizes;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getPizzaId() {
		if(pizzas != null) {
			return pizzas.getId();
		} else {
			return null;
		}
	}

	public String getPizzaName() {
		if(pizzas != null) {
			return pizzas.getName();
		} else {
			return null;
		}
	}
	
	public String getPizzaImage() {
		if(pizzas != null) {
			return pizzas.getImage();
		} else {
			return null;
		}
	}

	public Integer getPizzaSizeId() {
		if(pizzaSizes != null) {
			return pizzaSizes.getId();
		} else {
			return null;
		}
	}

	public String getPizzaSizeName() {
		if(pizzaSizes != null) {
			return pizzaSizes.getName();
		} else {
			return null;
		}
	}
	
}
