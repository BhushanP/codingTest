package com.xyz.ecommerce.promotionengine.service.api;

import com.xyz.ecommerce.promotionengine.model.Cart;
import com.xyz.ecommerce.promotionengine.model.CartItem;

public interface ICartService {
	
	public Cart addItemToCart(Integer cartId,CartItem item);
	
	public Cart deleteItemFromCart(Integer cartId, String itemID) throws Exception;

	public Cart viewCart(Integer cartID) throws Exception;

	public Cart applyPromoCodeOnCart(Integer cartId, String promoCode) throws Exception;

}
