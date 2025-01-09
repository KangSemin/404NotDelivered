package Not.Delivered.menu.repository;

import Not.Delivered.menu.domain.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

  List<Menu> findAllByShop_ShopId(Long ShopId);
}
