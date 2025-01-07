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

	public User createUser(User user){
		// 추가적인 비즈니스 로직을 여기에 포함시킬 수 있습니다.
		return userRepository.save(user);
	}

	public Optional<User> getUserById(Long userId){
		return userRepository.findById(userId);
	}

	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	public User updateUser(Long userId, UserUpdateDto userDetails){
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			// 사용자 미존재 시 예외 처리 또는 null 반환
			return null;
		}
		User user = optionalUser.get();

//		로직...

		return userRepository.save(user);
	}

	public void deleteUser(Long userId){
		userRepository.deleteById(userId);
	}

	public Optional<User> getUserByEmail(String email){
		return userRepository.findByEmail(email);
	}
}