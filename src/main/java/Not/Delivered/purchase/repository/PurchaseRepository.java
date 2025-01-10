package Not.Delivered.purchase.repository;


import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

  // NORMAL_USER용
  List<Purchase> findByPurchaseUser_UserId(Long userId);
  List<Purchase> findByPurchaseUser_UserIdAndPurchaseStatus(Long userId, PurchaseStatus status);
  Optional<Purchase> findByPurchaseIdAndPurchaseUser_UserId(Long purchaseId, Long userId);

  // OWNER용
  Optional<List<Purchase>> findByShop_OwnerUser_UserId(Long ownerId);
  Optional<List<Purchase>> findByShop_OwnerUser_UserIdAndPurchaseStatus(Long ownerId, PurchaseStatus status);
  Optional<Purchase> findByPurchaseIdAndShop_OwnerUser_UserId(Long purchaseId, Long ownerId);

  // 상태로 주문 조회
  List<Purchase> findByPurchaseStatus(PurchaseStatus status);
}