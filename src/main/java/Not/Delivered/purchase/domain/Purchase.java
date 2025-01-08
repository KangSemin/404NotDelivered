package Not.Delivered.purchase.domain;

import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.menu.domain.Menu;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "purchase")
@Getter
@NoArgsConstructor
public class Purchase extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_id")
	private Long purchaseId;

	// 주문한 사용자
	@ManyToOne
	@JoinColumn(name = "purchase_user_id", nullable = false)
	private User purchaseUser;

	// 배달하는 사용자
	@ManyToOne
	@JoinColumn(name = "delivery_user_id", nullable = false)
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
	@Column(name = "purchase_status", nullable = false)
	private PurchaseStatus purchaseStatus;

	// 주문 생성 메서드
	public Purchase(User purchaseUser, User deliveringUser, Shop shop, Menu menu, PurchaseStatus purchaseStatus) {
		this.purchaseUser=purchaseUser;
		this.deliveringUser = deliveringUser;
		this.shop = shop;
		this.menu = menu;
		this.purchaseStatus = purchaseStatus;
	}

	// 주문 상태 변경 메서드
	public void setPurchaseStatus(PurchaseStatus purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}
}