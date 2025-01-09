package Not.Delivered.menu.domain;

import Not.Delivered.menu.dto.MenuUpdateRequestDto;
import Not.Delivered.shop.domain.Shop;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "menu")
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_id")
  private Long menuId;

  @ManyToOne
  @JoinColumn(name = "shop_id")
  private Shop shop;

  @Column(name = "menu_name", nullable = false)
  private String menuName;

  @Column(name = "price", nullable = false)
  private Integer price;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1)")
  @ColumnDefault("false")
  private boolean isDeleted;

  @Builder
  public Menu(Shop shop, String menuName, Integer price) {
    this.shop = shop;
    this.menuName = menuName;
    this.price = price;
  }

  public void updateMenuInfo(MenuUpdateRequestDto dto) {
    if (Objects.nonNull(dto.getMenuName())) {
      this.menuName = dto.getMenuName();
    }

    if (Objects.nonNull(dto.getPrice())) {
      this.price = dto.getPrice();
    }
  }
}
