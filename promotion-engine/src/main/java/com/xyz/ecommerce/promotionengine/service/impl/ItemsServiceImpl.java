package com.xyz.ecommerce.promotionengine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.ecommerce.promotionengine.model.Item;
import com.xyz.ecommerce.promotionengine.repos.ItemsRepo;
import com.xyz.ecommerce.promotionengine.service.api.IItemsService;

@Service
public class ItemsServiceImpl implements IItemsService{
	
	@Autowired
	ItemsRepo itemsRepo;

	@Override
	public Item addItem(Item item) {
		return itemsRepo.save(item);		
	}

	@Override
	public List<Item> findItems() {
		return itemsRepo.findAll();
		
	}

	@Override
	public void removeItem(String itemId) {
		itemsRepo.deleteById(itemId);
	}

}
