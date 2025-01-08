package Not.Delivered.user.service;

import Not.Delivered.user.domain.User;
import Not.Delivered.user.dto.UserUpdateDto;
import Not.Delivered.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public Optional<User> getUserById(Long userId){
		if (userId == null) {
			throw new IllegalArgumentException("User ID and user details must not be null");
		}

		return userRepository.findById(userId);
	}

	public Optional<User> getUserByEmail(String email){
		if (email == null) {
			return Optional.empty();
		}

		return userRepository.findByEmail(email);
	}

	public User updateUser(Long userId, UserUpdateDto userDetails){

		if (userId == null || userDetails == null) {
			throw new IllegalArgumentException("User ID and user details must not be null");
		}

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new TempUserNotFoundException("User Not Found.")); // 정식 Exception 추가되면 관리

		return userRepository.save(user);
	}

	public void deleteUser(Long userId){
		if (userId == null) {
			throw new IllegalArgumentException("User ID must not be null");
		}

		userRepository.deleteById(userId);
	}


}