package Not.Delivered.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuCreateRequestDto {

  @NotNull(message = "가게 ID는 필수 입력 값입니다.")
  private final Long shopId;

  @NotBlank(message = "메뉴 이름은 필수 입력 값입니다.")
  private final String menuName;

  @NotNull(message = "가격은 필수 입력 값입니다.")
  private final Integer price;
}
