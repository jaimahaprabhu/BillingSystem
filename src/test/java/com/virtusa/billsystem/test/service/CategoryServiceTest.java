package com.virtusa.billsystem.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.virtusa.billsystem.entity.Category;
import com.virtusa.billsystem.repositories.CategoryRepository;
import com.virtusa.billsystem.service.Impl.CategoryServiceImpl;

public class CategoryServiceTest {

	@Mock
	CategoryRepository categoryRepository;

	@InjectMocks
	CategoryServiceImpl categoryServiceImpl;

	private List<Category> categoryList = new ArrayList<Category>();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveCategory() {

		Category category = new Category();
		when(categoryRepository.findCategoryName("honey")).thenReturn(new ArrayList<Category>());
		when(categoryRepository.save(any(Category.class))).thenReturn(category);
		assertEquals(true, categoryServiceImpl.saveCategory(category));
	}

	@Test
	public void testSaveCategoryFail() {

		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);
		when(categoryRepository.findCategoryName("honey")).thenReturn(categoryList);
		when(categoryRepository.save(any(Category.class))).thenReturn(category);
		assertEquals(false, categoryServiceImpl.saveCategory(category));
	}

	@Test
	public void testDeleteCategory() {
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);
		when(categoryRepository.findCategoryId(1)).thenReturn(categoryList);
		doNothing().when(categoryRepository);
		categoryRepository.deleteById(Mockito.anyInt());
		categoryServiceImpl.deleteCategory(category);
		assertEquals(true, categoryServiceImpl.deleteCategory(category));
	}

	@Test
	public void testDeleteCategoryFail() {
		Category category = new Category();
		categoryList.add(category);
		when(categoryRepository.findCategoryId(1)).thenReturn(categoryList);
		doNothing().when(categoryRepository);
		categoryRepository.deleteById(Mockito.anyInt());
		assertEquals(false, categoryServiceImpl.deleteCategory(category));
	}

	@Test
	public void testEditCategory() {
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);
		when(categoryRepository.findCategoryName(Mockito.anyString())).thenReturn(categoryList);
		when(categoryRepository.updateCategory(Mockito.anyString(), Mockito.anyInt())).thenReturn(1);
		assertEquals(true, categoryServiceImpl.editCategory(category));
	}

	@Test
	public void testEditCategoryFail() {
		Category category = new Category();
		categoryList.add(category);
		when(categoryRepository.findCategoryName(Mockito.anyString())).thenReturn(categoryList);
		when(categoryRepository.updateCategory(Mockito.anyString(), Mockito.anyInt())).thenReturn(1);
		assertEquals(false, categoryServiceImpl.editCategory(category));
	}

	@Test
	public void testGetAll() {
		categoryServiceImpl.getCategories();
		verify(categoryRepository, times(1)).findAll();
	}

}
