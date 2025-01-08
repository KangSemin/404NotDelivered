package Not.Delivered.comment.domain;

import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.review.OwnerDataException;
import Not.Delivered.review.domain.Review;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentId;

	@Column(name = "comment_content", nullable = false)
	private String commentContent;

	//OndeleteOption 고민
	@OneToOne
	@JoinColumn(name = "review_id", columnDefinition = "BIGINT DEFAULT -1")
	@OnDelete(action = OnDeleteAction.SET_DEFAULT)
	private Review review;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Comment(Long commentId, String commentContent, Review review, User user) {
		this.commentId = commentId;
		this.commentContent = commentContent;
		this.review = review;
		this.user = user;
	}

	public static void commentReviewAndUserValidate(Comment comment, Long reviewId, Long userId) {
		if(!comment.getReview().getReviewId().equals(reviewId)){
			throw new IllegalArgumentException("Not just a comment in that review.");
		}
		if(!comment.getUser().getUserId().equals(userId)){
			throw new OwnerDataException("Only your comments are accessible.");
		}
	}

	public static void shopOwnerValidate(Shop shop, Long userId) {
		if(shop.getOwnerUser().getUserId().equals(userId)){
			throw new OwnerDataException("Only store owner can fill it out.");
		}
	}
}
