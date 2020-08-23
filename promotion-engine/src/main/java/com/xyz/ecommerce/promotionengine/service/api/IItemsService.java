package com.xyz.ecommerce.promotionengine.service.api;

import java.util.List;

import com.xyz.ecommerce.promotionengine.model.Item;

public interface IItemsService {
	
	public Item addItem(Item item);
	
	public List<Item> findItems();
	
	public void removeItem(String itemId);

}
