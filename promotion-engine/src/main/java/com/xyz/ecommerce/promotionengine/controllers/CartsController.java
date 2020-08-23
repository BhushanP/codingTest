package com.xyz.ecommerce.promotionengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.ecommerce.promotionengine.model.Cart;
import com.xyz.ecommerce.promotionengine.model.CartItem;
import com.xyz.ecommerce.promotionengine.service.impl.CartServiceImpl;

@RestController
@RequestMapping("/xyz/ecommerce/cart")
public class CartsController {

	@Autowired
	CartServiceImpl cartServiceImpl;

	@RequestMapping(method = RequestMethod.POST, value = "/{cartId}")
	public @ResponseBody ResponseEntity<Cart> addItemToCart(@PathVariable Integer cartId, @RequestBody CartItem item) {
		Cart createdItem = cartServiceImpl.addItemToCart(cartId, item);
		return ResponseEntity.status(HttpStatus.OK).body(createdItem);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{cartId}")
	public @ResponseBody ResponseEntity viewCart(@PathVariable Integer cartId) {
		Cart cart = null;
		try {
			cart = cartServiceImpl.viewCart(cartId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart Not Found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(cart);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{cartId}/{itemId}")
	public @ResponseBody ResponseEntity deleteCart(@PathVariable Integer cartId, @PathVariable String itemId) {
		Cart updateCart = null;
		try {
			updateCart = cartServiceImpl.deleteItemFromCart(cartId, itemId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart is Empty");
		}
		return ResponseEntity.status(HttpStatus.OK).body(updateCart);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{cartId}/{promoCode}")
	public @ResponseBody ResponseEntity applyPromocodeOnCart(@PathVariable Integer cartId,
			@PathVariable String promoCode) throws Exception {
		Cart createdItem = null;
		try {
			createdItem = cartServiceImpl.applyPromoCodeOnCart(cartId, promoCode);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(createdItem);

	}
}
