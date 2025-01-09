package Not.Delivered.review.repository;

import Not.Delivered.review.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
  boolean existsByPurchase_PurchaseId(Long purchaseId);

  List<Review> findAllByShopShopIdAndStarPointBetweenOrderByCreatedAtDesc(Long shopId, Long starPointStart, Long starPointEnd);
}
