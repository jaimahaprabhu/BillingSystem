package jai.service;

import jai.entity.Item;

import java.util.List;


public interface ItemService {

	public List<Item> getItems();

	public boolean addItem(Item item);

	public boolean editItem(String itemId, String editOption, String valueToUpdate);

	public boolean deleteItem(Item item);

	public List<Item> findItem(String search);

	public boolean checkSearch(String search);

	public void reduceQuantity(String itemName, int quantity);
}
