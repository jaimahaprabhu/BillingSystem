package jai.service.Impl;

import jai.entity.Bill;
import jai.entity.BillOrder;
import jai.entity.Item;
import jai.entity.Orders;
import jai.repositories.BillRepository;
import jai.repositories.ItemRepository;
import jai.repositories.OrderRepository;
import jai.service.BillService;
import jai.service.ItemService;
import jai.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BillServiceImpl implements BillService {

	@Autowired
    OrderRepository orderRepository;
	@Autowired
    ItemRepository itemRepository;
	@Autowired
    BillRepository billRepository;
	@Autowired
    OrderService orderService;
	@Autowired
    ItemService itemService;

	private int index, listSize;
	private float totalAmount;
	
	private List<BillOrder> orderBillList;
	private List<Orders> orderList;
	private List<Item> itemList;

	/**
	 * Creating BillOrder Table by setting values based on orderId
	 */
	public List<BillOrder> generateBill(int orderId) {

		index = -1;
		orderList = orderRepository.findByOrderId(orderId);
		listSize = orderList.size();
		orderBillList = new ArrayList<BillOrder>();
		while (++index < listSize) {
			BillOrder billOrder = new BillOrder();
			billOrder.setOrderId(orderList.get(index).getOrderId());
			billOrder.setQuantity(orderList.get(index).getQuantity());
			billOrder.setAmount(orderList.get(index).getAmount());
			itemList = itemRepository.findByItemId(orderList.get(index).getItemId());
			billOrder.setItemName(itemList.get(0).getItemName());
			billOrder.setPrice(itemList.get(0).getPrice());
			billOrder.setTax(itemList.get(0).getTax());
			orderBillList.add(billOrder);
		}
		return orderBillList;
	}

	/**
	 * reducing Quantity of item in Inventory based on OrderId and Setting flag to
	 * false for generating New OrderNo
	 */
	public boolean saveBill(int orderId) {

		if (billRepository.findBillByOrderId(orderId).isEmpty()) {
			Bill bill = new Bill();
			bill.setOrderId(orderId);
			bill.setPurchaseDate(getDate());
			billRepository.save(bill);
			reduceQuantity(orderId);
			orderService.setFlagValue(0);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Creating BillOrder Table by setting values based on orderId with date
	 */
	public List<BillOrder> printBill(int orderId) {

		index = -1;
		totalAmount = 0;
		orderList = orderRepository.findByOrderId(orderId);
		listSize = orderList.size();
		String purchaseDate = billRepository.findBillByOrderId(orderId).get(0).getPurchaseDate();
		orderBillList = new ArrayList<BillOrder>();
		while (++index < listSize) {
			BillOrder billOrder = new BillOrder();
			billOrder.setOrderId(orderList.get(index).getOrderId());
			billOrder.setQuantity(orderList.get(index).getQuantity());
			billOrder.setPurchaseDate(purchaseDate);
			billOrder.setAmount(orderList.get(index).getAmount());
			totalAmount += orderList.get(index).getAmount();
			itemList = itemRepository.findByItemId(orderList.get(index).getItemId());
			billOrder.setItemName(itemList.get(0).getItemName());
			billOrder.setPrice(itemList.get(0).getPrice());
			billOrder.setTax(itemList.get(0).getTax());
			orderBillList.add(billOrder);
		}
		return orderBillList;
	}

	private String getDate() {

		return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
	}

	private void reduceQuantity(int orderId) {

		orderList = orderRepository.findByOrderId(orderId);
		listSize = orderList.size();
		index = -1;
		while (++index < listSize) {
			itemService.reduceQuantity(
					itemRepository.findByItemId(orderList.get(index).getItemId()).get(0).getItemName(),
					orderList.get(index).getQuantity());
		}
	}

	public float getTotal() {
		return totalAmount;
	}

}
