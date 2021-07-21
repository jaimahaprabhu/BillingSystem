package com.virtusa.billsystem.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.virtusa.billsystem.entity.Orders;
import com.virtusa.billsystem.repositories.ItemRepository;
import com.virtusa.billsystem.repositories.OrderRepository;
import com.virtusa.billsystem.service.Impl.OrderServiceImpl;

public class OrderServiceTest {

	@Mock
	OrderRepository orderRepository;

	@Mock
	ItemRepository itemRepository;

	@InjectMocks
	OrderServiceImpl orderServiceImpl;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testReturnOrderId() {
		assertEquals(0, orderServiceImpl.returnCurrentOrderId());
	}

	@Test
	public void testGetOrderId() {
		Orders orders = new Orders();
		orders.setOrderId(101);
		orders.setItemId(1);
		List<Orders> orderList = new ArrayList<Orders>();
		orderList.add(orders);
		Mockito.when(orderRepository.getOrderId(Mockito.anyInt())).thenReturn(orderList);
		orderServiceImpl.getOrderId(orders);

	}

	@Test
	public void testEditOrder() {
		Orders orders = new Orders();
		List<Orders> orderList = new ArrayList<Orders>();
		orderList.add(orders);
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		Mockito.when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);
		when(orderRepository.findRedundantOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderList);
		when(orderRepository.updateQuantity(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Mockito.anyInt());
		orderServiceImpl.editOrder("Tea", orders);
	}

	@Test
	public void testEditOrder1() {
		Orders orders = new Orders();
		List<Orders> orderList = new ArrayList<Orders>();

		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		Mockito.when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);
		when(orderRepository.findRedundantOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderList);
		when(orderRepository.updateQuantity(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Mockito.anyInt());
		orderServiceImpl.editOrder("Tea", orders);
	}

	@Test
	public void testDeletetOrder() {
		List<Orders> orderList = new ArrayList<Orders>();
		Orders orders = new Orders();
		orderList.add(orders);
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		Mockito.when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);
		when(orderRepository.findRedundantOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderList);
		when(orderRepository.deleteOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(1);
		orderServiceImpl.deleteOrder(orders, "Dabur");
	}

	@Test
	public void testDeletetOrderFalse() {
		List<Orders> orderList = new ArrayList<Orders>();
		Orders orders = new Orders();
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		Mockito.when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);
		when(orderRepository.findRedundantOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderList);
		when(orderRepository.deleteOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(1);
		orderServiceImpl.deleteOrder(orders, "Dabur");
	}

	@Test
	public void testSetFlag() {
		orderServiceImpl.setFlagValue(0);
	}

	@Test
	public void testSetFlagFalse() {
		orderServiceImpl.setFlagValue(1);
	}

	@Test
	public void testSaveOrderFail() {
		List<Orders> orderList = new ArrayList<Orders>();
		Orders orders = new Orders();
		orderList.add(orders);
		when(orderRepository.findRedundantOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderList);
		orderServiceImpl.saveOrder(orders, "Dabur");
	}

	@Test
	public void testSaveOrder() {
		List<Orders> orderList = new ArrayList<Orders>();
		Orders orders = new Orders();
		List<Item> itemList = returnItemList();
		Mockito.when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);
		when(orderRepository.findRedundantOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderList);
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		orderServiceImpl.saveOrder(orders, "Dabur");
	}

	@Test
	public void testSaveOrderLessQuantity() {
		List<Orders> orderList = new ArrayList<Orders>();
		Orders orders = new Orders();
		orders.setQuantity(4);
		List<Item> itemList = returnItemList();
		Mockito.when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);
		when(orderRepository.findRedundantOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderList);
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		orderServiceImpl.saveOrder(orders, "Dabur");
	}

	@Test
	public void testSaveOrderFalse() {
		List<Orders> orderList = new ArrayList<Orders>();
		Orders orders = new Orders();
		List<Item> itemList = returnItemList();
		Mockito.when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);
		when(orderRepository.findRedundantOrder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderList);
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		orderServiceImpl.setFlagValue(1);
		orderServiceImpl.saveOrder(orders, "Dabur");
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
