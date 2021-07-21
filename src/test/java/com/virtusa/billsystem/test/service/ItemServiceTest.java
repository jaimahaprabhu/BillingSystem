package com.virtusa.billsystem.test.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.virtusa.billsystem.entity.Item;
import com.virtusa.billsystem.repositories.ItemRepository;
import com.virtusa.billsystem.service.Impl.ItemServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemServiceTest {

	@Mock
	ItemRepository itemRepository;
	@InjectMocks
	ItemServiceImpl itemServiceImpl;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testFindItems() {
		itemServiceImpl.getItems();
		verify(itemRepository, times(1)).findAll();
	}

	@Test
	public void testDeleteItem() {
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);

		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);

		doNothing().when(itemRepository);
		itemRepository.deleteByItemId(Mockito.anyInt());
		assertEquals(true, itemServiceImpl.deleteItem(item));
	}

	@Test
	public void testDeleteItemFail() {
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);

		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);

		doNothing().when(itemRepository);
		itemRepository.deleteByItemId(Mockito.anyInt());
		assertEquals(false, itemServiceImpl.deleteItem(item));
	}

	@Test
	public void testFindItem() {

		itemServiceImpl.findItem("Honey");
		verify(itemRepository, times(1)).findAll();
	}

	@Test
	public void testAddItem() {

		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		List<Item> newItemList = new ArrayList<Item>();
		when(itemRepository.findByCompositeKey(Mockito.anyString(), Mockito.anyString(), Mockito.anyFloat(),
				Mockito.anyFloat())).thenReturn(newItemList);
		when(itemRepository.save(any(Item.class))).thenReturn(item);

		assertEquals(true, itemServiceImpl.addItem(item));
	}

	@Test
	public void testAddItemFail() {

		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);

		when(itemRepository.findByCompositeKey(Mockito.anyString(), Mockito.anyString(), Mockito.anyFloat(),
				Mockito.anyFloat())).thenReturn(itemList);
		when(itemRepository.save(any(Item.class))).thenReturn(item);

		assertEquals(false, itemServiceImpl.addItem(item));
	}

	@Test
	public void testCheckSearchEmptyList() {

		List<Item> itemList = new ArrayList<Item>();
		Mockito.when(itemRepository.findAll()).thenReturn(itemList);
		assertEquals(false, itemServiceImpl.checkSearch("Dabur"));
	}

	@Test
	public void testCheckSearch() {

		List<Item> itemList = returnItemList();
		Mockito.when(itemRepository.findAll()).thenReturn(itemList);
		assertEquals(true, itemServiceImpl.checkSearch("Dabur"));
	}

	@Test
	public void testEditItemFail() {
		List<Item> itemList = new ArrayList<Item>();
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);

		assertEquals(false, itemServiceImpl.editItem("1", "Price", "4"));
	}

	@Test
	public void testEditItemForPrice() {
		List<Item> itemList = returnItemList();
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);

		assertEquals(true, itemServiceImpl.editItem("1", "Price", "4"));
	}

	@Test
	public void testEditItemForTax() {
		List<Item> itemList = returnItemList();
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);

		assertEquals(true, itemServiceImpl.editItem("1", "Tax", "4"));
	}

	@Test
	public void testEditItemForQuantity() {
		List<Item> itemList = returnItemList();
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);

		assertEquals(true, itemServiceImpl.editItem("1", "Quantity", "4"));
	}

	@Test
	public void testReduceQuantity() {
		List<Item> itemList = returnItemList();
		when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);
		itemServiceImpl.reduceQuantity("Dabur", 2);
	}

	private List<Item> returnItemList() {
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		return itemList;
	}

}
