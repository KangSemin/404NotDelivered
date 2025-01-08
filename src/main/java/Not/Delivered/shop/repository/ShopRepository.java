package Not.Delivered.shop.repository;

import Not.Delivered.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepository extends JpaRepository<Shop, Long> {

  int countByOwnerUser_UserId(Long userId);

  @Query(
      value =
          """
        SELECT COUNT(*) FROM shop s
        WHERE s.user_id = :userId
        AND s.is_closing = 0
        """,
      nativeQuery = true)
  int countByOwnerUserId(@Param("userId") Long userId);
}
