package jai.repositories;

import jai.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

	static final String findRedundantOrder = "SELECT ORD FROM Orders ORD WHERE itemId= :itemId AND orderId= :orderId";
	static final String updateOrderId = "UPDATE Orders SET orderId = LPAD(convert(rand()*99999.9,signed),6,'1') WHERE orderId=:orderId";
	static final String getOrderId = "SELECT ORD FROM Orders ORD WHERE itemId= :itemId";
	static final String updateQuantity = "UPDATE Orders SET quantity= :quantity WHERE itemId=:itemId AND orderId= :orderId";
	static final String findByOrderId = "SELECT DISTINCT  ORD FROM Orders ORD WHERE ORD.orderId=:orderId";
	static final String deleteOrder = "DELETE  FROM Orders WHERE itemId= :itemId AND orderId= :orderId";

	@Query(findRedundantOrder)
	List<Orders> findRedundantOrder(@Param("orderId") int orderId, @Param("itemId") int itemId);

	@Modifying
	@Query(updateOrderId)
	int updateOrderId(@Param("orderId") int orderId);

	@Query(getOrderId)
	List<Orders> getOrderId(@Param("itemId") int itemId);

	@Modifying
	@Query(updateQuantity)
	int updateQuantity(@Param("orderId") int orderId, @Param("itemId") int itemId, @Param("quantity") int quantity);

	@Query(findByOrderId)
	List<Orders> findByOrderId(@Param("orderId") int orderId);

	@Modifying
	@Query(deleteOrder)
	int deleteOrder(@Param("orderId") int orderId, @Param("itemId") int itemId);

}
