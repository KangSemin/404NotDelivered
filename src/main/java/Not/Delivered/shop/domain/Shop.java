package Not.Delivered.shop.domain;

import Not.Delivered.user.domain.User;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "shop")
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_id")
	private Long shopId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User userId;

	@Column(name = "shop_name", nullable = false)
	private String shopName;

	@Column(name = "introduce")
	private String introduce;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "detail_address1")
	private String detailAddress1;

	@Column(name = "detail_address2")
	private String detailAddress2;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "open_time", nullable = false)
	private LocalDateTime openTime;

	@Column(name = "close_time", nullable = false)
	private LocalDateTime closeTime;

	@Column(name = "min_order_price", nullable = false)
	private Integer minOrderPrice;

	@Column(name = "is_closing", nullable = false, columnDefinition = "TINYINT(1)")
	@ColumnDefault("false")
	private boolean isClosing;
}
