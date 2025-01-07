package Not.Delivered.review.domain;

import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.order.domain.Order;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id", nullable = false)
	private Long reviewId;

	@Column(name = "star_point", nullable = false)
	private Long startPoint;

	@Column(name = "review_content", nullable = false)
	private String reviewContent;

	//OndeleteOption 고민 > SET_DEFAULT 적용방법?
	@OneToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	//OndeleteOption 고민
	@ManyToOne
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;

}
