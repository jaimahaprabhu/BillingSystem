package jai.repositories;

import jai.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
	
	static final String findByCompositeKey = "SELECT i FROM Item i WHERE (itemName= :ITEMNAME AND price= :PRICE) AND (tax= :TAX AND categoryName= :CATEGORYNAME)";
	static final String findByKey = "SELECT i FROM Item i WHERE (itemName= :ITEMNAME AND price= :PRICE) AND (tax= :TAX AND categoryName= :CATEGORYNAME)";
	static final String findByItemId = "SELECT i FROM Item i where itemId= :itemId";
	static final String findItemId = "SELECT  i FROM Item  i WHERE itemName= :itemName ";
	static final String updateItemPrice = "UPDATE Item SET price= :price WHERE itemId= :itemId";
	static final String updateItemTax = "UPDATE Item SET tax= :tax WHERE itemId= :itemId";
	static final String updateItemQuantity = "UPDATE Item SET quantity= :quantity WHERE itemId= :itemId";
	static final String deleteByItemId = "Delete FROM Item WHERE itemId= :itemId";

	@Query(findByCompositeKey)
	public List<Item> findByCompositeKey(@Param("CATEGORYNAME") String catName, @Param("ITEMNAME") String itemName,
                                         @Param("PRICE") float price, @Param("TAX") float tax);
	@Query(findByItemId)
	public List<Item> findByItemId(@Param("itemId") int itemId);

	@Query(findItemId)
	public List<Item> findItemId(@Param("itemName") String itemName);
	@Modifying
	@Query(updateItemPrice)
	int updateItemPrice(@Param("itemId") int id, @Param("price") float price);

	@Modifying
	@Query(updateItemTax)
	int updateItemTax(@Param("itemId") int id, @Param("tax") float tax);

	@Modifying
	@Query(updateItemQuantity)
	int updateItemQuantity(@Param("itemId") int id, @Param("quantity") int quantity);

	@Modifying
	@Query(deleteByItemId)
	public void deleteByItemId(@Param("itemId") int id);

}
