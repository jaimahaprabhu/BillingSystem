package jai.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemSerialize implements Serializable {

	private static final long serialVersionUID = 1L;
	private String categoryName;
	private String itemName;
	private float price;
	private float tax;
	private int itemId;
	public ItemSerialize() {
	}

}
