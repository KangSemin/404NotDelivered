package Not.Delivered.auth.service;


import Not.Delivered.auth.dto.LoginRequestDto;
import Not.Delivered.auth.dto.SignupRequestDto;
import Not.Delivered.common.config.PasswordEncoder;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;


	public User signUpUser(SignupRequestDto request) {
		String encodedPassword = passwordEncoder.encode(request.getPassword());

		User user = User.builder()
				.email(request.getEmail())
				.password(encodedPassword)
				.userName(request.getUsername())
				.userStatus(request.getUserStatus())
				.address(request.getAddress())
				.build();

		return userRepository.save(user);
	}


  public User login(LoginRequestDto request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return user;
  }
}
