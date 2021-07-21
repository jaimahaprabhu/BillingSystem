package jai.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BillOrderSerialize implements Serializable {

	public BillOrderSerialize() {

	}

	private int orderId;

	private String purchaseDate;

	private int quantity;

	private float amount;

	private String itemName;

	private float price;

	private float tax;
	
	private static final long serialVersionUID = 1L;
}
