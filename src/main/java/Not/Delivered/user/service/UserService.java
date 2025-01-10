package Not.Delivered.user.service;

import Not.Delivered.user.domain.User;
import Not.Delivered.user.dto.UserResponseDto;
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

  public UserResponseDto getUserById(Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("User ID and user details must not be null");
    }

    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User ID not found"));

    return UserResponseDto.convertToDto(user);
  }


  public UserResponseDto updateUser(Long userId, UserUpdateDto userDetails) {

    if (userId == null || userDetails == null) {
      throw new IllegalArgumentException("User ID and user details must not be null");
    }

    User user = userRepository.findById(userId)
        .orElseThrow(
            () -> new TempUserNotFoundException("User Not Found."));

//   업데이트 로직 미구현


    return UserResponseDto.convertToDto(user);
  }

  public void deleteUser(Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("User ID must not be null");
    }

    userRepository.deleteById(userId);
  }

}