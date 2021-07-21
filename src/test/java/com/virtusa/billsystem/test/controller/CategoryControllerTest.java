package com.virtusa.billsystem.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtusa.billsystem.BillsystemApplication;
import com.virtusa.billsystem.entity.Category;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { BillsystemApplication.class })
@ActiveProfiles("test")
public class CategoryControllerTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testAddCategory() throws Exception {

		Category category = new Category();
		category.setCategoryId(2);
		category.setCategoryName("honey");

		this.mockMvc.perform(post("/billingsystem/inventory/category").contentType("application/json")
				.content(objectMapper.writeValueAsString(category))).andExpect(status().isCreated());
	}

	@Test
	public void testEditCategory() throws Exception {

		Category category = new Category();
		category.setCategoryId(2);
		category.setCategoryName("Sweets");
		this.mockMvc.perform(put("/billingsystem/inventory/category").contentType("application/json")
				.content(objectMapper.writeValueAsString(category))).andExpect(status().isOk());

	}

	@Test
	public void testGetAllCategories() throws Exception {

		this.mockMvc.perform(get("/billingsystem/inventory/categories")).andExpect(status().isOk())
				.andExpect(content().json(" [{'categoryId': 1,'categoryName': 'honey'}]"));

	}

	@Test
	public void testDeleteCategory() throws Exception {
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("Soaps");
		this.mockMvc.perform(delete("/billingsystem/inventory/category").contentType("application/json")
				.content(objectMapper.writeValueAsString(category))).andExpect(status().isOk());

	}

}
