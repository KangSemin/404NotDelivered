package Not.Delivered.menu.repository;

import Not.Delivered.menu.domain.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<Menu, Long> {

  @Query("SELECT m FROM Menu m WHERE m.shop.shopId = :shopId AND m.isDeleted = false")
  List<Menu> findAllByShopId(Long shopId);
}
