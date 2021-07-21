package jai.repositories;

import jai.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	static final String findBillByOrderId = "SELECT DISTINCT B FROM Bill B WHERE orderId=:orderId";

	@Query(findBillByOrderId)
	public List<Bill> findBillByOrderId(@Param("orderId") int orderId);

}
