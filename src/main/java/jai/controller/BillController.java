package jai.controller;

import jai.entity.Bill;
import jai.entity.BillOrder;
import jai.entity.Orders;
import jai.service.BillService;
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
public class BillController {

	@Autowired
    ItemService itemService;
	@Autowired
    BillService billService;
	@Autowired
    OrderService orderService;

	@GetMapping("/bills/order")
	public ResponseEntity<List<BillOrder>> genrerateBill(@RequestParam("orderId") int orderId) {
	
			return ResponseEntity.ok(billService.generateBill(orderId));	
	}

	@PutMapping("/bills/order")
	public ResponseEntity<Orders> editOrder(@RequestParam("itemName") String itemName, @RequestBody Orders orders) {
		int status = orderService.editOrder(itemName, orders);
		if (status==1) {
			return ResponseEntity.ok().build();
		}else if (status == 0) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.unprocessableEntity().header("quantity", String.valueOf(status)).build();
		}
	}

	@DeleteMapping("/bills/order")
	public ResponseEntity<Orders> deleteOrder(@RequestBody Orders orders, @RequestParam("itemName") String itemName) {

		if (orderService.deleteOrder(orders, itemName)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/bills/bill")
	public ResponseEntity<List<Bill>> saveBill(@RequestParam("orderId") int orderId) throws URISyntaxException {
	
		if (billService.saveBill(orderId)) {
			return ResponseEntity.created(new URI("https://localhost/billingsystem/billing/bills/bill")).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@GetMapping(value = "/bills/bill")
	public ResponseEntity<List<BillOrder>> displayBill(@RequestParam("orderId") int orderId) {

		List<BillOrder> billOrderList = billService.printBill(orderId);
		float total = billService.getTotal();
		if (billOrderList.isEmpty()) {
			return ResponseEntity.ok(billOrderList);
		} else {
			return ResponseEntity.ok().header("Total", String.valueOf(total)).body(billOrderList);
		}
	}

}
