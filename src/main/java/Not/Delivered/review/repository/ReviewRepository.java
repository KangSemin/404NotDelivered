package Not.Delivered.review.repository;

import Not.Delivered.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
  boolean existsByPurchase_PurchaseId(Long purchaseId);
}
