package Not.Delivered.review.repository;

import Not.Delivered.review.domain.Dto.ReviewWithCommentDto;
import Not.Delivered.review.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  boolean existsByPurchase_PurchaseId(Long purchaseId);

  List<Review> findAllByShopShopIdAndStarPointBetweenOrderByCreatedAtDesc(Long shopId,
      Long starPointStart, Long starPointEnd);

  @Query("""
          SELECT new Not.Delivered.review.domain.Dto.ReviewWithCommentDto(
                                                                        r.reviewId,
                                                                        r.user.userId,
                                                                        r.createdAt,
                                                                        r.lastModifiedAt,
                                                                        r.starPoint,
                                                                        r.reviewContent,
                                                                        r.shop.shopId,
                                                                        r.purchase.purchaseId,
                                                                        (SELECT c FROM Comment c WHERE c.review.reviewId = r.reviewId)
                                                                    )
                                                                    FROM Review r
                                                                    WHERE r.shop.shopId = :shopId
                                                                      AND r.starPoint BETWEEN :minStarPoint AND :maxStarPoint
                                                                    ORDER BY r.createdAt DESC
      """)
  List<ReviewWithCommentDto> findReviewsWithComment(Long shopId, Long minStarPoint,
      Long maxStarPoint);
}
