package jai.controller;

import jai.entity.Item;
import jai.entity.Orders;
import jai.repositories.ItemRepository;
import jai.repositories.OrderRepository;
import jai.service.ItemService;
import jai.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/billingsystem/billing")
public class OrderController {

	@Autowired
    OrderRepository orderRepository;
	@Autowired
    ItemRepository itemRepository;
	@Autowired
    OrderService orderService;
	@Autowired
    ItemService itemService;

	@GetMapping("/search")
	public ResponseEntity<List<Item>> searchItem(@RequestParam("search") String search) {

		if (itemService.checkSearch(search)) {
			return ResponseEntity.ok(itemService.findItem(search));
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@PostMapping("/order")
	public ResponseEntity<Orders> saveOrder(@RequestBody Orders orders, @RequestParam("itemName") String itemName)
			throws URISyntaxException {

		if (orders.getQuantity() == 0) {
			return ResponseEntity.unprocessableEntity().build();
		} else {
			int status = orderService.saveOrder(orders, itemName);
			if (status == 0) {
				return ResponseEntity.created(new URI("https://localhost/billingsystem/billing/order")).build();
			} else if (status == -1) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			} else {
				return ResponseEntity.unprocessableEntity().header("quantity", String.valueOf(status)).build();
			}
		}
	}

}
