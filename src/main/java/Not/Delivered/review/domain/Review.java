package Not.Delivered.review.domain;

import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
	@JoinColumn(name = "purchase_id", nullable = false)
	private Purchase purchase;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	//OndeleteOption 고민
	@ManyToOne
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;

}
