package jai.controller;

import jai.entity.Category;
import jai.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/billingsystem/inventory")
public class CategoryController {

	@Autowired
    CategoryService categoryService;

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getCategories() {

		return ResponseEntity.ok(categoryService.getCategories());
	}

	@PostMapping("/category")
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) throws URISyntaxException {

	
		if (categoryService.saveCategory(category)) {
			return ResponseEntity.created(new URI("https://localhost/billingsystem/inventory/category")).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		}
	}

	@PutMapping("/category")
	public ResponseEntity<Category> editCategory(@RequestBody Category category) {

	
		if (categoryService.editCategory(category)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();

		}
	}

	@DeleteMapping("/category")
	public ResponseEntity<Category> deleteCategory(@RequestBody Category category) {

		if (categoryService.deleteCategory(category)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();

		}
	}

}
