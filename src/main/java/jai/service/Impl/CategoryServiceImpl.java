package jai.service.Impl;

import jai.entity.Category;
import jai.repositories.CategoryRepository;
import jai.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
    CategoryRepository categoryRepository;

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	public boolean saveCategory(@RequestBody Category category) {

		if (categoryRepository.findCategoryName(category.getCategoryName()).isEmpty()) {

			categoryRepository.save(category);
			return true;
		} else
			return false;
	}

	public boolean deleteCategory(Category category) {

		if (categoryRepository.findCategoryId(category.getCategoryId()).isEmpty()) {
			
			return false;
		} else {
			categoryRepository.deleteById(category.getCategoryId());
			return true;
		}

	}

	public boolean editCategory(Category category) {

		if (categoryRepository.findCategoryName(category.getCategoryName()).isEmpty()) {
			
			categoryRepository.updateCategory(category.getCategoryName(), category.getCategoryId());
			return true;
		} else {
			return false;
		}

	}

}
