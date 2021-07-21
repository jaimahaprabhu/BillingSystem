package jai.repositories;

import jai.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	static final String findCategoryName = "SELECT C FROM Category C WHERE categoryName= :categoryName";
	static final String updateCategory = "UPDATE Category SET categoryName= :categoryName WHERE categoryId= :categoryId";
	static final String findCategoryId = "SELECT C FROM Category C WHERE categoryId= :categoryId";

	@Query(findCategoryName)
	public List<Category> findCategoryName(@Param("categoryName") String categoryName);

	@Modifying
	@Query(updateCategory)
	int updateCategory(@Param("categoryName") String categoryName, @Param("categoryId") int categoryId);

	@Query(findCategoryId)
	public List<Category> findCategoryId(@Param("categoryId") int categoryId);

}
