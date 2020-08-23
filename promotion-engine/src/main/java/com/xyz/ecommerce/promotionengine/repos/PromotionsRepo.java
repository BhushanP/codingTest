package com.xyz.ecommerce.promotionengine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyz.ecommerce.promotionengine.model.Promotion;

public interface PromotionsRepo extends JpaRepository<Promotion, String> {

}
