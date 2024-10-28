package com.example.classicfashion.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.classicfashion.model.CartItem;

@Service
public class CartItemService {

	Map<String, CartItem> maps = new HashMap<>();

	private String createKey(Long productId, String color, String size) {
		return productId + "_" + color + "_" + size;
	}

	public void add(CartItem item) {
		String key = createKey(item.getProductID(), item.getColor(), item.getSize());
		CartItem cartItem = maps.get(key);

		if (cartItem == null) {
			maps.put(key, item);
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
		}
	}

	public void remove(Long productId, String color, String size) {
		String key = createKey(productId, color, size);
		maps.remove(key);
	}

	public CartItem update(Long productId, String color, String size, int qty) {
		String key = createKey(productId, color, size);
		CartItem cartItem = maps.get(key);
		if (cartItem != null) {
			cartItem.setQuantity(qty);
		}
		return cartItem;
	}

	public Collection<CartItem> getAllItems() {
		return maps.values();
	}

	public int getCount() {
		return maps.size();
	}

	public double getAmount() {
		return maps.values().stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
	}

	public void removeSelectedItems(Collection<CartItem> cartItems) {
		List<String> keysToRemove = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			if (cartItem.getIsSelected()) {
				String key = createKey(cartItem.getProductID(), cartItem.getColor(), cartItem.getSize());
				keysToRemove.add(key);
			}
		}
		for (String key : keysToRemove) {
			maps.remove(key);
		}
	}

}
