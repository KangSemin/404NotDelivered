package Not.Delivered.user.repository;

import Not.Delivered.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// 이메일로 사용자 조회를 위한 메서드 추가
	Optional<User> findByEmail(String email);
	Optional<User> findByUserIdAndIsWithdrawalFalse(Long userId);
}
