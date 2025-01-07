package Not.Delivered.order.domain;

import Not.Delivered.menu.domain.Menu;
import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor
public class Order extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	// 주문한 사용자
	@ManyToOne
	@JoinColumn(name = "ordered_user_id", nullable = false)
	private User orderedUser;

	// 배달하는 사용자
	@ManyToOne
	@JoinColumn(name = "delivering_user_id", nullable = false)
	private User deliveringUser;

	// 주문한 가게
	@ManyToOne
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;

	// 주문한 메뉴
	@ManyToOne
	@JoinColumn(name = "menu_id", nullable = false)
	private Menu menu;

	// 주문 상태
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private OrderStatus orderStatus;

	// 주문 생성 메서드
	public Order(User orderedUser, User deliveringUser, Shop shop, Menu menu, OrderStatus orderStatus) {
		this.orderedUser = orderedUser;
		this.deliveringUser = deliveringUser;
		this.shop = shop;
		this.menu = menu;
		this.orderStatus = orderStatus;
	}

	// 주문 상태 변경 메서드
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}