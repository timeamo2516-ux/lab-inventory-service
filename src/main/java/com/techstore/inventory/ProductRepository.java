package com.techstore.inventory;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {
  @Modifying(clearAutomatically=true,flushAutomatically=true)
  @Query("update Product p set p.stock = p.stock - :quantity where p.id = :id and p.stock >= :quantity")
  int decreaseStock(@Param("id") Long id,@Param("quantity") int quantity);
}
