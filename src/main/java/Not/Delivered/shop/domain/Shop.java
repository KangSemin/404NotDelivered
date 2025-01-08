package Not.Delivered.shop.domain;

import Not.Delivered.common.entity.Address;
import Not.Delivered.user.domain.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "shop")
@Getter
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

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "city", column = @Column(name = "city", nullable = false)),
      @AttributeOverride(name = "state", column = @Column(name = "state", nullable = false)),
      @AttributeOverride(name = "street", column = @Column(name = "street", nullable = false)),
      @AttributeOverride(name = "detailAddress1", column = @Column(name = "detail_address1")),
      @AttributeOverride(name = "detailAddress2", column = @Column(name = "detail_address2"))

  })
  private Address address;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "open_time", nullable = false)
  private LocalTime openTime;

  @Column(name = "close_time", nullable = false)
  private LocalTime closeTime;

  @Column(name = "min_order_price", nullable = false)
  private Integer minOrderPrice;

  @Column(name = "is_closing", nullable = false, columnDefinition = "TINYINT(1)")
  @ColumnDefault("false")
  private boolean isClosing;
}
