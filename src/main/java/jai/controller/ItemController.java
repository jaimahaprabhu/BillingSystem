package jai.controller;

import jai.entity.Item;
import jai.repositories.ItemRepository;
import jai.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/billingsystem/inventory")
public class ItemController {

	@Autowired
    ItemRepository itemRepository;
	@Autowired
    ItemService itemService;

	@GetMapping("/items")
	public ResponseEntity<List<Item>> getItems() {

		return ResponseEntity.ok(itemService.getItems());

	}

	@PostMapping("/item")
	public ResponseEntity<Item> saveItem(@RequestBody Item item) throws URISyntaxException {

		if (item.getPrice() == 0 || item.getQuantity() == 0 || item.getTax() == 0 || item.getItemName().length()==0
				|| item.getItemName().matches("[a-zA-Z0-9]{0,1}")) {
			return ResponseEntity.unprocessableEntity().build();
		} else {

			if (itemService.addItem(item)) {
				return ResponseEntity.created(new URI("https://localhost/billingsystem/inventory/item")).build();
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}
	}

	@PutMapping("/item")
	public ResponseEntity<Item> updateItem(@RequestParam("itemId") String itemId,
                                           @RequestParam("editOption") String editOption, @RequestParam("valueToUpdate") String valueToUpdate) {

		if (valueToUpdate.length()==0 || valueToUpdate.matches("[a-zA-Z]+")) {
			return ResponseEntity.unprocessableEntity().build();
		} else {

			if (itemService.editItem(itemId, editOption, valueToUpdate)) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}

	@DeleteMapping("/item")
	public ResponseEntity<Item> deleteItem(@RequestBody Item item) {

		if (itemService.deleteItem(item)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

}
