package jai.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Bill {

	@NotNull
	private String purchaseDate;
	@Id
	private int orderId;

}
