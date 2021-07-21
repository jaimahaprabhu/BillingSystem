package jai.service.Impl;

import jai.entity.Item;
import jai.entity.Orders;
import jai.repositories.ItemRepository;
import jai.repositories.OrderRepository;
import jai.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
    OrderRepository orderRepository;

	@Autowired
    ItemRepository itemRepository;

	private boolean orderFlag;
	private int orderId = 0;
	private List<Orders> orderList;
	private List<Item> itemList;

	public void setFlagValue(int flag) {

		if (flag == 0)
			orderFlag = false;
		else
			orderFlag = true;

	}

	public int saveOrder(Orders orders, String itemName) {

		if (orderFlag == false)
			orderId = 0;

		orderList = orderRepository.findRedundantOrder(orderId, orders.getItemId());
		int result = 0;
		if (orderList.isEmpty()) {
			itemList = itemRepository.findByItemId(orders.getItemId());
			int quantity = itemList.get(0).getQuantity();
			if (quantity >= orders.getQuantity()) {

				float amount = itemList.get(0).getPrice() * orders.getQuantity();
				float taxAmount = itemList.get(0).getTax() / 100;
				amount += (taxAmount * amount);
				orders.setAmount(amount);
				if (orderFlag) {
					orders.setOrderId(orderId);
					orderRepository.save(orders);
				} else {
					orderRepository.save(orders);
					orderRepository.updateOrderId(0);
					orderId = getOrderId(orders);
					orderFlag = true;
				}
				result = 0;
			} else {
				result = quantity;
			}

		} else {
			result = -1;
		}
		return result;
	}

	public int returnCurrentOrderId() {
		return orderId;
	}

	public int getOrderId(Orders orders) {

		orderList = orderRepository.getOrderId(orders.getItemId());
		orderId = 0;
		if (!orderList.isEmpty()) {
			orderId = orderList.get(0).getOrderId();
		}
		return orderId;
	}

	public int editOrder(String itemName, Orders orders) {

		itemList = itemRepository.findItemId(itemName);
		int size = itemList.size(), count = 0, itemId = 0;
		while (count < size) {
			if (orderRepository.findRedundantOrder(orders.getOrderId(), itemList.get(count).getItemId()).isEmpty()) {
				count++;
			} else {
				itemId = itemList.get(count).getItemId();
				count = size;
			}
		}
		if (orderRepository.findRedundantOrder(orders.getOrderId(), itemId).isEmpty()) {
			return 0;
		} else {
			itemList = itemRepository.findByItemId(orders.getItemId());
			int quantity = itemList.get(0).getQuantity();
			if (quantity >= orders.getQuantity()) {
				orderRepository.updateQuantity(orders.getOrderId(), itemId, orders.getQuantity());
				return 1;
			}
			else {
				return quantity;
			}
			
			
		}
	}

	public boolean deleteOrder(Orders orders, String itemName) {
		itemList = itemRepository.findItemId(itemName);
		int size = itemList.size(), count = 0, itemId = 0;
		while (count < size) {
			if (orderRepository.findRedundantOrder(orders.getOrderId(), itemList.get(count).getItemId()).isEmpty()) {
				count++;
			} else {
				itemId = itemList.get(count).getItemId();
				count = size;
			}
		}
		if (orderRepository.findRedundantOrder(orders.getOrderId(), itemId).isEmpty()) {
			return false;
		} else {
			orderRepository.deleteOrder(orders.getOrderId(), itemId);
			return true;
		}

	}
}
