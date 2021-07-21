package jai.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@IdClass(ItemSerialize.class)
@SequenceGenerator(name="seq", initialValue=100, allocationSize=1)
public class Item {

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private int itemId;

	@Id
	private String categoryName;

	@Id
	private String itemName;

	@Id
	private float price;

	@Id
	private float tax;

	@NotNull
	private int quantity;

}
