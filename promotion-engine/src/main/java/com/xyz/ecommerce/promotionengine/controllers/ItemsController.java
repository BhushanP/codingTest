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

import com.xyz.ecommerce.promotionengine.model.Item;
import com.xyz.ecommerce.promotionengine.service.impl.ItemsServiceImpl;

@RestController
@RequestMapping("/xyz/ecommerce")
public class ItemsController {

	@Autowired
	ItemsServiceImpl itemsService;

	@RequestMapping(method = RequestMethod.POST, value = "/item")
	public @ResponseBody ResponseEntity<Item> addItem(@RequestBody Item item) {
		Item createdItem = itemsService.addItem(item);
		return ResponseEntity.status(HttpStatus.OK).body(createdItem);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/item")
	public List<Item> findItems() {
		return itemsService.findItems();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/item/{itemId}")
	public @ResponseBody ResponseEntity<String> deleteItem(@PathVariable String itemId) {
		try {
			itemsService.removeItem(itemId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
