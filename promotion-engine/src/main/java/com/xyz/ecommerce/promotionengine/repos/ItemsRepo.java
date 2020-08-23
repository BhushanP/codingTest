package com.xyz.ecommerce.promotionengine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyz.ecommerce.promotionengine.model.Item;

public interface ItemsRepo extends JpaRepository<Item, String>{

}
