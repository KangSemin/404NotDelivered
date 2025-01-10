package Not.Delivered.purchase.repository;


import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

  // NORMAL_USER용
  List<Purchase> findByPurchaseUser_UserIdAndIsCancelledFalse(Long userId);
  List<Purchase> findByPurchaseUser_UserIdAndPurchaseStatusAndIsCancelledFalse(Long userId, PurchaseStatus status);
  Optional<Purchase> findByPurchaseIdAndPurchaseUser_UserIdAndIsCancelledFalse(Long purchaseId, Long userId);


  // OWNER용
  List<Purchase> findByShop_OwnerUser_UserIdAndIsCancelledFalse(Long ownerId);
  List<Purchase> findByShop_OwnerUser_UserIdAndPurchaseStatusAndIsCancelledFalse(Long ownerId, PurchaseStatus status);
  Optional<Purchase> findByPurchaseIdAndShop_OwnerUser_UserIdAndIsCancelledFalse(Long purchaseId, Long ownerId);

  // RIDER용
  List<Purchase> findByPurchaseStatusAndDeliveringUserIsNullAndIsCancelledFalse(PurchaseStatus status);
  List<Purchase> findByPurchaseStatusAndDeliveringUser_UserIdAndIsCancelledFalse(PurchaseStatus status, Long riderId);
  List<Purchase> findByDeliveringUser_UserIdAndIsCancelledFalse(Long riderId);
}