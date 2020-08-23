package com.xyz.ecommerce.promotionengine.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.ecommerce.promotionengine.model.Cart;
import com.xyz.ecommerce.promotionengine.model.CartItem;
import com.xyz.ecommerce.promotionengine.model.Item;
import com.xyz.ecommerce.promotionengine.model.Promotion;
import com.xyz.ecommerce.promotionengine.repos.CartsRepo;
import com.xyz.ecommerce.promotionengine.repos.ItemsRepo;
import com.xyz.ecommerce.promotionengine.repos.PromotionsRepo;
import com.xyz.ecommerce.promotionengine.service.api.ICartService;

@Service
public class CartServiceImpl implements ICartService {

	private static final String FIXED = "fixed";

	private static final String PERCENTAGE = "percentage";

	@Autowired
	CartsRepo cartsRepo;

	@Autowired
	ItemsRepo itemssRepo;

	@Autowired
	PromotionsRepo promotionsRepo;

	@Override
	public Cart addItemToCart(Integer cartId, CartItem item) {
		boolean itemExists = false;
		List<CartItem> items = new <CartItem>ArrayList();
		Optional<Cart> opt = cartsRepo.findById(cartId);
		Cart cart = null;
		if (opt.isPresent()) {
			cart = opt.get();
			items = cart.getItems();
		} else
			cart = new Cart();
		for (CartItem cartItem : items) {
			if (cartItem.getItem().getId().equalsIgnoreCase(item.getItem().getId())) {
				cartItem.getItem().setPrice(item.getItem().getPrice());
				itemExists = true;
			}
		}
		if (!itemExists) {
			items.add(item);
		}
		cart.setItems(items);
		float cartValue = 0;
		for (CartItem cartItem : items) {
			cartValue += cartItem.getItem().getPrice() * cartItem.getQuantity();
		}
		cart.setCartValue(cartValue);
		return cartsRepo.save(cart);
	}

	@Override
	public Cart deleteItemFromCart(Integer cartId, String itemId) throws Exception {
		Optional<Cart> opt = cartsRepo.findById(cartId);
		Cart cart = null;
		if (opt.isPresent()) {
			cart = opt.get();
		} else
			throw new Exception("Cart Is Empty");

		List<CartItem> items = cart.getItems();
		items.removeIf(i -> i.getItem().getId().equalsIgnoreCase(itemId));
		cart.setItems(items);
		float cartValue = 0;
		for (CartItem cartItem : items) {
			cartValue += cartItem.getItem().getPrice() * cartItem.getQuantity();
		}
		cart.setCartValue(cartValue);
		return cartsRepo.save(cart);
	}

	@Override
	public Cart viewCart(Integer cartId) throws Exception {
		Optional<Cart> opt = cartsRepo.findById(cartId);
		if (opt.isPresent()) {
			return opt.get();
		} else
			throw new Exception("Cart Is Empty");
	}

	@Override
	public Cart applyPromoCodeOnCart(Integer cartId, String promoCode) throws Exception {
		Optional<Cart> optCart = cartsRepo.findById(cartId);
		Cart cart = null;
		if (optCart.isPresent()) {
			cart = optCart.get();
		} else
			throw new Exception("Cart Is Empty");

		Optional<Promotion> optPromo = promotionsRepo.findById(promoCode);
		Promotion promotion = null;
		if (optPromo.isPresent()) {
			promotion = optPromo.get();
		} else
			throw new Exception("PromoCode Expired");

		String[] itemsOnDiscountWithUnits = promotion.getCodeCriteria().split(":");
		float currentValue = cart.getCartValue();

		if (FIXED.equalsIgnoreCase(promotion.getDiscountType()) && promotion.getDiscountPrice() > 0) {
			if (itemsOnDiscountWithUnits.length == 2)
				currentValue = cartValueAfterFixedDiscountOnSingleItem(cart, promotion, itemsOnDiscountWithUnits,
						currentValue);
			else
				currentValue = cartValueAfterFixedDiscountOnGroupedItems(cart, promotion, itemsOnDiscountWithUnits,
						currentValue);

		} else if (PERCENTAGE.equalsIgnoreCase(promotion.getDiscountType()) && promotion.getDiscountPrice() > 0) {
			currentValue = cartValueAfterPercentageDiscount(cart, promotion, itemsOnDiscountWithUnits, currentValue);
		}
		cart.setCartValue(currentValue);
		return cart;
	}

	private float cartValueAfterFixedDiscountOnSingleItem(Cart cart, Promotion promotion,
			String[] itemsOnDiscountWithUnits, float currentValue) throws Exception {
		for (CartItem item : cart.getItems()) {
			String itemOnDiscount = itemsOnDiscountWithUnits[0];
			int itemUnitsPresentInCart = item.getQuantity();
			int minUnitstoAvailDiscount = Integer.valueOf(itemsOnDiscountWithUnits[1]);
			if (item.getItem().getId().equals(itemOnDiscount) && itemUnitsPresentInCart >= minUnitstoAvailDiscount) {
				while (itemUnitsPresentInCart >= minUnitstoAvailDiscount) {
					float actualPrice = item.getItem().getPrice() * minUnitstoAvailDiscount;
					cart.setCartValue((currentValue - actualPrice) + promotion.getDiscountPrice());
					currentValue = cart.getCartValue();
					itemUnitsPresentInCart -= minUnitstoAvailDiscount;
				}
			} else
				throw new Exception("Promocode is not applicable for your cart");
		}
		return currentValue;
	}

	private float cartValueAfterFixedDiscountOnGroupedItems(Cart cart, Promotion promotion,
			String[] itemsOnDiscountWithUnits, float currentValue) throws Exception {
		int countValidItems = 0;
		for (int i = 0; i < itemsOnDiscountWithUnits.length; i = i + 2) {
			for (CartItem item : cart.getItems()) {
				String itemOnDiscount = itemsOnDiscountWithUnits[i];
				int itemUnitsPresentInCart = item.getQuantity();
				int minUnitstoAvailDiscount = Integer.valueOf(itemsOnDiscountWithUnits[i + 1]);
				if (item.getItem().getId().equals(itemOnDiscount)
						&& itemUnitsPresentInCart >= minUnitstoAvailDiscount) {
					countValidItems++;
					float actualPrice = item.getItem().getPrice() * minUnitstoAvailDiscount;
					cart.setCartValue(currentValue - actualPrice);
					currentValue = cart.getCartValue();
				}

			}
		}
		if (countValidItems != itemsOnDiscountWithUnits.length / 2) {
			throw new Exception("Promocode is not applicable for your cart");
		}
		return currentValue + promotion.getDiscountPrice();
	}

	private float cartValueAfterPercentageDiscount(Cart cart, Promotion promotion, String[] itemsOnDiscountWithUnits,
			float currentValue) throws Exception {
		int countValidItems = 0;
		for (int i = 0; i < itemsOnDiscountWithUnits.length; i = i + 2) {
			for (CartItem item : cart.getItems()) {
				String itemOnDiscount = itemsOnDiscountWithUnits[i];
				int itemUnitsPresentInCart = item.getQuantity();
				int minUnitstoAvailDiscount = Integer.valueOf(itemsOnDiscountWithUnits[i + 1]);
				if (item.getItem().getId().equals(itemOnDiscount)
						&& itemUnitsPresentInCart >= minUnitstoAvailDiscount) {
					countValidItems++;
					while (itemUnitsPresentInCart >= minUnitstoAvailDiscount) {
						cart.setCartValue(currentValue - (currentValue * promotion.getDiscountPercentage() / 100));
						currentValue = cart.getCartValue();
						itemUnitsPresentInCart -= minUnitstoAvailDiscount;
					}
				}
			}
		}
		if (countValidItems != itemsOnDiscountWithUnits.length / 2) {
			throw new Exception("Promocode is not applicable for your cart");
		}
		return currentValue;
	}

}
