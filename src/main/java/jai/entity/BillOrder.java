package jai.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@IdClass(BillOrderSerialize.class)
public class BillOrder {

	@Id
	private int orderId;
	@Id
	private String purchaseDate;
	@Id
	private int quantity;
	@Id
	private float amount;
	@Id
	private String itemName;
	@Id
	private float price;
	@Id
	private float tax;

}
