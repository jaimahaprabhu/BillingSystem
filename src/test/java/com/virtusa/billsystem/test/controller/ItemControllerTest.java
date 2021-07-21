
package com.virtusa.billsystem.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { BillsystemApplication.class })
@ActiveProfiles("test")
public class ItemControllerTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testViewItem() throws Exception {
		Item item = new Item();
		item.setCategoryName("Drinks");
		item.setItemId(1);
		item.setItemName("Maaza");
		item.setPrice(25.0f);
		item.setQuantity(21);
		item.setTax(2.0f);
		this.mockMvc.perform(get("/billingsystem/inventory/items")).andExpect(status().isOk()).andExpect(content().json(
				" [{'itemId': 110,'categoryName': 'honey','itemName': 'Dabur','price': 22.0,'tax': 1.2,'quantity': 3}]"));

	}

	@Test
	public void testEditItemFailIfNull() throws Exception {

		mockMvc.perform(put("/billingsystem/inventory/item").param("valuetoupdate", "").param("itemId", "1")
				.param("editoption", "Tax")).andExpect(status().isBadRequest());
	}

	@Test
	public void testEditItemFailIfString() throws Exception {

		mockMvc.perform(put("/billingsystem/inventory/item").param("valuetoupdate", "rg").param("itemId", "1")
				.param("editoption", "Tax")).andExpect(status().isBadRequest());
	}

	@Test
	public void testSaveItem() throws Exception {

		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		item.setTax(1.2f);

		this.mockMvc.perform(post("/billingsystem/inventory/item").contentType("application/json")
				.content(objectMapper.writeValueAsString(item))).andExpect(status().isCreated());
	}

	@Test
	public void testSaveItemPriceFail() throws Exception {

		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(0.0f);
		item.setQuantity(20);

		this.mockMvc.perform(post("/billingsystem/inventory/item").contentType("application/json")
				.content(objectMapper.writeValueAsString(item))).andExpect(status().isBadRequest());
	}

	@Test
	public void testAddItemQuantiyFail() throws Exception {

		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(10.0f);
		item.setQuantity(0);

		this.mockMvc.perform(post("/billingsystem/inventory/item").contentType("application/json")
				.content(objectMapper.writeValueAsString(item))).andExpect(status().isBadRequest());
	}

	@Test
	public void testAddItemTaxFail() throws Exception {

		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(10.0f);
		item.setTax(0.0f);
		item.setQuantity(10);

		this.mockMvc.perform(post("/billingsystem/inventory/item").contentType("application/json")
				.content(objectMapper.writeValueAsString(item))).andExpect(status().isBadRequest());
	}

	@Test
	public void testEditItem() throws Exception {
		Item item = new Item();
		item.setCategoryName("Drinks");
		item.setItemId(1);
		item.setItemName("Maaza");
		item.setPrice(25.0f);
		item.setQuantity(21);
		item.setTax(2.0f);
		mockMvc.perform(put("/billingsystem/inventory/item").param("valueToUpdate", "1.7").param("itemId", "2")
				.param("editOption", "Tax")).andExpect(status().isOk());
	}

	@Test
	public void testDeleteItem() throws Exception {

		Item item = new Item();
		item.setCategoryName("Drinks");
		item.setItemId(2);
		item.setItemName("Maaza");
		item.setPrice(25.0f);
		item.setQuantity(21);
		item.setTax(2.0f);
		this.mockMvc.perform(delete("/billingsystem/inventory/item").contentType("application/json")
				.content(objectMapper.writeValueAsString(item))).andExpect(status().isOk());
	}

}
