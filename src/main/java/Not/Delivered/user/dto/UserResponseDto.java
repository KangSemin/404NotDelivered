package Not.Delivered.user.dto;

import Not.Delivered.common.entity.Address;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.domain.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

public class UserResponseDto {

  private Long userId;
  private String userName;
  private String email;
  private UserStatus userStatus;

  private String phoneNumber;
  private Boolean isWithdrawal = false;

  private Address address;

  @Builder
  public UserResponseDto(Long userId, String userName, String email, UserStatus userStatus, String phoneNumber, Boolean isWithdrawal, Address address) {
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.userStatus = userStatus;
    this.isWithdrawal = isWithdrawal;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public static UserResponseDto convertToDto(User user){
    return UserResponseDto.builder()
        .userId(user.getUserId())
        .userName(user.getUserName())
        .email(user.getEmail())
        .userStatus(user.getUserStatus())
        .phoneNumber(user.getPhoneNumber())
        .isWithdrawal(user.getIsWithdrawal())
        .address(user.getAddress())
        .build();
  }
}
