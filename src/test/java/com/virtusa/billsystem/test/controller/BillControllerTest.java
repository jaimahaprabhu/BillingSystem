package com.virtusa.billsystem.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.virtusa.billsystem.entity.Orders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { BillsystemApplication.class })
@ActiveProfiles("test")
public class BillControllerTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testDeleteOrder() throws Exception {

		Orders orders = new Orders();
		orders.setOrderId(1);
		mockMvc.perform(delete("/billingsystem/billing/bills/order").param("itemName", "Maaza")
				.contentType("application/json").content(objectMapper.writeValueAsString(orders)))
				.andExpect(status().isOk());
	}

	@Test
	public void testSaveBill() throws Exception {

		mockMvc.perform(post("/billingsystem/billing/bills/bill").param("orderId", "0"))
				.andExpect(status().isCreated());

	}

	@Test
	public void testGenerateBill() throws Exception {

		mockMvc.perform(get("/billingsystem/billing/bills/order").param("orderId", "10")).andExpect(status().isOk())
				.andExpect(content().json(
						"[{\"orderId\":10,\"purchaseDate\":null,\"quantity\":2,\"amount\":46.0,\"itemName\":\"Maaza\",\"price\":23.0,\"tax\":2.0}]"));
	}

	@Test
	public void testprintBill() throws Exception {

		mockMvc.perform(get("/billingsystem/billing/bills/bill").param("orderId", "10").header("total", "46.0"))
				.andExpect(status().isOk()).andExpect(content().json(
						"[{\"orderId\":10,\"purchaseDate\":'2020/03/10 15:53:07',\"quantity\":4,\"amount\":46.0,\"itemName\":\"Maaza\",\"price\":23.0,\"tax\":2.0}]"));

	}

	@Test
	public void testEditOrder() throws Exception {

		Orders orders = new Orders();
		orders.setQuantity(4);
		orders.setOrderId(10);

		mockMvc.perform(put("/billingsystem/billing/bills/order").param("itemName", "Maaza")
				.contentType("application/json").content(objectMapper.writeValueAsString(orders)))
				.andExpect(status().isOk());
	}

}
