package com.virtusa.billsystem.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import com.virtusa.billsystem.entity.Item;
import com.virtusa.billsystem.entity.Orders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { BillsystemApplication.class })
@ActiveProfiles("test")
public class OrderControllerTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testViewOrder() throws Exception {

		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);

		this.mockMvc.perform(get("/billingsystem/billing/search").param("search", "Maaza")).andExpect(status().isOk())
				.andExpect(content().json(
						"[{ 'itemId': 2, 'categoryName': 'Drinks','itemName': 'Maaza', 'price': 23.0, 'tax': 2.0, 'quantity': 21} ]"));
	}

	@Test
	public void testSaveOrder() throws Exception {

		Orders orders = new Orders();
		orders.setQuantity(3);
		orders.setItemId(2);
		orders.setOrderId(0);
		this.mockMvc
				.perform(post("/billingsystem/billing/order").contentType("application/json")
						.content(objectMapper.writeValueAsString(orders)).param("itemName", "Dabur"))
				.andExpect(status().isCreated());
	}

}
