package com.xyz.ecommerce.promotionengine.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart {

	@Id
	@GeneratedValue
	private int id;

	private float cartValue;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<CartItem> items;

	public int getId() {
		return id;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public List<CartItem> getItems() {
		return items;
	}

	public float getCartValue() {
		return cartValue;
	}
		
	public void setId(int id) {
		this.id = id;
	}

	public void setCartValue(float cartValue) {
		this.cartValue = cartValue;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public Cart(int id, List<CartItem> items, float cartValue) {
		super();
		this.id = id;
		this.items = items;
		this.cartValue = cartValue;
	}

	public Cart() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + Float.floatToIntBits(cartValue);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (id != other.id)
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (Float.floatToIntBits(cartValue) != Float.floatToIntBits(other.cartValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", cartValue=" + cartValue + ", items=" + items + "]";
	}

}
