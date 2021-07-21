package jai.service;

import jai.entity.Category;

import java.util.List;

public interface CategoryService {

	public List<Category> getCategories();

	public boolean deleteCategory(Category category);

	public boolean editCategory(Category category);

	public boolean saveCategory(Category category);
}
