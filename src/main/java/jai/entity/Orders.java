package jai.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@IdClass(OrderSerialize.class)
public class Orders {

	@Id
	private int orderId;

	@Id
	private int itemId;
	
	@NotNull
	private int quantity;
	
	@NotNull
	private float amount;

	}
