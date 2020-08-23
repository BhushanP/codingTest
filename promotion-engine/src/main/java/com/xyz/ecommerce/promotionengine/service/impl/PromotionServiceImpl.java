package com.xyz.ecommerce.promotionengine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.ecommerce.promotionengine.model.Promotion;
import com.xyz.ecommerce.promotionengine.repos.PromotionsRepo;
import com.xyz.ecommerce.promotionengine.service.api.IPromotionService;

@Service
public class PromotionServiceImpl implements IPromotionService{
	
	@Autowired
	PromotionsRepo promotionsRepo; 

	@Override
	public Promotion addPromotion(Promotion promotion) {
		return promotionsRepo.save(promotion);
	}

	@Override
	public void deletePromotion(String promoCode) {
		promotionsRepo.deleteById(promoCode);
	}
	
	@Override
	public void deletePromotions() {
		promotionsRepo.deleteAll();
	}

	@Override
	public List<Promotion> viewPromotions() {
		return promotionsRepo.findAll();
	}

}
