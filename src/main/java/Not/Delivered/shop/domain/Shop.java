package Not.Delivered.shop.domain;

import Not.Delivered.common.entity.Address;
import Not.Delivered.common.exception.AccessDeniedException;
import Not.Delivered.shop.dto.ShopUpdateRequestDto;
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
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "shop")
@Getter
@NoArgsConstructor
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "shop_id")
  private Long shopId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User ownerUser;

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
  private Long minOrderPrice;

  @Column(name = "is_closing", nullable = false, columnDefinition = "TINYINT(1)")
  @ColumnDefault("false")
  private boolean isClosing;

  @Builder
  public Shop(
      User ownerUser,
      String shopName,
      String introduce,
      Address address,
      String phoneNumber,
      LocalTime openTime,
      LocalTime closeTime,
      Long minOrderPrice,
      boolean isClosing) {
    this.ownerUser = ownerUser;
    this.shopName = shopName;
    this.introduce = introduce;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.minOrderPrice = minOrderPrice;
    this.isClosing = isClosing;
  }

  public void validShopOwner(Long userId, Long ownerUserId) {
    if (!Objects.equals(userId, ownerUserId)) {
      throw new AccessDeniedException("해당 가게에 대한 권한이 없습니다.");
    }
  }

  public void updateShopInfo(ShopUpdateRequestDto dto) {
    this.shopName = dto.getShopName();
    this.introduce = dto.getIntroduce();
    this.address = dto.getAddress();
    this.phoneNumber = dto.getPhoneNumber();
    this.openTime = dto.getOpenTime();
    this.closeTime = dto.getCloseTime();
    this.minOrderPrice = dto.getMinOrderPrice();
  }

  public void closedShop() {
    this.isClosing = true;
  }
}
