package Not.Delivered.comment.domain;

import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.review.domain.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import Not.Delivered.user.domain.User;
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
	@JoinColumn(name = "review_id")
	@OnDelete(action = OnDeleteAction.SET_DEFAULT)
	private Review review;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
