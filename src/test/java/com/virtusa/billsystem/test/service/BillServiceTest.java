package com.virtusa.billsystem.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.virtusa.billsystem.entity.Bill;
import com.virtusa.billsystem.entity.Item;
import com.virtusa.billsystem.entity.Orders;
import com.virtusa.billsystem.repositories.BillRepository;
import com.virtusa.billsystem.repositories.ItemRepository;
import com.virtusa.billsystem.repositories.OrderRepository;
import com.virtusa.billsystem.service.Impl.BillServiceImpl;
import com.virtusa.billsystem.service.Impl.ItemServiceImpl;
import com.virtusa.billsystem.service.Impl.OrderServiceImpl;

public class BillServiceTest {

	@Mock
	OrderRepository orderRepository;

	@Mock
	ItemRepository itemRepository;
	@Mock
	BillRepository billRepository;
	@Mock
	ItemServiceImpl itemServiceImpl;
	@Mock
	OrderServiceImpl orderServiceImpl;
	@InjectMocks
	BillServiceImpl billServiceImpl;

	private List<Bill> billList = new ArrayList<Bill>();

	@BeforeEach
	public void setup() {

		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testgetTotal() {

		assertEquals(0.0, billServiceImpl.getTotal(), 1);
	}

	@Test
	public void testGenerateBill() {
		List<Orders> orderList = returnOrderList();
		List<Item> itemList = returnItemList();
		when(orderRepository.findByOrderId(Mockito.anyInt())).thenReturn(orderList);
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		billServiceImpl.generateBill(1);
	}

	@Test
	public void testsaveBill() {
		billList = new ArrayList<Bill>();
		List<Item> itemList = returnItemList();
		List<Orders> orderList = returnOrderList();
		Bill bill = new Bill();
		when(billRepository.findBillByOrderId(Mockito.anyInt())).thenReturn(billList);
		when(billRepository.save(Mockito.any(Bill.class))).thenReturn(bill);
		when(orderRepository.findByOrderId(Mockito.anyInt())).thenReturn(orderList);
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		doNothing().when(itemServiceImpl);
		itemServiceImpl.reduceQuantity(Mockito.anyString(), Mockito.anyInt());
		assertEquals(billServiceImpl.saveBill(0), true);
	}

	@Test
	public void testsaveBillFail() {
		billList = new ArrayList<Bill>();
		Bill bill = new Bill();
		bill.setOrderId(0);
		bill.setPurchaseDate("2020/02/26 14:22:22");
		billList.add(bill);
		when(billRepository.findBillByOrderId(Mockito.anyInt())).thenReturn(billList);
		when(billRepository.save(Mockito.any(Bill.class))).thenReturn(bill);
		assertEquals(billServiceImpl.saveBill(0), false);
	}

	@Test
	public void testPrintBill() {
		List<Orders> orderList = returnOrderList();

		List<Item> itemList = returnItemList();
		String purchaseDate = "2020/02/26 14:22:22";
		billList = new ArrayList<Bill>();
		Bill bill = new Bill();
		bill.setOrderId(1);
		bill.setPurchaseDate(purchaseDate);

		billList.add(bill);
		when(orderRepository.findByOrderId(Mockito.anyInt())).thenReturn(orderList);
		when(billRepository.findBillByOrderId(Mockito.anyInt())).thenReturn(billList);
		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		billServiceImpl.printBill(1);
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

	private List<Orders> returnOrderList() {
		List<Orders> orderList = new ArrayList<Orders>();
		Orders orders = new Orders();
		orders.setOrderId(1);
		orderList.add(orders);

		return orderList;
	}
}
