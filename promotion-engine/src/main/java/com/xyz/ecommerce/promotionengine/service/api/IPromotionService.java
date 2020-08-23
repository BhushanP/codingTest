package com.xyz.ecommerce.promotionengine.service.api;

import java.util.List;

import com.xyz.ecommerce.promotionengine.model.Promotion;

public interface IPromotionService {
	
	public Promotion addPromotion(Promotion promotion);
		
	public void deletePromotion(String promoCode);

	public void deletePromotions();
	
	public List<Promotion> viewPromotions();

}
