package Not.Delivered.auth.service;


import Not.Delivered.auth.dto.SignupRequestDto;
import Not.Delivered.common.config.PasswordEncoder;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public Long signUpUser(SignupRequestDto request) {
		String encodedPassword = passwordEncoder.encode(request.getPassword());

		User user = User.builder()
				.email(request.getEmail())
				.password(encodedPassword)
				.userName(request.getUsername())
				.userStatus(request.getUserStatus())
				.build();

		return userRepository.save(user).getUserId();
	}


}
