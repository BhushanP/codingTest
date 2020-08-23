package com.xyz.ecommerce.promotionengine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.ecommerce.promotionengine.model.Promotion;
import com.xyz.ecommerce.promotionengine.service.impl.PromotionServiceImpl;

@RestController
@RequestMapping("/xyz/ecommerce")
public class PromotionsController {

	@Autowired
	PromotionServiceImpl promotionService;

	@RequestMapping(method = RequestMethod.POST, value = "/promotion")
	public @ResponseBody ResponseEntity addProduct(@RequestBody Promotion promotion) {
		if (promotion.getPromoCode() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Promocode is Mandatory");
		}

		if (promotion.getDiscountType() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DiscountType is Mandatory");
		} else if (!"fixed".equalsIgnoreCase(promotion.getDiscountType())
				&& !"percentage".equalsIgnoreCase(promotion.getDiscountType())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("DiscountType has to be either fixed or percentage only");
		}

		if (promotion.getCodeCriteria() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code Criteria is Mandatory");
		}

		if (promotion.getDiscountPrice() == 0 && promotion.getDiscountPercentage() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Either DiscountPercentage or DiscountPrice must be grater than 0");
		}
		Promotion createdPromotion = promotionService.addPromotion(promotion);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/promotion")
	public List<Promotion> findProducts() {
		return promotionService.viewPromotions();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/promotion/{promoCode}")
	public @ResponseBody ResponseEntity<String> deletePromotion(@PathVariable String promoCode) {
		try {
			promotionService.deletePromotion(promoCode);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PromoCode Not Found");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/promotion")
	public @ResponseBody ResponseEntity<String> deletePromotions() {
		try {
			promotionService.deletePromotions();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body("PromoCode Not Found");
	}

}
