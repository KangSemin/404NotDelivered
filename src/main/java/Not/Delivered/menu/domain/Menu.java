package Not.Delivered.menu.domain;

import Not.Delivered.shop.domain.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Column(name = "menu_status", nullable = false)
	private String menuStatus;
}
