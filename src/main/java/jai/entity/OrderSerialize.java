package jai.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderSerialize implements Serializable {

	private int orderId;
	private int itemId;
	private static final long serialVersionUID = 1L;

	public OrderSerialize() {
	}



}
