package com.xyz.ecommerce.promotionengine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyz.ecommerce.promotionengine.model.Cart;

public interface CartsRepo extends JpaRepository<Cart, Integer>{

}
